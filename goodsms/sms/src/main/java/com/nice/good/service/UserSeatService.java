package com.nice.good.service;
import com.nice.good.model.UserSeat;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/08/09.
 */
public interface UserSeatService extends Service<UserSeat> {
      void userSeatAdd(UserSeat userSeat,String userId);
      void userSeatUpdate(UserSeat userSeat,String userId);
}
