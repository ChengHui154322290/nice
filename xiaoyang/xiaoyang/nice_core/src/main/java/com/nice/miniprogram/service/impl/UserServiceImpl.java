package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.dao.UserMapper;
import com.nice.miniprogram.model.User;
import com.nice.miniprogram.service.UserService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/06/11.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;


    @Override
    public User getUser(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void updateUserInfo(User user) {
        userMapper.updateUserInfo(user);
    }

    @Override
    public int selectByCount(String account) {
        return userMapper.selectByCount(account);
    }

    @Override
    public String findPassword(String account) {
        return  userMapper.findPassword(account);
    }

    @Override
    public User findUser(String account) {
        return userMapper.findUser(account);
    }

    @Override
    public void updateNum(User user) {
        userMapper.updateNum(user);
    }


}
