package com.nice.good.wx_web;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.utils.DelPicutre;
import com.nice.good.wx_model.RoomPicture;
import com.nice.good.service.RoomPictureService;

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

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;

import javax.annotation.Resource;
import java.util.List;
import org.apache.commons.lang3.StringUtils;;

/**
* Created by CodeGenerator on 2018/08/27.
*/
@RestController
@RequestMapping("/room/picture")
public class RoomPictureController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(RoomPictureController.class);


    @Resource
    private RoomPictureService roomPictureService;

	@LogAnnotation(logType = "其他日志",content = "图片上传")
    @PostMapping("/add")
    public Result add(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		List<MultipartFile> filesNew = multipartRequest.getFiles("file");


		if(filesNew == null ||filesNew.size()==0){
			return ResultGenerator.genFailResult(ResultCode.PICTURE_IS_NULL);
		}

		MultipartFile[] files = new MultipartFile[filesNew.size()];
		for(int i=0;i<filesNew.size();i++){
			files[i] = filesNew.get(i);
		}


		String userId = getUserName(request);

		List<String> pictures;
		try {
			pictures = roomPictureService.roomPictureAdd(files,userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.UPLOAD_IS_FAIL);
		}

		return ResultGenerator.genSuccessResult().setMessage("图片上传成功").setData(pictures);
	}

	@LogAnnotation(logType = "其他日志",content = "图片删除")
    @PostMapping("/delete")
    public Result delete(@RequestBody List<String> imgIds) {
		if(imgIds==null || imgIds.size()==0){
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}
    	try {
		    //删除数据库记录
    		roomPictureService.deletePicture(imgIds);
    		//删除服务器图片
            DelPicutre.deleteImg(imgIds);


        } catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody RoomPicture roomPicture,HttpServletRequest request) {
    	if(roomPicture == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);

    	if(roomPicture.getId() == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}
    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		roomPictureService.roomPictureUpdate(roomPicture,userId);

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id,HttpServletRequest request) {
    	if(id == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

        String userId = getUserName(request);


    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	RoomPicture roomPicture = null;
    	try {
    		roomPicture = roomPictureService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult(roomPicture);
    }

    @PostMapping("/list")
    public Result list(@RequestBody RoomPicture roomPicture, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(roomPicture.getClass());
        Criteria criteria = condition.createCriteria();

		PageInfo pageInfo = null;
		try {
    		 List<RoomPicture> list = roomPictureService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
