package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.OrderGoodMapping;
import com.nice.good.service.OrderGoodMappingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

;

/**
* Created by CodeGenerator on 2018/03/26.
*/
@RestController
@RequestMapping("/order/good/mapping")
public class OrderGoodMappingController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(OrderGoodMappingController.class);

    @Resource
    private OrderGoodMappingService orderGoodMappingService;

    @PostMapping("/add")
    public Result add(@RequestBody OrderGoodMapping orderGoodMapping, HttpServletRequest request) {
    	if(orderGoodMapping == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);

    	try {
    		
    		orderGoodMappingService.orderGoodMappingAdd(orderGoodMapping,userId);
    

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult();
    }




    @PostMapping("/update")
    public Result update(@RequestBody OrderGoodMapping orderGoodMapping,HttpServletRequest request) {

    	if(orderGoodMapping == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);

    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		orderGoodMappingService.orderGoodMappingUpdate(orderGoodMapping, userId);

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id,String userId) {
    	if(id == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}
    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	OrderGoodMapping orderGoodMapping = null;
    	try {
    		orderGoodMapping = orderGoodMappingService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult(orderGoodMapping);
    }

    @PostMapping("/list")
    public Result list(@RequestBody OrderGoodMapping orderGoodMapping, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,String userId) {
        if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(orderGoodMapping.getClass());
        Criteria criteria = condition.createCriteria();

		PageInfo pageInfo = null;
		try {
    		 List<OrderGoodMapping> list = orderGoodMappingService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
