package com.nice.miniprogram.api;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.GoodImg;
import com.nice.miniprogram.service.GoodImgService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;


/**
* Created by CodeGenerator on 2018/08/07.
*/
@RestController
@RequestMapping("/good/img")
public class GoodImgController {

	private static Logger log = LoggerFactory.getLogger(GoodImgController.class);


    @Resource
    private GoodImgService goodImgService;

//    @PostMapping("/add")
//    public Result add(@RequestBody GoodImg goodImg,HttpServletRequest request) {
//
//
//		return ResultGenerator.genSuccessResult();
// 	}
}
