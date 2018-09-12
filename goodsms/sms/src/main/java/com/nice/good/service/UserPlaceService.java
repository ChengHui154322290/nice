package com.nice.good.service;

import com.nice.good.model.UserPlace;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/21.
 */
public interface UserPlaceService extends Service<UserPlace> {
    void userPlaceAdd(UserPlace userPlace, String userId) throws Exception;

    void userPlaceUpdate(UserPlace userPlace, String userId);


    /**
     * 通过 username 查询 PlaceName -- List<String>    -- 2018/05/21  14:27
     *
     * @param username
     * @return
     */
    List<String> findPlaceNamesByUsername(String username);


    /**
     * 通过 username 查询 placeNumber -- List<String>
     *
     * @param username
     * @return
     */
    List<String> findPlaceNumbersByUsername(String username);

    /**
     * 通过 username 删除 a_user_place 表中对应的数据
     *
     * @param username
     */
    void deleteUserPlaceByUsername(String username);

    /**
     * 传递 username、 placeNumber、 userId 数据，对 a_user_place 表进行数据插入 。
     *
     * @param username
     * @param placeNumber
     * @param userId
     * @throws Exception
     */
    void insertUserPlaceByUsernamePlaceNumber(String username, String placeNumber, String userId) throws Exception;

}
