package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.dao.CollarOrderMapper;
import com.nice.miniprogram.model.*;
import com.nice.miniprogram.service.*;
import com.nice.miniprogram.dao.OrderDetailMapper;
import com.nice.miniprogram.core.AbstractService;
import com.nice.miniprogram.vo.OrderDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/27.
 */
@Service
@Transactional
public class OrderDetailServiceImpl extends AbstractService<OrderDetail> implements OrderDetailService {
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private CollarOrderMapper collarOrderMapper;

    @Resource
    private GoodCountService goodCountService;

    @Resource
    private SeatStockService seatStockService;
    @Resource
    private GoodSpuService goodSpuService;
    @Resource
    private GoodSkuService goodSkuService;

    @Override
    public List<OrderDetail> selectDetailListByOrderId(int orderId) {
        Map<String,Object> param = new HashMap<>();
        param.put("orderId",orderId);
        param.put("orderType",0);
        List<OrderDetail> details = orderDetailMapper.selectByParams(param);
        return details;
    }

    /*
    根据明细表的状态变动来改变领用订单主表的状态
     */
    @Transactional
    @Override
    public void updateStatus(Integer orderId, Integer id, Integer status,String userName) {
        Date current = new Date();
        OrderDetail orderDetail = getById(id);
        orderDetail.setStatus(status);
        orderDetail.setModifytime(current);
        orderDetail.setModifier(userName);
        orderDetailMapper.updateByPrimaryKey(orderDetail);
//        orderDetailMapper.updateStatusById(status,id,userName,current);
        CollarOrder collarOrder = collarOrderMapper.selectByPrimaryKey(orderId);
        Map<String,Object> params = new HashMap<>();
        params.put("orderId",orderId);
        List<OrderDetail> allDetails = orderDetailMapper.selectByParams(params);
        params.put("status",status);
        List<OrderDetail> details = orderDetailMapper.selectByParams(params);
        if (status == 2) {//状态收货时，库存移动
            catchStock(orderDetail);
        } else if (status == 4) {//状态归还时，库存移动
            returnStock(orderDetail);
        }
        if(allDetails.size() > details.size()){     // 部分操作
            if(status == 2){
                collarOrder.setStatus(1);
            }else if(status == 4){
                collarOrder.setStatus(3);
            }else{
                collarOrder.setStatus(status);
            }
        }else if(allDetails.size() == details.size()){  //全部操作
            collarOrder.setStatus(status);
        }

        collarOrderMapper.updateByPrimaryKey(collarOrder);
    }

    @Override
    public void batchUpdateStatusByOrderId(Map<String,Object> params) {
        orderDetailMapper.batchUpdateStatusByOrderId(params);

    }

    @Override
    public List<OrderDetail> selectByParams(Map<String, Object> params) {
        return orderDetailMapper.selectByParams(params);
    }

    @Override
    public void batchInsert(List<OrderDetail> orderDetails) {
        orderDetailMapper.batchInsert(orderDetails);
        for(OrderDetail orderDetail:orderDetails){
            GoodCount goodCount = goodCountService.getBySku(orderDetail.getSkuCode());
            //计数
            if(goodCount==null){
                goodCount = new GoodCount();
                goodCount.setCollarNum(1);
                goodCount.setClickNum(1);
                goodCount.setSkuCode(orderDetail.getSkuCode());
                GoodSku goodSku = goodSkuService.getBySku(orderDetail.getSkuCode());
                goodCount.setSpuCode(goodSku.getSpuCode());
                goodCountService.newCountRecord(goodCount);
            }else{
                goodCountService.addCollarNum(orderDetail.getSkuCode());
            }
        }
    }


    @Override
    public List<OrderDetail> freezeStock(List<OrderDetail> details,String placeNumber) {
        List<OrderDetail> newDetails = new ArrayList<>();
        for(OrderDetail orderDetail:details){
            GoodSku goodSku = goodSkuService.getBySku(orderDetail.getSkuCode());
            Integer choseNum = orderDetail.getChoseNum();
            if(StringUtils.isNotBlank(orderDetail.getSourceSeatCode())){  //有源库位的
                Map<String, Object> params = new HashMap<>();
                params.put("goodCode", goodSku.getSkuCode());
                params.put("seatCode", orderDetail.getSourceSeatCode());
                List<SeatStock> seatStocks = seatStockService.getByParams(params);
                for(SeatStock seatStock : seatStocks){
                    seatStock.setUseNum(seatStock.getNowNum() - choseNum);
                    seatStock.setFreezeNum(seatStock.getFreezeNum() + choseNum);
                    seatStockService.update(seatStock);
                }
                newDetails.add(orderDetail);
            }else {     //没有源库位的
//                Good good = goodService.findById(orderDetail.getGoodId());
                Map<String, Object> params = new HashMap<>();
                params.put("goodCode", goodSku.getSkuCode());
//                params.put("gooderCode", );
                params.put("placeNumber", placeNumber);
                List<SeatStock> seatStocks = seatStockService.getByParams(params);
                //库位库存冻结
                for (SeatStock seatStock : seatStocks) {
                    int useNum = seatStock.getUseNum(); //原可用量
                    if (useNum > 0) {
                        if (useNum >= choseNum) {
                            seatStock.setUseNum(useNum - choseNum);//库存可用量
//                        seatStock.setNowNum(seatStock.getNowNum() - choseNum);//现有量
                            seatStock.setFreezeNum(seatStock.getFreezeNum() + choseNum);//冻结量 = 原冻结量 + 已选量
                            seatStockService.update(seatStock);
                            OrderDetail newDetail = orderDetail;
                            newDetail.setChoseNum(choseNum);
                            newDetail.setSourceSeatCode(seatStock.getSeatCode());
                            newDetails.add(newDetail);
                            break;
                        } else {
                            seatStock.setUseNum(0);//库存可用量
//                        seatStock.setNowNum(0);//现有量
                            seatStock.setFreezeNum(seatStock.getFreezeNum() + useNum);//冻结量 = 原冻结量 + 原可用量
                            seatStockService.update(seatStock);
                            choseNum = choseNum - useNum;
                            OrderDetail newDetail = orderDetail;
                            newDetail.setChoseNum(useNum);
                            newDetail.setSourceSeatCode(seatStock.getSeatCode());
                            newDetails.add(newDetail);
                        }
                    }
                }

//                //库存扣减
//                Stock stock = stockService.getOneByParams(params);
//                stock.setNowNum(stock.getFreezeNum() + choseNum);//冻结量 = 原冻结量 + 已选量
//                stock.setUseNum(stock.getUseNum() - choseNum);//库存可用量
//                stockService.update(stock);
            }
        }
        return newDetails;
    }

    @Override
    public List<OrderDetail> freezeStock(OrderDetail detail, String placeNumber) {
        List<OrderDetail> newDetails = new ArrayList<>();
        OrderDetail newDetail = new OrderDetail();
        GoodSku goodSku = goodSkuService.getBySku(detail.getSkuCode());
        Map<String, Object> params = new HashMap<>();
        params.put("goodCode", goodSku.getSkuCode());
//        params.put("gooderCode", good.getGooderCode());
        params.put("placeNumber", placeNumber);
        List<SeatStock> seatStocks = seatStockService.getByParams(params);
        Integer choseNum = detail.getChoseNum();
        //库位库存冻结
        for (SeatStock seatStock : seatStocks) {
            int useNum = seatStock.getUseNum(); //原可用量
            if (useNum > 0) {
                if (useNum >= choseNum) {
                    seatStock.setUseNum(useNum - choseNum);//库存可用量
                    seatStock.setFreezeNum(seatStock.getFreezeNum() + choseNum);//冻结量 = 原冻结量 + 已选量
                    seatStockService.update(seatStock);
//                    detail.setChoseNum(choseNum);
                    detail.setSourceSeatCode(seatStock.getSeatCode());
                    newDetail = detail;
//                    newDetail.setChoseNum(useNum);
                    newDetail.setSourceSeatCode(seatStock.getSeatCode());
                    newDetails.add(newDetail);
                    break;
                } else {
                    seatStock.setUseNum(0);//库存可用量
//                        seatStock.setNowNum(0);//现有量
                    seatStock.setFreezeNum(seatStock.getFreezeNum() + useNum);//冻结量 = 原冻结量 + 原可用量
                    seatStockService.update(seatStock);
                    choseNum = choseNum - useNum;
                    newDetail = detail;
                    newDetail.setChoseNum(useNum);
                    newDetail.setSourceSeatCode(seatStock.getSeatCode());
                    newDetails.add(newDetail);
                }
            }
        }

        return newDetails;
    }

    @Override
    public List<OrderDetail> setSourceSeatCode(OrderDetail detail, String placeNumber) {
        List<OrderDetail> newDetails = new ArrayList<>();
        OrderDetail newDetail = new OrderDetail();
        GoodSku goodSku = goodSkuService.getBySku(detail.getSkuCode());
        Map<String, Object> params = new HashMap<>();
        params.put("goodCode", goodSku.getSkuCode());
//        params.put("gooderCode", good.getGooderCode());
        params.put("placeNumber", placeNumber);
        List<SeatStock> seatStocks = seatStockService.getByParams(params);
        Integer choseNum = detail.getChoseNum();
        //库位库存冻结
        for (SeatStock seatStock : seatStocks) {
            int useNum = seatStock.getUseNum(); //原可用量
            if (useNum > 0) {
                if (useNum >= choseNum) {

                    detail.setSourceSeatCode(seatStock.getSeatCode());
                    newDetail = detail;
                    newDetail.setSourceSeatCode(seatStock.getSeatCode());
                    newDetails.add(newDetail);
                    break;
                } else {
                    choseNum = choseNum - useNum;
                    newDetail = detail;
                    newDetail.setChoseNum(useNum);
                    newDetail.setSourceSeatCode(seatStock.getSeatCode());
                    newDetails.add(newDetail);
                }
            }
        }
        return newDetails;
    }

    @Override
    public void catchStock(OrderDetail orderDetail) {
        Integer number = orderDetail.getChoseNum();
        GoodSku goodSku = goodSkuService.getBySku(orderDetail.getSkuCode());
        GoodSpu goodSpu = goodSpuService.getBySpu(goodSku.getSpuCode());
        Map<String,Object> params = new HashMap<>();
        params.put("goodCode",goodSku.getSkuCode());
        params.put("seatCode",orderDetail.getTargetSeatCode());
        SeatStock targetSeatStock = seatStockService.getOneByParams(params);
        if(targetSeatStock != null) {
            targetSeatStock.setFreezeNum(targetSeatStock.getFreezeNum() + number);//目标库存冻结量增加
            targetSeatStock.setNowNum(targetSeatStock.getNowNum() + number);//目标库存现有量增加
            seatStockService.update(targetSeatStock);
        }else { //新增
            targetSeatStock = new SeatStock();
            targetSeatStock.setStockId(UUID.randomUUID().toString());
            targetSeatStock.setGooderCode(goodSpu.getGooderCode());
            targetSeatStock.setGoodCode(goodSku.getSkuCode());
            targetSeatStock.setSeatCode(orderDetail.getTargetSeatCode());
            targetSeatStock.setGoodName(goodSpu.getSpuName());
            targetSeatStock.setNowNum(orderDetail.getChoseNum());
            targetSeatStock.setUseNum(0);
            targetSeatStock.setAllotNum(0);
            targetSeatStock.setPickNum(0);
            targetSeatStock.setFreezeNum(orderDetail.getChoseNum());
            targetSeatStock.setFreezeStatus(1);
            targetSeatStock.setCreateId("system");
            targetSeatStock.setCreateDate(new Date());
            targetSeatStock.setModifyId("system");
            targetSeatStock.setModifyDate(new Date());
            seatStockService.save(targetSeatStock);
        }
        params.put("seatCode",orderDetail.getSourceSeatCode());
        SeatStock sourceSeatStock = seatStockService.getOneByParams(params);
        sourceSeatStock.setFreezeNum(sourceSeatStock.getFreezeNum() - number);//源库存冻结量减少
        sourceSeatStock.setNowNum(sourceSeatStock.getNowNum() - number);//源库存现有量减少
        seatStockService.update(sourceSeatStock);
    }

    @Override
    public void returnStock(OrderDetail orderDetail) {
        Integer number = orderDetail.getChoseNum();
        GoodSku goodSku = goodSkuService.getBySku(orderDetail.getSkuCode());
        Map<String,Object> params = new HashMap<>();
        params.put("goodCode",goodSku.getSkuCode());
        params.put("seatCode",orderDetail.getTargetSeatCode());
        SeatStock targetSeatStock = seatStockService.getOneByParams(params);
        targetSeatStock.setFreezeNum(targetSeatStock.getFreezeNum()- number);//目标库存冻结量减少
        targetSeatStock.setNowNum(targetSeatStock.getNowNum() - number);//目标库存现有量减少
        seatStockService.update(targetSeatStock);
        params.put("seatCode",orderDetail.getSourceSeatCode());
        SeatStock sourceSeatStock = seatStockService.getOneByParams(params);
        sourceSeatStock.setUseNum(sourceSeatStock.getUseNum() + number);//源库存可用量增加
        sourceSeatStock.setNowNum(sourceSeatStock.getNowNum() + number);//源库存现有量增加
        seatStockService.update(sourceSeatStock);
    }

    @Override
    public List<OrderDetail> queryByParams(Map<String, Object> params) {
        return orderDetailMapper.queryByParams(params);
    }

    @Override
    public void updateDetStatus(Integer orderId) {
        orderDetailMapper.updateDetStatus(orderId);
    }
}
