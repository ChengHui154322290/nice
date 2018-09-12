package com.nice.good.service;
import com.nice.good.model.MoveBillDetail;
import com.nice.good.core.Service;

import java.util.List;
import java.util.Set;


/**
 * Created by CodeGenerator on 2018/06/05.
 */
public interface MoveBillDetailService extends Service<MoveBillDetail> {
      void moveBillDetailAdd(MoveBillDetail moveBillDetail,String userId) throws Exception;
      void moveBillDetailUpdate(MoveBillDetail moveBillDetail,String userId);


      /**
       * 通过 moveBillId(移动单编号) 查询 k_move_bill_detail 表中的 status 值   -- 2018/06/14  10:04  rk
       * @param moveBillId
       * @return
       */
      List<Integer> selectStatusByMoveBillId(String moveBillId);

      /**
       * 更新 k_move_bill_detail 表中数据  -- 2018/06/09  11:23  rk
       * @param moveBillDetail
       */
      void moveBillDetailUpdate(MoveBillDetail moveBillDetail);

      /**
       * 通过 detail_id(主键id) 查询 k_move_bill_detail(移动明细表) 中对应的 status 值  -- 2018/06/09  09:43  rk
       * @param detail_id
       * @return
       */
      Integer selectStatusByDetailId(String detail_id);

      /**
       * 通过 moveBillId(移动单编号) 查询 MoveBillDetail.java 信息   -- 2018/06/08  16:08  rk
       * @param moveBillId
       * @return
       */
      List<MoveBillDetail> selectMoveBillDetailByMoveBillId(String moveBillId);

      /**
       * 新增移动明细单  -- 2018/06/05  14:58 rk
       * @param moveBillDetail
       */
      void moveBillDetailAdd(MoveBillDetail moveBillDetail) throws Exception;

}
