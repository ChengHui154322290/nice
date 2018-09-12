package com.nice.good.service;


import com.nice.good.core.Service;
import com.nice.good.wx_model.BookingSlipVo;
import com.nice.good.wx_model.CollarOrder;
import com.nice.good.wx_model.CollarOrderVo;
import com.nice.good.wx_model.OrderDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/06/07.
 */
public interface CollarOrderService extends Service<CollarOrder> {

    String addCollarOrderAndDetail(CollarOrder collarOrder, List<OrderDetail> orderDetails);

    Integer selectMaxId();

	CollarOrderVo selectCollar(String orderCode);

	void collarOrderUpdate(String orderCode);

	void collarOrderRepayUpdate(String orderCode);

	void deleteByOrderCode(String orderCode);

	void updateById(Integer orderId,Integer status);

	List<Integer> findStatusByOwnId(Integer ownId);
}
