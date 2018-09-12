package com.nice.miniprogram.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.ConsigneeInfoMapper;
import com.nice.miniprogram.model.ConsigneeInfo;
import com.nice.miniprogram.service.ConsigneeInfoService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/06/19.
 */
@Service
@Transactional
public class ConsigneeInfoServiceImpl extends AbstractService<ConsigneeInfo> implements ConsigneeInfoService {
    @Resource
    private ConsigneeInfoMapper consigneeInfoMapper;


    @Override
    public void consigneeInfoAdd(ConsigneeInfo consigneeInfo) {
        consigneeInfoMapper.insert(consigneeInfo);
    }

    @Override
    public List<ConsigneeInfo> findByOwbId(int ownId) {
        return consigneeInfoMapper.findByOwbId(ownId);
    }

    @Override
    public  ConsigneeInfo  selectGetConsignee(int id) {
        ConsigneeInfo consigneeInfo = consigneeInfoMapper.selectByPrimaryKey(id);
        return consigneeInfo;
    }

    @Override
    public void deleteByIds(List<String> ids) {
        for (String id :ids){
            consigneeInfoMapper.deleteByPrimaryKey(id);
        }
    }
}
