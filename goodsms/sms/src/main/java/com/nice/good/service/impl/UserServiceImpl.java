package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.UserMapper;
import com.nice.good.wx_model.User;
import com.nice.good.service.UserService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/08/24.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public  void userAdd(User user,String userId){


        userMapper.insert(user);

    }


   @Override
   public void  userUpdate(User user,String userId){

        userMapper.updateByPrimaryKey(user);
   }


    @Override
    public User getUser(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> findByUser(User user) {
        return userMapper.findByUser(user);
    }

}
