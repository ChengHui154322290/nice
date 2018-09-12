package com.nice.good.service;

import com.nice.good.model.AdjustBill;
import com.nice.good.core.Service;
import com.nice.good.model.AdjustBillDetail;
import com.nice.good.vo.CodeAndDateVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/01.
 */
public interface AdjustBillService extends Service<AdjustBill> {

    String saveAdjustBill(AdjustBill adjustBill, String placeId, String userId);


    String updateAdjustBillDetails(List<AdjustBillDetail> adjustBillDetails, String placeId, String userId);


    String updateAdjustBill(List<AdjustBill> adjustBills, String placeId, String userId);


    Integer selectAdjustStatus(String adjustBillId);

    CodeAndDateVo getAdjustIdAndDate(String userId);

}
