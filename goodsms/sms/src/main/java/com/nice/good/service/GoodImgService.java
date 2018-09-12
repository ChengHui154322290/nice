package com.nice.good.service;

import com.nice.good.core.Service;
import com.nice.good.wx_model.GoodImg;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/08/07.
 */
public interface GoodImgService extends Service<GoodImg> {

    GoodImg getBySpu(String spuCode);

    List<GoodImg> getBySku(String skuCode);

    List<GoodImg> getSpuDetailImgs(String spuCode);

    List<GoodImg> selectByParams(Map<String,Object> params);
}
