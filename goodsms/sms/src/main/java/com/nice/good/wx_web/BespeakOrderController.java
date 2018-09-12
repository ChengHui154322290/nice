package com.nice.good.wx_web;

import com.nice.good.core.RedisService;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.model.SeatStock;
import com.nice.good.service.*;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.utils.CodeFormatUtil;
import com.nice.good.utils.DateCutUtils;
import com.nice.good.utils.DateFormatUtil;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.wx_model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/08/03.
 */
@RestController
@RequestMapping("/bespeak/order")
public class BespeakOrderController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(BespeakOrderController.class);


	@Resource
	private BespeakOrderService bespeakOrderService;

	@Resource
	private ShowRoomInfoService showRoomInfoService;

	@Resource
	private RedisService redisServicer;

	@Resource
	private ShowRoomInfoMapper showRoomInfoMapper;

	@Autowired
	private OrderDetailService orderDetailService;

	@Resource
	private SeatStockMapper seatStockMapper;

	@Resource
	private OrderDetailMapper orderDetailMapper;

	@Resource
	private ShowCountService showCountService;

	@Resource
	private CollarOrderService collarOrderService;

	@Resource
	private UserMapper userMapper;

	@Resource
	private BespeakOrderMapper bespeakOrderMapper;

	@Resource
	private WXMessageService WXMessageService;

	/**
	 * 新增预约单
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/add")
	@Transactional
	public Result add(@RequestBody BespeakOrderVo bespeakOrderVo, HttpServletRequest request) throws ParseException {

		log.info("==================================新增预约单==================================");
		if (bespeakOrderVo == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		if (StringUtils.isNotBlank(bespeakOrderVo.getBespeakTimeStartStr())
				&& StringUtils.isNotBlank(bespeakOrderVo.getBespeakTimeEndStr())) {
			bespeakOrderVo.setBespeakTimeStart(DateFormatUtil.stringToDate(bespeakOrderVo.getBespeakTimeStartStr()));
			bespeakOrderVo.setBespeakTimeEnd(DateFormatUtil.stringToDate(bespeakOrderVo.getBespeakTimeEndStr()));
		} else {
			return ResultGenerator.genFailResult(ResultCode.APPOINTMENT_IS_NULL);
		}
		for (OrderDetailVo orderDetailVo : bespeakOrderVo.getOrderDetailVoList()) {
			if (orderDetailVo.getChoseNum() == null || orderDetailVo.getChoseNum() < 0) {
				return ResultGenerator.genFailResult(ResultCode.CHOOSE_IS_NULL);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date bt = bespeakOrderVo.getBespeakTimeStart();
		Date dt = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(dt);
		ca.add(Calendar.HOUR_OF_DAY, 3);

		if (bt.before(ca.getTime())) {
			return ResultGenerator.genFailResult(ResultCode.CHOOSE_REASONABLE_TIME);
		}


		if (bespeakOrderVo.getShowRoomCode() == null) {
			return ResultGenerator.genFailResult(ResultCode.ROOMCODE_IS_NULL);
		}
		String account = bespeakOrderVo.getAccount();
		if (account == null) {
			return ResultGenerator.genFailResult(ResultCode.OWN_IS_NULL);
		}
		Integer ownId = userMapper.findIdByAccount(account);
		//预约时间要最多提前3天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 3);
		Date dateLine = calendar.getTime();
		if (dateLine.compareTo(bespeakOrderVo.getBespeakTimeStart()) < 0) {
			return ResultGenerator.genFailResult(ResultCode.DAYS_IN_ADVANCE);
		}
		//开始时间不能大于结束时间
		if ((bespeakOrderVo.getBespeakTimeStart()).compareTo(bespeakOrderVo.getBespeakTimeEnd()) > 0) {
			return ResultGenerator.genFailResult(ResultCode.CHOOSE_REASONABLE_TIME);
		}


		int startDay = DateCutUtils.getDay(bespeakOrderVo.getBespeakTimeStart()); //预约开始的日期
		int endDay = DateCutUtils.getDay(bespeakOrderVo.getBespeakTimeEnd()); //预约结束的日期
		int startHour = DateCutUtils.getHour(bespeakOrderVo.getBespeakTimeStart()); //预约开始的小时
		int endHour = DateCutUtils.getHour(bespeakOrderVo.getBespeakTimeEnd());     //预约结束的小时
		Timestamp timeStamp = TimeStampUtils.getTimeStamp();

		ShowRoomInfo showRoom = showRoomInfoMapper.findByCode(bespeakOrderVo.getShowRoomCode());
		Boolean flag = showRoomInfoService.checkIsChose(showRoom.getId(), startDay, endDay, startHour, endHour);
		if (flag) {  //true 为已被选
			return ResultGenerator.genFailResult(ResultCode.HAS_BENN_SELECTED);
		}
		String userName = getUserName(request);
		if (StringUtils.isBlank(userName)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}

		Date current = new Date();
		BespeakOrder bespeakOrder = new BespeakOrder();
		BeanUtils.copyProperties(bespeakOrderVo, bespeakOrder);
		Integer id = bespeakOrderService.selectMaxId();
		if (id == null || "".equals(id)) {
			id = 1;
		} else {
			id++;
		}
		String code = CodeFormatUtil.formatCode("PQ", id);
		log.info("生成的订单编号：" + code);
		bespeakOrder.setOwnId(ownId);
		bespeakOrder.setStatus(0);
		bespeakOrder.setOrderCode(code);
		bespeakOrder.setCreater(userName);
		bespeakOrder.setCreatetime(current);
		bespeakOrder.setModifier(userName);
		bespeakOrder.setModifytime(current);
		bespeakOrder.setShowRoomId(showRoom.getId());
		bespeakOrderService.save(bespeakOrder);
		//预约的直播间id+日期作为key,小时数为value
		//写入缓存
		if (startDay == endDay) {
			for (int i = startHour; i <= endHour; i++) {
				redisServicer.add(bespeakOrder.getShowRoomId() + "_" + startDay, i);
			}
		} else if (startDay < endDay) {
			for (int i = startHour; i < 24; i++) {
				redisServicer.add(bespeakOrder.getShowRoomId() + "_" + startDay, i);
			}
			for (int i = 0; i < endHour; i++) {
				redisServicer.add(bespeakOrder.getShowRoomId() + "_" + endDay, i);
			}
		}
		//借领明细新增
		List<OrderDetailVo> collarOrderDetails = bespeakOrderVo.getOrderDetailVoList();
		if (collarOrderDetails != null && collarOrderDetails.size() != 0) {
			OrderDetail orderDetail = null;
			List<OrderDetail> newOrderDetails = new ArrayList<>();
			for (OrderDetailVo orderDetailVo : collarOrderDetails) {
				orderDetail = new OrderDetail();
				BeanUtils.copyProperties(orderDetailVo, orderDetail); //将vo中的数据copy到model中
				orderDetail.setOrderType(1);
				orderDetail.setStatus(6);
				orderDetail.setTargetSeatCode(showRoom.getSeatCode()); //目标库位
				orderDetail.setSourceSeatCode(orderDetail.getSeatCode());//来源库位
				orderDetail.setCreater(userName);
				orderDetail.setCreatetime(timeStamp);
				orderDetail.setModifier(userName);
				orderDetail.setModifytime(timeStamp);
				orderDetail.setIsFeedback(0);
				orderDetail.setOrderId(bespeakOrder.getId());
				//库存扣减
				Integer choseNum = orderDetail.getChoseNum();
//				if (choseNum == null || choseNum < 0) {
//					return ResultGenerator.genFailResult(ResultCode.CHOOSE_IS_NULL);
//				}
				SeatStock seatStock = seatStockMapper.findByStockId(orderDetail.getStockId());
				if (choseNum > seatStock.getUseNum()) {
					return ResultGenerator.genSuccessResult(ResultCode.SUCCESS_AND_DEFEATED);
				}
				/*seatStock.setUseNum(seatStock.getUseNum() - choseNum);
				seatStock.setFreezeNum(seatStock.getFreezeNum() + choseNum);
				seatStockMapper.updateByPrimaryKey(seatStock);*/
				newOrderDetails.add(orderDetail);
			}
			orderDetailMapper.insertList(newOrderDetails);
			 /*====================== 预约量计数 ======================*/
			ShowCount showCount = showCountService.getByShowRoomId(bespeakOrder.getShowRoomId());
			if (showCount == null) {
				showCount = new ShowCount();
				showCount.setBespeakNum(1);
				showCount.setClickNum(1);
				showCount.setShowRoomId(bespeakOrder.getShowRoomId());
				showCount.setType(0);
				showCountService.newCountRecord(showCount);
			} else {
				showCountService.addBespeakNum(showCount.getId());
			}
		}

		//消息推送
		Map<String,Object> messageMap = new HashMap<>(); //消息数据
		Map<String,Object> dataMap = new HashMap<>(); // 消息内容
        messageMap.put("value",bespeakOrder.getOrderCode());//订单编号
        messageMap.put("color","#173177");
		dataMap.put("keyword1",messageMap);
		messageMap.put("value",DateFormatUtil.dateToString(bespeakOrder.getBespeakTimeStart()));//开始时间
		messageMap.put("color","#173177");
		dataMap.put("keyword2",messageMap);
		messageMap.put("value",DateFormatUtil.dateToString(bespeakOrder.getBespeakTimeEnd()));//结束时间
		messageMap.put("color","#173177");
		dataMap.put("keyword3",messageMap);
		messageMap.put("value","备注：您有一条新的预约单，请及时查看。");//温馨提示
		messageMap.put("color","#173177");
		dataMap.put("keyword4",messageMap);

		WXMessageService.pushMassege(ownId,dataMap,1);

		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/update")
	public Result update(@RequestBody BespeakOrder bespeakOrder, HttpServletRequest request) {
		if (bespeakOrder == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		String userId = getUserName(request);

		if (bespeakOrder.getId() == null) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}
		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		try {

			bespeakOrderService.bespeakOrderUpdate(bespeakOrder, userId);

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/detail")
	public Result detail(@RequestParam String id, HttpServletRequest request) {
		if (id == null) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}

		String userId = getUserName(request);


		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		BespeakOrder bespeakOrder = null;
		try {
			bespeakOrder = bespeakOrderService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult(bespeakOrder);
	}

	/**
	 * 查询所有的借领预约单
	 *
	 * @return
	 */
	@PostMapping("/listAll")
	public Result listAll(@RequestBody BookingSlipVo bookingSlipVo, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

		PageHelper.startPage(page, size);

		PageInfo pageInfo = null;
		try {
			List<BookingSlipVo> list1 = bespeakOrderService.selectBespeakAll(bookingSlipVo);
			for (BookingSlipVo bookings : list1) {
				bookings.setOrderType(1);
				//bookings.setTargetSeatCode(bookings.getShowRoomCode());
			}
			String placeId = getPlaceId(request);
			bookingSlipVo.setPlaceId(placeId);
			List<BookingSlipVo> list2 = bespeakOrderService.selectCollarAll(bookingSlipVo);
			for (BookingSlipVo bookings : list2) {
				bookings.setOrderType(0);
			}
			//内部借领 ,内部预约条件查询
			if (bookingSlipVo.getOrderType() != null) {
				if (bookingSlipVo.getOrderType() == 0) {
					pageInfo = new PageInfo(list2);
					pageInfo.setPageSize(size);
					return ResultGenerator.genSuccessResult(pageInfo);
				} else if (bookingSlipVo.getOrderType() == 1) {
					pageInfo = new PageInfo(list1);
					pageInfo.setPageSize(size);
					return ResultGenerator.genSuccessResult(pageInfo);
				}
			}
			if(bookingSlipVo.getBespeakTimeEnd()!=null&&bookingSlipVo.getBespeakTimeStart()!=null){
				pageInfo = new PageInfo(list1);
				pageInfo.setPageSize(size);
				return ResultGenerator.genSuccessResult(pageInfo);
			}
			List<BookingSlipVo> retList = new ArrayList<>();
			retList.addAll(list1);
			retList.addAll(list2);
			pageInfo = new PageInfo(retList);
			pageInfo.setPageSize(size);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult(pageInfo);
	}


	/**
	 * 取消预约单
	 */
	@PostMapping("/cancel")
	@Transactional
	public Result cancelOrder(@RequestBody List<String> orderCodes, HttpServletRequest request) {
//        User user = (User)request.getSession().getAttribute("user");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(java.util.Calendar.HOUR_OF_DAY, 3);
		Date startTimeLine = calendar.getTime();
		if (orderCodes.size() == 0 || orderCodes == null) {
			return ResultGenerator.genFailResult("请重新操作");
		}
		for (String orderCode : orderCodes) {
			BespeakOrder bespeakOrder = bespeakOrderService.getByOrderCode(orderCode);
			if ((startTimeLine).before(bespeakOrder.getBespeakTimeStart()) && bespeakOrder.getStatus() == 0) {
				bespeakOrder.setStatus(2);
				bespeakOrder.setModifier(getUserName(request));
				bespeakOrder.setModifytime(new Date());
				bespeakOrderService.update(bespeakOrder);
				//明细库存移动
				Integer orderId = bespeakOrder.getId();
				List<OrderDetail> details = orderDetailService.selectByParams(orderId);
				if (details != null && details.size() > 0) {
					for (OrderDetail orderDetail : details) {
						//Integer choseNum = orderDetail.getChoseNum();
						orderDetail.setStatus(8);
						orderDetailService.update(orderDetail);
						/*String seatCode = orderDetail.getSourceSeatCode();
						String goodCode = orderDetail.getSkuCode();
						SeatStock seatStock = seatStockMapper.findBySeatCode(seatCode, goodCode);
						if (seatStock != null) {
							seatStock.setUseNum(seatStock.getUseNum() + choseNum);
							seatStock.setFreezeNum(seatStock.getFreezeNum() - choseNum);
							seatStockMapper.updateByPrimaryKey(seatStock);
						}*/
					}
				}
			} else {
				return ResultGenerator.genFailResult(ResultCode.ORDER_IS_CANNOT);
			}
			//取消预约单，同时删除缓存
			int startDay = DateCutUtils.getDay(bespeakOrder.getBespeakTimeStart()); //预约开始的日期
			int endDay = DateCutUtils.getDay(bespeakOrder.getBespeakTimeEnd()); //预约结束的日期
			int startHour = DateCutUtils.getHour(bespeakOrder.getBespeakTimeStart()); //预约开始的小时
			int endHour = DateCutUtils.getHour(bespeakOrder.getBespeakTimeEnd());     //预约结束的小时
			//        Integer showRoomId = bespeakOrder.getShowRoomId();
			if (startDay == endDay) {
				Set members = redisServicer.setMembers(bespeakOrder.getShowRoomId() + "_" + startDay);
				for (int i = startHour; i <= endHour; i++) {
					members.remove(i);
				}
				redisServicer.removePattern(bespeakOrder.getShowRoomId() + "_" + startDay);
				for (Object value : members) {
					redisServicer.add(bespeakOrder.getShowRoomId() + "_" + startDay, value);
				}
			} else if (startDay < endDay) {
				Set members1 = redisServicer.setMembers(bespeakOrder.getShowRoomId() + "_" + startDay);
				for (int i = startHour; i < 24; i++) {
					members1.remove(i);
				}
				for (Object value : members1) {
					redisServicer.add(bespeakOrder.getShowRoomId() + "_" + startDay, value);
				}
				Set members2 = redisServicer.setMembers(bespeakOrder.getShowRoomId() + "_" + endDay);
				for (int i = 0; i < endHour; i++) {
					members2.remove(i);
				}
				for (Object value : members2) {
					redisServicer.add(bespeakOrder.getShowRoomId() + "_" + endDay, value);
				}
			}

			//消息推送
			Map<String,Object> messageMap = new HashMap<>(); //消息数据
			Map<String,Object> dataMap = new HashMap<>(); // 消息内容
			messageMap.put("value",bespeakOrder.getOrderCode());//订单编号
			messageMap.put("color","#173177");
			dataMap.put("keyword1",messageMap);
			messageMap.put("value",DateFormatUtil.dateToString(bespeakOrder.getBespeakTimeStart()));//开始时间
			messageMap.put("color","#173177");
			dataMap.put("keyword2",messageMap);
			messageMap.put("value",DateFormatUtil.dateToString(bespeakOrder.getBespeakTimeEnd()));//结束时间
			messageMap.put("color","#173177");
			dataMap.put("keyword3",messageMap);
			messageMap.put("value","备注：预约人已取消预约");//温馨提示
			messageMap.put("color","#173177");
			dataMap.put("keyword4",messageMap);

			WXMessageService.pushMassege(bespeakOrder.getOwnId(),dataMap,2);


		}


		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 删除预约单/借领单
	 *
	 * @return
	 */
	@PostMapping("/delete")
	@Transactional
	public Result delete(@RequestBody List<BookingSlipVo> bookingSlipVos) {
		if (bookingSlipVos == null || bookingSlipVos.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		for (BookingSlipVo bookingSlipVo : bookingSlipVos) {
			String orderCode = bookingSlipVo.getOrderCode();
			if (orderCode == null) {
				return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
			}
			Integer orderType = bookingSlipVo.getOrderType();


			if (orderType == 1) {   //删除预约单
				if (bookingSlipVo.getStatus() != 1 && bookingSlipVo.getStatus() != 2) {
					return ResultGenerator.genFailResult(ResultCode.CAN_BE_DELETED);
				}
				bespeakOrderService.deleteByOrderCode(orderCode);
				//删除预约明细
				orderDetailService.deleteByBesOrderCode(orderCode);
			} else if (orderType == 0) { //删除借领单
				if (bookingSlipVo.getStatus() != 4) {
					return ResultGenerator.genFailResult(ResultCode.HAS_BEEN_RETURNED);
				}

				collarOrderService.deleteByOrderCode(orderCode);
				//删除借领明细
				orderDetailService.deleteByCollarOrderCode(orderCode);
			}

		}
		return ResultGenerator.genSuccessResult();
	}


	/**
	 * 添加预约单明细
	 *
	 * @return
	 */
	@PostMapping("/addBes")
	@Transactional
	public Result addBes(@RequestBody BespeakOrderVo bespeakOrderVo, HttpServletRequest request) {
		if (bespeakOrderVo == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		if(bespeakOrderVo.getStatus()==1 || bespeakOrderVo.getStatus()==2){
			return ResultGenerator.genFailResult(ResultCode.OFF_THE_STOCK);
		}
		//借领明细新增
		String userName = getUserName(request);
		Timestamp timeStamp = TimeStampUtils.getTimeStamp();
		BespeakOrder bespeakOrder = new BespeakOrder();
		BeanUtils.copyProperties(bespeakOrderVo, bespeakOrder);
		bespeakOrder.setModifier(userName);
		bespeakOrder.setModifytime(timeStamp);
		bespeakOrderService.update(bespeakOrder);
		List<OrderDetailVo> collarOrderDetails = bespeakOrderVo.getOrderDetailVoList();
		if (collarOrderDetails == null) {
			return ResultGenerator.genFailResult(ResultCode.DETAILS_ID_NULL);
		}

		OrderDetail orderDetail = null;
		List<OrderDetail> newOrderDetails = new ArrayList<>();
		for (OrderDetailVo orderDetailVo : collarOrderDetails) {
			if (orderDetailVo.getCreatetime() == null) {
				orderDetail = new OrderDetail();
				BeanUtils.copyProperties(orderDetailVo, orderDetail); //将vo中的数据copy到model中
				orderDetail.setOrderType(1);
				orderDetail.setStatus(6);
				orderDetail.setSourceSeatCode(orderDetail.getSeatCode());//来源库位
				orderDetail.setTargetSeatCode(bespeakOrderVo.getShowRoomCode()); //目标库位
				orderDetail.setCreater(userName);
				orderDetail.setCreatetime(timeStamp);
				orderDetail.setModifier(userName);
				orderDetail.setModifytime(timeStamp);
				orderDetail.setIsFeedback(0);
				orderDetail.setOrderId(bespeakOrderVo.getId());
				//默认为1
				Integer choseNum = 1;
				orderDetail.setChoseNum(1);
				SeatStock seatStock = seatStockMapper.findByStockId(orderDetail.getStockId());
				if (choseNum > seatStock.getUseNum()) {
					return ResultGenerator.genFailResult(ResultCode.STOCK_IS_NULL);
				}
				seatStock.setUseNum(seatStock.getUseNum() - choseNum);
				seatStock.setFreezeNum(seatStock.getFreezeNum() + choseNum);
				seatStockMapper.updateByPrimaryKey(seatStock);
				newOrderDetails.add(orderDetail);
			}

		}
		if(newOrderDetails.size()>0){
			orderDetailMapper.insertList(newOrderDetails);
		}

		return ResultGenerator.genSuccessResult();
	}


	/**
	 * 删除预约单明细
	 *
	 * @return
	 */
	@PostMapping("/deleteBes")
	@Transactional
	public Result deleteBes(@RequestBody List<OrderDetail> orderDetails, HttpServletRequest request) {
		if (orderDetails == null || orderDetails.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		Integer orderId = -1;
		for (OrderDetail orderDetail : orderDetails) {
			if (orderDetail.getStatus() != 6) {
				return ResultGenerator.genFailResult("已预约状态下的明细才可删除");
			}
			int choseNum = orderDetail.getChoseNum();
			SeatStock seatStock = seatStockMapper.findByStockId(orderDetail.getStockId());
			if (seatStock != null) {
				seatStock.setUseNum(seatStock.getUseNum() - choseNum);
				seatStock.setFreezeNum(seatStock.getFreezeNum() + choseNum);
				seatStockMapper.updateByPrimaryKey(seatStock);
			}
			orderDetailMapper.delete(orderDetail);
			orderId = orderDetail.getOrderId();
		}
		if (orderId != -1) {
			BespeakOrder bespeakOrder = bespeakOrderMapper.selectByPrimaryKey(orderId);
			bespeakOrder.setModifier(getUserName(request));
			bespeakOrder.setModifytime(new Date());
			bespeakOrderService.update(bespeakOrder);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 查询预约单
	 *
	 * @return
	 */
	@PostMapping("/list")
	public BespeakOrderVo list(@RequestBody BookingSlipVo bookingSlipVo) {

		BespeakOrderVo bespeakOrderVo = null;
		try {
			String orderCode = bookingSlipVo.getOrderCode();
			bespeakOrderVo = bespeakOrderService.selectBespeak(orderCode);
			bespeakOrderVo.setPlaceNumber(bookingSlipVo.getPlaceNumber());
			bespeakOrderVo.setShowRoomCode(bookingSlipVo.getShowRoomCode());
			//明细
			int choseSum = 0;
			List<OrderDetailVo> orderDetailVos = orderDetailService.selectBespeakDetail(bespeakOrderVo.getId());

			for (OrderDetailVo vo : orderDetailVos) {
				choseSum = vo.getChoseNum() + choseSum;
			}
			bespeakOrderVo.setAccount(bookingSlipVo.getAccount());
			bespeakOrderVo.setChoseSum(choseSum);
			bespeakOrderVo.setOrderDetailVoList(orderDetailVos);
			return bespeakOrderVo;
//			pageInfo = new PageInfo(bespeakOrderVo);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
		}
		return bespeakOrderVo;
	}
}
