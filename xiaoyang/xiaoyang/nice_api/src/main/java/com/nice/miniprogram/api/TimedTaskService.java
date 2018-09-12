package com.nice.miniprogram.api;

import com.nice.miniprogram.dao.CollarOrderMapper;
import com.nice.miniprogram.model.BespeakOrder;
import com.nice.miniprogram.model.CollarOrder;
import com.nice.miniprogram.model.OrderDetail;
import com.nice.miniprogram.model.SysUser;
import com.nice.miniprogram.service.*;
import com.nice.miniprogram.utils.CodeFormatUtil;
import com.nice.miniprogram.utils.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Component
public class TimedTaskService {
	private static Logger log = LoggerFactory.getLogger(TimedTaskService.class);

	@Resource
	private BespeakOrderService bespeakOrderService;
	@Resource
	private CollarOrderService collarOrderService;
	@Resource
	private OrderDetailService orderDetailService;
	@Resource
	private CollarOrderMapper collarOrderMapper;
	@Resource
	private ShowRoomInfoService showRoomInfoService;
	@Resource
	private ExhibitionRoomService exhibitionRoomService;
	@Resource
	private WXMessageService WXMessageService;
	@Resource
	private SysUserService sysUserService;

	//    每5分钟启动
	@Scheduled(cron = "0 0/5 * * * ?")
	public void timerToNewCollarOrder() {
		System.out.println("========================================== 定时任务 预约开始前3小时内，根据预约单生成领用单 ==========================================");
//        System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(java.util.Calendar.HOUR_OF_DAY, 3);
		Map<String, Object> params = new HashMap<>();
		params.put("startTime", calendar.getTime());
		params.put("status", 0);
		List<BespeakOrder> bespeakOrders = bespeakOrderService.getListByStartTime(params);
		log.info("================================== 定时任务 新增领用单 ==================================");
//        List<CollarOrder> collarOrders = new ArrayList<>();
		for (BespeakOrder bespeakOrder : bespeakOrders) {
			CollarOrder collarOrder = new CollarOrder();
			Integer id = collarOrderService.selectMaxId();
			if (id == null || "".equals(id)) {
				id = 1;
			} else {
				id++;
			}
			String code = CodeFormatUtil.formatCode("JL", id);
			log.info("生成的订单编号：" + code);
			collarOrder.setOrderCode(code);
			collarOrder.setShowRoomId(bespeakOrder.getShowRoomId());
			collarOrder.setExhibitionRoomId(bespeakOrder.getExhibitionRoomId());
			collarOrder.setOwnId(bespeakOrder.getOwnId());
			collarOrder.setOperatorId(bespeakOrder.getOperatorId());
			collarOrder.setOperatorType(0);
			collarOrder.setCreater(bespeakOrder.getCreater());
			collarOrder.setCreatetime(new Date());
			collarOrder.setModifier(bespeakOrder.getModifier());
			collarOrder.setModifytime(new Date());
//            collarOrders.add(collarOrder);
			List<OrderDetail> newOrderDetails = new ArrayList<>();
			List<OrderDetail> orderDetails = orderDetailService.selectDetailListByOrderId(bespeakOrder.getId());
			if(orderDetails != null && orderDetails.size()>0) {
				for (OrderDetail orderDetail : orderDetails) {
					OrderDetail orderDetailNew = orderDetail;
					orderDetailNew.setId(null);
					orderDetailNew.setCreater("system");
					orderDetailNew.setCreatetime(new Date());
					orderDetailNew.setModifier("system");
					orderDetailNew.setModifytime(new Date());
					newOrderDetails.add(orderDetailNew);
				}
				String placeNumber = null;
				if (bespeakOrder.getShowRoomId() != null) {
					placeNumber = showRoomInfoService.queryPlaceNumber(bespeakOrder.getShowRoomId());
				} else if (null != bespeakOrder.getExhibitionRoomId()) {
					placeNumber = exhibitionRoomService.queryPlaceNumber(bespeakOrder.getExhibitionRoomId());
				}
				save(bespeakOrder, collarOrder, newOrderDetails, placeNumber);
				//消息推送
				if (null != bespeakOrder.getOperatorId() && bespeakOrder.getOperatorType() == 1) {
					Map<String, Object> messageMap = new HashMap<>(); //消息数据
					Map<String, Object> dataMap = new HashMap<>(); // 消息内容
					SysUser sysUser = sysUserService.findById(bespeakOrder.getOperatorId());
					messageMap.put("value", sysUser.getName());//预约人
					messageMap.put("color", "#173177");
					dataMap.put("keyword1", messageMap);
					messageMap.put("value", bespeakOrder.getOrderCode());//订单编号
					messageMap.put("color", "#173177");
					dataMap.put("keyword2", messageMap);
					messageMap.put("value", DateFormatUtil.dateToString(bespeakOrder.getBespeakTimeStart()));//开始时间
					messageMap.put("color", "#173177");
					dataMap.put("keyword3", messageMap);
					messageMap.put("value", DateFormatUtil.dateToString(bespeakOrder.getBespeakTimeEnd()));//结束时间
					messageMap.put("color", "#173177");
					dataMap.put("keyword4", messageMap);
					messageMap.put("value", "请在" + DateFormatUtil.dateToString(bespeakOrder.getBespeakTimeStart()) + "前进入直播间");//温馨提示
					messageMap.put("color", "#173177");
					dataMap.put("keyword5", messageMap);
					WXMessageService.pushMassege(bespeakOrder.getOwnId(), dataMap, 3);
				}
			}
		}
	}

	@Transactional
	public void save(BespeakOrder bespeakOrder,CollarOrder collarOrder, List<OrderDetail> orderDetails, String placeNumber) {
		collarOrderMapper.insert(collarOrder);
		int orderId = collarOrder.getId();
		List<OrderDetail> details = new ArrayList<>();
		//将主表id添加到明细中
		for (OrderDetail orderDetail : orderDetails) {
			orderDetail.setOrderId(orderId);
			orderDetail.setOrderType(0);//0：领用单
			orderDetail.setStatus(0);//待收货:0
			details.add(orderDetail);
		}
		bespeakOrder.setStatus(3);//已预约并已生成领用单
		bespeakOrderService.update(bespeakOrder);
		//库存冻结
		details = orderDetailService.freezeStock(details, placeNumber);
		//批量新增明细
		orderDetailService.batchInsert(details);

	}

	//    每分钟启动
	@Scheduled(cron = "0 0/1 * * * ?")
	public void timerToEndBespeakOrder() {
		System.out.println("========================================== 定时任务 超过结束时间，修改预约单状态为已完成 ==========================================");
		Date current = new Date();
		List<BespeakOrder> bespeakOrders = bespeakOrderService.selectList(null);
		for (BespeakOrder bespeakOrder : bespeakOrders) {
			if (bespeakOrder.getStatus() == 0 || bespeakOrder.getStatus() == 3) {
				if ((bespeakOrder.getBespeakTimeEnd()).before(current)) {
					bespeakOrder.setStatus(1);//状态：已完成
					bespeakOrderService.update(bespeakOrder);
					Integer orderId = bespeakOrder.getId();
					orderDetailService.updateDetStatus(orderId);
				}
			}
		}

	}

}
