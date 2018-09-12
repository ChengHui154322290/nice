package com.nice.good.web;

import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.OperateLog;
import com.nice.good.service.OperateLogService;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/06/11.
 */
@RestController
@RequestMapping("/operate/log")
public class OperateLogController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(OperateLogController.class);


    @Resource
    private OperateLogService operateLogService;

    @PostMapping("/add")
    public Result add(@RequestBody OperateLog operateLog, HttpServletRequest request) {
        if (operateLog == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }


        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            operateLogService.operateLogAdd(operateLog, userId);

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
            operateLogService.deleteById(id);
        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody OperateLog operateLog, HttpServletRequest request) {
        if (operateLog == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        if (operateLog.getId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }
        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            operateLogService.operateLogUpdate(operateLog, userId);

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
        OperateLog operateLog = null;
        try {
            operateLog = operateLogService.findById(id);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult(operateLog);
    }

    @PostMapping("/list")
    public Result list(@RequestBody OperateLog operateLog,@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(operateLog.getClass());

        condition.orderBy("id").desc();

        Criteria criteria = condition.createCriteria();

        //操作开始时间
        Date modifyTimeStart = null;
        //操作结束时间
        Date modifyTimeEnd = null;
        //日志类型
        String logType = null;
        //相关单据
        String relativeInvoice = null;
        //关键字
        String content = null;

        if (operateLog != null) {

            modifyTimeStart = operateLog.getModifyTimeStart();

            modifyTimeEnd = operateLog.getModifyTimeEnd();
            if (modifyTimeStart != null && modifyTimeEnd != null) {
                criteria.andBetween("modifyTime", modifyTimeStart, modifyTimeEnd);
            }
            logType = operateLog.getLogType();
            if (StringUtils.isNotBlank(logType)) {
                criteria.andEqualTo("logType", logType);
            }

            relativeInvoice = operateLog.getRelativeInvoice();
            if (StringUtils.isNotBlank(relativeInvoice)) {
                criteria.andLike("relativeInvoice", "%" + relativeInvoice + "%");
            }

            content = operateLog.getContent();
            if (StringUtils.isNotBlank(content)) {

                criteria.andLike("content", "%" + content + "%");
            }


            Calendar ca = Calendar.getInstance();
            ca.add(Calendar.DAY_OF_MONTH, -7);
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            criteria.andGreaterThanOrEqualTo("modifyTime",sdf.format(ca.getTime()));


        }

        PageInfo pageInfo = null;
        try {
            List<OperateLog> list = operateLogService.findByCondition(condition);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
