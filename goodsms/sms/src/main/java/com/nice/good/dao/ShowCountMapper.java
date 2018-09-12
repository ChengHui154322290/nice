package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.wx_model.ShowCount;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ShowCountMapper extends Mapper<ShowCount> {
	@Select("select * from x_show_count where show_room_id = #{showRoomId}")
	ShowCount getByShowRoomId(Integer showRoomId);
	@Update("update x_show_count set bespeak_num=(bespeak_num+1) where id=#{id}")
	void addBespeakNum(Integer id);
}