package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.ModuleRegionMapper;
import com.nice.good.model.ModuleRegion;
import com.nice.good.service.ModuleRegionService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/05/16.
 */
@Service
@Transactional
public class ModuleRegionServiceImpl extends AbstractService<ModuleRegion> implements ModuleRegionService {
    @Resource
    private ModuleRegionMapper moduleRegionMapper;

    @Override
    public List<ModuleRegion> findRegion(String parentId) {
        List<ModuleRegion> list = moduleRegionMapper.findRegion(parentId);
        return list;
    }

    @Override
    public List<String> findCname(List<String> lists) {

        List<String > list = moduleRegionMapper.findCname(lists);
        return list;
    }
}


