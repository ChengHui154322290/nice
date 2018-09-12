package com.nice.good.web;

import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.AdjustBillMapper;
import com.nice.good.dao.InventoryBillDetailMapper;
import com.nice.good.model.AdjustBill;
import com.nice.good.model.AdjustBillDetail;
import com.nice.good.model.InventoryBill;
import com.nice.good.model.InventoryBillDetail;
import com.nice.good.service.AdjustBillDetailService;
import com.nice.good.service.AdjustBillService;
import com.nice.good.service.InventoryBillDetailService;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.service.InventoryBillService;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.vo.BillDetailVo;
import org.springframework.beans.BeanUtils;
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
 * Created by CodeGenerator on 2018/06/07.
 */
@Transactional
@RestController
@RequestMapping("/inventory/bill/detail")
public class InventoryBillDetailController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(InventoryBillDetailController.class);


    @Resource
    private InventoryBillDetailService inventoryBillDetailService;
    @Resource
    private InventoryBillService inventoryBillService;

    @Resource
    private InventoryBillDetailMapper inventoryBillDetailMapper;


    /**
     * 批量新增 盘点明细单(k_inventory_bill_detail)     -- 2018/06/12  19:31  rk
     *
     * @param billDetailVos
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestParam String inventoryBillId, @RequestBody List<BillDetailVo> billDetailVos, HttpServletRequest request) {
        if (billDetailVos == null || billDetailVos.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        try {

            if (StringUtils.isBlank(inventoryBillId)) {
                //单号不能为空
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("盘点单号不能为空!");
            }
            //只有未开的盘点单才能新增明细
            // 获取单号编号
            InventoryBill inventoryBill = inventoryBillService.findById(inventoryBillId);
            Integer inventoryStatus = inventoryBill.getInventoryStatus();
            if (inventoryStatus != 0) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("只有未开始的单据才能新增盘点明细!");
            }

            if (billDetailVos == null || billDetailVos.size() == 0) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("请选择要添加的明细单!");
            }

            List<String> list = new ArrayList<>();


            //现在新增和以前新增的记录都不能重复
            //1.原有的记录
            List<InventoryBillDetail> inventoryBillDetails = inventoryBillDetailMapper.selectInventoryBillDetailByInventoryBillId(inventoryBillId);

            if (inventoryBillDetails != null && inventoryBillDetails.size() > 0) {
                for (InventoryBillDetail detail : inventoryBillDetails) {

                    String gooderCode = detail.getGooderCode();
                    String goodCode = detail.getGoodCode();
                    String orgCode = detail.getOrgCode();
                    String providerCode = detail.getProviderCode();
                    String seatCode = detail.getSeatCode();

                    String goodMessage = gooderCode + goodCode + orgCode + providerCode + seatCode;

                    list.add(goodMessage);
                }
            }

            //2.新增的记录
            for (BillDetailVo detail : billDetailVos) {

                String gooderCode = detail.getGooderCode();
                String goodCode = detail.getGoodCode();
                String orgCode = detail.getOrgCode();
                String providerCode = detail.getProviderCode();
                String seatCode = detail.getSeatCode();

                String goodMessage = gooderCode + goodCode + orgCode + providerCode + seatCode;

                if (list.contains(goodMessage)) {
                    return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("新增的明细中存在重复的记录，添加失败!");
                }

                list.add(goodMessage);


                if (StringUtils.isBlank(detail.getSeatCode())) {
                    return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("来源库位不能为空!");
                }

                //现有量
                Integer existingQuantity = detail.getNowNum();

                if (existingQuantity == null || StringUtils.isBlank(existingQuantity.toString())) {
                    return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("盘点明细现有量不能为空!");

                }

                if (existingQuantity == 0) {
                    return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("盘点明细现有量不能为0!");
                }


            }

            for (BillDetailVo billVo : billDetailVos) {
                InventoryBillDetail detail = new InventoryBillDetail();
                Timestamp timeStamp = TimeStampUtils.getTimeStamp();
                detail.setDetailId(IdsUtils.getOrderId());
                detail.setInventoryBillId(inventoryBillId);
                detail.setCreateId(userId);
                detail.setCreateDate(timeStamp);
                detail.setModifyId(userId);
                detail.setModifyDate(timeStamp);
                //现有量
                int nowNum = billVo.getNowNum();
                detail.setExistingQuantity(nowNum);
                //调整量
                detail.setInventoryQuantity(nowNum);

                //差异量
                detail.setDiffQuantity(0);

                detail.setStatus(0);
                BeanUtils.copyProperties(billVo, detail, "id");

                inventoryBillDetailService.save(detail);

            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> detailIds, HttpServletRequest request) {
        if (detailIds == null || detailIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        String userId = getUserName(request);
        String errorMsg = "";

        try {

            Boolean flag = false;
            String inventoryBillId = null;
            for (String detailId : detailIds) {

                InventoryBillDetail detail = inventoryBillDetailService.findById(detailId);

                if (detail == null) {
                    continue;
                }

                if (detail.getStatus() != 0) {
                    errorMsg += "第：" + detail.getId() + " 条记录单据状态不对,删除失败!\n";
                    continue;

                }

                //根据 detailId(主键id) 删除 盘点单明细表 中对应的信息
                inventoryBillDetailService.deleteById(detailId);

                flag = true;
                inventoryBillId = detail.getInventoryBillId();
            }

            if (flag) {
                updateInventory(userId, inventoryBillId);
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


    private void updateInventory(String userId, String inventoryBillId) {
        Integer status = getStatus(inventoryBillId);
        InventoryBill inventoryBill = inventoryBillService.findById(inventoryBillId);
        inventoryBill.setInventoryStatus(status);
        inventoryBill.setModifyDate(TimeStampUtils.getTimeStamp());
        inventoryBill.setModifyId(userId);
        inventoryBillService.update(inventoryBill);
    }

    public Integer getStatus(String inventoryBillId) {

        List<Integer> counts = inventoryBillDetailService.selectStatusByInventoryBillId(inventoryBillId);
        if (counts == null || counts.size() == 0) {
            return 0;
        }

        if (counts.size() > 1) {
            return 1;
        } else {
            return counts.get(0);
        }
    }

    /**
     * 页面刷新
     *
     * @param inventoryBillId
     * @return
     */
    @PostMapping("/listDetail")
    public Result listDetail(@RequestParam String inventoryBillId) {
        InventoryBill inventoryBill = inventoryBillService.findById(inventoryBillId);
        List<InventoryBillDetail> inventoryBillDetails = inventoryBillDetailService.selectInventoryBillDetailByInventoryBillId(inventoryBillId);
        if (inventoryBillDetails != null && inventoryBillDetails.size() > 0) {
            inventoryBill.setInventoryBillDetails(inventoryBillDetails);
        }

        return ResultGenerator.genSuccessResult(inventoryBill);
    }

    @PostMapping("/update")
    public Result update(@RequestBody InventoryBillDetail inventoryBillDetail, HttpServletRequest request) {
        if (inventoryBillDetail == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        if (inventoryBillDetail.getId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }
        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            inventoryBillDetailService.inventoryBillDetailUpdate(inventoryBillDetail, userId);

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/listMsg")
    public Result findMsg(@RequestBody BillDetailVo billDetailVo, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,HttpServletRequest request) {

//        PageHelper.startPage(page, size);

        PageInfo pageInfo = null;
        try {

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            List<BillDetailVo> list2 = new ArrayList<>();
            List<String> gooderCodes = getGooderCodes(request);


            String gooderCode = billDetailVo.getGooderCode();

            String goodCode = billDetailVo.getGoodCode();

            String areaCode = billDetailVo.getAreaCode();

            String goodModel = billDetailVo.getGoodModel();

            String seatCode = billDetailVo.getSeatCode();

            String goodName = billDetailVo.getGoodName();

            Integer isNull = billDetailVo.getIsNull();

            Date modifyDateStart = billDetailVo.getModifyDateStart();

            Date modifyDateEnd = billDetailVo.getModifyDateEnd();

            Map<String, Object> map = new HashMap<>();

            map.put("gooderCode", gooderCode);
            map.put("goodCode", goodCode);
            map.put("areaCode", areaCode);
            map.put("goodModel", goodModel);
            map.put("seatCode", seatCode);
            map.put("goodName", goodName);
            map.put("isNull", isNull);
            map.put("modifyDateStart", modifyDateStart);
            map.put("modifyDateEnd", modifyDateEnd);
            map.put("placeId", placeId);


            if (gooderCodes != null && gooderCodes.size() > 0) {

                List<BillDetailVo> list = inventoryBillDetailMapper.findMsg(map);

                if (list != null && list.size() > 0) {
                    for (BillDetailVo vo : list) {
                        if (gooderCodes.contains(vo.getGooderCode())) {
                            list2.add(vo);
                        }
                    }
                }
            }

            pageInfo = new PageInfo(list2);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


}
