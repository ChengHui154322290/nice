package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.dao.GoodSkuMapper;
import com.nice.miniprogram.model.GoodSku;
import com.nice.miniprogram.service.GoodSkuService;
import com.nice.miniprogram.core.AbstractService;
import com.nice.miniprogram.vo.GoodSkuVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/08/06.
 */
@Service
@Transactional
public class GoodSkuServiceImpl extends AbstractService<GoodSku> implements GoodSkuService {
    @Resource
    private GoodSkuMapper goodSkuMapper;

    @Override
    public List<GoodSkuVo> getGoodList(Map<String, Object> params) {
        return goodSkuMapper.getGoodList(params);
    }

    @Override
    public GoodSku getBySku(String skuCode) {
        return goodSkuMapper.getBySku(skuCode);
    }

    @Override
    public List<GoodSku> getBySpu(String spuCode) {
        return goodSkuMapper.getBySpu(spuCode);
    }

    @Override
    public List<GoodSkuVo> getSkuListByParams(Map<String,Object> params) {
        return goodSkuMapper.getSkuListByParams(params);
    }

    @Override
    public GoodSkuVo getSkuVoBySkuCode(Map<String,Object> param) {
        return goodSkuMapper.getSkuVoBySkuCode(param);
    }

    @Override
    public List<GoodSkuVo> findPlaceByParams(Map<String, Object> params) {
        return goodSkuMapper.findPlaceByParams(params);
    }
}
