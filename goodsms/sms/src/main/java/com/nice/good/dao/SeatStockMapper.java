package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.SeatStock;
import com.nice.good.vo.SeatStockVo;
import com.nice.good.vo.StockNumVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SeatStockMapper extends Mapper<SeatStock> {


	/**
	 * 更新 库位库存表 中的‘现有量’。 货主编码、货品编码、库位编码，确定一条库位库存信息。   -- 2018/06/19  9:47  rk
	 */
	void updateNowNumAndUseNum(@Param(value = "adjustQuantity") Integer adjustQuantity,
							   @Param(value = "gooderCode") String gooderCode,
							   @Param(value = "goodCode") String goodCode,
							   @Param(value = "seatCode") String seatCode,
							   @Param(value = "orgCode") String orgCode,
							   @Param(value = "providerCode") String providerCode,
							   @Param(value = "placeId") String placeId);

	String selectStockId(@Param(value = "gooderCode") String gooderCode,
						 @Param(value = "goodCode") String goodCode,
						 @Param(value = "seatCode") String seatCode,
						 @Param(value = "orgCode") String orgCode,
						 @Param(value = "providerCode") String providerCode,
						 @Param(value = "placeId") String placeId);


	void updateSeatStockNowNum(@Param(value = "gooderCode") String gooderCode,
							   @Param(value = "goodCode") String goodCode,
							   @Param(value = "seatCode") String seatCode,
							   @Param(value = "receiveNum") Integer receiveNum,
							   @Param(value = "orgCode") String orgCode,
							   @Param(value = "providerCode") String providerCode,
							   @Param(value = "placeId") String placeId);


	void removeSeatStockAllotNum(@Param(value = "gooderCode") String gooderCode,
								 @Param(value = "goodCode") String goodCode,
								 @Param(value = "seatCode") String seatCode,
								 @Param(value = "pickNum") Integer pickNum,
								 @Param(value = "orgCode") String orgCode,
								 @Param(value = "providerCode") String providerCode,
								 @Param(value = "placeId") String placeId);


	List<SeatStock> selectAllGood(@Param(value = "gooderCode") String gooderCode,
								  @Param(value = "goodCode") String goodCode,
								  @Param(value = "orgCode") String orgCode,
								  @Param(value = "providerCode") String providerCode,
								  @Param(value = "placeId") String placeId);


	SeatStock selectBySeatCode(@Param(value = "gooderCode") String gooderCode,
							   @Param(value = "goodCode") String goodCode,
							   @Param(value = "tempSeatCode") String tempSeatCode,
							   @Param(value = "orgCode") String orgCode,
							   @Param(value = "providerCode") String providerCode,
							   @Param(value = "placeId") String placeId);

	void updateSeatStockAllotNum(@Param(value = "gooderCode") String gooderCode,
								 @Param(value = "goodCode") String goodCode,
								 @Param(value = "seatCode") String seatCode,
								 @Param(value = "rfidNum") Integer rfidNum,
								 @Param(value = "orgCode") String orgCode,
								 @Param(value = "providerCode") String providerCode,
								 @Param(value = "placeId") String placeId);

	void updateOutSendNum(@Param(value = "gooderCode") String gooderCode,
						  @Param(value = "goodCode") String goodCode,
						  @Param(value = "seatCode") String seatCode,
						  @Param(value = "pickNum") Integer pickNum,
						  @Param(value = "orgCode") String orgCode,
						  @Param(value = "providerCode") String providerCode,
						  @Param(value = "placeId") String placeId);


	void reduceSeatStockUseNum(@Param(value = "gooderCode") String gooderCode,
							   @Param(value = "goodCode") String goodCode,
							   @Param(value = "seatCode") String seatCode,
							   @Param(value = "allotNum") Integer allotNum,
							   @Param(value = "orgCode") String orgCode,
							   @Param(value = "providerCode") String providerCode,
							   @Param(value = "placeId") String placeId);

	void addGoalSeatStockAllotNum(@Param(value = "gooderCode") String gooderCode,
								  @Param(value = "goodCode") String goodCode,
								  @Param(value = "seatCode") String seatCode,
								  @Param(value = "allotNum") Integer allotNum,
								  @Param(value = "orgCode") String orgCode,
								  @Param(value = "providerCode") String providerCode,
								  @Param(value = "placeId") String placeId);


	void addGoalSeatStockPickNum(@Param(value = "gooderCode") String gooderCode,
								 @Param(value = "goodCode") String goodCode,
								 @Param(value = "seatCode") String seatCode,
								 @Param(value = "rfidNum") Integer rfidNum,
								 @Param(value = "orgCode") String orgCode,
								 @Param(value = "providerCode") String providerCode,
								 @Param(value = "placeId") String placeId);

	void reduceGoalSeatStock(@Param(value = "gooderCode") String gooderCode,
							 @Param(value = "goodCode") String goodCode,
							 @Param(value = "seatCode") String seatCode,
							 @Param(value = "rfidNum") Integer rfidNum,
							 @Param(value = "orgCode") String orgCode,
							 @Param(value = "providerCode") String providerCode,
							 @Param(value = "placeId") String placeId);

	void reduceGoalSeatStockNum(@Param(value = "gooderCode") String gooderCode,
								@Param(value = "goodCode") String goodCode,
								@Param(value = "seatCode") String seatCode,
								@Param(value = "pickNum") Integer pickNum,
								@Param(value = "orgCode") String orgCode,
								@Param(value = "providerCode") String providerCode,
								@Param(value = "placeId") String placeId);


	List<String> selectSeatCodes(@Param(value = "placeId") String placeId);


	void updatePickNum(@Param(value = "gooderCode") String gooderCode,
					   @Param(value = "goodCode") String goodCode,
					   @Param(value = "seatCode") String seatCode,
					   @Param(value = "pickNum") Integer pickNum,
					   @Param(value = "orgCode") String orgCode,
					   @Param(value = "providerCode") String providerCode,
					   @Param(value = "placeId") String placeId);


	Integer selectNowNumByCode(@Param(value = "gooderCode") String gooderCode,
							   @Param(value = "goodCode") String goodCode,
							   @Param(value = "orgCode") String orgCode,
							   @Param(value = "providerCode") String providerCode,
							   @Param(value = "seatCode") String seatCode,
							   @Param(value = "placeId") String placeId);

	Integer selectUseNumByCode(@Param(value = "gooderCode") String gooderCode,
							   @Param(value = "goodCode") String goodCode,
							   @Param(value = "orgCode") String orgCode,
							   @Param(value = "providerCode") String providerCode,
							   @Param(value = "seatCode") String seatCode,
							   @Param(value = "placeId") String placeId);


	Integer selectAllotNumByCode(@Param(value = "gooderCode") String gooderCode,
								 @Param(value = "goodCode") String goodCode,
								 @Param(value = "orgCode") String orgCode,
								 @Param(value = "providerCode") String providerCode,
								 @Param(value = "seatCode") String seatCode,
								 @Param(value = "placeId") String placeId);


	Integer selectFreezeNumByCode(@Param(value = "gooderCode") String gooderCode,
								  @Param(value = "goodCode") String goodCode,
								  @Param(value = "orgCode") String orgCode,
								  @Param(value = "providerCode") String providerCode,
								  @Param(value = "seatCode") String seatCode,
								  @Param(value = "placeId") String placeId);


	Integer selectPickNumByCode(@Param(value = "gooderCode") String gooderCode,
								@Param(value = "goodCode") String goodCode,
								@Param(value = "orgCode") String orgCode,
								@Param(value = "providerCode") String providerCode,
								@Param(value = "seatCode") String seatCode,
								@Param(value = "placeId") String placeId);


	StockNumVo countSeatNum(Map<String, Object> conditionMap);


	List<SeatStock> getByParams(Map<String, Object> params);

	SeatStock  findByStockId(String stockId);

	SeatStock findBySeatCode(@Param(value ="seatCode" ) String seatCode,@Param(value ="goodCode" )String goodCode);

	List<SeatStockVo> findByBar(SeatStockVo seatStockvo);
}