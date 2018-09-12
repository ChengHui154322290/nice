package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.MoveBill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MoveBillMapper extends Mapper<MoveBill> {



    MoveBill selectByMoveBillCode(@Param(value = "moveBillCode") String moveBillCode);

    /**
     * 通过移动单号、移动状态、移动类型、说明、创建人、创建开始时间、创建结束时间、
     * 修改人、修改开始时间、修改结束时间、来源库位、目标库位 查询 k_move_bill(移动单表) 的信息   -- 2018/06/06  10:58  rk
     **/


    List<MoveBill> findMoveBill(@Param(value = "moveBillId") String moveBillId,
                                @Param(value = "moveStatus") Integer moveStatus,
                                @Param(value = "moveType") Integer moveType,
                                @Param(value = "remark") String remark,
                                @Param(value = "createId") String createId,
                                @Param(value = "createDateStart") Date createDateStart,
                                @Param(value = "createDateEnd") Date createDateEnd,
                                @Param(value = "modifyId") String modifyId,
                                @Param(value = "modifyDateStart") Date modifyDateStart,
                                @Param(value = "modifyDateEnd") Date modifyDateEnd,
                                @Param(value = "sourceSeat") String sourceSeat,
                                @Param(value = "targetSeat") String targetSeat,
                                @Param(value = "placeId") String placeId);

    /**
     * 根据 moveBillId(移动单编号) 查询 k_move_bill 表中对应的 move_status(移动状态)     --  2018/06/05  17:02  rk
     * @param move_bill_id
     * @return
     */
    Integer selectMoveStatus(@Param(value = "move_bill_id") String move_bill_id);

    /**
     * 查询 k_move_bill 表中最大的id值    -- 2018/06/05 11:09  rk
     * @return
     */
    Integer selectMaxId();

}