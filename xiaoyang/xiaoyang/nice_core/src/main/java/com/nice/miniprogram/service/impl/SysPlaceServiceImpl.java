package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.SysPlaceMapper;
import com.nice.miniprogram.model.SysPlace;
import com.nice.miniprogram.service.SysPlaceService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/18.
 */
@Service
@Transactional
public class SysPlaceServiceImpl extends AbstractService<SysPlace> implements SysPlaceService {
    @Resource
    private SysPlaceMapper sysPlaceMapper;


    @Override
    public SysPlace getByPlaceNumber(String placeNumber) {
        return sysPlaceMapper.getByPlaceNumber(placeNumber);
    }

    @Override
    public List<SysPlace> getList() {
        return sysPlaceMapper.getList();
    }
}
