package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.TypeDetail;

import java.util.List;

public interface TypeDetailMapper extends Mapper<TypeDetail> {
	List<String> selectDetailAll();

	TypeDetail findByTypeCode(String typeCode);

	List<String> findPlaceNumber(String detailCode);

	List<String> findSysUser(String detailCode);

	List<String> findStoreArea(String detailCode);

	List<String> findStoreSeat(String detailCode);

	List<String> findStyle(String detailCode);

	List<String> findCarrier(String detailCode);

	List<String> findOrder(String detailCode);

	List<String> findReceiveType(String detailCode);

	List<String> findOrderType(String detailCode);

	List<String> findInventoryType(String detailCode);

	List<String> findAdjustType(String detailCode);

	List<String> findMoveType(String detailCode);
}