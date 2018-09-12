package com.nice.good.aop;

import java.util.Date;

import com.nice.good.dao.*;
import com.nice.good.model.*;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.IpUtils;
import com.nice.good.utils.LoggerUtil;
import com.nice.good.utils.TimeStampUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;


@Aspect
@Component
@Slf4j
public class LogAopAction {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    private ReceiveOrderMapper receiveOrderMapper;


    @Resource
    private StoreAreaMapper storeAreaMapper;

    @Resource
    private StoreSeatMapper storeSeatMapper;


    @Resource
    private UserSeatMapper userSeatMapper;


    @Resource
    private SysPlaceMapper sysPlaceMapper;

    @Pointcut("@annotation(com.nice.good.aop.LogAnnotation)")
    private void pointCutMethod() {
    }

    /**
     * 记录操作日志
     */
    @After("pointCutMethod()")
    public void recordLog(JoinPoint joinPoint) {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        SysUser sessionUser = (SysUser) request.getSession().getAttribute("sessionUser");

        String username = sessionUser.getUsername();

        Long start = System.currentTimeMillis();

        try {


            Map<String, String> map = getLogMark(joinPoint);

            String content = map.get(LoggerUtil.CONTENT);

            String logType = map.get(LoggerUtil.LOG_TYPE);

            //所有删除操作
            String methodName = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            if ("delete".equals(methodName)) {
                //执行删除操作
                Object object1 = args[0];
                for (Object obj : (ArrayList) object1) {
                    generateLog(request, username, content, logType, obj.toString());
                }

                return;
            }

            if ("新增场地/展厅".equals(content)) {
                Object object1 = args[0];
                if (object1 instanceof SysPlace) {
                    SysPlace sysPlace = ((SysPlace) object1);

                    //只有新增时才生成，默认库区和库位
                    Integer flag = sysPlace.getFlag();
                    String placeNumber = sysPlace.getPlaceNumber();

                    if (0 == flag.intValue()){


                        String userId = sysPlace.getCreateId();

                        String placeId = sysPlace.getPlaceId();

                        /**
                         * 新增场地以后默认生成出库暂存区和暂存位
                         */
                        generateOutAreaAndCode(sysPlace, userId, placeId);

                        /**
                         * 新增场地以后默认生成收货暂存区和收货暂存位
                         */
                        generateReceiveAreaAndCode(sysPlace, userId, placeId);

                        /**
                         * 新增场地以后默认生成借领存放区
                         */
                        generateBorrowAreaAndCode(sysPlace, userId, placeId);

                        /**
                         * 新增场地以后默认生成货品上架区和货品上架位
                         */
                        generateShelfAreaAndCode(sysPlace, userId, placeId);
                    }


                    //生成日志
                    generateLog(request, username, content, logType, placeNumber);


                    return;
                }
            }

            if ("用户新增".equals(content)) {

                Object object1 = args[0];
                if (object1 instanceof SysUser) {
                    SysUser sysUser = ((SysUser) object1);

                    String userType = sysUser.getUserType();


                    String userId = sysUser.getUserId();

                    String username1 = sysUser.getUsername();

                    //生成日志
                    generateLog(request, username, content, logType, username1);

                    Integer flag = sysUser.getFlag();

                    if ("运营".equals(userType) && 0 == flag.intValue()) {

                        Timestamp timeStamp = TimeStampUtils.getTimeStamp();


                        //每个展厅都要生成一套运营的账号
                        List<SysPlace> places = sysPlaceMapper.selectAll();

                        if (places==null || places.size()==0){
                            return;
                        }

                        for (SysPlace place : places) {

                            String placeId = place.getPlaceId();

                            String placeNumber = place.getPlaceNumber();

                            String areaCode = "JLZCKQ001";

                            int num;
                            Integer count = storeSeatMapper.selectCountByAreaAndPlace(areaCode, placeId);
                            if (count == null || count == 0) {
                                num = 1;
                            } else {
                                num = count + 1;
                            }


                            String seatCode = "JLZCKW" + String.format("%03d", num);

                            String seatName = "借领存放位" + String.format("%03d", num);
                            Integer mixBatch = 1;
                            Integer mixGood = 1;
                            String seatType = "暂存";
                            String seatTag = "冻结";
                            String seatLevel = "1";
                            Integer seatCapacity = null;
                            String statement = "系统预置";

                            Integer seatStatus = 1;

                            //出库暂存位
                            StoreSeat storeSeat = new StoreSeat();

                            storeSeat.setPlaceNumber(placeNumber);
                            storeSeat.setPlaceId(placeId);
                            storeSeat.setAreaCode(areaCode);
                            storeSeat.setSeatId(IdsUtils.getOrderId());
                            storeSeat.setSeatCode(seatCode);
                            storeSeat.setSeatName(seatName);
                            storeSeat.setMixBatch(mixBatch);
                            storeSeat.setMixGood(mixGood);
                            storeSeat.setSeatType(seatType);
                            storeSeat.setSeatTag(seatTag);
                            storeSeat.setLevel(seatLevel);
                            storeSeat.setSeatStatus(seatStatus);
                            storeSeat.setSeatCapacity(seatCapacity);
                            storeSeat.setModifier(userId);
                            storeSeat.setCreater(userId);
                            storeSeat.setCreatetime(timeStamp);
                            storeSeat.setModifytime(timeStamp);
                            storeSeat.setStatement(statement);


                            storeSeatMapper.insert(storeSeat);

                            UserSeat userSeat = new UserSeat();

                            userSeat.setUsername(sysUser.getUsername());
                            userSeat.setPlaceId(placeId);
                            userSeat.setSeatCode(seatCode);
                            userSeat.setCreateId(userId);
                            userSeat.setCreateDate(timeStamp);
                            userSeat.setModifyId(userId);
                            userSeat.setModifyDate(timeStamp);

                            userSeatMapper.insert(userSeat);

                        }
                    }

                }

            }


            if ("新增承运商".equals(content)) {
                Object object1 = args[0];
                if (object1 instanceof Carrier) {
                    String carrierCode = ((Carrier) object1).getCarrierCode();
                    generateLog(request, username, content, logType, carrierCode);
                    return;
                }
            }


            if ("调整单新增".equals(content)) {
                Object object1 = args[0];
                if (object1 instanceof AdjustBill) {
                    String adjustBillId = ((AdjustBill) object1).getAdjustBillId();
                    generateLog(request, username, content, logType, adjustBillId);
                    return;
                }
            }

            if ("货品档案新增".equals(content)) {
                Object object1 = args[0];
                if (object1 instanceof Good) {
                    String goodCode = ((Good) object1).getGoodCode();
                    generateLog(request, username, content, logType, goodCode);
                    return;
                }
            }

            if ("货主档案新增".equals(content)) {
                Object object1 = args[0];
                if (object1 instanceof Gooder) {
                    String gooderCode = ((Gooder) object1).getGooderCode();
                    generateLog(request, username, content, logType, gooderCode);
                    return;
                }
            }

            if ("盘点单新增".equals(content)) {
                Object object1 = args[0];
                if (object1 instanceof InventoryBill) {
                    String inventoryBillId = ((InventoryBill) object1).getInventoryBillId();
                    generateLog(request, username, content, logType, inventoryBillId);
                    return;
                }
            }

            if ("计量单位新增".equals(content)) {
                Object object1 = args[0];
                if (object1 instanceof Measure) {
                    String measureId = ((Measure) object1).getMeasureId();
                    generateLog(request, username, content, logType, measureId);
                    return;
                }
            }

            if ("移动单新增".equals(content)) {
                Object object1 = args[0];
                if (object1 instanceof MoveBill) {
                    String moveBillId = ((MoveBill) object1).getMoveBillId();
                    generateLog(request, username, content, logType, moveBillId);
                    return;
                }
            }

            if ("供应商档案新增".equals(content)) {
                Object object1 = args[0];
                if (object1 instanceof Provider) {
                    String providerCode = ((Provider) object1).getProviderCode();
                    generateLog(request, username, content, logType, providerCode);
                    return;
                }
            }

            if ("库区新增".equals(content)) {
                Object object1 = args[0];
                if (object1 instanceof StoreArea) {
                    String areaCode = ((StoreArea) object1).getAreaCode();
                    generateLog(request, username, content, logType, areaCode);
                    return;
                }
            }

            if ("库位新增".equals(content)) {
                Object object1 = args[0];
                if (object1 instanceof StoreSeat) {
                    String seatCode = ((StoreSeat) object1).getSeatCode();
                    generateLog(request, username, content, logType, seatCode);
                    return;
                }
            }


            if ("采购单日志".equals(logType)) {

                Object object1 = args[0];
                if (object1 instanceof Order) {
                    String purchaseCode = ((Order) object1).getPurchaseCode();
                    generateLog(request, username, content, logType, purchaseCode);
                    return;

                }

                // 采购单/确认/取消确认/结算  确认1 反确认0, 结算7
                if ("updateStatus".equals(methodName)) {
                    ArrayList list = (ArrayList) object1;
                    Object object2 = args[1];
                    if ("1".equals(object2.toString())) {
                        content = "确认";
                    }

                    if ("0".equals(object2.toString())) {
                        content = "取消确认";
                    }

                    if ("7".equals(object2.toString())) {
                        content = "结算";
                    }

                    for (Object orderId : list) {

                        String purchaseCode = orderMapper.selectOrderPurchaseCodeByOrderId(orderId.toString());
                        generateLog(request, username, content, logType, purchaseCode);

                    }
                    return;

                }

            }


            if (logType.equals("收货单日志")) {
                Object object1 = args[0];

                //收货单新增
                if ("add".equals(methodName) || "receiveGoods".equals(methodName)) {
                    if (object1 instanceof ReceiveOrder) {
                        String receiveCode = ((ReceiveOrder) object1).getReceiveCode();
                        generateLog(request, username, content, logType, receiveCode);
                    }

                    return;
                }

                if ("hangUp".equals(methodName)) {
                    ArrayList list = (ArrayList) object1;
                    Object object2 = args[1];

                    if ("1".equals(object2.toString())) {
                        content = "挂起";
                    }

                    if ("0".equals(object2.toString())) {
                        content = "取消挂起";
                    }

                    for (Object receiveId : list) {
                        String receiveCode = receiveOrderMapper.selectReceiveCodeByReceiveId(receiveId.toString());
                        generateLog(request, username, content, logType, receiveCode);

                    }
                    return;

                }

                if ("receiveMainGoods".equals(methodName) || "cancelMainGoods".equals(methodName)) {
                    ArrayList list = (ArrayList) object1;
                    for (Object obj : list) {
                        if (obj instanceof ReceiveOrder) {
                            String receiveCode = ((ReceiveOrder) obj).getReceiveCode();
                            generateLog(request, username, content, logType, receiveCode);
                        }
                    }
                    return;
                }

                if ("shelfGoods".equals(methodName) || "cancelGoods".equals(methodName)) {
                    ArrayList list = (ArrayList) object1;
                    for (Object obj : list) {
                        if (obj instanceof ReceiveDetail) {
                            String detailId = ((ReceiveDetail) object1).getDetailId();
                            generateLog(request, username, content, logType, detailId);
                        }
                    }
                    return;
                }


                if ("clearGoods".equals(methodName)) {
                    ArrayList list = (ArrayList) object1;
                    Object object2 = args[1];

                    if ("false".equals(object2.toString())) {
                        content = "收货单结算";
                    }

                    if ("true".equals(object2.toString())) {
                        content = "收货单强制结算";
                    }

                    for (Object obj : list) {
                        if (obj instanceof ReceiveDetail) {
                            String detailId = ((ReceiveDetail) obj).getDetailId();
                            generateLog(request, username, content, logType, detailId);
                        }
                    }
                    return;
                }


                if ("clearMainGoods".equals(methodName)) {
                    ArrayList list = (ArrayList) object1;
                    Object object2 = args[1];

                    if ("false".equals(object2.toString())) {
                        content = "收货单结算";
                    }

                    if ("true".equals(object2.toString())) {
                        content = "收货单强制结算";
                    }

                    for (Object obj : list) {
                        if (obj instanceof ReceiveOrder) {
                            String receiveCode = ((ReceiveOrder) obj).getReceiveCode();
                            generateLog(request, username, content, logType, receiveCode);
                        }
                    }
                    return;
                }


                if ("delReceiveDetail".equals(methodName)) {

                    for (Object obj : (ArrayList) object1) {
                        generateLog(request, username, content, logType, obj.toString());
                    }

                    return;
                }

            }

            //出库日志
            if (logType.equals("出库日志")) {

                Object object1 = args[0];

                if (object1 instanceof OutBase) {
                    String sendCode = ((OutBase) object1).getSendCode();
                    generateLog(request, username, content, logType, sendCode);
                    return;
                }

                ArrayList list = (ArrayList) object1;
                for (Object obj : list) {
                    if (obj instanceof OutBase) {
                        String sendCode = ((OutBase) obj).getSendCode();
                        generateLog(request, username, content, logType, sendCode);
                    }
                }
                return;

            }

            //调整单日志
            if ("调整单日志".equals(logType)) {
                Object object1 = args[0];

                if ("add".equals(methodName)) {
                    if (object1 instanceof AdjustBill) {
                        String adjustBillCode = ((AdjustBill) object1).getAdjustBillCode();
                        generateLog(request, username, content, logType, adjustBillCode);
                        return;
                    }
                }

                //明细调整
                if ("update".equals(methodName)) {
                    if (object1 instanceof AdjustBill) {
                        List<AdjustBillDetail> adjustBillDetails = ((AdjustBill) object1).getAdjustBillDetails();
                        if (adjustBillDetails == null || adjustBillDetails.size() == 0) {
                            return;
                        }
                        for (AdjustBillDetail detail : adjustBillDetails) {
                            generateLog(request, username, content, logType, detail.getDetailId());
                        }
                        return;
                    }
                }

                //主表调整
                if ("updateAdjustBill".equals(methodName)) {
                    ArrayList list = (ArrayList) object1;

                    for (Object obj : list) {
                        if (obj instanceof AdjustBill) {
                            String adjustBillId = ((AdjustBill) obj).getAdjustBillId();
                            generateLog(request, username, content, logType, adjustBillId);
                        }
                    }
                    return;
                }

            }

            //移动单日志
            if ("移动单日志".equals(logType)) {
                Object object1 = args[0];

                if ("add".equals(methodName)) {
                    if (object1 instanceof MoveBill) {
                        String moveBillId = ((MoveBill) object1).getMoveBillId();
                        generateLog(request, username, content, logType, moveBillId);
                        return;
                    }
                }

                if ("update".equals(methodName)) {

                    if (object1 instanceof MoveBill) {
                        String moveBillId = ((MoveBill) object1).getMoveBillId();
                        generateLog(request, username, content, logType, moveBillId);
                        return;
                    }
                }

                if ("updateMoveBill".equals(methodName)) {
                    ArrayList list = (ArrayList) object1;
                    for (Object obj : list) {
                        String moveBillId = ((MoveBill) obj).getMoveBillId();
                        generateLog(request, username, content, logType, moveBillId);
                    }
                    return;
                }
            }

            //盘点单日志
            if ("盘点单日志".equals(logType)) {
                Object object1 = args[0];

                if ("add".equals(methodName)) {
                    if (object1 instanceof InventoryBill) {
                        String inventoryBillId = ((InventoryBill) object1).getInventoryBillId();
                        generateLog(request, username, content, logType, inventoryBillId);
                        return;
                    }
                }

                //复盘
                if ("inventory".equals(methodName)) {
                    ArrayList list = (ArrayList) object1;
                    for (Object obj : list) {
                        String inventoryBillId = ((InventoryBill) obj).getInventoryBillId();
                        generateLog(request, username, content, logType, inventoryBillId);
                    }
                    return;
                }

                if ("update".equals(methodName)) {

                    if (object1 instanceof InventoryBill) {
                        String inventoryBillId = ((InventoryBill) object1).getInventoryBillId();
                        generateLog(request, username, content, logType, inventoryBillId);
                        return;
                    }
                }


                if ("createAdjustBill".equals(methodName)) {
                    ArrayList list = (ArrayList) object1;
                    for (Object obj : list) {
                        String inventoryBillId = ((InventoryBill) obj).getInventoryBillId();
                        generateLog(request, username, content, logType, inventoryBillId);
                    }
                    return;
                }

            }

        } catch (ClassNotFoundException c) {
            log.error(c.getMessage());
        } catch (Exception e) {
            log.error("插入日志异常", e.getMessage());
        }
        Long end = System.currentTimeMillis();
        log.info("记录日志消耗时间:" + (end - start) / 1000);
    }

    private void generateLog(HttpServletRequest request, String username, String content, String logType, String code) {
        OperateLog operateLog = new OperateLog();
        operateLog.setContent(content);
        operateLog.setLogType(logType);
        operateLog.setRelativeInvoice(code);
        operateLog.setIpRecord(IpUtils.getIpFromRequest(request));
        operateLog.setModifyTime(TimeStampUtils.getTimeStamp());
        operateLog.setModifyId(username);

        operateLogMapper.insert(operateLog);
    }

    private Map<String, String> getLogMark(JoinPoint joinPoint) throws ClassNotFoundException {
        Map<String, String> map = new HashMap<>();
        String methodName = joinPoint.getSignature().getName();
        String targetName = joinPoint.getTarget().getClass().getName();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
                map.put(LoggerUtil.CONTENT, logAnnotation.content());
                map.put(LoggerUtil.LOG_TYPE, logAnnotation.logType());


            }
        }
        return map;
    }


    private void generateOutAreaAndCode(SysPlace sysPlace, String userId, String placeId) {
        String placeNumber = sysPlace.getPlaceNumber();
        String areaCode = "KQZC001";
        String areaName = "出库暂存区";
        String areaType = "成品";
        String statement = "系统预置";

        String seatCode = "KWZC001";
        String seatName = "出库暂存位";
        Integer mixBatch = 1;
        Integer mixGood = 1;
        String seatType = "暂存";
        String seatTag = "无";
        String seatLevel = "1";
        Integer seatCapacity = null;

        Integer seatStatus = 1;


        generateAreaAndCode(userId, placeId, placeNumber, areaCode, areaName, areaType, statement, seatCode, seatName, mixBatch, mixGood, seatType, seatTag, seatLevel, seatCapacity, seatStatus);
    }


    private void generateReceiveAreaAndCode(SysPlace sysPlace, String userId, String placeId) {
        String placeNumber = sysPlace.getPlaceNumber();
        String areaCode = "SHZCKQ001";
        String areaName = "收货暂存区";
        String areaType = "成品";
        String statement = "系统预置";

        String seatCode = "SHZCKW001";
        String seatName = "收货暂存位";
        Integer mixBatch = 1;
        Integer mixGood = 1;
        String seatType = "暂存";
        String seatTag = "无";
        String seatLevel = "1";
        Integer seatCapacity = null;

        Integer seatStatus = 1;


        generateAreaAndCode(userId, placeId, placeNumber, areaCode, areaName, areaType, statement, seatCode, seatName, mixBatch, mixGood, seatType, seatTag, seatLevel, seatCapacity, seatStatus);
    }


    private void generateBorrowAreaAndCode(SysPlace sysPlace, String userId, String placeId) {
        String placeNumber = sysPlace.getPlaceNumber();
        String areaCode = "JLZCKQ001";
        String areaName = "借领存放区";
        String areaType = "成品";
        String statement = "系统预置";


        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

        StoreArea storeArea = new StoreArea();

        storeArea.setPlaceId(placeId);
        storeArea.setPlaceNumber(placeNumber);
        storeArea.setAreaId(IdsUtils.getOrderId());
        storeArea.setAreaCode(areaCode);
        storeArea.setAreaName(areaName);
        storeArea.setAreaType(areaType);
        storeArea.setModifier(userId);
        storeArea.setCreater(userId);
        storeArea.setCreatetime(timeStamp);
        storeArea.setModifytime(timeStamp);
        storeArea.setStatement(statement);

        storeAreaMapper.insert(storeArea);


    }

    private void generateShelfAreaAndCode(SysPlace sysPlace, String userId, String placeId) {
        String placeNumber = sysPlace.getPlaceNumber();
        String areaCode = "SJKQ001";
        String areaName = "货品上架区";
        String areaType = "成品";
        String statement = "系统预置";

        String seatCode = "SJKW001";
        String seatName = "货品上架位";
        Integer mixBatch = 1;
        Integer mixGood = 1;
        String seatType = "挂装";
        String seatTag = "无";
        String seatLevel = "1";
        Integer seatCapacity = null;

        Integer seatStatus = 1;


        generateAreaAndCode(userId, placeId, placeNumber, areaCode, areaName, areaType, statement, seatCode, seatName, mixBatch, mixGood, seatType, seatTag, seatLevel, seatCapacity, seatStatus);
    }


    private void generateAreaAndCode(String userId, String placeId, String placeNumber, String areaCode, String areaName, String areaType, String statement, String seatCode, String seatName, Integer mixBatch, Integer mixGood, String seatType, String seatTag, String seatLevel, Integer seatCapacity, Integer seatStatus) {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

        StoreArea storeArea = new StoreArea();

        storeArea.setPlaceId(placeId);
        storeArea.setPlaceNumber(placeNumber);
        storeArea.setAreaId(IdsUtils.getOrderId());
        storeArea.setAreaCode(areaCode);
        storeArea.setAreaName(areaName);
        storeArea.setAreaType(areaType);
        storeArea.setModifier(userId);
        storeArea.setCreater(userId);
        storeArea.setCreatetime(timeStamp);
        storeArea.setModifytime(timeStamp);
        storeArea.setStatement(statement);

        storeAreaMapper.insert(storeArea);


        //库位生成
        StoreSeat storeSeat = new StoreSeat();
        storeSeat.setPlaceId(placeId);
        storeSeat.setPlaceNumber(placeNumber);
        storeSeat.setAreaCode(areaCode);
        storeSeat.setSeatId(IdsUtils.getOrderId());
        storeSeat.setSeatCode(seatCode);
        storeSeat.setSeatName(seatName);
        storeSeat.setMixBatch(mixBatch);
        storeSeat.setMixGood(mixGood);
        storeSeat.setSeatType(seatType);
        storeSeat.setSeatTag(seatTag);
        storeSeat.setLevel(seatLevel);
        storeSeat.setSeatStatus(seatStatus);
        storeSeat.setSeatCapacity(seatCapacity);
        storeSeat.setModifier(userId);
        storeSeat.setCreater(userId);
        storeSeat.setCreatetime(timeStamp);
        storeSeat.setModifytime(timeStamp);
        storeSeat.setStatement(statement);

        storeSeatMapper.insert(storeSeat);
    }
}