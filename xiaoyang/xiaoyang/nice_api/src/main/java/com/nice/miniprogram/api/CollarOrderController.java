package com.nice.miniprogram.api;
import com.alibaba.fastjson.JSON;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.*;
import com.nice.miniprogram.service.*;
import com.nice.miniprogram.utils.CodeFormatUtil;
import com.nice.miniprogram.vo.OrderDetailVo;
import com.nice.miniprogram.vo.CollarOrderVo;
import com.nice.miniprogram.vo.OrderListVo;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.nice.miniprogram.enums.ResultCode.INTERNAL_SERVER_ERROR;
import static com.nice.miniprogram.utils.DateFormatUtil.dateToString;


/**
* Created by CodeGenerator on 2018/06/07.
*/
@Api(value = "领用单接口",description = "领用单相关操作")
@RestController
@RequestMapping("/collarOrder")
public class CollarOrderController {

	private static Logger log = LoggerFactory.getLogger(CollarOrderController.class);

    @Resource
    private AlternativeService alternativeService;
    @Resource
    private CollarOrderService collarOrderService;
    @Resource
    private ShowRoomInfoService showRoomInfoService;
    @Resource
    private StoreSeatService storeSeatService;
    @Resource
    private ExhibitionRoomService exhibitionRoomService;
    @Resource
    private UserService userService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private GoodImgService goodImgService;
    @Resource
    private GoodSkuService goodSkuService;

    @ApiOperation(value = "新增领用单", httpMethod = "POST")
    @PostMapping("/add")
    public Result add(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                      @RequestBody CollarOrderVo collarOrderVo,
//                      @ApiParam(value = "场地编码") @RequestParam(value = "placeNumber",required = false) String placeNumber,
                      HttpServletRequest request) {
        log.info("================================== 新增领用单 ==================================");
        log.info("新增领用订单参数：{}",JSON.toJSONString(collarOrderVo));

        User user = userService.getById(userId);
        String userName = user.getAccount();//用户名
//        log.info("新增领用订单明细参数：{0}",collarOrderVo.getCollarOrderDetails());
        CollarOrder collarOrder = new CollarOrder();
        List<OrderDetailVo> collarOrderDetails = collarOrderVo.getCollarOrderDetails();

        BeanUtils.copyProperties(collarOrderVo,collarOrder); //将vo中的数据copy到model中
        Integer id = collarOrderService.selectMaxId();
        if(id==null||"".equals(id)){
            id = 1;
        }else{
            id++;
        }
        String code = CodeFormatUtil.formatCode("JL",id);
        log.info("生成的订单编号："+ code);
        collarOrder.setOrderCode(code);
        collarOrder.setOwnId(userId);
        collarOrder.setOperatorId(String.valueOf(userId));
        collarOrder.setOperatorType(0);
        collarOrder.setCreater(userName);
        collarOrder.setCreatetime(new Date());
        collarOrder.setModifier(userName);
        collarOrder.setModifytime(new Date());
//        if(){
//
//        }
        List<OrderDetail> newOrderDetails = new ArrayList<>();
        OrderDetail orderDetail = null;
        for(OrderDetailVo orderDetailVo :collarOrderDetails){
            orderDetail = new OrderDetail();
            BeanUtils.copyProperties(orderDetailVo, orderDetail); //将vo中的数据copy到model中
            orderDetail.setCreater(userName);
            orderDetail.setCreatetime(new Date());
            orderDetail.setModifier(userName);
            orderDetail.setModifytime(new Date());
            newOrderDetails.add(orderDetail);
        }
        String placeNumber = null;
        if(collarOrder.getShowRoomId() != null) {
            placeNumber = showRoomInfoService.queryPlaceNumber(collarOrder.getShowRoomId());
        }else if(null!=collarOrder.getExhibitionRoomId()){
            placeNumber = exhibitionRoomService.queryPlaceNumber(collarOrder.getExhibitionRoomId());
        }else{
            StoreSeat storeSeat = storeSeatService.findByCode(collarOrder.getSeatCode(),"");
            placeNumber =storeSeat.getPlaceNumber();
        }
        Integer orderId = 0;
        orderId = collarOrderService.addCollarOrderAndDetail(collarOrder, newOrderDetails,placeNumber);
        if(orderId ==null ){
            return ResultGenerator.genFailResult(INTERNAL_SERVER_ERROR);
        }else{
            List<Integer> ids = new ArrayList<>();
            for(OrderDetailVo orderDetailVo :collarOrderDetails){
                ids.add(orderDetailVo.getAlternativeId());
            }
            alternativeService.deleteByOwnIds(ids);
		    return ResultGenerator.genSuccessResult();
        }
 	}

    @ApiOperation(value = "修改领用单", httpMethod = "POST")
    @PostMapping("/update")
    public Result update(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                         @RequestBody CollarOrderVo collarOrderVo, HttpServletRequest request) {
        log.info("================================== 修改领用单状态 ==================================");
//        log.info("修改领用订单参数：{}",JSON.toJSONString(collarOrderVo));
        User user = userService.getById(userId);
        String userName = user.getAccount();//用户名

        collarOrderService.updateOrderAndDetail(collarOrderVo.getStatus(),collarOrderVo.getId(),userName);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "批量修改领用单状态", httpMethod = "POST")
    @PostMapping("/batchUpdate")
    public Result batchUpdate(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                              @RequestBody OrderListVo orderListVo, HttpServletRequest request) {
        log.info("================================== 批量修改领用单状态 ==================================");
        log.info("批量修改领用订单参数：{}",JSON.toJSONString(orderListVo));
        User user = userService.getById(userId);
        String userName = user.getAccount();//用户名
        for(CollarOrderVo collarOrderVo : orderListVo.getCollarOrderVos()) {
            collarOrderService.updateOrderAndDetail(collarOrderVo.getStatus(),collarOrderVo.getId(), userName);
        }
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "查询订单列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "tab",dataType = "int" ,required = true,value = "页签(1:待收货,2:待归还,3:已归还,5:历史,//4:预约单)"),
            @ApiImplicitParam(paramType = "query",name = "page",dataType = "int" ,required = true,value = "页码"),
            @ApiImplicitParam(paramType = "query",name = "size",dataType = "int" ,required = true,value = "每页条数")
    })
    @GetMapping("/list")
    public Result list(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                       @RequestParam Integer tab, @RequestParam Integer page, @RequestParam Integer size, HttpServletRequest request) {
        log.info("================================== 进入查询订单列表 ==================================");

        List<Integer> statusList = new ArrayList<>();
        if(page==null || page==0){
            page = 1;
        }
        if(size == null){
            size = 10;
        }
        Integer start = (page-1)*size;

        Map<String,Object> params = new HashMap<>();
//        params.put("operatorType",0);//主播创建
        params.put("ownId",userId);//userId从session中取
        params.put("start",start);//从第几条开始
        params.put("size",size);//每页条数
        List<CollarOrder> orders = new ArrayList<>();
        if(tab==null) {
//            statusList.add(0);
        }else if(tab==1){
            statusList.add(0);
            statusList.add(1);
            params.put("statusList",statusList);
            orders = collarOrderService.selectListByParams(params);
        }else if(tab==2){
            statusList.add(2);
            statusList.add(3);
            params.put("statusList",statusList);
            orders = collarOrderService.selectListByParams(params);
        }else if(tab==3){
            statusList.add(4);
            params.put("statusList",statusList);
            orders = collarOrderService.selectListByParams(params);
        }else if(tab==5){
            statusList.add(2);
            statusList.add(3);
            params.put("statusList",statusList);
            List<CollarOrder> newOrders2 = collarOrderService.selectListByParams(params);
            orders.addAll(newOrders2);
//            if(newOrders2!=null && newOrders2.size()==0){
//                statusList.add(4);
//                params.put("statusList", statusList);
//                List<CollarOrder> newOrders4 = collarOrderService.selectListByParams(params);
//                orders.addAll(newOrders4);
//            }
            if(orders==null || orders.size()<10) {
                statusList = new ArrayList<>();
                statusList.add(4);
                params.put("statusList", statusList);
                params.put("size",size-newOrders2.size());//每页条数
                List<CollarOrder> newOrders4 = collarOrderService.selectListByParams(params);
                orders.addAll(newOrders4);
            }
        }

//        params.put("status",status);


        /*================== 重封装对象 ===================*/
        List<Map<String,Object>> retResult = new ArrayList<>();
        Map<String,Object> retMap = null;
        for(CollarOrder collarOrder:orders){
            retMap = new HashMap<>();
            retMap.put("collarOrderId",collarOrder.getId());
            retMap.put("orderCode",collarOrder.getOrderCode());
            retMap.put("createtime",dateToString(collarOrder.getCreatetime()));
            retMap.put("status",collarOrder.getStatus());
            retMap.put("isSelect",false);
            List<OrderDetail> details = orderDetailService.selectDetailListByOrderId(collarOrder.getId());
            if(details!=null && details.size()>0) {
                queryImg: for(int i=0;i<details.size();i++) {
//                    String skuCode = details.get(i).getSkuCode();
//                    List<GoodImg> imgs = goodImgService.getBySku(skuCode);
                    String skuCode = details.get(i).getSkuCode();
                    GoodSku goodSku = goodSkuService.getBySku(skuCode);
                    String spuCode = goodSku.getSpuCode();
                    List<GoodImg> imgs = goodImgService.getListBySpu(spuCode);
                    if (imgs != null && imgs.size() > 0) {
                        retMap.put("imageUrl", imgs.get(0).getImgUrl());
                        break queryImg;
                    }
                }
            }
            retResult.add(retMap);
        }

        return ResultGenerator.genSuccessResult(retResult);
    }


}
