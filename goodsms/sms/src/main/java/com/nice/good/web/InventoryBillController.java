package com.nice.good.web;

import com.nice.good.aop.LogAnnotation;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.AdjustBillDetailMapper;
import com.nice.good.dao.AdjustBillMapper;
import com.nice.good.dao.InventoryBillMapper;
import com.nice.good.model.*;

import com.nice.good.service.InventoryBillDetailService;
import com.nice.good.service.InventoryBillService;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.vo.CodeAndDateVo;
import com.nice.good.vo.InventoryBillVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import java.util.*;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/06/06.
 */

@RestController
@RequestMapping("/inventory/bill")
public class InventoryBillController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(InventoryBillController.class);


    @Resource
    private InventoryBillService inventoryBillService;


    @Resource
    private InventoryBillMapper inventoryBillMapper;


    @Resource
    private InventoryBillDetailService inventoryBillDetailService;

    /**
     * 盘点单 导出 操作
     *
     * @param inventoryBills
     * @return
     */
    @LogAnnotation(logType = "盘点单日志", content = "导出")
    @PostMapping("/adjustBillExport")
    public Result adjustBillExport(@RequestBody List<InventoryBill> inventoryBills, HttpServletRequest request, HttpServletResponse response) {


        try {

            String errorMsg = inventoryBillService.inventoryBillExport(inventoryBills,request,response);

            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
            }
        } catch (Exception e) {
            log.error("盘点单生成调整单操作出现异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();

    }


    /**
     * 盘点单 生成调整单操作 。   -- 2018/06/14  16:38  rk
     * 在该函数中，盘点单对应的盘点明细单，也进行生成 调整明细单。
     *
     * @param inventoryBills
     * @return
     */
    @LogAnnotation(logType = "盘点单日志", content = "生成调整单")
    @PostMapping("/updateAdjustBill")
    public Result updateAdjustBill(@RequestBody List<InventoryBill> inventoryBills, HttpServletRequest request) {

        String userId = getUserName(request);



        try {

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = inventoryBillService.generateAdjustBills(inventoryBills, placeId, userId);

            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("盘点单生成调整单操作出现异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();

    }

    /**
     * 盘点单 复盘 操作    -- 2018/06/14  15:46  rk
     *
     * @param inventoryBills
     * @return
     */
    @LogAnnotation(logType = "盘点单日志", content = "复盘")
    @Transactional
    @PostMapping("/inventory")
    public Result inventory(@RequestBody List<InventoryBill> inventoryBills, HttpServletRequest request) {


        try {

            String userId = getUserName(request);

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = inventoryBillService.reInventory(inventoryBills, placeId, userId);


            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("盘点单进行复盘操作出现异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();

    }


    /**
     * 查询 盘点单--盘点明细单信息   -- 2018/06/12  16:32  rk
     *
     * @param inventoryBillId
     * @return
     */
    @PostMapping("/listInventoryMsg")
    public Result listInventoryMsg(@RequestParam String inventoryBillId) {


        InventoryBill inventoryBill = null;

        try {

            // 通过 inventoryBillId 查询 InventoryBill 类信息
            inventoryBill = inventoryBillService.selectInventoryBillByKeyId(inventoryBillId);
            // 通过 inventoryBillId 查询 InventoryBillDetail 类信息
            List<InventoryBillDetail> inventoryBillDetails = inventoryBillDetailService.selectInventoryBillDetailByInventoryBillId(inventoryBillId);
            if (inventoryBillDetails != null && inventoryBillDetails.size() > 0) {
                inventoryBill.setInventoryBillDetails(inventoryBillDetails);
            }


        } catch (Exception e) {
            log.error("查询移动单--移动明细单信息异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(inventoryBill);
    }


    /**
     * 新增 盘点单 --- 盘点明细单      --  2018/06/11  14:42  rk
     *
     * @param inventoryBill
     * @return
     */
    @LogAnnotation(logType = "盘点单日志", content = "盘点单新增")
    @PostMapping("/add")
    public Result add(@RequestBody InventoryBill inventoryBill, HttpServletRequest request) {
        if (inventoryBill == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }


        try {

            String userId = getUserName(request);

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = inventoryBillService.saveInventoryBill(inventoryBill, placeId, userId);


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
     * 批量删除 盘点单 信息     -- 2018/06/11  14:53  rk
     *
     * @param inventoryBillIds
     * @return
     */
    @LogAnnotation(logType = "盘点单日志", content = "删除")
    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> inventoryBillIds) {
        if (inventoryBillIds == null || inventoryBillIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        String errorMsg = "";

        try {

            for (String inventoryBillId : inventoryBillIds) {

                // 通过 inventoryBillId 查询表 k_inventory_bill 中的 inventory_status 值， inventory_status的值为0的可删除。
                Integer inventoryStatus = inventoryBillService.selectInventoryStatus(inventoryBillId);
                if (inventoryStatus == null) {
                    errorMsg += "第" + inventoryBillId + "条记录已被删除!\n";
                    continue;
                }

                if (inventoryStatus != 0) {
                    errorMsg += "第" + inventoryBillId + "条记录单据状态不对,删除失败!\n";
                    continue;
                }

                inventoryBillService.deleteById(inventoryBillId);

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
     * 盘点明细单 生成 调整明细单操作。  -- 2018/06/15  14:58  rk
     * 在该函数中，盘点明细单对应的盘点单，也进行生成 调整单。
     *
     * @param inventoryBillDetails
     * @return
     */
    @LogAnnotation(logType = "盘点单日志", content = "确认调整")
    @PostMapping("/update")
    public Result update(@RequestBody List<InventoryBillDetail> inventoryBillDetails, HttpServletRequest request) {
        String userId = getUserName(request);

        try {

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = inventoryBillService.updateInventoryBillDetails(inventoryBillDetails,placeId, userId);

            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("盘点明细单生成调整明细单操作出现异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }


        return ResultGenerator.genSuccessResult();
    }


    /**
     * 根据查询条件，查询对应的 盘点单信息    -- 2018/06/12  14:21  rk
     *
     * @param inventoryBillVo
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody InventoryBillVo inventoryBillVo, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,HttpServletRequest request) {



        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        PageHelper.startPage(page, size);

        PageInfo pageInfo = null;
        try {

            List<InventoryBill> list = inventoryBillMapper.selectInventoryBill(inventoryBillVo.getInventoryBillCode(), inventoryBillVo.getSourceBillCode(), inventoryBillVo.getInventoryStatus(), inventoryBillVo.getInventoryType(),
                    inventoryBillVo.getIsBlindInventory(), inventoryBillVo.getRemark(), inventoryBillVo.getCreateId(),
                    inventoryBillVo.getCreateDateStart(), inventoryBillVo.getCreateDateEnd(), inventoryBillVo.getModifyId(), inventoryBillVo.getModifyDateStart(),
                    inventoryBillVo.getModifyDateEnd(),placeId);

            // 用于封装 添加 diffQuantity 字段的 List<T> InventoryBills 集合
            List<InventoryBill> newList = new ArrayList<>();

            Integer isDifferent = inventoryBillVo.getIsDifferent();


            for (InventoryBill inventoryBill : list) {
                // 设置 diffQuantity(是否差异) 字段的值
                String inventoryBillId = inventoryBill.getInventoryBillId();
                Integer integer = inventoryBillMapper.selectSumDifferentByBillId(inventoryBillId);
                if (integer != null && integer == 0) {
                    inventoryBill.setIsDifferent(0);
                }

                if (integer != null && integer > 0) {
                    inventoryBill.setIsDifferent(1);
                }

                if (isDifferent != null && isDifferent == 0 && integer != null && integer > 0) {
                    continue;
                }

                if (isDifferent != null && isDifferent == 1 && integer != null && integer == 0) {
                    continue;
                }


                newList.add(inventoryBill);
            }

            /**
             * lambda对id进行排序
             */
            Collections.sort(list, (InventoryBill o1, InventoryBill o2) -> o2.getId() - o1.getId());

            pageInfo = new PageInfo(newList);
            pageInfo.setPageSize(size);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 盘点单编号规则：自动生成（CC+8位日期+4位流水）示例：CC201804110001
     *
     * @return
     */
    @PostMapping("/getInventoryId")
    public Result getInventoryIdAndDate(HttpServletRequest request) {

        String userId = getUserName(request);

        CodeAndDateVo codeAndDateVo = inventoryBillService.getInventoryIdAndDate(userId);

        return ResultGenerator.genSuccessResult().setData(codeAndDateVo);

    }


}
