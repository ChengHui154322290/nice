package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.User;

public interface UserMapper extends CoreMapper<User> {

	void updateUserInfo(User user);

	int selectByCount(String account);

	String findPassword(String account);

	User findUser(String account);

	void updateNum(User user);
}