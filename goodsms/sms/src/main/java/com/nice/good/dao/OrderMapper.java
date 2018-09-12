package com.nice.good.dao;

import java.util.List;
import java.util.Map;

import com.nice.good.core.Mapper;
import com.nice.good.model.Order;
import com.nice.good.model.OrderGoodMapping;
import com.nice.good.vo.OrderMapVo;
import com.nice.good.vo.OrderVo;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper extends Mapper<Order> {
	/*
	 * 通过orderStatus和purchaseCode查询order对象
	 */


    List<String> selectOrderByProviderCode(String providerCode);

    Integer selectMaxId();



    void updateOrderStatus(@Param(value = "orderId") String orderId, @Param(value = "status") Integer status);

    /**
     *  通过最大 id 值查询 Order.java  --  rk  2018/04/27
     * @return
     */
    Order findOrderByMaxId();

    List<OrderVo> findByConditions(Map conditionMap);


    void updateStatusByPurchaseCode(@Param(value = "purchaseCode") String purchaseCode,@Param(value = "orderStatus") Integer orderStatus);

    String selectOrderIdPurchaseCode(String purchaseCode);

    String selectOrderPurchaseCodeByOrderId(String orderId);

}