package com.nice.miniprogram.api;

import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.*;
import com.nice.miniprogram.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "我的仓库接口",description = "我的仓库相关操作")
@RestController
@RequestMapping("/holdStock")
public class HoldStockController {

    private static Logger log = LoggerFactory.getLogger(HoldStockController.class);


    @Resource
    private CollarOrderService collarOrderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private GoodSkuService goodSkuService;
    @Resource
    private GoodSpuService goodSpuService;
    @Resource
    private GoodStyleService goodStyleService;
    @Resource
    private GoodImgService goodImgService;
    /**
     *
     * @param userId
     * @param request
     * @return
     */
    @ApiOperation(value = "展示已领用未归还的样品", httpMethod = "GET")
    @GetMapping("/list")
    public Result list(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                       HttpRequest request){

        HashMap<String,Object> params = new HashMap<>();
        params.put("status",2);
        params.put("ownId",userId);
        List<OrderDetail> orderDetails = orderDetailService.queryByParams(params);
        List<Map> retList = new ArrayList<>();
        for(OrderDetail orderDetail:orderDetails){
            CollarOrder collarOrder = collarOrderService.getById(orderDetail.getOrderId());
            GoodSku goodSku = goodSkuService.getBySku(orderDetail.getSkuCode());
            GoodSpu goodSpu = goodSpuService.getBySpu(goodSku.getSpuCode());
            Map<String,Object> retMap = new HashMap<>();
            //商品title
            if(null != goodSpu.getStyleId()) {
                GoodStyle style = goodStyleService.getById(goodSpu.getStyleId());
                retMap.put("title", goodSpu.getSpuName() + style.getName());
            }else{
                retMap.put("title", goodSpu.getSpuName());
            }
            retMap.put("orderCode",collarOrder.getOrderCode());
            retMap.put("choseNum",orderDetail.getChoseNum());
            retMap.put("status",orderDetail.getStatus());
            retMap.put("isFeedBack",orderDetail.getIsFeedback());
            retMap.put("normalPrice",goodSku.getNormalPrice());
            retMap.put("color",goodSku.getGoodColor());
            retMap.put("size",goodSku.getGoodSize());
            retMap.put("detailId",orderDetail.getId());
            retMap.put("orderId",orderDetail.getOrderId());
            retMap.put("skuCode",goodSku.getSkuCode());
            retMap.put("isSelect",false);
//            List<GoodImg> imgs = goodImgService.getBySku(goodSku.getSkuCode());
            List<GoodImg> imgs = goodImgService.getListBySpu(goodSku.getSpuCode());
            if(imgs!=null && imgs.size()>0){
                retMap.put("picUrl",imgs.get(0).getImgUrl());
            }
            retList.add(retMap);
        }
        return ResultGenerator.genSuccessResult(retList);
    }

}
