package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.StoreSeat;
import com.nice.good.vo.PlaceSeatVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StoreSeatMapper extends Mapper<StoreSeat> {

    String findIdBySeatCode(@Param(value = "seatCode") String seatCode,
                            @Param(value = "placeId") String placeId);

    Integer selectCapacityBySeatCode(@Param(value = "seatCode") String seatCode,
                                     @Param(value = "placeId") String placeId);

    List<PlaceSeatVo>  findByConditions(Map seatMap);

    void updateSeatCapacity(@Param(value = "seatCode") String seatCode,
                            @Param(value = "capacity") Integer capacity,
                            @Param(value = "placeId") String placeId);

    List<String> selectByAreaCode(@Param(value = "areaCode") String areaCode,
                                  @Param(value = "placeId") String placeId);

    String findSeatCode(String seatId);

    List<String> findAllSeatCode(String placeId);

    List<StoreSeat> findSeat(@Param(value = "areaCode") String areaCode,
                             @Param(value = "placeId") String placeId);

    String selectSeatTagBySeatCode(@Param(value = "seatCode") String seatCode,
                                   @Param(value = "placeId") String placeId);

    String selectSeatTypeBySeatCode(@Param(value = "seatCode") String seatCode,
                                    @Param(value = "placeId") String placeId);

//    void batchInsertList(List<StoreSeat> storeSeatList);

	List<String> selectSeatCode(@Param(value = "placeId") String placeId);

	String findSeatType(@Param(value = "seatCode") String seatCode,@Param(value = "placeId")String placeId);

	Integer selectCountByAreaAndPlace(@Param(value = "areaCode") String areaCode,
                                      @Param(value = "placeId") String placeId);

	List<String> findWxSeatCode(String placeId);

	List<StoreSeat> findByConditionWx(Map<String, Object> seatMap);
}