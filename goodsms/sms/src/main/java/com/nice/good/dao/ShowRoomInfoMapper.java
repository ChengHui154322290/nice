package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.wx_model.ShowRoomInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ShowRoomInfoMapper extends Mapper<ShowRoomInfo> {
	ShowRoomInfo findByCode(String showRoomCode);

	List<String> findAllCode();

	List<ShowRoomInfo> findByConditions(Map<String, Object> map);

	ShowRoomInfo findByCodeNum(@Param(value = "code") String code, @Param(value="placeNumber")String placeNumber);

	List<String> selectRoomStyles();
}