package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.GoodCategory;

import java.util.List;

public interface GoodCategoryMapper extends Mapper<GoodCategory> {
	List<String> findAllCategory();

	Integer findCategoryId(String categoryName);
}