package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.GoodEvaluation;

import java.util.List;
import java.util.Map;

public interface GoodEvaluationMapper extends CoreMapper<GoodEvaluation> {
    List<GoodEvaluation> listFeedback(Map<String,Object> params);
    Integer getCount(Map<String,Object> params);
}