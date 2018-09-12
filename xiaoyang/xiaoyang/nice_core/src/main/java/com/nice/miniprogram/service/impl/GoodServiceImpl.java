package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.GoodMapper;
import com.nice.miniprogram.model.Good;
import com.nice.miniprogram.service.GoodService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/08/21.
 */
@Service
@Transactional
public class GoodServiceImpl extends AbstractService<Good> implements GoodService {
    @Resource
    private GoodMapper goodMapper;

    @Override
    public Good getOneByGoodCode(String goodCode) {
        return goodMapper.getOneByGoodCode(goodCode);
    }
}
