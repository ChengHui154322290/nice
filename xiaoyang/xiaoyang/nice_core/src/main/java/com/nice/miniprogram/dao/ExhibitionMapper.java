package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.Exhibition;

import java.util.List;
import java.util.Map;

public interface ExhibitionMapper extends CoreMapper<Exhibition> {

    List<Exhibition> getListByParams(Map<String, Object> params);

}