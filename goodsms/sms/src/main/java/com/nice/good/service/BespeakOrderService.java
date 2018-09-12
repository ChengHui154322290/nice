package com.nice.good.service;
import com.nice.good.wx_model.BespeakOrder;
import com.nice.good.core.Service;
import com.nice.good.wx_model.BespeakOrderVo;
import com.nice.good.wx_model.BookingSlipVo;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/08/03.
 */
public interface BespeakOrderService extends Service<BespeakOrder> {
      void bespeakOrderAdd(BespeakOrder bespeakOrder,String userId);
      void bespeakOrderUpdate(BespeakOrder bespeakOrder,String userId);

	Integer selectMaxId();

	List<BookingSlipVo> selectBespeakAll( BookingSlipVo bookingSlipVo);

	List<BookingSlipVo> selectCollarAll(BookingSlipVo bookingSlipVo);

	BespeakOrderVo selectBespeak(String orderCode);

	void deleteByOrderCode(String orderCode);

	BespeakOrder getByOrderCode(String  orderCode);


	List<Integer> findStatusByOwnId(Integer ownId);
}
