package com.nice.miniprogram.api;
import com.nice.miniprogram.core.RedisService;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.StoreArea;
import com.nice.miniprogram.service.StoreAreaService;
import com.nice.miniprogram.utils.DateCutUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
* Created by CodeGenerator on 2018/07/18.
*/
//@Api(value = "展厅接口",description = "展厅信息相关操作")
//@RestController
//@RequestMapping("/storeArea")
public class StoreAreaController {

	private static Logger log = LoggerFactory.getLogger(StoreAreaController.class);

    @Resource
    private RedisService redisServicer;
    @Resource
    private StoreAreaService storeAreaService;

    @ApiOperation(value = "获取展厅列表", httpMethod = "GET")
    @GetMapping("/list")
    public Result list(@ApiParam(value = "场地编号", required = true) @RequestParam(value = "placeNumber" ) String placeNumber , HttpServletRequest request) {
        log.info("=============================== 获取展厅列表 ===============================");
        Map<String,Object> params = new HashMap<>();
        params.put("placeNumber",placeNumber);
        List<StoreArea> storeAreas = storeAreaService.selectByParams(params);
		return ResultGenerator.genSuccessResult(storeAreas);
 	}

    /*
       场地展示(根据时间段查看可选的展厅)
        */
    @ApiOperation(value = "展厅展示(根据时间段查看可选的展厅)", httpMethod = "GET")
    @GetMapping("/getListByTime")
    public Result getRoomList(@ApiParam(value = "开始时间") @RequestParam(value = "startTime",required = false) Date startTime,
                              @ApiParam(value = "结束时间") @RequestParam(value = "endTime",required = false) Date endTime, HttpServletRequest request) {
        log.info("================================== 根据时间段查看可选的展厅 ==================================");
        List<StoreArea> storeAreas = storeAreaService.selectList(null);
        List<StoreArea> choseStoreAreas = new ArrayList<>();
        List<StoreArea> unchoseStoreAreas = new ArrayList<>();
//        Map<String,Object> showRoom = new HashMap<>();
        int startDay = DateCutUtils.getDay(startTime);
        int endDay = DateCutUtils.getDay(endTime);
        int startHour = DateCutUtils.getHour(startTime);
        int endHour = DateCutUtils.getHour(endTime);
        if(startDay == endDay) {
            for (StoreArea storeArea : storeAreas) {
                Boolean flag = storeAreaService.checkIsChose(storeArea.getAreaId(),startDay,endDay,startHour,endHour);

                if(flag){  //true 为已被选
                    choseStoreAreas.add(storeArea);
                }else {     //false 为可选
                    unchoseStoreAreas.add(storeArea);
                }
            }
        }else if(startDay < endDay){
            for (StoreArea storeArea : storeAreas) {
                Set startHours = redisServicer.setMembers(storeArea.getAreaId() + "_" + startDay);
                Set endHours = redisServicer.setMembers(storeArea.getAreaId() + "_" + startDay);
                if(startHours.contains(startHour)||startHours.contains(endHour)|| endHours.contains(startHour)|| endHours.contains(endHour)){
                    choseStoreAreas.add(storeArea);
                }else{
                    unchoseStoreAreas.add(storeArea);
                }
            }
        }
//        showRoom.put("chose",choseRooms);
//        showRoom.put("unchose",unchoseRooms);
        return ResultGenerator.genSuccessResult(unchoseStoreAreas);
    }

    /*
   场地展示(根据展厅查看可选的时间)
    */
    @ApiOperation(value = "展厅展示(根据展厅查看可选的时间)", httpMethod = "GET")
    @GetMapping("/getListByRoomId")
    public Result getTimeList(@ApiParam(value = "展厅id") @RequestParam(value = "areaId",required = false) String areaId,
                              @ApiParam(value = "查看可选时间的日期") @RequestParam(value = "queryTime",required = false) Date queryTime, HttpServletRequest request) {
        log.info("================================== 根据展厅查看可选的时间 ==================================");
        int queryDay = DateCutUtils.getDay(queryTime);
        int currentDay = DateCutUtils.getDay(new Date());
        int currentHour = DateCutUtils.getHour(new Date());
        List unchose = new ArrayList(); //可选时间点；
        List chose = new ArrayList();  //已选时间点
        Set choseHours = redisServicer.setMembers(areaId + "_" + queryDay);
        int queryHour ;
        if(currentDay == queryDay) {
            queryHour = currentHour+1;
        }else{
            queryHour = 0;
        }
        for (int i = queryHour; i < 24; i++) {
            if (choseHours.contains(i)) {
                chose.add(i);
            } else {
                unchose.add(i);
            }
        }

        return ResultGenerator.genSuccessResult(unchose);
    }

    /*
    查看直播间明细
     */
//    @ApiOperation(value = "查看展厅明细", httpMethod = "GET")
//    @GetMapping("/getDetail")
//    public Result getDetail(@ApiParam(value = "展厅id") @RequestParam(value = "areaId",required = false) String areaId, HttpServletRequest request) {
//        log.info("================================== 查看展厅明细 ==================================");
//        StoreArea storeArea = storeAreaService.findById(areaId);
////        ShowPlaceInfo showPlaceInfo = showPlaceInfoService.getById(showRoomInfo.getShowPlaceId());
////        ShowRoomInfoVo showRoomInfoVo = new ShowRoomInfoVo();
////        BeanUtils.copyProperties(showRoomInfo,showRoomInfoVo);
//        return ResultGenerator.genSuccessResult(storeArea);
//    }

    /*
    获取直播间列表
     */
    @ApiOperation(value = "获取直播间列表", httpMethod = "GET")
    @GetMapping("/getList")
    public Result getList(@ApiParam(value = "场地编号") @RequestParam(value = "placeNumber",required = false) String placeNumber, HttpServletRequest request) {
        log.info("================================== 获取直播间列表 ==================================");
        Map<String,Object> params = new HashMap<>();
        params.put("placeNumber",placeNumber);
        List<StoreArea> storeAreas = storeAreaService.selectByParams(params);

        return ResultGenerator.genSuccessResult(storeAreas);
    }
}
