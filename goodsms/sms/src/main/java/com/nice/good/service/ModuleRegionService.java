package com.nice.good.service;
import com.nice.good.model.ModuleRegion;
import com.nice.good.core.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/05/16.
 */
public interface ModuleRegionService extends Service<ModuleRegion> {

    List<ModuleRegion> findRegion(String parentId);

	List<String> findCname(List <String>lists);
}
