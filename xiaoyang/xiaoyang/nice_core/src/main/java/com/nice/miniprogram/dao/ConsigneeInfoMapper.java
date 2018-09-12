package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.ConsigneeInfo;

import java.util.List;

public interface ConsigneeInfoMapper extends CoreMapper<ConsigneeInfo> {
	List<ConsigneeInfo> findByOwbId(int ownId);
}