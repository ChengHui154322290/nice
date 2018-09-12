package com.nice.good.service.impl;


import com.nice.good.core.AbstractService;
import com.nice.good.dao.GoodSpuMapper;
import com.nice.good.service.GoodSpuService;
import com.nice.good.wx_model.GoodSpu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/08/06.
 */
@Service
@Transactional
public class GoodSpuServiceImpl extends AbstractService<GoodSpu> implements GoodSpuService {
    @Resource
    private GoodSpuMapper goodSpuMapper;

    @Override
    public GoodSpu getBySpu(String spuCode) {
        return goodSpuMapper.getBySpu(spuCode);
    }

}
