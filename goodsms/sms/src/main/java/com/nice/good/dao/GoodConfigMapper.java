package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.GoodConfig;
import org.apache.ibatis.annotations.Param;

public interface GoodConfigMapper extends Mapper<GoodConfig> {

    void deleteByGoodId(String goodId);

    GoodConfig selectGoodConfigByGoodId(@Param(value = "gooderCode") String gooderCode,
                                        @Param(value = "goodCode") String goodCode,
                                        @Param(value = "placeId") String placeId);

    GoodConfig selectConfigByGoodId(@Param(value = "goodId") String goodId,
                                    @Param(value = "placeId") String placeId);

    String selectOutSeat(@Param(value = "gooderCode") String gooderCode,
                         @Param(value = "goodCode") String goodCode);


}