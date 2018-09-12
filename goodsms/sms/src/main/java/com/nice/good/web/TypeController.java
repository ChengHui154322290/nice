package com.nice.good.web;

import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.Type;
import com.nice.good.service.TypeService;

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

import javax.servlet.http.HttpServletRequest;

import javax.annotation.Resource;
import java.util.List;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/07/23.
 */
@RestController
@RequestMapping("/type")
public class TypeController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(TypeController.class);


	@Resource
	private TypeService typeService;


	/**
	 * 类型维护新增
	 *
	 * @param type
	 * @param request
	 * @return
	 */
	@PostMapping("/add")
	public Result add(@RequestBody Type type, HttpServletRequest request) {
		if (type == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		String userId = getUserName(request);

		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		try {

			String errorMsg = typeService.typeAdd(type, userId);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}
		} catch (Exception e) {
			log.error("新增对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/delete")
	public Result delete(@RequestParam String id) {
		if (id == null) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}
		if (StringUtils.isBlank(id)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		try {
			typeService.deleteById(id);
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/update")
	public Result update(@RequestBody Type type, HttpServletRequest request) {
		if (type == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		String userId = getUserName(request);

		if (type.getId() == null) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}
		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		try {

			String errorMsg =typeService.typeUpdate(type, userId);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}
		} catch (Exception e) {
			log.error("更新对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/detail")
	public Result detail(@RequestParam String id, HttpServletRequest request) {
		if (id == null) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}

		String userId = getUserName(request);


		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		Type type = null;
		try {
			type = typeService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult(type);
	}

	/**
	 * 类型维护查询
	 *
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/list")
	public Result list(@RequestBody Type type, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

		PageHelper.startPage(page, size);

		Condition condition = new Condition(type.getClass());
		Criteria criteria = condition.createCriteria();
		if (StringUtils.isNotBlank(type.getTypeCode())) {
			criteria.andLike("typeCode", "%" + type.getTypeCode() + "%");
		}
		if (StringUtils.isNotBlank(type.getTypeName())) {
			criteria.andLike("typeName", "%" + type.getTypeName() + "%");
		}

		PageInfo pageInfo = null;
		try {
			List<Type> list = typeService.findByCondition(condition);
			pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult(pageInfo);
	}
}
