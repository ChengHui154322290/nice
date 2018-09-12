package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.StoreArea;
import com.nice.good.vo.AreaVo;
import com.nice.good.vo.PlaceAreaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StoreAreaMapper extends Mapper<StoreArea> {

    String findIdByAreaCode(@Param(value = "areaCode") String areaCode,
                            @Param(value = "placeId") String placeId);




    List<PlaceAreaVo> findByConditions(@Param(value = "placeNumber") String placeNumber,
                                       @Param(value = "areaType") String areaType,
                                       @Param(value = "areaCode")  String areaCode,
                                       @Param(value = "areaName")  String areaName,
                                       @Param(value = "placeId")  String placeId);

    List<String> selectAreaCodeByPlaceId(String placeId);

    List<AreaVo> chooseArea(Map<String,String> conditionMap);

    List<String> findAllSeatCode(@Param(value = "areaId") String  areaId,
                                 @Param(value = "placeId")String placeId);

    List<String> findSeatCode(String placeId);

    String  selectAreaNameByCode(@Param(value = "areaCode") String areaCode,
                                 @Param(value = "placeId")String placeId);

	List<String> findAllAreaCode();
}