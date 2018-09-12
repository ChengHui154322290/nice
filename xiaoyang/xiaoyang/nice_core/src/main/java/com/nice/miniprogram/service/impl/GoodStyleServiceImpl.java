package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.GoodStyleMapper;
import com.nice.miniprogram.model.GoodStyle;
import com.nice.miniprogram.service.GoodStyleService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/21.
 */
@Service
@Transactional
public class GoodStyleServiceImpl extends AbstractService<GoodStyle> implements GoodStyleService {
    @Resource
    private GoodStyleMapper goodStyleMapper;


}
