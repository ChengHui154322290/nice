package com.nice.miniprogram.service;
import com.nice.miniprogram.model.GoodImg;
import com.nice.miniprogram.core.Service;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/08/07.
 */
public interface GoodImgService extends Service<GoodImg> {

    GoodImg getBySpu(String spuCode);

    List<GoodImg> getBySku(String skuCode);

    List<GoodImg> getSpuDetailImgs(String spuCode);

    List<GoodImg> getListBySpu(String spuCode);
}
