package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.GoodPictureMapper;
import com.nice.miniprogram.model.GoodPicture;
import com.nice.miniprogram.service.GoodPictureService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/19.
 */
@Service
@Transactional
public class GoodPictureServiceImpl extends AbstractService<GoodPicture> implements GoodPictureService {
    @Resource
    private GoodPictureMapper goodPictureMapper;


    @Override
    public List<GoodPicture> getByGoodId(String goodId) {
        return goodPictureMapper.getByGoodId(goodId);
    }
}
