package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.MoveBillDetail;
import com.nice.good.vo.BillDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface MoveBillDetailMapper extends Mapper<MoveBillDetail> {


    /**
     * 通过 move_bill_id(移动单编号) 查询 k_move_bill_detail 表中的 status 值   -- 2018/06/14  10:04  rk
     * @param moveBillId
     * @return
     */
    List<Integer> selectStatusByMoveBillId(@Param(value = "moveBillId") String moveBillId);

    /**
     * 通过 detail_id(主键id) 查询 k_move_bill_detail(移动明细表) 中对应的 status 值  -- 2018/06/09  09:43  rk
     * @param detailId
     * @return
     */
    Integer selectStatusByDetailId(@Param(value = "detailId") String detailId);

    /**
     * 通过 move_bill_id(移动单编号) 查询 k_move_bill_detail 表信息   -- 2018/06/08  16:08  rk
     * @param moveBillId
     * @return
     */
    List<MoveBillDetail> selectMoveBillDetailByMoveBillId(@Param(value = "moveBillId") String moveBillId);

    List<BillDetailVo> findMsg(@Param(value = "gooderCode") String gooderCode,
                               @Param(value = "goodCode") String goodCode,
                               @Param(value = "areaCode") String areaCode,
                               @Param(value = "goodModel") String goodModel,
                               @Param(value = "seatCode") String seatCode,
                               @Param(value = "goodName") String goodName,
                               @Param(value = "placeId") String placeId);


    List<BillDetailVo> findGoalMsg(@Param(value = "gooderCode") String gooderCode,
                               @Param(value = "goodCode") String goodCode,
                               @Param(value = "areaCode") String areaCode,
                               @Param(value = "goodModel") String goodModel,
                               @Param(value = "seatCode") String seatCode,
                               @Param(value = "goodName") String goodName,
                               @Param(value = "placeId") String placeId);


}