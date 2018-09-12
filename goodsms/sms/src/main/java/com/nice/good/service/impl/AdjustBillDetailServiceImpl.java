package com.nice.good.service.impl;


import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.AdjustBillDetailMapper;
import com.nice.good.model.AdjustBillDetail;
import com.nice.good.service.AdjustBillDetailService;
import com.nice.good.core.AbstractService;
import com.nice.good.vo.BillDetailVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/02.
 */
@Service
@Transactional
public class AdjustBillDetailServiceImpl extends AbstractService<AdjustBillDetail> implements AdjustBillDetailService {
    @Resource
    private AdjustBillDetailMapper adjustBillDetailMapper;


    /**
     * 通过 adjustBillId 查询 k_adjust_bill_detail 表中 status 的值   -- 2018/06/14  10:38  rk
     * @param adjustBillId
     * @return
     */
    @Override
    public List<Integer> selectStatusByAdjustBillId(String adjustBillId,String placeId) {
        return adjustBillDetailMapper.selectStatusByAdjustBillId(adjustBillId);
    }





}
