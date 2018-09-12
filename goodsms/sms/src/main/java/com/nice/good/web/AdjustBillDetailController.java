package com.nice.good.web;

import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.AdjustBillDetailMapper;
import com.nice.good.dao.AdjustBillMapper;
import com.nice.good.model.AdjustBill;
import com.nice.good.model.AdjustBillDetail;
import com.nice.good.service.AdjustBillDetailService;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.service.AdjustBillService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/06/02.
 */
@Transactional
@RestController
@RequestMapping("/adjust/bill/detail")
public class AdjustBillDetailController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(AdjustBillDetailController.class);


    @Resource
    private AdjustBillDetailService adjustBillDetailService;

    @Resource
    private AdjustBillService adjustBillService;


    @Resource
    private AdjustBillDetailMapper adjustBillDetailMapper;

    @PostMapping("/add")
    public Result add(@RequestParam String adjustBillId,
                      @RequestBody List<BillDetailVo> billDetailVos,
                      HttpServletRequest request) {
        if (billDetailVos == null || billDetailVos.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        String userId = getUserName(request);
        //主表只有未开始状态的才能添加明细
        // 获取单号编号
        if (StringUtils.isBlank(adjustBillId)) {
            //单号不能为空
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("调整单号不能为空!");
        }

        AdjustBill adjustBill = adjustBillService.findById(adjustBillId);

        int adjustStatus = adjustBill.getAdjustStatus();
        if (adjustStatus != 0) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("只有未开始的单据才能新增明细!");
        }


        try {

            if (billDetailVos == null || billDetailVos.size() == 0) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("请选择要添加的明细单!");
            }

            List<String> list = new ArrayList<>();

            //现在新增和以前新增的记录都不能重复
            //1.原有的记录
            List<AdjustBillDetail> adjustBillDetails = adjustBillDetailMapper.selectAdjustBillDetailByAdjustBillId(adjustBillId);

            if (adjustBillDetails!=null && adjustBillDetails.size()>0){
                for (AdjustBillDetail detail:adjustBillDetails){

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
            for (BillDetailVo billVo : billDetailVos) {

                String gooderCode = billVo.getGooderCode();
                String goodCode = billVo.getGoodCode();
                String orgCode = billVo.getOrgCode();
                String providerCode = billVo.getProviderCode();
                String seatCode = billVo.getSeatCode();

                String goodMessage = gooderCode + goodCode + orgCode + providerCode + seatCode;

                if (list.contains(goodMessage)) {
                    return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("新增的明细中存在重复的记录，添加失败!");
                }
                list.add(goodMessage);

            }


            // 循环新增 调整单明细 信息
            for (BillDetailVo billVo : billDetailVos) {
                AdjustBillDetail detail = new AdjustBillDetail();
                detail.setDetailId(IdsUtils.getOrderId());
                detail.setAdjustBillId(adjustBillId);
                detail.setStatus(0);
                detail.setCreateDate(TimeStampUtils.getTimeStamp());
                detail.setCreateId(userId);
                detail.setModifyId(userId);
                detail.setPlaceId(placeId);
                //现有量
                detail.setExistingQuantity(billVo.getNowNum());
                detail.setModifyDate(TimeStampUtils.getTimeStamp());
                BeanUtils.copyProperties(billVo, detail, "id");
                adjustBillDetailService.save(detail);
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

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }


            Boolean flag = false;
            String adjustBillId = null;
            for (String detailId : detailIds) {

                // 通过 detailId(主键id) 查询  k_adjust_bill_detail(调整明细单) 对应的 status 值
                AdjustBillDetail detail = adjustBillDetailService.findById(detailId);

                if (detail == null) {
                    errorMsg += "第 " + detail.getId() + "条记录已被删除!\n";
                    continue;
                }

                if (detail.getStatus() != 0) {
                    errorMsg += "第 " + detail.getId() + "条记录单据状态不对,删除失败!\n";
                    continue;

                }


                // 根据 detailId(主键id) 删除 调整单明细表中对应的信息
                adjustBillDetailService.deleteById(detailId);
                flag = true;
                adjustBillId = detail.getAdjustBillId();

            }

            if (flag) {
                Integer status = getStatus(adjustBillId, placeId);
                AdjustBill adjustBill = adjustBillService.findById(adjustBillId);
                adjustBill.setAdjustStatus(status);
                adjustBill.setModifyId(userId);
                adjustBill.setModifyDate(new Date());
                adjustBillService.update(adjustBill);
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


    public Integer getStatus(String adjustBillId, String placeId) {

        List<Integer> counts = adjustBillDetailService.selectStatusByAdjustBillId(adjustBillId, placeId);
        if (counts == null || counts.size() == 0) {
            return 0;
        }

        if (counts.size() > 1) {
            return 1;
        } else {
            return counts.get(0);
        }

    }


    @PostMapping("/listMsg")
    public Result findMsg(@RequestBody BillDetailVo billDetailVo, @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "20") Integer size,
                          HttpServletRequest request) {

//        PageHelper.startPage(page, size);

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        PageInfo pageInfo = null;
        try {


            List<BillDetailVo> list2= new ArrayList<>();
            List<String> gooderCodes = getGooderCodes(request);

            if (gooderCodes != null && gooderCodes.size() > 0) {

                List<BillDetailVo> list = adjustBillDetailMapper.findMsg(billDetailVo.getGooderCode(), billDetailVo.getGoodCode(), billDetailVo.getAreaCode(), billDetailVo.getGoodModel(), billDetailVo.getSeatCode(), billDetailVo.getGoodName(), placeId);

                if (list!=null && list.size()>0){
                    for (BillDetailVo vo:list){
                        if (gooderCodes.contains(vo.getGooderCode())){
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
