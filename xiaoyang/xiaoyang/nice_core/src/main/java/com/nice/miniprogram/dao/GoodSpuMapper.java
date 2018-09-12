package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.GoodSpu;
import com.nice.miniprogram.vo.GoodSpuVo;

import java.util.List;
import java.util.Map;

public interface GoodSpuMapper extends CoreMapper<GoodSpu> {

    List<GoodSpuVo> getListByParams(Map<String, Object> params);

    GoodSpu getBySpu(String spuCode);

    List<GoodSpuVo> getRecommendSpuList(Map<String, Object> params);
}