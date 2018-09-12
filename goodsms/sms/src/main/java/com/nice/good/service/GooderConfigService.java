package com.nice.good.service;
import com.nice.good.model.GooderConfig;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/04/16.
 */
public interface GooderConfigService extends Service<GooderConfig> {
      void gooderConfigAdd(GooderConfig gooderConfig,String userId);
      void gooderConfigUpdate(GooderConfig gooderConfig,String userId);
}
