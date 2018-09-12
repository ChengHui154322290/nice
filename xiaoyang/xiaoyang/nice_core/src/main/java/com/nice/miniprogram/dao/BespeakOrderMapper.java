package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.BespeakOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BespeakOrderMapper extends CoreMapper<BespeakOrder> {
    List<BespeakOrder> selectListByParams(Map<String,Object> params);
    Integer selectMaxId();

    List<BespeakOrder> getListByStartTime(Map<String,Object> params);
}