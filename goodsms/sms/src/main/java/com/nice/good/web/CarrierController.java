package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.Carrier;
import com.nice.good.model.Gooder;
import com.nice.good.service.CarrierService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

;

/**
 * @Description: 承运商档案
 * @Author: fqs
 * @Date: 2018/3/23 10:43
 * @Version: 1.0
 */
@RestController
@RequestMapping("/carrier")
public class CarrierController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(CarrierController.class);

    @Resource
    private CarrierService carrierService;

    @LogAnnotation(logType = "其他日志", content = "新增承运商")
    @PostMapping("/add")
    public Result add(@RequestBody Carrier carrier, HttpServletRequest request) {
        if (carrier == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);


        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            String errorMsg = carrierService.carrierAdd(carrier, userId);
            if (errorMsg != null) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);

            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 承运商删除
     *
     * @param carrierIds
     * @return
     */
    @LogAnnotation(logType = "其他日志", content = "承运商删除")
    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> carrierIds) {

        if (carrierIds == null || carrierIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {
            String errorMsg = carrierService.deleteByCarrierId(carrierIds);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);

            }

        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/list")
    public Result list(@RequestBody Carrier carrier, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(carrier.getClass());

        condition.orderBy("id").desc();

        Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotBlank(carrier.getCarrierName())) {
            criteria.andLike("carrierName", "%" + carrier.getCarrierName() + "%");
        }

        if (StringUtils.isNotBlank(carrier.getCarrierCode())) {
            criteria.andLike("carrierCode", "%" + carrier.getCarrierCode() + "%");
        }


        PageInfo pageInfo = null;
        try {
            List<Carrier> list = carrierService.findByCondition(condition);


            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
