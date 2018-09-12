package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.core.RedisService;
import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.StoreSeatMapper;
import com.nice.miniprogram.model.StoreSeat;
import com.nice.miniprogram.service.StoreSeatService;
import com.nice.miniprogram.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/23.
 */
@Service
@Transactional
public class StoreSeatServiceImpl extends AbstractService<StoreSeat> implements StoreSeatService {
    @Resource
    private StoreSeatMapper storeSeatMapper;
    @Resource
    private RedisService redisServicer;

    @Override
    public List<StoreSeat> selectByParams(Map<String, Object> params) {
        return storeSeatMapper.selectByParams(params);
    }

    @Override
    public String queryPlaceNumber(String seatId) {
        return storeSeatMapper.queryPlaceNumber(seatId);
    }

    @Override
    public StoreSeat findByCode(String seatCode,String placeId) {
        Map<String,Object> params = new HashMap<>();
        params.put("seatCode",seatCode);
        if(StringUtils.isNotBlank(placeId)) {
            params.put("placeId", placeId);
        }
        return storeSeatMapper.findByCode(params);
    }

    @Override
    public Boolean checkIsChose(String seatId, int startDay, int endDay, int startHour, int endHour) {
        if(startDay == endDay) {
            Set choseHours = redisServicer.setMembers(seatId + "_" + startDay);
            for (int i=startHour;i<=endHour;i++){
                if(choseHours.contains(i)){
                    return true;
                }
            }
        }else{
            Set choseHours1 = redisServicer.setMembers(seatId + "_" + startDay);
            Set choseHours2 = redisServicer.setMembers(seatId + "_" + endDay);
            for(int i=startHour;i<=endHour;i++){
                if(choseHours1.contains(i) || choseHours2.contains(i)){
                    return true;
                }
            }
        }
        return false;
    }
}
