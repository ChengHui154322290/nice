package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.wx_model.User;

import java.util.List;

public interface UserMapper extends Mapper<User> {
	List<String> findAllAccount();

	Integer findIdByAccount(String account);

	List<User> findByUser(User user);

}