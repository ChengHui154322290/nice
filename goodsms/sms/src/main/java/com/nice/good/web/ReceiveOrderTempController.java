package com.nice.good.web;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.ReceiveOrder;
import com.nice.good.model.ReceiveOrderTemp;
import com.nice.good.service.ReceiveOrderTempService;

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
@RequestMapping("/receive/order/temp")
public class ReceiveOrderTempController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(ReceiveOrderTempController.class);


    @Resource
    private ReceiveOrderTempService receiveOrderTempService;

    @PostMapping("/add")
    public Result add(@RequestBody ReceiveOrder receiveOrder, HttpServletRequest request) {
    	if(receiveOrder == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);

    	try {

            String errorMsg = receiveOrderTempService.receiveOrderTempSave(receiveOrder,userId);
            if (StringUtils.isNotBlank(errorMsg)){
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete/{receiveId}")
    public Result delete(@PathVariable(value = "receiveId") String receiveId) {
    	if(receiveId == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

    	try {
    		receiveOrderTempService.deleteById(receiveId);
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }



    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
    	if(id == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

    	ReceiveOrderTemp receiveOrderTemp = null;
    	try {
    		receiveOrderTemp = receiveOrderTempService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult(receiveOrderTemp);
    }

    @PostMapping("/list")
    public Result list(@RequestBody ReceiveOrderTemp receiveOrderTemp, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(receiveOrderTemp.getClass());
        Criteria criteria = condition.createCriteria();

		PageInfo pageInfo = null;
		try {
    		 List<ReceiveOrderTemp> list = receiveOrderTempService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
