package com.nice.miniprogram.service;
import com.nice.miniprogram.model.CollarOrder;
import com.nice.miniprogram.core.Service;
import com.nice.miniprogram.model.OrderDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/06/07.
 */
public interface CollarOrderService extends Service<CollarOrder> {

    Integer addCollarOrderAndDetail(CollarOrder collarOrder, List<OrderDetail> orderDetails,String placeNumber);

    List<CollarOrder> selectListByParams(Map<String,Object> params);

    void updateOrderAndDetail(Integer status, Integer orderId ,String userName);

    Integer selectMaxId();

}
