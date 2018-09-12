package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.ModuleRegionMapper;
import com.nice.miniprogram.model.ModuleRegion;
import com.nice.miniprogram.service.ModuleRegionService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/06/19.
 */
@Service
@Transactional
public class ModuleRegionServiceImpl extends AbstractService<ModuleRegion> implements ModuleRegionService {
	@Resource
	private ModuleRegionMapper moduleRegionMapper;

	@Override
	public String getCnNameById(String id) {
		return moduleRegionMapper.getCnNameById(id);
	}

	@Override
	public List<ModuleRegion> findRegion(String parentId) {
		List<ModuleRegion> list = moduleRegionMapper.findRegion(parentId);
		return list;
	}

	@Override
	public List<String> findCname(List<String> lists) {

		List<String> list = moduleRegionMapper.findCname(lists);
		return list;
	}
}
