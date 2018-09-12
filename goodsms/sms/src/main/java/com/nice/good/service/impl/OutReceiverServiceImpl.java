package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.OutReceiverMapper;
import com.nice.good.model.OutReceiver;
import com.nice.good.service.OutReceiverService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/04/11.
 */
@Service
@Transactional
public class OutReceiverServiceImpl extends AbstractService<OutReceiver> implements OutReceiverService {
    @Resource
    private OutReceiverMapper outReceiverMapper;

    @Override
    public  void outReceiverAdd(OutReceiver outReceiver,String userId){


        outReceiver.setCreateId(userId);
        outReceiver.setModifyId(userId);
        outReceiver.setCreateDate(TimeStampUtils.getTimeStamp());
        outReceiver.setModifyDate(TimeStampUtils.getTimeStamp());

        outReceiverMapper.insert(outReceiver);

    }


   @Override
   public void  outReceiverUpdate(OutReceiver outReceiver,String userId){

        outReceiver.setModifyId(userId);
        outReceiver.setModifyDate(TimeStampUtils.getTimeStamp());
        outReceiverMapper.updateByPrimaryKey(outReceiver);
   }

}
