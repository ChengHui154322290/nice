package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.ModuleRegion;

import java.util.List;

public interface ModuleRegionMapper extends Mapper<ModuleRegion> {

    List<ModuleRegion> findRegion(String parentId);

	List<String> findCname(List<String> lists);

	List<String> selectArea(String cnName);

	 List<String> findById(List<String> listr);
}