package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.wx_model.GoodSku;
import com.nice.good.wx_model.GoodSkuVo;

import java.util.List;
import java.util.Map;


public interface GoodSkuMapper extends Mapper<GoodSku> {
	List<GoodSkuVo> selectBygoodsMap(Map<String, Object> conditionMap);

	GoodSku getBySku(String skuCode);
}