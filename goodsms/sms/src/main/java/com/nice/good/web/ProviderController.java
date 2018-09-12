package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.Provider;
import com.nice.good.service.ProviderService;
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
 * @Description: 供应商档案
 * @Author: fqs
 * @Date: 2018/3/23 10:31
 * @Version: 1.0
 */
@RestController
@RequestMapping("/provider")
public class ProviderController extends BaseController{

    private static Logger log = LoggerFactory.getLogger(ProviderController.class);

    @Resource
    private ProviderService providerService;

    @LogAnnotation(logType = "其他日志",content = "供应商档案新增")
    @PostMapping("/add")
    public Result add(@RequestBody Provider provider, HttpServletRequest request) {
        if (provider == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            String errorMsg = providerService.providerAdd(provider, userId);
            if (StringUtils.isNotBlank(errorMsg)) {
              return  ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 供应商删除
     * @param providerIds
     * @return
     */
    @LogAnnotation(logType = "其他日志",content = "供应商档案删除")
    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> providerIds) {

        if (providerIds == null || providerIds.size()==0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {
            String errorMsg =  providerService.deleteByProviderId(providerIds);

            if (StringUtils.isNotBlank(errorMsg)) {
               return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }



    /**
     * 供应商模糊查询
     * @param provider
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody Provider provider, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(provider.getClass());

        /**
         * 根据id倒序排列
         */
        condition.orderBy("id").desc();

        Criteria criteria = condition.createCriteria();
        if (StringUtils.isNotBlank(provider.getProviderCode())){
            criteria.andLike("providerCode","%"+provider.getProviderCode()+"%");
        }

        if (StringUtils.isNotBlank(provider.getProviderName())){
            criteria.andLike("providerName","%"+provider.getProviderName()+"%");
        }
        if (StringUtils.isNotBlank(provider.getProviderLevel())){
            criteria.andLike("providerLevel","%"+provider.getProviderLevel()+"%");
        }
        if (StringUtils.isNotBlank(provider.getStatus()+"")){
            criteria.andEqualTo("status",provider.getStatus());
        }

        PageInfo pageInfo = null;
        try {
            List<Provider> list = providerService.findByCondition(condition);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
