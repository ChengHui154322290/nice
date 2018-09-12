package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.ShowCountMapper;
import com.nice.miniprogram.model.ShowCount;
import com.nice.miniprogram.service.ShowCountService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/21.
 */
@Service
@Transactional
public class ShowCountServiceImpl extends AbstractService<ShowCount> implements ShowCountService {
    @Resource
    private ShowCountMapper showCountMapper;


    @Override
    public ShowCount getByShowRoomId(Integer showRoomId) {
        return showCountMapper.getByShowRoomId(showRoomId);
    }

    @Override
    public ShowCount getByExhibitionRoomId(Integer exhibitionRoomId) {
        return showCountMapper.getByExhibitionRoomId(exhibitionRoomId);
    }

    @Override
    public void newCountRecord(ShowCount showCount) {
        showCountMapper.insert(showCount);
    }

    @Override
    public void addClickNum(Integer id) {
        showCountMapper.addClickNum(id);
    }

    @Override
    public void addBespeakNum(Integer id) {
        showCountMapper.addBespeakNum(id);
    }
}
