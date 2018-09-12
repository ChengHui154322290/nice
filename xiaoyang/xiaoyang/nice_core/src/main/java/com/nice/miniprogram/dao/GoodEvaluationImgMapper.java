package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.GoodEvaluationImg;

import java.util.List;

public interface GoodEvaluationImgMapper extends CoreMapper<GoodEvaluationImg> {
    void deleteById(Integer id);

    List<GoodEvaluationImg> getByEvaluationId(Integer evaluationId);
}