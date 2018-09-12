package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.AdjustBill;
import org.apache.ibatis.annotations.Param;

public interface AdjustBillMapper extends Mapper<AdjustBill> {


    /**
     * 通过 adjust_bill_id(调整单编号) 查询 AdjustBill 信息  -- 2018/06/07  13:44 rk
     * @param adjustBillCode
     * @return
     */
    AdjustBill selectAdjustBillByAdjustBillCode(@Param(value = "adjustBillCode") String adjustBillCode);


    /**
     * 通过 adjustBillId 查询表 k_adjust_bill 中 id 的值    -- 2018/06/02  15:30 rk
     * @param
     * @return
     */
    int selectId(String adjustBillId);

    /**
     * 通过 adjustBillId 查询表 k_adjust_bill 中 adjust_status 的值    -- 2018/06/02  14:46 rk
     * @param adjustBillId
     * @return
     */
    int selectAdjustStatus(String adjustBillId);


    Integer selectMaxId();

}