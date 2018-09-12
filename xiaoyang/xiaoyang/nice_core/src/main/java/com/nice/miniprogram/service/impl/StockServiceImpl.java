package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.StockMapper;
import com.nice.miniprogram.model.Stock;
import com.nice.miniprogram.service.StockService;
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
public class StockServiceImpl extends AbstractService<Stock> implements StockService {
    @Resource
    private StockMapper stockMapper;


    @Override
    public Stock getOneByParams(Map<String, Object> params) {
        return stockMapper.getOneByParams(params);
    }
}
