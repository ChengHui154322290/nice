package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.GoodArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodAreaMapper extends Mapper<GoodArea> {

    void deleteByGoodId(@Param(value = "goodId") String goodId,
                        @Param(value = "placeId") String placeId);


    List<GoodArea> selectAreaByGoodIdAndPlaceId(@Param(value = "goodId") String goodId,
                                                @Param(value = "placeId") String placeId);


    String selectAreaCodeByGoodId(@Param(value = "gooderCode") String gooderCode,
                                  @Param(value = "goodCode") String goodCode,
                                  @Param(value = "fistArea") Integer fistArea,
                                  @Param(value = "placeId") String placeId);


    List<String> selectAreaCodeByGoodIdAndPlaceId(@Param(value = "gooderCode") String gooderCode,
                                                  @Param(value = "goodCode") String goodCode,
                                                  @Param(value = "placeId") String placeId);



    void deleteAreaById(@Param(value = "areaId") String areaId,
                        @Param(value = "placeId") String placeId);

    List<GoodArea> selectAllAreaCodeByGoodId(@Param(value = "goodId") String goodId,
                                             @Param(value = "placeId") String placeId);
}