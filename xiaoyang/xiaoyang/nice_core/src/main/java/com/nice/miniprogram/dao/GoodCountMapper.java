package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.GoodCount;
import com.nice.miniprogram.vo.GoodCountSumVo;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodCountMapper extends CoreMapper<GoodCount> {

    @Update("update x_good_count set click_num=(click_num+1) where sku_code=#{skuCode}")
    void addClickNum(String skuCode);
    @Update("update x_good_count set collar_num=(collar_num+1) where sku_code=#{skuCode}")
    void addCollarNum(String skuCode);

    GoodCount getBySku(String skuCode);

    List<GoodCount> getBySpu(String spuCode);

    GoodCountSumVo getSumBySpu(String spuCode);
}