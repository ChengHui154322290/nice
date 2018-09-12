package com.nice.good.service.impl;


import com.nice.good.core.Result;
import com.nice.good.dao.*;
import com.nice.good.dto.ReceiveDetailDto;
import com.nice.good.model.*;
import com.nice.good.dto.ShelfPojo;
import com.nice.good.utils.ErrorMsg;
import com.nice.good.utils.IdsUtils;

import com.nice.good.utils.TimeStampUtils;
import com.nice.good.service.ReceiveOrderService;
import com.nice.good.core.AbstractService;
import com.nice.good.vo.GoodVo;
import com.nice.good.vo.ReceiveDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 收货单及其明细单操作
 * @Author: fqs
 * @Date: 2018/3/29 9:41
 * @Version: 1.0
 */
@Service
@Transactional
public class ReceiveOrderServiceImpl extends AbstractService<ReceiveOrder> implements ReceiveOrderService {
    @Resource
    private ReceiveOrderMapper receiveOrderMapper;

    @Resource
    private ReceiveDetailMapper receiveDetailMapper;


    @Resource
    private ReceiveDetailTempMapper receiveDetailTempMapper;


    @Resource
    private StockMapper stockMapper;

    @Resource
    private SeatStockMapper seatStockMapper;

    @Resource
    private StoreSeatMapper storeSeatMapper;


    @Resource
    private GoodConfigMapper goodConfigMapper;


    @Resource
    private OrderGoodMappingMapper orderGoodMappingMapper;

    @Resource
    private GoodMapper goodMapper;


    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodAreaMapper goodAreaMapper;

    @Resource
    private DynamicGoodMapper dynamicGoodMapper;


    @Resource
    private RfidLabelMapper rfidLabelMapper;


    /**
     * 收货单新增操作
     *
     * @param receiveOrder
     * @param userId
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String receiveOrderAdd(ReceiveOrder receiveOrder, String placeId, String userId) throws Exception {

        String errorMsg = "";
        if (receiveOrder != null) {

            String orgCode = receiveOrder.getOrgCode();

            if (StringUtils.isBlank(orgCode)) {
                errorMsg = "组织机构编码不能为空!";
                return errorMsg;
            }

            String providerCode = receiveOrder.getProviderCode();
            if (StringUtils.isBlank(providerCode)) {
                errorMsg = "供应商编码编码不能为空!";
                return errorMsg;
            }

            //新增时判断收货单号是否重复
            String id = receiveOrderMapper.selectReceiveIdByReceiveCode(receiveOrder.getReceiveCode());
            if (id != null) {
                receiveOrder.setReceiveCode(getReceive());
            }

            //主表收货单保存
            String receiveId = IdsUtils.getOrderId();
            receiveOrder.setReceiveId(receiveId);
            receiveOrder.setOrderStatus(0);
            //新增的时候,默认不挂起
            receiveOrder.setHangUp(0);
            receiveOrder.setCreateId(userId);
            receiveOrder.setModifyId(userId);
            receiveOrder.setCreateDate(TimeStampUtils.getTimeStamp());
            receiveOrder.setModifyDate(TimeStampUtils.getTimeStamp());

            receiveOrder.setPlaceId(placeId);


            //收货单预期量
            int totalExpectNum = 0;

            //收货单预计重量
            double totalPredictWeight = 0;

            //收货单预计体积
            double totalBulk = 0;

            //采购单号
            String purchaseCode = null;


            //子表收货单明细表保存
            List<ReceiveDetail> receiveDetails = receiveOrder.getReceiveDetails();
            if (receiveDetails == null || receiveDetails.size() == 0) {
                errorMsg = "收货单明细不能为空!";
                return errorMsg;

            }

            String gooderCode = receiveOrder.getGooderCode();

            for (ReceiveDetail receiveDetail : receiveDetails) {
                if (!receiveDetail.getGooderCode().equals(gooderCode)) {
                    errorMsg = "不同货主不能同时收货,操作不成功!";
                    return errorMsg;
                }
            }

            int count = 0;
            List<String> keys = new ArrayList<>();
            Set<String> set = new HashSet<>();
            for (ReceiveDetail receiveDetail : receiveDetails) {
                if (receiveDetail.getStatus() == 0) {
                    String key = receiveDetail.getGooderCode() + receiveDetail.getGoodCode();
                    if (keys.contains(key)) {
                        errorMsg = "未开始的收货明细中存在重复的记录,操作不成功(提示:删除重复的记录再操作)!";
                        return errorMsg;
                    }
                    String purchaseCode1 = receiveDetail.getPurchaseCode();
                    if (purchaseCode1 == null) {
                        set.add(purchaseCode);
                    } else {
                        ++count;
                    }

                    keys.add(key);
                }
            }

            //无PO和PO不能同时新增
            if (set.size() > 1) {
                errorMsg = "一个收货单中不能同时存在多个PO单!";
                return errorMsg;
            }

            if (set.size() > 0 && count > 0) {
                errorMsg = "收货单中不能同时存在PO单和无PO单!";
                return errorMsg;
            }


            for (ReceiveDetail receiveDetail : receiveDetails) {
                if (receiveDetail != null) {
                    //明细表id
                    receiveDetail.setDetailId(IdsUtils.getOrderId());
                    //单据状态未开始
                    receiveDetail.setStatus(0);
                    //新增的时候,默认不挂起
                    receiveDetail.setHangUp(0);

                    //新增时预期量和库位不能为空
                    Integer expectNum = receiveDetail.getExpectNum();

                    if (expectNum == null || StringUtils.isBlank(expectNum.toString()) || expectNum == 0) {
                        errorMsg = "预期量不能为空!";
                        return errorMsg;
                    }

                    String seatCode = receiveDetail.getSeatCode();
                    if (seatCode == null) {
                        errorMsg = "库位编码不能为空!";
                        return errorMsg;
                    }

                    //分配库位之前判断库位容量是否足够
                    Integer capacity = storeSeatMapper.selectCapacityBySeatCode(seatCode, placeId);
                    if (capacity != null && capacity != 0) {
                        if (capacity < expectNum) {
                            errorMsg = "库位库位容量不足,请重新分配库位!";
                            return errorMsg;
                        }

                    }


                    //新增时接收量,拒收量,质检量,次品量,上架量,rfid采集量都为0
                    receiveDetail.setReceiveNum(1);
                    receiveDetail.setRefuseNum(0);
                    receiveDetail.setQualityNum(0);
                    receiveDetail.setSecondNum(0);
                    receiveDetail.setShelfNum(0);
                    receiveDetail.setRfid(0);
                    receiveDetail.setCreateId(userId);
                    receiveDetail.setCreateDate(TimeStampUtils.getTimeStamp());
                    receiveDetail.setModifyId(userId);
                    receiveDetail.setModifyDate(TimeStampUtils.getTimeStamp());
                    //关收货单表id
                    receiveDetail.setReceiveId(receiveId);


                    //采购单号
                    purchaseCode = receiveDetail.getPurchaseCode();
                    Integer purchaseLineCode = receiveDetail.getPurchaseLineCode();

                    //选单时预期量等于采购数量,不可更改
                    if (purchaseCode != null && purchaseLineCode != null) {

                        OrderGoodMapping goodMapping = orderGoodMappingMapper.selectByPrimaryKey(purchaseLineCode);
                        List<ReceiveDetail> list = receiveDetailMapper.selectByPurchaseLineCode(purchaseCode, purchaseLineCode);

                        if (list == null || list.size() == 0) {
                            //第 一次新增采购单,预期量等于采购数量
                            expectNum = goodMapping.getPurchaseNumber();
                        } else {
                            //同一采购单二次收货,预期量为0
                            expectNum = 0;
                        }

                        //补充组织机构编码和货主编码
                        orgCode = goodMapping.getOrgCode();
                        providerCode = goodMapping.getProviderCode();

                    }

                    receiveDetail.setExpectNum(expectNum);

                    receiveDetail.setOrgCode(orgCode);
                    receiveDetail.setProviderCode(providerCode);

                    receiveDetail.setPlaceId(placeId);

                    receiveDetailMapper.insert(receiveDetail);


                    //收货单总的预期量
                    totalExpectNum += expectNum;


                    //获取每个货品的重量和体积
                    Double rweight = receiveDetail.getRweight();
                    Double bulk = receiveDetail.getBulk();

                    if (rweight == null) {
                        rweight = 0.0;
                    }

                    totalPredictWeight += rweight;

                    if (bulk == null) {
                        bulk = 0.0;
                    }
                    totalBulk += bulk;

                }
            }

            //接收量,拒收量,上架量,质检量,次品量都为0
            receiveOrder.setReceiveNum(0);
            receiveOrder.setRefuseNum(0);
            receiveOrder.setQualityNum(0);
            receiveOrder.setSecondNum(0);
            receiveOrder.setShelfNum(0);


            //采购单号
            receiveOrder.setPurchaseCode(purchaseCode);

            receiveOrder.setExpectNum(totalExpectNum);
            receiveOrder.setPredictWeight(totalPredictWeight);
            receiveOrder.setPredictBulk(totalBulk);
            receiveOrderMapper.insert(receiveOrder);

        }
        return errorMsg;

    }


    /**
     * 判断收货单是否被挂起
     *
     * @param receiveId
     * @param userId
     * @return
     */
    public String checkHangUp(String receiveId, Integer sign, String userId) {

        //sign自定义标志,0表示收货明细界面的挂起,1表示收货单主界面的挂起

        ReceiveOrder receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);
        Integer hangUp = receiveOrder.getHangUp();
        String modifier = receiveOrder.getModifyId();
        if (hangUp == 1 && !userId.equals(modifier)) {
            if (sign == 0) {
                return "当前记录已被" + receiveOrder.getModifyId() + "用户挂起,您无法操作!\n";
            }

            if (sign == 1) {
                return "收货单" + receiveOrder.getReceiveCode() + "已被" + receiveOrder.getModifyId() + "用户挂起,您无法操作!\n";
            }

        }
        if (hangUp == 1 && userId.equals(modifier)) {

            if (sign == 0) {
                return "当前记录已被挂起,请取消以后再操作!\n";
            }
            if (sign == 1) {
                return "收货单" + receiveOrder.getReceiveCode() + "已被挂起,请取消以后再操作!\n";
            }
        }

        return "";
    }

    /**
     * 收货明细界面订单挂起/取消挂起操作
     *
     * @param receiveIds
     * @param hangUp     表示挂起的标识，0表示否，1表示是
     * @param userId
     */


    @Override
    public String receiveOrderHangUp(List<String> receiveIds, Integer hangUp, Integer flag, String userId) {

        //flag 0表示明细界面操作,1表示主界面操作,不同界面操作,错误操作提示是不一样的

        //收货明细界面挂起/取消挂起操作
        String errorMsg = "";
        for (String receiveId : receiveIds) {
            if (receiveId == null) {
                continue;
            }

            ReceiveOrder receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);

            //已结算的订单不能挂起
            if (receiveOrder.getOrderStatus() == 7 && hangUp == 1) {
                errorMsg += "收货单" + receiveOrder.getReceiveCode() + "已结算,挂起失败!\n";
                continue;
            }

            if (receiveOrder.getOrderStatus() == 7 && hangUp == 0) {
                errorMsg += "收货单" + receiveOrder.getReceiveCode() + "已结算,取消挂起失败!\n";
                continue;
            }

            String modifier = receiveOrder.getModifyId();

            //执行挂起操作
            Integer hangUp1 = receiveOrder.getHangUp();
            //单据处于挂起中状态
            if (hangUp1 == 1) {

                if (hangUp == 1 && !userId.equals(modifier)) {

                    //明细界面挂起/取消挂起操作
                    if (flag == 0) {
                        errorMsg += "当前记录已被" + receiveOrder.getModifyId() + "用户挂起,您无法操作!\n";
                        continue;
                    }

                    //主界面挂起/取消挂起操作
                    if (flag == 1) {
                        errorMsg += "收货单" + receiveOrder.getReceiveCode() + "已被" + receiveOrder.getModifyId() + "用户挂起,您无法操作!\n";
                        continue;
                    }

                }
            }

            //主表挂起/取消挂起操作
            receiveOrder.setReceiveId(receiveId);
            receiveOrder.setHangUp(hangUp);
            receiveOrder.setModifyId(userId);
            receiveOrder.setModifyDate(TimeStampUtils.getTimeStamp());
            receiveOrderMapper.updateByPrimaryKey(receiveOrder);
            //子表挂起/取消挂起操作
            List<String> detailIds = receiveDetailMapper.selectByReceiveId(receiveId);
            for (String detailId : detailIds) {
                ReceiveDetail receiveDetail = receiveDetailMapper.selectByPrimaryKey(detailId);
                receiveDetail.setDetailId(detailId);
                receiveDetail.setHangUp(hangUp);
                receiveDetail.setModifyId(userId);
                receiveDetail.setModifyDate(TimeStampUtils.getTimeStamp());
                receiveDetailMapper.updateByPrimaryKey(receiveDetail);
            }

        }
        return errorMsg;
    }


    /**
     * 收货单主界面删除操作
     *
     * @param receiveIds
     * @return
     */
    @Override
    public String deleteByReceiveId(List<String> receiveIds) {

        String errorMsg = "";

        for (String receiveId : receiveIds) {

            if (receiveId == null) {
                continue;
            }

            ReceiveOrder receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);
            if (receiveOrder == null) {
                continue;
            }

            if (receiveOrder.getOrderStatus() != 0) {
                errorMsg += receiveOrder.getReceiveCode() + "," + "\n";
                continue;
            }

            //先删除收货单明细表
            List<String> list = receiveDetailMapper.selectByReceiveId(receiveId);
            for (String detail : list) {
                receiveDetailMapper.deleteByPrimaryKey(detail);
            }
            //再删除主表
            receiveOrderMapper.deleteByPrimaryKey(receiveId);

        }

        if (StringUtils.isNotBlank(errorMsg)) {
            String substring = errorMsg.substring(0, errorMsg.lastIndexOf(","));

            errorMsg = "第 " + substring + "条记录单据状态不对,操作失败!";

            return errorMsg;
        }

        return errorMsg;
    }

    /**
     * 收货明细界面收获操作，收货时拒收量不会进入库存
     *
     * @param receiveOrder
     * @param userId
     * @return
     * @throws Exception
     */

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String receiveGoods(ReceiveOrder receiveOrder, String placeId, String userId) throws Exception {

        String errorMsg = "";
        String errorMsg1 = "";
        String errorMsg2 = "";
        String errorMsg3 = "";
        String errorMsg4 = "";
        String errorMsg5 = "";
        String errorMsg6 = "";
        String errorMsg7 = "";
        String errorMsg8 = "";
        String errorMsg9 = "";
        String errorMsg10 = "";
        String errorMsg11 = "";

        String str1 = "";
        String str2 = "";
        String str3 = "";
        String str4 = "";
        String str5 = "";
        String str6 = "";
        String str7 = "";
        String str8 = "";
        String str9 = "";
        String str10 = "";
        String str11 = "";

        if (receiveOrder == null) {
            errorMsg = "请选择要操作的记录!";
            return errorMsg;
        }

        //判断收货单是否被挂起
        Integer sign = 0;
        errorMsg = checkHangUp(receiveOrder.getReceiveId(), sign, userId);

        if (StringUtils.isNotBlank(errorMsg)) {
            return errorMsg;
        }

        //单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算

        //订单明细表收货操作
        boolean flag = false;
        int totalExpectNum = 0;
        int totalReceiveNum = 0;
        int totalRefuseNum = 0;

        //只有未开始,收货中,质检中,上架中的货品才有可能收货
        Integer orderStatus = receiveOrder.getOrderStatus();
        if (orderStatus != 0 && orderStatus != 1 && orderStatus != 3 && orderStatus != 5) {
            errorMsg = "当前收货单单据状态不对，操作不成功!";
            return errorMsg;
        }

        List<ReceiveDetail> receiveDetails = receiveOrder.getReceiveDetails();
        if (receiveDetails == null || receiveDetails.size() == 0) {
            errorMsg = "当前收货单收货明细单为空，操作不成功!";
            return errorMsg;
        }


        Timestamp timestamp = TimeStampUtils.getTimeStamp();
        int count = 0;
        for (ReceiveDetail receiveDetail : receiveDetails) {

            //只有未开始的订单才能收货
            if (receiveDetail.getStatus() != 0) {
                errorMsg1 += receiveDetail.getId() + ",";
                str1 = "单据状态不对";
                continue;
            }

            //预期量不能为0
            Integer expectNum = receiveDetail.getExpectNum();
            if (expectNum == null || StringUtils.isBlank(expectNum.toString())) {
                errorMsg11 += receiveDetail.getId() + ",";
                str11 = "预期量不能为空";
                continue;
            }

            if (expectNum == 0) {
                errorMsg11 += receiveDetail.getId() + ",";
                str11 = "预期量不能为0";
                continue;
            }

            String seatCode = receiveDetail.getSeatCode();
            if (StringUtils.isBlank(seatCode)) {
                errorMsg2 += receiveDetail.getId() + ",";
                str2 = "库位不能为空";
                continue;
            }


            Integer receiveNum = receiveDetail.getReceiveNum();
            if (receiveNum == null || StringUtils.isBlank(receiveNum.toString())) {
                errorMsg3 += receiveDetail.getId() + ",";
                str3 = "接收量不能为空";
                continue;
            }

            Integer refuseNum = receiveDetail.getRefuseNum();
            if (refuseNum == null || StringUtils.isBlank(refuseNum.toString())) {
                errorMsg4 += receiveDetail.getId() + ",";
                str4 = "拒收量不能为空";
                continue;
            }

            if (receiveNum == 0 && refuseNum == 0) {
                errorMsg5 += receiveDetail.getId() + ",";
                str5 = "收货量和拒收量不能都为0";
                continue;
            }

            if (receiveNum < 0) {
                errorMsg6 += receiveDetail.getId() + ",";
                str6 = "收货量不正确";
                continue;
            }

            if (refuseNum < 0) {
                errorMsg7 += receiveDetail.getId() + ",";
                str7 = "拒货量不正确";
                continue;
            }

            //先对所有记录执行更新操作
            receiveDetailMapper.updateByPrimaryKey(receiveDetail);


            //货主编码
            String gooderCode = receiveDetail.getGooderCode();
            //货品编码
            String goodCode = receiveDetail.getGoodCode();
            //是否超量验证  0否,1是
            GoodConfig goodConfig = goodConfigMapper.selectGoodConfigByGoodId(gooderCode, goodCode, placeId);

            //无PO收货允许还是警告
            if (goodConfig != null) {
                String noPoReceive = goodConfig.getNoPoReceive();
                if (StringUtils.isNotBlank(noPoReceive) && "警告".equals(noPoReceive)) {
                    if (receiveDetail.getPurchaseCode() == null || receiveDetail.getPurchaseLineCode() == null) {

                        errorMsg8 += receiveDetail.getId() + ",";
                        str8 = "不允许无PO(采购单)收货";
                        continue;
                    }
                }
            }


            String receiveId = receiveDetail.getReceiveId();
            //预期量
            Integer sumExpectNum;
            //已接收量
            Integer sumReceiveNum;
            String purchaseCode = receiveDetail.getPurchaseCode();
            Integer purchaseLineCode = receiveDetail.getPurchaseLineCode();
            if (purchaseCode == null && purchaseLineCode == null) {
                sumExpectNum = receiveDetailMapper.selectSumOfExpectNum(gooderCode, goodCode, receiveId);
                sumReceiveNum = receiveDetailMapper.selectSumOfReceiveNum(gooderCode, goodCode, receiveId);

                flag = true;
            } else {
                sumExpectNum = receiveDetailMapper.selectTotalExpectNum(purchaseCode, purchaseLineCode);
                sumReceiveNum = receiveDetailMapper.selectTotalReceiveNum(purchaseCode, purchaseLineCode);
            }

            if (goodConfig != null) {
                if (goodConfig.getBeyondVerify() == 1) {
                    //是否超量验证
                    Double beyondRatio = goodConfig.getBeyondRatio();
                    if (beyondRatio == null) {
                        beyondRatio = 0.0;
                    }

                    Double beyondNum = sumExpectNum * (1 + beyondRatio / 100);
                    if (receiveNum + sumReceiveNum > beyondNum) {
                        errorMsg9 += receiveDetail.getId() + ",";
                        str9 = "货品已超过接收比例";
                        continue;
                    }
                }

            }


            //收货添加到库存
            String message = addToStock(userId, timestamp, receiveDetail, placeId);
            if (StringUtils.isNotBlank(message)) {
                errorMsg10 += receiveDetail.getId() + ",";
                str10 = "库存容量不足";
                continue;
            }

            //当前单据状态的确定
            Integer status2 = 0;
            Boolean bool1 = receiveNum + refuseNum + sumReceiveNum < sumExpectNum;
            if (bool1) {
                status2 = 1;
            }

            Boolean bool2 = receiveNum + refuseNum + sumReceiveNum >= sumExpectNum;
            if (bool2) {
                status2 = 2;
            }

            receiveDetail.setReceiveTime(timestamp);
            receiveDetail.setStatus(status2);
            receiveDetail.setModifyId(userId);
            receiveDetail.setModifyDate(timestamp);

            receiveDetailMapper.updateByPrimaryKey(receiveDetail);


            if (status2 == 2) {
                if (purchaseCode == null && purchaseLineCode == null) {

                    receiveDetailMapper.updateSameGoodStatus(gooderCode, goodCode, receiveId, status2);

                } else {
                    receiveDetailMapper.updateSamePurchaseStatus(purchaseCode, purchaseLineCode, status2);

                    //其他收货单中采购单总状态同步
                    List<ReceiveDetail> rdetails = receiveDetailMapper.selectReceiveDetailByPurchaseCode(purchaseCode);

                    Integer status3 = getOtherPuchase(rdetails);

                    receiveOrderMapper.updateOrderStatusByPurchaseCode(purchaseCode, status3);

                }

            }

            ++count;
            totalExpectNum = sumExpectNum;
            totalReceiveNum += receiveNum;
            totalRefuseNum += refuseNum;


            //收货以后,库位容量减少
            Integer capacity = storeSeatMapper.selectCapacityBySeatCode(seatCode, placeId);
            if (capacity != null && capacity != 0) {
                storeSeatMapper.updateSeatCapacity(seatCode, -receiveNum, placeId);
            }


            //收货时,收货状态同步到采购订单状态

            if (purchaseCode != null && purchaseLineCode != null) {
                updatePurchaseOrderStatus(receiveDetail, userId);
            }


            //货品动态跟踪
            DynamicGood dynamicGood = new DynamicGood();
            dynamicGood.setKeyId(IdsUtils.getOrderId());
            dynamicGood.setCreateDate(timestamp);
            dynamicGood.setCreateId(userId);
            dynamicGood.setModifyDate(timestamp);
            dynamicGood.setModifyId(userId);
            dynamicGood.setFromSeat(null);
            //rfid为0
            dynamicGood.setRfid(0);
            dynamicGood.setToSeat(receiveDetail.getSeatCode());
            dynamicGood.setSize(receiveDetail.getReceiveNum());
            dynamicGood.setGooderCode(receiveDetail.getGooderCode());
            dynamicGood.setGoodCode(receiveDetail.getGoodCode());
            dynamicGood.setTradeType("收货");
            //单据号
            dynamicGood.setBillCode(receiveOrder.getReceiveCode());

            dynamicGood.setPlaceId(placeId);
            dynamicGoodMapper.insert(dynamicGood);

        }

        //执行了收货操作
        if (count > 0) {
            //接收时间
            receiveOrder.setLastReceiveTime(timestamp);

            //更新预期量
            if (flag) {
                receiveOrder.setExpectNum(totalExpectNum);
            }

            receiveOrder.setReceiveNum(receiveOrder.getReceiveNum() + totalReceiveNum);
            receiveOrder.setRefuseNum(receiveOrder.getRefuseNum() + totalRefuseNum);

            Integer status = orderReceive(receiveOrder);

            receiveOrder.setOrderStatus(status);
            receiveOrder.setModifyId(userId);
            receiveOrder.setModifyDate(timestamp);

            receiveOrderMapper.updateByPrimaryKey(receiveOrder);
        }

        //统一消息提示
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg1, str1);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg2, str2);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg3, str3);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg4, str4);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg5, str5);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg6, str6);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg7, str7);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg8, str8);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg9, str9);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg10, str10);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg11, str11);

        return errorMsg;

    }


    private String addToStock(String userId, Timestamp timestamp, ReceiveDetail receiveDetail, String placeId) throws Exception {


        String message = "";

        String gooderCode = receiveDetail.getGooderCode();
        String goodCode = receiveDetail.getGoodCode();
        String seatCode = receiveDetail.getSeatCode();
        String goodName = receiveDetail.getGoodName();
        Integer receiveNum = receiveDetail.getReceiveNum();

        String commodityCode = goodMapper.selectCommodityCodeByGoodCode(goodCode);

        //补充组织机构编码和供应商编码
        String orgCode = receiveDetail.getOrgCode();
        String providerCode = receiveDetail.getProviderCode();

        //收货添加到库位库存表
        //分配库位之前判断库位容量是否足够
        Integer capacity = storeSeatMapper.selectCapacityBySeatCode(seatCode, placeId);
        if (capacity != null && capacity != 0) {
            if (receiveNum > capacity) {
                message = "第 " + receiveDetail.getId() + "条记录" + seatCode + "库存容量不足,请重新分配库位!";
                return message;
            }
        }

        //收货添加到库位库存表
        addSeatStock(userId, timestamp, gooderCode, goodCode, seatCode, commodityCode, goodName, receiveNum, orgCode, providerCode, placeId);

        //收货添加到总库存表
        addStock(userId, timestamp, gooderCode, goodCode, commodityCode, goodName, receiveNum, orgCode, providerCode, placeId);

        return message;
    }

    private void addSeatStock(String userId, Timestamp timestamp, String gooderCode, String goodCode, String seatCode, String commodityCode, String goodName, Integer receiveNum, String orgCode, String providerCode, String placeId) throws Exception {
        String stockId = seatStockMapper.selectStockId(gooderCode, goodCode, seatCode, orgCode, providerCode, placeId);
        if (stockId == null) {
            //新增一条记录
            SeatStock seatStock = new SeatStock();
            seatStock.setStockId(IdsUtils.getOrderId());

            //关联组织编码
            seatStock.setOrgCode(orgCode);
            //关联供应商编码
            seatStock.setProviderCode(providerCode);
            seatStock.setGooderCode(gooderCode);
            seatStock.setGoodCode(goodCode);
            seatStock.setSeatCode(seatCode);
            seatStock.setCommodityCode(commodityCode);
            seatStock.setGoodName(goodName);
            seatStock.setNowNum(receiveNum);
            seatStock.setUseNum(0);
            seatStock.setAllotNum(0);
            seatStock.setPickNum(0);
            seatStock.setFreezeNum(0);
            seatStock.setCreateId(userId);
            seatStock.setCreateDate(timestamp);
            seatStock.setModifyId(userId);
            seatStock.setModifyDate(timestamp);

            //关联场地id
            seatStock.setPlaceId(placeId);
            seatStockMapper.insert(seatStock);
        } else {
            SeatStock stock = seatStockMapper.selectByPrimaryKey(stockId);

            //在原有记录上累加
            Integer nowNum = stock.getNowNum();
            stock.setNowNum(receiveNum + nowNum);
            stock.setStockId(stockId);
            stock.setModifyId(userId);
            stock.setModifyDate(timestamp);
            seatStockMapper.updateByPrimaryKey(stock);
        }
    }

    private void addStock(String userId, Timestamp timestamp, String gooderCode, String goodCode, String commodityCode, String goodName, Integer receiveNum, String orgCode, String providerCode, String placeId) throws Exception {

        //1.根据货主id和货品编码查询总库存的现有数量
        Integer nowNum = stockMapper.selectNowNumByCode(gooderCode, goodCode, orgCode, providerCode, placeId);
        if (nowNum == null) {
            nowNum = 0;
        }

        //2.把接收量写入库存表
        String stockId = stockMapper.selectByGooderAndCode(gooderCode, goodCode, orgCode, providerCode, placeId);
        if (stockId == null) {
            //新增库存记录
            Stock stock = new Stock();
            stock.setStockId(IdsUtils.getOrderId());
            stock.setOrgCode(orgCode);
            stock.setProviderCode(providerCode);
            stock.setGooderCode(gooderCode);
            stock.setGoodCode(goodCode);
            stock.setCommodityCode(commodityCode);
            stock.setGoodName(goodName);
            stock.setNowNum(receiveNum);
            stock.setUseNum(0);
            stock.setAllotNum(0);
            stock.setPickNum(0);
            stock.setFreezeNum(0);
            stock.setCreateId(userId);
            stock.setCreateDate(timestamp);
            stock.setModifyId(userId);
            stock.setModifyDate(timestamp);

            //关联场地id
            stock.setPlaceId(placeId);

            stockMapper.insert(stock);
        } else {
            //修改库存记录
            Stock stock = stockMapper.selectByPrimaryKey(stockId);
            stock.setStockId(stockId);
            stock.setNowNum(receiveNum + nowNum);
            stock.setModifyId(userId);
            stock.setModifyDate(timestamp);

            stockMapper.updateByPrimaryKey(stock);

        }
    }


    private Integer orderReceive(ReceiveOrder receiveOrder) {

        String receiveId = receiveOrder.getReceiveId();
        List<ReceiveDetail> details = receiveDetailMapper.findDetailByReceiveId(receiveId);
        return getOtherPuchase(details);


    }

    private Integer getOtherPuchase(List<ReceiveDetail> details) {
        //主表收货单状态
        TreeSet<Integer> set = new TreeSet<>();
        for (ReceiveDetail detail : details) {
            set.add(detail.getStatus());
        }

        //收货单状态  0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算

        Integer first = set.first();
        Integer last = set.last();

        if (set.contains(5)) {
            return 5;
        }
        if (first < 6 && set.contains(6)) {
            return 5;
        }
        if (first < 2 && set.contains(2)) {
            return 1;
        }
        if (set.size() == 2 && last == 7) {
            if (first == 0) {
                return 1;
            } else {
                return first;
            }
        }
        if (set.size() == 1) {
            return first;
        }

        return last;
    }


    /**
     * 收货单主表界面进行收货操作
     *
     * @param receiveOrders
     * @param userId
     * @return
     */

    @Override
    public String receiveMainGoods(List<ReceiveOrder> receiveOrders, String placeId, String userId) throws Exception {

        String errorMsg = "";

        String errorMsg1 = "";
        String errorMsg2 = "";
        String errorMsg3 = "";

        String str1 = "";
        String str2 = "";
        String str3 = "";

        for (ReceiveOrder receiveOrder : receiveOrders) {
            Integer sign = 1;
            String receiveId = receiveOrder.getReceiveId();
            //判断订单是否被挂起
            String errorMessage = checkHangUp(receiveId, sign, userId);
            if (StringUtils.isNotBlank(errorMessage)) {
                errorMsg1 += receiveOrder.getReceiveCode() + "," + "\n";
                str1 = "单据已被挂起";
                continue;
            }

            //收货单状态  0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算

            Integer orderStatus = receiveOrder.getOrderStatus();
            if (orderStatus == 2 || orderStatus == 6 || orderStatus == 7) {
                receiveDetailTempMapper.deleteByReceiveId(receiveId);
                errorMsg2 += receiveOrder.getReceiveCode() + "," + "\n";
                str2 = "单据状态不对";
                continue;
            }


            Timestamp timestamp = TimeStampUtils.getTimeStamp();

            //收货明细表收货
            List<ReceiveDetailTemp> tempList = receiveDetailTempMapper.selectAllByReceiveId(receiveId);

            if (tempList == null || tempList.size() == 0) {

                errorMsg3 += receiveOrder.getReceiveCode() + "," + "\n";
                str3 = "请先填写收货明细";
                continue;
            }

            int num = 0;
            int totalReceiveNum = 0;
            int totalRefuseNum = 0;
            for (ReceiveDetailTemp temp : tempList) {

                Integer receiveNum = temp.getReceiveNum();
                Integer refuseNum = temp.getRefuseNum();
                Integer status = temp.getStatus();
                String seatCode = temp.getSeatCode();

                if (receiveNum == null || StringUtils.isEmpty(receiveNum.toString())) {
                    receiveNum = 0;
                }

                if (refuseNum == null || StringUtils.isEmpty(refuseNum.toString())) {
                    refuseNum = 0;
                }

                //只有未开始的订单才能收获
                if (status != 0) {
                    continue;
                }
                //没有进行收货动作操作的不能收获
                if (receiveNum == 0 && refuseNum == 0) {
                    continue;

                }

                if (receiveNum < 0 || refuseNum < 0) {
                    continue;

                }

                //库位为空的不能收货
                if (StringUtils.isBlank(temp.getSeatCode())) {
                    continue;
                }


                ReceiveDetail receiveDetail = receiveDetailMapper.selectByPrimaryKey(temp.getDetailId());

                if (receiveDetail == null) {
                    continue;
                }

                //非未开始状态不能收货
                Integer status1 = receiveDetail.getStatus();
                if (status1 != 0) {
                    continue;
                }

                //补货收货超过接收量的不能收货
                //货主编码
                String gooderCode = temp.getGooderCode();
                //货品编码
                String goodCode = temp.getGoodCode();

                //是否超量验证  0否,1是
                GoodConfig goodConfig = goodConfigMapper.selectGoodConfigByGoodId(gooderCode, goodCode, placeId);

                //无PO收货允许还是警告
                if ("警告".equals(goodConfig.getNoPoReceive())) {
                    if (temp.getPurchaseCode() != null && temp.getPurchaseLineCode() != null) {
                        continue;
                    }
                }

                //预期量
                Integer sumExpectNum;
                //已接收量
                Integer sumReceiveNum;
                String purchaseCode = receiveDetail.getPurchaseCode();
                Integer purchaseLineCode = receiveDetail.getPurchaseLineCode();
                if (purchaseCode == null && purchaseLineCode == null) {
                    sumExpectNum = receiveDetailMapper.selectSumOfExpectNum(gooderCode, goodCode, receiveId);
                    sumReceiveNum = receiveDetailMapper.selectSumOfReceiveNum(gooderCode, goodCode, receiveId);
                } else {
                    sumExpectNum = receiveDetailMapper.selectTotalExpectNum(purchaseCode, purchaseLineCode);
                    sumReceiveNum = receiveDetailMapper.selectTotalReceiveNum(purchaseCode, purchaseLineCode);
                }

                if (goodConfig != null) {
                    if (goodConfig.getBeyondVerify() == 1) {
                        //是否超量验证
                        Double beyondRatio = goodConfig.getBeyondRatio();
                        if (beyondRatio == null) {
                            beyondRatio = 0.0;
                        }

                        Double beyondNum = sumExpectNum * (1 + beyondRatio / 100);
                        if (receiveNum + sumReceiveNum > beyondNum) {
                            continue;
                        }
                    }

                }

                receiveDetail.setReceiveNum(receiveNum);

                //收货添加到库存
                String message = addToStock(userId, timestamp, receiveDetail, placeId);
                if (StringUtils.isNotBlank(message)) {
                    errorMsg += message;
                    continue;
                }

                //当前单据状态的确定
                Integer status2 = 0;
                Boolean bool1 = receiveNum + refuseNum + sumReceiveNum < sumExpectNum;
                if (bool1) {
                    status2 = 1;
                }

                Boolean bool2 = receiveNum + refuseNum + sumReceiveNum >= sumExpectNum;
                if (bool2) {
                    status2 = 2;
                }

                receiveDetail.setReceiveTime(timestamp);
                receiveDetail.setStatus(status2);

                receiveDetail.setRefuseNum(refuseNum);
                receiveDetail.setModifyId(userId);
                receiveDetail.setModifyDate(timestamp);

                receiveDetailMapper.updateByPrimaryKey(receiveDetail);

                if (status2 == 2) {
                    if (purchaseCode == null && purchaseLineCode == null) {

                        receiveDetailMapper.updateSameGoodStatus(gooderCode, goodCode, receiveId, status2);
                    } else {
                        receiveDetailMapper.updateSamePurchaseStatus(purchaseCode, purchaseLineCode, status2);

                        //其他收货单中采购单总状态同步
                        List<ReceiveDetail> rdetails = receiveDetailMapper.selectReceiveDetailByPurchaseCode(purchaseCode);

                        Integer status3 = getOtherPuchase(rdetails);

                        receiveOrderMapper.updateOrderStatusByPurchaseCode(purchaseCode, status3);

                    }

                }
                ++num;
                totalReceiveNum += receiveNum;
                totalRefuseNum += refuseNum;


                //收货以后,库位容量减少
                Integer capacity = storeSeatMapper.selectCapacityBySeatCode(seatCode, placeId);
                if (capacity != null && capacity != 0) {
                    storeSeatMapper.updateSeatCapacity(seatCode, -receiveNum, placeId);
                }

                //这里还需要同步采购单
                if (purchaseCode != null && purchaseLineCode != null) {
                    //同步采购单状态
                    updatePurchaseOrderStatus(receiveDetail, userId);
                }

                //货品动态跟踪
                DynamicGood dynamicGood = new DynamicGood();
                dynamicGood.setKeyId(IdsUtils.getOrderId());
                dynamicGood.setCreateDate(timestamp);
                dynamicGood.setCreateId(userId);
                dynamicGood.setModifyDate(timestamp);
                dynamicGood.setModifyId(userId);
                dynamicGood.setFromSeat(null);
                //rfid为0
                dynamicGood.setRfid(0);
                dynamicGood.setToSeat(receiveDetail.getSeatCode());
                dynamicGood.setSize(receiveDetail.getReceiveNum());
                dynamicGood.setGooderCode(receiveDetail.getGooderCode());
                dynamicGood.setGoodCode(receiveDetail.getGoodCode());
                dynamicGood.setTradeType("收货");

                dynamicGood.setPlaceId(placeId);
                dynamicGoodMapper.insert(dynamicGood);

            }

            //执行过收货操作
            if (num > 0) {
                //只对收货量和状态有影响
                receiveOrder.setReceiveNum(receiveOrder.getReceiveNum() + totalReceiveNum);
                receiveOrder.setRefuseNum(receiveOrder.getRefuseNum() + totalRefuseNum);
                //收货单单据状态确定

                Integer status = orderReceive(receiveOrder);

                receiveOrder.setOrderStatus(status);
                receiveOrder.setModifyId(userId);
                receiveOrder.setModifyDate(timestamp);

                receiveOrderMapper.updateByPrimaryKey(receiveOrder);

            }

            //清空临时表中的数据
            receiveDetailTempMapper.deleteByReceiveId(receiveId);
        }

        //错误消息提示
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg1, str1);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg2, str2);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg3, str3);


        return errorMsg;
    }

    /**
     * 收货时,收货单状态同步采购单
     *
     * @param receiveDetail
     */

    private void updatePurchaseOrderStatus(ReceiveDetail receiveDetail, String userId) {
        //收货时,收货状态同步到采购订单状态
        // 采购单状态 单据状态 0未开始 1已确认 2收货中 3已收货 5 质检中 6已质检 7已结算',
        //收货单状态  0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算

        //1.同步采购单明细表
        Integer purchaseLineCode = receiveDetail.getPurchaseLineCode();
        String purchaseCode = receiveDetail.getPurchaseCode();
        if (purchaseCode != null && purchaseLineCode != null) {

            //采购明细单和收货单是一对多的关系

            //1.1通过采购单号,查找采购单记录
            OrderGoodMapping orderGoodMapping = orderGoodMappingMapper.selectById(receiveDetail.getPurchaseLineCode());


            //获取同一个采购单下的单所有明细
            List<ReceiveDetail> receiveDetails = receiveDetailMapper.selectByPurchaseLineCode(purchaseCode, purchaseLineCode);

            if (receiveDetails != null && receiveDetails.size() > 0) {

                ReceiveDetailVo receiveDetailVo = receiveDetailMapper.selectOrderMapByPurchase(purchaseCode, purchaseLineCode);

                Integer expectSum = receiveDetailVo.getExpectSum();
                if (expectSum == null) {
                    expectSum = 0;
                }

                Integer receiveSum = receiveDetailVo.getReceiveSum();
                if (receiveSum == null) {
                    receiveSum = 0;
                }


                Integer mappingStatus = 1;

                Boolean bool1 = receiveSum > 0 && receiveSum < expectSum;
                if (bool1) {
                    //收货中
                    mappingStatus = 2;
                }

                Boolean bool2 = receiveSum > 0 && receiveSum >= expectSum;

                if (bool2) {
                    //已收货
                    mappingStatus = 3;
                }


                orderGoodMapping.setModifyId(userId);
                orderGoodMapping.setModifyDate(TimeStampUtils.getTimeStamp());
                orderGoodMapping.setStatus(mappingStatus);

                orderGoodMappingMapper.updateByPrimaryKey(orderGoodMapping);

                //2.同步采购单主表
                List<OrderGoodMapping> orderGoodMappings = orderGoodMappingMapper.selectMapperByPurchaseCode(purchaseCode);
                TreeSet<Integer> set = new TreeSet<>();
                for (OrderGoodMapping mapping : orderGoodMappings) {
                    set.add(mapping.getStatus());
                }
                // 采购单状态 单据状态 0未开始 1已确认 2收货中 3已收货 5 质检中 6已质检 7已结算

                Integer first = set.first();
                Integer orderStatus = 1;

                if (set.contains(2)) {
                    orderStatus = 2;
                }
                if (first < 2 && set.contains(3)) {
                    orderStatus = 2;
                }
                if (set.size() == 1) {
                    orderStatus = first;
                }

                orderMapper.updateStatusByPurchaseCode(purchaseCode, orderStatus);
            }

        }

    }


    /**
     * 收货明细界面撤销收货
     *
     * @param userId
     * @return
     */
    @Override
    public String cancelGoods(List<ReceiveDetail> receiveDetails, String placeId, String userId) throws Exception {

        String errorMsg = "";
        String errorMsg1 = "";
        String errorMsg2 = "";
        String errorMsg3 = "";

        String str1 = "";
        String str2 = "";
        String str3 = "";

        //判断收货单是否被挂起
        Integer sign = 0;
        String receiveId = receiveDetails.get(0).getReceiveId();

        errorMsg = checkHangUp(receiveId, sign, userId);

        if (StringUtils.isNotBlank(errorMsg)) {
            return errorMsg;
        }

        int num = 0;
        int totalReceiveNum = 0;
        int totalRefuseNum = 0;
        label:
        for (ReceiveDetail receiveDetail : receiveDetails) {

            receiveId = receiveDetail.getReceiveId();

            Timestamp timestamp = TimeStampUtils.getTimeStamp();
            ReceiveOrder receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);

            //收货单状态  0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算

            //如果收货单中不存在收货中或已收货的单据,则无法取消收货
            Integer orderStatus = receiveOrder.getOrderStatus();
            if (orderStatus == 4 || orderStatus == 6 || orderStatus == 7) {
                errorMsg += "当前单据状态不对，操作不成功\n";
                return errorMsg;
            }


            //只有收货中和已收货订单才能取消收货
            Integer status = receiveDetailMapper.selectStatus(receiveDetail.getDetailId());
            if (status != 1 && status != 2) {
                errorMsg1 += receiveDetail.getId() + "," + "\n";
                str1 = "单据状态不对";
                continue;
            }


            //已经采集的订单不能撤销收货
            if (receiveDetail.getRfid() != null && receiveDetail.getRfid() > 0) {
                errorMsg2 += receiveDetail.getId() + "," + "\n";
                str2 = "已经采集不能撤销收货";
                continue;
            }

            //采购单中如果已结算,则不能撤销收货
            String purchaseCode = receiveDetail.getPurchaseCode();
            Integer purchaseLineCode = receiveDetail.getPurchaseLineCode();

            List<ReceiveDetail> details = receiveDetailMapper.selectByPurchaseLineCode(purchaseCode, purchaseLineCode);
            if (details != null && details.size() > 0) {
                OrderGoodMapping orderGoodMapping = orderGoodMappingMapper.selectById(purchaseLineCode);
                if (orderGoodMapping.getStatus() == 7) {
                    errorMsg3 += receiveDetail.getId() + "," + "\n";
                    str3 = "采购单中已结算";
                    continue label;
                }
            }

            //收货量
            Integer receiveNum = receiveDetail.getReceiveNum();

            //拒收量
            Integer refuseNum = receiveDetail.getRefuseNum();

            totalReceiveNum += receiveNum;
            totalRefuseNum += refuseNum;

            //库位编码
            String seatCode = receiveDetail.getSeatCode();
            //货主编码
            String gooderCode = receiveDetail.getGooderCode();
            //货品编码
            String goodCode = receiveDetail.getGoodCode();

            //组织编码
            String orgCode = receiveDetail.getOrgCode();

            //供应商编码
            String providerCode = receiveDetail.getProviderCode();


            receiveDetail.setReceiveNum(0);
            receiveDetail.setRefuseNum(0);
            receiveDetail.setQualityNum(0);
            receiveDetail.setStatus(0);
            receiveDetail.setModifyId(userId);
            receiveDetail.setModifyDate(timestamp);
            receiveDetailMapper.updateByPrimaryKey(receiveDetail);


            if (purchaseCode == null && purchaseLineCode == null) {
                receiveDetailMapper.updateOtherStatus(gooderCode, goodCode, receiveId);
            } else {
                receiveDetailMapper.updateOtherPurchaseStatus(purchaseCode, purchaseLineCode);

                //其他收货单中采购单总状态同步
                List<ReceiveDetail> rdetails = receiveDetailMapper.selectReceiveDetailByPurchaseCode(purchaseCode);

                Integer status3 = getOtherPuchase(rdetails);

                receiveOrderMapper.updateOrderStatusByPurchaseCode(purchaseCode, status3);
            }

            ++num;

            //收货时,收货状态同步到采购订单状态
            // 采购单状态 0未开始 1 已确认 2 收货中 3 已收货 4 已质检 5 质检中 6 已结算
            //收货单状态  0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算

            if (purchaseCode != null && purchaseLineCode != null) {

                //采购明细单状态的变化
                updatePurchaseOrderStatus(receiveDetail, userId);

            }
            //库位容量恢复
            Integer capacity = storeSeatMapper.selectCapacityBySeatCode(seatCode, placeId);
            if (capacity != null && capacity != 0) {
                storeSeatMapper.updateSeatCapacity(seatCode, receiveNum, placeId);
            }


            //取消收货减库存
            reduceStock(receiveNum, seatCode, gooderCode, goodCode, orgCode, providerCode, placeId);

        }

        //执行了取消收货操作
        if (num > 0) {
            //收货单状态的变更
            Timestamp timestamp = TimeStampUtils.getTimeStamp();
            ReceiveOrder receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);

            //收货量的变化
            receiveOrder.setReceiveNum(receiveOrder.getReceiveNum() - totalReceiveNum);
            receiveOrder.setRefuseNum(receiveOrder.getRefuseNum() - totalRefuseNum);


            //收货单单据状态确定
            updateReceiveOrder(userId, timestamp, receiveOrder);
        }

        //提示错误消息收集
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg1, str1);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg2, str2);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg3, str3);

        return errorMsg;

    }


    private void reduceStock(Integer receiveNum, String seatCode, String gooderCode, String goodCode,
                             String orgCode, String providerCode, String placeId) {

        //库位减库存
        seatStockMapper.updateSeatStockNowNum(gooderCode, goodCode, seatCode, receiveNum, orgCode, providerCode, placeId);

        //取消收货减总库存表库存
        stockMapper.updateStockNowNum(gooderCode, goodCode, receiveNum,
                orgCode, providerCode, placeId);

    }

    /**
     * 收货单界面取消收货
     *
     * @param receiveOrders
     * @param userId
     * @return
     */
    @Override
    public String cancelMainGoods(List<ReceiveOrder> receiveOrders, String placeId, String userId) throws Exception {

        String errorMsg = "";
        String errorMsg1 = "";
        String errorMsg2 = "";

        String str1 = "";
        String str2 = "";

        for (ReceiveOrder receiveOrder : receiveOrders) {

            Timestamp timestamp = TimeStampUtils.getTimeStamp();
            String receiveId = receiveOrder.getReceiveId();

            Integer sign = 1;
            //收货明细单取消收货

            //判断订单是否被挂起
            String errorMessage = checkHangUp(receiveId, sign, userId);
            if (StringUtils.isNotBlank(errorMessage)) {
                errorMsg1 += receiveOrder.getReceiveCode() + "," + "\n";
                str1 = "已被挂起";
                continue;
            }

            //只有收货中和已收货的单据状态才能取消收货

            //防止重复点击,及时查询数据库
            receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);
            if (receiveOrder.getOrderStatus() != 1 && receiveOrder.getOrderStatus() != 2) {
                errorMsg2 += receiveOrder.getReceiveCode() + "," + "\n";
                str2 = "已经采集不能撤销收货";
                continue;
            }

            //收货明细表状态的变更
            int totalReceiveNum = 0;
            int totalRefuseSum = 0;
            int num = 0;
            List<ReceiveDetail> receiveDetails = receiveDetailMapper.selectListByReceiveId(receiveId);

            if (receiveDetails != null && receiveDetails.size() > 0) {

                for (ReceiveDetail receiveDetail : receiveDetails) {
                    if (receiveDetail.getStatus() != 1 && receiveDetail.getStatus() != 2) {
//						errorMsg3 += "收货单" + receiveOrder.getReceiveCode() + "中,第 " + receiveDetail.getId() + "条记录单据状态不对,操作不成功!\n";
                        continue;
                    }

                    //已经采集的订单不能撤销收货
                    if (receiveDetail.getRfid() > 0) {
//						errorMsg4 += "收货单" + receiveOrder.getReceiveCode() + "中,第 " + receiveDetail.getId() + "条记录已经采集,操作不成功!\n";
                        continue;
                    }


                    String purchaseCode = receiveDetail.getPurchaseCode();
                    Integer purchaseLineCode = receiveDetail.getPurchaseLineCode();

                    //已结算的采购单不能取消收货
                    List<ReceiveDetail> details = receiveDetailMapper.selectByPurchaseLineCode(purchaseCode, purchaseLineCode);
                    if (details != null && details.size() > 0) {
                        OrderGoodMapping orderGoodMapping = orderGoodMappingMapper.selectById(purchaseLineCode);
                        if (orderGoodMapping.getStatus() == 6) {
//							errorMsg5 += "收货单" + receiveOrder.getReceiveCode() + "中,第 " + receiveDetail.getId() + "条记录采购单已结算,操作不成功!\n";
                            continue;
                        }
                    }

                    //收货量
                    Integer receiveNum = receiveDetail.getReceiveNum();
                    //拒收量
                    Integer refuseNum = receiveDetail.getRefuseNum();
                    //库位编码
                    String seatCode = receiveDetail.getSeatCode();
                    //货主编码
                    String gooderCode = receiveDetail.getGooderCode();
                    //货品编码
                    String goodCode = receiveDetail.getGoodCode();

                    //组织编码
                    String orgCode = receiveDetail.getOrgCode();

                    //供应商编码
                    String providerCode = receiveDetail.getProviderCode();

                    //收货明细单状态变为"未开始"
                    totalReceiveNum += receiveNum;
                    totalRefuseSum += refuseNum;

                    receiveDetail.setReceiveNum(0);
                    receiveDetail.setRefuseNum(0);
                    receiveDetail.setStatus(0);
                    receiveDetail.setModifyId(userId);
                    receiveDetail.setModifyDate(timestamp);
                    receiveDetailMapper.updateByPrimaryKey(receiveDetail);

                    ++num;

                    //收货时,收货状态同步到采购订单状态
                    // 采购单状态 0未开始 1 已确认 2 收货中 3 已收货 4 已质检 5 质检中 6 已结算
                    //收货单状态  0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
                    if (purchaseCode != null && purchaseLineCode != null) {
                        updatePurchaseOrderStatus(receiveDetail, userId);
                    }

                    if (purchaseCode != null) {

                        //其他收货单中采购单总状态同步
                        List<ReceiveDetail> rdetails = receiveDetailMapper.selectReceiveDetailByPurchaseCode(purchaseCode);

                        Integer status3 = getOtherPuchase(rdetails);

                        receiveOrder.setOrderStatus(status3);
                        receiveOrder.setModifyId(userId);
                        receiveOrder.setModifyDate(timestamp);

                        receiveOrderMapper.updateByPrimaryKey(receiveOrder);
                    }

                    //取消收货减库存
                    reduceStock(receiveNum, seatCode, gooderCode, goodCode, orgCode, providerCode, placeId);

                }
            }


            //收货单状态变更
            if (num > 0) {
                receiveOrder.setReceiveNum(receiveOrder.getReceiveNum() - totalReceiveNum);
                receiveOrder.setRefuseNum(receiveOrder.getRefuseNum() - totalRefuseSum);
                //收货单单据状态确定
                updateReceiveOrder(userId, timestamp, receiveOrder);
            }
        }


        //消息收集
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg1, str1);

        errorMsg = ErrorMsg.getString(errorMsg, errorMsg2, str2);

        return errorMsg;
    }


    /**
     * 上架前合法性校验
     *
     * @param receiveDetails
     * @param userId
     */
    @Override
    public Result shelfGoods(List<ReceiveDetail> receiveDetails, String placeId, String userId) {


        Result result = new Result();

        String errorMsg = "";
        String errorMsg1 = "";
        String errorMsg2 = "";
        String errorMsg3 = "";

        String str1 = "";
        String str2 = "";
        String str3 = "";


        //判断收货单是否被挂起
        Integer sign = 0;
        String receiveId = receiveDetails.get(0).getReceiveId();

        errorMsg = checkHangUp(receiveId, sign, userId);

        if (StringUtils.isNotBlank(errorMsg)) {
            return result.setMessage(errorMsg);
        }

        List<ShelfPojo> list = new ArrayList<>();

        for (ReceiveDetail receiveDetail : receiveDetails) {

            //判断是否质检 如果不质检,收货单可以直接上架
            String gooderCode = receiveDetail.getGooderCode();
            String goodCode = receiveDetail.getGoodCode();
            GoodConfig goodConfig = goodConfigMapper.selectGoodConfigByGoodId(gooderCode, goodCode, placeId);
            Integer isQuality = goodConfig.getIsQuality();
            Integer rfidGather = goodConfig.getRfidGather();

            Integer status = receiveDetail.getStatus();

            //单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
            if (isQuality == 1) {

                //过检量或次品量不能为0
                if (receiveDetail.getQualityNum() == 0 || receiveDetail.getSecondNum() == 0) {
                    errorMsg1 += receiveDetail.getId() + "," + "\n";
                    str1 = "请先质检";
                    continue;

                }

                //0表示不质检,1表示质检
                //只有质检中,已质检的单据才能上架
                if (status != 3 && status != 4) {
                    errorMsg2 += receiveDetail.getId() + "," + "\n";
                    str2 = "单据状态不对";
                    continue;
                }


            } else {
                if (status < 1 || status > 4) {
                    errorMsg2 += receiveDetail.getId() + "," + "\n";
                    str2 = "单据状态不对";
                }


            }

            //判断是否进行rfid采集 0否,1是
            if (rfidGather == 1) {
                if (receiveDetail.getRfid() == null || receiveDetail.getRfid() == 0) {
                    errorMsg3 += receiveDetail.getId() + "," + "\n";
                    str3 = "请先采集";
                    continue;
                }
            }


            //货品标记 2正品,3次品
            if (isQuality == 1) {
                //进行质检
                Integer qualityNum = receiveDetail.getQualityNum();
                if (qualityNum > 0) {
                    ShelfPojo shelfPojo = new ShelfPojo();
                    shelfPojo.setId(receiveDetail.getId());
                    shelfPojo.setDetailId(receiveDetail.getDetailId());
                    shelfPojo.setId(receiveDetail.getId());
                    shelfPojo.setGooderCode(receiveDetail.getGooderCode());
                    shelfPojo.setGoodCode(receiveDetail.getGoodCode());
                    shelfPojo.setGoodName(receiveDetail.getGoodName());
                    shelfPojo.setGoodModel(receiveDetail.getGoodModel());
                    shelfPojo.setStatus(receiveDetail.getStatus());
                    shelfPojo.setExpectNum(receiveDetail.getExpectNum());
                    shelfPojo.setAcceptNum(qualityNum);
                    shelfPojo.setGoodSign(2);
                    //隐藏字段 方便后面统计上架量
                    shelfPojo.setIsQuality(1);
                    list.add(shelfPojo);
                }

                Integer secondNum = receiveDetail.getSecondNum();

                if (secondNum > 0) {
                    ShelfPojo shelfPojo = new ShelfPojo();
                    shelfPojo.setId(receiveDetail.getId());
                    shelfPojo.setDetailId(receiveDetail.getDetailId());
                    shelfPojo.setId(receiveDetail.getId());
                    shelfPojo.setGooderCode(receiveDetail.getGooderCode());
                    shelfPojo.setGoodCode(receiveDetail.getGoodCode());
                    shelfPojo.setGoodName(receiveDetail.getGoodName());
                    shelfPojo.setGoodModel(receiveDetail.getGoodModel());
                    shelfPojo.setStatus(receiveDetail.getStatus());
                    shelfPojo.setExpectNum(receiveDetail.getExpectNum());
                    shelfPojo.setAcceptNum(secondNum);
                    shelfPojo.setGoodSign(3);
                    //隐藏字段 方便后面统计上架量
                    shelfPojo.setIsQuality(1);
                    list.add(shelfPojo);
                }

            } else {
                //不进行质检
                ShelfPojo shelfPojo = new ShelfPojo();
                shelfPojo.setDetailId(receiveDetail.getDetailId());
                shelfPojo.setId(receiveDetail.getId());
                shelfPojo.setGooderCode(receiveDetail.getGooderCode());
                shelfPojo.setGoodCode(receiveDetail.getGoodCode());
                shelfPojo.setGoodName(receiveDetail.getGoodName());
                shelfPojo.setGoodModel(receiveDetail.getGoodModel());
                shelfPojo.setStatus(receiveDetail.getStatus());
                shelfPojo.setExpectNum(receiveDetail.getExpectNum());
                shelfPojo.setAcceptNum(receiveDetail.getReceiveNum());
                shelfPojo.setGoodSign(2);
                //隐藏字段 方便后面统计上架量
                shelfPojo.setIsQuality(0);
                list.add(shelfPojo);

            }
        }

        result.setData(list);

        //消息收集
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg1, str1);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg2, str2);
        errorMsg = ErrorMsg.getString(errorMsg, errorMsg3, str3);


        result.setMessage(errorMsg);

        return result;

    }

    /**
     * 上架时分配库位
     *
     * @param shelfPojos
     * @return
     */

    @Override
    public Result selectAllotSeat(List<ShelfPojo> shelfPojos, String placeId) {

        Result result = new Result();

        List<ShelfPojo> list = new ArrayList<>();

        String errorMsg = "";
        String errorMsg1 = "";

        label:
        for (ShelfPojo shelfPojo : shelfPojos) {

            String gooderCode = shelfPojo.getGooderCode();
            String goodCode = shelfPojo.getGoodCode();

            String detailId = shelfPojo.getDetailId();

            ReceiveDetail receiveDetail = receiveDetailMapper.selectByPrimaryKey(detailId);
            String receiveSeatCode = receiveDetail.getSeatCode();

            //默认按照第 一条记录匹配
            if (shelfPojo.getGoodSign() == 2) {

                String shelfSeatCode = null;
                //如果有首选区,就匹配首选区
                Integer fistArea = 1;
                String areaCode = goodAreaMapper.selectAreaCodeByGoodId(gooderCode, goodCode, fistArea, placeId);
                if (areaCode != null) {
                    List<String> seatCodes = storeSeatMapper.selectByAreaCode(areaCode, placeId);

                    if (seatCodes != null && seatCodes.size() > 0) {

                        for (String seatCode : seatCodes) {

                            //收货库位和上架库位不能混合
                            if (receiveSeatCode.equals(seatCode)) {
                                continue;
                            }

                            String seatTag = storeSeatMapper.selectSeatTagBySeatCode(seatCode, placeId);
                            String seatType = storeSeatMapper.selectSeatTypeBySeatCode(seatCode, placeId);

                            if ("无".equals(seatTag) && !"暂存".equals(seatType)) {
                                shelfSeatCode = seatCode;
                                shelfPojo.setSeatCode(shelfSeatCode);
                                list.add(shelfPojo);
                                continue label;
                            }

                        }
                    }
                }

                //首选区没有匹配库位,继续往下走
                List<String> areaCodes = goodAreaMapper.selectAreaCodeByGoodIdAndPlaceId(gooderCode, goodCode, placeId);
                if (areaCodes == null || areaCodes.size() == 0) {
                    errorMsg1 += shelfPojo.getId() + ",";
                    shelfPojo.setSeatCode(shelfSeatCode);
                    list.add(shelfPojo);
                    continue label;
                }

                for (String area : areaCodes) {

                    List<String> seatCodes = storeSeatMapper.selectByAreaCode(area, placeId);

                    if (seatCodes == null || seatCodes.size() == 0) {
                        continue;

                    }
                    for (String seat : seatCodes) {

                        String seatTag = storeSeatMapper.selectSeatTagBySeatCode(seat, placeId);
                        String seatType = storeSeatMapper.selectSeatTypeBySeatCode(seat, placeId);

                        if ("无".equals(seatTag) && !"暂存".equals(seatType)) {
                            shelfSeatCode = seat;
                            shelfPojo.setSeatCode(shelfSeatCode);
                            list.add(shelfPojo);
                            continue label;
                        }
                    }

                }


                if (shelfSeatCode == null) {
                    errorMsg1 += shelfPojo.getId() + ",";
                    shelfPojo.setSeatCode(shelfSeatCode);
                    list.add(shelfPojo);
                }

            } else {

                GoodConfig goodConfig = goodConfigMapper.selectGoodConfigByGoodId(gooderCode, goodCode, placeId);
                String badAcceptSeat = goodConfig.getBadAcceptSeat();
                if (badAcceptSeat == null) {
                    errorMsg1 += shelfPojo.getId() + ",";
                }

                shelfPojo.setSeatCode(badAcceptSeat);
                list.add(shelfPojo);
            }
        }


        if (StringUtils.isBlank(errorMsg1)) {
            errorMsg = "分配成功!";
        } else {
            errorMsg = "第 " + errorMsg1.substring(0, errorMsg1.lastIndexOf(",")) + "条记录库区配置中无可用库位,请手动选择!";
        }

        result.setMessage(errorMsg);
        result.setData(list);
        return result;
    }

    /**
     * 上架保存操作
     *
     * @param shelfPojos
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public String shelfGoodSave(List<ShelfPojo> shelfPojos, String placeId, String userId) throws Exception {
        Timestamp timestamp = TimeStampUtils.getTimeStamp();
        String errorMsg = "";


        //防止用户重复点击上架问题
        int num = 0;
        for (ShelfPojo shelfPojo : shelfPojos) {
            if (shelfPojo != null) {
                ReceiveDetail detail = receiveDetailMapper.selectByPrimaryKey(shelfPojo.getDetailId());
                Integer status = detail.getStatus();
                if (status == 5 || status == 6) {
                    num++;
                }
            }
        }
        if (num == shelfPojos.size()) {
            errorMsg = "请不要重复点击上架!";
            return errorMsg;
        }

        for (ShelfPojo shelfPojo : shelfPojos) {

            //上架再分配库位，库位不能为空，库位容量要足够
            String shelfSeatCode = shelfPojo.getSeatCode();
            if (StringUtils.isEmpty(shelfSeatCode)) {
                errorMsg = "选项中存在库位为空的记录,操作不成功!";
                return errorMsg;
            }


            //分配库位之前判断库位容量是否足够
            Integer capacity = storeSeatMapper.selectCapacityBySeatCode(shelfSeatCode, placeId);

            int acceptNum = shelfPojo.getAcceptNum();
            if (capacity != null && capacity != 0) {
                if (acceptNum > capacity) {
                    errorMsg = shelfSeatCode + "库位容量不足,请重新  分配库位!";
                    return errorMsg;
                }
            }

            ReceiveDetail receiveDetail = receiveDetailMapper.selectByPrimaryKey(shelfPojo.getDetailId());

            //临时库位
            String tempSeatCode = receiveDetail.getSeatCode();

            //临时库位和上架库位不能相同
            if (tempSeatCode.equals(shelfSeatCode)) {
                errorMsg = "收货库位和上架库位不能相同,操作不成功!";
                return errorMsg;
            }
        }


        Integer totalShelfNum = 0;
        int count = 0;
        String receiveId = null;
        for (ShelfPojo shelfPojo : shelfPojos) {

            //单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算

            //上架以后,收货明细单改变状态
            ReceiveDetail receiveDetail = receiveDetailMapper.selectByPrimaryKey(shelfPojo.getDetailId());
            //上架中和已上架的不能重复点击上架
            if (receiveDetail.getStatus() == 5 || receiveDetail.getStatus() == 6) {
                continue;
            }

            receiveId = receiveDetail.getReceiveId();
            ReceiveOrder receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);


            Integer acceptNum = shelfPojo.getAcceptNum();
            //临时库位
            String tempSeatCode = receiveDetail.getSeatCode();
            //上架库位
            String shelfSeatCode = shelfPojo.getSeatCode();
            //库位容量
            Integer capacity = storeSeatMapper.selectCapacityBySeatCode(shelfSeatCode, placeId);


            //同步上架量
            Integer shelfNum;
            if (shelfPojo.getIsQuality() == 0) {
                //不质检
                shelfNum = receiveDetail.getReceiveNum();

            } else {
                //进行质检
                shelfNum = receiveDetail.getQualityNum() + receiveDetail.getSecondNum();
            }


            Integer receiveNum = receiveDetail.getReceiveNum();


            String detailId = receiveDetail.getDetailId();


            if (shelfNum < receiveNum) {
                //1.如果上架量<接收量,单据状态上架中
                receiveDetail.setStatus(5);
            } else {
                //2.如果上架量 = 接收量,单据状态已上架
                receiveDetail.setStatus(6);
            }


            String gooderCode = receiveDetail.getGooderCode();
            String goodCode = receiveDetail.getGoodCode();


            //判断是否进行rfid采集,如果是,库位绑定rfid标签
            GoodConfig goodConfig = goodConfigMapper.selectGoodConfigByGoodId(gooderCode, goodCode, placeId);
            Integer rfidGather = goodConfig.getRfidGather();
            if (rfidGather != null && rfidGather == 1) {
                //进行rfid采集
                List<RfidLabel> rfidLabels = rfidLabelMapper.selectByGooderAndGoodCode(gooderCode, goodCode);
                //同一货品只能上到同一库位
                String seat = null;
                for (RfidLabel rfidLabel : rfidLabels) {
                    String seatCode = rfidLabel.getSeatCode();
                    if (StringUtils.isNotBlank(seatCode)) {
                        seat = seatCode;
                        break;
                    }
                }

                for (RfidLabel rfidLabel : rfidLabels) {
                    //上架库位
                    if (seat != null) {
                        rfidLabel.setSeatCode(seat);
                    } else {

                        rfidLabel.setSeatCode(shelfSeatCode);
                    }
                    rfidLabelMapper.updateByPrimaryKey(rfidLabel);
                }

            }

            receiveDetail.setShelfNum(shelfNum);
            receiveDetail.setDetailId(detailId);
            receiveDetail.setModifyId(userId);
            receiveDetail.setModifyDate(timestamp);

            //上架时间
            receiveDetail.setShelfTime(timestamp);

            //同步上架库位
            receiveDetail.setSeatCode(shelfSeatCode);
            receiveDetailMapper.updateByPrimaryKey(receiveDetail);
            totalShelfNum += shelfNum;
            ++count;


            //上架分配新的库位，正品分配到正品库位，次品分配到次品库位
            //goodSign标记,2表示正品,3表示次品
            Integer goodSign = shelfPojo.getGoodSign();

            //补充组织编码和供应商编码
            String orgCode = receiveDetail.getOrgCode();
            String providerCode = receiveDetail.getProviderCode();


            //上架库位库存修改操作
            shelfSeatStock(userId, timestamp, receiveDetail, shelfSeatCode, acceptNum, gooderCode, goodCode, goodSign, orgCode, providerCode, placeId);

            //临时库位库存现有量减少
            tempSeatStock(userId, timestamp, tempSeatCode, acceptNum, gooderCode, goodCode, orgCode, providerCode, placeId);

            //上架以后临时库位容量恢复,新库位容量减少
            Integer tempCapacity = storeSeatMapper.selectCapacityBySeatCode(tempSeatCode, placeId);
            if (tempCapacity != null && tempCapacity != 0) {
                storeSeatMapper.updateSeatCapacity(tempSeatCode, shelfNum, placeId);
            }

            if (capacity != null && capacity != 0) {
                storeSeatMapper.updateSeatCapacity(shelfSeatCode, -shelfNum, placeId);
            }

            //上架总库存修改操作  收货时,货品就存入总库存表,所以库存已经存在
            shelfStock(userId, timestamp, acceptNum, gooderCode, goodCode, goodSign, orgCode, providerCode, placeId);


            //货品动态跟踪
            DynamicGood dynamicGood = new DynamicGood();
            dynamicGood.setKeyId(IdsUtils.getOrderId());
            dynamicGood.setCreateDate(timestamp);
            dynamicGood.setCreateId(userId);
            dynamicGood.setModifyDate(timestamp);
            dynamicGood.setModifyId(userId);
            dynamicGood.setFromSeat(receiveDetail.getSeatCode());
            dynamicGood.setToSeat(shelfPojo.getSeatCode());
            dynamicGood.setSize(shelfPojo.getAcceptNum());
            dynamicGood.setGooderCode(receiveDetail.getGooderCode());
            dynamicGood.setGoodCode(receiveDetail.getGoodCode());
            //rfid数量,如果采集则采集量等于上架量,如果不采集,采集量等于0
            if (rfidGather != null && rfidGather == 1) {
                dynamicGood.setRfid(shelfNum);
            } else {
                dynamicGood.setRfid(0);
            }
            //单据号
            dynamicGood.setBillCode(receiveOrder.getReceiveCode());
            dynamicGood.setTradeType("上架");

            dynamicGood.setPlaceId(placeId);
            dynamicGoodMapper.insert(dynamicGood);

        }

        if (count > 0) {
            //获取主表id
            ReceiveOrder receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);

            //上架量同步
            //次品和正品都都上架
            receiveOrder.setShelfNum(receiveOrder.getShelfNum() + totalShelfNum);

            //收货单单据状态确定
            Integer status = orderReceive(receiveOrder);

            receiveOrder.setOrderStatus(status);

            //上次上架时间
            receiveOrder.setLastShelfTime(timestamp);
            receiveOrder.setModifyId(userId);
            receiveOrder.setModifyDate(timestamp);

            receiveOrderMapper.updateByPrimaryKey(receiveOrder);
        }

        return errorMsg;
    }

    private void tempSeatStock(String userId, Timestamp timestamp, String tempSeatCode, int acceptNum, String gooderCode, String goodCode, String orgCode, String provider, String placeId) {
        //更新操作 临时库位现有量减少
        SeatStock seatStock = seatStockMapper.selectBySeatCode(gooderCode, goodCode, tempSeatCode, orgCode, provider, placeId);
        Integer nowNum = seatStock.getNowNum();
        seatStock.setNowNum(nowNum - acceptNum);
        seatStock.setModifyId(userId);
        seatStock.setModifyDate(timestamp);
        seatStockMapper.updateByPrimaryKey(seatStock);


    }


    private void shelfStock(String userId, Timestamp timestamp, int acceptNum, String gooderCode, String goodCode, Integer goodSign, String orgCode, String providerCode, String placeId) {
        //1.1上架时,正品添加到可用量,次品添加到冻结量
        //1.2上架时总库存只有修改操作
        String stockId = stockMapper.selectByGooderAndCode(gooderCode, goodCode, orgCode, providerCode, placeId);
        Stock stock = stockMapper.selectByPrimaryKey(stockId);
        stock.setModifyId(userId);
        stock.setModifyDate(timestamp);
        if (goodSign == 2) {
            //正品加入库存
            Integer useNum = stockMapper.selectUseNum(stockId, placeId);
            stock.setUseNum(useNum + acceptNum);
        } else {
            //次品加入库存
            Integer freezeNum = stockMapper.selectFreezeNum(stockId, placeId);
            stock.setFreezeNum(freezeNum + acceptNum);

        }
        stockMapper.updateByPrimaryKey(stock);
    }


    private void shelfSeatStock(String userId, Timestamp timestamp, ReceiveDetail receiveDetail, String seatCode, int acceptNum, String gooderCode, String goodCode, Integer goodSign, String orgCode, String providerCode, String placeId) throws Exception {

        if (goodSign == 2) {
            //1.库位库存可用量添加
            //1.1查看当前sku的正品库位库存是否存在
            String stockId = seatStockMapper.selectStockId(gooderCode, goodCode, seatCode, orgCode, providerCode, placeId);
            if (stockId == null) {
                //1.2新增库位库存
                SeatStock seatStock = new SeatStock();
                seatStock.setStockId(IdsUtils.getOrderId());
                seatStock.setOrgCode(orgCode);
                seatStock.setProviderCode(providerCode);
                seatStock.setGooderCode(receiveDetail.getGooderCode());
                seatStock.setGooderCode(receiveDetail.getGooderCode());
                seatStock.setGoodCode(receiveDetail.getGoodCode());
                seatStock.setGoodName(receiveDetail.getGoodName());
                String commodityCode = goodMapper.selectCommodityCodeByGoodCode(goodCode);
                seatStock.setCommodityCode(commodityCode);
                //这里是上架库位
                seatStock.setSeatCode(seatCode);
                //现有量
                seatStock.setNowNum(receiveDetail.getReceiveNum());
                //上架时正品上架量
                seatStock.setUseNum(acceptNum);
                seatStock.setFreezeNum(0);
                seatStock.setAllotNum(0);
                seatStock.setPickNum(0);
                //是否冻结, 默认为否,即0
                seatStock.setFreezeStatus(0);
                seatStock.setCreateId(userId);
                seatStock.setCreateDate(timestamp);
                seatStock.setModifyId(userId);
                seatStock.setModifyDate(timestamp);

                //关联场地id
                seatStock.setPlaceId(placeId);

                seatStockMapper.insert(seatStock);
            } else {
                //1.3修改库位库存
                SeatStock st = seatStockMapper.selectByPrimaryKey(stockId);
                //现有量增加
                st.setNowNum(st.getNowNum() + acceptNum);
                //可用量增加
                st.setUseNum(st.getUseNum() + acceptNum);
                st.setModifyId(userId);
                st.setModifyDate(timestamp);
                seatStockMapper.updateByPrimaryKey(st);
            }

        } else {
            //次品库位库存修改操作 goodSign为3
            String stockId = seatStockMapper.selectStockId(gooderCode, goodCode, seatCode, orgCode, providerCode, placeId);
            if (stockId == null) {
                //1.2新增库位库存
                SeatStock seatStock = new SeatStock();
                seatStock.setStockId(IdsUtils.getOrderId());
                seatStock.setOrgCode(orgCode);
                seatStock.setProviderCode(providerCode);
                seatStock.setGooderCode(receiveDetail.getGooderCode());
                String goodCode1 = receiveDetail.getGoodCode();
                seatStock.setGoodCode(goodCode1);
                seatStock.setGoodName(receiveDetail.getGoodName());
                String commodityCode = goodMapper.selectCommodityCodeByGoodCode(goodCode1);
                seatStock.setCommodityCode(commodityCode);
                seatStock.setSeatCode(receiveDetail.getSeatCode());
                //现有量
                seatStock.setNowNum(receiveDetail.getReceiveNum());
                //可用量
                seatStock.setUseNum(0);
                //上架时次品上架量
                seatStock.setFreezeNum(acceptNum);
                seatStock.setAllotNum(0);
                seatStock.setPickNum(0);
                //是否冻结, 默认为否,即0
                seatStock.setFreezeStatus(0);
                seatStock.setCreateId(userId);
                seatStock.setCreateDate(timestamp);
                seatStock.setModifyId(userId);
                seatStock.setModifyDate(timestamp);

                //关联场地id
                seatStock.setPlaceId(placeId);

                //向库位库存中添加记录
                seatStockMapper.insert(seatStock);
            } else {
                //1.3修改库位库存
                SeatStock st = seatStockMapper.selectByPrimaryKey(stockId);
                //现有量增加
                st.setNowNum(st.getNowNum() + acceptNum);
                //冻结量增加
                st.setFreezeNum(st.getFreezeNum() + acceptNum);
                st.setModifyId(userId);
                st.setModifyDate(timestamp);
                seatStockMapper.updateByPrimaryKey(st);
            }
        }
    }


    /**
     * 收货明细界面结算操作
     *
     * @param receiveDetails
     * @param userId
     * @return
     */

    @Override
    public Result clearGoods(List<ReceiveDetail> receiveDetails, Boolean forceClear, String userId) {

        String errorMsg = "";
        Result result = new Result();
        List<ReceiveDetail> list = new ArrayList<>();

        //判断收货单是否被挂起
        Integer sign = 0;
        String receiveId = receiveDetails.get(0).getReceiveId();
        errorMsg = checkHangUp(receiveId, sign, userId);

        if (StringUtils.isNotBlank(errorMsg)) {
            return result.setMessage(errorMsg);
        }

        for (ReceiveDetail receiveDetail : receiveDetails) {

            //单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
            Integer status = receiveDetail.getStatus();
            if (status == 0 || status == 7) {
                errorMsg += "第 " + receiveDetail.getId() + "条记录单据状态不对，操作不成功!\n";
                continue;
            }
        }

        if (StringUtils.isNotBlank(errorMsg)) {
            return result.setMessage(errorMsg);
        }

        Timestamp timestamp = TimeStampUtils.getTimeStamp();
        int num = 0;
        for (ReceiveDetail receiveDetail : receiveDetails) {

            Integer status = receiveDetail.getStatus();
            if (status == 0 || status == 7) {
                continue;
            }

            if (!forceClear) {
                //不能强制结算
                if (status != 2 && status != 6) {
                    errorMsg += "第 " + receiveDetail.getId() + "条记录单据状态不对,是否强制结算!\n";
                    list.add(receiveDetail);
                    continue;
                }

                clearDetailSuccess(userId, timestamp, receiveDetail);
                ++num;

            } else {
                //能强制结算
                //单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
                if (status == 1 || status == 3 || status == 4 || status == 5) {
                    clearDetailSuccess(userId, timestamp, receiveDetail);
                    ++num;
                }
            }
            receiveId = receiveDetail.getReceiveId();

        }
        //收货单主表状态的变化
        if (num > 0) {

            ReceiveOrder receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);
            updateReceiveOrder(userId, timestamp, receiveOrder);
        }

        return result.setMessage(errorMsg).setData(list);
    }


    private void clearDetailSuccess(String userId, Timestamp timestamp, ReceiveDetail detail) {
        detail.setStatus(7);
        detail.setModifyId(userId);
        detail.setModifyDate(timestamp);
        receiveDetailMapper.updateByPrimaryKey(detail);

    }


    /**
     * 主页面结算操作
     *
     * @param receiveOrders
     * @param forceClear
     * @param userId
     * @return
     */

    @Override
    public Result clearMainGoods(List<ReceiveOrder> receiveOrders, Boolean forceClear, String userId) {

        Timestamp timestamp = TimeStampUtils.getTimeStamp();

        String errorMsg = "";

        Result result = new Result();

        Set<ReceiveOrder> set = new HashSet<>();

        for (ReceiveOrder receiveOrder : receiveOrders) {

            String receiveId = receiveOrder.getReceiveId();

            Integer sign = 1;
            //判断订单是否被挂起
            String message = checkHangUp(receiveId, sign, userId);
            if (StringUtils.isNotBlank(message)) {
                errorMsg += message;
            }

            ReceiveOrder order = receiveOrderMapper.selectByPrimaryKey(receiveId);
            //收货单状态为未开始和已结算的订单不能结算
            if (order.getOrderStatus() == 0 || order.getOrderStatus() == 7) {
                errorMsg += "收货单号为" + receiveOrder.getReceiveCode() + "的记录单据状态不对，操作不成功!\n";
            }

        }

        if (StringUtils.isNotBlank(errorMsg)) {
            return result.setMessage(errorMsg);
        }


        for (ReceiveOrder receiveOrder : receiveOrders) {

            String receiveId = receiveOrder.getReceiveId();
            //单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算

            //主表收货单的所有明细记录为
            List<ReceiveDetail> details = receiveDetailMapper.findDetailByReceiveId(receiveId);
            if (details == null || details.size() == 0) {
                continue;
            }

            if (!forceClear) {
                //不能强制结算
                int num = 0;
                String msg = "";
                for (ReceiveDetail detail : details) {
                    Integer status = detail.getStatus();
                    if (status == 1 || status == 3 || status == 4 || status == 5) {
                        msg = "收货单" + receiveOrder.getReceiveCode() + "中存在不合法的单据状态,是否强制结算?\n";
                        set.add(receiveOrder);
                    }
                    //已收货和已上架的可以直接结算
                    if (status == 2 || status == 6) {
                        clearDetailSuccess(userId, timestamp, detail);
                        ++num;
                    }
                }

                //收货单主表状态的变化
                if (num > 0) {
                    updateReceiveOrder(userId, timestamp, receiveOrder);
                }


                if (num == details.size()) {
                    continue;
                } else {
                    errorMsg += msg;
                    continue;
                }

            } else {
                //能强制结算
                int count = 0;
                for (ReceiveDetail detail : details) {
                    //未开始和已结算的订单不能结算
                    Integer status = detail.getStatus();
                    if (status == 1 || status == 3 || status == 4 || status == 5) {
                        clearDetailSuccess(userId, timestamp, detail);
                        ++count;
                    }
                }

                //收货单主表状态的变化
                if (count > 0) {
                    //收货单单据状态确定
                    updateReceiveOrder(userId, timestamp, receiveOrder);
                }
            }

        }

        return result.setMessage(errorMsg).setData(set);
    }

    /**
     * 收货单状态的确定
     *
     * @param userId
     * @param timestamp
     * @param receiveOrder
     */
    private void updateReceiveOrder(String userId, Timestamp timestamp, ReceiveOrder receiveOrder) {
        Integer status = orderReceive(receiveOrder);
        receiveOrder.setOrderStatus(status);
        receiveOrder.setModifyId(userId);
        receiveOrder.setModifyDate(timestamp);

        receiveOrderMapper.updateByPrimaryKey(receiveOrder);
    }


    /**
     * 模糊匹配查询
     *
     * @param receiveOrder
     * @return
     */
    @Override
    public List<ReceiveOrder> findByConditions(ReceiveOrder receiveOrder, String placeId, List<String> gooderCodes) {

        Map<String, Object> map = new HashMap<>();

        //收货单编码
        String receiveCode = null;
        //单据状态
        Integer orderStatus = null;
        //货主编码
        String gooderCode = null;
        //收货类型
        String receiveType = null;
        //承运商编码
        String carrierCode = null;

        String orgCode = null;

        //预约时间
        Date appointTimeStart = null;
        Date appointTimeEnd = null;

        //创建时间
        Date createTimeStart = null;
        Date createTimeEnd = null;

        //接收时间
        Date receiveTimeStart = null;
        Date receiveTimeEnd = null;

        //质检时间
        Date qualityTimeStart = null;
        Date qualityTimeEnd = null;

        //上架时间
        Date shelfTimeStart = null;
        Date shelfTimeEnd = null;

        //供应商
        String providerCode = null;

        //车牌
        String carNum = null;

        //司机
        String driver = null;

        //外部单号
        String outsideCode = null;

        //采购订单
        String purchaseCode = null;

        //操作人
        String modifyId = null;

        //货品编码
        String goodCode = null;

        //货品名称
        String goodName = null;

        if (receiveOrder != null) {

            receiveCode = receiveOrder.getReceiveCode();

            orderStatus = receiveOrder.getOrderStatus();

            gooderCode = receiveOrder.getGooderCode();

            receiveType = receiveOrder.getReceiveType();

            carrierCode = receiveOrder.getCarrierCode();

            appointTimeStart = receiveOrder.getAppointTimeStart();

            appointTimeEnd = receiveOrder.getAppointTimeEnd();

            createTimeStart = receiveOrder.getCreateTimeStart();

            createTimeEnd = receiveOrder.getCreateTimeEnd();

            providerCode = receiveOrder.getProviderCode();

            receiveTimeStart = receiveOrder.getReceiveTimeStart();

            receiveTimeEnd = receiveOrder.getReceiveTimeEnd();

            qualityTimeStart = receiveOrder.getQualityTimeStart();

            qualityTimeEnd = receiveOrder.getQualityTimeEnd();

            carNum = receiveOrder.getCarNum();

            shelfTimeStart = receiveOrder.getShelfTimeStart();

            shelfTimeEnd = receiveOrder.getShelfTimeEnd();

            driver = receiveOrder.getDriver();

            outsideCode = receiveOrder.getOutsideCode();

            purchaseCode = receiveOrder.getPurchaseCode();

            modifyId = receiveOrder.getModifyId();

            goodCode = receiveOrder.getGoodCode();

            goodName = receiveOrder.getGoodName();

            orgCode = receiveOrder.getOrgCode();

        }
        map.put("receiveCode", receiveCode);
        map.put("orderStatus", orderStatus);
        map.put("gooderCode", gooderCode);
        map.put("receiveType", receiveType);
        map.put("carrierCode", carrierCode);
        map.put("appointTimeStart", appointTimeStart);
        map.put("appointTimeEnd", appointTimeEnd);
        map.put("createTimeStart", createTimeStart);
        map.put("createTimeEnd", createTimeEnd);
        map.put("providerCode", providerCode);
        map.put("receiveTimeStart", receiveTimeStart);
        map.put("receiveTimeEnd", receiveTimeEnd);
        map.put("qualityTimeStart", qualityTimeStart);
        map.put("qualityTimeEnd", qualityTimeEnd);
        map.put("carNum", carNum);
        map.put("shelfTimeStart", shelfTimeStart);
        map.put("shelfTimeEnd", shelfTimeEnd);
        map.put("driver", driver);
        map.put("outsideCode", outsideCode);
        map.put("purchaseCode", purchaseCode);
        map.put("modifyId", modifyId);
        map.put("goodCode", goodCode);
        map.put("goodName", goodName);
        map.put("orgCode", orgCode);
        map.put("placeId", placeId);

        List<ReceiveOrder> list2 = new ArrayList<>();

        if (gooderCodes != null && gooderCodes.size() > 0) {

            List<ReceiveOrder> list = receiveOrderMapper.selectByManyCondition(map);

            if (list != null && list.size() > 0) {

                for (ReceiveOrder receiveOrder1 : list) {

                    String gooderCode1 = receiveOrder1.getGooderCode();

                    if (gooderCodes.contains(gooderCode1)) {

                        list2.add(receiveOrder1);
                    }
                }
            }
        }


        return list2;

    }


    /**
     * 修改时新增明细
     *
     * @param goods
     * @param receiveId
     * @param userId
     * @return
     */

    @Override
    public String addReceiveDetail(List<GoodVo> goods, String receiveId, String placeId, String userId) throws Exception {

        String errorMsg = "";
        ReceiveOrder receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);
        if (receiveOrder == null) {
            errorMsg = "收货单不存在,新增失败!";
            return errorMsg;
        }

        if (receiveOrder.getPurchaseCode() != null) {
            errorMsg = "当前收货单不允许新增无PO单,新增收货明细失败!";
            return errorMsg;
        }


        //单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
        Integer status = receiveOrder.getOrderStatus();
        //只有未开始和收货中的单据才能新增明细
        if (status != 0 && status != 1) {
            errorMsg = "只有未开始或收货中的收货单才能新增收货明细,新增明细失败!";
            return errorMsg;
        }

        //原有的货品记录
        List<String> list = new ArrayList<>();
        List<ReceiveDetail> details = receiveDetailMapper.selectReceiveDetailByReceiveId(receiveId);
        if (details != null && details.size() > 0) {

            for (ReceiveDetail detail : details) {
                String gooderCode = detail.getGooderCode();
                String goodCode = detail.getGoodCode();

                String goodMessage = gooderCode + goodCode;

                list.add(goodMessage);
            }
        }

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

        int num = 0;
        Double totalRweight = 0.0;
        Double totalBulk = 0.0;

        if (status == 0) {
            //未开始的明细不能重复添加
            //1.和原有的单据不能重复
            //2.新增的单据不能重复
            for (GoodVo good : goods) {
                String gooderCode = good.getGooderCode();
                String goodCode = good.getGoodCode();
                String goodMessage = gooderCode + goodCode;

                if (list.contains(goodMessage)) {
                    errorMsg = "新增的明细中存在重复的记录，添加失败!";
                    continue;
                }


                GoodConfig goodConfig = goodConfigMapper.selectGoodConfigByGoodId(gooderCode, goodCode, placeId);

                ReceiveDetail detail = new ReceiveDetail();

                //关联主表
                detail.setReceiveId(receiveId);
                //关联供应商编码和组织机构编码
                detail.setProviderCode(receiveOrder.getProviderCode());
                detail.setOrgCode(receiveOrder.getOrgCode());

                detail.setDetailId(IdsUtils.getOrderId());
                detail.setExpectNum(0);
                detail.setReceiveNum(0);
                detail.setRefuseNum(0);
                detail.setQualityNum(0);
                detail.setSecondNum(0);
                detail.setShelfNum(0);
                detail.setRfid(0);
                detail.setStatus(0);
                //是否挂起
                detail.setHangUp(0);
                detail.setGooderCode(gooderCode);
                detail.setGoodCode(goodCode);
                detail.setGoodName(good.getGoodName());
                detail.setGoodModel(good.getGoodModel());
                Double roughWeight = good.getrWeight();
                if (roughWeight == null) {
                    roughWeight = 0.0;
                }
                detail.setRweight(roughWeight);
                totalRweight += roughWeight;
                Double bulk = good.getBulk();
                detail.setBulk(bulk);
                if (bulk == null) {
                    bulk = 0.0;
                }

                totalBulk += bulk;
                detail.setIsQuality(goodConfig.getIsQuality());
                detail.setCreateId(userId);
                detail.setCreateDate(timeStamp);
                detail.setModifyId(userId);
                detail.setModifyDate(timeStamp);

                detail.setPlaceId(placeId);

                receiveDetailMapper.insert(detail);
                ++num;

                list.add(goodMessage);
            }
        }

        if (status == 1) {
            //续收，必须存在的记录
            //2.新增的单据不能重复
            for (GoodVo good : goods) {
                String gooderCode = good.getGooderCode();
                String goodCode = good.getGoodCode();
                String goodMessage = gooderCode + goodCode;

                if (!list.contains(goodMessage)) {
                    errorMsg = goodCode + "货品不在续收范围，添加失败!";
                    continue;
                }


                GoodConfig goodConfig = goodConfigMapper.selectGoodConfigByGoodId(gooderCode, goodCode, placeId);

                ReceiveDetail detail = new ReceiveDetail();

                //关联主表
                detail.setReceiveId(receiveId);
                //关联供应商编码和组织机构编码
                detail.setProviderCode(receiveOrder.getProviderCode());
                detail.setOrgCode(receiveOrder.getOrgCode());

                detail.setDetailId(IdsUtils.getOrderId());
                detail.setExpectNum(0);
                detail.setReceiveNum(0);
                detail.setRefuseNum(0);
                detail.setQualityNum(0);
                detail.setSecondNum(0);
                detail.setShelfNum(0);
                detail.setRfid(0);
                detail.setStatus(0);
                //是否挂起
                detail.setHangUp(0);
                detail.setGooderCode(gooderCode);
                detail.setGoodCode(goodCode);
                detail.setGoodName(good.getGoodName());
                detail.setGoodModel(good.getGoodModel());
                Double roughWeight = good.getrWeight();
                if (roughWeight == null) {
                    roughWeight = 0.0;
                }
                detail.setRweight(roughWeight);
                totalRweight += roughWeight;
                Double bulk = good.getBulk();
                detail.setBulk(bulk);
                if (bulk == null) {
                    bulk = 0.0;
                }

                totalBulk += bulk;
                detail.setIsQuality(goodConfig.getIsQuality());
                detail.setCreateId(userId);
                detail.setCreateDate(timeStamp);
                detail.setModifyId(userId);
                detail.setModifyDate(timeStamp);

                detail.setPlaceId(placeId);

                receiveDetailMapper.insert(detail);
                ++num;

            }

        }


        //收货单预计体积和预计重量
        if (num > 0) {
            Double predictWeight = receiveOrder.getPredictWeight();
            if (predictWeight == null) {
                predictWeight = 0.0;
            }

            Double predictBulk = receiveOrder.getPredictBulk();
            if (predictBulk == null) {
                predictBulk = 0.0;
            }
            receiveOrder.setPredictWeight(predictWeight + totalRweight);
            receiveOrder.setPredictBulk(predictBulk + totalBulk);

            receiveOrder.setModifyId(userId);
            receiveOrder.setModifyDate(timeStamp);
            receiveOrderMapper.updateByPrimaryKey(receiveOrder);
        }

        return errorMsg;
    }

    /**
     * 修改时,明细界面的删除
     *
     * @param detailIds
     * @return
     */

    @Override
    @Transactional
    public String delReceiveDetail(List<String> detailIds, String userId) {

        String errorMsg = "";

        Double predictBulk = 0.0;
        Double predictWeight = 0.0;
        String receiveId = null;
        int count = 0;
        for (String detailId : detailIds) {
            ReceiveDetail detail = receiveDetailMapper.selectByPrimaryKey(detailId);
            if (detailId == null) {
                continue;
            }
            //只有未开始的订单才能执行删除操作
            if (detail.getStatus() != 0) {
                errorMsg += "第 " + detail.getId() + "条记录单据状态不对,删除失败!\n";
                continue;
            }

            receiveId = detail.getReceiveId();

            Double bulk = detail.getBulk();
            if (bulk != null) {
                predictWeight += bulk;
            }
            Double rweight = detail.getRweight();
            if (rweight != null) {
                predictBulk += rweight;
            }

            receiveDetailMapper.deleteByPrimaryKey(detail.getDetailId());
            count++;

        }

        if (count > 0) {
            //全部记录都被删除
            List<ReceiveDetail> receiveDetails = receiveDetailMapper.selectReceiveDetailByReceiveId(receiveId);
            if (receiveDetails == null || receiveDetails.size() == 0) {
                receiveOrderMapper.deleteByPrimaryKey(receiveId);
                return errorMsg;
            }

            ReceiveOrder receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);
            if (predictBulk == null) {
                predictBulk = 0.0;
            }

            if (predictWeight == null) {
                predictWeight = 0.0;
            }
            Double predictBulk1 = receiveOrder.getPredictBulk();
            if (predictBulk1 != null) {
                receiveOrder.setPredictBulk(-predictBulk);
            }
            Double predictWeight1 = receiveOrder.getPredictWeight();
            if (predictWeight1 != null) {
                receiveOrder.setPredictWeight(-predictWeight);
            }

            receiveOrder.setModifyDate(TimeStampUtils.getTimeStamp());
            receiveOrder.setModifyId(userId);

            Integer status = orderReceive(receiveOrder);

            receiveOrder.setOrderStatus(status);

            receiveOrderMapper.updateByPrimaryKey(receiveOrder);
        }

        return errorMsg;
    }

    /**
     * 修改时收货单明细刷新操作
     *
     * @param receiveId
     * @return
     */
    @Override
    public Result listReceiveDetail(String receiveId) {
        Result result = new Result();
        List<ReceiveDetail> receiveDetails = receiveDetailMapper.findDetailByReceiveId(receiveId);
        result.setData(receiveDetails);
        return result;
    }

    /**
     * 通过最大 id 值查询 ReceiveOrder.java  --  rk  2018/05/02
     *
     * @return
     */
    @Override
    public ReceiveOrder findReceiveOrderByMaxId() {
        return receiveOrderMapper.findReceiveOrderByMaxId();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void uploadExcelForAddStoreSeat(List<ReceiveDetailDto> success, String userId, ReceiveOrder
            receiveOrder) throws Exception {
        ReceiveDetail receiveDetail;
        if (success.size() > 0) {
            String receiveId = IdsUtils.getOrderId();
            //明细表
            int i = 1;
            int expectNum = 0;
            int receiveNum = 0;
            int refuseNum = 0;
            for (ReceiveDetailDto receiveDetailDto : success) {
                receiveDetail = new ReceiveDetail();
                BeanUtils.copyProperties(receiveDetailDto, receiveDetail);
                if (StringUtils.isNotBlank(receiveDetailDto.getExpectNum())) {
                    receiveDetail.setExpectNum(Math.round(Float.valueOf(receiveDetailDto.getExpectNum())));
                }
                if (StringUtils.isNotBlank(receiveDetailDto.getReceiveNum())) {
                    receiveDetail.setReceiveNum(Math.round(Float.valueOf(receiveDetailDto.getReceiveNum())));
                } else {
                    receiveDetail.setReceiveNum(0);
                }
                if (StringUtils.isNotBlank(receiveDetailDto.getRefuseNum())) {
                    receiveDetail.setRefuseNum(Math.round(Float.valueOf(receiveDetailDto.getRefuseNum())));
                } else {
                    receiveDetail.setRefuseNum(0);
                }
                receiveDetail.setDetailId(IdsUtils.getOrderId());
                //单据状态未开始
                receiveDetail.setStatus(0);
                //新增的时候,默认不挂起
                receiveDetail.setHangUp(0);
                //新增时质检量,次品量,上架量,rfid采集量都为0
                receiveDetail.setQualityNum(0);
                receiveDetail.setSecondNum(0);
                receiveDetail.setShelfNum(0);
                receiveDetail.setRfid(0);
                //明细预期量  接收量  拒收量 聚合
                expectNum = expectNum + receiveDetail.getExpectNum();
                receiveNum = receiveNum + receiveDetail.getReceiveNum();
                refuseNum = refuseNum + receiveDetail.getRefuseNum();

                //外部单行号
                if (StringUtils.isNotBlank(receiveOrder.getOutsideCode())) {
                    receiveDetail.setOutsideLineCode(i + "");
                    i++;
                }
                receiveDetail.setPlaceId(receiveOrder.getPlaceId());
                receiveDetail.setOrgCode(receiveOrder.getOrgCode());
                receiveDetail.setProviderCode(receiveOrder.getProviderCode());
                receiveDetail.setCreateId(userId);
                receiveDetail.setCreateDate(TimeStampUtils.getTimeStamp());
                receiveDetail.setModifyId(userId);
                receiveDetail.setModifyDate(TimeStampUtils.getTimeStamp());
                //关收货单表id
                receiveDetail.setReceiveId(receiveId);
                receiveDetailMapper.insert(receiveDetail);
            }
            //主表收货单保存
            receiveOrder.setReceiveCode(getReceive());
            receiveOrder.setReceiveId(receiveId);
            receiveOrder.setOrderStatus(0);
            //新增的时候,默认不挂起
            receiveOrder.setHangUp(0);
            receiveOrder.setCreateId(userId);
            receiveOrder.setModifyId(userId);
            receiveOrder.setCreateDate(TimeStampUtils.getTimeStamp());
            receiveOrder.setModifyDate(TimeStampUtils.getTimeStamp());
            // 接收 预期 拒收
            receiveOrder.setExpectNum(expectNum);
            receiveOrder.setReceiveNum(receiveNum);
            receiveOrder.setRefuseNum(refuseNum);
            //接收量,拒收量,上架量,质检量,次品量都为0
            receiveOrder.setQualityNum(0);
            receiveOrder.setSecondNum(0);
            receiveOrder.setShelfNum(0);

            receiveOrderMapper.insert(receiveOrder);
        }
    }


    /**
     * 收货单号的规则：自动生成（ASN+6位日期+5位流水）示例：ASN180909000001
     *
     * @return
     */

    public String getReceive() {
        Integer id = receiveOrderMapper.selectMaxId();
        if (id == null) {
            id = 1;
        } else {
            id++;
        }

        return "ASN" + getBody(id);
    }

    private String getBody(Integer id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()).substring(2, 8) + String.format("%06d", id);
    }


}