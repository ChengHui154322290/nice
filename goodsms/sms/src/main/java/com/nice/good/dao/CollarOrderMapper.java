package com.nice.good.dao;


import com.nice.good.core.Mapper;
import com.nice.good.wx_model.BookingSlipVo;
import com.nice.good.wx_model.CollarOrder;
import com.nice.good.wx_model.CollarOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CollarOrderMapper extends Mapper<CollarOrder> {

    List<CollarOrder> selectListByParams(Map<String, Object> params);

    Integer selectMaxId();

	CollarOrderVo selectCollar(String orderCode);

	void collarOrderUpdate(String orderCode);

	void collarOrderRepayUpdate(String orderCode);

	void deleteByOrderCode(String orderCode);

	void updateById(@Param(value = "id") Integer id, @Param(value = "status")Integer status);

	CollarOrder findCollarByOrderCode(String orderCode);

	List<Integer> findStatusByOwnId(Integer ownId);
}