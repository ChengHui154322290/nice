package com.nice.miniprogram.api;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.enums.ResultCode;
import com.nice.miniprogram.model.*;
import com.nice.miniprogram.service.*;
import com.nice.miniprogram.utils.DateFormatUtil;
import com.nice.miniprogram.vo.ScoreAndEvaluationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
* Created by CodeGenerator on 2018/07/11.
*/
@Api(value = "评分评价接口",description = "评分评价相关操作")
@RestController
@RequestMapping("/good/evaluation")
public class GoodEvaluationController {

	private static Logger log = LoggerFactory.getLogger(GoodEvaluationController.class);

    @Resource
    private GoodEvaluationService goodEvaluationService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private UserService userService;
    @Resource
    private GoodEvaluationImgService goodEvaluationImgService;
    @Resource
    private GoodSkuService goodSkuService;

//    private final String IMG_PATH="/nice/sms/img/";
//    private final String IMG_PATH="E:/miniprogram/";
    private final String IMG_PATH="/nice/miniprogram/img/";
    /*
    修改订单明细(添加评分评价)
     */
    @ApiOperation(value = "添加评分评价", httpMethod = "POST")
    @PostMapping("/addFeedback")
    @Transactional
    public Result addFeedback(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                              @RequestBody ScoreAndEvaluationVo scoreAndEvaluationVo , HttpServletRequest request){
        log.info("================================== 添加评分评价 ==================================");
        User user = userService.getById(userId);
        String userName = user.getAccount();//用户名
        GoodEvaluation goodEvaluation = new GoodEvaluation();
        goodEvaluation.setSkuCode(scoreAndEvaluationVo.getSkuCode());
        GoodSku goodSku = goodSkuService.getBySku(scoreAndEvaluationVo.getSkuCode());
        goodEvaluation.setSpuCode(goodSku.getSpuCode());
        goodEvaluation.setScore(scoreAndEvaluationVo.getScore());
        goodEvaluation.setHomogeneity(scoreAndEvaluationVo.getHomogeneity());
        goodEvaluation.setFansPraise(scoreAndEvaluationVo.getFansPraise());
        goodEvaluation.setEvaluation(scoreAndEvaluationVo.getEvaluation());
        goodEvaluation.setCreater(userName);
        goodEvaluation.setCreatetime(new Date());
        goodEvaluation.setModifier(userName);
        goodEvaluation.setModifytime(new Date());
        goodEvaluationService.save(goodEvaluation);
        OrderDetail orderDetail = orderDetailService.getById(scoreAndEvaluationVo.getDetailId());
        orderDetail.setIsFeedback(1);
        orderDetailService.update(orderDetail);
        List<Integer> imgids = scoreAndEvaluationVo.getImgIds();
        for(Integer imgId:imgids){
            GoodEvaluationImg img = goodEvaluationImgService.getById(imgId);
            img.setEvaluationId(goodEvaluation.getId());
            goodEvaluationImgService.update(img);
        }
//        orderDetailService.updateFeedback(scoreAndEvaluationVo.getDetailId(),1);
        return ResultGenerator.genSuccessResult();
    }
    /*
        修改订单明细(添加评分评价)
         */
    @ApiOperation(value = "添加评分评价图片", httpMethod = "POST")
    @PostMapping("/addFeedbackImg")
    public Result addFeedbackImg(@RequestParam("file") MultipartFile multipartFile,HttpServletRequest request) throws Exception {
        GoodEvaluationImg img = new GoodEvaluationImg();
        Map<String,Object> retMap = new HashMap<>();
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        List<MultipartFile> files = multipartRequest.getFiles("file");
        if(files == null ||files.size()==0){
            return ResultGenerator.genFailResult(ResultCode.PICTURE_IS_NULL);
        }
        MultipartFile file = files.get(0);

        //用来限制用户上传文件大小的  4M
        int maxPostSize = 4 * 1024 * 1024;

//        InputStream ins = file.getInputStream();
        File uploadFile = new File(file.getOriginalFilename());
//        long size = uploadFile.length(); // 大小 bytes
        long size = file.getSize();
        if(size>maxPostSize){
            //图片大于4兆
            return ResultGenerator.genFailResult(ResultCode.PIC_OVER_MAXSIZE);
        }
        String fileName = file.getOriginalFilename();
        //允许上传的文件类型
        String fileType = "png,bmp,jpeg,jpg,jpe";
        //获取文件后缀名
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase().trim();
        if ( !Arrays.<String> asList(fileType.split(",")).contains(extName)) {
            return ResultGenerator.genFailResult(ResultCode.PIC_TYPE_ERROR);
        }
        //上传文件
        uploadFileUtil(file.getBytes(), IMG_PATH, fileName);
        img.setImgUrl(fileName);
        goodEvaluationImgService.save(img);
        retMap.put("imgId",img.getId());
        retMap.put("imgName",img.getImgUrl());
        return ResultGenerator.genSuccessResult(retMap);
    }
    /**
     * 上传文件的方法
     *
     * @param file：文件的字节
     * @param imgPath：文件的路径
     * @param imgName：文件的名字
     * @throws Exception
     */
    private void uploadFileUtil(byte[] file, String imgPath, String imgName) throws Exception {
        File targetFile = new File(imgPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(imgPath + imgName);
        out.write(file);
        out.flush();
        out.close();
    }

    /*
    好评、中评、差评展示
     */
    @ApiOperation(value = "好评、中评、差评展示", httpMethod = "GET")
    @GetMapping("/listFeedback")
    public Result listFeedback(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                               @ApiParam(value = "商品编码") @RequestParam(value = "spuCode",required = false) String spuCode,
                               @ApiParam(value = "货品编码") @RequestParam(value = "skuCode",required = false) String skuCode,
                               @ApiParam(value = "评价类型 1:好评，2：中评，3：差评") @RequestParam(value = "type",required = false) Integer type,
                               @ApiParam(value = "页码") @RequestParam(value = "page",required = false) Integer page,
                               @ApiParam(value = "页长") @RequestParam(value = "size",required = false) Integer size,
                               HttpServletRequest request){



        if(page == null){
            page = 1;
        }
        if(size == null){
            size = 10;
        }
        Integer start = (page-1)*size;
        Map<String,Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(skuCode)){
            List<String> skuList = new ArrayList<>();
            skuList.add(skuCode);
            params.put("skuCodes",skuList);
        }else{
            List<GoodSku> goodSkus = goodSkuService.getBySpu(spuCode);
            List<String> skuList = new ArrayList<>();
//            String[] skuList = new String[goodSkus.size()];
            for (int i=0;i<goodSkus.size();i++){
                skuList.add(goodSkus.get(i).getSkuCode());
//                skuList[i]=goodSkus.get(i).getSkuCode();
            }
            params.put("skuCodes",skuList);
        }
        params.put("type",type);
        params.put("start",start);
        params.put("size",size);
         List<GoodEvaluation> goodEvaluations = goodEvaluationService.listFeedback(params);
        //重封装返回
        List<Map<String,Object>> retList = new ArrayList<>();
        for(GoodEvaluation goodEvaluation:goodEvaluations){
            Map<String,Object> retMap = retMap = new HashMap<>();
            GoodSku goodSku = goodSkuService.getBySku(goodEvaluation.getSkuCode());
//            retMap.put("skuCode",goodEvaluation.getSkuCode());
            retMap.put("skuSpec",goodSku.getGoodColor()+" "+goodSku.getGoodSize() );
            retMap.put("score",goodEvaluation.getScore());
            retMap.put("evaluation",goodEvaluation.getEvaluation());
            retMap.put("fansPraise",goodEvaluation.getFansPraise());
            retMap.put("homogeneity",goodEvaluation.getHomogeneity());
            retMap.put("createtime",DateFormatUtil.dateToString(goodEvaluation.getCreatetime()));
//            User user = userService.findUser(goodEvaluation.getCreater());
            retMap.put("nickName",goodEvaluation.getCreater());
            User user = userService.findUser(goodEvaluation.getCreater());
            if(StringUtils.isNotBlank(user.getPicture())) {
                retMap.put("userPic", user.getPicture());
            }else{
                retMap.put("userPic", "");
            }
//            retMap.put("userPic",user.getPicture());
            List<GoodEvaluationImg> goodEvaluationImgs = goodEvaluationImgService.getByEvaluationId(goodEvaluation.getId());
            List<String> imgUrls = new ArrayList<>();
            for(GoodEvaluationImg goodEvaluationImg:goodEvaluationImgs){
                imgUrls.add(goodEvaluationImg.getImgUrl());
            }
            retMap.put("imgUrls",imgUrls);
            retList.add(retMap);
        }
        return ResultGenerator.genSuccessResult(retList);
    }

    /*
        修改订单明细(添加评分评价)
         */
    @ApiOperation(value = "删除评分评价图片", httpMethod = "POST")
    @PostMapping("/delFeedbackImg")
    public Result delFeedbackImg(@ApiParam(value = "图片id") @RequestParam(value = "imgId",required = false) Integer imgId,
//                                 @ApiParam(value = "图片名称") @RequestParam(value = "imgName",required = false) String imgName,
                                 HttpServletRequest request) throws Exception {

        goodEvaluationImgService.deleteById(imgId);
        return ResultGenerator.genSuccessResult();
    }

}

