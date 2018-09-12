package com.nice.good.service;
import com.nice.good.model.OperateLog;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/06/11.
 */
public interface OperateLogService extends Service<OperateLog> {
      void operateLogAdd(OperateLog operateLog,String userId);
      void operateLogUpdate(OperateLog operateLog,String userId);
}
