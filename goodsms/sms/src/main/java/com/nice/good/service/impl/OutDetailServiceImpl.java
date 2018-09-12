package com.nice.good.service.impl;


import com.nice.good.dao.*;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.model.OutDetail;
import com.nice.good.service.OutDetailService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/04/11.
 */
@Service
@Transactional
public class OutDetailServiceImpl extends AbstractService<OutDetail> implements OutDetailService {
    @Resource
    private OutDetailMapper outDetailMapper;

    

    @Override
    public void outDetailAdd(OutDetail outDetail, String userId) {

//
//        outDetail.setCreateId(userId);
//        outDetail.setModifyId(userId);
//        outDetail.setCreateDate(TimeStampUtils.getTimeStamp());
//        outDetail.setModifyDate(TimeStampUtils.getTimeStamp());
//
//        outDetailMapper.insert(outDetail);

    }


    @Override
    public void outDetailUpdate(OutDetail outDetail, String userId) {

        outDetail.setModifyId(userId);
        outDetail.setModifyDate(TimeStampUtils.getTimeStamp());
        outDetailMapper.updateByPrimaryKey(outDetail);
    }

}
