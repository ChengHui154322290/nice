package com.nice.good.service;
import com.nice.good.model.OutReceiver;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/04/11.
 */
public interface OutReceiverService extends Service<OutReceiver> {
      void outReceiverAdd(OutReceiver outReceiver,String userId);
      void outReceiverUpdate(OutReceiver outReceiver,String userId);
}
