package com.nice.miniprogram.service;
import com.alibaba.fastjson.JSONArray;
import com.nice.miniprogram.model.Alternative;
import com.nice.miniprogram.core.Service;
import com.nice.miniprogram.vo.AlternativeVo;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/06/08.
 */
public interface AlternativeService extends Service<Alternative> {

	void alternativeAdd(Alternative alternative);

	List<AlternativeVo> findByParams(Map<String,Object> params);

	void deleteByOwnIds(List<Integer> ids);

	Alternative getOneByParams(Map<String,Object> params);
}
