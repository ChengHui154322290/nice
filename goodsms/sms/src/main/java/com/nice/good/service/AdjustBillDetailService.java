package com.nice.good.service;
import com.nice.good.model.AdjustBillDetail;
import com.nice.good.core.Service;
import com.nice.good.vo.BillDetailVo;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/02.
 */
public interface AdjustBillDetailService extends Service<AdjustBillDetail> {

      /**
       * 通过 adjustBillId 查询 k_adjust_bill_detail 表中 status 的值   -- 2018/06/14  10:38  rk
       * @param adjustBillId
       * @return
       */
      List<Integer> selectStatusByAdjustBillId(String adjustBillId,String placeId);


}
