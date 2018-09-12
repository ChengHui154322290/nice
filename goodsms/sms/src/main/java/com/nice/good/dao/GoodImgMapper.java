package com.nice.good.dao;


import com.nice.good.core.Mapper;
import com.nice.good.wx_model.GoodImg;

import java.util.List;
import java.util.Map;

public interface GoodImgMapper extends Mapper<GoodImg> {

    GoodImg getBySpu(String spuCode);

    List<GoodImg> getBySku(String skuCode);

    List<GoodImg> getSpuDetailImgs(String spuCode);

    List<GoodImg> selectByParams(Map<String, Object> params);
}