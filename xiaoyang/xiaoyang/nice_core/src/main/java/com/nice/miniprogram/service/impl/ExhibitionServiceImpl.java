package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.ExhibitionMapper;
import com.nice.miniprogram.model.Exhibition;
import com.nice.miniprogram.service.ExhibitionService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/08/14.
 */
@Service
@Transactional
public class ExhibitionServiceImpl extends AbstractService<Exhibition> implements ExhibitionService {
    @Resource
    private ExhibitionMapper exhibitionMapper;

    @Override
    public List<Exhibition> getListByParams(Map<String, Object> params) {
        return exhibitionMapper.getListByParams(params);
    }
}
