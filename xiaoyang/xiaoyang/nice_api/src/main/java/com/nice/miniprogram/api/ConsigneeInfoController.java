package com.nice.miniprogram.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.enums.ResultCode;
import com.nice.miniprogram.model.ConsigneeInfo;
import com.nice.miniprogram.service.ConsigneeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/19.
 */
//@Api(value = "收货人地址接口",description = "收货人地址相关操作")
//@RestController
//@RequestMapping("/consignee/info")
public class ConsigneeInfoController {

	private static Logger log = LoggerFactory.getLogger(ConsigneeInfoController.class);


	@Resource
	private ConsigneeInfoService consigneeInfoService;

	/**
	 * 收货人新增
	 */
	@ApiOperation(value = "新增收货人", httpMethod = "POST")
	@PostMapping("/add")
	public Result add(@RequestBody ConsigneeInfo consigneeInfo, HttpServletRequest request) {
		log.info("================================== 新增收货人 ==================================");
		if (consigneeInfo == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		Date current = new Date();
		consigneeInfo.setCreater("");
		consigneeInfo.setCreatetime(current);
		consigneeInfo.setModifier("");
		consigneeInfo.setModifytime(current);
		consigneeInfoService.consigneeInfoAdd(consigneeInfo);
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 查看收货人列表
	 */
	@ApiOperation(value = "收货人列表", httpMethod = "GET")
	@GetMapping("/list")
	public Result list(@RequestParam int ownId, HttpServletRequest request) {
		log.info("================================== 查看收货人列表 ==================================");
		List<ConsigneeInfo> list = consigneeInfoService.findByOwbId(ownId);
		return ResultGenerator.genSuccessResult(list);
	}

	/**
	 * 查看收货人信息详情
	 */
	@ApiOperation(value = "收货人信息明细", httpMethod = "GET")
	@GetMapping("/getConsignee")
	public Result getConsignee(@ApiParam(value = "联系人id") @RequestParam(value = "id") int id, HttpServletRequest request) {
		log.info("================================== 查看收货人信息明细 ==================================");
		ConsigneeInfo consigneeInfo = consigneeInfoService.selectGetConsignee(id);
		return ResultGenerator.genSuccessResult(consigneeInfo);
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "批量删除收货人信息", httpMethod = "POST")
	@ApiImplicitParam(paramType = "query",name = "ids",dataType = "List" ,required = true,value = "选择的条目的id")
	@PostMapping("/delete")
	public Result delete(@RequestParam List<String> ids) {
		log.info("================================== 批量删除收货人信息 ==================================");
//		JSONArray objects = JSONObject.parseArray(ids);
//		if (objects == null || objects.size() == 0) {
//			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
//		}
		consigneeInfoService.deleteByIds(ids);
		return ResultGenerator.genSuccessResult(ResultCode.SUCCESS);
	}

	/**
	 * 修改操作
	 */
	@ApiOperation(value = "修改收货人信息", httpMethod = "POST")
	@PostMapping("/update")
	public Result update(@RequestBody ConsigneeInfo consigneeInfo, HttpServletRequest request) {
		log.info("================================== 修改收货人信息 ==================================");
		if (consigneeInfo == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		Date current = new Date();
		consigneeInfo.setCreater("");
		consigneeInfo.setCreatetime(current);
		consigneeInfo.setModifier("");
		consigneeInfo.setModifytime(current);
		consigneeInfoService.update(consigneeInfo);
		return ResultGenerator.genSuccessResult();
	}
}
