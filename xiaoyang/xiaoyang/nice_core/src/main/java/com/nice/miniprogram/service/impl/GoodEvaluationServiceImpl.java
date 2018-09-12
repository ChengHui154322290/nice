package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.GoodEvaluationMapper;
import com.nice.miniprogram.model.GoodEvaluation;
import com.nice.miniprogram.service.GoodEvaluationService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/11.
 */
@Service
@Transactional
public class GoodEvaluationServiceImpl extends AbstractService<GoodEvaluation> implements GoodEvaluationService {
    @Resource
    private GoodEvaluationMapper goodEvaluationMapper;


    @Override
    public List<GoodEvaluation> listFeedback(Map<String,Object> params) {
        return goodEvaluationMapper.listFeedback(params);
    }

    @Override
    public Integer getCount(Map<String,Object> params) {
        return goodEvaluationMapper.getCount(params);
    }
}
