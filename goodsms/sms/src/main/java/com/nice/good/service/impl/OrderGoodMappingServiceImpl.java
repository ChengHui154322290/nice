package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.core.Result;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.OrderGoodMappingMapper;
import com.nice.good.model.OrderGoodMapping;
import com.nice.good.service.OrderGoodMappingService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/03/26.
 */
@Service
@Transactional
public class OrderGoodMappingServiceImpl extends AbstractService<OrderGoodMapping> implements OrderGoodMappingService {
    @Resource
    private OrderGoodMappingMapper orderGoodMappingMapper;

	@Override
	public void orderGoodMappingAdd(OrderGoodMapping orderGoodMapping, String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void orderGoodMappingUpdate(OrderGoodMapping orderGoodMapping, String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> findOrderGoodMappingByGoodCode(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}


}
