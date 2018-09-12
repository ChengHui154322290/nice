package com.nice.miniprogram.service;
import com.nice.miniprogram.model.GoodEvaluation;
import com.nice.miniprogram.core.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/07/11.
 */
public interface GoodEvaluationService extends Service<GoodEvaluation> {

    List<GoodEvaluation> listFeedback(Map<String,Object> params);

    Integer getCount(Map<String,Object> params);
}
