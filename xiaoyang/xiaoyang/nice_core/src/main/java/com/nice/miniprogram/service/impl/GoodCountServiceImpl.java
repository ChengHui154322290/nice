package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.dao.GoodCountMapper;
import com.nice.miniprogram.model.GoodCount;
import com.nice.miniprogram.model.GoodSku;
import com.nice.miniprogram.service.GoodCountService;
import com.nice.miniprogram.core.AbstractService;
import com.nice.miniprogram.service.GoodSkuService;
import com.nice.miniprogram.vo.GoodCountSumVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/27.
 */
@Service
@Transactional
public class GoodCountServiceImpl extends AbstractService<GoodCount> implements GoodCountService {
    @Resource
    private GoodCountMapper goodCountMapper;
    @Resource
    private GoodSkuService goodSkuService;

    @Override
    public void newCountRecord(GoodCount goodCount) {
        goodCountMapper.insert(goodCount);
    }

    @Override
    public void addClickNum(String skuCode) {
        goodCountMapper.addClickNum(skuCode);
    }

    @Override
    public void addCollarNum(String skuCode) {
        goodCountMapper.addCollarNum(skuCode);
    }

    @Override
    public GoodCount getBySku(String skuCode) {
        return goodCountMapper.getBySku(skuCode);
    }

    @Override
    public void setGoodCount(String skuCode) {
        GoodCount goodCount = getBySku(skuCode);
        //计数
        if(goodCount==null){
            GoodSku sku = goodSkuService.getBySku(skuCode);
            goodCount = new GoodCount();
            goodCount.setClickNum(1);
            goodCount.setCollarNum(0);
            goodCount.setSkuCode(skuCode);
            goodCount.setSpuCode(sku.getSpuCode());
            newCountRecord(goodCount);
        }else{
            addClickNum(skuCode);
        }
    }

    @Override
    public List<GoodCount> getBySpu(String spuCode) {
        return goodCountMapper.getBySpu(spuCode);
    }

    @Override
    public GoodCountSumVo getSumBySpu(String spuCode) {
        return goodCountMapper.getSumBySpu(spuCode);
    }

}
