package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.core.RedisService;
import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.ShowRoomInfoMapper;
import com.nice.miniprogram.model.ShowRoomInfo;
import com.nice.miniprogram.service.ShowRoomInfoService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/06/12.
 */
@Service
@Transactional
public class ShowRoomInfoServiceImpl extends AbstractService<ShowRoomInfo> implements ShowRoomInfoService {
    @Resource
    private ShowRoomInfoMapper showRoomInfoMapper;
    @Resource
    private RedisService redisServicer;

    @Override
    public Boolean checkIsChose(int roomId, int startDay, int endDay, int startHour, int endHour) {
        if(startDay == endDay) {
            Set choseHours = redisServicer.setMembers(roomId + "_" + startDay);
            for (int i=startHour;i<=endHour;i++){
                if(choseHours.contains(i)){
                    return true;
                }
            }
        }else{
            Set choseHours1 = redisServicer.setMembers(roomId + "_" + startDay);
            Set choseHours2 = redisServicer.setMembers(roomId + "_" + endDay);
            for(int i=startHour;i<=endHour;i++){
                if(choseHours1.contains(i) || choseHours2.contains(i)){
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public List<ShowRoomInfo> getListByParams(Map<String, Object> params) {
        return showRoomInfoMapper.getListByParams(params);
    }

    @Override
    public String queryPlaceNumber(Integer showRoomId) {
        return showRoomInfoMapper.queryPlaceNumber(showRoomId);
    }
}
