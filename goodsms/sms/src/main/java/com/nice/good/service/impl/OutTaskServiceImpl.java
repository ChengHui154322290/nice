package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.OutTaskMapper;
import com.nice.good.model.OutTask;
import com.nice.good.service.OutTaskService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/06/27.
 */
@Service
@Transactional
public class OutTaskServiceImpl extends AbstractService<OutTask> implements OutTaskService {
    @Resource
    private OutTaskMapper outTaskMapper;

    @Override
    public  void outTaskAdd(OutTask outTask,String userId){



        outTaskMapper.insert(outTask);

    }


   @Override
   public void  outTaskUpdate(OutTask outTask,String userId){


        outTaskMapper.updateByPrimaryKey(outTask);
   }

}
