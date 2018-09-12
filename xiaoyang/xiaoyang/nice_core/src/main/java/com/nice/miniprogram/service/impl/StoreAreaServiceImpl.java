package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.core.RedisService;
import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.StoreAreaMapper;
import com.nice.miniprogram.model.StoreArea;
import com.nice.miniprogram.service.StoreAreaService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/18.
 */
@Service
@Transactional
public class StoreAreaServiceImpl extends AbstractService<StoreArea> implements StoreAreaService {
    @Resource
    private StoreAreaMapper storeAreaMapper;
    @Resource
    private RedisService redisServicer;

    @Override
    public List<StoreArea> selectByParams(Map<String, Object> params) {
        return storeAreaMapper.selectByParams(params);
    }

    @Override
    public Boolean checkIsChose(String areaId, int startDay, int endDay, int startHour, int endHour) {
        if(startDay == endDay) {
            Set choseHours = redisServicer.setMembers(areaId + "_" + startDay);
            for (int i=startHour;i<=endHour;i++){
                if(choseHours.contains(i)){
                    return true;
                }
            }
        }else{
            Set choseHours1 = redisServicer.setMembers(areaId + "_" + startDay);
            Set choseHours2 = redisServicer.setMembers(areaId + "_" + endDay);
            for(int i=startHour;i<=endHour;i++){
                if(choseHours1.contains(i) || choseHours2.contains(i)){
                    return true;
                }
            }
        }
        return false;
    }
}
