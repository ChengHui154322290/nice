package com.nice.good.service.impl;


import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.CloudThermalMapper;
import com.nice.good.model.CloudThermal;
import com.nice.good.service.CloudThermalService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/04.
 */
@Service
@Transactional
public class CloudThermalServiceImpl extends AbstractService<CloudThermal> implements CloudThermalService {
	@Resource
	private CloudThermalMapper cloudThermalMapper;


	@Override
	public void cloudThermalAdd(CloudThermal cloudThermal, String userId) throws Exception {


		cloudThermal.setCloudId(IdsUtils.getOrderId());
		cloudThermal.setCreateId(userId);
		cloudThermal.setModifier(userId);
		cloudThermal.setCreateDate(TimeStampUtils.getTimeStamp());
		cloudThermal.setModifytime(TimeStampUtils.getTimeStamp());

		cloudThermalMapper.insert(cloudThermal);

	}


	@Override
	public void cloudThermalUpdate(CloudThermal cloudThermal, String userId) {

		cloudThermalMapper.updateByPrimaryKey(cloudThermal);
	}

	@Override
	public void deleteByCloudId(List<String> cloudIds) {
		for (String cloudId : cloudIds) {

			cloudThermalMapper.deleteByPrimaryKey(cloudId);
		}
	}

}
