package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.GoodPicture;

import java.util.List;
import java.util.Map;

public interface GoodPictureMapper extends CoreMapper<GoodPicture> {
    List<GoodPicture> getByGoodId(String goodId) ;
}