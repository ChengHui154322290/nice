package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.wx_model.BespeakOrder;
import com.nice.good.wx_model.BespeakOrderVo;
import com.nice.good.wx_model.BookingSlipVo;

import java.util.List;
import java.util.Map;

public interface BespeakOrderMapper extends Mapper<BespeakOrder> {
	List<BookingSlipVo> selectBespeakAll(BookingSlipVo bookingSlipVo);

	Integer selectMaxId();

	List<BookingSlipVo> selectCollarAll(BookingSlipVo bookingSlipVo);

	 BespeakOrderVo selectBespeak(String orderCode);

	void deleteByOrderCode(String orderCode);

	BespeakOrder getByOrderCode(String orderCode);

	List<Integer> findStatusByOwnId(Integer ownId);
}