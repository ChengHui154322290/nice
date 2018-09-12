package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.Alternative;
import com.nice.miniprogram.vo.AlternativeVo;

import java.util.List;
import java.util.Map;

public interface AlternativeMapper extends CoreMapper<Alternative> {


	List<AlternativeVo> findByParams(Map<String,Object> params);

	Alternative getOneByParams(Map<String, Object> params);
}