package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.DynamicGoodMapper;
import com.nice.good.model.DynamicGood;
import com.nice.good.service.DynamicGoodService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/05/14.
 */
@Service
@Transactional
public class DynamicGoodServiceImpl extends AbstractService<DynamicGood> implements DynamicGoodService {
    @Resource
    private DynamicGoodMapper dynamicGoodMapper;

    @Override
    public  void dynamicGoodAdd(DynamicGood dynamicGood,String userId){


        dynamicGood.setCreateId(userId);
        dynamicGood.setModifyId(userId);
        dynamicGood.setCreateDate(TimeStampUtils.getTimeStamp());
        dynamicGood.setModifyDate(TimeStampUtils.getTimeStamp());

        dynamicGoodMapper.insert(dynamicGood);

    }


   @Override
   public void  dynamicGoodUpdate(DynamicGood dynamicGood,String userId){

        dynamicGood.setModifyId(userId);
        dynamicGood.setModifyDate(TimeStampUtils.getTimeStamp());
        dynamicGoodMapper.updateByPrimaryKey(dynamicGood);
   }

    @Override
    public DynamicGood findDynamicGoodByMaxId() {
        return dynamicGoodMapper.findDynamicGoodByMaxId();
    }

}
