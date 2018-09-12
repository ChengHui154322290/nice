package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.StoreSeat;

import java.util.List;
import java.util.Map;

public interface StoreSeatMapper extends CoreMapper<StoreSeat> {
    String queryPlaceNumber(String seatId);

    List<StoreSeat> selectByParams(Map<String, Object> params);

    StoreSeat findByCode(Map<String,Object> params);
}