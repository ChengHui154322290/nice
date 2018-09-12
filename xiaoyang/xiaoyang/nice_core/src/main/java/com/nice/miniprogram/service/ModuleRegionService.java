package com.nice.miniprogram.service;

import com.nice.miniprogram.model.ModuleRegion;
import com.nice.miniprogram.core.Service;

import java.util.List;

/**
 * Created by CodeGenerator on 2018/06/19.
 */
public interface ModuleRegionService extends Service<ModuleRegion> {
	List<ModuleRegion> findRegion(String parentId);

	List<String> findCname(List<String> lists);

	String getCnNameById(String id);
}
