package com.nice.miniprogram.api;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.enums.ResultCode;
import com.nice.miniprogram.model.*;
import com.nice.miniprogram.service.*;
import com.nice.miniprogram.vo.GoodSkuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* Created by CodeGenerator on 2018/08/06.
*/
@Api(value = "商品接口",description = "商品相关接口")
@RestController
@RequestMapping("/good")
public class GoodSkuController {

	private static Logger log = LoggerFactory.getLogger(GoodSkuController.class);


    @Resource
    private GoodSkuService goodSkuService;
    @Resource
    private GoodSpuService goodSpuService;
    @Resource
    private GoodStyleService goodStyleService;
    @Resource
    private GoodImgService goodImgService;
    @Resource
    private ShowRoomInfoService showRoomInfoService;
    @Resource
    private GoodEvaluationService goodEvaluationService;
    @Resource
    private GoodCountService goodCountService;
    @Resource
    private StoreSeatService storeSeatService;

    @ApiOperation(value = "商品明细（spu进入/sku进入）", httpMethod = "GET")
    @GetMapping("/getDetail")
    public Result getDetail(@ApiParam(value = "商品编码") @RequestParam(value = "spuCode",required = false) String spuCode,
                            @ApiParam(value = "货品编码") @RequestParam(value = "skuCode",required = false) String skuCode,
                            @ApiParam(value = "计数标识") @RequestParam(value = "requestType",required = false) Integer requestType,
                            HttpServletRequest request) {
        log.info("================================ 进入商品明细 ==================================");

        Map<String, Object> retMap = new HashMap<>();
        GoodSkuVo goodSkuVo = new GoodSkuVo();
        if(StringUtils.isNotBlank(spuCode)) {
            Map<String, Object> params = new HashMap<>();
            params.put("spuCode", spuCode);
            List<GoodSkuVo> goodSkuVos = goodSkuService.getSkuListByParams(params);
//            if(goodSkuVos == null || goodSkuVos.size()<=0){
//                return ResultGenerator.genFailResult(ResultCode.NO_RESULT);
//            }
            goodSkuVo = goodSkuVos.get(0);
            //重新封装返回
            //规格集合
            List<Map<String, Object>> specList = new ArrayList<>();
            for (GoodSkuVo skuVo : goodSkuVos) {
                Map<String, Object> map = new HashMap<>();
                map.put("skuCode", skuVo.getSkuCode());
                map.put("spec", skuVo.getGoodColor() + "|" + skuVo.getGoodSize());
                if(!specList.contains(map)){
                    specList.add(map);
                }
            }
            retMap.put("skuAndSpec", specList);
            //好评率
            Map<String,Object> pars = new HashMap<>();
            pars.put("spuCode",spuCode);
            Integer allCount = goodEvaluationService.getCount(pars);
            pars.put("type",1);
            Integer goodCount = goodEvaluationService.getCount(pars);
            Integer rate = 0 ;
            if(allCount != null && allCount!=0) {
                rate = (goodCount / allCount) * 100;
            }
            retMap.put("rate", rate);
        }else if(StringUtils.isNotBlank(skuCode)){
            Map<String, Object> param = new HashMap<>();
            param.put("skuCode",skuCode);
            goodSkuVo = goodSkuService.getSkuVoBySkuCode(param);
//            if(goodSkuVo == null ){
//                return ResultGenerator.genFailResult(ResultCode.NO_RESULT);
//            }
            //好评率
            Map<String,Object> pars = new HashMap<>();
            pars.put("skuCode",skuCode);
            Integer allCount = goodEvaluationService.getCount(pars);
            pars.put("type",1);
            Integer goodCount = goodEvaluationService.getCount(pars);
            Double rate = 0.00D ;
            if(allCount != null && allCount!=0) {
                rate = (Double.valueOf(goodCount) / Double.valueOf(allCount)) * 100;
            }
            retMap.put("rate", rate);
        }
        retMap = parseRet(goodSkuVo,retMap);
        if(requestType!=null && requestType==1){
            goodCountService.setGoodCount((String) retMap.get("goodCode"));//计数
        }
		return ResultGenerator.genSuccessResult(retMap);
 	}


//    @ApiOperation(value = "sku进入商品明细", httpMethod = "GET")
//    @GetMapping("/getDetailBySku")
//    public Result getDetailBySku(@ApiParam(value = "货品编码") @RequestParam(value = "skuCode",required = false) String skuCode,
//                                     HttpServletRequest request) {
//        GoodSkuVo goodSkuVo = goodSkuService.getSkuVoBySkuCode(skuCode);
////        GoodSpu goodSpu = goodSpuService.getBySpu(goodSku.getSpuCode());
//        //重新封装返回
//        Map<String,Object> retMap = new HashMap<>();
//        retMap = parseRet(goodSkuVo,retMap);
//
//        return ResultGenerator.genSuccessResult(retMap);
//    }

    @ApiOperation(value = "货品列表（扫库位/直播间/新品）", httpMethod = "GET")
    @GetMapping("/skuList")
    public Result skuList(@ApiParam(value = "库位编码") @RequestParam(value = "seatCode",required = false) String seatCode,
                          @ApiParam(value = "直播间id") @RequestParam(value = "showRoomId",required = false) Integer showRoomId,
                          @ApiParam(value = "是否新品上架") @RequestParam(value = "isNewProduct",required = false) Integer isNewProduct,
                          @ApiParam(value = "样品title") @RequestParam(value = "title",required = false) String title,
                          @ApiParam(value = "页码") @RequestParam(value = "page",required = false) Integer page,
                          @ApiParam(value = "页长") @RequestParam(value = "size",required = false) Integer size,
                           HttpServletRequest request) {
        log.info("================================ 直播间中的货品列表 ==================================");

        Map<String,Object> params = new HashMap<>();
        if(page==null){
            page = 1;
        }
        if(size == null){
            size = 10;
        }
        Integer start = (page-1)*size;
        params.put("start",start);//从第几条开始
        params.put("size",size);//每页条数
        if(null!=showRoomId){
            ShowRoomInfo showRoomInfo = showRoomInfoService.getById(showRoomId);
            params.put("seatCode", showRoomInfo.getSeatCode());
        }else {
            params.put("seatCode", seatCode);
        }
        if(isNewProduct !=null && isNewProduct!=0){
            params.put("isNewProduct", isNewProduct);
        }
        if(StringUtils.isNotBlank(title)){
            params.put("title", title);
        }
        List<GoodSkuVo> goodSkuVos = goodSkuService.getSkuListByParams(params);
//        if(goodSkuVos == null || goodSkuVos.size()<=0){
//            return ResultGenerator.genFailResult(ResultCode.NO_RESULT);
//        }
        //重新封装返回
        Map<String,Object> retMap = new HashMap<>();
        List<Map<String,Object>> retList = new ArrayList<>();
        for(GoodSkuVo goodSkuVo:goodSkuVos) {
            retMap = new HashMap<>();
            retMap = parseRet(goodSkuVo, retMap);
            retList.add(retMap);
        }
        return ResultGenerator.genSuccessResult(retList);
    }

    /**
     * 封装返回
     * @param skuVo
     * @param retMap
     * @return
     */
    private Map<String,Object> parseRet(GoodSkuVo skuVo,Map<String,Object> retMap){

        GoodSpu goodSpu = goodSpuService.getBySpu(skuVo.getSpuCode());
        //商品title
        if(null != goodSpu.getStyleId()) {
            GoodStyle style = goodStyleService.getById(goodSpu.getStyleId());
            retMap.put("title", goodSpu.getSpuName() + style.getName());
        }else{
            retMap.put("title", goodSpu.getSpuName());
        }
        //链接中的itemId
        if(StringUtils.isNotBlank(goodSpu.getGoodLink())){
            String[] goodLinks = (goodSpu.getGoodLink()).split("id=");
            if(goodLinks.length>1){
                String partLink = goodLinks[1];
                String[] parts = partLink.split("&");
                retMap.put("itemId",parts[0]);
            }
        }

        //佣金比例
        if(skuVo.getBrokerageRatio() != null){
            retMap.put("brokerageRatio", skuVo.getBrokerageRatio());
        }else{
            retMap.put("brokerageRatio",0);
        }
        //正常价
        if(skuVo.getNormalPrice()!=null) {
            retMap.put("normalPrice", skuVo.getNormalPrice());
        }
        //秒杀价
        if(skuVo.getSeckillPrice()!=null){
            retMap.put("seckillPrice", skuVo.getSeckillPrice());
        }
        //可用库存
        if(skuVo.getUseNum()!= null){
            retMap.put("useNum", skuVo.getUseNum());
        }else{
            retMap.put("useNum",0);
        }
        //点击量
        if(skuVo.getClickNum()!=null){
            retMap.put("clickNum", skuVo.getClickNum());
        }
        //领用量
        if(skuVo.getClickNum()!=null){
            retMap.put("collarNum", skuVo.getCollarNum());
        }
        //货品编码
        if(StringUtils.isNotBlank(skuVo.getSkuCode())){
            retMap.put("goodCode",skuVo.getSkuCode());
        }
        //颜色
        if(StringUtils.isNotBlank(skuVo.getGoodColor())){
            retMap.put("goodColor",skuVo.getGoodColor());
        }
        //尺码
        if(StringUtils.isNotBlank(skuVo.getGoodSize())){
            retMap.put("goodSize",skuVo.getGoodSize());
        }
        //商品链接
        if(StringUtils.isNotBlank(goodSpu.getGoodLink())){
            retMap.put("goodLink",goodSpu.getGoodLink());
        }
        //优惠信息
        if(StringUtils.isNotBlank(skuVo.getDiscountContent())){
            retMap.put("discountContent",skuVo.getDiscountContent());
        }
        //优惠链接
        if(StringUtils.isNotBlank(skuVo.getDiscountLink())){
            retMap.put("discountLink",skuVo.getDiscountLink());
        }
        //图片链接
//        List<GoodImg> imgs = goodImgService.getBySku(skuVo.getSkuCode());
        List<GoodImg> imgs = goodImgService.getListBySpu(skuVo.getSpuCode());
        List<String> imgUrls = new ArrayList<>();
        for(GoodImg goodImg:imgs){
            imgUrls.add(goodImg.getImgUrl());
        }
        retMap.put("pictures", imgUrls);

        List<GoodImg> spuDetailImgs = goodImgService.getSpuDetailImgs(skuVo.getSpuCode());
        List<String> spuDetailImgUrls = new ArrayList<>();
        for(GoodImg goodImg:spuDetailImgs){
            spuDetailImgUrls.add(goodImg.getImgUrl());
        }
        retMap.put("detailPictures", spuDetailImgUrls);

        return retMap;
    }

    /**
     * 商品详情点击位置查询
     */
    @ApiOperation(value = "商品详情点击位置查询", httpMethod = "GET")
    @GetMapping("/listPlace")
    public Result listPlace(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                            @ApiParam(value = "场地编码") @RequestParam(value = "placeNumber",required = false) String placeNumber,
                            @ApiParam(value = "货品编码",required = true) @RequestParam(value = "skuCode") String skuCode) {
        log.info("================================== 样品明细位置查询 ==================================");
        if(StringUtils.isBlank(skuCode)){
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        Map<String,Object> params = new HashMap<>();
        if(null != placeNumber) {
            params.put("placeNumber", placeNumber);
        }
        params.put("skuCode",skuCode);
        List<GoodSkuVo> goodSkuVos = goodSkuService.findPlaceByParams(params);
        List<Map<String,Object>> retList = new ArrayList<>();
        for(GoodSkuVo goodVo:goodSkuVos) {
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("areaCode", goodVo.getAreaCode());
            StoreSeat storeSeat = storeSeatService.findByCode(goodVo.getSeatCode(),goodVo.getPlaceId());
            retMap.put("seatCode", storeSeat.getSeatName());//goodVo.getSeatCode());
            retMap.put("goodCode", goodVo.getSpuCode());//goodVo.getSkuCode());
            retMap.put("goodColor", goodVo.getGoodColor());
            retMap.put("goodSize", goodVo.getGoodSize());
            retMap.put("useNum", goodVo.getUseNum());
            retList.add(retMap);
        }
        return ResultGenerator.genSuccessResult(retList);
    }

    @ApiOperation(value = "扫库位/扫货品 ", httpMethod = "GET")
    @GetMapping("/scan")
    public Result scan( @ApiParam(value = "编码") @RequestParam(value = "code",required = false) String code,
                          HttpServletRequest request) {
        log.info("================================ 扫一扫 ==================================");

        Map<String,Object> params = new HashMap<>();
        params.put("seatCode", code);
        List<GoodSkuVo> goodSkuVos = goodSkuService.getSkuListByParams(params);
        if(goodSkuVos !=null && goodSkuVos.size()>0) {
            //重新封装返回
            Map<String, Object> retMap = new HashMap<>();
            List<Map<String, Object>> retList = new ArrayList<>();
            for (GoodSkuVo goodSkuVo : goodSkuVos) {
                retMap = new HashMap<>();
                retMap = parseRet(goodSkuVo, retMap);
                retList.add(retMap);
            }
            return ResultGenerator.genSuccessResult(retList);
        }else{
            Map<String, Object> param = new HashMap<>();
            param.put("skuCode",code);
            GoodSkuVo goodSkuVo = goodSkuService.getSkuVoBySkuCode(param);
            if(goodSkuVo == null ){
                return ResultGenerator.genFailResult(ResultCode.NO_RESULT);
            }
            Map<String, Object> retMap = new HashMap<>();
            if(goodSkuVo !=null) {
                //好评率
                Map<String, Object> pars = new HashMap<>();
                pars.put("skuCode", code);
                Integer allCount = goodEvaluationService.getCount(pars);
                pars.put("type", 1);
                Integer goodCount = goodEvaluationService.getCount(pars);

                Integer rate = 0;
                if (allCount != null && allCount != 0) {
                    rate = (goodCount / allCount) * 100;
                }
                retMap.put("rate", rate);
                retMap = parseRet(goodSkuVo, retMap);

            }
            return ResultGenerator.genSuccessResult(retMap);
        }
    }



}
