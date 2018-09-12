package com.nice.good.service;
import com.nice.good.model.Provider;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/23.
 */
public interface ProviderService extends Service<Provider> {

      String providerAdd(Provider provider,String userId) throws Exception;
      String  deleteByProviderId(List<String> providerIds);

      /**
       * 查询 e_provider表 中的 provider_code
       * @return
       */
      List<String> findProviderCodes();

}
