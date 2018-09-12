package com.nice.miniprogram.api;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.*;
import com.nice.miniprogram.service.*;
import com.nice.miniprogram.vo.GoodCountSumVo;
import com.nice.miniprogram.vo.GoodSpuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
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
public class GoodSpuController {

	private static Logger log = LoggerFactory.getLogger(GoodSpuController.class);


    @Resource
    private GoodSpuService goodSpuService;

    @Resource
    private GoodCountService goodCountService;
    @Resource
    private ShowRoomInfoService showRoomInfoService;
    @Resource
    private GoodStyleService goodStyleService;
    @Resource
    private UserService userService;
    @Resource
    private GoodImgService goodImgService;
    @Resource
    private GoodSkuService goodSkuService;

    @ApiOperation(value = "获取商品列表/通过搜索栏输入样品名称获取商品列表", httpMethod = "GET")
    @GetMapping("/spuList")
    public Result list(@ApiParam(value = "风格id集") @RequestParam(value = "styleIds",required = false) String styleIds,
                       @ApiParam(value = "品类id") @RequestParam(value = "categoryId",required = false) Integer categoryId,
                       @ApiParam(value = "尺码") @RequestParam(value = "goodSize",required = false) String goodSize,
                       @ApiParam(value = "佣金（最低比例）") @RequestParam(value = "lowBrokerageRatio",required = false) Double lowBrokerageRatio,
                       @ApiParam(value = "佣金（最高比例）") @RequestParam(value = "highBrokerageRatio",required = false) Double highBrokerageRatio,
                       @ApiParam(value = "价格区间（最低价格）") @RequestParam(value = "lowNormalPrice",required = false) Double lowNormalPrice,
                       @ApiParam(value = "价格区间（最高价格）") @RequestParam(value = "highNormalPrice",required = false) Double highNormalPrice,
                       @ApiParam(value = "场地编码") @RequestParam(value = "placeNumber",required = false) String placeNumber,
                       @ApiParam(value = "库位编码") @RequestParam(value = "seatCode",required = false) String seatCode,
                       @ApiParam(value = "库区编码") @RequestParam(value = "areaCode",required = false) String areaCode,
                       @ApiParam(value = "页码") @RequestParam(value = "page",required = false) Integer page,
                       @ApiParam(value = "页长") @RequestParam(value = "size",required = false) Integer size,
                       @ApiParam(value = "样品title") @RequestParam(value = "title",required = false) String title,
                       @ApiParam(value = "直播间id") @RequestParam(value = "showRoomId",required = false) Integer showRoomId,
                       @ApiParam(value = "需要排除的库位编码") @RequestParam(value = "exSeatCode",required = false) String exSeatCode,
                       @ApiParam(value = "点击量排序") @RequestParam(value = "orderByClick",required = false) Integer orderByClick,
                       @ApiParam(value = "价格排序") @RequestParam(value = "orderByPrice",required = false) Integer orderByPrice,
                       @ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {

        log.info("================================ 进入商品列表 ==================================");
        if(page==null){
            page = 1;
        }
        if(size == null){
            size = 10;
        }
        if(showRoomId!=null){
            ShowRoomInfo showRoomInfo = showRoomInfoService.getById(showRoomId);
            seatCode = showRoomInfo.getSeatCode();
        }
        Integer start = (page-1)*size;
        Map<String,Object> params = new HashMap<>();
        params.put("start",start);//从第几条开始
        params.put("size",size);//每页条数
        if(seatCode!=null && !"".equals(seatCode)){
            params.put("seatCode",seatCode);
        }
        if(areaCode!=null && !"".equals(areaCode)){
            params.put("areaCode",areaCode);
        }
        if(StringUtils.isNotBlank(title)){
            params.put("title",title);
        }
        if(StringUtils.isNotBlank(placeNumber)){
            params.put("placeNumber",placeNumber);
        }

        if(StringUtils.isNotBlank(styleIds )){
            String[] style = styleIds.split(",");
            List<Integer> styleList = new ArrayList<>();
            for(int i=0;i<style.length;i++){
                styleList.add(Integer.valueOf(style[i]));
            }
            params.put("styleList",styleList);
        }
        if(categoryId != null ){
            params.put("categoryId",categoryId);
        }
        if(StringUtils.isNotBlank(goodSize)){
            params.put("goodSize",goodSize);
        }
        if(lowBrokerageRatio != null ){
            params.put("lowBrokerageRatio",lowBrokerageRatio);
        }
        if(highBrokerageRatio != null ){
            params.put("highBrokerageRatio",highBrokerageRatio);
        }
        if(lowNormalPrice != null ){
            params.put("lowNormalPrice",lowNormalPrice);
        }
        if(highNormalPrice != null ){
            params.put("highNormalPrice",highNormalPrice);
        }
//        if(orderByClick==null){
//            orderByClick = 1;
//        }
        if(null!=orderByClick){
            params.put("orderByClick",orderByClick);
        }
//        params.put("orderByClick",orderByClick);
        if(null!=orderByPrice){
            params.put("orderByPrice",orderByPrice);
        }
        List<GoodSpuVo> goodSpuVos = goodSpuService.getListByParams(params);
        if(StringUtils.isNotBlank(exSeatCode) ){
            params.put("seatCode",exSeatCode);
            List<GoodSpuVo> exGoodSpuVos = goodSpuService.getListByParams(params);
            for(GoodSpuVo goodSpuVo:exGoodSpuVos){
                if(goodSpuVos.contains(goodSpuVo)) {
                    goodSpuVos.remove(goodSpuVo);
                }
            }
        }
        //======================================= 拼接返回参数 ======================================
        List<Map<String,Object>> retResult = parseRetResult(goodSpuVos);
		return ResultGenerator.genSuccessResult(retResult);
 	}

    @ApiOperation(value = "获取推荐商品列表", httpMethod = "GET")
    @GetMapping("/getRecommendSpuList")
    public Result list(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,HttpServletRequest request){

        User user = userService.getById(userId);
        Map<String,Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(user.getAnchorStyle())) {
            String[] userStyles = user.getAnchorStyle().split(",");
            params.put("styles",userStyles);
        }

        List<GoodSpuVo> goodSpuVos = goodSpuService.getRecommendSpuList(params);
        //======================================= 拼接返回参数 ======================================
        List<Map<String,Object>> retResult = parseRetResult(goodSpuVos);
        return ResultGenerator.genSuccessResult(retResult);
    }

    private List parseRetResult(List<GoodSpuVo> goodSpuVos){
        //======================================= 拼接返回参数 ======================================
        List<Map<String,Object>> retResult = new ArrayList<>();
        Map<String,Object> retMap = null;
        for(GoodSpuVo goodSpuVo:goodSpuVos) {
            retMap = new HashMap<>();
            retMap.put("spuCode", goodSpuVo.getSpuCode());
            if(null != goodSpuVo.getStyleId()) {
                GoodStyle goodStyle = goodStyleService.getById(goodSpuVo.getStyleId());
                retMap.put("title",goodSpuVo.getSpuName()+goodStyle.getName());
            }else{
                retMap.put("title",goodSpuVo.getSpuName());
            }
            if(goodSpuVo.getClickNum()!=null) {
                retMap.put("clickNum", goodSpuVo.getClickNum());
            }else{
                retMap.put("clickNum", 0);
            }
            if(goodSpuVo.getCollarNum()!=null) {
                retMap.put("collarNum", goodSpuVo.getCollarNum());
            }else{
                retMap.put("collarNum", 0);
            }
//            List<GoodSku> goodSkus = goodSkuService.getBySpu(goodSpuVo.getSpuCode());
            if(null != goodSpuVo.getNormalPrice()) {
                retMap.put("normalPrice", goodSpuVo.getNormalPrice());
            }
            if(goodSpuVo.getBrokerageRatio() != null) {
                retMap.put("brokerageRatio", goodSpuVo.getBrokerageRatio());
            }else{
                retMap.put("brokerageRatio", 0);
            }
            GoodImg img = goodImgService.getBySpu(goodSpuVo.getSpuCode());
            if(img != null ) {
                retMap.put("picUrl", img.getImgUrl());
            }else{
                retMap.put("picUrl", "");
            }
            retResult.add(retMap);
        }
        return retResult;
    }
}
