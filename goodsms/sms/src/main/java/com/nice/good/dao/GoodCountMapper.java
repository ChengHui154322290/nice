package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.wx_model.GoodCount;
import org.apache.ibatis.annotations.Update;

public interface GoodCountMapper extends Mapper<GoodCount> {
	GoodCount getGoodCount(String goodId);
	@Update("update x_good_count set collar_num=(collar_num+1) where good_id=#{goodId}")
	void addCollarNum(String goodId);
}