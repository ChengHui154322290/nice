package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.InventoryBill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface InventoryBillMapper extends Mapper<InventoryBill> {

    /**
     * 通过 inventory_bill_id(盘点单编号) 查询 k_inventory_bill 表中对应的数据    -- 2018/05/12  15:13  rk
     * @param inventory_bill_id
     * @return
     */
    InventoryBill selectInventoryBillByKeyId(@Param(value = "inventory_bill_id") String inventory_bill_id);



    List<InventoryBill> selectInventoryBill(@Param(value = "inventoryBillCode")String inventoryBillCode,
                                            @Param(value = "sourceBillCode") String sourceBillCode,
                                            @Param(value = "inventoryStatus")Integer inventoryStatus,
                                            @Param(value = "inventoryType") Integer inventoryType,
                                            @Param(value = "isBlindInventory") Integer isBlindInventory,
                                            @Param(value = "remark")String remark,
                                            @Param(value = "createId")String createId,
                                            @Param(value = "createDateStart") Date createDateStart,
                                            @Param(value = "createDateEnd")  Date createDateEnd,
                                            @Param(value = "modifyId") String modifyId,
                                            @Param(value = "modifyDateStart")  Date modifyDateStart,
                                            @Param(value = "modifyDateEnd")  Date modifyDateEnd,
                                            @Param(value = "placeId") String placeId);



    /**
     * 通过 inventory_bill_id(盘点单编号) 查询 k_inventory_bill 表中对应的 inventory_status(盘点状态)   -- 2018/06/11  15:04  rk
     * @param inventoryBillId
     * @return
     */
    Integer selectInventoryStatus(@Param(value = "inventoryBillId") String inventoryBillId);

    /**
     * 查询 k_inventory_bill 表中最大的 id 值    -- 2018/06/06  16:05 rk
     * @return
     */
    Integer selectMaxId();


    Integer selectSumDifferentByBillId(String inventoryBillId);

    InventoryBill selectInventoryBillCode(String inventoryBillCode);
}