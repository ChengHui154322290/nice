package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.GoodImg;

import java.util.List;

public interface GoodImgMapper extends CoreMapper<GoodImg> {

    GoodImg getBySpu(String spuCode);

    List<GoodImg> getBySku(String skuCode);

    List<GoodImg> getSpuDetailImgs(String spuCode);

    List<GoodImg> getListBySpu(String spuCode);
}