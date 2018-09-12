package com.nice.good.service.impl;


import com.nice.good.core.AbstractService;
import com.nice.good.dao.OrderDetailMapper;
import com.nice.good.dao.SeatStockMapper;
import com.nice.good.model.Good;
import com.nice.good.model.SeatStock;
import com.nice.good.service.GoodCountService;
import com.nice.good.service.GoodService;
import com.nice.good.service.OrderDetailService;
import com.nice.good.service.SeatStockService;
import com.nice.good.wx_model.GoodCount;
import com.nice.good.wx_model.OrderDetail;
import com.nice.good.wx_model.OrderDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/08/03.
 */
@Service
@Transactional
public class OrderDetailServiceImpl extends AbstractService<OrderDetail> implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public  void orderDetailAdd(OrderDetail orderDetail,String userId){



        orderDetailMapper.insert(orderDetail);

    }


   @Override
   public void  orderDetailUpdate(OrderDetail orderDetail,String userId){
        orderDetailMapper.updateByPrimaryKey(orderDetail);
   }

    @Override
    public List<OrderDetail> freezeStock(List<OrderDetail> details, String placeNumber) {
        return null;
    }

    @Override
    public void batchInsert(List<OrderDetail> details) {

    }

    @Override
    public List<OrderDetailVo> selectBespeakDetail(Integer id) {
        return orderDetailMapper.selectBespeakDetail(id);
    }

    @Override
    public void collarOrderUpdate(String orderCode) {
        orderDetailMapper.collarOrderUpdate(orderCode);
    }

    @Override
    public void collarOrderRepayUpdate(String orderCode) {
        orderDetailMapper.collarOrderRepayUpdate(orderCode);
    }

    @Override
    public void deleteByBesOrderCode(String orderCode) {
        orderDetailMapper.deleteByBesOrderCode(orderCode);
    }

    @Override
    public void deleteByCollarOrderCode(String orderCode) {
        orderDetailMapper.deleteByCollarOrderCode(orderCode);
    }

    @Override
    public List<OrderDetail> selectByParams(Integer orderId) {

       return orderDetailMapper.selectByParams(orderId);
    }

    @Override
    public void collarOrderUpdateByOrderId(Integer id) {
         orderDetailMapper.collarOrderUpdateByOrderId(id);
    }

    @Override
    public void updateRepayDetail(Integer id) {
        orderDetailMapper.updateRepayDetail(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Integer orderId) {
        return orderDetailMapper.findByOrderId(orderId);
    }

    @Override
    public List<OrderDetail> selectByOrderCode(String orderCode) {
        return orderDetailMapper.selectByOrderCode(orderCode);
    }

    @Override
    public OrderDetail getById(Integer id) {
        return orderDetailMapper.selectByPrimaryKey(id);
    }

}
