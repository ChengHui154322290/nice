package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.ShowCount;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ShowCountMapper extends CoreMapper<ShowCount> {

//    @Select("select * from x_show_count where show_room_id = #{showRoomId}")
    ShowCount getByShowRoomId(Integer showRoomId);

    @Select("select * from v_exhibition_room where id = #{id}")
    ShowCount getByExhibitionRoomId(Integer id);

    @Update("update x_show_count set click_num=(click_num+1) where id=#{id}")
    void addClickNum(Integer id);
    @Update("update x_show_count set bespeak_num=(bespeak_num+1) where id=#{id}")
    void addBespeakNum(Integer id);
}