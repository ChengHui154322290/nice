package com.nice.good.dao;



import com.nice.good.core.Mapper;
import com.nice.good.wx_model.GoodSpu;
import org.apache.ibatis.annotations.Param;


public interface GoodSpuMapper extends Mapper<GoodSpu> {

    GoodSpu getBySpu(String spuCode);
    GoodSpu getSpuByGooderAndCommodityCode(@Param(value = "gooderCode") String gooderCode,
                                           @Param(value = "spuCode") String spuCode);

}