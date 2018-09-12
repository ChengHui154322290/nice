package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.ExhibitionRoom;

import java.util.List;

public interface ExhibitionRoomMapper extends CoreMapper<ExhibitionRoom> {

    List<ExhibitionRoom> selectByPlaceNumber(String placeNumber);

    String queryPlaceNumber(Integer exhibitionRoomId);

}