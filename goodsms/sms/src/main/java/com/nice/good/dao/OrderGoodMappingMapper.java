package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.OrderGoodMapping;
import com.nice.good.vo.PurchaseDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderGoodMappingMapper extends Mapper<OrderGoodMapping> {
    // rk 2018/04/10
    //推测，需要在该interface中编写对应的方法，用于调用mapper/xml中对应的方法，实现sql语句的编写。

    List<String> selectByGoodCode(String goodCode);

    List<OrderGoodMapping> selectMapperByOrderId(String orderId);

    void deleteByOrderId(String orderId);

    List<PurchaseDetailVo> selectDetailByOrderId(String orderId);


    OrderGoodMapping selectById(Integer id);


    void updateStatusById(@Param(value = "id") Integer id, @Param(value = "status") Integer status);

    void updateGoodMapping(@Param(value = "purchaseStatus") Integer purchaseStatus,
                           @Param(value = "purchaseLineCode") Integer purchaseLineCode);

    List<OrderGoodMapping> selectMapperByPurchaseCode(String purchaseCode);
}