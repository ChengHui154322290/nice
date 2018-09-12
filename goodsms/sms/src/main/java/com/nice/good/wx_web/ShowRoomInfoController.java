package com.nice.good.wx_web;

import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.model.StoreSeat;
import com.nice.good.wx_model.PurchaseXcBean;
import com.nice.good.wx_model.ShowRoomInfo;
import com.nice.good.service.ShowRoomInfoService;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/08/06.
 */
@RestController
@RequestMapping("/show/room/info")
public class ShowRoomInfoController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(ShowRoomInfoController.class);


	@Resource
	private ShowRoomInfoService showRoomInfoService;

	@Resource
	private RoomStyleMapper roomStyleMapper;

	@Resource
	StoreSeatMapper storeSeatMapper;

	@Resource
	SysPlaceMapper sysPlaceMapper;

	@Resource
	ShowRoomInfoMapper showRoomInfoMapper;

	@Resource
	RoomPictureMapper roomPictureMapper;

	@PostMapping("/add")
	public Result add(@RequestBody ShowRoomInfo showRoomInfo, HttpServletRequest request) {
		if (showRoomInfo == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		String placeNumber = showRoomInfo.getPlaceNumber();
		String code = showRoomInfo.getCode();
		ShowRoomInfo showRoomInfo1 = showRoomInfoMapper.findByCodeNum(code, placeNumber);
		if (showRoomInfo1 != null) {
			return ResultGenerator.genFailResult(ResultCode.CODE_ALREADY_EXISTS);
		}


		Date date = new Date();
		String userId = getUserName(request);

		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		showRoomInfo.setCreater(userId);
		showRoomInfo.setCreatetime(date);
		showRoomInfo.setModifier(userId);
		showRoomInfo.setModifytime(date);
		try {

			showRoomInfoService.showRoomInfoAdd(showRoomInfo, userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody List<Integer> ids) {
		if (ids == null || ids.size()==0) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}
		try {
			for(Integer id:ids){
				showRoomInfoMapper.deleteByPrimaryKey(id);
			}
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult();
	}

	//修改
	@PostMapping("/update")
	public Result update(@RequestBody ShowRoomInfo showRoomInfo, HttpServletRequest request) {
		if (showRoomInfo == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		String userId = getUserName(request);

		if (showRoomInfo.getId() == null) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}
		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		try {
			showRoomInfo.setModifier(getUserName(request));
			showRoomInfo.setModifytime(new Date());
			showRoomInfoService.showRoomInfoUpdate(showRoomInfo, userId);

			List<String> list = showRoomInfo.getImgIds();

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
 		return ResultGenerator.genSuccessResult();
	}


	//修改查询
	@PostMapping("/detail")
	public Result detail(@RequestParam Integer id, HttpServletRequest request) {
		if (id == null) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}

		String userId = getUserName(request);


		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		ShowRoomInfo showRoomInfo = null;
		try {
			showRoomInfo = showRoomInfoMapper.selectByPrimaryKey(id);
			List<String> list = roomPictureMapper.selectImgsByRoomId(id);
			showRoomInfo.setImgIds(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult(showRoomInfo);
	}


	@PostMapping("/getRecipients")
	public Result getRecipients(HttpServletRequest request) {


		PurchaseXcBean purchaseXcBean = new PurchaseXcBean();
		try {
			String placeNumber = sysPlaceMapper.findPlaceNumberById(getPlaceId(request));
			List<String> styles = showRoomInfoMapper.selectRoomStyles();
			purchaseXcBean.setPlaceNumber(placeNumber);
			purchaseXcBean.setStyles(styles);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			e.printStackTrace();
		}
		return ResultGenerator.genSuccessResult(purchaseXcBean);
	}

	@PostMapping("/list")
	public Result list(@RequestBody ShowRoomInfo showRoomInfo, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

		PageHelper.startPage(page, size);

		Map<String, Object> map = new HashMap<>();


		String placeNumber = null;
		String name = null;
		String code = null;
		String equipment = null;
		String style = null;
		String remark = null;

		if (showRoomInfo != null) {
			placeNumber = showRoomInfo.getPlaceNumber();
			name = showRoomInfo.getName();
			code = showRoomInfo.getCode();
			equipment = showRoomInfo.getEquipment();
			remark = showRoomInfo.getRemark();
			style = showRoomInfo.getStyle();

		}
		map.put("placeNumber", placeNumber);
		map.put("name", name);
		map.put("code", code);
		map.put("equipment", equipment);
		map.put("style", style);
		map.put("remark", remark);


		PageInfo pageInfo = null;
		try {
			List<ShowRoomInfo> list = showRoomInfoMapper.findByConditions(map);
			pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	@PostMapping("/listSeat")
	public Result list(@RequestBody StoreSeat storeSeat, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {

		String placeId = getPlaceId(request);
		if (StringUtils.isBlank(placeId)) {
			return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
		}
		PageHelper.startPage(page, size);

		Map<String, Object> seatMap = new HashMap<>();

		String areaCode = null;
		String seatCode = null;
		if (storeSeat != null) {
			areaCode = storeSeat.getAreaCode();
			seatCode = storeSeat.getSeatCode();
		}
		seatMap.put("placeId", placeId);
		seatMap.put("areaCode", areaCode);
		seatMap.put("seatCode", seatCode);
		PageInfo pageInfo = null;
		try {
			List<StoreSeat> list = storeSeatMapper.findByConditionWx(seatMap);

			pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult(pageInfo);
	}

}
