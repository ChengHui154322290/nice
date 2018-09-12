package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.GoodCountMapper;
import com.nice.good.service.GoodCountService;
import com.nice.good.core.AbstractService;
import com.nice.good.wx_model.GoodCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/08/06.
 */
@Service
@Transactional
public class GoodCountServiceImpl extends AbstractService<GoodCount> implements GoodCountService {
    @Resource
    private GoodCountMapper goodCountMapper;

    @Override
    public  void goodCountAdd(GoodCount goodCount,String userId){



        goodCountMapper.insert(goodCount);

    }


   @Override
   public void  goodCountUpdate(GoodCount goodCount,String userId){
        goodCountMapper.updateByPrimaryKey(goodCount);
   }

    @Override
    public GoodCount getGoodCount(String goodId) {
        return goodCountMapper.getGoodCount(goodId);
    }

    @Override
    public void newCountRecord(GoodCount goodCount) {
        goodCountMapper.insert(goodCount);
    }

    @Override
    public void addCollarNum(String goodId) {
        goodCountMapper.addCollarNum(goodId);
    }

}
