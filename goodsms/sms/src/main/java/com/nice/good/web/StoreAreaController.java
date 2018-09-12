package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.StoreAreaMapper;
import com.nice.good.dao.SysPlaceMapper;

import com.nice.good.enums.ResultCode;
import com.nice.good.model.StoreArea;

import com.nice.good.model.SysPlace;
import com.nice.good.service.StoreAreaService;
import com.nice.good.vo.PlaceAreaVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * @Description: 展厅档案-库区档案
 * @Author: fqs
 * @Date: 2018/3/23 10:32
 * @Version: 1.0
 */
@RestController
@RequestMapping("/store/area")
public class StoreAreaController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(StoreAreaController.class);


    @Resource
    private StoreAreaService storeAreaService;


    @Resource
    private StoreAreaMapper storeAreaMapper;


    @Resource
    private SysPlaceMapper sysPlaceMapper;


    @LogAnnotation(logType = "其他日志",content = "库区新增")
    @PostMapping("/add")
    public Result add(@RequestBody StoreArea storeArea, HttpServletRequest request) {


        try {
            if (storeArea == null) {
                return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
            }

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String userId = getUserName(request);

            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
            }

            String errorMsg = storeAreaService.storeAreaAdd(storeArea,placeId, userId);
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
     * 库区删除
     *
     * @param areaIds
     * @return
     */
    @LogAnnotation(logType = "其他日志",content = "库区新增")
    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> areaIds,HttpServletRequest request) {

        if (areaIds == null || areaIds.size()==0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        try {

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = storeAreaService.deleteByAreaId(areaIds,placeId);

            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }
        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody StoreArea storeArea, HttpServletRequest request) {

        try {
            if (storeArea == null) {
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

            String errorMsg = storeAreaService.storeAreaUpdate(storeArea,placeId, userId);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        if (id == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        StoreArea storeArea = null;
        try {
            storeArea = storeAreaService.findById(id);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult(storeArea);
    }


    @PostMapping("/list")
    public Result list(@RequestBody StoreArea storeArea, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,HttpServletRequest request) {



        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        PageHelper.startPage(page, size);


        /**
         * 需要获取外表字段 展厅编号
         */

        PageInfo pageInfo ;
        String placeNumber = null;
        String areaCode = null;
        String areaType = null;
        String areaName = null;

        try {
            if (storeArea != null) {

                placeNumber = storeArea.getPlaceNumber();
                areaCode = storeArea.getAreaCode();
                areaType = storeArea.getAreaType();
                areaName = storeArea.getAreaName();
            }

            List<PlaceAreaVo> list = storeAreaMapper.findByConditions(placeNumber,areaType,areaCode, areaName,placeId);

            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 展厅下拉选框
     */
    @PostMapping("/exhibitionList")
    public Result listExhibit(HttpServletRequest request) {

        String placeId = getPlaceId(request);

        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        SysPlace sysPlace = sysPlaceMapper.selectByPrimaryKey(placeId);

        String placeNumber=null;
        if (sysPlace!=null) {
            placeNumber = sysPlace.getPlaceNumber();
        }

        Map<String,String> map = new HashMap<>();

        map.put("placeNumber",placeNumber);

        return ResultGenerator.genSuccessResult(map);
    }

}
