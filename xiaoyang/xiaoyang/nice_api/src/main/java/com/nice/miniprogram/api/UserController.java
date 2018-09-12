package com.nice.miniprogram.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.enums.ResultCode;
import com.nice.miniprogram.model.User;
import com.nice.miniprogram.service.UserService;
import com.nice.miniprogram.utils.HttpUtil;
import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;
import sun.security.util.Password;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


/**
 * Created by CodeGenerator on 2018/06/11.
 */
@Api(value = "用户信息接口",description = "用户注册、登录、修改及查看个人信息")
@RestController
@RequestMapping("/user")
public class UserController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);

	/** ======================== test ========================= */
//	private final static String AppID = "wx52b91795cbdab5b5";
//	private final static String AppSecret = "cd070f299bdee837e9883779f64cbd65";

	/**  ============================ server ============================== */
	private final String AppID = "wxc1137947f18d078c";
	private final String AppSecret = "7bd57909f01d1b59edcf6568ee347cd8";


	@Resource
	private UserService userService;

	/**
	 * 小程序授权之后用户注册
	 *
	 */
	@ApiOperation(value = "用户注册", httpMethod = "POST")
	@PostMapping("/add")
	public Result add(@RequestBody UserVo userVo, HttpServletRequest request) {
		log.info("================================== 小程序授权，新增用户 ==================================");
		if (userVo == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		User user = new User();
		BeanUtils.copyProperties(userVo,user);
		String account = user.getAccount();
		if(StringUtils.isBlank(account)||account==null){
			return ResultGenerator.genFailResult("请输入手机号");
		}
		if (!account.matches("^((13[0-9])|(14[579])|(15[0-3,5-9])|(16[6])|(17[0135678])|(18[0-9])|(19[89]))\\d{8}$")) {
			return ResultGenerator.genFailResult("请输入正确的手机号");
		}
		//判断该账号是否存在
		int i = userService.selectByCount(account);
		if(i==1){
			return ResultGenerator.genFailResult("该账号已经注册,请直接登录");
		}
		//校验密码
		String password = user.getPassword();
		if(!password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$")){
			return ResultGenerator.genFailResult("请输入合法的密码");
		}
		if(null != userVo.getNickName()) {
			String retNickName = removeFourChar(userVo.getNickName(),userVo.getAccount());
			user.setNickName(retNickName);
		}
		user.setLoginNum(0);
		user.setCreater(user.getAccount());
		user.setModifier(user.getAccount());
		user.setCreatetime(TimeStampUtils.getTimeStamp());
		user.setModifytime(TimeStampUtils.getTimeStamp());
		userService.save(user);
		HttpSession session = request.getSession();
		session.setAttribute("user",user);
		return ResultGenerator.genSuccessResult("注册成功");
	}
	/**
	 * 用户登录
	 */
	@ApiOperation(value = "用户登录", httpMethod = "POST")
	@PostMapping("/login")
	public Result login(@RequestBody User user, HttpServletRequest request) {
		log.info("================================== 用户登录 ==================================");
		if (user == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		String account = user.getAccount();
		String password = user.getPassword();
		if(StringUtils.isBlank(account)||account==null){
			return ResultGenerator.genFailResult("请输入账号");
		}
		//判断该账号是否存在
		User user1 = userService.findUser(account);
		if(user1==null){
			return ResultGenerator.genFailResult("该账号不存在");
		}
		if(user1.getUserType()==null || user1.getUserType()==0){
			return ResultGenerator.genFailResult(ResultCode.NOT_ACCESS);
		}
		//校验密码
		String oldPassword = user1.getPassword();
		if(!oldPassword.equals(password)){
			return ResultGenerator.genFailResult("密码错误");
		}
		Integer loginNum=0;
		if(user1.getLoginNum()==null){
			loginNum = 1;
		}else{
			loginNum= user1.getLoginNum()+1;
		}
		if(null != user.getNickName()) {
			String retNickName = removeFourChar(user.getNickName(),user.getAccount());
			user1.setNickName(retNickName);
		}
		user1.setLoginNum(loginNum);
		userService.updateNum(user1);
//		HttpSession session = request.getSession();
		request.getSession().setAttribute("user",user1);
		// 设置 session 超时机制
		HttpSession sessionOutTime = request.getSession(true);
		//设置cookie失效时间,一个小时
		int existTime=60*60;
		// 将sessionOutTime 的值设置为 12小时
		sessionOutTime.setMaxInactiveInterval(existTime);

		return ResultGenerator.genSuccessResult(user1);
	}

	@Transactional
	@ApiOperation(value = "上传头像", httpMethod = "POST")
	@PostMapping("/uploadHead")
	public Result uploadHead(@RequestParam("file") MultipartFile multipartFile,
							 @ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
							 HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		List<MultipartFile> files = multipartRequest.getFiles("file");
		if(files == null ||files.size()==0){
			return ResultGenerator.genFailResult(ResultCode.PICTURE_IS_NULL);
		}
		MultipartFile file = files.get(0);
		//用来限制用户上传文件大小的  4M
		int maxPostSize = 4 * 1024 * 1024;
		long size = file.getSize();
		if(size>maxPostSize){
			//图片大于一兆
			return ResultGenerator.genFailResult(ResultCode.PIC_OVER_MAXSIZE);
		}
		String fileName = file.getOriginalFilename();
		//允许上传的文件类型
		String fileType = "png,bmp,jpeg,jpg,jpe";
		//获取文件后缀名
		String extName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase().trim();
		if ( !Arrays.<String> asList(fileType.split(",")).contains(extName)) {
			return ResultGenerator.genFailResult(ResultCode.PIC_TYPE_ERROR);
		}
		//上传文件
		uploadFileUtil(file.getBytes(), "/nice/miniprogram/img/", fileName);
		User user = userService.getById(userId);
		user.setPicture(fileName);
		userService.update(user);
		return ResultGenerator.genSuccessResult();

	}
	/**
	 * 上传文件的方法
	 *
	 * @param file：文件的字节
	 * @param imgPath：文件的路径
	 * @param imgName：文件的名字
	 * @throws Exception
	 */
	private void uploadFileUtil(byte[] file, String imgPath, String imgName) throws Exception {
		File targetFile = new File(imgPath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(imgPath + imgName);
		out.write(file);
		out.flush();
		out.close();
	}

	/**
	 * 首次登陆
	 * 完善个人信息
	 * 点击确定
	 * 修改user表
	 * @param userVo
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "完善个人信息", httpMethod = "POST")
	@PostMapping("/update")
	public Result update(@RequestBody UserVo userVo, HttpServletRequest request) {
		log.info("================================== 完善用户个人信息 ==================================");
//		Integer id =userVo.getId();
		Integer id =userVo.getUserId();
		User user = userService.getUser(id);

		if(StringUtils.isNotBlank(userVo.getPicture())) {
			user.setPicture(userVo.getPicture());
		}
		if(null != userVo.getNickName()) {
			String retNickName = removeFourChar(userVo.getNickName(),userVo.getAccount());
			user.setNickName(retNickName);
		}
		if(null != userVo.getAnchorPlatform()) {
			user.setAnchorPlatform(userVo.getAnchorPlatform());
		}
		if(null != userVo.getAnchorUid()) {
			user.setAnchorUid(userVo.getAnchorUid());
		}
		if(null!= userVo.getAnchorSex()) {
			user.setAnchorSex(userVo.getAnchorSex());
		}
		if(null != userVo.getFansAgeGroup()) {
			user.setFansAgeGroup(userVo.getFansAgeGroup());
		}
		if(null != userVo.getFansConsumingAbility()) {
			user.setFansConsumingAbility(userVo.getFansConsumingAbility());
		}
		if(null != userVo.getAnchorStyle()) {
			user.setAnchorStyle(userVo.getAnchorStyle());
		}
		if(null != userVo.getRemark()) {
			user.setRemark(userVo.getRemark());
		}
		if(null != userVo.getOrgName()) {
			user.setOrgName(userVo.getOrgName());
		}

//		user.setModifier(userVo.getAccount());
		user.setModifytime(TimeStampUtils.getTimeStamp());

		userService.update(user);
		HttpSession session = request.getSession();
		session.setAttribute("user",user);
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 个人信息
	 */
	@ApiOperation(value = "根据用户id，获取用户个人信息", httpMethod = "GET")
	@GetMapping("/getUser")
	public Result list(@ApiParam(value = "用户id",required = true) @RequestParam(value = "id") Integer id, HttpServletRequest request) {
		log.info("================================== 获取用户个人信息 ==================================");
		User user = userService.getUser(id);
		Map<String,Object> retMap = new HashMap<>();
		retMap.put("picture",user.getPicture());
		if(StringUtils.isNotBlank(user.getNickName())) {
			retMap.put("nickName", user.getNickName());
		}else{
			retMap.put("nickName","");
		}
		retMap.put("account",user.getAccount());
		if(StringUtils.isNotBlank(user.getAnchorPlatform())) {
			retMap.put("anchorPlatform",user.getAnchorPlatform());
		}else{
			retMap.put("anchorPlatform","");
		}
		if(StringUtils.isNotBlank(user.getAnchorUid())) {
			retMap.put("anchorUid", user.getAnchorUid());
		}else{
			retMap.put("anchorUid","");
		}
		retMap.put("anchorSex", user.getAnchorSex());
		if(StringUtils.isNotBlank(user.getFansAgeGroup())) {
			retMap.put("fansAgeGroup", user.getFansAgeGroup());
		}else{
			retMap.put("fansAgeGroup","");
		}
		if(StringUtils.isNotBlank(user.getFansConsumingAbility())) {
			retMap.put("fansConsumingAbility", user.getFansConsumingAbility());
		}else{
			retMap.put("fansConsumingAbility","");
		}
		if(StringUtils.isNotBlank(user.getAnchorStyle())) {
			retMap.put("anchorStyle", user.getAnchorStyle());
		}else{
			retMap.put("anchorStyle","");
		}
		if(StringUtils.isNotBlank(user.getRemark())) {
			retMap.put("remark", user.getRemark());
		}else{
			retMap.put("remark","");
		}
		if(StringUtils.isNotBlank(user.getOrgName())) {
			retMap.put("orgName", user.getOrgName());
		}else{
			retMap.put("orgName","");
		}
		retMap.put("userId",user.getId());
		return ResultGenerator.genSuccessResult(retMap);
	}

	/**
	 * 修改密码
	 */
	@ApiOperation(value = "修改密码", httpMethod = "POST")
	@PostMapping("/modifyPassword")
	public Result modifyPassword(@ApiParam(value = "用户id",required = true) @RequestParam(value = "id") Integer id,
								 @ApiParam(value = "原始密码",required = true) @RequestParam(value = "oldPassword") String oldPassword,
								 @ApiParam(value = "新密码",required = true) @RequestParam(value = "newPassword") String newPassword,
								 @ApiParam(value = "确认密码",required = true) @RequestParam(value = "confirmPassword") String confirmPassword,
								 HttpServletRequest request) {

		User user = userService.getById(id);
		if((user.getPassword()).equals(oldPassword)){
			if(!(user.getPassword()).equals(newPassword)){
				if(newPassword.equals(confirmPassword)){
					user.setPassword(newPassword);
					userService.update(user);
					return ResultGenerator.genSuccessResult();
				}
			}
		}else {
			return ResultGenerator.genFailResult(ResultCode.OLDPASSWORD_ERROR);
		}
		return ResultGenerator.genFailResult(ResultCode.FAIL);
	}

	/**
	 * 获取微信小程序 session_key 和 openid
	 *
	 * @param code
	 * @author zy
	 */
	@ApiOperation(value = "获取openId", httpMethod = "POST")
	@PostMapping("/getSessionKeyOropenid")
//	public static JSONObject getSessionKeyOropenid(@ApiParam(value = "code",required = true) @RequestParam(value = "code") String code) {
	public Result getSessionKeyOropenid(@ApiParam(value = "code",required = true) @RequestParam(value = "code") String code) {
		log.info("==================================== 接口获取openid,请求参数code：" + code +" =========================================");
		//微信端登录code值
		String wxCode = code;
		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
		Map<String, String> requestUrlParam = new HashMap<String, String>();
		requestUrlParam.put("appid", AppID );  //开发者设置中的appId
		requestUrlParam.put("secret", AppSecret); //开发者设置中的appSecret
		requestUrlParam.put("js_code", wxCode); //小程序调用wx.login返回的code
		requestUrlParam.put("grant_type", "authorization_code");    //默认参数
		//发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
		JSONObject jsonObject = JSON.parseObject(HttpUtil.sendPost(requestUrl, requestUrlParam));
		log.info("==================================== 接口获取openid返回：" + jsonObject+" =========================================");
		String openId = jsonObject.get("openid").toString();
		String session_key = jsonObject.get("session_key").toString();
		Map<String,Object> retMap = new HashMap<>();
		retMap.put("openid",openId);
		retMap.put("session_key",session_key);
		return ResultGenerator.genSuccessResult(retMap);
//		return jsonObject;
	}



	/**
	 * 替换四个字节的字符 '\xF0\x9F\x98\x84\xF0\x9F）的解决方案
	 */
	public static String removeFourChar(String content,String account) {
		byte[] conbyte = content.getBytes();
		for (int i = 0; i < conbyte.length; i++) {
			if ((conbyte[i] & 0xF8) == 0xF0) {
				for (int j = 0; j < 4; j++) {
					conbyte[i+j]=0x30;
				}
				i += 3;
			}
		}
		content = new String(conbyte);
		if(content.replaceAll("0000", "").length() ==0 ){
			return account;
		}else{
			return content.replaceAll("0000", "");
		}

	}
}