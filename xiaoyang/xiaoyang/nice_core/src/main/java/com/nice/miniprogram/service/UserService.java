package com.nice.miniprogram.service;
import com.nice.miniprogram.model.User;
import com.nice.miniprogram.core.Service;


/**
 * Created by CodeGenerator on 2018/06/11.
 */
public interface UserService extends Service<User> {

	User getUser(int userId);

	void updateUserInfo(User user);

	int selectByCount(String account);

	String findPassword(String account);

	User findUser(String account);

	void updateNum(User user);
}
