package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.InventoryBillDetail;
import com.nice.good.vo.BillDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InventoryBillDetailMapper extends Mapper<InventoryBillDetail> {


    /**
     * 通过 inventory_bill_id(盘点单编号) 计算 对应的 diff_quantity(差异量) 总和   -- 2018/06/15  15:46 rk
     * @param inventoryBillId
     * @return
     */
    Integer calculateDiffQuantity(String inventoryBillId);

    /**
     * 通过 inventory_bill_id(盘点单编号) 查询 对应的 status 值，返回 HashSet<T> 集合   -- 2018/06/13  16:04  rk
     * @param inventoryBillId
     * @return
     */
    List<Integer> selectStatusByInventoryBillId(String inventoryBillId);

    /**
     *  通过 detail_id(主键Id) 查询 k_inventory_bill_detail(盘点明细表) 中对应的 status 值  -- 2018/06/13  09:53  rk
     * @param detailId
     * @return
     */
    Integer selectStatusByDetailId(String detailId);

    /**
     * 通过 inventory_bill_id(盘点单编号) 查询 k_inventory_bill_detail 表中相应的 List<T> InventoryBillDetail   --  2018/06/12  15:30  rk
     * @param inventoryBillId
     * @return
     */
    List<InventoryBillDetail> selectInventoryBillDetailByInventoryBillId(String inventoryBillId);

    /**
     *
     *
     * @param map
     * @return
     */
    List<BillDetailVo> findMsg( Map<String,Object> map);

}