package com.nice.good.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.nice.good.model.ReceiveDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nice.good.dao.ReceiveDetailMapper;
import com.nice.good.service.DynamicGoodAdviceService;

@Service
@Transactional
public class DynamicGoodAdviceServiceImpl implements DynamicGoodAdviceService {
	
	@Resource
	private ReceiveDetailMapper receiveDetailMapper;
	
	@Override
	public List<ReceiveDetail> selectReceiveDetailByReceiveId(String receiveId) throws Exception {
		return receiveDetailMapper.selectReceiveDetailByReceiveId(receiveId);
	}


}
