package com.nice.good.service;
import com.nice.good.wx_model.RoomStyle;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/08/27.
 */
public interface RoomStyleService extends Service<RoomStyle> {
      void roomStyleAdd(RoomStyle roomStyle,String userId);
      void roomStyleUpdate(RoomStyle roomStyle,String userId);
}
