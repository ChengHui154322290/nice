package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.OperateLogMapper;
import com.nice.good.model.OperateLog;
import com.nice.good.service.OperateLogService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/06/11.
 */
@Service
@Transactional
public class OperateLogServiceImpl extends AbstractService<OperateLog> implements OperateLogService {
    @Resource
    private OperateLogMapper operateLogMapper;

    @Override
    public  void operateLogAdd(OperateLog operateLog,String userId){


//        operateLog.setCreateId(userId);
//        operateLog.setModifyId(userId);
//        operateLog.setCreateDate(TimeStampUtils.getTimeStamp());
//        operateLog.setModifyDate(TimeStampUtils.getTimeStamp());

        operateLogMapper.insert(operateLog);

    }


   @Override
   public void  operateLogUpdate(OperateLog operateLog,String userId){

//        operateLog.setModifyId(userId);
//        operateLog.setModifyDate(TimeStampUtils.getTimeStamp());
        operateLogMapper.updateByPrimaryKey(operateLog);
   }

}
