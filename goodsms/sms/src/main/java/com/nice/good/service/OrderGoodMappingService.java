package com.nice.good.service;
import com.nice.good.core.Result;
import com.nice.good.model.OrderGoodMapping;

import java.util.List;
import java.util.Map;

import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/03/26.
 */
public interface OrderGoodMappingService extends Service<OrderGoodMapping> {
      void orderGoodMappingAdd(OrderGoodMapping orderGoodMapping,String userId);
      void orderGoodMappingUpdate(OrderGoodMapping orderGoodMapping,String userId);
      
      
    //通过商品编码查询订单明细
	List<String> findOrderGoodMappingByGoodCode(Map<String, String> map);


}
