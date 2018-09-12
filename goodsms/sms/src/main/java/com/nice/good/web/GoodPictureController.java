package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.GoodPicture;
import com.nice.good.service.GoodPictureService;
import com.nice.good.utils.DelPicutre;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

;

/**
 * @Description:   货品档案-图片上传
 * @Author:   fqs
 * @Date:  2018/3/23 10:28
 * @Version:   1.0
 */
@RestController
@RequestMapping("/good/picture")
public class GoodPictureController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(GoodPictureController.class);


    @Resource
    private GoodPictureService goodPictureService;

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
          pictures = goodPictureService.goodPictureAdd(files,userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.UPLOAD_IS_FAIL);
		}

        return ResultGenerator.genSuccessResult().setMessage("图片上传成功").setData(pictures);
    }

	/**
	 *  -- 2018/05/26 11:45  rk
	 * @param imgIds
	 * @return
	 */
    @LogAnnotation(logType = "其他日志",content = "图片删除")
    @PostMapping("/delete")
    public Result delete(@RequestBody List<String> imgIds) {
    	if(imgIds==null || imgIds.size()==0){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

    	try {

    	    //删除数据库记录
            goodPictureService.deletePicture(imgIds);
            //删除服务器图片
            DelPicutre.deleteImg(imgIds);

		} catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody GoodPicture goodPicture,HttpServletRequest request) {
    	if(goodPicture == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);
    	try {

    		goodPictureService.goodPictureUpdate(goodPicture, userId);

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
    	if(id == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

    	GoodPicture goodPicture = null;
    	try {
    		goodPicture = goodPictureService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

        return ResultGenerator.genSuccessResult(goodPicture);
    }

    @PostMapping("/list")
    public Result list(@RequestBody GoodPicture goodPicture, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(goodPicture.getClass());
        Criteria criteria = condition.createCriteria();

		PageInfo pageInfo = null;
		try {
    		 List<GoodPicture> list = goodPictureService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
