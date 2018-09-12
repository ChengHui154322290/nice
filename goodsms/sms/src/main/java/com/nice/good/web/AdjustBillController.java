package com.nice.good.web;

import com.nice.good.aop.LogAnnotation;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.model.AdjustBill;
import com.nice.good.model.AdjustBillDetail;

import com.nice.good.service.*;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.vo.CodeAndDateVo;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/06/01.
 */
@RestController
@RequestMapping("/adjust/bill")
public class AdjustBillController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(AdjustBillController.class);

    @Resource
    private AdjustBillService adjustBillService;

    @Resource
    private AdjustBillDetailMapper adjustBillDetailMapper;


    /**
     * 查询 调整单--调整明细单信息   -- 2018/06/07  11:57  rk
     *
     * @param adjustBillId
     * @return
     */
    @PostMapping("/listAdjustMsg")
    public Result listAdjustMsg(@RequestParam String adjustBillId,HttpServletRequest request) {

        // 初始化 AdjustBillLis类
        AdjustBill adjustBill = null;

        try {

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            // 通过 adjustBillId 查询 AdjustBill 类信息
            adjustBill = adjustBillService.findById(adjustBillId);
            // 通过 adjustBillId 查询 AdjustBillDetail 类信息
            List<AdjustBillDetail> adjustBillDetails = adjustBillDetailMapper.selectAdjustBillDetailByAdjustBillId(adjustBillId);

            if (adjustBillDetails != null && adjustBillDetails.size() != 0) {
                adjustBill.setAdjustBillDetails(adjustBillDetails);
            }

        } catch (Exception e) {
            log.error("查询调整单--调整明细单信息异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(adjustBill);

    }

    /**
     * 新增 调整单 --- 调整明细单 信息    -- 2018/06/06  10:06  rk
     *
     * @param adjustBill
     * @return
     */
    @LogAnnotation(logType = "调整单日志", content = "调整单新增")
    @PostMapping("/add")
    public Result add(@RequestBody AdjustBill adjustBill, HttpServletRequest request) {

        try {

            if (adjustBill == null) {
                return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
            }
            String userId = getUserName(request);


            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = adjustBillService.saveAdjustBill(adjustBill, placeId, userId);

            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }


    @LogAnnotation(logType = "调整单日志", content = "调整单删除")
    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> adjustBillIds,HttpServletRequest request) {
        if (adjustBillIds == null || adjustBillIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        // 记录调整单删除信息
        String errorMsg = "";
        try {


            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            for (String billId : adjustBillIds) {
                // 通过 billId 查询 adjustStatus的值
                int status = adjustBillService.selectAdjustStatus(billId);

                if (status != 0) {
                    errorMsg += "第" + billId + "条记录单据状态不对,删除失败!\n";
                    continue;
                }

                // 删除 调整单 中对应的信息
                adjustBillService.deleteById(billId);
                // 批量删除 调整明细单 中对应的信息
                adjustBillDetailMapper.deleteDetailByAdjustBillId(billId);

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
     * 更新 调整单 --- 调整明细单 中的数据    -- 2018/06/07  15:00  rk
     *
     * @param adjustBillDetails
     * @param request
     * @return
     */
    @LogAnnotation(logType = "调整单日志", content = "确认调整")
    @PostMapping("/update")
    public Result update(@RequestBody List<AdjustBillDetail> adjustBillDetails, HttpServletRequest request) {
        if (adjustBillDetails == null || adjustBillDetails.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }


        try {

            String userId = getUserName(request);

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg =adjustBillService.updateAdjustBillDetails(adjustBillDetails,placeId,userId);

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
     * 批量更新 ‘调整单’   --  2018/06/20  11:14  rk
     *
     * @param adjustBills
     * @param request
     * @return
     */

    @LogAnnotation(logType = "调整单日志", content = "确认调整")
    @PostMapping("/updateAdjustBill")
    public Result updateAdjustBill(@RequestBody List<AdjustBill> adjustBills, HttpServletRequest request) {
        if (adjustBills == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }


        try {

            String errorMsg =adjustBillService.updateAdjustBill(adjustBills,placeId,userId);

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
     * 根据传递的参数， 查询 调整单 信息
     *
     * @param adjustBill
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody AdjustBill adjustBill, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        PageHelper.startPage(page, size);

        Condition condition = new Condition(adjustBill.getClass());

        //根据id倒序排序
        condition.orderBy("id").desc();

        Criteria criteria = condition.createCriteria();

        // 调整单号
        if (StringUtils.isNotBlank(adjustBill.getAdjustBillId())) {
            criteria.andLike("adjustBillId", "%" + adjustBill.getAdjustBillId() + "%");
        }
        // 来源单据
        if (StringUtils.isNotBlank(adjustBill.getSourceBillCode())) {
            criteria.andLike("sourceBillId", "%" + adjustBill.getSourceBillCode() + "%");
        }
        // 调整状态  -- 全匹配
        Integer adjustStatus = adjustBill.getAdjustStatus();
        if (adjustStatus != null && StringUtils.isNotBlank(adjustStatus.toString())) {
            criteria.andEqualTo("adjustStatus", adjustStatus);
        }
        // 调整类型  -- 全匹配
        Integer adjustType = adjustBill.getAdjustType();
        if (adjustType != null && StringUtils.isNotBlank(adjustType.toString())) {
            criteria.andEqualTo("adjustType", adjustType);
        }
        // 创建人
        if (StringUtils.isNotBlank(adjustBill.getCreateId())) {
            criteria.andLike("createId", "%" + adjustBill.getCreateId() + "%");
        }
        // 操作人
        if (StringUtils.isNotBlank(adjustBill.getModifyId())) {
            criteria.andLike("modifyId", "%" + adjustBill.getModifyId() + "%");
        }
        // 创建时间
        if (adjustBill.getCreateDateStart() != null && adjustBill.getCreateDateEnd() != null) {
            criteria.andBetween("createDate", adjustBill.getCreateDateStart(), adjustBill.getCreateDateEnd());
        }
        // 操作时间
        if (adjustBill.getModifyDateStart() != null && adjustBill.getModifyDateEnd() != null) {
            criteria.andBetween("modifyDate", adjustBill.getModifyDateStart(), adjustBill.getModifyDateEnd());
        }
        // 说明
        if (StringUtils.isNotBlank(adjustBill.getRemark())) {
            criteria.andLike("remark", "%" + adjustBill.getRemark() + "%");
        }

        criteria.andEqualTo("placeId", placeId);


        PageInfo pageInfo = null;
        try {
            List<AdjustBill> list = adjustBillService.findByCondition(condition);

            /**
             * lambda对日期进行排序
             */
            Collections.sort(list, (AdjustBill o1, AdjustBill o2) -> o2.getCreateDate().compareTo(o1.getCreateDate()));
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 查询货主编码    -- 2018/06/08  14:57  rk
     *
     * @return
     */
    @PostMapping("/listGooderCodes")
    public Result listGooderCodes(HttpServletRequest request) {

        // 封装 货主编码 gooderCodes
        List<String> gooderCodes;
        try {

            gooderCodes = getGooderCodes(request);

//          gooderCodes = goodMapper.findAllGooderCodes();

        } catch (Exception e) {
            log.error("查询货主编码异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(gooderCodes);
    }

    /**
     * 调整单号的规则：自动生成  （RD+8位日期+6位流水）示例：RD20150702104055
     *
     * @return
     */
    @PostMapping("/getAdjustId")
    public Result getAdjustIdAndDate(HttpServletRequest request) {

        String userId = getUserName(request);

        CodeAndDateVo codeAndDateVo=adjustBillService.getAdjustIdAndDate(userId);

        return ResultGenerator.genSuccessResult().setData(codeAndDateVo);

    }


}
