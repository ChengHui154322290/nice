package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.Type;

import java.util.List;

public interface TypeMapper extends Mapper<Type> {
	List<String> selectTypeAll();
}