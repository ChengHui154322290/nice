package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.SeatStockMapper;
import com.nice.miniprogram.model.SeatStock;
import com.nice.miniprogram.service.SeatStockService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/10.
 */
@Service
@Transactional
public class SeatStockServiceImpl extends AbstractService<SeatStock> implements SeatStockService {
    @Resource
    private SeatStockMapper seatStockMapper;


    @Override
    public List<SeatStock> getByParams(Map<String,Object> params) {
        return seatStockMapper.getByParams(params);
    }

    @Override
    public SeatStock getOneByParams(Map<String, Object> params) {
        return seatStockMapper.getOneByParams(params);
    }
}
