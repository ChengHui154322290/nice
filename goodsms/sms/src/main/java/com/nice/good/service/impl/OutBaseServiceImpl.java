package com.nice.good.service.impl;


import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.dto.GatherDto;
import com.nice.good.dto.OutBaseDto;
import com.nice.good.dto.OutDetailDto;
import com.nice.good.enums.ResultCode;
import com.nice.good.exception.IllegalOperateException;
import com.nice.good.model.*;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.service.OutBaseService;
import com.nice.good.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by CodeGenerator on 2018/04/11.
 */
@Service
@Transactional
public class OutBaseServiceImpl extends AbstractService<OutBase> implements OutBaseService {
    @Resource
    private OutBaseMapper outBaseMapper;

    @Resource
    private OutReceiverMapper outReceiverMapper;


    @Resource
    private OutSenderMapper outSenderMapper;


    @Resource
    private OutDetailMapper outDetailMapper;


    @Resource
    private OutPickMapper outPickMapper;


    @Resource
    private OutInvoiceMapper outInvoiceMapper;

    @Resource
    private SeatStockMapper seatStockMapper;


    @Resource
    private StockMapper stockMapper;


    @Resource
    private StoreSeatMapper storeSeatMapper;


    @Resource
    private GoodMapper goodMapper;


    @Resource
    private ReceiveDetailMapper receiveDetailMapper;


    @Autowired
    private StringRedisTemplate redisTemplate;


    @Resource
    private RfidLabelMapper rfidLabelMapper;

    @Resource
    private ModuleRegionMapper moduleRegionMapper;


    @Resource
    private OutTaskMapper outTaskMapper;


    @Resource
    private GoodConfigMapper goodConfigMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void outBaseAdd(OutBase outBase, String placeId, String userId) throws Exception {
        Timestamp timestamp = TimeStampUtils.getTimeStamp();
        String baseId = outBase.getBaseId();


        if (baseId == null) {

            //判断发货单号是否重复
            String id = outBaseMapper.selectBaseIdBySendCode(outBase.getSendCode());
            if (id != null) {
                outBase.setSendCode(getOutCode());
            }
            //基本信息新增
            baseId = IdsUtils.getOrderId();

            outBase.setBaseId(baseId);
            outBase.setCreateId(userId);
            outBase.setCreateDate(timestamp);
            outBase.setModifyId(userId);
            outBase.setModifyDate(timestamp);
            outBase.setOperator(userId);
            outBase.setOrderTime(timestamp);

            outBase.setTypeName("内部创建");

            //挂起状态,默认不挂起
            outBase.setHangUp(0);
            //新增单单据状态未开始
            outBase.setOrderStatus(0);

            //关联场地id
            outBase.setPlaceId(placeId);

            outBaseMapper.insert(outBase);


            //收货人新增
            OutReceiver outReceiver = outBase.getOutReceiver();
            if (outReceiver != null) {
                outReceiver.setReceiverId(IdsUtils.getOrderId());
                //收货人不能为空
                String receiver = outReceiver.getReceiver();
                if (StringUtils.isEmpty(receiver)) {
                    throw new IllegalOperateException("收货人不能为空!");
                }

                Long telephone = outReceiver.getTelephone();

                String phone = outReceiver.getPhone();
                Boolean bool1 = (telephone == null || telephone.toString() == "") && (phone == null || phone.toString() == "");
                if (bool1) {
                    throw new IllegalOperateException("收货人联系方式不能为空!");
                }

                String address = outReceiver.getAddress();
                if (StringUtils.isEmpty(address)) {
                    throw new IllegalOperateException("收货地址不能为空!");
                }

                //关联主表id
                outReceiver.setBaseId(baseId);
                outReceiver.setCreateId(userId);
                outReceiver.setCreateDate(timestamp);
                outReceiver.setModifyId(userId);
                outReceiver.setModifyDate(timestamp);


                //挂起状态,默认不挂起
                outReceiver.setHangUp(0);

                //关联场地id
                outReceiver.setPlaceId(placeId);

                outReceiverMapper.insert(outReceiver);
            }

            //发货人新增
            OutSender outSender = outBase.getOutSender();
            if (outSender != null) {
                //发货人不能为空
                String sender = outSender.getSender();
                String address = outSender.getAddress();
                Long telephone = outSender.getTelephone();
                String phone = outSender.getPhone();

                if (StringUtils.isEmpty(sender)) {
                    throw new IllegalOperateException("发货人不能为空!");
                }
                Boolean bool2 = (telephone == null || telephone.toString() == "") && (phone == null || phone.toString() == "");
                if (bool2) {
                    throw new IllegalOperateException("发货人联系方式不能为空!");
                }

                if (StringUtils.isEmpty(address)) {
                    throw new IllegalOperateException("发货地址不能为空!");
                }

                outSender.setSenderId(IdsUtils.getOrderId());
                //关联主表id
                outSender.setBaseId(baseId);
                outSender.setCreateId(userId);
                outSender.setCreateDate(timestamp);
                outSender.setModifyId(userId);
                outSender.setModifyDate(timestamp);

                //挂起状态,默认不挂起
                outSender.setHangUp(0);

                //关联场地id
                outSender.setPlaceId(placeId);

                outSenderMapper.insert(outSender);
            }

            //新增发票
            OutInvoice outInvoice = outBase.getOutInvoice();
            if (outInvoice != null) {
                outInvoice.setInvoiceId(IdsUtils.getOrderId());
                //关联主表id
                outInvoice.setBaseId(baseId);
                outInvoice.setCreateId(userId);
                outInvoice.setCreateDate(timestamp);
                outInvoice.setModifyId(userId);
                outInvoice.setModifyDate(timestamp);

                //挂起状态,默认不挂起
                outInvoice.setHangUp(0);

                //关联场地id
                outInvoice.setPlaceId(placeId);

                outInvoiceMapper.insert(outInvoice);
            }

            //新增出货明细单
            List<OutDetail> outDetails = outBase.getOutDetails();
            if (outDetails == null || outDetails.size() == 0) {
                throw new IllegalOperateException("出库明细不能为空!");
            }

            // 需要采集和不需要采集的货品不能同时新增

            List<String> list = new ArrayList<>();

            //行号生成规则
            int count = 1;
            Set<Integer> set = new HashSet<>();
            for (OutDetail outDetail : outDetails) {
                if (outDetail != null) {

                    String gooderCode = outDetail.getGooderCode();
                    String goodCode = outDetail.getGoodCode();
                    String orgCode = outDetail.getOrgCode();
                    String providerCode = outDetail.getProviderCode();

                    String goodMessage = gooderCode + goodCode + orgCode + providerCode;

                    if (list.contains(goodMessage)) {
                        throw new IllegalOperateException("新增的明细中存在重复的记录，添加失败!");
                    }

                    list.add(goodMessage);

                    //同一个出库单中不能同时存在rfid采集和不需要采集的明细
                    GoodConfig goodConfig = goodConfigMapper.selectGoodConfigByGoodId(gooderCode, goodCode, placeId);
                    Integer rfidGather = goodConfig.getRfidGather();
                    set.add(rfidGather);
                    if (set.size()>0){
                        throw new IllegalOperateException("同一个出库单中不能同时添加需要RFID采集和无需RFID采集的明细，添加失败!");
                    }

                    //出货单只有生成以后,才能执行分配操作
                    outDetail.setDetailId(IdsUtils.getOrderId());
                    //关联主表id
                    outDetail.setBaseId(baseId);
                    Integer orderNum = outDetail.getOrderNum();
                    if (orderNum == null || orderNum == 0 || StringUtils.isBlank(orderNum.toString())) {
                        throw new IllegalOperateException("订单量不能为空!");
                    }
                    //新增时,分配量,拣货量,发货量,剩余量都为0
                    outDetail.setAllotNum(0);
                    outDetail.setPickNum(0);
                    outDetail.setSendNum(0);
                    outDetail.setSurplusNum(0);
                    //剩余量等于订单量
                    outDetail.setSurplusNum(orderNum);
                    outDetail.setCreateId(userId);
                    outDetail.setCreateDate(timestamp);
                    outDetail.setModifyId(userId);
                    outDetail.setModifyDate(timestamp);
                    outDetail.setStatus(0);

                    //挂起状态,默认不挂起
                    outDetail.setHangUp(0);

                    //行号生成
                    outDetail.setLineCode(String.format("%04d", count));


                    //关联场地id
                    outDetail.setPlaceId(placeId);

                    outDetailMapper.insert(outDetail);

                    count++;


                }
            }

        } else {
            //修改操作,只能针对基本信息和收货人和发货人发票,明细不可更改

            //收货人修改
            OutReceiver outReceiver = outBase.getOutReceiver();
            if (outReceiver != null) {
                //收货人不能为空
                String receiver = outReceiver.getReceiver();
                if (StringUtils.isEmpty(receiver)) {
                    throw new IllegalOperateException("收货人不能为空!");
                }

                Long telephone = outReceiver.getTelephone();

                String phone = outReceiver.getPhone();
                Boolean bool1 = (telephone == null || telephone.toString() == "") && (phone == null || phone.toString() == "");
                if (bool1) {
                    throw new IllegalOperateException("收货人联系方式不能为空!");
                }

                String address = outReceiver.getAddress();
                if (StringUtils.isEmpty(address)) {
                    throw new IllegalOperateException("收货地址不能为空!");
                }

                outReceiver.setModifyId(userId);
                outReceiver.setModifyDate(timestamp);
                outReceiverMapper.updateByPrimaryKey(outReceiver);
            }

            //发货人修改
            OutSender outSender = outBase.getOutSender();
            if (outSender != null) {

                //发货人不能为空
                String sender = outSender.getSender();
                String address = outSender.getAddress();
                Long telephone = outSender.getTelephone();
                String phone = outSender.getPhone();

                if (StringUtils.isEmpty(sender)) {
                    throw new IllegalOperateException("发货人不能为空!");
                }
                Boolean bool2 = (telephone == null || telephone.toString() == "") && (phone == null || phone.toString() == "");
                if (bool2) {
                    throw new IllegalOperateException("发货人联系方式不能为空!");
                }

                if (StringUtils.isEmpty(address)) {
                    throw new IllegalOperateException("发货地址不能为空!");
                }
                outSender.setModifyId(userId);
                outSender.setModifyDate(timestamp);
                outSenderMapper.updateByPrimaryKey(outSender);
            }

            //发票修改
            OutInvoice outInvoice = outBase.getOutInvoice();
            if (outInvoice != null) {
                outInvoice.setModifyId(userId);
                outInvoice.setModifyDate(timestamp);
                outInvoiceMapper.updateByPrimaryKey(outInvoice);
            }


            //明细表更新
            List<OutDetail> outDetails = outBase.getOutDetails();
            if (outDetails != null && outDetails.size() > 0) {
                for (OutDetail outDetail : outDetails) {
                    if (outDetail != null) {

                        outDetailMapper.updateByPrimaryKey(outDetail);
                    }
                }
            }

            //基本信息修改
            outBase.setModifyId(userId);
            outBase.setModifyDate(timestamp);
            outBase.setOperator(userId);
            outBaseMapper.updateByPrimaryKey(outBase);

        }
    }

    /**
     * 挂起操作
     *
     * @param baseIds
     * @param hangUp
     * @param userId
     * @return
     */
    @Override
    public String hangUp(List<String> baseIds, Integer hangUp, String userId) {

        /**
         * 单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货
         */
        //如果该订单已被其他用户挂起，则无法操作
        String errorMsg = "";

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

        for (String baseId : baseIds) {

            if (baseId == null) {
                continue;
            }

            //只能针对已发货之外的订单操作挂起
            OutBase outBase = outBaseMapper.selectByPrimaryKey(baseId);
            if (outBase.getOrderStatus() == 12) {
                if (hangUp == 1) {
                    errorMsg += "发货单" + outBase.getSendCode() + "已发货,挂起失败!\n";
                }
                if (hangUp == 0) {
                    errorMsg += "发货单" + outBase.getSendCode() + "已发货,取消挂起失败!\n";
                }

                continue;

            }

            if (outBase.getOrderStatus() == 13) {

                if (hangUp == 1) {
                    errorMsg += "发货单" + outBase.getSendCode() + "已取消,挂起失败!\n";
                }
                if (hangUp == 0) {
                    errorMsg += "发货单" + outBase.getSendCode() + "已取消,取消挂起失败!\n";
                }

                continue;

            }

            if (outBase.getHangUp() == 1 && !userId.equals(outBase.getModifyId())) {
                errorMsg += "发货单" + outBase.getSendCode() + "已被" + outBase.getModifyId() + "的用户挂起,您无法操作!\n";
                continue;

            }


            //基本信息表挂起
            outBase.setHangUp(hangUp);
            outBase.setModifyId(userId);
            outBase.setModifyDate(timeStamp);
            outBaseMapper.updateByPrimaryKey(outBase);

            //收货人挂起
            outReceiverMapper.updateByBaseId(baseId, hangUp);

            //发货人挂起
            outSenderMapper.updateByBaseId(baseId, hangUp);

            //发票挂起
            outInvoiceMapper.updateByBaseId(baseId, hangUp);

            //拣货明细挂起
            outDetailMapper.updateByBaseId(baseId, hangUp);

        }


        return errorMsg;
    }

    /**
     * 删除操作
     *
     * @param baseIds
     * @return
     */
    @Override
    public String deleteByBaseId(List<String> baseIds) {

        String errorMsg = "";

        for (String baseId : baseIds) {

            //删除之前先要判断订单是否被挂起
            if (baseId == null) {
                continue;
            }


            OutBase outBase = outBaseMapper.selectByPrimaryKey(baseId);
            Integer hangUp = outBase.getHangUp();
            if (hangUp == 1) {
                errorMsg += "发货单" + outBase.getSendCode() + "已被挂起,删除失败!";
                continue;
            }

            //只有未开始的订单才能执行删除操作
            if (outBase.getOrderStatus() != 0) {
                errorMsg += "发货单" + outBase.getSendCode() + "状态不对,删除失败!";
                continue;

            }

            //删除子表收货人
            outReceiverMapper.deleteByBaseId(baseId);

            //删除子表发货人
            outSenderMapper.deleteByBaseId(baseId);

            //删除子表发票
            outInvoiceMapper.deleteByBaseId(baseId);

            //删除子表明细表
            outDetailMapper.deleteByBaseId(baseId);

            //删除主表

            outBaseMapper.deleteByPrimaryKey(baseId);

        }
        return errorMsg;
    }

    /**
     * 主界面分配操作  只有库位之间发生移动和出库时才会影响现有量
     *
     * @param outBases
     * @param userId
     * @return
     */

    @Override
    public String generateMainOutPick(List<OutBase> outBases, String placeId, String userId) throws Exception {

        //单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货'

        String errorMsg = "";

        for (OutBase outBase : outBases) {
            //对于主表来说未开始,部分分配订单才能执行分配操作
            Integer orderStatus = outBase.getOrderStatus();
            if (orderStatus != 0 && orderStatus != 1) {
                errorMsg += "发货单" + outBase.getSendCode() + "单据状态不对,分配失败!\n";
                continue;
            }
            //挂起中的订单不能执行分配操作
            if (outBase.getHangUp() == 1) {
                errorMsg += "发货单" + outBase.getSendCode() + "已被挂起,分配失败!\n";
                continue;
            }


            //一个出库单中存在多个出库明细单
            //每次去数据库查询,防止用户重复点击分配
            List<OutDetail> outDetails = outDetailMapper.selectByBaseId(outBase.getBaseId());


            errorMsg = generateOutPick(outBase, outDetails, errorMsg, placeId, userId);


        }

        return errorMsg;
    }


    @Transactional
    public String generateOutPick(OutBase outBase, List<OutDetail> outDetails, String errorMsg, String placeId, String userId) throws Exception {


        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

        //只有未开始和部分分配的订单才能分配

        int count = 0;
        int num = 0;
        label:
        for (OutDetail outDetail : outDetails) {
            if (outDetail != null) {

                // 状态 0未开始,1部分分配,2已分配,3部分拣货,4拣货完成,5部分发货,6已发货
                //只有未开始的订单才能分配
                if (outDetail.getStatus() != 0) {
                    continue;
                }

                //通过货主编码和货品编码查找货品所在库位,只有正品才能生成拣货明细
                String gooderCode = outDetail.getGooderCode();
                String goodCode = outDetail.getGoodCode();

                //组织机构编码
                String orgCode = outDetail.getOrgCode();

                //供应商编码
                String providerCode = outDetail.getProviderCode();

                //查找当前货品所分布所有库位及库存
                List<SeatStock> seatStocks = seatStockMapper.selectAllGood(gooderCode, goodCode, orgCode, providerCode, placeId);

                if (seatStocks == null || seatStocks.size() == 0) {
                    ++num;
                    continue;
                }

                //根据订单量最大限度出货
                Integer orderNum = outDetail.getOrderNum();
                int totalPickNum = 0;
                int times = 1;
                for (SeatStock seatStock : seatStocks) {

                    //获取每个库位可用量
                    Integer useNum = seatStock.getNowNum();
                    if (useNum == null || useNum == 0) {
                        continue;
                    }

                    //如果当前库位分配量足够,则不去下一个库位分配
                    if (orderNum <= useNum && times == 1) {
                        //这里只能是第 一次进入,第 一个库位即满足要求,第 二次不能进入
                        ++times;
                        //按照分配量拣货
                        GenerateAndUpdate generateAndUpdate = new GenerateAndUpdate(outBase, errorMsg, outDetail, totalPickNum, seatStock, orderNum, placeId, userId).invoke();
                        if (generateAndUpdate.is()) {
                            errorMsg += generateAndUpdate.getErrorMsg();
                            continue label;
                        }
                        totalPickNum = generateAndUpdate.getTotalPickNum();


                        //已分配
                        outDetail.setStatus(2);

                        outDetail.setAllotNum(totalPickNum);
                        outDetail.setModifyId(userId);
                        outDetail.setModifyDate(timeStamp);

                        outDetailMapper.updateByPrimaryKey(outDetail);

                        ++count;

                        continue label;

                    } else {
                        //当前库位库存可用量不够分配,查找下一个库位分配
                        if (totalPickNum + useNum < orderNum) {
                            //累加到当前库位,分配量还不够

                            //每个库位按照最大可用量进行分配
                            GenerateAndUpdate generateAndUpdate = new GenerateAndUpdate(outBase, errorMsg, outDetail, totalPickNum, seatStock, useNum, placeId, userId).invoke();
                            if (generateAndUpdate.is()) {
                                errorMsg += generateAndUpdate.getErrorMsg();
                                continue label;
                            }
                            totalPickNum = generateAndUpdate.getTotalPickNum();

                        } else {
                            //累加到当前库位,分配量已经足够
                            //当前库位实际分配量为
                            int actualAllotNum = totalPickNum + useNum - orderNum;

                            //按照实际分配量拣货
                            GenerateAndUpdate generateAndUpdate = new GenerateAndUpdate(outBase, errorMsg, outDetail, totalPickNum, seatStock, actualAllotNum, placeId, userId).invoke();
                            if (generateAndUpdate.is()) {
                                errorMsg += generateAndUpdate.getErrorMsg();
                                continue label;
                            }

                            totalPickNum = generateAndUpdate.getTotalPickNum();
                            //分配量已经足够,退出循环,进行下一个明细的分配


                            //已分配
                            outDetail.setStatus(2);

                            outDetail.setAllotNum(totalPickNum);
                            outDetail.setModifyId(userId);
                            outDetail.setModifyDate(timeStamp);

                            outDetailMapper.updateByPrimaryKey(outDetail);

                            ++count;

                            continue label;
                        }

                    }
                }
                //订单量超过可用量,默认以最大可用量为准
                Integer useNum = stockMapper.selectUseNumByCode(gooderCode, goodCode, orgCode, providerCode, placeId);

                //总库存中分配量变更
                stockMapper.updateAllotNumStock(gooderCode, goodCode, useNum, orgCode, providerCode, placeId);


                //部分分配
                outDetail.setStatus(1);
                //原因 分配缺货
                outDetail.setReason("分配缺货");


                outDetail.setAllotNum(totalPickNum);
                outDetail.setModifyId(userId);
                outDetail.setModifyDate(timeStamp);

                outDetailMapper.updateByPrimaryKey(outDetail);
                ++count;

            }

        }
        String baseId = outBase.getBaseId();
        //基本信息状态的变更
        if (count > 0) {
            updateBaseStatus(baseId);
        }

        if (num == outDetails.size()) {
            errorMsg += "发货单" + outBase.getSendCode() + "库存可用量为空,分配失败!\n";
        }

        return errorMsg;
    }


    /**
     * 主界面出库发货操作
     *
     * @param outBases
     * @param userId
     * @return
     */

    @Override
    public String outMainSend(List<OutBase> outBases, String placeId, String userId) {
        //单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货,13已取消
        String errorMsg = "";
        for (OutBase outBase : outBases) {

            Integer orderStatus = outBase.getOrderStatus();
            if (orderStatus < 1 || orderStatus >= 11) {
                errorMsg += "发货单" + outBase.getSendCode() + "单据状态不对,发货失败!\n";
                continue;
            }


            //挂起中的订单不能执行发货操作
            String modifyId = outBase.getModifyId();
            if (outBase.getHangUp() == 1 && !modifyId.equals(userId)) {
                errorMsg += "发货单" + outBase.getSendCode() + "已被" + modifyId + "用户挂起,发货失败!\n";
                continue;
            }

            if (outBase.getHangUp() == 1 && modifyId.equals(userId)) {
                errorMsg += "发货单" + outBase.getSendCode() + "已被挂起,发货失败!\n";
                continue;
            }


            //每次去数据库查询,防止用户重复点击发货
            List<OutDetail> outDetails = outDetailMapper.selectByBaseId(outBase.getBaseId());

            //调用出库发货逻辑
            errorMsg += outSend(outBase, outDetails, errorMsg, placeId, userId);

        }

        return errorMsg;
    }


    public String outSend(OutBase outBase, List<OutDetail> outDetails, String errorMsg, String placeId, String userId) {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();


        //快递单号不能为空
        int sum1 = 0;
        int sum2 = 0;
        for (OutDetail outDetail : outDetails) {
            if (outDetail != null) {
                if (outDetail.getExpressCode() == null) {
                    sum1++;
                }

                String gooderCode = outDetail.getGooderCode();
                String goodCode = outDetail.getGoodCode();
                //判断是否进行rfid采集
                GoodConfig goodConfig = goodConfigMapper.selectGoodConfigByGoodId(gooderCode, goodCode,placeId);
                if (goodConfig.getRfidGather() == 1) {
                    if (outDetail.getPickNum() == null || outDetail.getPickNum() == 0) {
                        sum2++;
                    }
                }
            }
        }

//        if (sum1 == outDetails.size()) {
//            errorMsg = "发货单" + outBase.getSendCode() + "快递单号不能为空!\n";
//            return errorMsg;
//        }


        if (sum2 == outDetails.size()) {
            errorMsg = "发货单" + outBase.getSendCode() + "请先进行rfid采集!\n";
            return errorMsg;
        }


        int count = 0;
        for (OutDetail outDetail : outDetails) {
            //只有部分拣货,拣货完成和部分发货的订单才能发货
            //状态判断   单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货

            if (outDetail != null) {

                //快递单号为空的不能出库
//                if (outDetail.getExpressCode() == null) {
//                    errorMsg += "发货单" + outBase.getSendCode() + "中第 " + outDetail.getId() + "条发货明细记录快递单号为空,操作不成功!\n";
//                    continue;
//                }

                //未分配和发货操作之后的的出库单不能发货
                Integer status = outDetail.getStatus();
                if (status < 1 || status > 10) {
                    errorMsg += "发货单" + outBase.getSendCode() + "中第 " + outDetail.getId() + "条发货明细单据状态不对,操作不成功!\n";
                    continue;
                }


                String gooderCode = outDetail.getGooderCode();
                String goodCode = outDetail.getGoodCode();

                //组织编码
                String orgCode = outDetail.getOrgCode();

                //供应商编码
                String providerCode = outDetail.getProviderCode();


                List<OutPick> outPicks = outPickMapper.selectAllByDetailId(outDetail.getDetailId());

                //判断是否进行rfid采集
                GoodConfig goodConfig = goodConfigMapper.selectGoodConfigByGoodId(gooderCode, goodCode,placeId);

                if (goodConfig.getRfidGather() == 1) {

                    //收货时,如果不进行rfid采集,出货时,则无需rfid采集
                    if (outDetail.getPickNum() == null || outDetail.getPickNum() == 0) {
                        errorMsg += "发货单" + outBase.getSendCode() + "中第 " + outDetail.getId() + "条发货明细记录拣货量不能为空,操作不成功!\n";
                        continue;
                    }


                    //库位库存更新  出库以后,目标库位库存:现有量=现有量-拣货量,分配量=分配量-拣货量,拣货量=拣货量-拣货量
                    for (OutPick outPick : outPicks) {
                        String goalSeatCode = outPick.getGoalSeatCode();
                        Integer rfidNum = outPick.getRfid();

                        seatStockMapper.reduceGoalSeatStock(gooderCode, goodCode, goalSeatCode, rfidNum, orgCode, providerCode, placeId);

                        //操作人更新
                        outPick.setOperator(userId);
                        outPick.setOperateTime(timeStamp);

                        outPickMapper.updateByPrimaryKey(outPick);
                    }

                    //发货量即为拣货量
                    Integer sendNum = outDetail.getPickNum();

                    //出库明细更新
                    //剩余量
                    Integer surplusNum = outDetail.getSurplusNum();

                    if (sendNum > 0 && sendNum < surplusNum) {
                        //部分发货
                        outDetail.setStatus(11);
                        //原因
                        outDetail.setReason("发货缺货");
                    }
                    if (sendNum > 0 && sendNum.equals(surplusNum)) {
                        //已发货
                        outDetail.setStatus(12);
                    }


                    //发货量
                    outDetail.setSendNum(sendNum);
                    //剩余量
                    outDetail.setSurplusNum(outDetail.getOrderNum() - sendNum);

                    outDetail.setModifyId(userId);

                    outDetail.setModifyDate(timeStamp);

                    outDetailMapper.updateByPrimaryKey(outDetail);
                    ++count;


                    //总库存更新
                    //总库存现有量=现有量-出货量 ,分配量=分配量-出库量,拣货量=拣货量-出库量
                    stockMapper.reduceStock(gooderCode, goodCode, sendNum, orgCode, providerCode, placeId);


                } else {
                    //不进行rfid采集,直接出货  分配量即为发货量
                    Integer sendNum = outDetail.getAllotNum();
                    //拣货量为0
                    //库位库存更新  出库以后,目标库位库存:现有量=现有量-拣货量,分配量=分配量-拣货量
                    for (OutPick outPick : outPicks) {
                        String goalSeatCode = outPick.getGoalSeatCode();
                        Integer pickNum = outPick.getPickNum();
                        seatStockMapper.reduceGoalSeatStockNum(gooderCode, goodCode, goalSeatCode, pickNum, orgCode, providerCode, placeId);
                    }

                    //出库明细更新

                    //已发货
                    outDetail.setStatus(12);

                    //发货量
                    outDetail.setSendNum(sendNum);
                    //剩余量
                    outDetail.setSurplusNum(outDetail.getOrderNum() - sendNum);

                    outDetail.setModifyId(userId);

                    outDetail.setModifyDate(timeStamp);

                    outDetailMapper.updateByPrimaryKey(outDetail);
                    ++count;

                    //总库存更新
                    //总库存现有量=现有量-出货量 ,分配量=分配量-出库量
                    stockMapper.reduceStockNum(gooderCode, goodCode, sendNum, orgCode, providerCode, placeId);

                }
            }
        }

        if (count > 0) {
            //基本信息单据状态的变化
            Integer sumOfOrderNum = outDetailMapper.selectSumOfOrderNum(outBase.getBaseId());
            Integer sumOfSurplusNum = outDetailMapper.selectSumOfSurplusNum(outBase.getBaseId());

            if (sumOfSurplusNum > 0 && sumOfSurplusNum < sumOfOrderNum) {
                //部分发货
                outBase.setOrderStatus(11);
            }
            if (sumOfSurplusNum == 0) {
                //已发货
                outBase.setOrderStatus(12);
            }

            outBase.setModifyId(userId);
            outBase.setModifyDate(timeStamp);
            //发货时间
            outBase.setDeliverTime(timeStamp);

            outBaseMapper.updateByPrimaryKey(outBase);
        }

        return errorMsg;
    }


    /**
     * 主界面反分配操作
     *
     * @param outBases
     * @param userId
     * @return
     */

    @Override
    public String cancelMainOutPick(List<OutBase> outBases, String placeId, String userId) throws Exception {


        String errorMsg = "";
        for (OutBase outBase : outBases) {
            //对于主表来说未开始,部分分配订单才能执行分配操作
            Integer orderStatus = outBase.getOrderStatus();
            if (orderStatus != 1 && orderStatus != 2) {
                errorMsg += "发货单" + outBase.getSendCode() + "单据状态不对,分配失败!\n";
                continue;
            }
            //挂起中的订单不能执行分配操作
            if (outBase.getHangUp() == 1) {
                errorMsg += "发货单" + outBase.getSendCode() + "被挂起,分配失败!\n";
                continue;
            }

            //一个出单单中存在多个出库明细单
            List<OutDetail> outDetails = outDetailMapper.selectByBaseId(outBase.getBaseId());

            cancelOutPick(outDetails, placeId, userId);

        }
        return errorMsg;
    }


    public void cancelOutPick(List<OutDetail> outDetails, String placeId, String userId) throws Exception {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();


        //单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货
        int count = 0;
        String baseId = null;
        for (OutDetail outDetail : outDetails) {

            //只有部分分配或分配中的订单才能反分配

            if (outDetail.getStatus() != 1 && outDetail.getStatus() != 2) {

                continue;
            }

            //组织编码
            String orgCode = outDetail.getOrgCode();

            //供应商编码
            String providerCode = outDetail.getProviderCode();


            List<OutPick> outPicks = outPickMapper.selectAllByDetailId(outDetail.getDetailId());

            if (outPicks != null && outPicks.size() > 0) {

                for (OutPick outPick : outPicks) {

                    String pickId = outPick.getPickId();
                    String gooderCode = outPick.getGooderCode();
                    String goodCode = outPick.getGoodCode();
                    String seatCode = outPick.getSeatCode();
                    //每个库位上的拣货量
                    Integer pickNum = outPick.getPickNum();

                    String goalSeatCode = outPick.getGoalSeatCode();

                    //源库位库存中的现有量,可用量增加
                    seatStockMapper.removeSeatStockAllotNum(gooderCode, goodCode, seatCode, pickNum, orgCode, providerCode, placeId);

                    //目标库位库存中现有量减少,分配量减少
                    seatStockMapper.reduceGoalSeatStock(gooderCode, goodCode, goalSeatCode, pickNum, orgCode, providerCode, placeId);

                    //源库位容量减少,目标库位容量复原
                    Integer capacity = storeSeatMapper.selectCapacityBySeatCode(seatCode,placeId);
                    if (capacity != null && capacity != 0) {
                        storeSeatMapper.updateSeatCapacity(seatCode, -pickNum,placeId);
                    }

                    Integer goalCapacity = storeSeatMapper.selectCapacityBySeatCode(seatCode,placeId);
                    if (goalCapacity != null && goalCapacity != 0) {
                        storeSeatMapper.updateSeatCapacity(goalSeatCode, pickNum,placeId);
                    }


                    //总库存中的分配量还原
                    stockMapper.updateAllotNumStock(gooderCode, goodCode, -pickNum, orgCode, providerCode, placeId);

                    //反分配的拣货明细单要清空
                    outPickMapper.deleteByPrimaryKey(pickId);
                }

            }
            //明细单据状态的变更
            outDetail.setStatus(0);
            outDetail.setAllotNum(0);
            outDetail.setReason(null);
            outDetail.setModifyDate(timeStamp);
            outDetail.setModifyId(userId);
            outDetailMapper.updateByPrimaryKey(outDetail);
            ++count;
            baseId = outDetail.getBaseId();

        }
        //基本信息状态的变更
        if (count > 0) {
            updateBaseStatus(baseId);
        }


    }

    /**
     * 出库单基本信息状态更新
     *
     * @param baseId
     */
    private void updateBaseStatus(String baseId) {
        Integer status = getOutBaseStatus(baseId);

        outBaseMapper.updateStatus(baseId, status);
    }

    /**
     * 通过最大 id 值查询 OutBase.java中的 SendCode(发货编号)  --  rk  2018/05/02
     *
     * @return
     */
    @Override
    public OutBase findSendCodeByMaxId() {
        OutBase code = outBaseMapper.findSendCodeByMaxId();
        return code;
    }


    /**
     * 出库rfid采集校验验
     *
     * @param outBase
     * @return
     */
    @Override
    public Result rfidGather(OutBase outBase, String userId) {

        Result result = new Result();
        String baseId = outBase.getBaseId();

        String errorMsg = "";


        //挂起中的订单不能执行取消操作
        String modifyId = outBase.getModifyId();
        if (outBase.getHangUp() == 1 && !modifyId.equals(userId)) {
            errorMsg += "发货单" + outBase.getSendCode() + "已被" + modifyId + "用户挂起,操作不成功!\n";
            return result.setMessage(errorMsg);
        }

        if (outBase.getHangUp() == 1 && modifyId.equals(userId)) {
            errorMsg += "发货单" + outBase.getSendCode() + "已被挂起,操作不成功!\n";
            return result.setMessage(errorMsg);
        }

        List<OutDetail> outDetails = outDetailMapper.selectByBaseId(baseId);


        int num = 0;
        if (outDetails != null && outDetails.size() > 0) {
            for (OutDetail detail : outDetails) {
                if (detail != null) {
                    Integer status = detail.getStatus();
                    if (status != 1 && status != 2 && status != 3) {
                        num++;
                    }
                }
            }
        }

        if (num == outDetails.size()) {
            errorMsg += "暂无可采集的出库单据!\n";
            result.setMessage(errorMsg);
            return result;
        }


        //只有进行rfid采集的订单才进行采集
        int count = 0;
        if (outDetails != null && outDetails.size() > 0) {
            for (OutDetail detail : outDetails) {
                String gooderCode = detail.getGooderCode();
                String goodCode = detail.getGoodCode();
                //只有在收货单中进行过rfid采集的订单才能解绑采集
                List<Integer> rfids = receiveDetailMapper.selectRfidByGooderAndCode(gooderCode, goodCode);
                if (rfids == null || rfids.size() == 0) {
                    errorMsg += "暂无可采集的出库单据!\n";
                    result.setMessage(errorMsg);
                }

            }
        }

        if (count == outDetails.size()) {
            errorMsg += "发货单" + outBase.getSendCode() + "无需rfid采集!\n";
            result.setMessage(errorMsg);
            return result;
        }


        List<GatherDto> list = new ArrayList<>();

        if (outDetails != null && outDetails.size() > 0) {
            for (OutDetail detail : outDetails) {
                if (detail != null) {
                    //只有部分分配,已分配和部分拣货的的订单才能出库发货
                    //单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货
                    Integer status = detail.getStatus();
                    if (status != 1 && status != 2 && status != 3) {
                        continue;
                    }

                    String gooderCode = detail.getGooderCode();
                    String goodCode = detail.getGoodCode();
                    //只有在收货单中进行过rfid采集的订单才能解绑采集
                    List<Integer> rfids = receiveDetailMapper.selectRfidByGooderAndCode(gooderCode, goodCode);
                    for (Integer rfid : rfids) {

                        if (rfid == null || rfid == 0) {
                            continue;
                        }
                    }

                    GatherDto gatherDto = new GatherDto();
                    gatherDto.setDetailId(detail.getDetailId());
                    gatherDto.setGooderCode(detail.getGooderCode());
                    gatherDto.setGoodCode(detail.getGoodCode());
                    gatherDto.setAllotNum(detail.getAllotNum());
                    gatherDto.setPickNum(detail.getPickNum());
                    gatherDto.setStatus(detail.getStatus());
                    list.add(gatherDto);
                }
            }
        }
        result.setData(list);
        result.setMessage(errorMsg);
        return result;
    }

    /**
     * rfid采集以后保存   rfid采集不影响库存
     *
     * @param gatherDto
     * @param userId
     */

    @Override
    public String rfidGatherSave(GatherDto gatherDto, String placeId, String userId) {

        String errorMsg = "";
        Timestamp timeStamp = TimeStampUtils.getTimeStamp();


        Integer rfidGather = gatherDto.getPickNum();

        if (rfidGather == null || rfidGather == 0) {
            errorMsg = "拣货量不能为空,采集失败!";
            return errorMsg;
        }

        //追溯拣货库位是否正确,提醒
        String gooderCode = gatherDto.getGooderCode();
        String goodCode = gatherDto.getGoodCode();
        Set<String> rfidLabels = redisTemplate.opsForSet().members(gooderCode + ":" + goodCode);

        if (rfidLabels == null || rfidLabels.size() == 0) {
            errorMsg = "rfid为空,采集失败!";
            return errorMsg;
        }

        //拣货来源库位
        List<OutPick> outPicks = outPickMapper.selectByGooderAndGoodCode(gooderCode, goodCode);
        Set<String> seatCodes = new HashSet<>();
        for (OutPick outPick : outPicks) {
            seatCodes.add(outPick.getSeatCode());
        }

        //实际采集量等于原有采集量加上现有采集量
        OutDetail outDetail = outDetailMapper.selectByPrimaryKey(gatherDto.getDetailId());
        //原有采集量
        Integer pickNum = outDetail.getPickNum();

        Map<String, Integer> map = new HashMap<>();


        for (String rfidCode : rfidLabels) {

            RfidLabel rfidLabel = rfidLabelMapper.selectByPrimaryKey(rfidCode);

            if (rfidLabel == null) {
                errorMsg += rfidCode + "标签有误!";
                continue;
            }
            String seatCode = rfidLabel.getSeatCode();

            if (seatCode == null) {
                errorMsg += "货品编码为" + goodCode + "的采集单中,rfid编码为" + rfidLabel.getLabelCode() + "的货品,来源库位不对,采集失败!\n";
                continue;
            }

            if (!seatCodes.contains(seatCode)) {
                errorMsg += "货品编码为" + goodCode + "的采集单中,rfid编码为" + rfidLabel.getLabelCode() + "的货品,来源库位不对,采集失败!\n";
                continue;
            }

            String gooderCode1 = rfidLabel.getGooderCode();
            String goodCode1 = rfidLabel.getGoodCode();

            if (!gooderCode.equals(gooderCode1) || !goodCode.equals(goodCode1)) {
                errorMsg += rfidCode + "标签有误!";
                continue;
            }

            rfidLabelMapper.deleteByPrimaryKey(rfidLabel);

            //采集完以后,清除redis缓存
            redisTemplate.delete(gooderCode + ":" + goodCode);

            //统计拣货库位上的实际拣货量
            Integer num = map.get(seatCode);
            if (num == null) {
                num = 0;
            }

            map.put(seatCode, ++num);

            //采集量同步拣货明细
            pickNum++;

        }

        //采集量同步拣货订单(根据货品和库位同步)
        for (String seatCode : map.keySet()) {
            OutPick outPick = outPickMapper.selectGoodAndSeatCode(gooderCode, goodCode, seatCode);
            String goalSeatCode = outPick.getGoalSeatCode();
            int newRfidNum = map.get(seatCode);
            outPick.setRfid(outPick.getRfid() + newRfidNum);
            outPick.setModifyId(userId);
            outPick.setModifyDate(timeStamp);
            outPickMapper.updateByPrimaryKey(outPick);


            String orgCode = outPick.getOrgCode();

            String providerCode = outPick.getProviderCode();

            //采集量同步目标库位库存拣货量
            seatStockMapper.addGoalSeatStockPickNum(gooderCode, goodCode, goalSeatCode, newRfidNum, orgCode, providerCode, placeId);

            //采集量同步总库存拣货量
            stockMapper.addStockPickNum(gooderCode, goodCode, newRfidNum, orgCode, providerCode, placeId);

        }

        //数量和状态变更
        //单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货,13已取消
        Integer allotNum = outDetail.getAllotNum();
        if (pickNum > 0 && pickNum < allotNum) {
            //部分拣货
            outDetail.setStatus(3);
            outDetail.setReason("拣货缺货");
        }

        if (pickNum > 0 && pickNum.equals(allotNum)) {
            //已拣货
            outDetail.setStatus(4);
        }

        outDetail.setPickNum(pickNum);
        outDetail.setModifyId(userId);
        outDetail.setModifyDate(timeStamp);
        outDetailMapper.updateByPrimaryKey(outDetail);


        //基本信息表状态更新
        String baseId = outDetail.getBaseId();

        Integer totalOrderNum = outDetailMapper.selectOrderSumByBaseId(baseId);

        Integer totalAllotNum = outDetailMapper.selectAllotSumByBaseId(baseId);

        Integer totalPickNum = outDetailMapper.selectSumOfPickNum(baseId);


        int status = 0;
        if (totalAllotNum > 0 && totalAllotNum < totalOrderNum) {
            //单据状态,分配中
            status = 1;
        }

        if (totalAllotNum > 0 && totalAllotNum.equals(totalOrderNum)) {
            //单据状态,已分配
            status = 2;
        }

        Boolean bool1 = totalAllotNum.equals(totalOrderNum) && totalPickNum > 0 && totalPickNum < totalAllotNum;

        if (bool1) {
            //单据状态,部分拣货
            status = 3;
        }

        Boolean bool2 = totalAllotNum.equals(totalOrderNum) && totalPickNum > 0 && totalPickNum.equals(totalAllotNum);

        if (bool2) {
            //单据状态,已拣货
            status = 4;
        }

        outBaseMapper.updateStatus(baseId, status);


        return errorMsg;

    }

    /**
     * 订单取消操作
     *
     * @param outBases
     * @param userId
     * @return
     */

    @Override
    public String cancelOutBase(List<OutBase> outBases, String userId) {

        String errorMsg = "";

        for (OutBase outBase : outBases) {
            if (outBase == null) {
                continue;
            }
            //挂起中的订单不能执行取消操作
            String modifyId = outBase.getModifyId();
            if (outBase.getHangUp() == 1 && !modifyId.equals(userId)) {
                errorMsg += "发货单" + outBase.getSendCode() + "已被" + modifyId + "用户挂起,操作不成功!\n";
                continue;
            }

            if (outBase.getHangUp() == 1 && modifyId.equals(userId)) {
                errorMsg += "发货单" + outBase.getSendCode() + "已被挂起,操作不成功!\n";
                continue;
            }

            //只有未开始的订单才能执行取消操作
            if (outBase.getOrderStatus() != 0) {
                errorMsg += "只有未开始的单据才能执行取消操作,发货单" + outBase.getSendCode() + "操作不成功!\n";
                continue;
            }

            /**
             * 单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货,13已取消
             */

            Timestamp timeStamp = TimeStampUtils.getTimeStamp();

            OutBase base = outBaseMapper.selectByPrimaryKey(outBase.getBaseId());
            base.setOrderStatus(13);
            base.setModifyDate(timeStamp);
            base.setModifyId(userId);

            outBaseMapper.updateByPrimaryKey(base);

            List<OutDetail> outDetails = outDetailMapper.selectByBaseId(base.getBaseId());
            for (OutDetail detail : outDetails) {
                if (detail != null) {
                    detail.setStatus(13);
                    detail.setModifyDate(timeStamp);
                    detail.setModifyId(userId);
                    outDetailMapper.updateByPrimaryKey(detail);
                }
            }


        }
        return errorMsg;
    }

    /**
     * 修改新增明细
     *
     * @param stocks
     * @param baseId
     * @param userId
     * @return
     */

    @Override
    public String addOutDetail(List<SeatStock> stocks, String baseId, String userId) throws Exception {

        String errorMsg = "";
        OutBase base = outBaseMapper.selectByPrimaryKey(baseId);
        if (base == null) {
            errorMsg = "出库单不存在,操作不成功!";
            return errorMsg;
        }

        if (base.getOrderStatus() != 0) {
            errorMsg = "只有未开始的出库单才能添加明细,添加失败!";
            return errorMsg;
        }

        List<String> list = new ArrayList<>();

        //不能添加重复的记录
        //1.原来已有的明细记录
        List<OutDetail> outDetails = outDetailMapper.selectByBaseId(baseId);
         if (outDetails!=null && outDetails.size()>0){
             for (OutDetail detail:outDetails){
                 String gooderCode = detail.getGooderCode();
                 String goodCode = detail.getGoodCode();
                 String orgCode = detail.getOrgCode();
                 String providerCode = detail.getProviderCode();

                 String goodMessage = gooderCode + goodCode + orgCode + providerCode;

                 list.add(goodMessage);
             }
         }


        //2.新增的明细记录
        for (SeatStock billVo : stocks) {

            String gooderCode = billVo.getGooderCode();
            String goodCode = billVo.getGoodCode();
            String orgCode = billVo.getOrgCode();
            String providerCode = billVo.getProviderCode();

            String goodMessage = gooderCode + goodCode + orgCode + providerCode;

            if (list.contains(goodMessage)) {
                errorMsg = "新增的明细中存在重复的记录，添加失败!";
                return errorMsg;
            }
            list.add(goodMessage);

        }


        Integer count = outDetailMapper.selectCountByBaseId(baseId);
        if (count == null || count == 0) {
            count = 1;
        }

        for (SeatStock stock : stocks) {
            OutDetail outDetail = new OutDetail();
            //关联主表
            outDetail.setBaseId(baseId);
            outDetail.setDetailId(IdsUtils.getOrderId());
            outDetail.setGooderCode(stock.getGooderCode());
            outDetail.setGoodCode(stock.getGoodCode());
            outDetail.setGoodName(stock.getGoodName());
            outDetail.setPackCode(stock.getPackCode());

            //包装单位
            outDetail.setUnite(stock.getUnite());
            outDetail.setStatus(0);
            outDetail.setOrderNum(0);
            outDetail.setAllotNum(0);
            outDetail.setPickNum(0);
            outDetail.setSendNum(0);
            outDetail.setSurplusNum(0);
            outDetail.setCreateId(userId);
            outDetail.setCreateDate(TimeStampUtils.getTimeStamp());
            outDetail.setModifyId(userId);
            outDetail.setModifyDate(TimeStampUtils.getTimeStamp());

            //行号生成
            if (count != 1) {
                ++count;
            }
            outDetail.setLineCode(String.format("%04d", count));

            outDetailMapper.insert(outDetail);

            count++;

        }

        /**
         * 单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货,13已取消
         */

        base.setModifyId(userId);
        base.setModifyDate(TimeStampUtils.getTimeStamp());

        outBaseMapper.updateByPrimaryKey(base);

        return errorMsg;
    }


    /**
     * 修改删除明细
     *
     * @param detailIds
     * @return
     */

    @Override
    public String delOutDetail(List<String> detailIds, String userId) {

        String errorMsg = "";

        String baseId = null;
        int num = 0;
        for (String detailId : detailIds) {
            OutDetail outDetail = outDetailMapper.selectByPrimaryKey(detailId);

            if (outDetail == null) {
                continue;
            }
            if (outDetail.getStatus() != 0) {
                errorMsg += "第 " + outDetail.getId() + "条记录状态不对,删除失败!\n";
                continue;
            }

            baseId = outDetail.getBaseId();

            outDetailMapper.deleteByPrimaryKey(detailId);
            ++num;
        }


        //基本信息状态的变更
        if (num > 0) {

            updateBaseStatus(baseId);

        }
        return errorMsg;
    }

    private Integer getOutBaseStatus(String baseId) {

        /**
         *  出库单状态:
         0未开始,1部分分配,2已分配,3部分拣货,4已拣货, 11部分发货,12已发货
         规律一: contains 11  status =11
         规律二: first <11  contains 12  status=11
         规律三: contains 3  status=3;
         规律四: first<3  contains 4, status=3;
         规律五: contains 1,  status=1;
         规律六: first<2  contains 2, status=1;
         规律七:size=1 ,status=first
         (状态优先级依次从前到后)
         */

        List<OutDetail> outDetails = outDetailMapper.selectByBaseId(baseId);
        if (outDetails == null || outDetails.size() == 0) {
            return 0;
        }

        TreeSet<Integer> set = new TreeSet<>();

        for (OutDetail detail : outDetails) {
            set.add(detail.getStatus());
        }

        int first = set.first();

        if (set.contains(11)) {
            return 11;
        }
        if (first < 11 && set.contains(12)) {
            return 11;
        }

        if (set.contains(3)) {
            return 3;
        }
        if (first < 3 && set.contains(4)) {
            return 3;
        }
        if (set.contains(1)) {
            return 1;
        }
        if (first < 2 && set.contains(2)) {
            return 1;
        }
        if (set.size() == 1) {
            return first;
        }

        return 0;
    }

    /**
     * 修改刷新出库明细
     *
     * @param baseId
     * @return
     */
    @Override
    public Result listOutDetail(String baseId) {
        Result result = new Result();
        List<OutDetail> outDetails = outDetailMapper.selectByBaseId(baseId);
        result.setData(outDetails);
        return result;
    }


    /**
     * 拣货明细单的生成,库位容量和库存的更新
     */
    private class GenerateAndUpdate {
        private boolean myResult;
        private OutBase outBase;
        private String errorMsg;
        private OutDetail outDetail;
        private int totalPickNum;
        private SeatStock seatStock;
        private Integer useNum;
        private String placeId;
        private String userId;

        public GenerateAndUpdate(OutBase outBase, String errorMsg, OutDetail outDetail, int totalPickNum, SeatStock seatStock, Integer useNum, String placeId, String userId) {
            this.outBase = outBase;
            this.errorMsg = errorMsg;
            this.outDetail = outDetail;
            this.totalPickNum = totalPickNum;
            this.seatStock = seatStock;
            this.useNum = useNum;
            this.placeId = placeId;
            this.userId = userId;
        }

        boolean is() {
            return myResult;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public int getTotalPickNum() {
            return totalPickNum;
        }

        public GenerateAndUpdate invoke() throws Exception {

            //拣货明细默认目标库位来自于货品档案中的出库待选库位
            String goalSeat = goodConfigMapper.selectOutSeat(outDetail.getGooderCode(), outDetail.getGoodCode());

            if (goalSeat == null) {
                errorMsg = "目标库位不能为空,操作不成功!\n";
                myResult = true;
                return this;
            }


            // 分配库位之前判断库位容量是否足够
            // 查看当前库位现有库存容量
            Integer goalCapacity = storeSeatMapper.selectCapacityBySeatCode(goalSeat,placeId);
            if (goalCapacity != null && goalCapacity != 0) {
                if (useNum > goalCapacity) {
                    errorMsg += "发货单" + outBase.getSendCode() + "中第 " + outDetail.getId() + "条明细目标库位容量不足,分配失败!";
                    myResult = true;
                    return this;
                }
            }


            //生成拣货明细
            OutPick outPick = new OutPick();

            //关联明细表id
            String detailId = outDetail.getDetailId();
            outPick.setDetailId(detailId);
            outPick.setPickId(IdsUtils.getOrderId());
            //发货单号
            outPick.setSendCode(outBase.getSendCode());
            //去数据库查找最大值
            Integer id = outPickMapper.selectMaxId();
            if (id == 0 || id == null) {
                id = 1;
            } else {
                id++;
            }

            String gooderCode = outDetail.getGooderCode();
            String goodCode = outDetail.getGoodCode();

            outPick.setDetailCode(getPickCode());
            outPick.setGooderCode(gooderCode);
            outPick.setGoodCode(goodCode);
            outPick.setPackCode(outDetail.getPackCode());
            //新增拣货明细时rfid采集量为0
            outPick.setRfid(0);


            //目标库位
            outPick.setGoalSeatCode(goalSeat);
            outPick.setPickNum(useNum);

            totalPickNum += useNum;

            //源库位
            String seatCode = seatStock.getSeatCode();
            outPick.setSeatCode(seatCode);

            //点击分配以后拣货明细要同步到数据库
            Timestamp timeStamp = TimeStampUtils.getTimeStamp();
            outPick.setCreateDate(timeStamp);
            outPick.setCreateId(userId);
            outPick.setModifyDate(timeStamp);
            outPick.setModifyId(userId);


            //组织机构编码
            String orgCode = outDetail.getOrgCode();
            outPick.setOrgCode(orgCode);

            //供应商编码
            String providerCode = outDetail.getProviderCode();
            outPick.setProviderCode(providerCode);

            //行号生成
            String lineCode = outDetail.getLineCode();
            outPick.setLineCode(lineCode);

            outPickMapper.insert(outPick);

            //生成拣货任务
            generateTask(outDetail, goalSeat, seatCode, timeStamp);


            //生成明细以后,源库位容量恢复,目标库位容量减少
            Integer capacity = storeSeatMapper.selectCapacityBySeatCode(seatCode,placeId);
            if (capacity != null && capacity != 0) {
                storeSeatMapper.updateSeatCapacity(seatCode, useNum,placeId);
            }

            if (goalCapacity != null && goalCapacity != 0) {
                storeSeatMapper.updateSeatCapacity(goalSeat, -useNum,placeId);
            }

            //分配以后,源库位库存中的现有量,可用量减少,分配量数量转移到目标库位库存的分配量
            seatStockMapper.reduceSeatStockUseNum(gooderCode, goodCode, seatCode, useNum, orgCode, providerCode, placeId);

            //目标库位库存变化:现有量=现有量+分配量,分配量=分配量+分配量
            updateGoalSeatStock(gooderCode, goodCode, goalSeat, useNum, orgCode, providerCode, placeId
            );

            //总库存更新 :现有量不变,可用量=可用量-分配量,分配量=分配量+分配量
            stockMapper.updateAllotNumStock(gooderCode, goodCode, useNum, orgCode, providerCode, placeId);


            myResult = false;
            return this;
        }


        /**
         * 生成拣货任务
         *
         * @param outDetail
         * @param goalSeat
         * @param seatCode
         * @param timeStamp
         */
        private void generateTask(OutDetail outDetail, String goalSeat, String seatCode, Timestamp timeStamp) {
            //生成拣货任务

            String detailId = outDetail.getDetailId();
            String gooderCode = outDetail.getGooderCode();
            String goodCode = outDetail.getGoodCode();
            String lineCode = outDetail.getLineCode();
            String providerCode = outDetail.getProviderCode();
            String orgCode = outDetail.getOrgCode();

            OutTask outTask = new OutTask();
            outTask.setSendCode(outBase.getSendCode());
            //任务号
            outTask.setTaskCode(getTaskCode(detailId));
            //优先级
            outTask.setPriority(outBase.getPriority());
            //任务类型
            outTask.setTaskType("拣货");
            //货主
            outTask.setGooderCode(gooderCode);
            //关联明细表id
            outTask.setDetailId(detailId);
            //sku
            outTask.setGoodCode(goodCode);
            //包装
            outTask.setPackCode(outDetail.getPackCode());
            //状态
            outTask.setStatus(0);
            //拣货量
            outTask.setPickNum(useNum);
            //库位
            outTask.setSeatCode(seatCode);
            //目标库位
            outTask.setGoalSeat(goalSeat);
            outTask.setModifyTime(timeStamp);

            //行号生成
            outTask.setLineCode(lineCode);
            //供应商编码
            outTask.setProviderCode(providerCode);
            //组织机构编码
            outTask.setOrgCode(orgCode);

            outTaskMapper.insert(outTask);
        }

        private void updateGoalSeatStock(String gooderCode, String goodCode, String goalSeat, Integer allotNum, String orgCode, String providerCode, String placeId) throws Exception {
            String stockId = seatStockMapper.selectStockId(gooderCode, goodCode, goalSeat, orgCode, providerCode, placeId);
            Good good = goodMapper.selectByGooderCodeAndGoodCode(gooderCode, goodCode);

            if (stockId == null) {
                //新增一条记录
                SeatStock seatStock = new SeatStock();
                seatStock.setStockId(IdsUtils.getOrderId());
                seatStock.setGooderCode(gooderCode);
                seatStock.setGoodCode(goodCode);
                seatStock.setSeatCode(goalSeat);
                seatStock.setCommodityCode(good.getCommodityCode());
                seatStock.setGoodName(good.getGoodName());
                seatStock.setNowNum(allotNum);
                seatStock.setUseNum(0);
                seatStock.setAllotNum(allotNum);
                seatStock.setPickNum(0);
                seatStock.setFreezeNum(0);
                seatStock.setCreateId(userId);
                seatStock.setModifyId(userId);
                seatStock.setModifyDate(TimeStampUtils.getTimeStamp());

                //关联场地id
                seatStock.setPlaceId(placeId);
                seatStockMapper.insert(seatStock);
            } else {

                seatStockMapper.addGoalSeatStockAllotNum(gooderCode, goodCode, goalSeat, allotNum, orgCode, providerCode, placeId
                );
            }

        }

    }

    /**
     * 出库单号的规则：自动生成（FH+6位日期+5位流水）示例：SO180909000001
     *
     * @return
     */

    public String getOutCode() {
        Integer id = outBaseMapper.selectMaxId();
        if (id == null) {
            id = 1;
        } else {
            id++;
        }

        String send_code = "FH" + getBody(id);


        return send_code;
    }

    /**
     * 拣货明细编号规则：自动生成（JH+6位日期+5位流水）示例：JH180909000001
     *
     * @return
     */

    public String getPickCode() {
        Integer id = outPickMapper.selectMaxId();
        if (id == null) {
            id = 1;
        } else {
            id++;
        }

        String detail_code = "JH" + getBody(id);

        return detail_code;
    }

    private String getBody(Integer id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()).substring(2, 8) + String.format("%06d", id);
    }

    /**
     * 发货单号的规则：自动生成（FH+6位日期+5位流水）示例：FH180909000001    -- 2018/05/29  17:34 rk
     *
     * @return
     */
    public String getSendCode() {

        Integer id = outBaseMapper.selectMaxId();
        if (id == null) {
            id = 1;
        } else {
            id++;
        }

        String send_code = "FH" + getBody(id);

        return send_code;

    }

    /**
     * 任务号生成规则
     */
    private String getTaskCode(String detailId) {
        Integer maxId = outTaskMapper.selectCountByDetailId(detailId);
        if (maxId == 0 || maxId == null) {
            maxId = 1;
        } else {
            maxId++;
        }
        return "JH" + String.format("%03d", maxId);
    }

    /**
     * 出库订单导入保存
     *
     * @param success
     * @param success1
     * @param userId
     */
    @Override
    @Transactional
    public void uploadExcelForAddStoreSeat(List<OutBaseDto> success, List<OutDetailDto> success1, String userId) throws Exception {
        OutBase outBase;
        OutDetail outDetail;
        OutInvoice outInvoice;
        OutReceiver outReceiver;
        OutSender outSender;
        if (success.size() > 0) {
            // 主表出库单保存
            for (OutBaseDto outBaseDto : success) {
                outBase = new OutBase();
                OutBase outBase1 = findSendCodeByMaxId();
                String sendCodeMsg = null;
                // if (StringUtils.isNotBlank(sendCode)) {
                sendCodeMsg = getSendCode();
                outBaseDto.setSendCode(sendCodeMsg);
                BeanUtils.copyProperties(outBaseDto, outBase);
                outBase.setOrderType(outBaseDto.getOrderTypeInt());
                //基本信息新增

                String baseId = IdsUtils.getOrderId();
                Timestamp timestamp = TimeStampUtils.getTimeStamp();
                outBase.setBaseId(baseId);
                outBase.setCreateId(userId);
                outBase.setCreateDate(timestamp);
                outBase.setModifyId(userId);
                outBase.setModifyDate(timestamp);
                outBase.setOperator(userId);
                outBase.setOrderTime(timestamp);
                //挂起状态,默认不挂起
                outBase.setHangUp(0);
                //新增单单据状态未开始
                outBase.setOrderStatus(0);

                //类型名称
                outBase.setTypeName("外部导入");
                outBaseMapper.insert(outBase);
                //收货人新增
                outReceiver = new OutReceiver();
                outReceiver.setCountry("中国");
                outReceiver.setReceiverId(IdsUtils.getOrderId());
                outReceiver.setReceiver(outBaseDto.getReceiver());
                outReceiver.setTelephone(Long.parseLong(outBaseDto.getR_telephone()));
                outReceiver.setPhone(outBaseDto.getR_phone());
                List<String> list1 = new ArrayList<>();
                list1.add(outBaseDto.getR_province());
                list1.add(outBaseDto.getR_city());
                list1.add(outBaseDto.getR_district());
                List<String> areaid = moduleRegionMapper.findById(list1);
                int i = 1;
                for (String id : areaid) {
                    if (i == 1) {
                        outReceiver.setProvince(id);
                        i++;
                    } else if (i == 2) {
                        outReceiver.setCity(id);
                        i++;
                    } else {
                        outReceiver.setDistrict(id);
                    }
                }
                outReceiver.setAddress(outBaseDto.getR_address());

                //关联主表id
                outReceiver.setBaseId(baseId);
                outReceiver.setCreateId(userId);
                outReceiver.setCreateDate(timestamp);
                outReceiver.setModifyId(userId);
                outReceiver.setModifyDate(timestamp);

                //挂起状态,默认不挂起
                outReceiver.setHangUp(0);
                outReceiverMapper.insert(outReceiver);
                //发货人新增
                outSender = new OutSender();
                outSender.setCountry("中国");
                outSender.setSender(outBaseDto.getSender());
                outSender.setTelephone(Long.parseLong(outBaseDto.getS_telephone()));
                outSender.setPhone(outBaseDto.getS_phone());
                List<String> list3 = new ArrayList<>();
                list3.add(outBaseDto.getS_province());
                list3.add(outBaseDto.getS_city());
                list3.add(outBaseDto.getS_district());
                List<String> newareaid = moduleRegionMapper.findById(list3);
                int j = 1;
                for (String id : newareaid) {
                    if (j == 1) {
                        outSender.setProvince(id);
                        j++;
                    } else if (j == 2) {
                        outSender.setCity(id);
                        j++;
                    } else {
                        outSender.setDistrict(id);
                    }
                }
                outSender.setAddress(outBaseDto.getS_address());

                outSender.setSenderId(IdsUtils.getOrderId());
                //关联主表id
                outSender.setBaseId(baseId);
                outSender.setCreateId(userId);
                outSender.setCreateDate(timestamp);
                outSender.setModifyId(userId);
                outSender.setModifyDate(timestamp);

                //挂起状态,默认不挂起
                outSender.setHangUp(0);
                outSenderMapper.insert(outSender);

                //新增发票
                outInvoice = new OutInvoice();
                outInvoice.setCountry("中国");
                outInvoice.setInvoiceType(outBaseDto.getInvoiceType());
                outInvoice.setInvoiceHead(outBaseDto.getInvoiceHead());
                if (!"null".equals(outBaseDto.getInvoicelAmount()) && StringUtils.isNotBlank(outBaseDto.getInvoicelAmount())) {
                    outInvoice.setInvoicelAmount(Double.valueOf(outBaseDto.getInvoicelAmount()));
                }
                outInvoice.setInvoiceId(IdsUtils.getOrderId());
                //关联主表id
                outInvoice.setBaseId(baseId);
                outInvoice.setCreateId(userId);
                outInvoice.setCreateDate(timestamp);
                outInvoice.setModifyId(userId);
                outInvoice.setModifyDate(timestamp);
                //挂起状态,默认不挂起
                outInvoice.setHangUp(0);
                outInvoiceMapper.insert(outInvoice);

                //明细新增
                String orderCode = outBaseDto.getOrderCode();
                for (OutDetailDto outDetailDto : success1) {
                    if (orderCode.equals(outDetailDto.getOrderCode())) {
                        outDetail = new OutDetail();
                        BeanUtils.copyProperties(outDetailDto, outDetail);
                        outDetail.setOrderNum(Math.round(Float.valueOf(outDetailDto.getOrderNum())));
                        //新增时,分配量,拣货量,发货量,剩余量都为0
                        outDetail.setGooderCode(outBaseDto.getGooderCode());
                        outDetail.setBaseId(baseId);
                        outDetail.setAllotNum(0);
                        outDetail.setPickNum(0);
                        outDetail.setSendNum(0);
                        outDetail.setSurplusNum(0);
                        //剩余量等于订单量
                        outDetail.setSurplusNum(outDetail.getOrderNum());
                        outDetail.setCreateId(userId);
                        outDetail.setCreateDate(timestamp);
                        outDetail.setModifyId(userId);
                        outDetail.setModifyDate(timestamp);
                        outDetail.setStatus(0);
                        //挂起状态,默认不挂起
                        outDetail.setHangUp(0);
                        outDetail.setDetailId(IdsUtils.getOrderId());
                        outDetailMapper.insert(outDetail);
                        break;
                    }
                }
            }

        }


    }

    @Override
    public List<OutBase> findByOutBase(OutBase outBase, String placeId, List<String> gooderCodes) {
        Map<Object, Object> conditionMap = new HashMap<>();
        Integer orderStatus = null;

        String gooderCode = null;

        String carrierCode = null;

        String priority = null;

        String providerCode = null;

        Integer orderType = null;

        String orderCode = null;

        String waveCode = null;


        Integer printNum = null;

        Integer hangUp = null;

        String sendCode = null;

        String waybillCode = null;

        Date payTimeStart = null;


        Date payTimeEnd = null;

        Date orderTimeStart = null;

        Date orderTimeEnd = null;

        String goodCode = null;

        String statement = null;

        //组织机构编码
        String orgCode = null;

        if (outBase != null) {
            orderTimeStart = outBase.getOrderTimeStart();
            orderTimeEnd = outBase.getOrderTimeEnd();
            orderStatus = outBase.getOrderStatus();
            gooderCode = outBase.getGooderCode();
            carrierCode = outBase.getCarrierCode();
            priority = outBase.getPriority();
            //供应商编码
            providerCode = outBase.getProviderCode();
            //组织机构编码
            orgCode = outBase.getOrgCode();
            orderCode = outBase.getOrderCode();
            waveCode = outBase.getWaveCode();
            orderType = outBase.getOrderType();
            payTimeStart = outBase.getPayTimeStart();
            printNum = outBase.getPrintNum();
            hangUp = outBase.getHangUp();
            sendCode = outBase.getSendCode();
            waybillCode = outBase.getWaybillCode();
            payTimeEnd = outBase.getPayTimeEnd();
            statement = outBase.getStatement();
            goodCode = outBase.getGoodCode();
        }
        //模糊匹配
        conditionMap.put("orderStatus", orderStatus);
        conditionMap.put("gooderCode", gooderCode);
        conditionMap.put("carrierCode", carrierCode);
        conditionMap.put("priority", priority);

        conditionMap.put("orderCode", orderCode);
        conditionMap.put("waveCode", waveCode);
        conditionMap.put("orderType", orderType);
        conditionMap.put("payTimeStart", payTimeStart);
        conditionMap.put("printNum", printNum);
        conditionMap.put("hangUp", hangUp);
        conditionMap.put("sendCode", sendCode);
        conditionMap.put("waybillCode", waybillCode);
        conditionMap.put("payTimeEnd", payTimeEnd);
        conditionMap.put("statement", statement);
        conditionMap.put("goodCode", goodCode);
        conditionMap.put("orderTimeStart", orderTimeStart);
        conditionMap.put("orderTimeEnd", orderTimeEnd);
        conditionMap.put("placeId", placeId);

        conditionMap.put("providerCode", providerCode);

        conditionMap.put("orgCode", orgCode);



        List<OutBase> outBases2 = new ArrayList<>();

        if (gooderCodes != null && gooderCodes.size() > 0) {

            List<OutBase> outBases = outBaseMapper.selectByConditionMap(conditionMap);

            if (outBases != null && outBases.size() > 0) {

                for (OutBase outBase1 : outBases) {
                    if (gooderCodes.contains(outBase1.getGooderCode())) {
                        outBases2.add(outBase1);
                    }
                }
            }

        }


        return outBases2;
    }
}
