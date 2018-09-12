package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.UserSeat;
import org.apache.ibatis.annotations.Param;

public interface UserSeatMapper extends Mapper<UserSeat> {

    String selectSeatCodeByUserName(@Param(value = "userName") String userName,
                                    @Param(value = "placeId") String placeId);

	String findByseat(@Param(value = "userName") String userName,@Param(value = "placeId") String placeId);
}