package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.Stock;
import com.nice.good.vo.StockNumVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public interface StockMapper extends Mapper<Stock> {


    /**
     * 更新 库存表 中的‘现有量’。 货主编码、货品编码，确定一条库存表。  -- 2018/06/19  10:30  rk
     *
     * @param adjustQuantity
     * @param gooderCode
     * @param goodCode
     */
    void updateNowNumAndUseNum(@Param(value = "adjustQuantity") Integer adjustQuantity,
                      @Param(value = "gooderCode") String gooderCode,
                      @Param(value = "goodCode") String goodCode,
                      @Param(value = "orgCode") String orgCode,
                      @Param(value = "providerCode") String providerCode,
                      @Param(value = "placeId") String placeId);

    String selectByGooderAndCode(@Param(value = "gooderCode") String gooderCode,
                                 @Param(value = "goodCode") String goodCode,
                                 @Param(value = "orgCode") String orgCode,
                                 @Param(value = "providerCode") String providerCode,
                                 @Param(value = "placeId") String placeId);

    void updateStockNowNum(@Param(value = "gooderCode") String gooderCode,
                           @Param(value = "goodCode") String goodCode,
                           @Param(value = "receiveNum") Integer receiveNum,
                           @Param(value = "orgCode") String orgCode,
                           @Param(value = "providerCode") String providerCode,
                           @Param(value = "placeId") String placeId);

    void updateStockPickNum(@Param(value = "totalRfidNum") Integer totalRfidNum,
                            @Param(value = "gooderCode") String gooderCode,
                            @Param(value = "goodCode") String goodCode,
                            @Param(value = "orgCode") String orgCode,
                            @Param(value = "providerCode") String providerCode,
                            @Param(value = "placeId") String placeId);

    Integer selectNowNumByCode(@Param(value = "gooderCode") String gooderCode,
                                        @Param(value = "goodCode") String goodCode,
                                        @Param(value = "orgCode") String orgCode,
                                        @Param(value = "providerCode") String providerCode,
                                        @Param(value = "placeId") String placeId);

    Integer selectUseNum(@Param(value = "stockId") String stockId,
                         @Param(value = "placeId") String placeId);

    Integer selectFreezeNum(@Param(value = "stockId") String stockId,
                            @Param(value = "placeId") String placeId);


    void updateAllotNumStock(@Param(value = "gooderCode") String gooderCode,
                             @Param(value = "goodCode") String goodCode,
                             @Param(value = "totalPickNum") Integer totalPickNum,
                             @Param(value = "orgCode") String orgCode,
                             @Param(value = "providerCode") String providerCode,
                             @Param(value = "placeId") String placeId);

    void updateOutStock(@Param(value = "gooderCode") String gooderCode,
                        @Param(value = "goodCode") String goodCode,
                        @Param(value = "sendNum") Integer sendNum,
                        @Param(value = "orgCode") String orgCode,
                        @Param(value = "providerCode") String providerCode,
                        @Param(value = "placeId") String placeId);

    Integer selectPicNumByGooderAndCode(@Param(value = "gooderCode") String gooderCode,
                                        @Param(value = "goodCode") String goodCode,
                                        @Param(value = "orgCode") String orgCode,
                                        @Param(value = "providerCode") String providerCode,
                                        @Param(value = "placeId") String placeId);

    void updateOutStockNum(@Param(value = "gooderCode") String gooderCode,
                           @Param(value = "goodCode") String goodCode,
                           @Param(value = "sendNum") Integer sendNum,
                           @Param(value = "orgCode") String orgCode,
                           @Param(value = "providerCode") String providerCode,
                           @Param(value = "placeId") String placeId);

    Integer selectUseNumByCode(@Param(value = "gooderCode") String gooderCode,
                               @Param(value = "goodCode") String goodCode,
                               @Param(value = "orgCode") String orgCode,
                               @Param(value = "providerCode") String providerCode,
                               @Param(value = "placeId") String placeId);

    void addStockPickNum(@Param(value = "gooderCode") String gooderCode,
                         @Param(value = "goodCode") String goodCode,
                         @Param(value = "rfidNum") Integer rfidNum,
                         @Param(value = "orgCode") String orgCode,
                         @Param(value = "providerCode") String providerCode,
                         @Param(value = "placeId") String placeId);

    void reduceStock(@Param(value = "gooderCode") String gooderCode,
                     @Param(value = "goodCode") String goodCode,
                     @Param(value = "sendNum") Integer sendNum,
                     @Param(value = "orgCode") String orgCode,
                     @Param(value = "providerCode") String providerCode,
                     @Param(value = "placeId") String placeId);

    void reduceStockNum(@Param(value = "gooderCode") String gooderCode,
                        @Param(value = "goodCode") String goodCode,
                        @Param(value = "sendNum") Integer sendNum,
                        @Param(value = "orgCode") String orgCode,
                        @Param(value = "providerCode") String providerCode,
                        @Param(value = "placeId") String placeId);

    List<Stock> selectByGooder(@Param(value = "gooderCode") String gooderCode,
                               @Param(value = "placeId") String placeId);

    List<String> selectGooderCodes(@Param(value = "placeId") String placeId);

    void updatePickNum(@Param(value = "gooderCode") String gooderCode,
                       @Param(value = "goodCode") String goodCode,
                       @Param(value = "pickNum") Integer pickNum,
                       @Param(value = "orgCode") String orgCode,
                       @Param(value = "providerCode") String providerCode,
                       @Param(value = "placeId") String placeId);


    StockNumVo countStockNum(Map<String, Object> conditionMap);
}
