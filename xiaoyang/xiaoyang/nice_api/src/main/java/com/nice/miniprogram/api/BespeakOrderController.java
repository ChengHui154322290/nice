package com.nice.miniprogram.api;
import com.nice.miniprogram.core.RedisService;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.enums.ResultCode;
import com.nice.miniprogram.model.*;
import com.nice.miniprogram.service.*;
import com.nice.miniprogram.utils.CodeFormatUtil;
import com.nice.miniprogram.utils.DateCutUtils;
import com.nice.miniprogram.utils.DateFormatUtil;
import com.nice.miniprogram.vo.BespeakOrderVo;
import com.nice.miniprogram.vo.GoodSkuVo;
import com.nice.miniprogram.vo.OrderDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;


/**
* Created by CodeGenerator on 2018/06/13.
*/
@Api(value = "预约单接口",description = "预约单相关操作")
@RestController
@RequestMapping("/bespeakOrder")
public class BespeakOrderController {

	private static Logger log = LoggerFactory.getLogger(BespeakOrderController.class);

    @Resource
    private BespeakOrderService bespeakOrderService;
    @Resource
    private ShowRoomInfoService showRoomInfoService;
    @Resource
    private RedisService redisServicer;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private StoreSeatService storeSeatService;
    @Resource
    private ExhibitionRoomService exhibitionRoomService;
    @Resource
    private ExhibitionService exhibitionService;
    @Resource
    private ShowCountService showCountService;
    @Resource
    private GoodSpuService goodSpuService;
    @Resource
    private GoodSkuService goodSkuService;
    @Resource
    private SysPlaceService sysPlaceService;
    @Resource
    private GoodStyleService goodStyleService;
    @Resource
    private SeatStockService seatStockService;
    @Resource
    private UserService userService;
    @Resource
    private GoodImgService goodImgService;
    /*
    新增预约单
     */
    @ApiOperation(value = "新增预约单", httpMethod = "POST")
    @PostMapping("/add")
    @Transactional
    public Result add(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                      @RequestBody BespeakOrderVo bespeakOrderVo,
//                      @ApiParam(value = "场地编码") @RequestParam(value = "placeNumber",required = false) String placeNumber,
                      HttpServletRequest request) throws ParseException {
        log.info("==================================新增预约单==================================");

        if(StringUtils.isNotBlank(bespeakOrderVo.getBespeakTimeStartStr())
                && StringUtils.isNotBlank(bespeakOrderVo.getBespeakTimeEndStr())){
            bespeakOrderVo.setBespeakTimeStart(DateFormatUtil.stringToDate(bespeakOrderVo.getBespeakTimeStartStr()));
            bespeakOrderVo.setBespeakTimeEnd(DateFormatUtil.stringToDate(bespeakOrderVo.getBespeakTimeEndStr()));

            //开始时间不能小于当前时间+3小时
            Calendar calendar1 =Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.HOUR_OF_DAY, 3);
            if((calendar1.getTime()).compareTo(bespeakOrderVo.getBespeakTimeStart()) >= 0){
                return ResultGenerator.genFailResult(ResultCode.CHOSE_TIME_ERROR);
            }
            //预约时间要最多提前3天
            Calendar calendar2 =Calendar.getInstance();
            calendar2.setTime(new Date());
            calendar2.add(Calendar.DAY_OF_MONTH, 3);
            if((calendar2.getTime()).compareTo(bespeakOrderVo.getBespeakTimeStart()) < 0){
                return ResultGenerator.genFailResult(ResultCode.BESPEAK_TIME_ERROR);
            }
            //开始时间不能大于结束时间
            if((bespeakOrderVo.getBespeakTimeStart()).compareTo(bespeakOrderVo.getBespeakTimeEnd()) > 0){
                return ResultGenerator.genFailResult(ResultCode.CHOSE_TIME_ERROR);
            }
            //结束时间最多跨一天
            Calendar calendar3 =Calendar.getInstance();
            calendar3.setTime(bespeakOrderVo.getBespeakTimeStart());
            calendar3.add(Calendar.DAY_OF_MONTH, 1);
            if((calendar3.getTime()).compareTo(bespeakOrderVo.getBespeakTimeEnd()) < 0){
                return ResultGenerator.genFailResult(ResultCode.BESPEAK_TIME_ERROR_NEW);
            }
        }
        int startDay = DateCutUtils.getDay(bespeakOrderVo.getBespeakTimeStart()); //预约开始的日期
        int endDay = DateCutUtils.getDay(bespeakOrderVo.getBespeakTimeEnd()); //预约结束的日期
        int startHour = DateCutUtils.getHour(bespeakOrderVo.getBespeakTimeStart()); //预约开始的小时
        int endHour = DateCutUtils.getHour(bespeakOrderVo.getBespeakTimeEnd());     //预约结束的小时
        Boolean flag = showRoomInfoService.checkIsChose(bespeakOrderVo.getShowRoomId(),startDay,endDay,startHour,endHour);
        if(flag){  //true 为已被选
            return ResultGenerator.genFailResult("所选择的直播间已被选");
        }
        User user = userService.getById(userId);
        String userName = user.getAccount();//用户名
//        String userName ="aa";
        Date current = new Date();
        BespeakOrder bespeakOrder = new BespeakOrder();
        BeanUtils.copyProperties(bespeakOrderVo,bespeakOrder);
        Integer id = bespeakOrderService.selectMaxId();
        if(id==null||"".equals(id)){
            id = 1;
        }else{
            id++;
        }
        String code = CodeFormatUtil.formatCode("PQ",id);
        log.info("生成的订单编号："+ code);
        bespeakOrder.setOwnId(userId);
        bespeakOrder.setStatus(0);
        bespeakOrder.setOrderCode(code);
        bespeakOrder.setCreater(userName);
        bespeakOrder.setCreatetime(current);
        bespeakOrder.setModifier(userName);
        bespeakOrder.setModifytime(current);
        bespeakOrderService.save(bespeakOrder);
        //预约的直播间id+日期作为key,小时数为value
        //写入缓存
        if (startDay == endDay){
            for(int i=startHour;i<=endHour;i++){
                redisServicer.add(bespeakOrder.getShowRoomId()+"_"+startDay,i);
            }
        }else if(startDay < endDay){
            for(int i=startHour;i<24;i++){
                redisServicer.add(bespeakOrder.getShowRoomId()+"_"+startDay,i);
            }
            for (int i=0;i<endHour;i++){
                redisServicer.add(bespeakOrder.getShowRoomId()+"_"+endDay,i);
            }
        }
        String placeNumber = null;
        if(bespeakOrder.getShowRoomId() != null) {
            placeNumber = showRoomInfoService.queryPlaceNumber(bespeakOrder.getShowRoomId());
        }else if(null != bespeakOrder.getExhibitionRoomId()){
            placeNumber = exhibitionRoomService.queryPlaceNumber(bespeakOrder.getExhibitionRoomId());
        }
        /*========================== 新增预约单时添加明细(默认预约直播间下的所有样品） ============================*/
        String seatCode = null;
        if(bespeakOrderVo.getShowRoomId() != null ) {
            ShowRoomInfo showRoomInfo = showRoomInfoService.getById(bespeakOrder.getShowRoomId());
            seatCode = showRoomInfo.getSeatCode();
        }else if(null != bespeakOrder.getExhibitionRoomId()){
            ExhibitionRoom exhibitionRoom = exhibitionRoomService.getById(bespeakOrder.getExhibitionRoomId());
            seatCode = exhibitionRoom.getSeatCode();
        }
//          ShowRoomInfo showRoomInfo = showRoomInfoService.getById(bespeakOrder.getShowRoomId());
        Map<String,Object> params = new HashMap<>();
        List<GoodSkuVo> goodSkuVos = null;
        if(StringUtils.isNotBlank(seatCode)){
            params.put("seatCode",seatCode);
            goodSkuVos = goodSkuService.getGoodList(params);
        }
        OrderDetail orderDetail = null;
        List<OrderDetail> details = new ArrayList<>();
        for(GoodSkuVo goodSkuVo : goodSkuVos){
            orderDetail = new OrderDetail();
            orderDetail.setOrderType(1);
            orderDetail.setOrderId(bespeakOrder.getId());
            orderDetail.setStatus(6);
            orderDetail.setChoseNum(goodSkuVo.getUseNum());
            orderDetail.setSkuCode(goodSkuVo.getSkuCode());
            orderDetail.setRemark("");
            orderDetail.setCreater(userName);
            orderDetail.setCreatetime(current);
            orderDetail.setModifier(userName);
            orderDetail.setModifytime(current);
            orderDetail.setSourceSeatCode(seatCode);
            orderDetail.setTargetSeatCode(seatCode);
            details.add(orderDetail);
        }

//        for(OrderDetailVo orderDetailVo:bespeakOrderVo.getOrderDetailVoList()){
//            orderDetail = new OrderDetail();
//            orderDetail.setOrderType(1);
//            orderDetail.setOrderId(bespeakOrder.getId());
//            orderDetail.setStatus(6);
//            orderDetail.setChoseNum(orderDetailVo.getChoseNum());
//            orderDetail.setSkuCode(orderDetailVo.getSkuCode());
//            orderDetail.setRemark("");
//            orderDetail.setCreater(userName);
//            orderDetail.setCreatetime(current);
//            orderDetail.setModifier(userName);
//            orderDetail.setModifytime(current);
//            orderDetail.setSourceSeatCode(seatCode);
//            orderDetail.setTargetSeatCode(seatCode);
//            details.add(orderDetail);
//        }
        if(null != details && details.size()>0) {
//            details = orderDetailService.freezeStock(details, placeNumber);
            orderDetailService.batchInsert(details);
        }
        /*========================== 新增预约单时添加明细(默认预约直播间下的所有样品） end ============================*/
        /*====================== 预约量计数 ======================*/
        if(bespeakOrder.getShowRoomId() != null) {
            ShowCount showCount = showCountService.getByShowRoomId(bespeakOrder.getShowRoomId());
            if(showCount == null){
                showCount = new ShowCount();
                showCount.setBespeakNum(1);
                showCount.setClickNum(1);
                showCount.setShowRoomId(bespeakOrder.getShowRoomId());
                showCount.setType(0);
                showCountService.newCountRecord(showCount);
            }else{
                showCountService.addBespeakNum(showCount.getId());
            }
        }else if(null != bespeakOrder.getExhibitionRoomId()){
            ShowCount showCount = showCountService.getByExhibitionRoomId(bespeakOrder.getExhibitionRoomId());//
            if(showCount == null){
                showCount = new ShowCount();
                showCount.setBespeakNum(1);
                showCount.setClickNum(1);
                showCount.setExhibitionRoomId(bespeakOrder.getExhibitionRoomId());
                showCount.setType(1);
                showCountService.newCountRecord(showCount);
            }else{
                showCountService.addBespeakNum(showCount.getId());
            }
        }
        /*====================== 预约量计数 end ======================*/

		return ResultGenerator.genSuccessResult(bespeakOrder.getId());
 	}

 	/*
 	修改预约单状态(取消)
 	 */
    @ApiOperation(value = "取消预约单", httpMethod = "POST")
    @PostMapping("/cancel")
    @Transactional
    public Result cancel(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                         @ApiParam(value = "预约单id",required = true) @RequestParam(value = "orderId") Integer orderId ,
                         HttpServletRequest request ){
        log.info("==================================修改预约单状态==================================");
        cancelOrder(orderId,userId);
        return ResultGenerator.genSuccessResult();
    }
    @Transactional
    public void cancelOrder(Integer orderId,Integer userId){
//        User user = (User)request.getSession().getAttribute("user");
        User user = userService.getById(userId);
        String userName = user.getAccount();//用户名
//        String userName ="aa";
        BespeakOrder bespeakOrder = bespeakOrderService.getById(orderId);
        bespeakOrder.setStatus(2);
        bespeakOrder.setModifier(userName);
        bespeakOrder.setModifytime(new Date());
        bespeakOrderService.update(bespeakOrder);
        //明细库存移动
        Map<String,Object> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("orderType",1);
        List<OrderDetail> details = orderDetailService.selectByParams(params);
        if(details!=null && details.size()>0){
            for(OrderDetail orderDetail:details) {
                orderDetail.setStatus(8);
                orderDetailService.update(orderDetail);
//                GoodSku goodSku = goodSkuService.getBySku(orderDetail.getSkuCode());
//                Map<String,Object> para = new HashMap<>();
//                para.put("goodCode",goodSku.getSkuCode());
//                para.put("seatCode",orderDetail.getSourceSeatCode());
//                SeatStock seatStock = seatStockService.getOneByParams(para);
//                seatStock.setFreezeNum(seatStock.getFreezeNum()- orderDetail.getChoseNum());//目标库存冻结量增加
//                seatStock.setUseNum(seatStock.getUseNum() + orderDetail.getChoseNum());//目标库存现有量增加
//                seatStockService.update(seatStock);
            }
        }
        //取消预约单，同时删除缓存
        int startDay = DateCutUtils.getDay(bespeakOrder.getBespeakTimeStart()); //预约开始的日期
        int endDay = DateCutUtils.getDay(bespeakOrder.getBespeakTimeEnd()); //预约结束的日期
        int startHour = DateCutUtils.getHour(bespeakOrder.getBespeakTimeStart()); //预约开始的小时
        int endHour = DateCutUtils.getHour(bespeakOrder.getBespeakTimeEnd());     //预约结束的小时
//        Integer showRoomId = bespeakOrder.getShowRoomId();

        if (startDay == endDay){
            Set members = redisServicer.setMembers(bespeakOrder.getShowRoomId()+"_"+startDay);
            for(int i=startHour;i<=endHour;i++){
                members.remove(i);
            }
            redisServicer.removePattern(bespeakOrder.getShowRoomId()+"_"+startDay);
            for(Object value : members) {
                redisServicer.add(bespeakOrder.getShowRoomId() + "_" + startDay,value);
            }
        }else if(startDay < endDay){
            Set members1 = redisServicer.setMembers(bespeakOrder.getShowRoomId()+"_"+startDay);
            for(int i=startHour;i<24;i++){
                members1.remove(i);
            }
            for(Object value : members1){
                redisServicer.add(bespeakOrder.getShowRoomId() + "_" + startDay,value);
            }
            Set members2 = redisServicer.setMembers(bespeakOrder.getShowRoomId()+"_"+endDay);
            for (int i=0;i<endHour;i++){
                members2.remove(i);
            }
            for(Object value : members2){
                redisServicer.add(bespeakOrder.getShowRoomId() + "_" + endDay,value);
            }
        }

    }
    /*
 	修改预约单状态(取消)
 	 */
    @ApiOperation(value = "批量取消预约单", httpMethod = "POST")
    @PostMapping("/batchCancel")
    @Transactional
    public Result batchCancel(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                              @ApiParam(value = "预约单id列表",required = true) @RequestParam(value = "orderIds") List<Integer> orderIds, HttpServletRequest request ){
        for(Integer orderId:orderIds){
            cancelOrder(orderId,userId);
        }
        return ResultGenerator.genSuccessResult();
    }

    /*
     * 取消样品
     */
    @ApiOperation(value = "取消样品", httpMethod = "POST")
    @PostMapping("/detailCancel")
    @Transactional
    public Result detailCancel(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                         @ApiParam(value = "预约明细单id",required = true) @RequestParam(value = "detailId") Integer detailId,
                         HttpServletRequest request ){
        cancelDetail(userId,detailId);
        return ResultGenerator.genSuccessResult();
    }
    /*
     * 批量取消样品
     */
    @ApiOperation(value = "批量取消样品", httpMethod = "POST")
    @PostMapping("/detailBatchCancel")
    @Transactional
    public Result detailBatchCancel(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                                    @ApiParam(value = "预约明细单id列表",required = true) @RequestParam(value = "detailIds") List<Integer> detailIds,
                               HttpServletRequest request ){
        for(Integer detailId:detailIds){
            cancelDetail(userId,detailId);
        }
        return ResultGenerator.genSuccessResult();
    }

    private void cancelDetail(Integer userId,Integer detailId){
        User user = userService.getById(userId);
        String userName = user.getAccount();//用户名
        OrderDetail orderDetail = orderDetailService.getById(detailId);
        orderDetailService.delete(orderDetail);
//        orderDetail.setStatus(8);
//        orderDetail.setModifier(userName);
//        orderDetail.setModifytime(new Date());
//        orderDetailService.update(orderDetail);
//        GoodSku goodSku = goodSkuService.getBySku(orderDetail.getSkuCode());
//        Map<String,Object> para = new HashMap<>();
//        para.put("goodCode",goodSku.getSkuCode());
//        para.put("seatCode",orderDetail.getSourceSeatCode());
//        SeatStock seatStock = seatStockService.getOneByParams(para);
//        seatStock.setFreezeNum(seatStock.getFreezeNum() - orderDetail.getChoseNum());//目标库存冻结量减少
//        seatStock.setUseNum(seatStock.getUseNum() + orderDetail.getChoseNum());//目标库存可用量增加
//        seatStockService.update(seatStock);
    }
    /*
    查询预约单列表
     */
    @ApiOperation(value = "查询预约单列表", httpMethod = "GET")
    @GetMapping("/list")
    public Result list(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                       @ApiParam(value = "状态") @RequestParam(value = "status",required = false) Integer status ,
                       @ApiParam(value = "页码",required = true) @RequestParam(value = "page") Integer page,
                       @ApiParam(value = "页长",required = true) @RequestParam(value = "size" ) Integer size,
                       HttpServletRequest request ){
        log.info("==================================查询预约单列表==================================");
        User user = userService.getById(userId);
//        Integer userId = user.getId();
//        Integer userId = 0;
        if(page==null){
            page = 1;
        }
        if(size == null){
            size = 10;
        }
        Integer start = (page-1)*size;
        Map<String,Object> params = new HashMap<>();
        if(null != status) {
            params.put("status", status);
        }
        params.put("ownId",userId);//userId从session中取
        params.put("start",start);//从第几条开始
        params.put("size",size);//每页条数
        List<BespeakOrder> orders = bespeakOrderService.selectListByParams(params);

        //===================================
        List<Map<String,Object>> retResult = new ArrayList<>();
        Map<String,Object> retMap = null;

        Calendar calendar =Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(java.util.Calendar.HOUR_OF_DAY, 3);
        Date startTimeLine = calendar.getTime();
        for(BespeakOrder bespeakOrder:orders){
            retMap = new HashMap<>();
            retMap.put("bespeakOrderId",bespeakOrder.getId());
            retMap.put("code",bespeakOrder.getOrderCode());
            retMap.put("status",bespeakOrder.getStatus());
            retMap.put("bespeakTimeStart",DateFormatUtil.dateToString(bespeakOrder.getBespeakTimeStart()));
            retMap.put("bespeakTimeEnd",DateFormatUtil.dateToString(bespeakOrder.getBespeakTimeEnd()));
            if((startTimeLine).before(bespeakOrder.getBespeakTimeStart()) && bespeakOrder.getStatus()==0){
                retMap.put("flag",true);
            }else {
                retMap.put("flag",false);
            }
            retMap.put("isSelect",false);
            ShowRoomInfo showRoomInfo = showRoomInfoService.getById(bespeakOrder.getShowRoomId());
            retMap.put("placePic",showRoomInfo.getPicture());
            retResult.add(retMap);
//            List<OrderDetail> details = orderDetailService.selectDetailListByOrderId(bespeakOrder.getId());
//            String skuCode = details.get(0).getSkuCode();
//            List<GoodImg> imgs = goodImgService.getBySku(skuCode);
//            retMap.put("imageUrl",imgs.get(0).getImgUrl());
        }
        return ResultGenerator.genSuccessResult(retResult);
    }


    /*
    查询预约单明细
     */
    @ApiOperation(value = "查询预约单明细", httpMethod = "GET")
    @GetMapping("/getDetail")
    public Result getDetail(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                            @ApiParam(value = "预约单id",required = true) @RequestParam(value = "orderId" ) Integer orderId , HttpServletRequest request ){
        log.info("==================================查询预约单明细==================================");
        Map<String,Object> retMap = new HashMap<>();
        BespeakOrderVo bespeakOrderVo = new BespeakOrderVo();
        OrderDetailVo orderDetailVo = null;
        List<OrderDetailVo> detailVos = new ArrayList<>();
        BespeakOrder bespeakOrder = bespeakOrderService.getById(orderId);
        Map<String ,Object> params = new HashMap<>();
        params.put("orderId",orderId);
        List<OrderDetail> details = orderDetailService.selectByParams(params);
        BeanUtils.copyProperties(bespeakOrder,bespeakOrderVo);

        Calendar calendar =Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(java.util.Calendar.HOUR_OF_DAY, 3);
        Date startTimeLine = calendar.getTime();

        List<Map<String,Object>> retDetails = new ArrayList<>();
        Map<String,Object> detailMap = new HashMap<>();
        if(details !=null && details.size()>0) {
            for (OrderDetail orderDetail : details) {
                detailMap = new HashMap<>();
                orderDetailVo = new OrderDetailVo();
                BeanUtils.copyProperties(orderDetail, orderDetailVo);
                GoodSku goodSku = goodSkuService.getBySku(orderDetail.getSkuCode());
                GoodSpu goodSpu = goodSpuService.getBySpu(goodSku.getSpuCode());
                detailVos.add(orderDetailVo);
                detailMap.put("detailId", orderDetailVo.getId());
                if (null != goodSpu.getStyleId()) {
                    GoodStyle style = goodStyleService.getById(goodSpu.getStyleId());
                    detailMap.put("title", goodSpu.getSpuName() + style.getName());
                } else {
                    detailMap.put("title", goodSpu.getSpuName());
                }
                detailMap.put("color", goodSku.getGoodColor());
                if (null != goodSku.getGoodSize()) {
                    detailMap.put("size", goodSku.getGoodSize().toUpperCase());
                } else {
                    detailMap.put("size", goodSku.getGoodSize());
                }
                detailMap.put("choseNum", orderDetail.getChoseNum());
                detailMap.put("normalPrice", goodSku.getNormalPrice());
                detailMap.put("isSelect", false);
                if ((startTimeLine).before(bespeakOrder.getBespeakTimeStart()) && orderDetail.getStatus() == 6
                        && !(orderDetail.getSourceSeatCode()).equals(orderDetail.getTargetSeatCode())) {
                    detailMap.put("detailFlag", true);
                } else {
                    detailMap.put("detailFlag", false);
                }
                detailMap.put("status", orderDetail.getStatus());

                List<GoodImg> imgs = goodImgService.getBySku(goodSku.getSkuCode());
                List<String> pics = new ArrayList<>();
                for (GoodImg goodImg : imgs) {
                    pics.add(goodImg.getImgUrl());
                }
                detailMap.put("pictures", pics);

                retDetails.add(detailMap);
            }
        }

        retMap.put("orderDetail",retDetails);

        bespeakOrderVo.setOrderDetailVoList(detailVos);

        String seatCode = null;
        String smallName = null;
        String placeNumber = null;
        if(null != bespeakOrderVo.getShowRoomId()){
            ShowRoomInfo showRoomInfo = showRoomInfoService.getById(bespeakOrderVo.getShowRoomId());
            seatCode = showRoomInfo.getSeatCode();
            smallName = showRoomInfo.getName();
            placeNumber = showRoomInfo.getPlaceNumber();
        }else{
            ExhibitionRoom exhibitionRoom = exhibitionRoomService.getById(bespeakOrderVo.getExhibitionRoomId());
            Exhibition exhibition = exhibitionService.getById(exhibitionRoom.getExhibitionId());
            seatCode = exhibitionRoom.getSeatCode();
            smallName = exhibition.getName();
            placeNumber = exhibitionRoom.getPlaceNumber();
        }
        SysPlace sysPlace = sysPlaceService.getByPlaceNumber(placeNumber);
        retMap.put("placeName",sysPlace.getExhibition()+smallName);
        retMap.put("placeNumber",placeNumber);
        retMap.put("timeZone",DateFormatUtil.dateToString(bespeakOrderVo.getBespeakTimeStart()) +"-"+ DateFormatUtil.dateToString(bespeakOrderVo.getBespeakTimeEnd()) );
        retMap.put("seatCode",seatCode);
        retMap.put("orderCode",bespeakOrderVo.getOrderCode());
        retMap.put("status",bespeakOrderVo.getStatus());
        retMap.put("orderId",orderId);
        if((startTimeLine).before(bespeakOrder.getBespeakTimeStart()) && bespeakOrder.getStatus()==0){
            retMap.put("flag",true);
        }else {
            retMap.put("flag",false);
        }

        return ResultGenerator.genSuccessResult(retMap);
    }

    /*
    新增预约单明细
    */
    @ApiOperation(value = "新增预约单明细", httpMethod = "POST")
    @PostMapping("/addDetails")
    public Result addDetails(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                             @ApiParam(value = "订单id",required = true) @RequestParam(value = "orderId") Integer orderId,
                             @ApiParam(value = "sku编码",required = true) @RequestParam(value = "skuCode") String skuCode,
                             @ApiParam(value = "已选数量",required = true) @RequestParam(value = "checkedNum") Integer checkedNum,HttpServletRequest request){
        log.info("==================================新增预约单明细==================================");
        User user = userService.getById(userId);
        String userName = user.getAccount();//用户名
        BespeakOrder bespeakOrder = bespeakOrderService.getById(orderId);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderId);
        orderDetail.setOrderType(1); //1:预约单
        orderDetail.setStatus(6);
        orderDetail.setSkuCode(skuCode);
        orderDetail.setChoseNum(checkedNum);
        if(null!=bespeakOrder.getShowRoomId()) {
            ShowRoomInfo showRoominfo = showRoomInfoService.getById(bespeakOrder.getShowRoomId());
            orderDetail.setTargetSeatCode(showRoominfo.getSeatCode());
        }else{
            ExhibitionRoom exhibitionRoom = exhibitionRoomService.getById(bespeakOrder.getExhibitionRoomId());
            orderDetail.setTargetSeatCode(exhibitionRoom.getSeatCode());
        }
        orderDetail.setCreater(userName);
        orderDetail.setCreatetime(new Date());
        orderDetail.setModifier(userName);
        orderDetail.setModifytime(new Date());
//        orderDetails.add(orderDetail);

        String placeNumber = showRoomInfoService.queryPlaceNumber(bespeakOrder.getShowRoomId());
        List<OrderDetail> orderDetails = orderDetailService.setSourceSeatCode(orderDetail,placeNumber);
        orderDetailService.batchInsert(orderDetails);
//        orderDetailService.save(orderDetail);
        return ResultGenerator.genSuccessResult();
    }

    /*
     * 修改预约时间
     */
    @ApiOperation(value = "修改预约时间", httpMethod = "POST")
    @PostMapping("/update")
    public Result update(@ApiParam(value = "用户id",required = true) @RequestParam(value = "userId") Integer userId,
                         @ApiParam(value = "预约单id",required = true) @RequestParam(value = "orderId" ) Integer orderId ,
                         @ApiParam(value = "开始时间",required = true) @RequestParam(value = "bespeakTimeStartStr" ) String bespeakTimeStartStr ,
                         @ApiParam(value = "结束时间",required = true) @RequestParam(value = "bespeakTimeEndStr" ) String bespeakTimeEndStr ,
                         HttpServletRequest request ) throws ParseException {

        if(StringUtils.isNotBlank(bespeakTimeStartStr)
                && StringUtils.isNotBlank(bespeakTimeEndStr)){

            //开始时间不能小于当前时间+3小时
            Calendar calendar1 =Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.HOUR_OF_DAY, 3);
            if((calendar1.getTime()).compareTo(DateFormatUtil.stringToDate(bespeakTimeStartStr)) >= 0){
                return ResultGenerator.genFailResult(ResultCode.CHOSE_TIME_ERROR);
            }
            //预约时间要最多提前3天
            Calendar calendar2 =Calendar.getInstance();
            calendar2.setTime(new Date());
            calendar2.add(Calendar.DAY_OF_MONTH, 3);
            if((calendar2.getTime()).compareTo(DateFormatUtil.stringToDate(bespeakTimeStartStr)) < 0){
                return ResultGenerator.genFailResult(ResultCode.BESPEAK_TIME_ERROR);
            }
            //开始时间不能大于结束时间
            if((DateFormatUtil.stringToDate(bespeakTimeStartStr)).compareTo(DateFormatUtil.stringToDate(bespeakTimeEndStr)) > 0){
                return ResultGenerator.genFailResult(ResultCode.CHOSE_TIME_ERROR);
            }
        }
        Date startTime = DateFormatUtil.stringToDate(bespeakTimeStartStr);
        Date endTime = DateFormatUtil.stringToDate(bespeakTimeEndStr);
        int startDay = DateCutUtils.getDay(startTime); //预约开始的日期
        int endDay = DateCutUtils.getDay(endTime); //预约结束的日期
        int startHour = DateCutUtils.getHour(startTime); //预约开始的小时
        int endHour = DateCutUtils.getHour(endTime);     //预约结束的小时
        BespeakOrder bespeakOrder = bespeakOrderService.getById(orderId);
        Boolean flag = showRoomInfoService.checkIsChose(bespeakOrder.getShowRoomId(),startDay,endDay,startHour,endHour);
        if(flag){  //true 为已被选
            return ResultGenerator.genFailResult("所选择的直播间已被选");
        }
        User user = userService.getById(userId);
        String userName = user.getAccount();
        bespeakOrder.setBespeakTimeStart(startTime);
        bespeakOrder.setBespeakTimeEnd(endTime);
        bespeakOrder.setModifier(userName);
        bespeakOrder.setModifytime(new Date());
        bespeakOrderService.update(bespeakOrder);
        //写入缓存
        if (startDay == endDay){
            for(int i=startHour;i<=endHour;i++){
                redisServicer.add(bespeakOrder.getShowRoomId()+"_"+startDay,i);
            }
        }else if(startDay < endDay){
            for(int i=startHour;i<24;i++){
                redisServicer.add(bespeakOrder.getShowRoomId()+"_"+startDay,i);
            }
            for (int i=0;i<endHour;i++){
                redisServicer.add(bespeakOrder.getShowRoomId()+"_"+endDay,i);
            }
        }
        return ResultGenerator.genSuccessResult();
    }

}
