package com.nice.miniprogram.api;
import com.nice.miniprogram.core.RedisService;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.enums.ResultCode;
import com.nice.miniprogram.model.ShowCount;
import com.nice.miniprogram.model.ShowRoomInfo;
import com.nice.miniprogram.service.GoodSkuService;
import com.nice.miniprogram.service.GoodSpuService;
import com.nice.miniprogram.service.ShowCountService;
import com.nice.miniprogram.service.ShowRoomInfoService;
import com.nice.miniprogram.utils.DateCutUtils;
import com.nice.miniprogram.utils.DateFormatUtil;
import com.nice.miniprogram.vo.GoodSkuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
* Created by CodeGenerator on 2018/06/12.
*/
@Api(value = "直播间接口",description = "直播间信息相关操作")
@RestController
@RequestMapping("/showRoomInfo")
public class ShowRoomInfoController {

	private static Logger log = LoggerFactory.getLogger(ShowRoomInfoController.class);

    @Resource
    private RedisService redisServicer;
    @Resource
    private ShowRoomInfoService showRoomInfoService;
    @Resource
    private ShowCountService showCountService;
    @Resource
    private GoodSpuService goodSpuService;
    @Resource
    private GoodSkuService goodSkuService;

    /*
    场地展示(根据时间段查看可选的直播间)
     */
    @ApiOperation(value = "直播间展示(根据时间段查看可选的直播间)", httpMethod = "GET")
    @GetMapping("/getListByTime")
    public Result getRoomList(@ApiParam(value = "开始时间") @RequestParam(value = "startTime",required = false) String startTime,
                              @ApiParam(value = "结束时间") @RequestParam(value = "endTime",required = false) String endTime, HttpServletRequest request) throws ParseException {
        log.info("================================== 根据时间段查询可选直播间 ==================================");
        List<ShowRoomInfo> rooms = showRoomInfoService.selectList(null);
        List<ShowRoomInfo> choseRooms = new ArrayList<>();
        List<ShowRoomInfo> unchoseRooms = new ArrayList<>();
//        Map<String,Object> showRoom = new HashMap<>();
        Date start = new Date();
        Date end = new Date();
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            start = DateFormatUtil.stringToDate(startTime);
            end = DateFormatUtil.stringToDate(endTime);

            //开始时间不能小于当前时间+3小时
            Calendar calendar1 =Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.HOUR_OF_DAY, 3);
            if((calendar1.getTime()).compareTo(start) >= 0){
                return ResultGenerator.genFailResult(ResultCode.CHOSE_TIME_ERROR);
            }
            //预约时间要最多提前3天
            Calendar calendar2 =Calendar.getInstance();
            calendar2.setTime(new Date());
            calendar2.add(Calendar.DAY_OF_MONTH, 3);
            if((calendar2.getTime()).compareTo(start) < 0){
                return ResultGenerator.genFailResult(ResultCode.BESPEAK_TIME_ERROR);
            }
            //开始时间不能大于结束时间
            if(start.compareTo(end) > 0){
                return ResultGenerator.genFailResult(ResultCode.CHOSE_TIME_ERROR);
            }
        }
        int startDay = DateCutUtils.getDay(start);
        int endDay = DateCutUtils.getDay(end);
        int startHour = DateCutUtils.getHour(start);
        int endHour = DateCutUtils.getHour(end);
        if(startDay == endDay) {
            for (ShowRoomInfo room : rooms) {
                Boolean flag = showRoomInfoService.checkIsChose(room.getId(),startDay,endDay,startHour,endHour);

                if(flag){  //true 为已被选
                    choseRooms.add(room);
                }else {     //false 为可选
                    unchoseRooms.add(room);
                }
            }
        }else if(startDay < endDay){
            for (ShowRoomInfo room : rooms) {
                Set startHours = redisServicer.setMembers(room.getId() + "_" + startDay);
                Set endHours = redisServicer.setMembers(room.getId() + "_" + startDay);
                if(startHours.contains(startHour)||startHours.contains(endHour)|| endHours.contains(startHour)|| endHours.contains(endHour)){
                    choseRooms.add(room);
                }else{
                    unchoseRooms.add(room);
                }
            }
        }

//        showRoom.put("chose",choseRooms);
//        showRoom.put("unchose",unchoseRooms);
        /*=============================== 重封装返回 ===============================*/
        List<Map<String,Object>> retResult = new ArrayList<>();
        Map<String,Object> retMap = null;
        for(ShowRoomInfo showRoomInfo: unchoseRooms){
            retMap = formatRetMap(showRoomInfo);
            retResult.add(retMap);
        }
		return ResultGenerator.genSuccessResult(retResult);
 	}

    /*
   场地展示(根据直播间查看可选的时间)
    */
    @ApiOperation(value = "直播间展示(根据直播间查看可选的时间)", httpMethod = "GET")
    @GetMapping("/getListByRoomId")
    public Result getTimeList(@ApiParam(value = "直播间id",required = true) @RequestParam(value = "id") Integer id,
                              @ApiParam(value = "查看可选时间的日期1" ,required = true) @RequestParam(value = "queryTime1") String queryTime1,
                              @ApiParam(value = "查看可选时间的日期2") @RequestParam(value = "queryTime2",required = false) String queryTime2,HttpServletRequest request) throws ParseException {
        log.info("================================== 根据直播间查询可用时间 ==================================");


        List<String> choseTimes = new ArrayList();  //已选时间段集合

        if(queryTime1 != null && !queryTime1.equals(queryTime2)) {
            List<String> choseTimes1 = getTimeList(id,queryTime1);
            List<String> choseTimes2 = getTimeList(id,queryTime2);
            choseTimes.addAll(choseTimes1);
            choseTimes.addAll(choseTimes2);

        }else{
            List<String> choseTimes1 = getTimeList(id,queryTime1);
            choseTimes.addAll(choseTimes1);
        }

        ShowRoomInfo showRoomInfo = showRoomInfoService.getById(id);
        Map<String,Object> retMap = formatRetMap(showRoomInfo);
        Map<String,Object> params = new HashMap<>();
        params.put("seatCode",showRoomInfo.getSeatCode());
        List<GoodSkuVo> goodVos = goodSkuService.getGoodList(params);
        if(null!=goodVos && goodVos.size()>0) {
            retMap.put("good", true);
        }else{
            retMap.put("good", false);
        }
        retMap.put("choseTimes", choseTimes);

        return ResultGenerator.genSuccessResult(retMap);
    }

    public List<String> getTimeList(Integer id ,String queryTime) throws ParseException {
        Date queryDate = new Date();
        if(queryTime != null){
            queryDate = new SimpleDateFormat("yyyy-MM-dd").parse(queryTime);
        }
        int queryDay = DateCutUtils.getDay(queryDate);
        int currentDay = DateCutUtils.getDay(new Date());
        int currentHour = DateCutUtils.getHour(new Date())+ 4; //
        List unchose = new ArrayList(); //可选时间点；
        List chose = new ArrayList();  //已选时间点
        Set choseHours = redisServicer.setMembers(id + "_" + queryDay);
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
        /*============================ 重封装返回值 ==============================*/
        // ============================ 已选时间段集合 =============================
        List<String> choseTimes = new ArrayList();  //已选时间段集合
        String choseTime = "";    //已选时间段
        List continuityList = new ArrayList();
        for(int i = 0; i < chose.size(); i++){
            if(i==0 || chose.contains((int)chose.get(i)-1)){
                continuityList.add(chose.get(i));
            }else{
                choseTime = queryTime + " "+ continuityList.get(0)+":00:00-"+continuityList.get(continuityList.size()-1)+":59:59";
                choseTimes.add(choseTime);
                continuityList = new ArrayList();
                continuityList.add(chose.get(i));
            }
        }
        if(continuityList!=null && continuityList.size()>0){
            choseTime = queryTime + " "+ continuityList.get(0)+":00:00-"+continuityList.get(continuityList.size()-1)+":59:59";
            choseTimes.add(choseTime);
        }

        return choseTimes;
//        return ResultGenerator.genSuccessResult(retMap);
    }

    /*
    查看直播间明细
     */
    @ApiOperation(value = "查看直播间明细", httpMethod = "GET")
    @GetMapping("/getDetail")
    public Result getDetail(@ApiParam(value = "直播间id") @RequestParam(value = "id",required = false) Integer id, HttpServletRequest request) {
        log.info("================================== 查看直播间明细 ==================================");
        ShowRoomInfo showRoomInfo = showRoomInfoService.getById(id);
//        ShowPlaceInfo showPlaceInfo = showPlaceInfoService.getById(showRoomInfo.getShowPlaceId());
//        ShowRoomInfoVo showRoomInfoVo = new ShowRoomInfoVo();
//        BeanUtils.copyProperties(showRoomInfo,showRoomInfoVo);
        /* ======================= 直播间点击量计数 ======================== */
        ShowCount showCount = showCountService.getByShowRoomId(showRoomInfo.getId());
        if(showCount == null){
            showCount = new ShowCount();
            showCount.setBespeakNum(0);
            showCount.setClickNum(1);
            showCount.setShowRoomId(showRoomInfo.getId());
            showCount.setType(0);
            showCountService.newCountRecord(showCount);
        }else{
            showCountService.addClickNum(showCount.getId());
        }
        /* ======================= 直播间点击量计数 end ======================== */
        Map<String,Object> retMap = formatRetMap(showRoomInfo);

        Map<String,Object> params = new HashMap<>();
        params.put("seatCode",showRoomInfo.getSeatCode());
        List<GoodSkuVo> goodVos = goodSkuService.getGoodList(params);
        if(null!=goodVos && goodVos.size()>0) {
            retMap.put("good", true);
        }else{
            retMap.put("good", false);
        }

        return ResultGenerator.genSuccessResult(retMap);
    }

    /*
    获取直播间列表
     */
    @ApiOperation(value = "获取直播间列表", httpMethod = "GET")
    @GetMapping("/getList")
    public Result getList(@ApiParam(value = "场地编号") @RequestParam(value = "placeNumber",required = false) String placeNumber, HttpServletRequest request) {
        log.info("================================== 获取直播间列表 ==================================");
        Map<String,Object> params = new HashMap<>();
        params.put("placeNumber",placeNumber);
        List<ShowRoomInfo> showRooms = showRoomInfoService.getListByParams(params);

        List<Map<String,Object>> retResult = new ArrayList<>();
        Map<String,Object> retMap = null;
        for(ShowRoomInfo showRoomInfo:showRooms){
            retMap = formatRetMap(showRoomInfo);
            retResult.add(retMap);
        }
        return ResultGenerator.genSuccessResult(retResult);
    }

    private Map<String,Object> formatRetMap(ShowRoomInfo showRoomInfo){
        Map<String,Object> retMap = new HashMap<>();
        ShowCount showCount = showCountService.getByShowRoomId(showRoomInfo.getId());
        retMap.put("showRoomId",showRoomInfo.getId());
        retMap.put("code",showRoomInfo.getCode());
        retMap.put("name",showRoomInfo.getName());
        retMap.put("style",showRoomInfo.getStyle());
        retMap.put("area",showRoomInfo.getArea());
        retMap.put("equipment",showRoomInfo.getEquipment());
        if(showCount!=null && showCount.getClickNum() != null){
            retMap.put("clickNum",showCount.getClickNum());
        }else{
            retMap.put("clickNum",0);
        }
        if(showCount!=null && showCount.getBespeakNum() != null) {
            retMap.put("bespeakNum", showCount.getBespeakNum());
        }else{
            retMap.put("bespeakNum",0);
        }
        retMap.put("seatCode",showRoomInfo.getSeatCode());
        retMap.put("picture",showRoomInfo.getPicture());
        return retMap;
    }
}
