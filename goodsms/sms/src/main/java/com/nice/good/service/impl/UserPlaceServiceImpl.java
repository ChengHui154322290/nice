package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.UserPlaceMapper;
import com.nice.good.model.UserPlace;
import com.nice.good.service.UserPlaceService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/21.
 */
@Service
@Transactional
public class UserPlaceServiceImpl extends AbstractService<UserPlace> implements UserPlaceService {
    @Resource
    private UserPlaceMapper userPlaceMapper;

    @Override
    public void userPlaceAdd(UserPlace userPlace, String userId) throws Exception {

        userPlace.setKeyId(IdsUtils.getOrderId());
        userPlace.setCreateId(userId);
        userPlace.setModifyId(userId);
        userPlace.setCreateDate(TimeStampUtils.getTimeStamp());
        userPlace.setModifyDate(TimeStampUtils.getTimeStamp());

        userPlaceMapper.insert(userPlace);

    }


    @Override
    public void userPlaceUpdate(UserPlace userPlace, String userId) {

        userPlace.setModifyId(userId);
        userPlace.setModifyDate(TimeStampUtils.getTimeStamp());
        userPlaceMapper.updateByPrimaryKey(userPlace);
    }



    /**
     * 通过 username 查询 PlaceName -- List<String>    -- 2018/05/21  14:27
     *
     * @param username
     * @return
     */
    @Override
    public List<String> findPlaceNamesByUsername(String username) {
        return userPlaceMapper.findPlaceNamesByUsername(username);
    }

    /**
     * 通过 username 查询 placeNumber -- List<String>
     *
     * @param username
     * @return
     */
    @Override
    public List<String> findPlaceNumbersByUsername(String username) {
        return userPlaceMapper.findPlaceNumbersByUsername(username);
    }

    /**
     * 通过 username 删除 a_user_place 表中对应的数据
     *
     * @param username
     */
    @Override
    public void deleteUserPlaceByUsername(String username) {
        userPlaceMapper.deleteUserPlaceByUsername(username);
    }

    /**
     * 传递 username、 placeNumber、 userId 数据，对 a_user_place 表进行数据插入 。
     *
     * @param username
     * @param placeNumber
     * @param userId
     * @throws Exception
     */
    @Override
    public void insertUserPlaceByUsernamePlaceNumber(String username, String placeNumber, String userId) throws Exception {

        UserPlace userPlace = new UserPlace();
        // 设置 keyId
        userPlace.setKeyId(IdsUtils.getOrderId());
        // 设置 username
        userPlace.setUsername(username);
        // 设置 placeNumber
        userPlace.setPlaceNumber(placeNumber);
        // 设置 CreateId
        userPlace.setCreateId(userId);
        // 设置 ModifyId
        userPlace.setModifyId(userId);
        // 设置 CreateDate
        userPlace.setCreateDate(TimeStampUtils.getTimeStamp());
        // 设置 ModifyDate
        userPlace.setModifyDate(TimeStampUtils.getTimeStamp());

        userPlaceMapper.insert(userPlace);

    }

}
