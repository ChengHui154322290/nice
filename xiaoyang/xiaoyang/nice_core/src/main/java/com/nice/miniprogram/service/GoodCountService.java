package com.nice.miniprogram.service;
import com.nice.miniprogram.model.GoodCount;
import com.nice.miniprogram.core.Service;
import com.nice.miniprogram.vo.GoodCountSumVo;

import java.util.List;

/**
 * Created by CodeGenerator on 2018/06/27.
 */
public interface GoodCountService extends Service<GoodCount> {

    //新增（第一次计数）
    void newCountRecord(GoodCount goodCount);
    /*===================================点击量计数===================================*/
    //修改（累加计数）
    void addClickNum(String skuCode);
    /*===================================领用次数计数===================================*/
    //修改（累加计数）
    void addCollarNum(String skuCode);

    GoodCount getBySku(String skuCode);

    //计数
    void setGoodCount(String skuCode);

    List<GoodCount> getBySpu(String spuCode);

    GoodCountSumVo getSumBySpu(String spuCode);
}
