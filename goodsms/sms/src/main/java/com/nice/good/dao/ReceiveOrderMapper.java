package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.ReceiveOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ReceiveOrderMapper extends Mapper<ReceiveOrder> {
    List<ReceiveOrder> selectByManyCondition(Map map);
    List<String> selectReceiveByProviderCode(String providerCode);
    List<String> selectReceiveByCarrierCode(String carrierCode);
    ReceiveOrder selectByReceiveId(String receiveId);

    /**
     *  通过最大 id 值查询 ReceiveOrder.java 中的 receiveCode(收货单编号)  --  rk  2018/05/02
     * @return
     */
    ReceiveOrder findReceiveOrderByMaxId();

    Integer  selectMaxId();

    String selectReceiveIdByReceiveCode(String receiveCode);

    void  updateFields(Map<String,Object> map);


	List<String> selectAllOutsideCode();

	void updateOrderStatusByPurchaseCode(@Param(value = "purchaseCode") String purchaseCode,@Param(value = "status") Integer status);

	String selectReceiveCodeByReceiveId(String receiveId);

	Integer selectStatusByReceiveCode(String receiveCode);

	String selectGooderCodeByReceiveCode(String receiveCode);
}