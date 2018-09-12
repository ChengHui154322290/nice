package com.nice.good.service.impl;


import com.nice.good.dao.*;
import com.nice.good.model.*;
import com.nice.good.service.PdaReceiveGoodService;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.vo.PdaDetailVo;
import com.nice.good.vo.ReceiveDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.TreeSet;

@Service
@Transactional
public class PdaReceiveGoodServiceImpl implements PdaReceiveGoodService {


    @Autowired
    private ReceiveOrderMapper receiveOrderMapper;


    @Autowired
    private ReceiveDetailMapper receiveDetailMapper;


    @Autowired
    private GoodConfigMapper goodConfigMapper;

    @Autowired
    private OrderGoodMappingMapper orderGoodMappingMapper;

    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    private StoreSeatMapper storeSeatMapper;

    @Autowired
    private GoodMapper goodMapper;


    @Autowired
    private SeatStockMapper seatStockMapper;

    @Autowired
    private StockMapper stockMapper;


    @Override
    @Transactional
    public String receiveGood(PdaDetailVo pdaDetailVo,String placeId, String userId) throws Exception {
        //预期量
        Integer expectNum = pdaDetailVo.getExpectNum();

        //接收量
        Integer receiveNum = pdaDetailVo.getReceiveNum();


        //拒收量
        Integer refuseNum = pdaDetailVo.getRefuseNum();

        if (receiveNum == 0) {
            return "接收量不能为0";
        }
        if (receiveNum == null || StringUtils.isBlank(receiveNum.toString())) {
            return "接收量不能空!";
        }

        if (refuseNum == null || StringUtils.isBlank(refuseNum.toString())) {
            refuseNum = 0;

        }


        ReceiveDetail detail = receiveDetailMapper.selectByPrimaryKey(pdaDetailVo.getDetailId());


        //货主编码
        String gooderCode = detail.getGooderCode();
        //货品编码
        String goodCode = detail.getGoodCode();
        //是否超量验证  0否,1是
        GoodConfig goodConfig = goodConfigMapper.selectGoodConfigByGoodId(gooderCode, goodCode,placeId);

        //无PO收货允许还是警告
        if (goodConfig != null) {
            String noPoReceive = goodConfig.getNoPoReceive();
            if (StringUtils.isNotBlank(noPoReceive) && "警告".equals(noPoReceive)) {
                if (detail.getPurchaseCode() == null || detail.getPurchaseLineCode() == null) {
                    return "当前记录不允许无PO(采购单)收货,操作不成功!";
                }
            }
        }


        //ASN收货单号
        String receiveCode = pdaDetailVo.getReceiveCode();

        String receiveId = receiveOrderMapper.selectReceiveIdByReceiveCode(receiveCode);
        //预期量
        Integer sumExpectNum;
        //已接收量
        Integer sumReceiveNum;
        String purchaseCode = detail.getPurchaseCode();
        Integer purchaseLineCode = detail.getPurchaseLineCode();

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
                    return "当前记录条记录货品已超过接收比例,拒绝收货!";
                }
            }

        }

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();
        //收货添加到库存
        String message = addToStock(userId,placeId, timeStamp, detail);
        if (StringUtils.isNotBlank(message)) {
            return message;
        }


        //如果接收量小于预期量,收货中
        Integer status = 0;
        if (receiveNum > 0 && receiveNum + refuseNum < expectNum) {
            status = 1;
        }

        if (receiveNum + refuseNum >= expectNum) {
            status = 2;
        }


        detail.setStatus(status);
        detail.setModifyDate(timeStamp);
        detail.setModifyId(userId);

        receiveDetailMapper.updateByPrimaryKey(detail);

        String seatCode = detail.getSeatCode();

        //收货以后,库位容量减少
        Integer capacity = storeSeatMapper.selectCapacityBySeatCode(seatCode,placeId);
        if (capacity != null && capacity != 0) {
            storeSeatMapper.updateSeatCapacity(seatCode, -receiveNum,placeId);
        }


        //收货时,收货状态同步到采购订单状态
        if (purchaseCode != null && purchaseLineCode != null) {
            updatePurchaseOrderStatus(detail, userId);
        }

        if (status == 2) {
            if (purchaseCode == null && purchaseLineCode == null) {

                receiveDetailMapper.updateSameGoodStatus(gooderCode, goodCode, receiveId, status);

            } else {
                receiveDetailMapper.updateSamePurchaseStatus(purchaseCode, purchaseLineCode, status);

                //其他收货单中采购单总状态同步
                List<ReceiveDetail> rdetails = receiveDetailMapper.selectReceiveDetailByPurchaseCode(purchaseCode);

                Integer status3 = getOtherPuchase(rdetails);

                receiveOrderMapper.updateOrderStatusByPurchaseCode(purchaseCode, status3);

            }

        }

        //主表状态同步
        ReceiveOrder receiveOrder = receiveOrderMapper.selectByPrimaryKey(receiveId);

        receiveOrder.setLastReceiveTime(timeStamp);

        receiveOrder.setReceiveNum(receiveOrder.getReceiveNum() + receiveNum);

        receiveOrder.setRefuseNum(receiveOrder.getRefuseNum() + refuseNum);

        Integer status2 = orderReceive(receiveOrder);

        receiveOrder.setOrderStatus(status2);
        receiveOrder.setModifyId(userId);
        receiveOrder.setModifyDate(timeStamp);

        receiveOrderMapper.updateByPrimaryKey(receiveOrder);

        return null;
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
     * 收货添加到库存
     *
     * @param userId
     * @param placeId
     * @param timestamp
     * @param receiveDetail
     * @return
     * @throws Exception
     */

    private String addToStock(String userId, String placeId, Timestamp timestamp, ReceiveDetail receiveDetail) throws Exception {


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
        Integer capacity = storeSeatMapper.selectCapacityBySeatCode(seatCode,placeId);
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
            seatStock.setOrgCode(orgCode);
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
}
