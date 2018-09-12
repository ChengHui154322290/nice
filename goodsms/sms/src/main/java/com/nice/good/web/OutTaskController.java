package com.nice.good.web;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.OutTask;
import com.nice.good.service.OutTaskService;

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
* Created by CodeGenerator on 2018/06/27.
*/
@RestController
@RequestMapping("/out/task")
public class OutTaskController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(OutTaskController.class);


    @Resource
    private OutTaskService outTaskService;

    @PostMapping("/add")
    public Result add(@RequestBody OutTask outTask,HttpServletRequest request) {
    	if(outTask == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}


         String userId = getUserName(request);

    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		outTaskService.outTaskAdd(outTask,userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
    	if(id == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}
    	if(StringUtils.isBlank(id)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {
    		outTaskService.deleteById(id);
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody OutTask outTask,HttpServletRequest request) {
    	if(outTask == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);

    	if(outTask.getId() == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}
    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		outTaskService.outTaskUpdate(outTask,userId);

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
    	OutTask outTask = null;
    	try {
    		outTask = outTaskService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult(outTask);
    }

    @PostMapping("/list")
    public Result list(@RequestBody OutTask outTask, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(outTask.getClass());
        Criteria criteria = condition.createCriteria();

		PageInfo pageInfo = null;
		try {
    		 List<OutTask> list = outTaskService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
