package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.Stock;

import java.util.Map;

public interface StockMapper extends CoreMapper<Stock> {
    Stock getOneByParams(Map<String,Object> params);
}