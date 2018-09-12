package com.nice.good.service;

import com.nice.good.model.SeatStock;
import com.nice.good.core.Service;
import com.nice.good.vo.SeatStockVo;
import com.nice.good.vo.StockNumVo;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/04/09.
 */
public interface SeatStockService extends Service<SeatStock> {
    void seatStockAdd(SeatStock seatStock, String userId);

    void seatStockUpdate(SeatStock seatStock, String userId);


    StockNumVo countSeatNum(Map<String, Object> conditionMap);

	List<SeatStock> getByParams(Map<String, Object> params);

	List<SeatStockVo> findByBar(SeatStockVo seatStockvo);
}
