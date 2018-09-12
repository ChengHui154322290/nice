package com.nice.miniprogram.service;
import com.nice.miniprogram.model.StoreArea;
import com.nice.miniprogram.core.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/07/18.
 */
public interface StoreAreaService extends Service<StoreArea> {

    List<StoreArea> selectByParams(Map<String,Object> params);

    Boolean checkIsChose(String areaId,int startDay,int endDay,int startHour,int endHour);


}
