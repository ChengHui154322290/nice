package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.GoodCategoryMapper;
import com.nice.good.model.GoodCategory;
import com.nice.good.service.GoodCategoryService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/12.
 */
@Service
@Transactional
public class GoodCategoryServiceImpl extends AbstractService<GoodCategory> implements GoodCategoryService {
    @Resource
    private GoodCategoryMapper goodCategoryMapper;

    @Override
    public  void goodCategoryAdd(GoodCategory goodCategory,String userId){


        goodCategory.setCreater(userId);
        goodCategory.setModifier(userId);
        goodCategory.setCreatetime(TimeStampUtils.getTimeStamp());
        goodCategory.setModifytime(TimeStampUtils.getTimeStamp());

        goodCategoryMapper.insert(goodCategory);

    }


   @Override
   public void  goodCategoryUpdate(GoodCategory goodCategory,String userId){

        goodCategory.setModifier(userId);
        goodCategory.setCreatetime(TimeStampUtils.getTimeStamp());
        goodCategoryMapper.updateByPrimaryKey(goodCategory);
   }

}
