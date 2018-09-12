package com.nice.miniprogram.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.AlternativeMapper;
import com.nice.miniprogram.model.Alternative;
import com.nice.miniprogram.service.AlternativeService;
import com.nice.miniprogram.core.AbstractService;
import com.nice.miniprogram.vo.AlternativeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/06/08.
 */
@Service
@Transactional
public class AlternativeServiceImpl extends AbstractService<Alternative> implements AlternativeService {
    @Resource
    private AlternativeMapper alternativeMapper;


    @Override
    public void alternativeAdd(Alternative alternative) {
        alternative.setCreatetime(TimeStampUtils.getTimeStamp());
        alternative.setModifytime(TimeStampUtils.getTimeStamp());
        alternativeMapper.insert(alternative);
    }

    @Override
    public List<AlternativeVo> findByParams(Map<String,Object> params) {
        return alternativeMapper.findByParams(params);
    }

    @Override
    public void deleteByOwnIds(List<Integer> ids) {
        for (Integer id :ids){
            alternativeMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Alternative getOneByParams(Map<String, Object> params) {
        return alternativeMapper.getOneByParams(params);
    }
}
