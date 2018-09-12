package com.nice.good.wx_web;

import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.BespeakOrderMapper;
import com.nice.good.dao.UserMapper;
import com.nice.good.service.BespeakOrderService;
import com.nice.good.service.CollarOrderService;
import com.nice.good.wx_model.User;
import com.nice.good.service.UserService;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.wx_model.UserVo;
import org.springframework.beans.BeanUtils;
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
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/08/24.
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);


	@Resource
	private UserService userService;

	@Resource
	private UserMapper userMapper;

	@Resource
	private CollarOrderService collarOrderService;

	@Resource
	private BespeakOrderService bespeakOrderService;

	@PostMapping("/add")
	public Result add(@RequestBody User user, HttpServletRequest request) {
		if (user == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}


		String userId = getUserName(request);

		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		try {

			userService.userAdd(user, userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody List<Integer> ids) {
		if (ids == null || ids.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}
		try {
			for (Integer id : ids) {
				//判断主播下面有没有未完成的订单
				List<Integer> collarStatus = collarOrderService.findStatusByOwnId(id);
				if (collarStatus.size() != 0) {
					if (!collarStatus.contains(4)) {
						return ResultGenerator.genFailResult(ResultCode.ORDER_IS_OWN);
					}
				}
				List<Integer> besStatus = bespeakOrderService.findStatusByOwnId(id);
				if (besStatus.size() != 0) {
					if (besStatus.contains(0)) {
						return ResultGenerator.genFailResult(ResultCode.ORDER_IS_OWN);
					}
				}
				userMapper.deleteByPrimaryKey(id);
			}

		} catch (Exception e) {
			log.error("删除对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/update/dept")
	public Result updateDept(@RequestBody UserVo uservo, HttpServletRequest request) {
		if (uservo == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		String userId = getUserName(request);

		if (uservo.getIds().size() == 0 || uservo.getIds() == null) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}
		try {
			for (Integer id : uservo.getIds()) {
				User user = userMapper.selectByPrimaryKey(id);
				user.setModifytime(new Date());
				user.setModifier(userId);
				user.setUserType(uservo.getUserType());
				userService.userUpdate(user, userId);
			}

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult();
	}


	@PostMapping("/update")
	public Result update(@RequestBody User user, HttpServletRequest request) {
		if (user == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		String userId = getUserName(request);

		if (user.getId() == null) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}
		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		String password = user.getPassword();
		if(!password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$")){
			return ResultGenerator.genFailResult(ResultCode.PASS_IS_WORD);
		}
		try {
			User newuser = userMapper.selectByPrimaryKey(user.getId());
			newuser.setModifytime(new Date());
			newuser.setModifier(userId);
			newuser.setPassword(user.getPassword());
			newuser.setUserType(user.getUserType());
			userService.userUpdate(newuser, userId);

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/detail")
	public Result detail(@RequestParam Integer id, HttpServletRequest request) {
		if (id == null) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}

		String userId = getUserName(request);


		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		UserVo userVo = new UserVo();
		try {
			User user = userMapper.selectByPrimaryKey(id);

			BeanUtils.copyProperties(user, userVo);
			String fansAgeGroup = userVo.getFansAgeGroup();
			String fansConsumingAbility = userVo.getFansConsumingAbility();
			if (StringUtils.isNotBlank(fansAgeGroup)) {
				String[] str = fansAgeGroup.split("-");
				if (str.length != 0) {
					userVo.setFansAge(str[0]);
					userVo.setFansAgeEnd(str[1]);
				}
			}
			if (StringUtils.isNotBlank(fansConsumingAbility)) {
				String[] str = fansConsumingAbility.split("-");
				if (str.length != 0) {
					userVo.setFansConsuming(str[0]);
					userVo.setFansConsumingEnd(str[1]);
				}

			}


		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult(userVo);
	}

	/**
	 * 主播档案查询
	 *
	 * @param user
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/list")
	public Result list(@RequestBody User user, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

		PageHelper.startPage(page, size);


		PageInfo pageInfo = null;
		try {
			List<User> list = userService.findByUser(user);
			pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult(pageInfo);
	}
}
