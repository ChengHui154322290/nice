package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.RfidDeviceMapper;
import com.nice.good.model.RfidDevice;
import com.nice.good.service.RfidDeviceService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/06/05.
 */
@Service
@Transactional
public class RfidDeviceServiceImpl extends AbstractService<RfidDevice> implements RfidDeviceService {
    @Resource
    private RfidDeviceMapper rfidDeviceMapper;

    @Override
    public  void rfidDeviceAdd(RfidDevice rfidDevice,String userId){


        rfidDevice.setCreateId(userId);
        rfidDevice.setModifyId(userId);
        rfidDevice.setCreateDate(TimeStampUtils.getTimeStamp());
        rfidDevice.setModifyDate(TimeStampUtils.getTimeStamp());

        rfidDeviceMapper.insert(rfidDevice);
    }


   @Override
   public void  rfidDeviceUpdate(RfidDevice rfidDevice,String userId){

        rfidDevice.setModifyId(userId);
        rfidDevice.setModifyDate(TimeStampUtils.getTimeStamp());
        rfidDeviceMapper.updateByPrimaryKey(rfidDevice);
   }

}
