package com.nice.good.service;
import com.nice.good.wx_model.User;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/08/24.
 */
public interface UserService extends Service<User> {
      void userAdd(User user,String userId);
      void userUpdate(User user,String userId);

      User getUser(int userId);

	List<User> findByUser(User user);

}
