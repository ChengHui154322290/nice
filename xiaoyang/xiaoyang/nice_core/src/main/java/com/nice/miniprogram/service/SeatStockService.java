package com.nice.miniprogram.service;
import com.nice.miniprogram.model.SeatStock;
import com.nice.miniprogram.core.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/07/10.
 */
public interface SeatStockService extends Service<SeatStock> {

    List<SeatStock> getByParams(Map<String,Object> params);

    SeatStock getOneByParams(Map<String,Object> params);
}
