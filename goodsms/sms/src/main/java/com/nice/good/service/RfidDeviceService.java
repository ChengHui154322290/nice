package com.nice.good.service;
import com.nice.good.model.RfidDevice;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/06/05.
 */
public interface RfidDeviceService extends Service<RfidDevice> {
      void rfidDeviceAdd(RfidDevice rfidDevice,String userId);
      void rfidDeviceUpdate(RfidDevice rfidDevice,String userId);
}
