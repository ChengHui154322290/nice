package com.nice.miniprogram.api;

import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.enums.ResultCode;
import com.nice.miniprogram.model.*;
import com.nice.miniprogram.service.*;
import com.nice.miniprogram.vo.AlternativeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * Created by CodeGenerator on 2018/06/08.
 */
@Api(value = "已选接口",description = "对已选的样品进行相关操作")
@RestController
@RequestMapping("/alternative")
public class AlternativeController {

	private static Logger log = LoggerFactory.getLogger(AlternativeController.class);


	@Resource
	private AlternativeService alternativeService;
	@Resource
	private SysPlaceService sysPlaceService;
//	@Resource
//	private GoodService goodService;
	@Resource
	private GoodPictureService goodPictureService;
	@Resource
	private UserService userService;
	@Resource
	private GoodStyleService goodStyleService;
	@Resource
	private GoodSpuService goodSpuService;
	@Resource
	private GoodSkuService goodSkuService;
	@Resource
	private GoodImgService goodImgService;

	/**
	 * 加入备选
	 */
	@ApiOperation(value = "加入备选", httpMethod = "POST")
	@PostMapping("/add")
	public Result add(@RequestBody Alternative alternative,
					  @ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
					  HttpServletRequest request) {
		log.info("================================== 加入备选 ==================================");
		if (alternative == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}
		//		User user = (User)request.getSession().getAttribute("user");
		User user = userService.getById(userId);
		String userName = user.getAccount();//用户名
//		String userName ="aa";

		Map<String,Object> params = new HashMap<>();
		params.put("skuCode",alternative.getSkuCode());
		params.put("ownId",userId);
		Alternative exAlternative = alternativeService.getOneByParams(params);
		if(exAlternative !=null){
			exAlternative.setCheckedNum(exAlternative.getCheckedNum()+alternative.getCheckedNum());
			exAlternative.setModifytime(new Date());
			exAlternative.setModifier(userName);
			alternativeService.update(exAlternative);
		}else {
			Integer ownId = userId;
			alternative.setCreatetime(new Date());
			alternative.setModifytime(new Date());
			alternative.setCreater(userName);
			alternative.setModifier(userName);
			alternative.setOwnId(ownId);
			alternativeService.alternativeAdd(alternative);
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 点击已选样品
	 */
	@ApiOperation(value = "查看已选样品", httpMethod = "GET")
//	@ApiImplicitParam(paramType = "query",name = "ownId",dataType = "int" ,required = true,value = "所属用户id")
	@GetMapping("/list")
	public Result list(@ApiParam(value = "场地编码",required = true) @RequestParam(value = "placeNumber") String placeNumber,
					   @ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
					   HttpServletRequest request) {
		log.info("================================== 查看已选样品 ==================================");
		User user = userService.getById(userId);
		Integer ownId = userId;
		Map<String,Object> params = new HashMap<>();
		params.put("placeNumber",placeNumber);
		params.put("ownId",ownId);
		List<AlternativeVo> list = alternativeService.findByParams(params);
		List<AlternativeVo> successList = new ArrayList<>();
		List<AlternativeVo> failList = new ArrayList<>();
		Map<String,Object> resultMap = new HashMap<>();
		//判断是否缺货
		for (AlternativeVo alternativeVo : list) {
			if (alternativeVo.getCheckedNum() > alternativeVo.getUseNum()) {
				failList.add(alternativeVo);
			} else {
				successList.add(alternativeVo);
			}
		}
//		resultMap.put("successList",successList);
//		resultMap.put("failList",failList);
		//=================== 重封装返回 ======================

		List<Map<String, Object>> retListSuccess = new ArrayList<>();
		List<Map<String, Object>> retListFail = new ArrayList<>();

		for (AlternativeVo alternativeVo : successList) {
			Map<String, Object> retMap = newFormat(alternativeVo);
			retListSuccess.add(retMap);
		}
		for (AlternativeVo alternativeVo : failList) {
			Map<String, Object> retMap = newFormat(alternativeVo);
			retListFail.add(retMap);
		}
		resultMap.put("successList", retListSuccess);
		resultMap.put("failList", retListFail);

		return ResultGenerator.genSuccessResult(resultMap);
	}

	private Map<String,Object> newFormat(AlternativeVo alternativeVo){
		Map<String,Object> retMap = new HashMap<>();
		retMap.put("id",alternativeVo.getId());
		retMap.put("isSelect",false);
		retMap.put("goodCode",alternativeVo.getSkuCode());
		retMap.put("choseNum",alternativeVo.getCheckedNum());
		retMap.put("useNum",alternativeVo.getUseNum());
		GoodSku goodSku = goodSkuService.getBySku(alternativeVo.getSkuCode());
		GoodSpu goodSpu = goodSpuService.getBySpu(goodSku.getSpuCode());
		if (null != goodSpu.getStyleId()) {
			GoodStyle goodStyle = goodStyleService.getById(goodSpu.getStyleId());
			retMap.put("title",goodSpu.getSpuName()+goodStyle.getName());
//			retMap.put("title",goodVo.getGoodName()+goodVo.getStyleName());
		}else{
			retMap.put("title",goodSpu.getSpuName());
		}
		if(null != goodSku.getNormalPrice()){
			retMap.put("normalPrice",goodSku.getNormalPrice());
		}else{
			retMap.put("normalPrice","");
		}
		if(StringUtils.isNotBlank(goodSku.getGoodColor())){
			retMap.put("goodColor",goodSku.getGoodColor());
		}else{
			retMap.put("goodColor","");
		}
		if(StringUtils.isNotBlank(goodSku.getGoodSize())){
			retMap.put("goodSize",goodSku.getGoodSize());
		}else{
			retMap.put("goodSize","");
		}
		GoodImg img = goodImgService.getBySpu(goodSku.getSpuCode());
		if(img != null ) {
			retMap.put("picUrl", img.getImgUrl());
		}else{
			retMap.put("picUrl", "");
		}

		return retMap;
	}
	/**
	 * 已选样品编辑后批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "已选样品编辑后批量删除", httpMethod = "POST")
	@ApiImplicitParam(paramType = "query",name = "ids",dataType = "List" ,required = true,value = "选择的条目的id")
	@PostMapping("/delete")
	public Result delete(@RequestParam List<Integer> ids,
						 @ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId) {
		log.info("================================== 删除已选样品 ==================================");
//		JSONArray objects = JSONObject.parseArray(ids);
//		if (objects == null ||objects.size()==0) {
//			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
//		}
		alternativeService.deleteByOwnIds(ids);
		return ResultGenerator.genSuccessResult(ResultCode.SUCCESS);

	}
	@ApiOperation(value = "已选样品改变数量", httpMethod = "POST")
	@PostMapping("/update")
	public Result update(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
						 @ApiParam(value = "主键id",required = true) @RequestParam(value = "id") Integer id,
						 @ApiParam(value = "操作类型，点加号为1，减号为 0",required = true) @RequestParam(value = "type") Integer type){
		Alternative alternative = alternativeService.getById(id);
		if(type ==1 ) {
			alternative.setCheckedNum(alternative.getCheckedNum() + 1);
		}else{
			alternative.setCheckedNum(alternative.getCheckedNum() - 1);
		}
		alternativeService.update(alternative);
		return ResultGenerator.genSuccessResult();
	}
}
