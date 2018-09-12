package com.nice.good.service.impl;


import com.nice.good.dao.TypeDetailMapper;
import com.nice.good.model.TypeDetail;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.TypeMapper;
import com.nice.good.model.Type;
import com.nice.good.service.TypeService;
import com.nice.good.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;



/**
 * Created by CodeGenerator on 2018/07/23.
 */
@Service
@Transactional
public class TypeServiceImpl extends AbstractService<Type> implements TypeService {
	@Resource
	private TypeMapper typeMapper;

	@Resource
	private TypeDetailMapper typeDetailMapper;

	@Override
	@Transactional
	public String typeAdd(Type type, String userId) throws Exception {
		String errorMsg = "";
		if (StringUtils.isBlank(type.getTypeCode())) {
			errorMsg = "类型编码不能为空";
			return errorMsg;
		}
		if (StringUtils.isBlank(type.getTypeName())) {
			errorMsg = "类型名称不能为空";
			return errorMsg;
		}
		List<String> typeCodes = typeMapper.selectTypeAll();
		List<String> detailCodes = typeDetailMapper.selectDetailAll();

		if (typeCodes.contains(type.getTypeCode())) {
			errorMsg = "类型编码已经存在";
			return errorMsg;
		}

		Timestamp timeStamp = TimeStampUtils.getTimeStamp();
		//主表新增
		String typeCode = type.getTypeCode();
		type.setCreater(userId);
		type.setCreatetime(timeStamp);
		type.setModifier(userId);
		type.setModifytime(timeStamp);
		String typeId = IdsUtils.getOrderId();
		type.setTypeId(typeId);
		typeMapper.insert(type);
		//明细表新增
		List<TypeDetail> details = type.getTypeDetails();
		for (TypeDetail detail : details) {
			if (StringUtils.isBlank(detail.getDetailCode())) {
				errorMsg = "类型明细编码不能为空";
				return errorMsg;
			}
			if (StringUtils.isBlank(detail.getDetailName())) {
				errorMsg = "类型明细名称不能为空";
				return errorMsg;
			}
			if (detailCodes.contains(detail.getDetailCode())) {
				errorMsg = "类型明细编码已经存在";
				return errorMsg;
			}
			detail.setTypeCode(typeCode);

			detail.setPreset(0);
			detail.setCreater(userId);
			detail.setCreatetime(timeStamp);
			detail.setModifier(userId);
			detail.setModifytime(timeStamp);
			detail.setDetailId(IdsUtils.getOrderId());
			typeDetailMapper.insert(detail);
		}

		return errorMsg;
	}

	@Override
	public String typeUpdate(Type type, String userId) {
		String typeCode = type.getTypeCode();
		String errorMsg="";
		if(StringUtils.isBlank(typeCode)|| typeCode==null){
			errorMsg = "请选择要操作的选项";
			return  errorMsg;
		}



		typeMapper.updateByPrimaryKey(type);
		return  null;
	}

}
