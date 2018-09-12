package com.nice.good.service;

import com.nice.good.model.InventoryBill;
import com.nice.good.core.Service;
import com.nice.good.model.InventoryBillDetail;
import com.nice.good.vo.CodeAndDateVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/06.
 */
public interface InventoryBillService extends Service<InventoryBill> {

    String generateAdjustBills(List<InventoryBill> inventoryBills, String placeId, String userId);


    String saveInventoryBill(InventoryBill inventoryBill, String placeId, String userId);


    String reInventory(List<InventoryBill> inventoryBills, String placeId, String userId);


    String updateInventoryBillDetails(List<InventoryBillDetail> inventoryBillDetails, String placeId,String userId);

    String inventoryBillExport(List<InventoryBill> inventoryBills, HttpServletRequest request, HttpServletResponse response);

    /**
     * 通过 inventoryBillId(盘点单编号) 查询 k_inventory_bill 表中对应的数据    -- 2018/05/12  15:13  rk
     *
     * @param inventoryBillId
     * @return
     */
    InventoryBill selectInventoryBillByKeyId(String inventoryBillId);


    /**
     * 通过 inventory_bill_id(盘点单编号) 查询 k_inventory_bill 表中对应的 inventory_status(盘点状态)   -- 2018/06/11  15:04  rk
     *
     * @param inventoryBillId
     * @return
     */
    Integer selectInventoryStatus(String inventoryBillId);


    /**
     * 查询 k_inventory_bill 表中最大的 id 值    -- 2018/06/06  16:05 rk
     *
     * @return
     */
    Integer selectMaxId();

    CodeAndDateVo getInventoryIdAndDate(String userId);

}
