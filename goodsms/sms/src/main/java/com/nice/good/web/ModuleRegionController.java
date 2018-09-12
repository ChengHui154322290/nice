package com.nice.good.web;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.ModuleRegion;
import com.nice.good.service.ModuleRegionService;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

import javax.annotation.Resource;
import java.util.List;
import org.apache.commons.lang3.StringUtils;;

/**
* Created by CodeGenerator on 2018/05/16.
*/
@RestController
@RequestMapping("/module/region")
public class ModuleRegionController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(ModuleRegionController.class);


    @Resource
    private ModuleRegionService moduleRegionService;


	/**
	 * 省市区查询(三级联动)
	 * @param
	 * @return
	 */
	@PostMapping("/list")
    public List<ModuleRegion> listAll(@RequestParam(defaultValue = "100000") String id) {
		List<ModuleRegion> list = moduleRegionService.findRegion(id);
        return list;
    }
}
