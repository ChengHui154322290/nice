package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.GoodConfigMapper;
import com.nice.good.model.GoodConfig;
import com.nice.good.service.GoodConfigService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
* @Description:   货品档案-配置
* @Author:   fqs
* @Date:  2018/3/23 10:38
* @Version:   1.0
*/
@Service
@Transactional
public class GoodConfigServiceImpl extends AbstractService<GoodConfig> implements GoodConfigService {
    @Resource
    private GoodConfigMapper goodConfigMapper;

    @Override
    public  void goodConfigAdd(GoodConfig goodConfig,String userId) throws Exception {

        goodConfig.setConfigId(IdsUtils.getOrderId());
        goodConfig.setCreater(userId);
        goodConfig.setModifier(userId);
        goodConfig.setCreatetime(TimeStampUtils.getTimeStamp());
        goodConfig.setModifytime(TimeStampUtils.getTimeStamp());

        goodConfigMapper.insert(goodConfig);

    }


   @Override
   public void  goodConfigUpdate(GoodConfig goodConfig,String userId){

        goodConfig.setModifier(userId);
        goodConfig.setModifytime(TimeStampUtils.getTimeStamp());
        goodConfigMapper.updateByPrimaryKey(goodConfig);
   }

}
