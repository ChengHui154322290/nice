package com.nice.good.service.impl;


import com.nice.good.core.AbstractService;
import com.nice.good.dao.GoodSkuMapper;
import com.nice.good.service.GoodSkuService;
import com.nice.good.wx_model.GoodSku;
import com.nice.good.wx_model.GoodSkuVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/08/06.
 */
@Service
@Transactional
public class GoodSkuServiceImpl extends AbstractService<GoodSku> implements GoodSkuService {
    @Resource
    private GoodSkuMapper goodSkuMapper;

    @Override
    public GoodSku getBySku(String skuCode) {
        return goodSkuMapper.getBySku(skuCode);
    }

    @Override
        public List<GoodSkuVo> findBygoods(GoodSkuVo good) {
            Map<String, Object> conditionMap = new HashMap<>();

            String gooderCode = null;

            String placeNumber = null;

            String skuName = null;

            String skuCode = null;

            String seatCode = null;

            String areaCode = null;

            String goodColor = null;

            String goodSize = null;
            if (good != null) {
                placeNumber = good.getPlaceNumber();
                areaCode = good.getAreaCode();
                goodColor = good.getGoodColor();
                goodSize = good.getGoodSize();
                gooderCode = good.getGooderCode();
                skuName = good.getSkuName();
                skuCode = good.getSkuCode();
                seatCode = good.getSeatCode();
            }
            //货主名称模糊匹配

            conditionMap.put("gooderCode", gooderCode);

            conditionMap.put("placeNumber", placeNumber);

            conditionMap.put("areaCode", areaCode);

            conditionMap.put("skuName", skuName);

            conditionMap.put("goodColor", goodColor);

            conditionMap.put("goodSize", goodSize);

            conditionMap.put("skuCode", skuCode);

            conditionMap.put("seatCode", seatCode);

            return goodSkuMapper.selectBygoodsMap(conditionMap);


        }

    }
