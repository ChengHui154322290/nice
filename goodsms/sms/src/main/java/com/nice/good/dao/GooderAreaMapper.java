package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.GooderArea;
import com.nice.good.vo.AreaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GooderAreaMapper extends Mapper<GooderArea> {
    void deleteByGooderId(String gooderId);

    List<GooderArea> selectAreaByGooderId(@Param(value = "gooderId") String gooderId,
                                          @Param(value = "placeId") String placeId);


    void deleteAreaById(@Param(value = "areaId") String areaId,
                        @Param(value = "placeId") String placeId);

    List<GooderArea> selectAreaByGooderCode(@Param(value = "gooderCode") String gooderCode,
                                            @Param(value = "placeId") String placeId);


    GooderArea selectByGooderCodeAndPlaceId(@Param(value = "gooderCode") String gooderCode,
                                            @Param(value = "placeId") String placeId,
                                            @Param(value = "firstArea") Integer firstArea);
}