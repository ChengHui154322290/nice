package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.dao.OrderDetailMapper;
import com.nice.miniprogram.model.*;
import com.nice.miniprogram.dao.CollarOrderMapper;
import com.nice.miniprogram.service.*;
import com.nice.miniprogram.core.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/06/07.
 */
@Service
@Transactional
public class CollarOrderServiceImpl extends AbstractService<CollarOrder> implements CollarOrderService{
    private static Logger log = LoggerFactory.getLogger(CollarOrderServiceImpl.class);
    @Resource
    private CollarOrderMapper collarOrderMapper;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private ShowRoomInfoService showRoomInfoService;
    @Resource
    private SeatStockService seatStockService;
    @Resource
    private ExhibitionRoomService exhibitionRoomService;
    /*
    新增领用订单及明细
     */
    @Override
    @Transactional
    public Integer addCollarOrderAndDetail(CollarOrder collarOrder, List<OrderDetail> orderDetails,String placeNumber) {
        log.info("新增领用订单及明细！");
        collarOrderMapper.insert(collarOrder);
        int orderId = collarOrder.getId();
        List<OrderDetail> details = new ArrayList<>();
        //将主表id添加到明细中
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrderId(orderId);
            orderDetail.setOrderType(0);//0：领用单
            orderDetail.setStatus(0);//待收货:0
            if(null!=collarOrder.getShowRoomId()) {
                ShowRoomInfo showRoominfo = showRoomInfoService.getById(collarOrder.getShowRoomId());
                orderDetail.setTargetSeatCode(showRoominfo.getSeatCode());
            }else{
//                SeatStock seatStock = seatStockService.findById(collarOrder.getSeatId());
                ExhibitionRoom exhibitionRoom = exhibitionRoomService.getById(collarOrder.getExhibitionRoomId());
                orderDetail.setTargetSeatCode(exhibitionRoom.getSeatCode());
            }
            details.add(orderDetail);

        }
        //库存冻结
        details = orderDetailService.freezeStock(details,placeNumber);
        //批量新增明细
        orderDetailService.batchInsert(details);

        return orderId;
    }

    /*
    根据条件获取订单列表
     */
    @Override
    public List<CollarOrder> selectListByParams(Map<String, Object> params) {
        List<CollarOrder> orders = collarOrderMapper.selectListByParams(params);
        return orders;
    }

    /*
    修改订单及明细状态
     */
    @Override
    public void updateOrderAndDetail(Integer status, Integer orderId ,String userName) {
        Date current = new Date();
        CollarOrder collarOrder = collarOrderMapper.selectByPrimaryKey(orderId);
        collarOrder.setStatus(status);
        collarOrder.setModifier(userName);
        collarOrder.setModifytime(current);
        collarOrderMapper.updateByPrimaryKey(collarOrder);
        Map<String,Object> params = new HashMap<>();
        params.put("status",collarOrder.getStatus());
        params.put("orderId",collarOrder.getId());
        params.put("userName",userName);
        params.put("current",current);
        orderDetailService.batchUpdateStatusByOrderId(params);
        List<OrderDetail> details = orderDetailService.selectByParams(params);
        for(OrderDetail orderDetail:details) {
            if (status == 2) {//状态收货时，库存移动
                orderDetailService.catchStock(orderDetail);
            } else if (status == 4) {//状态归还时，库存移动
                orderDetailService.returnStock(orderDetail);
            }
        }
    }

    @Override
    public Integer selectMaxId() {
        return collarOrderMapper.selectMaxId();
    }

}
