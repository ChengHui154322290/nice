package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.ShelfGoodMapper;
import com.nice.good.model.ShelfGood;
import com.nice.good.service.ShelfGoodService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/04/09.
 */
@Service
@Transactional
public class ShelfGoodServiceImpl extends AbstractService<ShelfGood> implements ShelfGoodService {
    @Resource
    private ShelfGoodMapper shelfGoodMapper;

    @Override
    public  void shelfGoodAdd(ShelfGood shelfGood,String userId){


        shelfGood.setCreateId(userId);
        shelfGood.setModifyId(userId);
        shelfGood.setCreateDate(TimeStampUtils.getTimeStamp());
        shelfGood.setModifyDate(TimeStampUtils.getTimeStamp());

        shelfGoodMapper.insert(shelfGood);

    }


   @Override
   public void  shelfGoodUpdate(ShelfGood shelfGood,String userId){

        shelfGood.setModifyId(userId);
        shelfGood.setModifyDate(TimeStampUtils.getTimeStamp());
        shelfGoodMapper.updateByPrimaryKey(shelfGood);
   }

}
