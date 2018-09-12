package com.nice.good.service;
import com.nice.good.model.CloudThermal;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/04.
 */
public interface CloudThermalService extends Service<CloudThermal> {
      void cloudThermalAdd(CloudThermal cloudThermal,String userId) throws Exception;
      void cloudThermalUpdate(CloudThermal cloudThermal,String userId);

      void deleteByCloudId(List<String> cloudId);
}
