package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.ShowRoomInfo;

import java.util.List;
import java.util.Map;

public interface ShowRoomInfoMapper extends CoreMapper<ShowRoomInfo> {

    List<ShowRoomInfo> getListByParams(Map<String, Object> params);

    String queryPlaceNumber(Integer showRoomId);
}