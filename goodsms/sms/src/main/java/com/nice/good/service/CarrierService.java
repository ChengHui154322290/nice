package com.nice.good.service;
import com.nice.good.model.Carrier;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/23.
 */
public interface CarrierService extends Service<Carrier> {
      String carrierAdd(Carrier carrier,String userId) throws Exception;

      String deleteByCarrierId(List<String> carrierIds);

      /**
       * 查询 c_carrier 表 中的 carrier_code
       * @return
       */
      List<String> findCarrierCodes();
}
