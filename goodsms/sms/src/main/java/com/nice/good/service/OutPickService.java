package com.nice.good.service;
import com.nice.good.model.OutPick;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/04/11.
 */
public interface OutPickService extends Service<OutPick> {
      void outPickAdd(OutPick outPick,String userId);
      void outPickUpdate(OutPick outPick,String userId);
}
