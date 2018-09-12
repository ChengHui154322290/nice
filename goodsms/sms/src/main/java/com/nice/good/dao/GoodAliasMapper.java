package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.GoodAlias;

import java.util.List;

public interface GoodAliasMapper extends Mapper<GoodAlias> {
    String findIdByAliasCode(String aliasCode);
    String findIdByAliasType(String aliasType);
    void deleteByGoodId(String goodId);
    List<GoodAlias> selectAliasByGoodId(String goodId);
}