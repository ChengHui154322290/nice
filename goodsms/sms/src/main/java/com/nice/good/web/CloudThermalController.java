package com.nice.good.web;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.CloudThermal;
import com.nice.good.service.CloudThermalService;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;

import javax.annotation.Resource;
import java.util.List;
import org.apache.commons.lang3.StringUtils;;

/**
* Created by CodeGenerator on 2018/06/04.
*/
@RestController
@RequestMapping("/cloud/thermal")
public class CloudThermalController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(CloudThermalController.class);


    @Resource
    private CloudThermalService cloudThermalService;

    @PostMapping("/add")
    public Result add(@RequestBody CloudThermal cloudThermal,HttpServletRequest request) {
    	if(cloudThermal == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}


         String userId = getUserName(request);

    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		cloudThermalService.cloudThermalAdd(cloudThermal,userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/delete")
    public Result delete(@RequestParam  List<String> cloudIds) {
    	if(cloudIds == null || cloudIds.size()<1){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}
    	try {
    		cloudThermalService.deleteByCloudId(cloudIds);
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody CloudThermal cloudThermal,HttpServletRequest request) {
    	if(cloudThermal == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);

    	if(cloudThermal.getId() == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}
    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		cloudThermalService.cloudThermalUpdate(cloudThermal,userId);

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id,HttpServletRequest request) {
    	if(id == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

        String userId = getUserName(request);


    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	CloudThermal cloudThermal = null;
    	try {
    		cloudThermal = cloudThermalService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult(cloudThermal);
    }

    @PostMapping("/list")
    public Result list(@RequestBody CloudThermal cloudThermal, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(cloudThermal.getClass());
        Criteria criteria = condition.createCriteria();

		if (StringUtils.isNotBlank(cloudThermal.getGooderCode())) {
			criteria.andEqualTo("gooderCode",cloudThermal.getGooderCode());
		}
		if (StringUtils.isNotBlank(cloudThermal.getThermalCode())) {
			criteria.andLike("thermalCode", "%" + cloudThermal.getThermalCode() + "%");
		}
		if (StringUtils.isNotBlank(cloudThermal.getPlaceNumber())) {
			criteria.andLike("placeNumber", "%" +cloudThermal.getPlaceNumber() + "%");
		}
		if (StringUtils.isNotBlank(cloudThermal.getCarrierCode())) {
			criteria.andLike("carrierCode", "%" + cloudThermal.getCarrierCode() + "%");
		}
		if (cloudThermal.getTaobaoStatus()!=null) {
			criteria.andLike("taobaoStatus", "%" + cloudThermal.getTaobaoStatus() + "%");
		}
		PageInfo pageInfo = null;
		try {
    		 List<CloudThermal> list = cloudThermalService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
