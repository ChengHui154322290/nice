package com.nice.miniprogram.service;
import com.nice.miniprogram.model.ExhibitionRoom;
import com.nice.miniprogram.core.Service;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/07/27.
 */
public interface ExhibitionRoomService extends Service<ExhibitionRoom> {

    List<ExhibitionRoom> selectByPlaceNumber(String placeNumber);

    Boolean checkIsChose(Integer exhibitionRoomId,int startDay,int endDay,int startHour,int endHour);

    String queryPlaceNumber(Integer exhibitionRoomId);
}
