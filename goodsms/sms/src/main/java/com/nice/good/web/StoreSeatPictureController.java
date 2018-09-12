package com.nice.good.web;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.StoreSeatPicture;
import com.nice.good.service.StoreSeatPictureService;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.utils.DelPicutre;
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
* Created by CodeGenerator on 2018/08/13.
*/
@RestController
@RequestMapping("/store/seat/picture")
public class StoreSeatPictureController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(StoreSeatPictureController.class);


    @Resource
    private StoreSeatPictureService storeSeatPictureService;

    @LogAnnotation(logType = "其他日志",content = "图片上传")
    @PostMapping("/add")
    public Result add( HttpServletRequest request) {


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
            pictures = storeSeatPictureService.goodPictureAdd(files,userId);

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
            storeSeatPictureService.deletePicture(imgIds);
            //删除服务器图片
            DelPicutre.deleteImg(imgIds);

        } catch (Exception e) {
            log.error("删除对象操作异常e:{}",e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/list")
    public Result list(@RequestBody StoreSeatPicture storeSeatPicture, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(storeSeatPicture.getClass());
        Criteria criteria = condition.createCriteria();

		PageInfo pageInfo = null;
		try {
    		 List<StoreSeatPicture> list = storeSeatPictureService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
