package com.nice.good.web;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.GoodStyle;
import com.nice.good.service.GoodStyleService;

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
* Created by CodeGenerator on 2018/07/12.
*/
@RestController
@RequestMapping("/good/style")
public class GoodStyleController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(GoodStyleController.class);


    @Resource
    private GoodStyleService goodStyleService;

    @PostMapping("/add")
    public Result add(@RequestBody GoodStyle goodStyle,HttpServletRequest request) {
    	if(goodStyle == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}


         String userId = getUserName(request);

    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		goodStyleService.goodStyleAdd(goodStyle,userId);

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
    		goodStyleService.deleteById(id);
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody GoodStyle goodStyle,HttpServletRequest request) {
    	if(goodStyle == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);

    	if(goodStyle.getId() == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}
    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		goodStyleService.goodStyleUpdate(goodStyle,userId);

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
    	GoodStyle goodStyle = null;
    	try {
    		goodStyle = goodStyleService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult(goodStyle);
    }

    @PostMapping("/list")
    public Result list(@RequestBody GoodStyle goodStyle, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(goodStyle.getClass());
        Criteria criteria = condition.createCriteria();

		PageInfo pageInfo = null;
		try {
    		 List<GoodStyle> list = goodStyleService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
