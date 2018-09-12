package com.nice.miniprogram.api;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.GoodStyle;
import com.nice.miniprogram.service.GoodStyleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.ArrayStack;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* Created by CodeGenerator on 2018/07/20.
*/
@Api(value = "风格接口",description = "风格信息相关操作")
@RestController
@RequestMapping("/good/style")
public class GoodStyleController {

	private static Logger log = LoggerFactory.getLogger(GoodStyleController.class);


    @Resource
    private GoodStyleService goodStyleService;


    @ApiOperation(value = "风格列表展示", httpMethod = "GET")
    @GetMapping("/list")
    public Result list(HttpServletRequest request) {
        List<GoodStyle> styles = goodStyleService.selectList(null);
        List<Map<String,Object>> retList = new ArrayList<>();
        for(GoodStyle goodStyle :styles){
            Map<String,Object> retMap = new HashMap<>();
            retMap.put("styleId",goodStyle.getId());
            retMap.put("styleName",goodStyle.getName());
            retList.add(retMap);
        }
		return ResultGenerator.genSuccessResult(retList);
 	}
}
