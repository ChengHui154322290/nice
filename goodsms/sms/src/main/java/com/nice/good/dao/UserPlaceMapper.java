package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.UserPlace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserPlaceMapper extends Mapper<UserPlace> {


    /**
     * 通过 username 查询 PlaceName -- List<String>    -- 2018/05/21  14:27
     * @param username
     * @return
     */
    List<String> findPlaceNamesByUsername(String username);


    /**
     * 通过 username 查询 PlaceNumber -- List<String>
     * @param username
     * @return
     */
    List<String> findPlaceNumbersByUsername(String username);

    /**
     * 通过 username 删除 a_user_gooder 表中对应的数据
     * @param username
     */
    void deleteUserPlaceByUsername(@Param(value = "username") String username);


    List<String> selectPlaceIdByUserName(@Param(value = "username") String username);


	List<String> findAllPlaceNumbers();

    List<String> selectUserNameByPlaceNumber(String placeNumber);
}