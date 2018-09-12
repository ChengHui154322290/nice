package com.nice.good.service;
import com.nice.good.wx_model.ShowRoomInfo;
import com.nice.good.core.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/08/06.
 */
public interface ShowRoomInfoService extends Service<ShowRoomInfo> {
      void showRoomInfoAdd(ShowRoomInfo showRoomInfo,String userId);
      void showRoomInfoUpdate(ShowRoomInfo showRoomInfo,String userId);

	Boolean checkIsChose(Integer showRoomId, int startDay, int endDay, int startHour, int endHour);

}
