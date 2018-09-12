package com.nice.miniprogram.service;
import com.alibaba.fastjson.JSONArray;
import com.nice.miniprogram.model.ConsigneeInfo;
import com.nice.miniprogram.core.Service;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/06/19.
 */
public interface ConsigneeInfoService extends Service<ConsigneeInfo> {

	void consigneeInfoAdd(ConsigneeInfo consigneeInfo);

	List<ConsigneeInfo> findByOwbId(int ownId);

	ConsigneeInfo  selectGetConsignee(int id);

	void deleteByIds(List<String> ids);
}
