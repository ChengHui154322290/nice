package com.nice.good.service;
import com.nice.good.model.OutTask;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/06/27.
 */
public interface OutTaskService extends Service<OutTask> {
      void outTaskAdd(OutTask outTask,String userId);
      void outTaskUpdate(OutTask outTask,String userId);
}
