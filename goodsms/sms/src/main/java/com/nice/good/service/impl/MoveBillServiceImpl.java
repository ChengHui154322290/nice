package com.nice.good.service.impl;


import com.nice.good.dao.*;
import com.nice.good.model.*;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.service.MoveBillService;
import com.nice.good.core.AbstractService;
import com.nice.good.vo.CodeAndDateVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/05.
 */
@Service

public class MoveBillServiceImpl extends AbstractService<MoveBill> implements MoveBillService {


    @Resource
    private MoveBillMapper moveBillMapper;


    @Resource
    private DynamicGoodMapper dynamicGoodMapper;

    @Resource
    private StoreSeatMapper storeSeatMapper;

    @Resource
    private SeatStockMapper seatStockMapper;

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private StockMapper stockMapper;


    @Resource
    private MoveBillDetailMapper moveBillDetailMapper;


    @Resource
    private SysUserMapper sysUserMapper;


    @Override
    @Transactional
    public String moveBillSave(MoveBill moveBill, String placeId, String userId) {

        String errorMsg = "";

        List<MoveBillDetail> moveBillDetails = moveBill.getMoveBillDetails();

        if (moveBillDetails == null || moveBillDetails.size() == 0) {
            errorMsg = "移动明细单不能为空!";

            return errorMsg;
        }


        List<String> list = new ArrayList<>();

        for (MoveBillDetail detail : moveBillDetails) {

            if (StringUtils.isBlank(detail.getTargetSeat())) {
                errorMsg = "目标库位不能为空!";
                return errorMsg;
            }

            //现有量
            Integer existingQuantity = detail.getExistingQuantity();
            //移动量
            Integer moveQuantity = detail.getMoveQuantity();

            if (existingQuantity == null || StringUtils.isBlank(existingQuantity.toString())) {
                errorMsg = "移动明细现有量不能为空!";
                return errorMsg;
            }

            if (existingQuantity == 0) {
                errorMsg = "移动明细现有量不能为0!";
                return errorMsg;
            }


            if (moveQuantity == null || StringUtils.isBlank(moveQuantity.toString())) {
                errorMsg = "移动明细移动量不能为空!";
                return errorMsg;
            }

            if (moveQuantity == 0) {
                errorMsg = "移动明细移动量不能为0!";
                return errorMsg;
            }

            if (moveQuantity > existingQuantity) {
                errorMsg = "移动明细移动量不能大于现有量!";
                return errorMsg;
            }

            String gooderCode = detail.getGooderCode();
            String goodCode = detail.getGoodCode();
            String orgCode = detail.getOrgCode();
            String providerCode = detail.getProviderCode();
            String seatCode = detail.getSourceSeat();

            String goodMessage = gooderCode + goodCode + orgCode + providerCode + seatCode;

            if (list.contains(goodMessage)) {
                errorMsg = "新增的明细中存在重复的记录，添加失败!";
                return errorMsg;
            }

            list.add(goodMessage);

        }


        //新增操作
        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

        String moveBillId = moveBill.getMoveBillId();
        if (moveBillId == null) {

            String moveBillCode = moveBill.getMoveBillCode();

            MoveBill moveBillIsNull = moveBillMapper.selectByMoveBillCode(moveBillCode);
            // 判断 moveBillId 是否为 null
            if (moveBillIsNull != null) {
                moveBillCode = getMoveCode();
            }

            // 新增移动单
            moveBillId = IdsUtils.getOrderId();
            moveBill.setMoveBillId(moveBillId);
            moveBill.setMoveBillCode(moveBillCode);
            moveBill.setMoveStatus(0);

            //关联场地id
            moveBill.setPlaceId(placeId);


            //明细单新增

            for (MoveBillDetail detail : moveBillDetails) {
                detail.setDetailId(IdsUtils.getOrderId());
                detail.setMoveBillId(moveBillId);
                detail.setStatus(0);
                detail.setCreateId(userId);

                detail.setCreateDate(timeStamp);
                detail.setModifyId(userId);
                detail.setModifyDate(timeStamp);

                //关联场地id
                detail.setPlaceId(placeId);

                moveBillDetailMapper.insert(detail);
            }

            //判断当前用户是否是运营，如果是运营，移动类型只能是借领或归还，如果中途被人修改，这里需要更正 TODO
//            String userType = sysUserMapper.selecUserTypeByUserName(userId);
//
//            if ("运营".equals(userType)) {
//
//
//            }
//
//            moveBillMapper.insert(moveBill);


        } else {
            //修改操作
            moveBill.setModifyId(userId);
            moveBill.setModifyDate(timeStamp);
            moveBillMapper.updateByPrimaryKey(moveBill);

            for (MoveBillDetail detail : moveBillDetails) {
                if (detail.getStatus() != 0) {
                    continue;
                }

                if (StringUtils.isBlank(detail.getTargetSeat())) {
                    errorMsg = "目标库位不能为空!";
                    return errorMsg;
                }

                //现有量
                Integer existingQuantity = detail.getExistingQuantity();
                //移动量
                Integer moveQuantity = detail.getMoveQuantity();

                if (existingQuantity == null || StringUtils.isBlank(existingQuantity.toString())) {
                    errorMsg = "移动明细现有量不能为空!";
                    return errorMsg;
                }

                if (existingQuantity == 0) {
                    errorMsg = "移动明细现有量不能为0!";
                    return errorMsg;
                }


                if (moveQuantity == null || StringUtils.isBlank(moveQuantity.toString())) {
                    errorMsg = "移动明细移动量不能为空!";
                    return errorMsg;
                }

                if (moveQuantity == 0) {
                    errorMsg = "移动明细移动量不能为0!";
                    return errorMsg;
                }

                if (moveQuantity > existingQuantity) {
                    errorMsg = "移动明细移动量不能大于现有量!";
                    return errorMsg;
                }
                detail.setModifyId(userId);
                detail.setModifyDate(timeStamp);
                moveBillDetailMapper.updateByPrimaryKey(detail);
            }

        }


        return errorMsg;
    }

    @Override
    @Transactional
    public String updateMoveBillDetails(List<MoveBillDetail> moveBillDetails, String placeId, String userId) {

        String errorMsg = "";

        // 循环更新 MoveBillDetail(移动明细单) 中信息
        int num = 0;
        String moveBillId = null;
        for (MoveBillDetail moveBillDetail : moveBillDetails) {

            // 移动量
            Integer moveQuantity = moveBillDetail.getMoveQuantity();

            //现有量
            Integer existingQuantity = moveBillDetail.getExistingQuantity();

            String targetSeat = moveBillDetail.getTargetSeat();

            String sourceSeat = moveBillDetail.getSourceSeat();

            //库位标记
            String tagertSeatTag = storeSeatMapper.selectSeatTagBySeatCode(targetSeat, placeId);
            String sourceSeatTag = storeSeatMapper.selectSeatTagBySeatCode(sourceSeat, placeId);

            Integer status = moveBillDetail.getStatus();
            if (status != 0) {
                errorMsg += "第 " + moveBillDetail.getId() + "条记录单据状态不对,移动失败!\n";
                continue;
            }

            if (existingQuantity == null || StringUtils.isBlank(existingQuantity.toString())) {
                errorMsg += "第 " + moveBillDetail.getId() + "条记录现有量不能为空,操作失败!\n";
                continue;
            }

            if (existingQuantity == 0) {
                errorMsg += "第 " + moveBillDetail.getId() + "条记录现有量不能为0,操作失败!\n";
                continue;
            }


            if (moveQuantity == null || StringUtils.isBlank(moveQuantity.toString())) {
                errorMsg += "第 " + moveBillDetail.getId() + "条记录移动量不能为空,移动失败!\n";
                continue;
            }

            if (moveQuantity == 0) {
                errorMsg += "第 " + moveBillDetail.getId() + "条记录移动量不能为0,移动失败!\n";
                continue;
            }

            if (moveQuantity < 0) {
                errorMsg += "第 " + moveBillDetail.getId() + "条记录移动量不能小于0,移动失败!\n";
                continue;
            }

            if (StringUtils.isBlank(targetSeat)) {
                errorMsg += "第 " + moveBillDetail.getId() + "条记录目标库位不能为空,移动失败!\n";
                continue;
            }

            if (StringUtils.isBlank(sourceSeat)) {
                errorMsg += "第 " + moveBillDetail.getId() + "条记录来源库位不能为空,移动失败!\n";
                continue;
            }


            //执行移动之前，先执行更新操作，同步数据
            moveBillDetailMapper.updateByPrimaryKey(moveBillDetail);



            Integer capacity = storeSeatMapper.selectCapacityBySeatCode(targetSeat, placeId);
            if (capacity != null && moveQuantity > capacity) {
                errorMsg += "第 " + moveBillDetail.getId() + "条记录目标库位容量不足,移动失败!\n";
                continue;
            }

            if (sourceSeatTag != null && "冻结".equals(sourceSeatTag) && tagertSeatTag != null && "冻结".equals(tagertSeatTag)) {
                errorMsg += "第 " + moveBillDetail.getId() + "条记录冻结库位之间不可移动,移动失败!\n";
                continue;
            }




            //库存有变动不能操作
            String gooderCode = moveBillDetail.getGooderCode();
            String goodCode = moveBillDetail.getGoodCode();
            String orgCode = moveBillDetail.getOrgCode();
            String providerCode = moveBillDetail.getProviderCode();

            Integer nowNum = seatStockMapper.selectNowNumByCode(gooderCode, goodCode, orgCode, providerCode, sourceSeat, placeId);
            if (nowNum == null || !nowNum.equals(existingQuantity)) {
                errorMsg += "第 " + moveBillDetail.getId() + "条记录货品库存有变动,操作失败!\n";
                continue;
            }


            moveBillDetail.setStatus(2);
            moveBillDetail.setModifyId(userId);
            moveBillDetail.setModifyDate(new Date());

            moveBillDetailMapper.updateByPrimaryKey(moveBillDetail);

            createDynamicGood(moveBillDetail, placeId);

            //更新库位库存
            // 来源库位对 ‘库位库存表’的 现存量 更新
            updateSourceSeatStock(gooderCode, goodCode, sourceSeat, moveQuantity, sourceSeatTag, tagertSeatTag, userId, orgCode, providerCode, placeId);
            // 目标库位对 ‘库位库存表’的 现存量 更新
            updateTargetSeatStock(gooderCode, goodCode, targetSeat, moveQuantity, sourceSeatTag, tagertSeatTag, userId, orgCode, providerCode, placeId);

            //总库存
            updateStock(gooderCode, goodCode, moveQuantity, sourceSeatTag, tagertSeatTag, userId, orgCode, providerCode, placeId);

            num++;
            moveBillId = moveBillDetail.getMoveBillId();

        }

        if (num > 0) {

            // 用于设置移动单明细表中的 status 的值， 以此判断 移动单中的 moveStatus 的值
            Integer moveStatus = getStatus(moveBillId);
            MoveBill moveBill = moveBillMapper.selectByPrimaryKey(moveBillId);
            moveBill.setMoveStatus(moveStatus);
            moveBill.setModifyId(userId);
            moveBill.setModifyDate(TimeStampUtils.getTimeStamp());
            moveBillMapper.updateByPrimaryKey(moveBill);
        }


        return errorMsg;
    }

    @Override
    @Transactional
    public String updateMoveBill(List<MoveBill> moveBills, String placeId, String userId) {

        String errorMsg = "";

        for (MoveBill moveBill : moveBills) {
            // 移动单状态:0-未开始,1-部分移动 才可进行更新操作
            if (moveBill.getMoveStatus() == 2) {
                errorMsg += "第" + moveBill.getMoveBillId() + "条记录单据状态不对,移动失败!\n";
                continue;
            }
            String moveBillId = moveBill.getMoveBillId();

            // 获取 移动单 下的所有 移动明细单
            List<MoveBillDetail> moveBillDetails = moveBillDetailMapper.selectMoveBillDetailByMoveBillId(moveBillId);
            if (moveBillDetails.size() == 0) {
                continue;
            }

            Boolean flag = false;
            for (MoveBillDetail moveBillDetail : moveBillDetails) {
                // 移动明细单状态为：0-未开始 的才可进行更新操作

                if (moveBillDetail.getStatus() != 0) {
                    continue;
                }

                //现有量
                Integer existingQuantity = moveBillDetail.getExistingQuantity();

                //移动量
                Integer moveQuantity = moveBillDetail.getMoveQuantity();

                //目标库位
                String targetSeat = moveBillDetail.getTargetSeat();

                //来源库位
                String sourceSeat = moveBillDetail.getSourceSeat();

                //库位标记
                String tagertSeatTag = storeSeatMapper.selectSeatTagBySeatCode(targetSeat, placeId);
                String sourceSeatTag = storeSeatMapper.selectSeatTagBySeatCode(sourceSeat, placeId);


                if (StringUtils.isBlank(targetSeat)) {
                    continue;
                }

                if (StringUtils.isBlank(sourceSeat)) {
                    continue;
                }

                if (moveQuantity == null || StringUtils.isBlank(moveQuantity.toString())) {
                    continue;
                }

                if (moveQuantity == 0) {
                    continue;
                }

                if (StringUtils.isBlank(targetSeat)) {
                    continue;
                }

                Integer capacity = storeSeatMapper.selectCapacityBySeatCode(targetSeat, placeId);
                if (capacity != null && moveQuantity > capacity) {
                    continue;
                }

                if (sourceSeatTag != null && "冻结".equals(sourceSeatTag) && tagertSeatTag != null && "冻结".equals(tagertSeatTag)) {
                    continue;
                }

                //库存有变动不能操作
                String gooderCode = moveBillDetail.getGooderCode();
                String goodCode = moveBillDetail.getGoodCode();
                String orgCode = moveBillDetail.getOrgCode();
                String providerCode = moveBillDetail.getProviderCode();

                Integer nowNum = seatStockMapper.selectNowNumByCode(gooderCode, goodCode, orgCode, providerCode, sourceSeat, placeId);
                if (nowNum == null || !nowNum.equals(existingQuantity)) {
                    continue;
                }

                // 将更新的 移动明细单 的状态设置为： 1-已完成
                moveBillDetail.setStatus(2);
                // 更新 移动明细单
                moveBillDetailMapper.updateByPrimaryKey(moveBillDetail);

                createDynamicGood(moveBillDetail, placeId);

                //更新库位库存
                // 来源库位对 ‘库位库存表’的 现存量 更新
                updateSourceSeatStock(gooderCode, goodCode, sourceSeat, moveQuantity, sourceSeatTag, tagertSeatTag, userId
                        , orgCode, providerCode, placeId);
                // 目标库位对 ‘库位库存表’的 现存量 更新
                updateTargetSeatStock(gooderCode, goodCode, targetSeat, moveQuantity, sourceSeatTag, tagertSeatTag, userId, orgCode, providerCode, placeId);

                //总库存
                updateStock(gooderCode, goodCode, moveQuantity, sourceSeatTag, tagertSeatTag, userId, orgCode, providerCode, placeId);

                flag = true;
            }

            if (flag) {
                Integer moveStatus = getStatus(moveBillId);
                moveBill.setMoveStatus(moveStatus);
                moveBill.setModifyId(userId);
                moveBill.setModifyDate(TimeStampUtils.getTimeStamp());
                moveBillMapper.updateByPrimaryKey(moveBill);
            }

        }


        return errorMsg;
    }

    @Override
    public CodeAndDateVo getMoveIdAndDate(String userId) {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();
        CodeAndDateVo codeAndDateVo = new CodeAndDateVo();
        codeAndDateVo.setWorkCode(getMoveCode());
        codeAndDateVo.setCreateDate(timeStamp);
        codeAndDateVo.setModifyDate(timeStamp);

        codeAndDateVo.setCreateId(userId);
        codeAndDateVo.setModifyId(userId);

        return codeAndDateVo;
    }

    /**
     * 通过移动单号、移动状态、移动类型、说明、创建人、创建开始时间、创建结束时间、
     * 修改人、修改开始时间、修改结束时间、来源库位、目标库位 查询 k_move_bill(移动单表) 的信息   -- 2018/06/06  10:58  rk
     *
     * @param moveBillId
     * @param moveStatus
     * @param moveType
     * @param remark
     * @param createId
     * @param createDateStart
     * @param createDateEnd
     * @param modifyId
     * @param modifyDateStart
     * @param modifyDateEnd
     * @param sourceSeat
     * @param targetSeat
     * @return
     */
    @Override
    public List<MoveBill> findMoveBill(String moveBillId,
                                       Integer moveStatus,
                                       Integer moveType,
                                       String remark,
                                       String createId,
                                       Date createDateStart,
                                       Date createDateEnd,
                                       String modifyId,
                                       Date modifyDateStart,
                                       Date modifyDateEnd,
                                       String sourceSeat,
                                       String targetSeat, String placeId) {
        return moveBillMapper.findMoveBill(moveBillId,
                moveStatus,
                moveType,
                remark,
                createId,
                createDateStart,
                createDateEnd,
                modifyId,
                modifyDateStart,
                modifyDateEnd,
                sourceSeat,
                targetSeat,
                placeId);
    }


    private void updateStock(String gooderCode, String goodCode, Integer moveQuantity, String sourceSeatTag, String tagertSeatTag, String userId,
                             String orgCode, String providerCode, String placeId) {

        String stockId = stockMapper.selectByGooderAndCode(gooderCode, goodCode, orgCode, providerCode, placeId);
        Stock stock = stockMapper.selectByPrimaryKey(stockId);
        stock.setModifyId(userId);
        stock.setModifyDate(TimeStampUtils.getTimeStamp());

        //个人向库位拿东西
        if (!"冻结".equals(sourceSeatTag) && "冻结".equals(tagertSeatTag)) {

            Integer useNum = stock.getUseNum();

            stock.setUseNum(useNum - moveQuantity);

            Integer freezeNum = stock.getFreezeNum();

            stock.setFreezeNum(freezeNum + moveQuantity);

            stockMapper.updateByPrimaryKey(stock);

        }

        if ("冻结".equals(sourceSeatTag) && !"冻结".equals(tagertSeatTag)) {

            Integer useNum = stock.getUseNum();

            stock.setUseNum(useNum + moveQuantity);

            Integer freezeNum = stock.getFreezeNum();

            stock.setFreezeNum(freezeNum - moveQuantity);

            stockMapper.updateByPrimaryKey(stock);
        }

    }


    private void updateTargetSeatStock(String gooderCode, String goodCode, String seatCode, Integer moveQuantity, String sourceSeatTag,
                                       String tagertSeatTag, String userId, String orgCode, String providerCode, String placeId) {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

        String stockId = seatStockMapper.selectStockId(gooderCode, goodCode, seatCode, orgCode, providerCode, placeId);
        if (stockId == null) {
            //1.2新增库位库存
            SeatStock seatStock = new SeatStock();
            seatStock.setStockId(IdsUtils.getOrderId());

            Good good = goodMapper.selectByGooderCodeAndGoodCode(gooderCode, goodCode);

            seatStock.setGooderCode(gooderCode);
            seatStock.setGoodCode(goodCode);
            seatStock.setGoodName(good.getGoodName());
            seatStock.setCommodityCode(good.getCommodityCode());
            //这里是上架库位
            seatStock.setSeatCode(seatCode);
            //现有量
            seatStock.setNowNum(moveQuantity);

            //个人向库位拿东西
            if (!"冻结".equals(sourceSeatTag) && "冻结".equals(tagertSeatTag)) {
                seatStock.setUseNum(0);
                seatStock.setFreezeNum(moveQuantity);
            } else {
                //个人向库位还东西
                seatStock.setUseNum(moveQuantity);
                seatStock.setFreezeNum(0);
            }

            seatStock.setAllotNum(0);
            seatStock.setPickNum(0);
            //是否冻结, 默认为否,即0
            seatStock.setFreezeStatus(0);
            seatStock.setCreateId(userId);
            seatStock.setCreateDate(timeStamp);
            seatStock.setModifyId(userId);
            seatStock.setModifyDate(timeStamp);

            //关联场地id
            seatStock.setPlaceId(placeId);

            seatStockMapper.insert(seatStock);
        } else {
            //1.3修改库位库存
            SeatStock st = seatStockMapper.selectByPrimaryKey(stockId);
            //现有量增加
            st.setNowNum(st.getNowNum() + moveQuantity);

            //个人向库位拿东西
            if (!"冻结".equals(sourceSeatTag) && "冻结".equals(tagertSeatTag)) {
                st.setFreezeNum(st.getFreezeNum() + moveQuantity);
            } else {
                st.setUseNum(st.getUseNum() + moveQuantity);

            }

            st.setModifyId(userId);
            st.setModifyDate(timeStamp);
            seatStockMapper.updateByPrimaryKey(st);
        }
    }

    private void updateSourceSeatStock(String gooderCode, String goodCode, String seatCode, Integer moveQuantity, String sourceSeatTag, String tagertSeatTag, String userId, String orgCode, String providerCode, String placeId) {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

        String stockId = seatStockMapper.selectStockId(gooderCode, goodCode, seatCode, orgCode, providerCode, placeId);

        //1.3修改库位库存
        SeatStock st = seatStockMapper.selectByPrimaryKey(stockId);
        //现有量减少
        st.setNowNum(st.getNowNum() - moveQuantity);


        //个人向库位还东西
        if ("冻结".equals(sourceSeatTag) && !"冻结".equals(tagertSeatTag)) {
            st.setFreezeNum(st.getFreezeNum() - moveQuantity);
        } else {
            st.setUseNum(st.getUseNum() - moveQuantity);
        }

        st.setModifyId(userId);
        st.setModifyDate(timeStamp);
        seatStockMapper.updateByPrimaryKey(st);

    }


    /**
     * 新增“货品动态”信息   -- 2018/06/21  10:57  rk
     *
     * @param moveBillDetail
     * @throws Exception
     */
    public void createDynamicGood(MoveBillDetail moveBillDetail, String placeId) {

        //货品动态跟踪
        DynamicGood dynamicGood = new DynamicGood();
        dynamicGood.setKeyId(IdsUtils.getOrderId());
        dynamicGood.setCreateDate(TimeStampUtils.getTimeStamp());
        dynamicGood.setCreateId(moveBillDetail.getCreateId());
        dynamicGood.setModifyDate(TimeStampUtils.getTimeStamp());
        dynamicGood.setModifyId(moveBillDetail.getModifyId());
        dynamicGood.setFromSeat(moveBillDetail.getSourceSeat());
        dynamicGood.setRfid(moveBillDetail.getMoveQuantity());
        dynamicGood.setToSeat(moveBillDetail.getTargetSeat());
        dynamicGood.setSize(moveBillDetail.getMoveQuantity());
        dynamicGood.setGooderCode(moveBillDetail.getGooderCode());
        dynamicGood.setGoodCode(moveBillDetail.getGoodCode());
        dynamicGood.setTradeType("移动");

        dynamicGood.setPlaceId(placeId);
        dynamicGoodMapper.insert(dynamicGood);

    }


    public String getMoveCode() {
        Integer id = moveBillMapper.selectMaxId();
        if (id == null) {
            id = 1;
        } else {
            id++;
        }
        return "YD" + getBody(id);
    }


    /**
     * 8位日期 + 6位流水号
     *
     * @param id
     * @return
     */
    private String getBody(Integer id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()) + String.format("%06d", id);
    }


    /**
     * 获取 status 的 Set<Integer> 集合   -- 2018/06/14  10:11  rk
     *
     * @param moveBillId
     * @return
     */
    public Integer getStatus(String moveBillId) {

        List<Integer> status = moveBillDetailMapper.selectStatusByMoveBillId(moveBillId);
        if (status == null || status.size() == 0) {
            return 0;
        }

        if (status.size() > 1) {
            return 1;
        } else {
            return status.get(0);
        }

    }


}
