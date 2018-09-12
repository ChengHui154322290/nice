package com.nice.good.service;
import com.nice.good.model.ReceiveOrder;
import com.nice.good.model.ReceiveOrderTemp;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/04/05.
 */
public interface ReceiveOrderTempService extends Service<ReceiveOrderTemp> {
    String receiveOrderTempSave(ReceiveOrder receiveOrder, String userId);

}
