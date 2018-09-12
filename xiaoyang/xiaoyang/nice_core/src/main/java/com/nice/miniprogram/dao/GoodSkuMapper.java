package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.GoodSku;
import com.nice.miniprogram.vo.GoodSkuVo;

import java.util.List;
import java.util.Map;

public interface GoodSkuMapper extends CoreMapper<GoodSku> {
    GoodSku getBySku(String skuCode);

    List<GoodSku> getBySpu(String spuCode);

    List<GoodSkuVo> getGoodList(Map<String, Object> params);

    List<GoodSkuVo> getSkuListByParams(Map<String, Object> params);

    GoodSkuVo getSkuVoBySkuCode(Map<String,Object> param);

    List<GoodSkuVo> findPlaceByParams(Map<String, Object> params);
}