package com.nice.miniprogram.api;
import com.nice.miniprogram.core.RedisService;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.Exhibition;
import com.nice.miniprogram.model.ExhibitionRoom;
import com.nice.miniprogram.model.ShowCount;
import com.nice.miniprogram.service.*;
import com.nice.miniprogram.utils.DateCutUtils;
import com.nice.miniprogram.vo.GoodSkuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
* Created by CodeGenerator on 2018/07/27.
*/
@Api(value = "展厅接口",description = "展厅信息相关操作")
@RestController
@RequestMapping("/exhibitionRoom")
public class ExhibitionRoomController {

	private static Logger log = LoggerFactory.getLogger(ExhibitionRoomController.class);


    @Resource
    private ExhibitionRoomService exhibitionRoomService;
    @Resource
    private ExhibitionService exhibitionService;
//    @Resource
//    private StoreSeatService storeSeatService;
    @Resource
    private RedisService redisServicer;
    @Resource
    private ShowCountService showCountService;
    @Resource
    private GoodSpuService goodSpuService;
    @Resource
    private GoodSkuService goodSkuService;
//    @Resource
//    private GoodService goodService;

    @ApiOperation(value = "获取展厅中直播位的列表", httpMethod = "GET")
    @GetMapping("/getList")
    public Result list(@ApiParam(value = "场地编号", required = true) @RequestParam(value = "placeNumber" ) String placeNumber , HttpServletRequest request) {
        log.info("=============================== 获取展厅列表 ===============================");
        log.info("================================== 获取展厅列表 ==================================");
        List<ExhibitionRoom> exhibitionRooms = exhibitionRoomService.selectByPlaceNumber(placeNumber);

        List<Map<String,Object>> retResult = new ArrayList<>();
        Map<String,Object> retMap = null;
        for(ExhibitionRoom exhibitionRoom:exhibitionRooms){
            retMap = formatRetMap(exhibitionRoom);
            retResult.add(retMap);
        }
        return ResultGenerator.genSuccessResult(retResult);
    }

    /*
       场地展示(根据时间段查看可选的展厅)
        */
    @ApiOperation(value = "展厅展示(根据时间段查看可选的展厅)", httpMethod = "GET")
    @GetMapping("/getListByTime")
    public Result getRoomList(@ApiParam(value = "开始时间") @RequestParam(value = "startTime",required = false) Date startTime,
                              @ApiParam(value = "结束时间") @RequestParam(value = "endTime",required = false) Date endTime, HttpServletRequest request) {
        log.info("================================== 根据时间段查看可选的展厅 ==================================");
        List<ExhibitionRoom> exhibitionRooms = exhibitionRoomService.selectList(null);
        List<ExhibitionRoom> choseExhibitionRooms = new ArrayList<>();
        List<ExhibitionRoom> unchoseExhibitionRooms = new ArrayList<>();
//        Map<String,Object> showRoom = new HashMap<>();
        int startDay = DateCutUtils.getDay(startTime);
        int endDay = DateCutUtils.getDay(endTime);
        int startHour = DateCutUtils.getHour(startTime);
        int endHour = DateCutUtils.getHour(endTime);
        if(startDay == endDay) {
            for (ExhibitionRoom exhibitionRoom : exhibitionRooms) {
                Boolean flag = exhibitionRoomService.checkIsChose(exhibitionRoom.getId(),startDay,endDay,startHour,endHour);
                if(flag){  //true 为已被选
                    choseExhibitionRooms.add(exhibitionRoom);
                }else {     //false 为可选
                    unchoseExhibitionRooms.add(exhibitionRoom);
                }
            }
        }else if(startDay < endDay){
            for (ExhibitionRoom exhibitionRoom : exhibitionRooms) {
                Set startHours = redisServicer.setMembers(exhibitionRoom.getId() + "_" + startDay);
                Set endHours = redisServicer.setMembers(exhibitionRoom.getId() + "_" + startDay);
                if(startHours.contains(startHour)||startHours.contains(endHour)|| endHours.contains(startHour)|| endHours.contains(endHour)){
                    choseExhibitionRooms.add(exhibitionRoom);
                }else{
                    unchoseExhibitionRooms.add(exhibitionRoom);
                }
            }
        }
//        showRoom.put("chose",choseRooms);
//        showRoom.put("unchose",unchoseRooms);
        return ResultGenerator.genSuccessResult(unchoseExhibitionRooms);
    }

    /*
   场地展示(根据展厅查看可选的时间)
    */
    @ApiOperation(value = "展厅展示(根据展厅查看可选的时间)", httpMethod = "GET")
    @GetMapping("/getListByRoomId")
    public Result getTimeList(@ApiParam(value = "展厅id") @RequestParam(value = "exhibitionRoomId",required = false) Integer exhibitionRoomId,
                              @ApiParam(value = "查看可选时间的日期") @RequestParam(value = "queryTime",required = false) String queryTime, HttpServletRequest request) throws ParseException {
        log.info("================================== 根据展厅查看可选的时间 ==================================");
        Date queryDate = new Date();
        if(queryTime != null){
            queryDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(queryTime);
        }
        int queryDay = DateCutUtils.getDay(queryDate);
        int currentDay = DateCutUtils.getDay(new Date());
        int currentHour = DateCutUtils.getHour(new Date());
        List unchose = new ArrayList(); //可选时间点；
        List chose = new ArrayList();  //已选时间点
        Set choseHours = redisServicer.setMembers(exhibitionRoomId + "_" + queryDay);
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
        List<String> choseTimes = new ArrayList();  //已选时间段集合
        String choseTime = "";    //已选时间段
        List continuityList = new ArrayList();
        for(int i = 0; i < chose.size(); i++){
            if(i==0 || chose.contains((int)chose.get(i)-1)){
                continuityList.add(chose.get(i));
            }else{
                choseTime = continuityList.get(0)+":00:00-"+continuityList.get(continuityList.size()-1)+":00:00";
                choseTimes.add(choseTime);
                continuityList = new ArrayList();
                continuityList.add(chose.get(i));
            }
        }
        ExhibitionRoom exhibitionRoom = exhibitionRoomService.getById(exhibitionRoomId);
        Map<String,Object> retMap = formatRetMap(exhibitionRoom);
        Map<String,Object> params = new HashMap<>();
        params.put("seatCode",exhibitionRoom.getSeatCode());
        List<GoodSkuVo> goodVos = goodSkuService.getGoodList(params);
        if(null!=goodVos && goodVos.size()>0) {
            retMap.put("good", true);
        }else{
            retMap.put("good", false);
        }

        return ResultGenerator.genSuccessResult(choseTimes);
    }
    

    private Map<String,Object> formatRetMap(ExhibitionRoom exhibitionRoom){
        Map<String,Object> retMap = new HashMap<>();
        ShowCount showCount = showCountService.getByShowRoomId(exhibitionRoom.getId());
        Exhibition exhibition = exhibitionService.getById(exhibitionRoom.getExhibitionId());
        retMap.put("exhibitionRoomId",exhibitionRoom.getId());
        retMap.put("code",exhibition.getCode());
        retMap.put("name",exhibition.getName());
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
        retMap.put("seatCode",exhibitionRoom.getSeatCode());
        return retMap;
    }

    /*
   查看展厅明细
    */
    @ApiOperation(value = "查看展厅明细", httpMethod = "GET")
    @GetMapping("/getDetail")
    public Result getDetail(@ApiParam(value = "展厅id") @RequestParam(value = "id",required = false) Integer id, HttpServletRequest request) {
        log.info("================================== 查看展厅明细 ==================================");
        ExhibitionRoom exhibitionRoom = exhibitionRoomService.getById(id);
        /* ======================= 展厅点击量计数 ======================== */
        ShowCount showCount = showCountService.getByShowRoomId(exhibitionRoom.getId());
        if(showCount == null){
            showCount = new ShowCount();
            showCount.setBespeakNum(0);
            showCount.setClickNum(1);
            showCount.setExhibitionRoomId(exhibitionRoom.getId());
            showCount.setType(1);
            showCountService.newCountRecord(showCount);
        }else{
            showCountService.addClickNum(showCount.getId());
        }
        /* ======================= 展厅点击量计数 end ======================== */
        Map<String,Object> retMap = formatRetMap(exhibitionRoom);
        Map<String,Object> params = new HashMap<>();
        params.put("seatCode",exhibitionRoom.getSeatCode());
        List<GoodSkuVo> goodVos = goodSkuService.getGoodList(params);

//        /* ============================ 重封装返回值 end============================ */
        if(null!=goodVos && goodVos.size()>0) {
            retMap.put("good", true);
        }else{
            retMap.put("good", false);
        }
        return ResultGenerator.genSuccessResult(retMap);
    }
}
