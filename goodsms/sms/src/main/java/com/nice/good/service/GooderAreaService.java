package com.nice.good.service;

import com.nice.good.core.Result;
import com.nice.good.model.GooderArea;
import com.nice.good.core.Service;
import com.nice.good.vo.AreaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/16.
 */
public interface GooderAreaService extends Service<GooderArea> {
    String deleteByAreaId(List<String> areaIds,String placeId);

    String addGooderArea(List<AreaVo> areaVos, String gooderId, String placeId, String userId) throws Exception;

    Result listGooderArea(String gooderId,String placeId);
}
