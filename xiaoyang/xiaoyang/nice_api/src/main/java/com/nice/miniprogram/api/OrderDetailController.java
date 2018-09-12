package com.nice.miniprogram.api;
import com.alibaba.fastjson.JSON;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.*;
import com.nice.miniprogram.service.*;
import com.nice.miniprogram.vo.OrderDetailListVo;
import com.nice.miniprogram.vo.OrderDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
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
* Created by CodeGenerator on 2018/07/27.
*/
@Api(value = "领用单预约单明细接口",description = "订单明细相关操作")
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    private static Logger log = LoggerFactory.getLogger(OrderDetailController.class);

    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private CollarOrderService collarOrderService;
    @Resource
    private BespeakOrderService bespeakOrderService;
    @Resource
    private ShowRoomInfoService showRoomInfoService;
    @Resource
    private ExhibitionRoomService exhibitionRoomService;
    @Resource
    private ExhibitionService exhibitionService;
    @Resource
    private SysPlaceService sysPlaceService;
    @Resource
    private ModuleRegionService moduleRegionService;
    @Resource
    private GoodSpuService goodSpuService;
    @Resource
    private GoodSkuService goodSkuService;
    @Resource
    private UserService userService;
    @Resource
    private GoodStyleService goodStyleService;
    @Resource
    private GoodImgService goodImgService;
    @Resource
    private StoreSeatService storeSeatService;
    /*
     * 明细展示
     */
    @ApiOperation(value = "订单明细列表展示", httpMethod = "GET")
    @GetMapping("/list")
    public Result list(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                       @ApiParam(value = "订单id",required = true) @RequestParam(value = "orderId") Integer orderId,
                       @ApiParam(value = "订单类型",required = true) @RequestParam(value = "orderType") Integer orderType,HttpServletRequest request) {
        log.info("================================== 订单明细列表展示 ==================================");
        Map<String,Object> retMap = new HashMap<>();

        Integer showRoomId = null;
        Integer exhibitionRoomId= null;
        String orderCode = null;
        String seatCode = null;
        if(orderType==0){
            CollarOrder collarOrder = collarOrderService.getById(orderId);
            showRoomId = collarOrder.getShowRoomId();
            exhibitionRoomId = collarOrder.getExhibitionRoomId();
            orderCode = collarOrder.getOrderCode();
            seatCode = collarOrder.getSeatCode();
        }else if(orderType==1){
            BespeakOrder bespeakOrder = bespeakOrderService.getById(orderId);
            showRoomId = bespeakOrder.getShowRoomId();
            exhibitionRoomId = bespeakOrder.getExhibitionRoomId();
            orderCode = bespeakOrder.getOrderCode();
        }
        retMap.put("orderCode",orderCode);
        String placeNumber = null;
        if(null!=showRoomId ){
            ShowRoomInfo showRoominfo = showRoomInfoService.getById(showRoomId);
            retMap.put("name",showRoominfo.getName());
            placeNumber = showRoominfo.getPlaceNumber();
//			sysPlace = sysPlaceService.getByPlaceNumber(placeNumber);
        }else if(null!=exhibitionRoomId){
            ExhibitionRoom exhibitionRoom = exhibitionRoomService.getById(exhibitionRoomId);
            Exhibition exhibition = exhibitionService.getById(exhibitionRoom.getExhibitionId());
            retMap.put("name",exhibition.getName());
            placeNumber = exhibitionRoom.getPlaceNumber();
        }else{
            StoreSeat storeSeat = storeSeatService.findByCode(seatCode,"");
            placeNumber = storeSeat.getPlaceNumber();
        }
        SysPlace sysPlace = sysPlaceService.getByPlaceNumber(placeNumber);
        String provinceName = moduleRegionService.getCnNameById(sysPlace.getProvince());
        String cityName = moduleRegionService.getCnNameById(sysPlace.getCity());
        String districtName = moduleRegionService.getCnNameById(sysPlace.getDistrict());

        retMap.put("placeAddress",provinceName+cityName+districtName+sysPlace.getAddress());

        List<OrderDetail> details = orderDetailService.selectDetailListByOrderId(orderId);
		List<Map<String,Object>> detailList = new ArrayList<>();
        for(OrderDetail orderDetail:details){
            Map<String,Object> detailMap = new HashMap<>();
            OrderDetailVo orderDetailVo = new OrderDetailVo();
            BeanUtils.copyProperties(orderDetail,orderDetailVo);
            GoodSku goodSku = goodSkuService.getBySku(orderDetail.getSkuCode());
            GoodSpu goodSpu = goodSpuService.getBySpu(goodSku.getSpuCode());
//            List<GoodImg> imgs = goodImgService.getBySku(goodSku.getSkuCode());
            List<GoodImg> imgs = goodImgService.getListBySpu(goodSku.getSpuCode());
            List<String> pics = new ArrayList<>();
            for(GoodImg goodImg :imgs){
                pics.add(goodImg.getImgUrl());
            }
            detailMap.put("picUrls",pics);
            if(null != goodSpu.getStyleId()){
                GoodStyle goodStyle = goodStyleService.getById(goodSpu.getStyleId());
                detailMap.put("title",goodSpu.getSpuName()+goodStyle.getName());
            }else {
                detailMap.put("title", goodSpu.getSpuName());
            }
            detailMap.put("normalPrice",goodSku.getNormalPrice());     //价格
            detailMap.put("color",goodSku.getGoodColor());             //颜色
            detailMap.put("size",goodSku.getGoodSize());               //尺码
            detailMap.put("isFeedback",orderDetailVo.getIsFeedback());  //是否评价
            detailMap.put("isSelect",false);
            detailMap.put("status",orderDetailVo.getStatus());
            detailMap.put("choseNum",orderDetailVo.getChoseNum());
            detailMap.put("skuCode",orderDetailVo.getSkuCode());
            detailMap.put("id",orderDetailVo.getId());
            detailMap.put("orderId",orderDetailVo.getOrderId());
            detailList.add(detailMap);
        }
//        retMap.put("details",detailVos);
        retMap.put("details",detailList);
        return ResultGenerator.genSuccessResult(retMap);
    }

    /*
    修改订单明细
     */
    @ApiOperation(value = "修改订单明细", httpMethod = "POST")
    @PostMapping("/update")
    public Result update(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
//                         @ApiParam(value = "订单id") @RequestParam(value = "orderId",required = false) Integer orderId,
//                         @ApiParam(value = "明细id") @RequestParam(value = "detailId",required = false) Integer detailId,
//                         @ApiParam(value = "状态") @RequestParam(value = "status",required = false) Integer status,
                         @RequestBody OrderDetailVo orderDetailVo,
                         HttpServletRequest request) {
        log.info("================================== 修改订单明细（状态） ==================================");
        User user = userService.getById(userId);
        String userName = user.getAccount();//用户名
        orderDetailService.updateStatus(orderDetailVo.getOrderId(),orderDetailVo.getId(),orderDetailVo.getStatus(),userName);
        return ResultGenerator.genSuccessResult();
    }


    @ApiOperation(value = "批量修改订单明细", httpMethod = "POST")
    @PostMapping("/batchUpdate")
    public Result batchUpdate(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                              @RequestBody OrderDetailListVo orderDetailListVo, HttpServletRequest request) {
        log.info("================================== 批量修改领用单状态 ==================================");
        log.info("批量修改领用订单参数：{}",JSON.toJSONString(orderDetailListVo));
        User user = userService.getById(userId);
        String userName = user.getAccount();//用户名
        for(OrderDetailVo orderDetailVo : orderDetailListVo.getOrderDetailVos()) {
//            CollarOrder collarOrder = new CollarOrder();
//            BeanUtils.copyProperties(collarOrderVo, collarOrder);
            orderDetailService.updateStatus(orderDetailVo.getOrderId(),orderDetailVo.getId(),orderDetailVo.getStatus(), userName);
        }
        return ResultGenerator.genSuccessResult();
    }

}
