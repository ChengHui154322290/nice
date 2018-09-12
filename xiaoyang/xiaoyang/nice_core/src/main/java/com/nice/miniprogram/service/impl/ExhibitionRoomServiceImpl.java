package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.core.RedisService;
import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.ExhibitionRoomMapper;
import com.nice.miniprogram.model.ExhibitionRoom;
import com.nice.miniprogram.service.ExhibitionRoomService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/27.
 */
@Service
@Transactional
public class ExhibitionRoomServiceImpl extends AbstractService<ExhibitionRoom> implements ExhibitionRoomService {
    @Resource
    private ExhibitionRoomMapper exhibitionRoomMapper;
    @Resource
    private RedisService redisServicer;

    @Override
    public Boolean checkIsChose(Integer exhibitionRoomId, int startDay, int endDay, int startHour, int endHour) {
        if(startDay == endDay) {
            Set choseHours = redisServicer.setMembers(exhibitionRoomId + "_" + startDay);
            for (int i=startHour;i<=endHour;i++){
                if(choseHours.contains(i)){
                    return true;
                }
            }
        }else{
            Set choseHours1 = redisServicer.setMembers(exhibitionRoomId + "_" + startDay);
            Set choseHours2 = redisServicer.setMembers(exhibitionRoomId + "_" + endDay);
            for(int i=startHour;i<=endHour;i++){
                if(choseHours1.contains(i) || choseHours2.contains(i)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<ExhibitionRoom> selectByPlaceNumber(String placeNumber) {
        return exhibitionRoomMapper.selectByPlaceNumber(placeNumber);
    }

    @Override
    public String queryPlaceNumber(Integer exhibitionRoomId) {
        return exhibitionRoomMapper.queryPlaceNumber(exhibitionRoomId);
    }
}
