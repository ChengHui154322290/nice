package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.UserGooder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGooderMapper extends Mapper<UserGooder> {


    /**
     * 通过 username 查询 gooderName -- List<String>   -- 2018/05/21  14:12
     *
     * @param username
     * @return
     */
    List<String> findGooderNameByUsername(String username);


    /**
     * 通过 username 查询 gooderCode -- List<String>
     *
     * @param username
     * @return
     */
    List<String> findGooderCodeByUsername(String username);

    /**
     * 通过 username 删除 a_user_gooder 表中对应的数据
     *
     * @param username
     */
    void deleteUserGooderByUsername(@Param(value = "username") String username);


    List<String> selectGooderCodeByUserName(@Param(value = "username") String username);

    List<String> selectUserNameByGooderCode(String gooderCode);


}