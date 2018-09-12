package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.dao.BespeakOrderMapper;
import com.nice.miniprogram.model.BespeakOrder;
import com.nice.miniprogram.service.BespeakOrderService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/06/13.
 */
@Service
@Transactional
public class BespeakOrderServiceImpl extends AbstractService<BespeakOrder> implements BespeakOrderService {
    @Resource
    private BespeakOrderMapper bespeakOrderMapper;

    @Override
    public BespeakOrder getById(int id) {
        return bespeakOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BespeakOrder> selectListByParams(Map<String, Object> map) {
        return bespeakOrderMapper.selectListByParams(map);
    }

    @Override
    public Integer selectMaxId() {
        return bespeakOrderMapper.selectMaxId();
    }

    @Override
    public List<BespeakOrder> getListByStartTime(Map<String,Object> params) {
        return bespeakOrderMapper.getListByStartTime(params);
    }
}
