package com.nice.good.web;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.TypeDetail;
import com.nice.good.service.TypeDetailService;

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
@RequestMapping("/type/detail")
public class TypeDetailController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(TypeDetailController.class);


    @Resource
    private TypeDetailService typeDetailService;

    @PostMapping("/add")
    public Result add(@RequestBody TypeDetail typeDetail,HttpServletRequest request) {
    	if(typeDetail == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}


         String userId = getUserName(request);

    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		typeDetailService.typeDetailAdd(typeDetail,userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult();
    }

	/**
	 * 修改删除
	 * @param typeDetail
	 * @param request
	 * @return
	 */
	@PostMapping("/delete")
    public Result delete(@RequestBody TypeDetail typeDetail ,HttpServletRequest request) {
		if (typeDetail == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		String userId = getUserName(request);
		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		String typeCode = typeDetail.getTypeCode();
		if(StringUtils.isBlank(typeCode)|| typeCode==null){
			return ResultGenerator.genFailResult("请选择要操作的选项");
		}
		String detailCode = typeDetail.getDetailCode();
		if(StringUtils.isBlank(detailCode)|| detailCode==null){
			return ResultGenerator.genFailResult("请选择要操作的选项");
		}
    	try {
			String errorMsg =	typeDetailService.deleteByDetail(typeDetail);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody TypeDetail typeDetail,HttpServletRequest request) {
    	if(typeDetail == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);

    	if(typeDetail.getId() == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}
    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		typeDetailService.typeDetailUpdate(typeDetail,userId);

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }


	/**
	 * 修改查询
	 * @param typeCode
	 * @param request
	 * @return
	 */
    @PostMapping("/list")
    public Result detail(@RequestParam String typeCode,HttpServletRequest request) {
    	if(typeCode == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

        String userId = getUserName(request);


    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	TypeDetail typeDetail = null;
    	try {
    		typeDetail = typeDetailService.findByTypeCode(typeCode);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult(typeDetail);
    }

}
