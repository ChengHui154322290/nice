package com.nice.good.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.dto.ColorImgDto;
import com.nice.good.dto.GoodImportDto;
import com.nice.good.dto.GoodLinkDto;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.*;
import com.nice.good.service.GoodService;
import com.nice.good.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

;import static com.nice.good.constant.FILE_PATH.IP1;
import static com.nice.good.constant.FILE_PATH.IP2;
import static com.nice.good.constant.REDIS_KEY.IMG_UPLOAD_Fail;
import static com.nice.good.constant.REDIS_KEY.IMG_UPLOAD_SUCCESS;

/**
 * @Description: 货品档案
 * @Author: fqs
 * @Date: 2018/3/23 10:29
 * @Version: 1.0
 */
@RestController
@RequestMapping("/good")
public class GoodController extends BaseController {


    private static Logger log = LoggerFactory.getLogger(GoodController.class);
    private final static String TMP_PATH = "/file_temp/";
    private final static String STORE_SEAT_MODEL = "good_model_tmp.xlsx";
    private final static String UP_PATH = "/upload_temp/";
    private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
    private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径
    @Resource
    private GoodService goodService;

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private GoodConfigMapper goodConfigMapper;


    @Resource
    private GoodAreaMapper goodAreaMapper;

    @Resource
    private GoodAliasMapper goodAliasMapper;


    @Resource
    private GoodPictureMapper goodPictureMapper;

    @Resource
    private GoodStyleMapper goodStyleMapper;

    @Resource
    private GoodCategoryMapper goodCategoryMapper;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @LogAnnotation(logType = "其他日志", content = "货品档案新增")
    @PostMapping("/add")
    public Result add(@RequestBody Good good, HttpServletRequest request) {
        if (good == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        String placeId = getPlaceId(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }

        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }


        try {

            String errorMsg = goodService.goodAdd(good, placeId, userId);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }


        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    @LogAnnotation(logType = "其他日志", content = "货品档案删除")
    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> goodIds, HttpServletRequest request) {
        if (goodIds == null || goodIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = goodService.deleteGoodByGoodId(goodIds, placeId);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }
        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/list")
    public Result list(@RequestBody Good good, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {

        PageHelper.startPage(page, size);


        PageInfo pageInfo = null;
        try {

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            List<String> gooderCodes = getGooderCodes(request);

            List<Good> list;
            if (gooderCodes == null || gooderCodes.size() == 0) {
                list = null;
            } else {

                if (StringUtils.isBlank(good.getGoodCode())) {
                    good.setGooderCode(gooderCodes.get(0));
                }

                list = goodService.findByConditions(good);
            }


//            List<String> gooderCodes = getGooderCodes(request);
//
//            List<Good> goodList = new ArrayList<>() ;
//
//            List<Good> list =null;
//
//            if (gooderCodes != null && gooderCodes.size() > 0) {
//
//
//                list = goodService.findByConditions(good);
//
//                System.out.println("我是总数list："+list.size());
//
//
//
//            }
//
//
//            System.out.println("我是记录总数goodList："+goodList.size());

            pageInfo = new PageInfo(list);


        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 货品别名新增
     */
    @LogAnnotation(logType = "其他日志", content = "货品别名新增")
    @PostMapping("/addGoodAlias")
    public Result addGoodAlias(@RequestBody List<GoodAlias> goodAliasList, @RequestParam String goodId, HttpServletRequest request) {
        if (goodAliasList == null || goodAliasList.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {

            String userId = getUserName(request);

            String errorMsg = goodService.addGoodAlias(goodAliasList, goodId, userId);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }
        } catch (Exception e) {
            log.error("新增货品别名异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 货品别名修改删除
     */
    @LogAnnotation(logType = "其他日志", content = "货品别名修改删除")
    @PostMapping("/delGoodAlias")
    public Result delGoodAlias(@RequestParam List<String> aliasIds, HttpServletRequest request) {
        if (aliasIds == null || aliasIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {


            String errorMsg = goodService.delGoodAlias(aliasIds);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }
        } catch (Exception e) {
            log.error("货品别名修改删除异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    /**
     * 货品别名修改刷新
     */
    @PostMapping("/listGoodAlias")
    public Result listGoodAlias(@RequestParam String goodId) {
        Object data;
        try {

            Result result = goodService.listGoodAlias(goodId);

            data = result.getData();

        } catch (Exception e) {
            log.error("查询货品库区操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult().setData(data);

    }


    /**
     * 修改查询字段回显
     */

    @PostMapping("/listAll")
    public Result listAll(@RequestBody Good good, HttpServletRequest request) {

        String placeId = getPlaceId(request);

        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }


        if (good != null) {
            String goodId = good.getGoodId();
            good = goodMapper.selectByPrimaryKey(good.getGoodId());
            GoodConfig goodConfig = goodConfigMapper.selectConfigByGoodId(goodId, placeId);
            List<GoodArea> goodAreas = goodAreaMapper.selectAreaByGoodIdAndPlaceId(goodId, placeId);
            List<GoodAlias> goodAliasList = goodAliasMapper.selectAliasByGoodId(goodId);
            //图片url
            List<String> list = goodPictureMapper.selectImgsByGoodId(goodId);
            good.setImgIds(list);
            good.setGoodConfig(goodConfig);
            good.setGoodAreas(goodAreas);
            good.setGoodAlias(goodAliasList);

            return ResultGenerator.genSuccessResult(good);

        }

        return ResultGenerator.genSuccessResult();

    }

    /**
     * 货品档案导入预览
     *
     * @param
     * @return
     */
    @LogAnnotation(logType = "其他日志", content = "货品档案导入预览")
    @RequestMapping(value = "/uploadForView", method = RequestMethod.POST)
    public Result uploadForView(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile file = multipartRequest.getFile("file");
        if (file == null) {
            return ResultGenerator.genFailResult("文件不存在,请重新上传");
        }

        List<String> listGooder = getGooderCodes(request);

        //截取后缀重命名
        String UuName = ExcelImportUtils.getUuName(file.getOriginalFilename());
        //将文件暂存
        ExcelImportUtils.addExlce(file, UuName);
        //解析文件
        List<GoodImportDto> goods = parseExcel(file);
        List<String> fileName = new ArrayList<>();
        fileName.add(UuName);
        //校验数据
        Map<String, List> map = valid(goods, listGooder);

        map.put("fileName", fileName);
        return ResultGenerator.genSuccessResult(map);
    }

    /**
     * 货品档案导入保存
     *
     * @return
     */
    @LogAnnotation(logType = "其他日志", content = "货品档案导入保存")
    @RequestMapping(value = "/uploadForSave", method = RequestMethod.POST)
    public Result uploadForSave(@RequestParam String fileName, HttpServletRequest request) throws IOException {

        String placeId = getPlaceId(request);

        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        //获取文件名
        String path = PROJECT_PATH + RESOURCES_PATH + UP_PATH;
        //根据文件名和路径读取文件
        MultipartFile file = ExcelImportUtils.findFiles(path, fileName);
        if (file == null || file.isEmpty()) {
            ExcelImportUtils.deleteDir(new File(path));
            return ResultGenerator.genFailResult("文件不存在,请重新上传");
        }
        String userId = getUserName(request);

        List<String> listGooder = getGooderCodes(request);

        //解析excel文件
        List<GoodImportDto> goods = parseExcel(file);
        //校验数据
        Map<String, List<GoodImportDto>> map = valid(goods, listGooder);
        //保存
        try {
            goodService.uploadExcelForAddStoreSeat(map.get("success"), placeId, userId);
        } catch (Exception e) {

            System.out.println("我是异常: " + e);
            log.error("导入失败");
            return ResultGenerator.genFailResult(ResultCode.IMPORT_FAIL);
        }

        ExcelImportUtils.deleteDir(new File(path));
        return ResultGenerator.genSuccessResultFile(map);
    }

    /**
     * 将导入的excel文件解析为对象的集合
     *
     * @param file
     * @return
     */
    private List<GoodImportDto> parseExcel(MultipartFile file) {
        int sheetNum = 0;
        List<GoodImportDto> goods = new ArrayList<>();
        GoodImportDto newgoods;
        //获取excel指定的sheet页
        Sheet sheet = ExcelImportUtils.getExcelSheet(file, sheetNum);
        //解析，获取excel中的数据
        List<String[]> list = ExcelImportUtils.getListFromSheet(sheet);
        for (String[] strs : list) {
            newgoods = new GoodImportDto();
            newgoods.setGooderCode(strs[0].split("\\.")[0].trim());
            newgoods.setCommodityCode(strs[1].split("\\.")[0].trim());
            newgoods.setGoodCode(strs[2].split("\\.")[0].trim());
            newgoods.setGoodName(strs[3].split("\\.")[0].trim());
            newgoods.setStyleName(strs[4].trim());
            newgoods.setCategoryName(strs[5].trim());
            newgoods.setGoodColor(strs[6].split("\\.")[0].trim());
            newgoods.setGoodSize(strs[7].split("\\.")[0].trim());
            newgoods.setGoodLink(strs[8].trim());
            newgoods.setNormalPrice(strs[9].trim());
            newgoods.setSeckillPrice(strs[10].trim());
            newgoods.setDiscountMethod(strs[11].trim());
            newgoods.setDiscountLink(strs[12].trim());
            newgoods.setDiscountContent(strs[13].trim());
            newgoods.setBrokerageLink(strs[14].trim());
            newgoods.setBrokerageRatio(strs[15].trim());
            newgoods.setRemark(strs[16].split("\\.")[0].trim());
            newgoods.setStatus(strs[17].split("\\.")[0].trim());
            newgoods.setGoodModel(strs[18].split("\\.")[0].trim());
            newgoods.setPackCode(strs[19].split("\\.")[0].trim());
            newgoods.setBulk(strs[20].trim());
            newgoods.setNetWeight(strs[21].trim());
            newgoods.setTareWeight(strs[22].trim());
            newgoods.setRoughWeight(strs[23].trim());
            newgoods.setProperty(strs[24].split("\\.")[0].trim());
            newgoods.setPeriod(strs[25].split("\\.")[0].trim());
            newgoods.setPeriodUnite(strs[26].split("\\.")[0].trim());
            newgoods.setBrand(strs[27].trim());
            newgoods.setBrand(strs[27].trim());
            goods.add(newgoods);
        }
        return goods;
    }

    /**
     * 对导入的excel校验
     *
     * @Author: zy
     */
    private Map valid(List<GoodImportDto> seatList, List<String> listGooder) {
        Map map = new HashMap();
        List<GoodImportDto> successList = new ArrayList();
        List<GoodImportDto> failList = new ArrayList();
        List<String> codes = new ArrayList<>();
        List<String> styleList = goodStyleMapper.findAllStyle();
        List<String> categoryList = goodCategoryMapper.findAllCategory();
        for (GoodImportDto goodImportDto : seatList) {


            String gooderCode = goodImportDto.getGooderCode().trim();//货主编码
            String commodityCode = goodImportDto.getCommodityCode().trim();//商品编码
            String goodCode = goodImportDto.getGoodCode().trim();//货品编码
            String goodModel = goodImportDto.getGoodModel().trim();  //货品规格
            String goodName = goodImportDto.getGoodName().trim();  //货品名称
            String status = goodImportDto.getStatus().trim();   //是否启用 0否,1是
            String packCode = goodImportDto.getPackCode().trim();  //包装编码
            String bulk = goodImportDto.getBulk().trim();  //体积
            String netWeight = goodImportDto.getNetWeight().trim();   //净重
            String tareWeight = goodImportDto.getTareWeight().trim();//皮重
            String roughWeight = goodImportDto.getRoughWeight().trim();//毛重
            String property = goodImportDto.getProperty().trim(); //属性
            String period = goodImportDto.getPeriod().trim();//有效期
            String periodUnite = goodImportDto.getPeriodUnite().trim();//期效单位
            String remark = goodImportDto.getRemark().trim();//备注
            String brokerageRatio = goodImportDto.getBrokerageRatio().trim();//佣金比例
            String styleName = goodImportDto.getStyleName().trim(); //风格名字
            String categoryName = goodImportDto.getCategoryName().trim(); //类目名字
            String normalPrice = goodImportDto.getNormalPrice().trim(); //正常售价
            String seckillPrice = goodImportDto.getSeckillPrice().trim(); //秒杀价
            String brand = goodImportDto.getBrand().trim(); //品牌

            //判断必填字段是否为空
            if (StringUtils.isBlank(gooderCode) || StringUtils.isBlank(commodityCode) || StringUtils.isBlank(goodCode)
                    || StringUtils.isBlank(status) || StringUtils.isBlank(brand)) {
                goodImportDto.setError("必填字段不能为空");
                failList.add(goodImportDto);
                continue;
            }
            //判断风格名字是否存在
            if (StringUtils.isNotBlank(styleName) && styleName != null) {
                if (!styleList.contains(styleName)) {
                    goodImportDto.setError("风格不存在");
                    failList.add(goodImportDto);
                    continue;
                }
            }
            //判断类目名字是否存在
            if (StringUtils.isNotBlank(categoryName) && categoryName != null) {
                if (!categoryList.contains(categoryName)) {
                    goodImportDto.setError("类目不存在");
                    failList.add(goodImportDto);
                    continue;
                }
            }
            if (!(status.equals("是") || status.equals("否"))) {
                goodImportDto.setError("是否启用只能填:是或否");
                failList.add(goodImportDto);
                continue;
            }
            if (StringUtils.isNotBlank(periodUnite)) {
                if (!(periodUnite.equals("年") || periodUnite.equals("月") || periodUnite.equals("日"))) {
                    goodImportDto.setError("期校单位只能填:年月日");
                    failList.add(goodImportDto);
                    continue;
                }
            }
            //判断有限期是否是整数或者小数
            if (StringUtils.isNotBlank(period)) {
                if (!period.matches("-?[0-9]+.?[0-9]*")) {
                    goodImportDto.setError("有限期只能是是:整数或者小数");
                    failList.add(goodImportDto);
                    continue;
                }
            }

            if (StringUtils.isNotBlank(seckillPrice)) {
                if (!seckillPrice.matches("-?[0-9]+.?[0-9]*")) {
                    goodImportDto.setError("秒杀价只能是:整数或者小数");
                    failList.add(goodImportDto);
                    continue;
                }
            }
            if (StringUtils.isNotBlank(normalPrice)) {
                if (!normalPrice.matches("-?[0-9]+.?[0-9]*")) {
                    goodImportDto.setError("正常售价只能是:整数或者小数");
                    failList.add(goodImportDto);
                    continue;
                }
            }
            if (StringUtils.isNotBlank(brokerageRatio)) {
                if (!brokerageRatio.matches("-?[0-9]+.?[0-9]*")) {
                    goodImportDto.setError("佣金比例只能是:整数或者小数");
                    failList.add(goodImportDto);
                    continue;
                }
            }
            //如果重量不为空判断是否是整数或者小数
            if (StringUtils.isNotBlank(bulk)) {
                if (!bulk.matches("-?[0-9]+.?[0-9]*")) {
                    goodImportDto.setError("体积只能是:整数或者小数");
                    failList.add(goodImportDto);
                    continue;
                }
            }
            if (StringUtils.isNotBlank(netWeight)) {
                if (!netWeight.matches("-?[0-9]+.?[0-9]*")) {
                    goodImportDto.setError("净重只能是:整数或者小数");
                    failList.add(goodImportDto);
                    continue;
                }
            }
            if (StringUtils.isNotBlank(tareWeight)) {
                if (!tareWeight.matches("-?[0-9]+.?[0-9]*")) {
                    goodImportDto.setError("皮重只能是:整数或者小数");
                    failList.add(goodImportDto);
                    continue;
                }
            }
            if (StringUtils.isNotBlank(roughWeight)) {
                if (!roughWeight.matches("-?[0-9]+.?[0-9]*")) {
                    goodImportDto.setError("毛重只能是:整数或者小数");
                    failList.add(goodImportDto);
                    continue;
                }
            }
            //判断某些必填字段的正则是否合法
            if (!gooderCode.matches("[0-9A-Za-z]{5,25}")) {
                goodImportDto.setError("货主编码不合法");
                failList.add(goodImportDto);
                continue;
            }
            //查询货品编码,判断插入的数据与数据库中的编码是否重复
            List<String> list = goodMapper.selectgoodCode();
            if (codes.contains(goodCode) || list.contains(goodCode)) {
                goodImportDto.setError("货品编码不能重复");
                failList.add(goodImportDto);
                continue;
            }
            codes.add(goodCode);
            //判断手动添加的货主编码是否存在
//            List<String> listGooder = gooderMapper.findAllGooderCodes();

            if (listGooder == null || listGooder.size() == 0) {
                goodImportDto.setError("货主编码不存在");
            }

            if (!listGooder.contains(gooderCode)) {
                goodImportDto.setError("货主编码不存在");
                failList.add(goodImportDto);
                continue;
            }
            successList.add(goodImportDto);
        }
        map.put("success", successList);
        map.put("fail", failList);
        return map;
    }

    /**
     * 货品档案下载模板
     *
     * @param
     * @return
     * @Author: zy
     */
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        new ExcelImportUtils().dowFileUtils(response, request, STORE_SEAT_MODEL, TMP_PATH);
    }


    /**
     * 下载图片到本地  详情介绍(小明)
     */
    @RequestMapping(value = "/downloadImgs", method = {RequestMethod.GET, RequestMethod.POST})
    public Result downloadImage(@RequestBody GoodLinkDto good, HttpServletRequest request) throws Exception {

        String userId = getUserName(request);

        String goodLink = good.getGoodLink();
        if (StringUtils.isBlank(goodLink)) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("图片链接不能为空!");
        }

        //获取链接里面货品id
        String goodId = goodLink.substring(goodLink.lastIndexOf("=") + 1);

        if (StringUtils.isBlank(goodId)) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("图片链接有误!");
        }

        List<String> imgList = new ArrayList<>();

        //左侧介绍图 东东
        String imgsUrl1 = IP1 + goodId;
        GetResponse getResponse1 = new GetResponse(imgsUrl1).invoke();
        if (getResponse1.is()) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("图片获取失败!");
        }
        String responseResult1 = getResponse1.getResponseResult();


//        List<String> list1 = (ArrayList)JSONArray.parse(responseResult1);

        JSONArray array1 = JSONObject.parseArray(responseResult1);

        if (array1 != null && array1.size() > 0) {
            imgList.addAll(array1.toJavaList(String.class));
        }


        //颜色小图 东东
        String imgsUrl2 = IP2 + goodId;

        GetResponse getResponse2 = new GetResponse(imgsUrl2).invoke();
        if (getResponse2.is()) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("图片获取失败!");
        }
        String responseResult2 = getResponse2.getResponseResult();

        JSONArray array2 = JSONObject.parseArray(responseResult2);

        List<ColorImgDto> list2 = new ArrayList<>();
        if (array2 != null && array2.size() > 0) {
            for (Object obj : array2) {
                ColorImgDto dto = new ColorImgDto();
                JSONObject object = JSONObject.parseObject(obj.toString());
                Object color = object.get("color");
                Object img = object.get("img");
                dto.setColor(color.toString());
                dto.setImg(img.toString());

                list2.add(dto);

            }
        }


        //获取网页id  详情图 小明
//        String imgsUrl3 = "https://hws.m.taobao.com/cache/mtop.wdetail.getItemDescx/4.1/?data=%7Bitem_num_id%3A" + id + "%7D&type=jsonp&dataType=jsonp";
//
//        GetResponse getResponse3 = new GetResponse(imgsUrl3).invoke();
//        if (getResponse3.is()) {
//            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("图片获取失败!");
//        }
//        String responseResult3 = getResponse3.getResponseResult();
//
//
//
//        String substring3 = responseResult3.substring(responseResult3.indexOf("{"), responseResult3.lastIndexOf(")"));
//
//        JSONObject sb = JSONObject.parseObject(substring3);
//        JSONObject data = sb.getJSONObject("data");
//        JSONArray images = data.getJSONArray("images");
//
//        if (images!=null && images.size()>0){
//            List<String> list3 = images.toJavaList(String.class);
//            imgList.addAll(list3);
//        }


        String savePath = "/nice/sms/img/";

        System.out.println("我是图片存放路径: " + savePath);

        File sf = new File(savePath);

        if (!sf.exists()) {
            sf.mkdirs();
        }

        try {

            for (String imgUrl : imgList) {
                //图片后缀
                String img = imgUrl.substring(imgUrl.lastIndexOf("."));

                Long imgId = PictureIdUtils.getImgId();

                String filename = imgId + img;


                Timestamp timeStamp = TimeStampUtils.getTimeStamp();
                //绑定商品
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

                DownloadUtils.download(imgUrl, filename, sf);
            }

            if (list2 != null && list2.size() > 0) {
                for (ColorImgDto dto : list2) {
                    //图片后缀
                    String imgUrl = dto.getImg();
                    String img = imgUrl.substring(imgUrl.lastIndexOf("."));

                    Long imgId = PictureIdUtils.getImgId();

                    String filename = dto.getColor() + img;

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

                    DownloadUtils.download(imgUrl, filename, sf);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("图片获取失败!");
        }

        return ResultGenerator.genSuccessResult();

    }


    /**
     * 图片上传成功以后页面刷新
     */
    @RequestMapping(value = "/flushImgs", method = {RequestMethod.GET, RequestMethod.POST})
    public Result flushImgs(@RequestParam String goodId) {

        List<String> list = goodPictureMapper.selectImgsByGoodId(goodId);

        return ResultGenerator.genSuccessResult().setData(list);

    }


    /**
     * 从redis中获取货品上传图片的信息
     */
    @RequestMapping(value = "/getUploadMsg", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getUploadMsg() {

        String successList = redisTemplate.opsForValue().get(IMG_UPLOAD_SUCCESS);

        JSONArray successArray = JSONObject.parseArray(successList);

        String failList = redisTemplate.opsForValue().get(IMG_UPLOAD_Fail);


        JSONArray failArray = JSONObject.parseArray(failList);

        //上传成功记录数
        int totalSuccess = 0;
        if (successArray != null) {
            totalSuccess = successArray.size();
        }

        //上传失败记录数
        int totalFail = 0;
        if (failArray != null) {
            totalFail = failArray.size();
        }

        String message = "";

        redisTemplate.delete(IMG_UPLOAD_SUCCESS);
        redisTemplate.delete(IMG_UPLOAD_Fail);

        if (totalFail == 0) {
            return ResultGenerator.genSuccessResult();
        } else {

            String goodCodeList = "";
            int i = 0;

            try {

                for (Object obj : failArray) {

                    GoodLinkDto goodLinkDto = (GoodLinkDto) obj;

                    String goodCode = goodLinkDto.getGoodCode();

                    goodCodeList += ++i + ":货品编码:" + goodCode + "\n";

                }
                message = "共计" + totalSuccess + "条数据上传成功\n" +
                        totalFail + "条数据上传失败\n" +
                        "上传失败货品:" + goodCodeList;
            } catch (Exception ex) {
                ex.printStackTrace();
                return ResultGenerator.genSuccessResult();
            }
        }
        return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(message);

    }


    /**
     * 图片重新上传或者手动上传
     */
    @RequestMapping(value = "/anewUpload", method = {RequestMethod.GET, RequestMethod.POST})
    public Result anewUpload(@RequestParam Boolean flag, HttpServletRequest request) {

        if (flag) {

            String userId = getUserName(request);

            String message = "";

            //重新上传
            String failList = redisTemplate.opsForValue().get(IMG_UPLOAD_Fail);

            JSONArray failArray = JSONObject.parseArray(failList);


            for (Object obj : failArray) {

                GoodLinkDto goodLinkDto = (GoodLinkDto) obj;

                String goodCode = goodLinkDto.getGoodCode();

                String goodId = goodLinkDto.getGoodId();

                String goodLink = goodLinkDto.getGoodLink();


                if (StringUtils.isBlank(goodLink)) {
                    // 图片下载失败
                    message += goodCode + ",";
                    continue;
                }

                //获取链接里面货品id
                String id = goodLink.substring(goodLink.lastIndexOf("=") + 1);
                if (StringUtils.isBlank(id)) {
                    // 图片下载失败
                    message += goodCode + ",";
                    continue;
                }


                List<String> imgList = new ArrayList<>();

                //左侧介绍图 东东
                String imgsUrl1 = IP1 + id;
                GetResponse getResponse1 = new GetResponse(imgsUrl1).invoke();
                if (getResponse1.is()) {
                    // 图片下载失败
                    message += goodCode + ",";
                    continue;
                }
                String responseResult1 = getResponse1.getResponseResult();

                responseResult1 = responseResult1.replace("https:https:", "https:");

                JSONArray array1 = JSONObject.parseArray(responseResult1);

                if (array1 != null && array1.size() > 0) {
                    imgList.addAll(array1.toJavaList(String.class));
                }


                //颜色小图 东东
                String imgsUrl2 = IP2 + id;

                GetResponse getResponse2 = new GetResponse(imgsUrl2).invoke();
                if (getResponse2.is()) {
                    // 图片下载失败
                    message += goodCode + ",";
                    continue;
                }
                String responseResult2 = getResponse2.getResponseResult();

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

                String savePath = "/nice/sms/img/";

                System.out.println("我是图片存放路径: " + savePath);

                File sf = new File(savePath);

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
                    System.out.println("我是goodId:" + goodId);
                    picture.setGoodId(goodId);
                    picture.setImgName(filename);
                    picture.setImgUrl(filename);
                    picture.setCreater(userId);
                    picture.setCreatetime(timeStamp);
                    picture.setModifier(userId);
                    picture.setModifytime(timeStamp);
                    picture.setImgId(imgId);

                    goodPictureMapper.insert(picture);

                    DownloadUtils.download(imgUrl, filename, sf);

                }

                if (list2 != null && list2.size() > 0) {
                    for (ColorImgDto dto : list2) {
                        //图片后缀
                        String imgUrl = dto.getImg();
                        String img = imgUrl.substring(imgUrl.lastIndexOf("."));

                        Long imgId = PictureIdUtils.getImgId();

//                     String filename = dto.getColor() + img;

                        String filename = imgId + img;

                        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

                        GoodPicture picture = new GoodPicture();
                        picture.setGoodId(goodId);
                        picture.setImgName(filename);
                        picture.setImgUrl(filename);
                        picture.setCreater(userId);
                        picture.setCreatetime(timeStamp);
                        picture.setModifier(userId);
                        picture.setModifytime(timeStamp);
                        picture.setImgId(imgId);

                        goodPictureMapper.insert(picture);

                        DownloadUtils.download(imgUrl, filename, sf);
                    }
                }

            }


            if (StringUtils.isNotBlank(message)) {
                message = message.substring(0, message.lastIndexOf(","));

                redisTemplate.delete(IMG_UPLOAD_SUCCESS);
                redisTemplate.delete(IMG_UPLOAD_Fail);

                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("货主编码为:" + message + "的记录图片下载失败,请去货品档案中手动下载!");
            }

        }

        //手动上传
        redisTemplate.delete(IMG_UPLOAD_SUCCESS);
        redisTemplate.delete(IMG_UPLOAD_Fail);

        return ResultGenerator.genSuccessResult();
    }


}





