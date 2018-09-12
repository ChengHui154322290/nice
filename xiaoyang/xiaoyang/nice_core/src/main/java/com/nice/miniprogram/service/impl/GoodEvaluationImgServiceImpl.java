package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.GoodEvaluationImgMapper;
import com.nice.miniprogram.model.GoodEvaluationImg;
import com.nice.miniprogram.service.GoodEvaluationImgService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/08/13.
 */
@Service
@Transactional
public class GoodEvaluationImgServiceImpl extends AbstractService<GoodEvaluationImg> implements GoodEvaluationImgService {
    @Resource
    private GoodEvaluationImgMapper goodEvaluationImgMapper;

    @Override
    public void deleteById(Integer id) {
        goodEvaluationImgMapper.deleteById(id);
    }

    @Override
    public List<GoodEvaluationImg> getByEvaluationId(Integer evaluationId) {
        return goodEvaluationImgMapper.getByEvaluationId(evaluationId);
    }
}
