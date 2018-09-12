package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.OutPick;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OutPickMapper extends Mapper<OutPick> {
    Integer selectMaxId();
    List<OutPick> selectAllByDetailId(String detailId);
    void  deleteByDetailId(String detailId);
    Integer selectSumOfRfidNum(String detailId);

   List<OutPick> selectByGooderAndGoodCode(@Param(value = "gooderCode")String gooderCode, @Param(value = "goodCode")String goodCode);

   OutPick selectGoodAndSeatCode(@Param(value = "gooderCode")String gooderCode,
                                 @Param(value = "goodCode")String goodCode,
                                 @Param(value = "seatCode")String seatCode);


   Integer selectCountRfidByDetailId(@Param(value = "detailId")String detailId);
}