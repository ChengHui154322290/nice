package com.nice.good.service;

import com.nice.good.core.Service;
import com.nice.good.model.ReceiveDetail;

import java.util.List;

public interface DynamicGoodAdviceService {

	List<ReceiveDetail> selectReceiveDetailByReceiveId(String receiveId) throws Exception;

	
}
