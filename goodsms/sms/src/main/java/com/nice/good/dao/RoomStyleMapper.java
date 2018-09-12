package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.wx_model.RoomStyle;

import java.util.List;

public interface RoomStyleMapper extends Mapper<RoomStyle> {
	List<String> selectRoomStyles();

	String findStyle(String name);
}