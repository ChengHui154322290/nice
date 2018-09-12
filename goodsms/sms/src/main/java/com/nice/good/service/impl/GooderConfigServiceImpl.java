package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.GooderConfigMapper;
import com.nice.good.model.GooderConfig;
import com.nice.good.service.GooderConfigService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/04/16.
 */
@Service
@Transactional
public class GooderConfigServiceImpl extends AbstractService<GooderConfig> implements GooderConfigService {
    @Resource
    private GooderConfigMapper gooderConfigMapper;

    @Override
    public  void gooderConfigAdd(GooderConfig gooderConfig,String userId){


        gooderConfig.setCreater(userId);
        gooderConfig.setModifier(userId);
        gooderConfig.setCreatetime(TimeStampUtils.getTimeStamp());
        gooderConfig.setModifytime(TimeStampUtils.getTimeStamp());

        gooderConfigMapper.insert(gooderConfig);

    }


   @Override
   public void  gooderConfigUpdate(GooderConfig gooderConfig,String userId){
       gooderConfig.setModifier(userId);
        gooderConfig.setModifytime(TimeStampUtils.getTimeStamp());
        gooderConfigMapper.updateByPrimaryKey(gooderConfig);
   }

}
