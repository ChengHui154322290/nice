package com.nice.good.service;
import com.nice.good.core.Result;
import com.nice.good.core.Service;
import com.nice.good.dto.OrderGoodDto;
import com.nice.good.model.Good;
import com.nice.good.model.Order;
import com.nice.good.model.OrderGoodMapping;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/26.
 */
public interface OrderService extends Service<Order> {
     
    String addOrderOpration(Order order, String placeId,String userId) throws Exception;

	String deleteOrderById(List<String> orderIds);

	String updateStatus(List<String> orderIds ,Integer status,String userId);

	/**
	 *  通过最大 id 值查询 Order.java  --  rk  2018/04/27
	 * @return
	 */
	Order findOrderByMaxId();

	void uploadExcelForAddStoreSeat(List<OrderGoodDto> success, String userId, Order order);

	String addGoodMapping(List<Good> goods, String orderId,String placeId, String userId);

	String delGoodMapping(List<Integer> ids);

    Result listGoodMapping(String orderId);
}
