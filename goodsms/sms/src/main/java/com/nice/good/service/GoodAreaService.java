package com.nice.good.service;

import com.nice.good.core.Result;
import com.nice.good.model.GoodArea;
import com.nice.good.core.Service;
import com.nice.good.vo.AreaVo;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/23.
 */
public interface GoodAreaService extends Service<GoodArea> {

    void goodAreaAdd(GoodArea goodArea, String userId) throws Exception;

    void goodAreaUpdate(GoodArea goodArea, String userId);

    String deleteByGoodAreaId(List<String> areaIds,String placeId);

    String addGoodArea(List<AreaVo> areaVos,String goodId,String placeId,String userId) throws Exception;

    Result listGoodArea(String goodId,String placeId);
}
