package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.ModuleRegion;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ModuleRegionMapper extends CoreMapper<ModuleRegion> {

	List<ModuleRegion> findRegion(String parentId);

	List<String> findCname(List<String> lists);


	List<String> findById(List<String> listr);

	@Select("select cn_name from z_module_region where id=#{id}")
	String getCnNameById(String id);
}