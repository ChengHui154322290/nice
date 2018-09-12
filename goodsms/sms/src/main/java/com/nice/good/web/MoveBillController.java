package com.nice.good.web;

import com.nice.good.aop.LogAnnotation;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.model.*;
import com.nice.good.service.MoveBillDetailService;
import com.nice.good.service.MoveBillService;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.service.SeatStockService;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.vo.CodeAndDateVo;
import com.nice.good.vo.MoveBillVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/06/05.
 */
@RestController
@RequestMapping("/move/bill")
public class MoveBillController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(MoveBillController.class);


    @Resource
    private MoveBillService moveBillService;
    @Resource
    private MoveBillDetailService moveBillDetailService;


    /**
     * 查询 移动单--移动明细单信息   -- 2018/06/08  15:36  rk
     *
     * @param moveBillId
     * @return
     */
    @PostMapping("/listMoveMsg")
    public Result listMoveMsg(@RequestParam String moveBillId) {

        MoveBill moveBill = null;

        try {
            // 通过 moveBillId 查询 MoveBill 类信息
            moveBill = moveBillService.findById(moveBillId);
            // 通过 moveBillId 查询 MoveBillDetail 类信息
            List<MoveBillDetail> moveBillDetails = moveBillDetailService.selectMoveBillDetailByMoveBillId(moveBillId);
            if (moveBillDetails != null && moveBillDetails.size() > 0) {
                moveBill.setMoveBillDetails(moveBillDetails);
            }

        } catch (Exception e) {
            log.error("查询移动单--移动明细单信息异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(moveBill);
    }

    /**
     * 新增移动单、批量新增移动明细单   -- 2018/06/07  09:57  rk
     *
     * @param moveBill
     * @param request
     * @return
     */
    @LogAnnotation(logType = "移动单日志", content = "移动单新增")
    @PostMapping("/add")
    public Result add(@RequestBody MoveBill moveBill, HttpServletRequest request) {
        if (moveBill == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);
        String placeId = getPlaceId(request);

        try {

            String errorMsg = moveBillService.moveBillSave(moveBill, placeId, userId);

            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 批量删除移动单信息   --  2018/06/05  rk
     *
     * @param moveBillIds
     * @return
     */
    @LogAnnotation(logType = "移动单日志", content = "移动单删除")
    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> moveBillIds) {
        if (moveBillIds == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }
        if (StringUtils.isBlank(moveBillIds.toString())) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }

        String errorMsg = "";

        try {

            for (String moveBillId : moveBillIds) {

                // 通过 moveBillId 查询表 k_move_bill 中的 move_status 值， move_status的值为0的可删除
                MoveBill moveBill = moveBillService.findById(moveBillId);
                if (moveBill == null) {
                    errorMsg += "第" + moveBillId + " 条记录已被删除!\n";
                    continue;
                }

                if (moveBill.getMoveStatus() != 0) {
                    errorMsg += "第" + moveBillId + " 条记录单据状态不对,删除失败!\n";
                    continue;
                }

                moveBillService.deleteById(moveBillId);
            }

        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        if (StringUtils.isNotBlank(errorMsg)) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 更新移动单、批量更新移动明细单   -- 2018/06/09  10:46  rk
     *
     * @param moveBillDetails
     * @param request
     * @return
     */
    @LogAnnotation(logType = "移动单日志", content = "确认移动")
    @PostMapping("/update")
    public Result update(@RequestBody List<MoveBillDetail> moveBillDetails, HttpServletRequest request) throws Exception {
        if (moveBillDetails == null || moveBillDetails.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {

            String userId = getUserName(request);

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = moveBillService.updateMoveBillDetails(moveBillDetails, placeId, userId);

            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }


        return ResultGenerator.genSuccessResult();
    }


    /**
     * 批量更新 ‘移动单’   --  2018/06/21  11:13  rk
     *
     * @param moveBills
     * @param request
     * @return
     */
    @LogAnnotation(logType = "移动单日志", content = "确认移动")
    @PostMapping("/updateMoveBill")
    public Result updateMoveBill(@RequestBody List<MoveBill> moveBills, HttpServletRequest request) {

        try {

            if (moveBills == null) {
                return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
            }

            String userId = getUserName(request);

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = moveBillService.updateMoveBill(moveBills, placeId, userId);

            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }


        return ResultGenerator.genSuccessResult();
    }


    /**
     * 根据查询条件， 查询出 移动单 信息   -- 2018/06/05 9:54  rk
     *
     * @param moveBillVo
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody MoveBillVo moveBillVo, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {


        PageHelper.startPage(page, size);

        PageInfo pageInfo;

        try {

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }


            // 移动单编号
            String moveBillId = moveBillVo.getMoveBillId();
            // 移动状态
            Integer moveStatus = moveBillVo.getMoveStatus();
            // 移动类型
            Integer moveType = moveBillVo.getMoveType();
            // 说明
            String remark = moveBillVo.getRemark();
            // 创建人
            String createId = moveBillVo.getCreateId();
            // 创建开始时间
            Date createDateStart = moveBillVo.getCreateDateStart();
            // 创建结束时间
            Date createDateEnd = moveBillVo.getCreateDateEnd();
            // 修改人
            String modifyId = moveBillVo.getModifyId();
            // 修改开始时间
            Date modifyDateStart = moveBillVo.getModifyDateStart();
            // 修改结束时间
            Date modifyDateEnd = moveBillVo.getModifyDateEnd();
            // 来源库位
            String sourceSeat = moveBillVo.getSourceSeat();
            // 目标库位
            String targetSeat = moveBillVo.getTargetSeat();


            List<MoveBill> list = moveBillService.findMoveBill(moveBillId, moveStatus, moveType, remark, createId, createDateStart, createDateEnd, modifyId, modifyDateStart, modifyDateEnd, sourceSeat, targetSeat, placeId);


            pageInfo = new PageInfo(list);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 移动单号的规则：自动生成  （YD+8位日期+6位流水）示例：YD20150202124455
     *
     * @return
     */
    @PostMapping("/getMoveId")
    public Result getMoveIdAndDate(HttpServletRequest request) {

        String userId = getUserName(request);

        CodeAndDateVo codeAndDateVo = moveBillService.getMoveIdAndDate(userId);

        return ResultGenerator.genSuccessResult().setData(codeAndDateVo);

    }


}
