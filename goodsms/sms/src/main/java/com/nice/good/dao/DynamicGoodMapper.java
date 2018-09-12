package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.DynamicGood;

public interface DynamicGoodMapper extends Mapper<DynamicGood> {


    DynamicGood findDynamicGoodByMaxId();

    /**
     * 从 s_dynamic_good 表中查询最大id值
     * @return
     */
    Integer selectMaxId();


}