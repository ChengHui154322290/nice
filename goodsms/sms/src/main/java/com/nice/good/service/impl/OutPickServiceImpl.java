package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.OutPickMapper;
import com.nice.good.model.OutPick;
import com.nice.good.service.OutPickService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/04/11.
 */
@Service
@Transactional
public class OutPickServiceImpl extends AbstractService<OutPick> implements OutPickService {
    @Resource
    private OutPickMapper outPickMapper;

    @Override
    public  void outPickAdd(OutPick outPick,String userId){


        outPick.setCreateId(userId);
        outPick.setModifyId(userId);
        outPick.setCreateDate(TimeStampUtils.getTimeStamp());
        outPick.setModifyDate(TimeStampUtils.getTimeStamp());

        outPickMapper.insert(outPick);

    }


   @Override
   public void  outPickUpdate(OutPick outPick,String userId){

        outPick.setModifyId(userId);
        outPick.setModifyDate(TimeStampUtils.getTimeStamp());
        outPickMapper.updateByPrimaryKey(outPick);
   }

}
