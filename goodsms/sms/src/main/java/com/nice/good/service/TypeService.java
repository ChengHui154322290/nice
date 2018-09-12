package com.nice.good.service;
import com.nice.good.model.Type;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/07/23.
 */
public interface TypeService extends Service<Type> {
      String typeAdd(Type type,String userId) throws Exception;
      String typeUpdate(Type type,String userId);
}
