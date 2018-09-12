package com.nice.good.service;
import com.nice.good.model.DynamicGood;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/05/14.
 */
public interface DynamicGoodService extends Service<DynamicGood> {
      void dynamicGoodAdd(DynamicGood dynamicGood,String userId);
      void dynamicGoodUpdate(DynamicGood dynamicGood,String userId);

    /**
     * 通过最大的 id 值查询 dynamicgood 动态货品所有信息
     * @return
     */
    DynamicGood findDynamicGoodByMaxId();
}
