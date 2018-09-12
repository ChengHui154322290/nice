package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.SeatStock;

import java.util.List;
import java.util.Map;

public interface SeatStockMapper extends CoreMapper<SeatStock> {
    List<SeatStock> getByParams(Map<String,Object> params);
    SeatStock getOneByParams(Map<String, Object> params);
}