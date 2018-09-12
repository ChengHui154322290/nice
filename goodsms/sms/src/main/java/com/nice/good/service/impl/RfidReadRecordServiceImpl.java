package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.RfidReadRecordMapper;
import com.nice.good.model.RfidReadRecord;
import com.nice.good.service.RfidReadRecordService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/06/06.
 */
@Service
@Transactional
public class RfidReadRecordServiceImpl extends AbstractService<RfidReadRecord> implements RfidReadRecordService {
    @Resource
    private RfidReadRecordMapper rfidReadRecordMapper;

    @Override
    public  void rfidReadRecordAdd(RfidReadRecord rfidReadRecord,String userId){


        rfidReadRecord.setCreateId(userId);
        rfidReadRecord.setCreateDate(TimeStampUtils.getTimeStamp());

        rfidReadRecordMapper.insert(rfidReadRecord);

    }


   @Override
   public void  rfidReadRecordUpdate(RfidReadRecord rfidReadRecord,String userId){

        rfidReadRecordMapper.updateByPrimaryKey(rfidReadRecord);
   }

}
