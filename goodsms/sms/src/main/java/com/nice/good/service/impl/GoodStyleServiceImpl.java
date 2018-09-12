package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.GoodStyleMapper;
import com.nice.good.model.GoodStyle;
import com.nice.good.service.GoodStyleService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/12.
 */
@Service
@Transactional
public class GoodStyleServiceImpl extends AbstractService<GoodStyle> implements GoodStyleService {
    @Resource
    private GoodStyleMapper goodStyleMapper;

    @Override
    public  void goodStyleAdd(GoodStyle goodStyle,String userId){


        goodStyle.setCreater(userId);
        goodStyle.setModifier(userId);
        goodStyle.setCreatetime(TimeStampUtils.getTimeStamp());
        goodStyle.setModifytime(TimeStampUtils.getTimeStamp());

        goodStyleMapper.insert(goodStyle);

    }


   @Override
   public void  goodStyleUpdate(GoodStyle goodStyle,String userId){

        goodStyle.setModifier(userId);
        goodStyle.setModifytime(TimeStampUtils.getTimeStamp());
        goodStyleMapper.updateByPrimaryKey(goodStyle);
   }

}
