package com.nice.miniprogram.api;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.Exhibition;
import com.nice.miniprogram.service.ExhibitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nice.miniprogram.enums.ResultCode.INTERNAL_SERVER_ERROR;


/**
* Created by CodeGenerator on 2018/08/14.
*/
@Api(value = "展厅接口",description = "展厅信息相关操作")
@RestController
@RequestMapping("/exhibition")
public class ExhibitionController {

	private static Logger log = LoggerFactory.getLogger(ExhibitionController.class);


    @Resource
    private ExhibitionService exhibitionService;

    @ApiOperation(value = "获取展厅列表", httpMethod = "GET")
    @GetMapping("/getList")
    public Result list(@ApiParam(value = "场地编号", required = true) @RequestParam(value = "placeNumber" ) String placeNumber , HttpServletRequest request) {
        Map<String,Object> params = new HashMap<>();
        params.put("placeNumber",placeNumber);
        List<Exhibition> exhibitions = exhibitionService.getListByParams(params);
        List<Map<String,Object>> retList = new ArrayList<>();
        for(Exhibition exhibition:exhibitions) {
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("id", exhibition.getId());
            retMap.put("name", exhibition.getName());
            retMap.put("code", exhibition.getCode());
            retMap.put("areaCode", exhibition.getAreaCode());
            retMap.put("placeNumber", exhibition.getPlaceNumber());
            retMap.put("remark", exhibition.getRemark());
            retList.add(retMap);
        }
        return ResultGenerator.genSuccessResult(retList);
    }
}
