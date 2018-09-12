package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.GoodImgMapper;
import com.nice.miniprogram.model.GoodImg;
import com.nice.miniprogram.service.GoodImgService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


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
    public List<GoodImg> getListBySpu(String spuCode) {
        return goodImgMapper.getListBySpu(spuCode);
    }
}
