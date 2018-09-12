package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.UserGooderMapper;
import com.nice.good.model.UserGooder;
import com.nice.good.service.UserGooderService;
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
public class UserGooderServiceImpl extends AbstractService<UserGooder> implements UserGooderService {
    @Resource
    private UserGooderMapper userGooderMapper;

    @Override
    public void userGooderAdd(UserGooder userGooder, String userId) throws Exception {

        userGooder.setKeyId(IdsUtils.getOrderId());
        userGooder.setCreateId(userId);
        userGooder.setModifyId(userId);
        userGooder.setCreateDate(TimeStampUtils.getTimeStamp());
        userGooder.setModifyDate(TimeStampUtils.getTimeStamp());

        userGooderMapper.insert(userGooder);

    }


    @Override
    public void userGooderUpdate(UserGooder userGooder, String userId) {

        userGooder.setModifyId(userId);
        userGooder.setModifyDate(TimeStampUtils.getTimeStamp());
        userGooderMapper.updateByPrimaryKey(userGooder);
    }

    /**
     * 通过 username 查询 gooderName -- List<String>   -- 2018/05/21  14:12
     *
     * @param username
     * @return
     */
    @Override
    public List<String> findGooderNameByUsername(String username) {
        return userGooderMapper.findGooderNameByUsername(username);
    }

    /**
     * 通过 username 查询 gooderCode -- List<String>
     *
     * @param username
     * @return
     */
    @Override
    public List<String> findGooderCodeByUsername(String username) {
        return userGooderMapper.findGooderCodeByUsername(username);
    }

    /**
     * 通过 username 删除 a_user_gooder 表中对应的数据
     *
     * @param username
     */
    @Override
    public void deleteUserGooderByUsername(String username) {
        userGooderMapper.deleteUserGooderByUsername(username);
    }


    @Override
    public void insertUserGooderByUsernameGooderCode(String username, String gooderCode, String userId) throws Exception {

        UserGooder userGooder = new UserGooder();
        // 设置 keyId
        userGooder.setKeyId(IdsUtils.getOrderId());
        // 设置 username
        userGooder.setUsername(username);
        // 设置 gooderCode
        userGooder.setGooderCode(gooderCode);
        // 设置 CreateId
        userGooder.setCreateId(userId);
        // 设置 modifyId
        userGooder.setModifyId(userId);
        // 设置 createDate
        userGooder.setCreateDate(TimeStampUtils.getTimeStamp());
        // 设置 ModifyDate
        userGooder.setModifyDate(TimeStampUtils.getTimeStamp());

        userGooderMapper.insert(userGooder);
    }
}
