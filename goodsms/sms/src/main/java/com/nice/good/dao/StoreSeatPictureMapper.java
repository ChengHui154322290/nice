package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.StoreSeatPicture;

import java.util.List;

public interface StoreSeatPictureMapper extends Mapper<StoreSeatPicture> {

    void deletePicture(String imgUrl);

    void deleteBySeatId(String seatId);

    List<String> selectSeatImgsBySeatId(String seatId);
}