package com.nice.good.web;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.ReceiveDetailTemp;
import com.nice.good.model.SysUser;
import com.nice.good.service.ReceiveDetailTempService;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.apache.commons.lang3.StringUtils;;

/**
* Created by CodeGenerator on 2018/04/05.
*/
@RestController
@RequestMapping("/receive/detail/temp")
public class ReceiveDetailTempController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(ReceiveDetailTempController.class);



    @Resource
    private ReceiveDetailTempService receiveDetailTempService;

    @PostMapping("/add")
    public Result add(@RequestBody ReceiveDetailTemp receiveDetailTemp, HttpServletRequest request) {
    	if(receiveDetailTemp == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);

    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		receiveDetailTempService.receiveDetailTempAdd(receiveDetailTemp,userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete/{detailId}")
    public Result delete(@PathVariable(value = "detailId") String detailId) {
    	if(detailId == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

    	try {
    		receiveDetailTempService.deleteById(detailId);
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody ReceiveDetailTemp receiveDetailTemp,HttpServletRequest request) {
    	if(receiveDetailTemp == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}
    	if(receiveDetailTemp.getId() == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}


        String userId = getUserName(request);

    	try {

    		receiveDetailTempService.receiveDetailTempUpdate(receiveDetailTemp,userId);

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
    	if(id == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

    	ReceiveDetailTemp receiveDetailTemp = null;
    	try {
    		receiveDetailTemp = receiveDetailTempService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult(receiveDetailTemp);
    }

    @PostMapping("/list")
    public Result list(@RequestBody ReceiveDetailTemp receiveDetailTemp, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(receiveDetailTemp.getClass());
        Criteria criteria = condition.createCriteria();

		PageInfo pageInfo = null;
		try {
    		 List<ReceiveDetailTemp> list = receiveDetailTempService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
