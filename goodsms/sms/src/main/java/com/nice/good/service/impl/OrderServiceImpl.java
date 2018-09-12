package com.nice.good.service.impl;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.nice.good.core.Result;
import com.nice.good.dao.OrderGoodMappingMapper;
import com.nice.good.dao.ReceiveDetailMapper;
import com.nice.good.dto.OrderGoodDto;
import com.nice.good.exception.IllegalOperateException;
import com.nice.good.model.Good;
import com.nice.good.model.GoodArea;
import com.nice.good.utils.TimeStampUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nice.good.core.AbstractService;

import com.nice.good.dao.OrderMapper;
import com.nice.good.model.Order;
import com.nice.good.model.OrderGoodMapping;
import com.nice.good.service.OrderService;
import com.nice.good.utils.IdsUtils;


/**
 * Created by CodeGenerator on 2018/03/26.
 */
@Service
public class OrderServiceImpl extends AbstractService<Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderGoodMappingMapper orderGoodMappingMapper;


    @Resource
    private ReceiveDetailMapper receiveDetailMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String addOrderOpration(Order order, String placeId, String userId) throws Exception {

        String errorMsg = "";

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

        if (order.getOrderId() == null) {


            String orgCode = order.getOrgCode();

            if (StringUtils.isBlank(orgCode)) {
                errorMsg = "组织机构编码不能为空!";
                return errorMsg;
            }

            String providerCode = order.getProviderCode();
            if (StringUtils.isBlank(providerCode)) {
                errorMsg = "供应商编码编码不能为空!";
                return errorMsg;
            }

            //新增时判断采购单号是否重复
            String id = orderMapper.selectOrderIdPurchaseCode(order.getPurchaseCode());
            if (id != null) {
                order.setPurchaseCode(getPurchase());
            }


            //新增操作
            String orderId = IdsUtils.getOrderId();
            order.setOrderId(orderId);
            order.setCreateId(userId);
            order.setOrderStatus(0);
            order.setCreateId(userId);
            order.setCreateName(userId);
            order.setCreateDate(timeStamp);
            order.setModifyId(userId);
            order.setModifyDate(timeStamp);

            //关联场地id
            order.setPlaceId(placeId);


            //保存明细对象
            List<OrderGoodMapping> objs = order.getObjs();
            if (objs == null || objs.size() == 0) {

                errorMsg = "采购明细不能为空!";

                return errorMsg;

            }

            //新增明细中不能有重复记录
            List<String> list = new ArrayList<>();
            for (OrderGoodMapping orderGoodMapping : objs) {

                Integer purchaseNumber = orderGoodMapping.getPurchaseNumber();
                if (purchaseNumber == null || purchaseNumber == 0) {
                    errorMsg = "采购数量不能为空!";
                    return errorMsg;
                }

                if (purchaseNumber < 0) {
                    errorMsg = "采购数量不正确!";
                    return errorMsg;
                }

                String gooderCode = orderGoodMapping.getGooderCode();
                String goodCode = orderGoodMapping.getGoodCode();

                String goodMessage = gooderCode + goodCode;

                if (list.contains(goodMessage)) {
                    errorMsg = "新增的明细中存在重复的记录，添加失败!";
                    return errorMsg;
                }

                list.add(goodMessage);

            }


            for (OrderGoodMapping orderGoodMapping : objs) {
                //关联主表
                orderGoodMapping.setOrderId(orderId);
                orderGoodMapping.setCreateId(userId);
                orderGoodMapping.setCreateName(userId);
                orderGoodMapping.setStatus(0);

                //补充组织机构编码和供应商编码
                if (StringUtils.isBlank(orderGoodMapping.getOrgCode())) {
                    orderGoodMapping.setOrgCode(orgCode);
                }

                if (StringUtils.isBlank(orderGoodMapping.getProviderCode())) {
                    orderGoodMapping.setProviderCode(providerCode);
                }

                orderGoodMapping.setCreateDate(timeStamp);


                //关联场地id
                order.setPlaceId(placeId);

                orderGoodMappingMapper.insert(orderGoodMapping);
            }


            //主表新增操作
            orderMapper.insert(order);


        } else {
            //修改操作
            if (order.getOrderStatus() == 0) {
                List<OrderGoodMapping> objs = order.getObjs();
                if (objs != null || objs.size() > 0) {
                    for (OrderGoodMapping orderGoodMapping : objs) {

                        Integer purchaseNumber = orderGoodMapping.getPurchaseNumber();
                        if (purchaseNumber == null) {
                            errorMsg = "采购数量不能为空!";
                            return errorMsg;
                        }

                        if (purchaseNumber == 0) {
                            errorMsg = "采购数量不能为0!";
                            return errorMsg;
                        }

                        if (purchaseNumber < 0) {
                            errorMsg = "采购数量不正确!";
                            return errorMsg;
                        }
                    }

                    order.setModifyId(userId);
                    order.setModifyDate(timeStamp);
                    orderMapper.updateByPrimaryKey(order);


                    if (objs != null || objs.size() > 0) {
                        for (OrderGoodMapping orderGoodMapping : objs) {
                            Integer status = orderGoodMapping.getStatus();
                            if (0==status.intValue()) {
                                orderGoodMappingMapper.updateByPrimaryKey(orderGoodMapping);
                            }
                        }

                    }
                }

            }

        }
        return errorMsg;
    }

    @Override
    public String deleteOrderById(List<String> orderIds) {

        String errorMsg = "";

        for (String orderId : orderIds) {

            //单据状态0未开始1已确认2收货中3已收货5质检中6已质检7已结算
            //只有未开始的订单才能执行删除操作
            Order order = orderMapper.selectByPrimaryKey(orderId);

            if (order == null) {
                errorMsg += "采购单号为" + order.getPurchaseCode() + "的记录不存在!\n";
                continue;
            }

            if (order.getOrderStatus() != 0) {
                errorMsg += "采购单号为" + order.getPurchaseCode() + "的记录单据状态不对,删除失败!\n";
                continue;

            }

            String purchaseCode = order.getPurchaseCode();
            //如果该订单在收货单中有关联,则不能删除
            Integer count = receiveDetailMapper.selectCountByPurchaseCode(purchaseCode);

            if (count != null && count != 0) {
                errorMsg += "采购单号为" + order.getPurchaseCode() + "的记录在收货单中已有关联,删除失败!\n";
                continue;
            }

            //删除主表
            orderMapper.deleteByPrimaryKey(orderId);
            //删除子表
            orderGoodMappingMapper.deleteByOrderId(orderId);
        }
        return errorMsg;
    }

    @Override
    public String updateStatus(List<String> orderIds, Integer status, String userId) {

        String errorMsg = "";

        for (String orderId : orderIds) {

            if (orderId == null) {
                continue;
            }

            Timestamp timeStamp = TimeStampUtils.getTimeStamp();

            //单据状态 单据状态 0未开始 1已确认 2收货中 3已收货 5质检中 6已质检 7已结算',
            Order order = orderMapper.selectByPrimaryKey(orderId);

            if (order == null) {
                continue;
            }

            if (status == 1) {
                //确认操作
                if (order.getOrderStatus() != 0) {
                    errorMsg += "采购单号为" + order.getPurchaseCode() + "的单据状态不对,操作不成功!\n";
                    continue;
                }

                //确认操作时,即为审核
                order.setAuditingDate(timeStamp);

                //审核人
                order.setAuditingName(userId);


                order.setAuditingId(userId);

                //单据状态变为已确认
                order.setOrderStatus(1);
            }

            if (status == 0) {
                //反确认操作
                if (order.getOrderStatus() != 1) {
                    errorMsg += "采购单号为" + order.getPurchaseCode() + "的单据状态不对,操作不成功!\n";
                    continue;
                }

                //如果该单据在收货单中已有关联,则无法操作
                Integer count = receiveDetailMapper.selectCountByPurchaseCode(order.getPurchaseCode());
                if (count != null) {
                    errorMsg += "采购单号为" + order.getPurchaseCode() + "的单据在收货单中已作关联,操作不成功!\n";
                    continue;
                }

                order.setAuditingName(null);

                order.setAuditingDate(null);

                order.setAuditingId(null);

                order.setOrderStatus(0);

            }

            if (status == 7) {
                //结算操作 只有已收货状态的订单才能结算
                if (order.getOrderStatus() != 3) {
                    errorMsg += "采购单号为" + order.getPurchaseCode() + "的单据状态不对,操作不成功!\n";
                    continue;
                }
            }

            order.setModifyId(userId);
            order.setModifyDate(timeStamp);

            orderMapper.updateByPrimaryKey(order);

            //明细单状态同步
            List<OrderGoodMapping> list = orderGoodMappingMapper.selectMapperByOrderId(orderId);
            for (OrderGoodMapping mapping : list) {
                mapping.setStatus(status);
                orderGoodMappingMapper.updateByPrimaryKey(mapping);
            }

        }
        return errorMsg;
    }

    /**
     * 通过最大 id 值查询 Order.java  --  rk  2018/04/27
     *
     * @return
     */
    @Override
    public Order findOrderByMaxId() {
        return orderMapper.findOrderByMaxId();
    }

    @Override
    @Transactional
    public void uploadExcelForAddStoreSeat(List<OrderGoodDto> success, String userId, Order order) {
        OrderGoodMapping goods;
        try {
            if (success.size() > 0) {
                Timestamp timeStamp = TimeStampUtils.getTimeStamp();
                String orderId = IdsUtils.getOrderId();
                order.setOrderId(orderId);
                order.setCreateId(userId);
                order.setOrderStatus(0);
                order.setCreateId(userId);
                order.setCreateName(userId);
                order.setCreateDate(timeStamp);
                order.setModifyId(userId);
                order.setModifyDate(timeStamp);
                orderMapper.insert(order);
                for (OrderGoodDto orderGoodDto : success) {
                    goods = new OrderGoodMapping();
                    BeanUtils.copyProperties(orderGoodDto, goods);
                    goods.setOriginPrice(new BigDecimal(orderGoodDto.getOriginPrice()));
                    goods.setRatePrice(new BigDecimal(orderGoodDto.getRatePrice()));
                    goods.setAmount(new BigDecimal(orderGoodDto.getAmount()));
                    goods.setRateAmount(new BigDecimal(orderGoodDto.getRateAmount()));
                    goods.setIsPay(orderGoodDto.getIsPayInt());
                    goods.setPurchaseNumber(Math.round(Float.valueOf(orderGoodDto.getPurchaseNumber())));
                    goods.setOrderId(orderId);
                    goods.setCreateId(userId);
                    goods.setCreateName(userId);
                    goods.setStatus(0);
                    goods.setCreateDate(timeStamp);
                    orderGoodMappingMapper.insert(goods);
                }
            }
        } catch (Exception e) {
            // log.error("导入库位数据出错："+e.getMessage().toString());
            e.printStackTrace();
        }

    }

    /**
     * 修改时新增明细
     *
     * @param goods
     * @param orderId
     * @param userId
     * @return
     */

    @Override
    public String addGoodMapping(List<Good> goods, String orderId, String placeId, String userId) {


        //只有未开始状态的才能添加明细
        Order order = orderMapper.selectByPrimaryKey(orderId);

        String errorMsg = "";
        if (order == null) {
            errorMsg = "采购单不存在,操作不成功!";
            return errorMsg;
        }

        if (order.getOrderStatus() != 0) {
            errorMsg = "只有未开始的采购单才能新增明细采购明细,操作不成功!";
            return errorMsg;

        }

        //不能新增重复的记录
        //1.原有未开始的记录
        List<String> list = new ArrayList<>();
        List<OrderGoodMapping> orderGoodMappings = orderGoodMappingMapper.selectMapperByOrderId(orderId);
        if (orderGoodMappings != null && orderGoodMappings.size() > 0) {
            for (OrderGoodMapping mapping : orderGoodMappings) {

                String gooderCode = mapping.getGooderCode();
                String goodCode = mapping.getGoodCode();

                String goodMessage = gooderCode + goodCode;

                list.add(goodMessage);
            }
        }

        //2.新增未开始的记录

        for (Good good : goods) {

            String gooderCode = good.getGooderCode();
            String goodCode = good.getGoodCode();

            String goodMessage = gooderCode + goodCode;

            if (list.contains(goodMessage)) {
                errorMsg = "新增的明细中存在重复的记录，添加失败!";
                return errorMsg;
            }

            list.add(goodMessage);
        }


        Timestamp timeStamp = TimeStampUtils.getTimeStamp();
        for (Good good : goods) {

            if (good == null) {
                continue;
            }

            OrderGoodMapping mapping = new OrderGoodMapping();
            //关联主表id
            mapping.setOrderId(orderId);
            mapping.setGoodCode(good.getGoodCode());
            mapping.setGoodModel(good.getGoodModel());
            mapping.setCommodityCode(good.getCommodityCode());
            mapping.setGoodName(good.getGoodName());
            mapping.setStatus(0);
            mapping.setPurchaseNumber(0);
            mapping.setCreateId(userId);
            mapping.setCreateDate(timeStamp);
            mapping.setModifyId(userId);
            mapping.setModifyDate(timeStamp);
            mapping.setIsPay(1);

            //关联场地id
            mapping.setPlaceId(placeId);

            orderGoodMappingMapper.insert(mapping);

            //采购单状态的更新
            order.setModifyId(userId);
            order.setModifyDate(timeStamp);
            orderMapper.updateByPrimaryKey(order);

        }
        return errorMsg;
    }

    /**
     * 修改明细删除
     *
     * @param ids
     * @return
     */

    @Override
    public String delGoodMapping(List<Integer> ids) {

        String errorMsg = "";

        int num = 0;
        String orderId = null;
        for (Integer id : ids) {
            if (id == null) {
                continue;
            }

            //只有未开始状态的单据才能删除
            OrderGoodMapping mapping = orderGoodMappingMapper.selectByPrimaryKey(id);
            orderId = mapping.getOrderId();
            if (mapping.getStatus() != 0) {
                errorMsg += "只有未开始的采购明细才能删除,第 " + id + "条记录删除失败!";
                continue;
            }

            orderGoodMappingMapper.deleteByPrimaryKey(id);
            num++;

        }

        if (num > 0) {
            List<OrderGoodMapping> orderGoodMappings = orderGoodMappingMapper.selectMapperByOrderId(orderId);
            if (orderGoodMappings == null || orderGoodMappings.size() == 0) {
                orderMapper.deleteByPrimaryKey(orderId);
            }
        }
        return errorMsg;
    }

    /**
     * 修改采购明细以后刷新操作
     *
     * @param orderId
     * @return
     */
    @Override
    public Result listGoodMapping(String orderId) {
        Result result = new Result();
        List<OrderGoodMapping> orderGoodMappings = orderGoodMappingMapper.selectMapperByOrderId(orderId);
        result.setData(orderGoodMappings);
        return result;

    }


    public String getPurchase() {
        Integer id = orderMapper.selectMaxId();
        if (id == null) {
            id = 1;
        } else {
            id++;
        }

        String purchaseCode = "PO" + getBody(id);

        return purchaseCode;
    }

    private String getBody(Integer id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()).substring(2, 8) + String.format("%05d", id);
    }
}
