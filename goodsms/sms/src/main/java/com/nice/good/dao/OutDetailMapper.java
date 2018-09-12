package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.OutDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutDetailMapper extends Mapper<OutDetail> {
    void updateByBaseId(@Param(value = "baseId") String baseId, @Param(value = "hangUp") Integer hangUp);

    List<String> selectByGooderAndCode(@Param(value = "gooderCode") String gooderCode, @Param(value = "goodCode") String goodCode);

    void deleteByBaseId(String baseId);

    List<OutDetail> selectByBaseId(String baseId);

    Integer selectOrderSumByBaseId(String baseId);

    Integer selectAllotSumByBaseId(String baseId);


    Integer selectSumOfOrderNum(String baseId);

    Integer selectSumOfSurplusNum(String baseId);

    Integer selectSumOfAllotNum(String baseId);

    Integer selectSumOfPickNum(String baseId);


    Integer selectCountByBaseId(String baseId);
}