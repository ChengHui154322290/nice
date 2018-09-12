package com.nice.good.service;

import com.nice.good.model.Stock;
import com.nice.good.core.Service;
import com.nice.good.vo.StockNumVo;

import java.util.Map;


/**
 * Created by CodeGenerator on 2018/04/07.
 */
public interface StockService extends Service<Stock> {
    void stockAdd(Stock stock, String userId);

    void stockUpdate(Stock stock, String userId);

    StockNumVo countStockNum(Map<String, Object> conditionMap);

}
