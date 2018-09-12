package com.nice.good.web;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.Measure;
import com.nice.good.service.MeasureService;

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
* Created by CodeGenerator on 2018/04/01.
*/
@RestController
@RequestMapping("/measure")
public class MeasureController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(MeasureController.class);



    @Resource
    private MeasureService measureService;

    @LogAnnotation(logType = "其他日志",content = "计量单位新增")
    @PostMapping("/add")
    public Result add(@RequestBody Measure measure, HttpServletRequest request) {
    	if(measure == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}


        String userId = getUserName(request);

    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		measureService.measureAdd(measure,userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete/{measureId}")
    public Result delete(@PathVariable(value = "measureId") String measureId) {
    	if(measureId == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

    	try {
    		measureService.deleteById(measureId);
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Measure measure,HttpServletRequest request) {
    	if(measure == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}
    	if(measure.getId() == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

    	try {

            String userId = getUserName(request);

    		measureService.measureUpdate(measure,userId);

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
    	Measure measure = null;
    	try {
    		measure = measureService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult(measure);
    }

	/**
	 * 计量单位查询
	 * @param measure
	 * @param page
	 * @param size
	 * @return
	 */

	@PostMapping("/list")
    public Result list(@RequestBody Measure measure, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(measure.getClass());

        condition.orderBy("id").desc();

        Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotBlank(measure.getmGroupCode())){
            criteria.andLike("mGroupCode","%"+measure.getmGroupCode()+"%");
        }
        if (StringUtils.isNotBlank(measure.getmGroupName())){
            criteria.andLike("mGroupName","%"+measure.getmGroupName()+"%");
        }

        if (StringUtils.isNotBlank(measure.getStatement())){
            criteria.andLike("statement","%"+measure.getStatement()+"%");
        }

		PageInfo pageInfo = null;
		try {
    		 List<Measure> list = measureService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
