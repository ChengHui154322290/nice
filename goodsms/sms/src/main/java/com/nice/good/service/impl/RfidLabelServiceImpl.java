package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.RfidLabelMapper;
import com.nice.good.model.RfidLabel;
import com.nice.good.service.RfidLabelService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/05/17.
 */
@Service
@Transactional
public class RfidLabelServiceImpl extends AbstractService<RfidLabel> implements RfidLabelService {
    @Resource
    private RfidLabelMapper rfidLabelMapper;

    @Override
    public  void rfidLabelAdd(RfidLabel rfidLabel,String userId){


        rfidLabel.setCreateId(userId);
        rfidLabel.setModifyId(userId);
        rfidLabel.setCreateDate(TimeStampUtils.getTimeStamp());
        rfidLabel.setModifyDate(TimeStampUtils.getTimeStamp());

        rfidLabelMapper.insert(rfidLabel);

    }


   @Override
   public void  rfidLabelUpdate(RfidLabel rfidLabel,String userId){

        rfidLabel.setModifyId(userId);
        rfidLabel.setModifyDate(TimeStampUtils.getTimeStamp());
        rfidLabelMapper.updateByPrimaryKey(rfidLabel);
   }

}
