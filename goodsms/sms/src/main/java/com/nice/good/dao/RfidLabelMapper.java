package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.RfidLabel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RfidLabelMapper extends Mapper<RfidLabel> {

    List<RfidLabel> selectByGooderAndGoodCode(@Param(value = "gooderCode") String gooderCode, @Param(value = "goodCode") String goodCode);

    RfidLabel selectByRfidCodeAndStatus(@Param(value = "rfidCode") String rfidCode,@Param(value = "status") Integer status);

    void deleteByGooderCodeAndGoodCode(@Param(value = "gooderCode") String gooderCode, @Param(value = "goodCode") String goodCode);

    List<String> selectRfidCodeByGooderAndGoodCode(@Param(value = "gooderCode") String gooderCode, @Param(value = "goodCode") String goodCode);


    List<String> selectAllLabelCode();
}