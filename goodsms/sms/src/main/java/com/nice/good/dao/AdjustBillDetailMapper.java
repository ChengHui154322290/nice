package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.AdjustBillDetail;
import com.nice.good.vo.BillDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdjustBillDetailMapper extends Mapper<AdjustBillDetail> {


    /**
     * 通过 adjust_bill_id 查询 k_adjust_bill_detail 表中 status 的值   -- 2018/06/14  10:38  rk
     * @param adjustBillId
     * @return
     */
    List<Integer> selectStatusByAdjustBillId(String adjustBillId);

    /**
     * 通过 detail_id(主键id) 查询 k_adjust_bill_detail(调整明细表) 中对应的 status 值  -- 2018/06/08  10:24  rk
     * @param detailId
     * @return
     */
    Integer selectStatusByDetailId(String detailId);

    /**
     * 通过 adjust_bill_id(调整单编号) 删除 k_adjust_bill_detail 表中数据。  -- 2018/06/07  16:19 rk
     * @param adjustBillId
     */
    void deleteDetailByAdjustBillId(String adjustBillId);

    /**
     * 通过 调整单编号(adjust_bill_id) 查询 k_adjust_bill_detail(调整单明细表) 信息   -- 2018/06/07 14:10  rk
     * @param adjustBillId
     * @return
     */
    List<AdjustBillDetail> selectAdjustBillDetailByAdjustBillId(String adjustBillId);

    /**
     *  多表关联，查询数据。 -- 表 s_seat_stock、 表 n_gooder 、 表 g_good 、 表 i_store_seat
     *  String gooder_code 货主编码 , String good_code 货品编码 ,
     *  String area_code 库区编码 , String good_model 货品规格 ,
     *  String seat_code 库位编码 , String good_name 货品名称
     *   -- 2018/06/04  16:54 rk
     * @return
     */
    List<BillDetailVo> findMsg(@Param(value = "gooderCode") String gooderCode,
                               @Param(value = "goodCode") String goodCode,
                               @Param(value = "areaCode") String areaCode,
                               @Param(value = "goodModel") String goodModel,
                               @Param(value = "seatCode") String seatCode,
                               @Param(value = "goodName") String goodName,
                               @Param(value = "placeId") String placeId);





}