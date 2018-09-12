package com.nice.good.service.impl;


import com.nice.good.core.AbstractService;
import com.nice.good.dao.*;
import com.nice.good.model.SeatStock;
import com.nice.good.wx_model.BookingSlipVo;
import com.nice.good.wx_model.CollarOrder;
import com.nice.good.service.CollarOrderService;
import com.nice.good.wx_model.CollarOrderVo;
import com.nice.good.wx_model.OrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * Created by CodeGenerator on 2018/06/07.
 */
@Service
public class CollarOrderServiceImpl extends AbstractService<CollarOrder> implements CollarOrderService {
	private static Logger log = LoggerFactory.getLogger(CollarOrderServiceImpl.class);
	@Resource
	private CollarOrderMapper collarOrderMapper;
	@Resource
	private OrderDetailMapper orderDetailMapper;
	@Resource
	private SeatStockMapper seatStockMapper;

	/*
	新增领用订单及明细
	 */
	@Override
	@Transactional
	public String  addCollarOrderAndDetail(CollarOrder collarOrder, List<OrderDetail> orderDetails) {
		log.info("新增领用订单及明细 参数： ");
		collarOrderMapper.insert(collarOrder);
		String errorMsg="";
		int orderId = collarOrder.getId();
		List<OrderDetail> details = new ArrayList<>();
		for (OrderDetail orderDetail : orderDetails) {
			orderDetail.setOrderId(orderId);
			orderDetail.setOrderType(0);//0：领用单
			orderDetail.setStatus(0);//待收货:0
			orderDetail.setIsFeedback(0);
			details.add(orderDetail);
			//库存扣减
			Integer choseNum = orderDetail.getChoseNum();
			if(choseNum==null || choseNum<0){
				 errorMsg = "领用量不能为空";
				return errorMsg;
			}
			SeatStock seatStock = seatStockMapper.findByStockId(orderDetail.getStockId());
			if(seatStock==null){
				errorMsg = "该货品在库存中不存在";
				return errorMsg;
			}
			if(choseNum>seatStock.getUseNum()){
				 errorMsg = "库存不足";
				return errorMsg;
			}
			seatStock.setUseNum(seatStock.getUseNum()-choseNum);
			seatStock.setFreezeNum(seatStock.getFreezeNum()+choseNum);
			seatStockMapper.updateByPrimaryKey(seatStock);
		}
		orderDetailMapper.insertList(details);
		return errorMsg;
	}


	@Override
	public Integer selectMaxId() {
		return collarOrderMapper.selectMaxId();
	}

	@Override
	public CollarOrderVo selectCollar(String orderCode) {
		return collarOrderMapper.selectCollar(orderCode);
	}

	@Override
	public void collarOrderUpdate(String orderCode) {
		collarOrderMapper.collarOrderUpdate(orderCode);
	}

	@Override
	public void collarOrderRepayUpdate(String orderCode) {
		collarOrderMapper.collarOrderRepayUpdate(orderCode);
	}

	@Override
	public void deleteByOrderCode(String orderCode) {
		collarOrderMapper.deleteByOrderCode(orderCode);
	}

	@Override
	public void updateById(Integer orderId,Integer status) {
		collarOrderMapper.updateById(orderId,status);
	}

	@Override
	public List<Integer> findStatusByOwnId(Integer ownId) {
		return collarOrderMapper.findStatusByOwnId(ownId);
	}

}
