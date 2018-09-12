package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.GoodConfig;
import com.nice.good.model.Gooder;
import com.nice.good.model.GooderConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GooderConfigMapper extends Mapper<GooderConfig> {
    void  deleteByGooderId(String gooderId);


    GooderConfig selectConfigByGooderIdAndPlaceId(@Param(value = "gooderId") String gooderId,
                                                  @Param(value = "placeId") String placeId);

    GooderConfig selectByGooderCodeAndPlaceId(@Param(value = "gooderCode") String gooderCode,
                                              @Param(value = "placeId") String placeId);
}