package com.nice.good.aop;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nice.good.constant.FILE_PATH;
import com.nice.good.dao.GoodPictureMapper;
import com.nice.good.dto.ColorImgDto;
import com.nice.good.dto.GoodImportDto;
import com.nice.good.dto.GoodLinkDto;
import com.nice.good.dto.ImgDowloadDto;
import com.nice.good.model.GoodPicture;
import com.nice.good.model.SysUser;
import com.nice.good.service.GoodService;
import com.nice.good.utils.*;
import com.nice.good.web.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nice.good.constant.FILE_PATH.IP1;
import static com.nice.good.constant.FILE_PATH.IP2;
import static com.nice.good.constant.REDIS_KEY.IMG_UPLOAD_Fail;
import static com.nice.good.constant.REDIS_KEY.IMG_UPLOAD_SUCCESS;

@Aspect
@Component
@Slf4j
public class PictureAspect {

//    @Pointcut("execution(com.nice.good.service.impl.GoodServiceImpl.uploadExcelForAddStoreSeat()")
//    public void export() {
//    }

    @Autowired
    private GoodPictureMapper goodPictureMapper;


    @Resource
    private GoodService goodService;


    @Pointcut("@annotation(com.nice.good.aop.LogAnnotation)")
    private void export() {
    }


    @Autowired
    private StringRedisTemplate redisTemplate;

    Thread thread = null;


    @AfterReturning(value = "export()", returning = "result")  // 使用上面定义的切入点
    public void exportPicture(JoinPoint joinPoint, Object result) {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        SysUser sessionUser = (SysUser) request.getSession().getAttribute("sessionUser");

        String userId = sessionUser.getUsername();


        if (result == null || !result.toString().equals("操作成功!")) {
            return;
        }

//        String userId = "admin";

        Long start = System.currentTimeMillis();

        try {

            thread = new Thread(() -> {

                //货品id
                String goodId = null;

                //货品编码
                String goodCode = null;

                //货品链接
                String goodLink = null;

                //总记录数
                int totalNum = 0;

                //当前记录上传的位置
                int currentNum = 0;

                //上传失败的记录
                List<GoodLinkDto> failList = new ArrayList<>();

                //上传成功的记录
                List<GoodLinkDto> successList = new ArrayList<>();

                ImgDowloadDto dowloadDto = new ImgDowloadDto();

                try {

                    Map<String, String> map = getLogMark(joinPoint);

                    String content = map.get(LoggerUtil.CONTENT);

                    String methodName = joinPoint.getSignature().getName();

                    Object object1 = joinPoint.getArgs()[0];

                    if (content.equals("图片导入") && "uploadExcelForAddStoreSeat".equals(methodName)) {
                        ArrayList list = (ArrayList) object1;
                        //货品总的记录条数
                        totalNum = list.size();


                        dowloadDto.setTotalNum(totalNum);

                        for (Object obj : list) {

                            if (obj instanceof GoodImportDto) {
                                //货品链接
                                GoodImportDto good = (GoodImportDto) obj;

                                goodLink = good.getGoodLink();

                                //货品编码
                                goodCode = good.getGoodCode();

                                //货品id
                                goodId = good.getGoodId();

                                if (StringUtils.isBlank(goodLink)) {
                                    // 图片下载失败,存放在redis中
                                    addFailGood(goodCode, failList, goodLink, goodId);
                                    dowloadDto.setCurrentNum(++currentNum);
                                    WebSocket.sendInfo(JSONObject.toJSON(dowloadDto).toString());
                                    continue;
                                }

                                //获取链接里面货品id
                                String id = null;
                                String[] goodLinks = goodLink.split("id=");
                                if (goodLinks.length > 1) {
                                    String partLink = goodLinks[1];
                                    String[] parts = partLink.split("&");
                                    id = parts[0];
                                }
//                                String id = goodLink.substring(goodLink.lastIndexOf("=") + 1);
                                if (StringUtils.isBlank(id)) {
                                    // 图片下载失败,存放在redis中
                                    addFailGood(goodCode, failList, goodLink, goodId);
                                    dowloadDto.setCurrentNum(++currentNum);
                                    WebSocket.sendInfo(JSONObject.toJSON(dowloadDto).toString());
                                    continue;
                                }


                                List<String> imgList = new ArrayList<>();

                                //左侧介绍图 东东
                                String imgsUrl1 = IP1 + id;

//                                String responseResult1 = CommonNetUtil.chooseProtocol(imgsUrl1, CommonNetUtil.CHARSETNAME);

                                GetResponse getResponse1 = new GetResponse(imgsUrl1).invoke();
                                if (getResponse1.is()) {
                                    // 图片下载失败,存放在redis中
                                    addFailGood(goodCode, failList, goodLink, goodId);
                                    dowloadDto.setCurrentNum(++currentNum);
                                    WebSocket.sendInfo(JSONObject.toJSON(dowloadDto).toString());
                                    continue;
                                }
                                String responseResult1 = getResponse1.getResponseResult();


//                                if (responseResult1 == null) {
//                                    // 图片下载失败,存放在redis中
//                                    addFailGood(goodCode, failList, goodLink, goodId);
//                                    dowloadDto.setCurrentNum(++currentNum);
//                                    WebSocket.sendInfo(JSONObject.toJSON(dowloadDto).toString());
//                                    continue;
//                                }


                                responseResult1 = responseResult1.replace("https:https:", "https:");

                                JSONArray array1 = JSONObject.parseArray(responseResult1);

                                if (array1 != null && array1.size() > 0) {
                                    imgList.addAll(array1.toJavaList(String.class));
                                }


                                //颜色小图 东东
                                String imgsUrl2 = IP2 + id;

//                                String responseResult2 = CommonNetUtil.chooseProtocol(imgsUrl2, CommonNetUtil.CHARSETNAME);

                                GetResponse getResponse2 = new GetResponse(imgsUrl2).invoke();
                                if (getResponse2.is()) {
                                    // 图片下载失败,存放在redis中
                                    addFailGood(goodCode, failList, goodLink, goodId);
                                    dowloadDto.setCurrentNum(++currentNum);
                                    WebSocket.sendInfo(JSONObject.toJSON(dowloadDto).toString());
                                    continue;
                                }
                                String responseResult2 = getResponse2.getResponseResult();


//                                if (responseResult1 == null) {
//                                    // 图片下载失败,存放在redis中
//                                    addFailGood(goodCode, failList, goodLink, goodId);
//                                    dowloadDto.setCurrentNum(++currentNum);
//                                    WebSocket.sendInfo(JSONObject.toJSON(dowloadDto).toString());
//                                    continue;
//                                }


                                responseResult2 = responseResult2.replace("https:https:", "https:");

                                JSONArray array2 = JSONObject.parseArray(responseResult2);

                                List<ColorImgDto> list2 = new ArrayList<>();
                                if (array2 != null && array2.size() > 0) {
                                    for (Object obj2 : array2) {
                                        ColorImgDto dto = new ColorImgDto();
                                        JSONObject object = JSONObject.parseObject(obj2.toString());
                                        Object color = object.get("color");
                                        Object img = object.get("img");
                                        dto.setColor(color.toString());
                                        dto.setImg(img.toString());

                                        list2.add(dto);

                                    }
                                }

                                System.out.println("我是图片存放路径: " + FILE_PATH.IMG_PATH);

                                File sf = new File(FILE_PATH.IMG_PATH);

                                if (!sf.exists()) {
                                    sf.mkdirs();
                                }


                                for (String imgUrl : imgList) {
                                    //图片后缀
                                    String img = imgUrl.substring(imgUrl.lastIndexOf("."));

                                    Long imgId = PictureIdUtils.getImgId();

                                    String filename = imgId + img;


                                    Timestamp timeStamp = TimeStampUtils.getTimeStamp();
                                    //绑定商品
                                    GoodPicture picture = new GoodPicture();
                                    System.out.println("我是goodId:" + good.getGoodId());
                                    picture.setGoodId(good.getGoodId());
                                    picture.setImgName(filename);
                                    picture.setImgUrl(filename);
                                    picture.setCreater(userId);
                                    picture.setCreatetime(timeStamp);
                                    picture.setModifier(userId);
                                    picture.setModifytime(timeStamp);
                                    picture.setImgId(imgId);

                                    goodPictureMapper.insert(picture);

                                    Thread.sleep(40);

                                    DownloadUtils.download(imgUrl, filename, sf);

                                }

                                if (list2 != null && list2.size() > 0) {
                                    for (ColorImgDto dto : list2) {
                                        //图片后缀
                                        String imgUrl = dto.getImg();
                                        String img = imgUrl.substring(imgUrl.lastIndexOf("."));

                                        Long imgId = PictureIdUtils.getImgId();

                                        String filename = imgId + img;

                                        Timestamp timeStamp = TimeStampUtils.getTimeStamp();


                                        GoodPicture picture = new GoodPicture();
                                        picture.setGoodId(good.getGoodId());
                                        picture.setImgName(filename);
                                        picture.setImgUrl(filename);
                                        picture.setCreater(userId);
                                        picture.setCreatetime(timeStamp);
                                        picture.setModifier(userId);
                                        picture.setModifytime(timeStamp);
                                        picture.setImgId(imgId);

                                        goodPictureMapper.insert(picture);

                                        Thread.sleep(40);

                                        DownloadUtils.download(imgUrl, filename, sf);
                                    }
                                }

                            }

                            //向redis中保存上传成功的记录
                            GoodLinkDto goodLinkDto = new GoodLinkDto();
                            goodLinkDto.setGoodId(goodId);
                            goodLinkDto.setGoodCode(goodCode);
                            goodLinkDto.setGoodLink(goodLink);

                            successList.add(goodLinkDto);

                            //这里webSocket向前端推送数据
                            dowloadDto.setCurrentNum(++currentNum);
                            WebSocket.sendInfo(JSONObject.toJSON(dowloadDto).toString());
                        }

                    }
                    //同步商品图片到小程序
                    goodService.parseImg(userId);
                } catch (Exception e) {
                    //图片下载失败,存放在redis中
                    addFailGood(goodCode, failList, goodLink, goodId);

                    //这里webSocket向前端推送数据
                    try {
                        dowloadDto.setCurrentNum(++currentNum);
                        WebSocket.sendInfo(JSONObject.toJSON(dowloadDto).toString());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }

                //向redis中保存上传失败和成功的记录
                redisTemplate.delete(IMG_UPLOAD_Fail);
                redisTemplate.delete(IMG_UPLOAD_SUCCESS);
                redisTemplate.opsForValue().set(IMG_UPLOAD_Fail, JSONObject.toJSON(failList).toString());
                redisTemplate.opsForValue().set(IMG_UPLOAD_SUCCESS, JSONObject.toJSON(successList).toString());

            });
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Long end = System.currentTimeMillis();
            log.info("记录日志消耗时间:" + (end - start) / 1000);
        }
    }

    private void addFailGood(String goodCode, List<GoodLinkDto> failList, String goodLink, String goodId) {
        GoodLinkDto goodLinkDto = new GoodLinkDto();
        goodLinkDto.setGoodId(goodId);
        goodLinkDto.setGoodCode(goodCode);
        goodLinkDto.setGoodLink(goodLink);

        failList.add(goodLinkDto);
    }


    private Map<String, String> getLogMark(JoinPoint joinPoint) throws Exception {
        Map<String, String> map = new HashMap<>();
        String methodName = joinPoint.getSignature().getName();
        String targetName = joinPoint.getTarget().getClass().getName();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
                map.put(LoggerUtil.CONTENT, logAnnotation.content());
                map.put(LoggerUtil.LOG_TYPE, logAnnotation.logType());
            }
        }
        return map;
    }


}

