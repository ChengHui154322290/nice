package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.TypeDetailMapper;
import com.nice.good.model.TypeDetail;
import com.nice.good.service.TypeDetailService;
import com.nice.good.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/07/23.
 */
@Service
@Transactional
public class TypeDetailServiceImpl extends AbstractService<TypeDetail> implements TypeDetailService {
	@Resource
	private TypeDetailMapper typeDetailMapper;

	@Override
	public void typeDetailAdd(TypeDetail typeDetail, String userId) {


		typeDetailMapper.insert(typeDetail);

	}


	@Override
	public void typeDetailUpdate(TypeDetail typeDetail, String userId) {

		typeDetailMapper.updateByPrimaryKey(typeDetail);
	}

	@Override
	public TypeDetail findByTypeCode(String typeCode) {
		return typeDetailMapper.findByTypeCode(typeCode);
	}

	@Override
	public String deleteByDetail(TypeDetail typeDetail) {
		String errorMsg = "";
		String typeCode = typeDetail.getTypeCode();
		String detailCode = typeDetail.getDetailCode();
		List<String> list = new ArrayList<>();
		//场地类型
		if (typeCode.equals("CDLX")) {
			list = typeDetailMapper.findPlaceNumber(detailCode);
		} else if (typeCode.equals("YHLX")) {
			//用户类型
			list = typeDetailMapper.findSysUser(detailCode);

		} else if (typeCode.equals("KQLX")) {
			//库区类型
			list = typeDetailMapper.findStoreArea(detailCode);

		} else if (typeCode.equals("KWLX")) {
			//库位类型
			list = typeDetailMapper.findStoreSeat(detailCode);

		} else if (typeCode.equals("FGLX")) {
			//风格类型
			list =typeDetailMapper.findStyle(detailCode);

		} else if (typeCode.equals("LMLX")) {
			//类目类型,和风格的查询一样
			list =typeDetailMapper.findStyle(detailCode);

		} else if (typeCode.equals("DHGZ")) {
			//单号规则
			list =typeDetailMapper.findCarrier(detailCode);

		} else if (typeCode.equals("CGLX")) {
			//采购类型
			list =typeDetailMapper.findOrder(detailCode);

		} else if (typeCode.equals("SHLX")) {
			//收货类型
			list =typeDetailMapper.findReceiveType(detailCode);

		} else if (typeCode.equals("DDLX")) {
			//订单类型
			list =typeDetailMapper.findOrderType(detailCode);

		} else if (typeCode.equals("PDLX")) {
			//盘点类型
			list =typeDetailMapper.findInventoryType(detailCode);

		} else if (typeCode.equals("TZLX")) {
			//调整类型
			list =typeDetailMapper.findAdjustType(detailCode);

		} else if (typeCode.equals("YDLX")) {
			//移动类型
			list =typeDetailMapper.findMoveType(detailCode);

		} else {
			errorMsg = "该类型尚未添加";
			return errorMsg;
		}
		if (list.size() > 0) {
			errorMsg = "该类型有关联不能删除";
			return errorMsg;
		}
		typeDetailMapper.delete(typeDetail);
		return errorMsg;

	}

}
