package com.nice.miniprogram.api;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.SysPlace;
import com.nice.miniprogram.service.ModuleRegionService;
import com.nice.miniprogram.service.SysPlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
* Created by CodeGenerator on 2018/07/18.
*/
@Api(value = "场地接口",description = "场地信息相关操作")
@RestController
@RequestMapping("/sysPlace")
public class SysPlaceController {

	private static Logger log = LoggerFactory.getLogger(SysPlaceController.class);

    @Resource
    private SysPlaceService sysPlaceService;
    @Resource
    private ModuleRegionService moduleRegionService;

    @ApiOperation(value = "获取场地列表", httpMethod = "GET")
    @GetMapping("/list")
    public Result list(HttpServletRequest request) {
        log.info("============================== 获取场地列表 ============================");
        List<SysPlace> places = sysPlaceService.getList();
        List<Map<String,Object>> retResult = new ArrayList<>();
        Map<String,Object> retMap = null;
        for(SysPlace sysPlace:places){
            retMap = new HashMap<>();
            String provinceName = moduleRegionService.getCnNameById(sysPlace.getProvince());
            String cityName = moduleRegionService.getCnNameById(sysPlace.getCity());
            String districtName = moduleRegionService.getCnNameById(sysPlace.getDistrict());
            retMap.put("placeNumber",sysPlace.getPlaceNumber());
            retMap.put("placeName",sysPlace.getExhibition());
            retMap.put("placeAddress",provinceName+cityName+districtName+sysPlace.getAddress());
            retMap.put("selected",false);
//            retMap.put("placeId",sysPlace.getPlaceId());
            retResult.add(retMap);
        }
		return ResultGenerator.genSuccessResult(retResult);
 	}
}
