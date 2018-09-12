package com.nice.good.service;
import com.nice.good.model.InventoryBillDetail;
import com.nice.good.core.Service;
import com.nice.good.vo.BillDetailVo;

import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/07.
 */
public interface InventoryBillDetailService extends Service<InventoryBillDetail> {
      void inventoryBillDetailUpdate(InventoryBillDetail inventoryBillDetail,String userId);


      /**
       * 通过 inventory_bill_id(盘点单编号) 查询 对应的 status 值，返回 HashSet<T> 集合   -- 2018/06/13  16:04  rk
       * @param inventory_bill_id
       * @return
       */
      List<Integer> selectStatusByInventoryBillId(String inventory_bill_id);


      /**
       * 通过 inventory_bill_id(盘点单编号) 查询 k_inventory_bill_detail 表中相应的 List<T> InventoryBillDetail   --  2018/06/12  15:30  rk
       * @param inventory_bill_id
       * @return
       */
      List<InventoryBillDetail> selectInventoryBillDetailByInventoryBillId(String inventory_bill_id);

}
