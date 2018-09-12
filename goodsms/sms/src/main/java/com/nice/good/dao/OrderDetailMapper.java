package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.wx_model.BookingSlipVo;
import com.nice.good.wx_model.OrderDetail;
import com.nice.good.wx_model.OrderDetailVo;

import java.util.List;

public interface OrderDetailMapper extends Mapper<OrderDetail> {
	List<OrderDetailVo> selectBespeakDetail(Integer orderId);

	void collarOrderUpdate(String orderCode);

	void collarOrderRepayUpdate(String orderCode);

	void deleteByBesOrderCode(String orderCode);

	void deleteByCollarOrderCode(String orderCode);

	List<OrderDetail> selectByParams(Integer orderId);

	void collarOrderUpdateByOrderId(Integer id);

	void updateRepayDetail(Integer id);

	List<OrderDetail> findByOrderId(Integer orderId);

	List<OrderDetail> selectByOrderCode(String orderCode);

//	OrderDetail findByOrderIdById(Integer id);
}