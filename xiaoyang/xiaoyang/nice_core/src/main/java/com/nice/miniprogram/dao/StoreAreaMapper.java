package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.StoreArea;

import java.util.List;
import java.util.Map;

public interface StoreAreaMapper extends CoreMapper<StoreArea> {

    List<StoreArea> selectByParams(Map<String, Object> params);

}