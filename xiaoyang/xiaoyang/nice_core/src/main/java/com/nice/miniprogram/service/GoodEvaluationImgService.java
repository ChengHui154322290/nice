package com.nice.miniprogram.service;
import com.nice.miniprogram.model.GoodEvaluationImg;
import com.nice.miniprogram.core.Service;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/08/13.
 */
public interface GoodEvaluationImgService extends Service<GoodEvaluationImg> {

    void deleteById(Integer id);

    List<GoodEvaluationImg> getByEvaluationId(Integer evaluationId);

}
