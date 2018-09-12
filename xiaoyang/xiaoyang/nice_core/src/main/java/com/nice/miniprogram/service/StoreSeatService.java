package com.nice.miniprogram.service;
import com.nice.miniprogram.model.StoreSeat;
import com.nice.miniprogram.core.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/07/23.
 */
public interface StoreSeatService extends Service<StoreSeat> {
    String queryPlaceNumber(String seatId);

    List<StoreSeat> selectByParams(Map<String,Object> params);

    Boolean checkIsChose(String seatId,int startDay,int endDay,int startHour,int endHour);

    StoreSeat findByCode(String seatCode,String placeId);
}
