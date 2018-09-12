package com.nice.good.service;
import com.nice.good.model.RfidReadRecord;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/06/06.
 */
public interface RfidReadRecordService extends Service<RfidReadRecord> {
      void rfidReadRecordAdd(RfidReadRecord rfidReadRecord,String userId);
      void rfidReadRecordUpdate(RfidReadRecord rfidReadRecord,String userId);
}
