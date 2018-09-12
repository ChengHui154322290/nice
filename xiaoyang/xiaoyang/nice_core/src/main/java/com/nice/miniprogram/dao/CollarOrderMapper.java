package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.CollarOrder;

import java.util.List;
import java.util.Map;

public interface CollarOrderMapper extends CoreMapper<CollarOrder> {

    List<CollarOrder> selectListByParams(Map<String,Object> params);

    Integer selectMaxId();

}