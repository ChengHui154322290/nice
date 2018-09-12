package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.OutSenderMapper;
import com.nice.good.model.OutSender;
import com.nice.good.service.OutSenderService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/04/11.
 */
@Service
@Transactional
public class OutSenderServiceImpl extends AbstractService<OutSender> implements OutSenderService {
    @Resource
    private OutSenderMapper outSenderMapper;

    @Override
    public  void outSenderAdd(OutSender outSender,String userId){


        outSender.setCreateId(userId);
        outSender.setModifyId(userId);
        outSender.setCreateDate(TimeStampUtils.getTimeStamp());
        outSender.setModifyDate(TimeStampUtils.getTimeStamp());

        outSenderMapper.insert(outSender);

    }


   @Override
   public void  outSenderUpdate(OutSender outSender,String userId){

        outSender.setModifyId(userId);
        outSender.setModifyDate(TimeStampUtils.getTimeStamp());
        outSenderMapper.updateByPrimaryKey(outSender);
   }

}
