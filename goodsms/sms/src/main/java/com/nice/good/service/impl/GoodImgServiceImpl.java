package com.nice.good.service.impl;


import com.nice.good.core.AbstractService;
import com.nice.good.dao.GoodImgMapper;
import com.nice.good.service.GoodImgService;
import com.nice.good.wx_model.GoodImg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/08/07.
 */
@Service
@Transactional
public class GoodImgServiceImpl extends AbstractService<GoodImg> implements GoodImgService {
    @Resource
    private GoodImgMapper goodImgMapper;

    @Override
    public GoodImg getBySpu(String spuCode) {
        return goodImgMapper.getBySpu(spuCode);
    }

    @Override
    public List<GoodImg> getBySku(String skuCode) {
        return goodImgMapper.getBySku(skuCode);
    }

    @Override
    public List<GoodImg> getSpuDetailImgs(String spuCode) {
        return goodImgMapper.getSpuDetailImgs(spuCode);
    }

    @Override
    public List<GoodImg> selectByParams(Map<String, Object> params) {
        return goodImgMapper.selectByParams(params);
    }
}
