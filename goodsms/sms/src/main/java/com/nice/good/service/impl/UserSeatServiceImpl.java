package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.UserSeatMapper;
import com.nice.good.model.UserSeat;
import com.nice.good.service.UserSeatService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/08/09.
 */
@Service
@Transactional
public class UserSeatServiceImpl extends AbstractService<UserSeat> implements UserSeatService {
    @Resource
    private UserSeatMapper userSeatMapper;

    @Override
    public  void userSeatAdd(UserSeat userSeat,String userId){


        userSeat.setCreateId(userId);
        userSeat.setModifyId(userId);
        userSeat.setCreateDate(TimeStampUtils.getTimeStamp());
        userSeat.setModifyDate(TimeStampUtils.getTimeStamp());

        userSeatMapper.insert(userSeat);

    }


   @Override
   public void  userSeatUpdate(UserSeat userSeat,String userId){

        userSeat.setModifyId(userId);
        userSeat.setModifyDate(TimeStampUtils.getTimeStamp());
        userSeatMapper.updateByPrimaryKey(userSeat);
   }

}
