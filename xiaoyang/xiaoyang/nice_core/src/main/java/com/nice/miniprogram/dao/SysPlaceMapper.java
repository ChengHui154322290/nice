package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.SysPlace;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysPlaceMapper extends CoreMapper<SysPlace> {

    List<SysPlace> getList();

    SysPlace getByPlaceNumber(String placeNumber);
}