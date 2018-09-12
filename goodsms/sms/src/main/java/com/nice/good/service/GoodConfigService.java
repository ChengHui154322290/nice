package com.nice.good.service;
import com.nice.good.model.GoodConfig;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/03/23.
 */
public interface GoodConfigService extends Service<GoodConfig> {
      void goodConfigAdd(GoodConfig goodConfig,String userId) throws Exception;
      void goodConfigUpdate(GoodConfig goodConfig,String userId);
}
