package com.nice.miniprogram.service;
import com.nice.miniprogram.model.GoodSpu;
import com.nice.miniprogram.core.Service;
import com.nice.miniprogram.vo.GoodSpuVo;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/08/06.
 */
public interface GoodSpuService extends Service<GoodSpu> {

    List<GoodSpuVo> getListByParams(Map<String,Object> params);

    GoodSpu getBySpu(String spuCode);

    List<GoodSpuVo> getRecommendSpuList(Map<String,Object> params);

}
