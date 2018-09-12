package com.nice.good.service;
import com.nice.good.core.Service;
import com.nice.good.wx_model.OrderDetail;
import com.nice.good.wx_model.OrderDetailVo;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/08/03.
 */
public interface OrderDetailService extends Service<OrderDetail> {
      void orderDetailAdd(OrderDetail orderDetail,String userId);
      void orderDetailUpdate(OrderDetail orderDetail,String userId);

	List<OrderDetail> freezeStock(List<OrderDetail> details, String placeNumber);

	void batchInsert(List<OrderDetail> details);

	List<OrderDetailVo> selectBespeakDetail(Integer id);

	void collarOrderUpdate(String orderCode);

	void collarOrderRepayUpdate(String orderCode);

	void deleteByBesOrderCode(String orderCode);

	void deleteByCollarOrderCode(String orderCode);

	List<OrderDetail> selectByParams(Integer orderId);

	void collarOrderUpdateByOrderId(Integer id);

	void updateRepayDetail(Integer id);

	List<OrderDetail> findByOrderId(Integer orderId);

	List<OrderDetail> selectByOrderCode(String orderCode);

	OrderDetail getById(Integer id);
}
