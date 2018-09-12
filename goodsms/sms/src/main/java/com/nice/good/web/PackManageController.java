package com.nice.good.web;

import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.PackManage;
import com.nice.good.service.PackManageService;

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
 * Created by CodeGenerator on 2018/04/15.
 */
@RestController
@RequestMapping("/pack/manage")
public class PackManageController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(PackManageController.class);


    @Resource
    private PackManageService packManageService;

    @LogAnnotation(logType = "其他日志", content = "包装管理新增")
    @PostMapping("/add")
    public Result add(@RequestBody PackManage packManage, HttpServletRequest request) {
        if (packManage == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        try {

            String errorMsg = packManageService.packManageAdd(packManage,placeId, userId);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 包装删除操作
     *
     * @param packIds
     * @return
     */
    @LogAnnotation(logType = "其他日志", content = "包装管理删除")
    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> packIds) {

        if (packIds == null || packIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {
            String errorMsg = packManageService.deleteByPackId(packIds);
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
     * 包装管理模糊查询
     *
     * @param packManage
     * @param page
     * @param size
     * @return
     */

    @PostMapping("/list")
    public Result list(@RequestBody PackManage packManage, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,HttpServletRequest request) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(packManage.getClass());
        Criteria criteria = condition.createCriteria();


        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        if (StringUtils.isNotBlank(packManage.getPackCode())) {
            criteria.andLike("packCode", "%" + packManage.getPackCode() + "%");
        }

        if (StringUtils.isNotBlank(packManage.getPackName())) {
            criteria.andLike("packName", "%" + packManage.getPackName() + "%");
        }

        if (StringUtils.isNotBlank(packManage.getStatement())) {
            criteria.andLike("statement", "%" + packManage.getStatement() + "%");
        }

        if (StringUtils.isNotBlank(packManage.getStatus() + "")) {
            criteria.andEqualTo("status", packManage.getStatus());
        }

        criteria.andEqualTo("placeId",placeId);

        PageInfo pageInfo = null;
        try {
            List<PackManage> list = packManageService.findByCondition(condition);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
