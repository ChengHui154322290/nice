package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.wx_model.RoomPicture;

import java.util.List;

public interface RoomPictureMapper extends Mapper<RoomPicture> {
	List<String> selectImgsByRoomId(Integer roomId);

	void deletePicture(String imgUrl);
}