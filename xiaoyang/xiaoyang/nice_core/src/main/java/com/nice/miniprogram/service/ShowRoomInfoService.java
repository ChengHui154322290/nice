package com.nice.miniprogram.service;
import com.nice.miniprogram.model.ShowRoomInfo;
import com.nice.miniprogram.core.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/06/12.
 */
public interface ShowRoomInfoService extends Service<ShowRoomInfo> {

    Boolean checkIsChose(int roomId,int startDay,int endDay,int startHour,int endHour);

    List<ShowRoomInfo> getListByParams(Map<String,Object> params);

    String queryPlaceNumber(Integer showRoomId);
}
