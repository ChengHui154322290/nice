package com.nice.miniprogram.service;
import com.nice.miniprogram.model.GoodSku;
import com.nice.miniprogram.core.Service;
import com.nice.miniprogram.vo.GoodSkuVo;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/08/06.
 */
public interface GoodSkuService extends Service<GoodSku> {

    List<GoodSkuVo> getGoodList(Map<String,Object> params);

    GoodSku getBySku(String skuCode);

    List<GoodSku> getBySpu(String spuCode);

    List<GoodSkuVo> getSkuListByParams(Map<String,Object> params);

    GoodSkuVo getSkuVoBySkuCode(Map<String,Object> param);

    List<GoodSkuVo> findPlaceByParams(Map<String,Object> params);
}
