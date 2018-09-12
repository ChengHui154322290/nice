package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.dao.GoodSpuMapper;
import com.nice.miniprogram.model.GoodSpu;
import com.nice.miniprogram.service.GoodSpuService;
import com.nice.miniprogram.core.AbstractService;
import com.nice.miniprogram.vo.GoodSpuVo;
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
public class GoodSpuServiceImpl extends AbstractService<GoodSpu> implements GoodSpuService {
    @Resource
    private GoodSpuMapper goodSpuMapper;

    @Override
    public List<GoodSpuVo> getListByParams(Map<String, Object> params) {
        return goodSpuMapper.getListByParams(params);
    }

    @Override
    public GoodSpu getBySpu(String spuCode) {
        return goodSpuMapper.getBySpu(spuCode);
    }

    @Override
    public List<GoodSpuVo> getRecommendSpuList(Map<String, Object> params) {
        return goodSpuMapper.getRecommendSpuList(params);
    }
}
