
package com.nice.good.wx_web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.SeatStock;
import com.nice.good.model.SysUser;
import com.nice.good.service.*;
import com.nice.good.wx_model.*;
import com.nice.good.utils.CodeFormatUtil;
import com.nice.good.utils.TimeStampUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;


/**
 * Created by CodeGenerator on 2018/06/07.
 */

@RestController
@RequestMapping("/collarOrder")
public class CollarOrderController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(CollarOrderController.class);


	@Resource
	private CollarOrderService collarOrderService;

	@Resource
	private GooderService gooderService;

	@Resource
	private UserPlaceMapper userPlaceMapper;

	@Resource
	private StoreAreaMapper storeAreaMapper;

	@Resource
	private StoreSeatMapper storeSeatMapper;

	@Resource
	private ShowRoomInfoMapper showRoomInfoMapper;

	@Resource
	private GoodSkuService goodSkuService;

	@Resource
	private UserSeatMapper userSeatMapper;

	@Resource
	private SeatStockMapper seatStockMapper;

	@Resource
	private OrderDetailService orderDetailService;

	@Resource
	private OrderDetailMapper orderDetailMapper;

	@Resource
	private CollarOrderMapper collarOrderMapper;

	@Resource
	private UserMapper userMapper;

	/**
	 * 领用单内部领用新增
	 *
	 * @param collarOrderVo
	 * @param request
	 * @return
	 */

	@PostMapping("/add")
	public Result add(@RequestBody CollarOrderVo collarOrderVo, HttpServletRequest request) {
		log.info("================================== 新增领用单 ==================================");
		CollarOrder collarOrder = new CollarOrder();
		List<OrderDetailVo> collarOrderDetails = collarOrderVo.getCollarOrderDetails();
		if (collarOrderDetails == null || collarOrderDetails.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.DETAILS_ID_NULL);
		}

		String account = collarOrderVo.getAccount();
		if (account == null) {
			return ResultGenerator.genFailResult(ResultCode.OWN_IS_NULL);
		}
		Integer ownId = userMapper.findIdByAccount(account);

		//如果不是运营 则没有权限
		SysUser sessionUser = (SysUser) request.getSession().getAttribute("sessionUser");
		String userType = sessionUser.getUserType();
		if (!userType.equals("运营")) {
			return ResultGenerator.genFailResult(ResultCode.IS_NOT_OPERATING);
		}
		String placeId = getPlaceId(request);
		String targetSeatCode = userSeatMapper.findByseat(getUserName(request), placeId);//运营库位

		BeanUtils.copyProperties(collarOrderVo, collarOrder); //将vo中的数据copy到model中
		Timestamp timeStamp = TimeStampUtils.getTimeStamp();
		collarOrder.setOrderCode(getReceive());
		collarOrder.setOperatorId(getUserId(request));
		//操作类型
		String userName = getUserName(request);
		collarOrder.setOwnId(ownId);
		collarOrder.setOperatorType(1);
		collarOrder.setStatus(0);
		collarOrder.setCreater(userName);
		collarOrder.setCreatetime(timeStamp);
		collarOrder.setModifier(userName);
		collarOrder.setModifytime(timeStamp);
		collarOrder.setSeatCode(targetSeatCode);
		List<OrderDetail> newOrderDetails = new ArrayList<>();
		OrderDetail orderDetail = null;

		for (OrderDetailVo orderDetailVo : collarOrderDetails) {
			orderDetail = new OrderDetail();
			BeanUtils.copyProperties(orderDetailVo, orderDetail); //将vo中的数据copy到model中
			orderDetail.setOrderType(0);
			orderDetail.setStatus(0);
			//运营库位
			orderDetail.setTargetSeatCode(targetSeatCode);
			orderDetail.setSourceSeatCode(orderDetail.getSeatCode());//来源库位
			orderDetail.setCreater(userName);
			orderDetail.setCreatetime(timeStamp);
			orderDetail.setModifier(userName);
			orderDetail.setModifytime(timeStamp);
			newOrderDetails.add(orderDetail);
		}
		String errorMsg = "";

		errorMsg = collarOrderService.addCollarOrderAndDetail(collarOrder, newOrderDetails);
		if (StringUtils.isNotBlank(errorMsg)) {
			return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
		} else {
			return ResultGenerator.genSuccessResult();
		}
	}


	/**
	 * 点击新增的查询返回的model
	 */
	@PostMapping("/getRecipients")
	public Result getRecipients(HttpServletRequest request) {

		String placeId = getPlaceId(request);
		if (StringUtils.isBlank(placeId)) {
			return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
		}

		// 自动生成领用单编号
		String oraderCode = getReceive();
		PurchaseXcBean purchaseXcBean = new PurchaseXcBean();
		Timestamp timeStamp = TimeStampUtils.getTimeStamp();
		try {
			// 封装 货主编码 gooderCodes
			List<String> gooderCodes = getGooderCodes(request);
			List<String> placeNumbers = userPlaceMapper.findAllPlaceNumbers();
			List<String> areaCodes = storeAreaMapper.findAllAreaCode();
			List<String> roomCodes = showRoomInfoMapper.findAllCode();
			purchaseXcBean.setRoomCodes(roomCodes);
			List<String> seatCodes = storeSeatMapper.findWxSeatCode(placeId);
			List<String> accounts = userMapper.findAllAccount();
			purchaseXcBean.setAccount(accounts);
			purchaseXcBean.setOrderCode(oraderCode);
			purchaseXcBean.setGooderCode(gooderCodes);
			purchaseXcBean.setPlaceNumbers(placeNumbers);
			purchaseXcBean.setAreaCodes(areaCodes);
			purchaseXcBean.setSeatCode(seatCodes);
			purchaseXcBean.setCreater(getUserName(request));
			purchaseXcBean.setModifier(getUserName(request));
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
		}

		return ResultGenerator.genSuccessResult(purchaseXcBean);
	}


	@PostMapping("/getGood")
	public Result getGood(@RequestBody GoodSkuVo good, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

		PageHelper.startPage(page, size);

		PageInfo pageInfo = null;
		try {
			List<GoodSkuVo> list = goodSkuService.findBygoods(good);
			pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult(pageInfo);
	}


	/**
	 * 借领页面 点击借领
	 *
	 * @return
	 */
	@PostMapping("/update/borrowDetail")
	@Transactional
	public Result updateBorrowDetail(@RequestBody List<OrderDetail> orderDetails, HttpServletRequest request) {
		if (orderDetails.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		Integer orderId = 0;
		for (OrderDetail orderDetail : orderDetails) {
			if (orderDetail.getStatus() != 0) {
				return ResultGenerator.genFailResult(ResultCode.PENDING_ID_STATE);
			}
			OrderDetail detail = orderDetailMapper.selectByPrimaryKey(orderDetail.getId());
			detail.setModifier(getUserName(request));
			detail.setModifytime(new Date());
			orderDetailService.update(detail);
			orderDetailService.collarOrderUpdateByOrderId(orderDetail.getId());
			orderId = orderDetail.getOrderId();
		}
		if (orderId != 0) {
			List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(orderId);

			//对主表的状态进行更改
			int i = 0;
			int j = 0;
			for (OrderDetail orderDetail : orderDetailList) {
				//根据明细判断主表的状态
				if (orderDetail.getStatus() == 0) {
					i++;
				}
				if (orderDetail.getStatus() == 2) {
					j++;
				}
			}
			if (i == 0) { // i=0 说明主表应为已收货
				Integer status = 2;
				collarOrderService.updateById(orderId, status);
			} else if (i != 0 && j != 0) { //说明主表为部分收货
				Integer status = 1;
				collarOrderService.updateById(orderId, status);
			}
		}
		CollarOrder collarOrder = collarOrderMapper.selectByPrimaryKey(orderId);
		collarOrder.setModifytime(new Date());
		collarOrder.setModifier(getUserName(request));
		collarOrderService.update(collarOrder);
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 预约/借领页面 点击借领
	 *
	 * @return
	 */
	@PostMapping("/update/borrow")
	public Result updateBorrow(@RequestBody List<BookingSlipVo> BookingSlipVos, HttpServletRequest request) {
		if (BookingSlipVos.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		try {
			String orderCode1 = null;
			for (BookingSlipVo bookingSlipVo : BookingSlipVos) {
				if (bookingSlipVo.getStatus() != 0 && bookingSlipVo.getStatus() != 1) {
					return ResultGenerator.genFailResult(ResultCode.PENDING_ID_STATE);
				}
				String orderCode = bookingSlipVo.getOrderCode();
				collarOrderService.collarOrderUpdate(orderCode);
				orderDetailService.collarOrderUpdate(orderCode);
				if (orderCode == null) {
					return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
				}
				orderCode1 = orderCode;
			}
			if (orderCode1 != null) {

				CollarOrder collarOrder = collarOrderMapper.findCollarByOrderCode(orderCode1);
				collarOrder.setModifytime(new Date());
				collarOrder.setModifier(getUserName(request));
				collarOrderService.update(collarOrder);
			}
		} catch (Exception e) {
			log.error("更新对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 借领页面 点击归还
	 *
	 * @return
	 */
	@PostMapping("/update/repayDetail")
	@Transactional
	public Result updateRepayDetail(@RequestBody List<OrderDetail> orderDetails, HttpServletRequest request) {
		if (orderDetails.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		Integer orderId = 0;
		CollarOrder collarOrder = null;
		for (OrderDetail orderDetail : orderDetails) {
			if (orderDetail.getStatus() != 2) {
				return ResultGenerator.genFailResult(ResultCode.CAN_NOT_RETURN);
			}
			collarOrder = collarOrderMapper.selectByPrimaryKey(orderDetail.getOrderId());
			if (collarOrder.getStatus() != 2 && collarOrder.getStatus() != 3) {
				return ResultGenerator.genFailResult(ResultCode.CAN_NOT_RETURN);
			}
			OrderDetail detail = orderDetailMapper.selectByPrimaryKey(orderDetail.getId());
			detail.setModifier(getUserName(request));
			detail.setModifytime(new Date());
			orderDetailService.update(detail);
			orderDetailService.updateRepayDetail(orderDetail.getId());
			orderId = orderDetail.getOrderId();
			String seatCode = orderDetail.getSourceSeatCode();
			String goodCode = orderDetail.getSkuCode();
			SeatStock seatStock = seatStockMapper.findBySeatCode(seatCode, goodCode);
			Integer choseNum = orderDetail.getChoseNum();
			if (seatStock != null) {
				seatStock.setUseNum(seatStock.getUseNum() + choseNum);
				seatStock.setFreezeNum(seatStock.getFreezeNum() - choseNum);
				seatStockMapper.updateByPrimaryKey(seatStock);
			}
		}
		collarOrder.setModifier(getUserName(request));
		collarOrder.setModifytime(new Date());
		collarOrderService.update(collarOrder);
		if (orderId != 0) {
			List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(orderId);

			//对主表的状态进行更改
			int i = 0;
			int j = 0;
			for (OrderDetail orderDetail : orderDetailList) {
				//根据明细判断主表的状态
				if (orderDetail.getStatus() == 2) {
					i++;
				}
				if (orderDetail.getStatus() == 4) {
					j++;
				}
			}
			if (i == 0) { // i=0 说明主表应为已归还
				Integer status = 4;

				collarOrderService.updateById(orderId, status);
			} else if (i != 0 && j != 0) { //说明主表为部分归还
				Integer status = 3;
				collarOrderService.updateById(orderId, status);
			}

		}


		return ResultGenerator.genSuccessResult();
	}


	/**
	 * 预约/借领页面 点击归还
	 *
	 * @return
	 */
	@PostMapping("/update/repay")
	public Result updateRepay(@RequestBody List<BookingSlipVo> BookingSlipVos, HttpServletRequest request) {
		if (BookingSlipVos.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		try {
			String orderCode1 = null;
			for (BookingSlipVo bookingSlipVo : BookingSlipVos) {
				if (bookingSlipVo.getStatus() != 2) {
					return ResultGenerator.genFailResult(ResultCode.CAN_NOT_RETURN);
				}
				String orderCode = bookingSlipVo.getOrderCode();
				if (orderCode == null) {
					return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
				}
				List<OrderDetail> orderDetailList = orderDetailService.selectByOrderCode(orderCode);
				for (OrderDetail orderDetail : orderDetailList) {
					//恢复库存操作
					String seatCode = orderDetail.getSourceSeatCode();
					String goodCode = orderDetail.getSkuCode();
					SeatStock seatStock = seatStockMapper.findBySeatCode(seatCode, goodCode);
					Integer choseNum = orderDetail.getChoseNum();
					if (seatStock != null) {
						seatStock.setUseNum(seatStock.getUseNum() + choseNum);
						seatStock.setFreezeNum(seatStock.getFreezeNum() - choseNum);
						seatStockMapper.updateByPrimaryKey(seatStock);
					}
				}
				//修改状态
				collarOrderService.collarOrderRepayUpdate(orderCode);
				orderDetailService.collarOrderRepayUpdate(orderCode);
				orderCode1 = orderCode;
			}
			if (orderCode1 != null) {
				CollarOrder collarOrder = collarOrderMapper.findCollarByOrderCode(orderCode1);
				collarOrder.setModifytime(new Date());
				collarOrder.setModifier(getUserName(request));
				collarOrderService.update(collarOrder);
			}
		} catch (Exception e) {
			log.error("更新对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 查询借领单
	 *
	 * @return
	 */
	@PostMapping("/list")
	public CollarOrderVo list(@RequestBody BookingSlipVo bookingSlipVo) {

		CollarOrderVo collarOrderVo = null;
		try {
			String orderCode = bookingSlipVo.getOrderCode();
			collarOrderVo = collarOrderService.selectCollar(orderCode);
			collarOrderVo.setPlaceNumber(bookingSlipVo.getPlaceNumber());
			//明细
			Integer choseSum = 0;
			Integer takeNum = 0; //收货数量
			Integer giveNum = 0;//已归还数量
			List<OrderDetailVo> orderDetailVos = orderDetailService.selectBespeakDetail(collarOrderVo.getId());
			//对数量的统计
			for (OrderDetailVo vo : orderDetailVos) {
				if (vo.getStatus() == 2) {
					takeNum = vo.getChoseNum() + takeNum;
				}
				if (vo.getStatus() == 4) {
					giveNum = vo.getChoseNum() + giveNum;
				}
				choseSum = vo.getChoseNum() + choseSum;
			}
			collarOrderVo.setStayNum(takeNum);
			collarOrderVo.setGiveNum(giveNum);
			collarOrderVo.setChoseSum(choseSum);
			collarOrderVo.setTakeNum(takeNum);
			collarOrderVo.setAccount(bookingSlipVo.getAccount());
			collarOrderVo.setCollarOrderDetails(orderDetailVos);
			return collarOrderVo;
//			pageInfo = new PageInfo(bespeakOrderVo);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			e.printStackTrace();
		}
		return collarOrderVo;
	}


	/**
	 * 添加借领单明细
	 *
	 * @return
	 */
	@PostMapping("/addCollar")
	@Transactional
	public Result addCollar(@RequestBody CollarOrderVo collarOrderVo, HttpServletRequest request) {
		if (collarOrderVo == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		//借领明细新增
		List<OrderDetailVo> collarOrderDetails = collarOrderVo.getCollarOrderDetails();
		if (collarOrderDetails == null) {
			return ResultGenerator.genFailResult(ResultCode.DETAILS_ID_NULL);
		}
		if (collarOrderVo.getStatus() == 4) {
			return ResultGenerator.genFailResult(ResultCode.OFF_THE_STOCK);
		}
		SysUser sessionUser = (SysUser) request.getSession().getAttribute("sessionUser");
		String userType = sessionUser.getUserType();
		if (!userType.equals("运营")) {
			return ResultGenerator.genFailResult(ResultCode.IS_NOT_OPERATING);
		}
		String placeId = getPlaceId(request);
		String targetSeatCode = userSeatMapper.findByseat(getUserName(request), placeId);//运营库位
		String userName = getUserName(request);
		Timestamp timeStamp = TimeStampUtils.getTimeStamp();
		OrderDetail orderDetail = null;
		List<OrderDetail> newOrderDetails = new ArrayList<>();
		CollarOrder collarOrder = new CollarOrder();
		BeanUtils.copyProperties(collarOrderVo, collarOrder);
		collarOrder.setModifier(getUserName(request));
		collarOrder.setModifytime(new Date());
		collarOrderService.update(collarOrder);
		for (OrderDetailVo orderDetailVo : collarOrderDetails) {
			if (orderDetailVo.getCreatetime() == null) {
				orderDetail = new OrderDetail();
				BeanUtils.copyProperties(orderDetailVo, orderDetail); //将vo中的数据copy到model中
				orderDetail.setOrderType(0);
				orderDetail.setStatus(0);
				orderDetail.setSourceSeatCode(orderDetail.getSeatCode());//来源库位
				orderDetail.setTargetSeatCode(targetSeatCode);
				orderDetail.setCreater(userName);
				orderDetail.setCreatetime(timeStamp);
				orderDetail.setModifier(userName);
				orderDetail.setModifytime(timeStamp);
				orderDetail.setIsFeedback(0);
				orderDetail.setOrderId(collarOrderVo.getId());
				Integer choseNum = 1;
				orderDetail.setChoseNum(1);
				SeatStock seatStock = seatStockMapper.findByStockId(orderDetail.getStockId());
				if (choseNum > seatStock.getUseNum()) {
					return ResultGenerator.genFailResult(ResultCode.STOCK_IS_NULL);
				}
				if (seatStock == null) {
					return ResultGenerator.genFailResult(ResultCode.NO_STORAGE_LOCATION);
				}
				seatStock.setUseNum(seatStock.getUseNum() - choseNum);
				seatStock.setFreezeNum(seatStock.getFreezeNum() + choseNum);
				seatStockMapper.updateByPrimaryKey(seatStock);
				newOrderDetails.add(orderDetail);
			}

		}
		if (newOrderDetails.size() > 0) {
			orderDetailMapper.insertList(newOrderDetails);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 删除预约单明细
	 *
	 * @return
	 */
	@PostMapping("/deleteCollar")
	@Transactional
	public Result deleteCollar(@RequestBody List<OrderDetail> orderDetails, HttpServletRequest request) {
		if (orderDetails == null || orderDetails.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		Integer orderId = -1;
		for (OrderDetail orderDetail : orderDetails) {
			if (orderDetail.getStatus() != 4) {
				return ResultGenerator.genFailResult(ResultCode.DETAIL_STATUS_RETURN);
			}
			int choseNum = orderDetail.getChoseNum();
			SeatStock seatStock = seatStockMapper.findByStockId(orderDetail.getStockId());
			if (seatStock != null) {
				seatStock.setUseNum(seatStock.getUseNum() + choseNum);
				seatStock.setFreezeNum(seatStock.getFreezeNum() - choseNum);
				seatStockMapper.updateByPrimaryKey(seatStock);
			}

			orderDetailMapper.delete(orderDetail);
			orderId = orderDetail.getOrderId();
		}
		if (orderId != -1) {
			CollarOrder collarOrder = collarOrderMapper.selectByPrimaryKey(orderId);
			collarOrder.setModifier(getUserName(request));
			collarOrder.setModifytime(new Date());
			collarOrderService.update(collarOrder);
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 借领单号生成的util
	 */

	public String getReceive() {
		Integer id = collarOrderService.selectMaxId();
		if (id == null || "".equals(id)) {
			id = 1;
		} else {
			id++;
		}
		String code = CodeFormatUtil.formatCode("JL", id);
		return code;
	}
}

