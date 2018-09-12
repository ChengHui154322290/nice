package com.nice.miniprogram.service;
import com.nice.miniprogram.model.Stock;
import com.nice.miniprogram.core.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/07/10.
 */
public interface StockService extends Service<Stock> {

    Stock getOneByParams(Map<String,Object> params);
}
