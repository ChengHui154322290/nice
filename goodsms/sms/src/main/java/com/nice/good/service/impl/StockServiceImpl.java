package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.StockMapper;
import com.nice.good.model.Stock;
import com.nice.good.service.StockService;
import com.nice.good.core.AbstractService;
import com.nice.good.vo.StockNumVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/04/07.
 */
@Service
@Transactional
public class StockServiceImpl extends AbstractService<Stock> implements StockService {
    @Resource
    private StockMapper stockMapper;

    @Override
    public  void stockAdd(Stock stock,String userId){


        stock.setCreateId(userId);
        stock.setModifyId(userId);
        stock.setCreateDate(TimeStampUtils.getTimeStamp());
        stock.setModifyDate(TimeStampUtils.getTimeStamp());

        stockMapper.insert(stock);

    }


   @Override
   public void  stockUpdate(Stock stock,String userId){

        stock.setModifyId(userId);
        stock.setModifyDate(TimeStampUtils.getTimeStamp());
        stockMapper.updateByPrimaryKey(stock);
   }

    @Override
    public StockNumVo countStockNum(Map<String, Object> conditionMap) {
        return stockMapper.countStockNum(conditionMap);
    }

}
