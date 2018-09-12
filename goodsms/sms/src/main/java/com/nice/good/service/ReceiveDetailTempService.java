package com.nice.good.service;
import com.nice.good.model.ReceiveDetailTemp;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/04/05.
 */
public interface ReceiveDetailTempService extends Service<ReceiveDetailTemp> {
      void receiveDetailTempAdd(ReceiveDetailTemp receiveDetailTemp,String userId);
      void receiveDetailTempUpdate(ReceiveDetailTemp receiveDetailTemp,String userId);
}
