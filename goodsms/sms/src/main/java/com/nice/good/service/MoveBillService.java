package com.nice.good.service;

import com.nice.good.model.MoveBill;
import com.nice.good.core.Service;
import com.nice.good.model.MoveBillDetail;
import com.nice.good.vo.CodeAndDateVo;

import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/05.
 */
public interface MoveBillService extends Service<MoveBill> {

    String moveBillSave(MoveBill moveBill, String placeId, String userId);


    String updateMoveBillDetails(List<MoveBillDetail> moveBillDetails, String placeId, String userId);


    String updateMoveBill(List<MoveBill> moveBills,String placeId, String userId);

    CodeAndDateVo getMoveIdAndDate(String userId);


    /**
     * 通过移动单号、移动状态、移动类型、说明、创建人、创建开始时间、创建结束时间、
     * 修改人、修改开始时间、修改结束时间、来源库位、目标库位 查询 k_move_bill(移动单表) 的信息   -- 2018/06/06  10:58  rk
     *
     * @param moveBillId
     * @param moveStatus
     * @param moveType
     * @param remark
     * @param createId
     * @param createDateStart
     * @param createDateEnd
     * @param modifyId
     * @param modifyDateStart
     * @param modifyDateEnd
     * @param sourceSeat
     * @param targetSeat
     * @return
     */
    List<MoveBill> findMoveBill(String moveBillId, Integer moveStatus, Integer moveType, String remark,
                                String createId, Date createDateStart, Date createDateEnd, String modifyId,
                                Date modifyDateStart, Date modifyDateEnd, String sourceSeat, String targetSeat,String placeId);


}
