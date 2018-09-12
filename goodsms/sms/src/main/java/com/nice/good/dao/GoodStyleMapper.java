package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.GoodStyle;

import java.util.List;

public interface GoodStyleMapper extends Mapper<GoodStyle> {
	 List<String> findAllStyle() ;

	Integer findStyleId(String styleName);
}