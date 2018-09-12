package com.nice.good.service.impl;


import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.AdjustBillDetailMapper;
import com.nice.good.dao.SeatStockMapper;
import com.nice.good.dao.StockMapper;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.AdjustBillDetail;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.AdjustBillMapper;
import com.nice.good.model.AdjustBill;
import com.nice.good.service.AdjustBillService;
import com.nice.good.core.AbstractService;
import com.nice.good.vo.BillDetailVo;
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
 * Created by CodeGenerator on 2018/06/01.
 */
@Service
public class AdjustBillServiceImpl extends AbstractService<AdjustBill> implements AdjustBillService {
    @Resource
    private AdjustBillMapper adjustBillMapper;


    @Resource
    private AdjustBillDetailMapper adjustBillDetailMapper;


    @Resource
    private SeatStockMapper seatStockMapper;

    @Resource
    private StockMapper stockMapper;


    @Override
    public Integer selectAdjustStatus(String adjustBillId) {
        return adjustBillMapper.selectAdjustStatus(adjustBillId);
    }


    @Override
    public CodeAndDateVo getAdjustIdAndDate(String userId) {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();
        CodeAndDateVo codeAndDateVo = new CodeAndDateVo();
        codeAndDateVo.setWorkCode(getAdjustCode());
        codeAndDateVo.setCreateDate(timeStamp);
        codeAndDateVo.setModifyDate(timeStamp);


        codeAndDateVo.setCreateId(userId);
        codeAndDateVo.setModifyId(userId);

        return codeAndDateVo;
    }


    @Override
    @Transactional
    public String saveAdjustBill(AdjustBill adjustBill, String placeId, String userId) {

        String errorMsg = "";

        List<AdjustBillDetail> adjustBillDetails = adjustBill.getAdjustBillDetails();
        if (adjustBillDetails == null || adjustBillDetails.size() == 0) {
            errorMsg = "调整明细不能为空!";
            return errorMsg;

        }

        List<String> list = new ArrayList<>();

        for (AdjustBillDetail detail : adjustBillDetails) {
            //现有量
            Integer existingQuantity = detail.getExistingQuantity();

            //调整量
            Integer adjustQuantity = detail.getAdjustQuantity();
            if (existingQuantity == null || StringUtils.isBlank(existingQuantity.toString())) {
                errorMsg = "调整明细现有量不能为空!";
                return errorMsg;
            }


            if (adjustQuantity == null || StringUtils.isBlank(adjustQuantity.toString())) {
                errorMsg = "调整明细调整量不能为空!";
                return errorMsg;
            }


            if (adjustQuantity == 0) {
                errorMsg = "调整明细调整量不能为0!";
                return errorMsg;
            }

            //调整后数量
            Integer adjustedQuantity = detail.getAdjustedQuantity();

            if (adjustedQuantity<0){
                errorMsg = "调整后数量不能小于0,操作失败!";
                return errorMsg;
            }

            String gooderCode = detail.getGooderCode();
            String goodCode = detail.getGoodCode();
            String orgCode = detail.getOrgCode();
            String providerCode = detail.getProviderCode();
            String seatCode = detail.getSeatCode();

            String goodMessage = gooderCode + goodCode + orgCode + providerCode + seatCode;

            if (list.contains(goodMessage)) {
                errorMsg ="新增的明细中存在重复的记录，添加失败!";
                return errorMsg;
            }

            list.add(goodMessage);


        }

        //只能根据明细是否和主表已经绑定进行判断是新增还是修改操作
        Timestamp timeStamp = TimeStampUtils.getTimeStamp();
        String adjustBillId = adjustBill.getAdjustBillId();
        if (adjustBillId == null) {
            //新增操作
            String adjustBillCode = adjustBill.getAdjustBillCode();
            // 通过 adjustBillId 查询 AdjustBill.java 实体类，判断其是否为 null
            AdjustBill adjustBillIsNull = adjustBillMapper.selectAdjustBillByAdjustBillCode(adjustBillCode);
            // 判断 adjustBillId 是否为 null
            if (adjustBillIsNull != null) {
                // 在高并发状态下，调整单号重复，重新生成一个调整单号。
                adjustBillCode = getAdjustCode();
            }


            //主键
            adjustBillId = IdsUtils.getOrderId();

            adjustBill.setAdjustBillId(adjustBillId);

            adjustBill.setSourceBillCode(null);

            adjustBill.setAdjustBillCode(adjustBillCode);

            adjustBill.setAdjustStatus(0);

            adjustBill.setPlaceId(placeId);
            adjustBillMapper.insert(adjustBill);

            //调整明细单生成
            for (AdjustBillDetail detail : adjustBillDetails) {
                detail.setDetailId(IdsUtils.getOrderId());
                detail.setCreateId(userId);
                detail.setCreateDate(timeStamp);
                detail.setModifyId(userId);
                detail.setModifyDate(timeStamp);

                //关联主表
                detail.setAdjustBillId(adjustBillId);
                detail.setStatus(0);

                //关联场地id
                detail.setPlaceId(placeId);

                adjustBillDetailMapper.insert(detail);
            }
        } else {
            //修改操作
            adjustBill.setModifyId(userId);
            adjustBill.setModifyDate(timeStamp);
            adjustBillMapper.updateByPrimaryKey(adjustBill);

            for (AdjustBillDetail detail : adjustBillDetails) {
                if (detail.getStatus() != 0) {
                    continue;
                }

                detail.setModifyId(userId);
                detail.setModifyDate(timeStamp);

                adjustBillDetailMapper.updateByPrimaryKey(detail);
            }
        }

        return errorMsg;

    }

    @Override
    @Transactional
    public String updateAdjustBillDetails(List<AdjustBillDetail> adjustBillDetails, String placeId, String userId) {


        String errorMsg = "";

        int num = 0;
        String adjustBillId = null;
        // 循环更新 AdjustBillDetail(调整明细表) 中信息
        for (AdjustBillDetail adjustBillDetail : adjustBillDetails) {

            if (adjustBillDetail.getStatus() != 0) {
                errorMsg += "第 " + adjustBillDetail.getId() + "条记录单据状态不对,操作失败!\n";
                continue;
            }
            //现有量
            Integer existingQuantity = adjustBillDetail.getExistingQuantity();

            if (existingQuantity==null || StringUtils.isBlank(existingQuantity.toString())){
                errorMsg += "第 " + adjustBillDetail.getId() + "条记录现有量不能为空,操作失败!\n";
                continue;
            }


            // 调整量
            Integer adjustQuantity = adjustBillDetail.getAdjustQuantity();
            if (adjustQuantity==null || StringUtils.isBlank(adjustQuantity.toString())){
                errorMsg += "第 " + adjustBillDetail.getId() + "条记录调整量不能为空,操作失败!\n";
                continue;
            }

            if (adjustQuantity==0){
                errorMsg += "第 " + adjustBillDetail.getId() + "条记录调整量不能为0,操作失败!\n";
                continue;
            }

            //调整后数量
            Integer adjustedQuantity = adjustBillDetail.getAdjustedQuantity();
            if (adjustedQuantity<0){
                errorMsg += "第 " + adjustBillDetail.getId() + "条记录调整量有误（调整后数量不能小于0）,操作失败!\n";
                continue;
            }

            //调整之前执行更新操作,同步更新数据
            adjustBillDetailMapper.updateByPrimaryKey(adjustBillDetail);


            //货品库位库存有变动不能操作
            String gooderCode = adjustBillDetail.getGooderCode();
            String goodCode = adjustBillDetail.getGoodCode();
            String orgCode = adjustBillDetail.getOrgCode();
            String providerCode = adjustBillDetail.getProviderCode();
            String seatCode = adjustBillDetail.getSeatCode();


            Integer nowNum = seatStockMapper.selectNowNumByCode(gooderCode, goodCode, orgCode, providerCode,seatCode, placeId);
            if (nowNum==null || !nowNum.equals(existingQuantity)){
                errorMsg += "第 " + adjustBillDetail.getId() + "条记录货品库存有变动,操作失败!\n";
                continue;
            }


            Integer useNum = seatStockMapper.selectUseNumByCode(gooderCode, goodCode, orgCode, providerCode,seatCode, placeId);
            if (useNum!=null && adjustQuantity+nowNum<0){
                errorMsg += "第 " + adjustBillDetail.getId() + "条记录调整量有误(库存可用量不能小于0),操作失败!\n";
                continue;
            }

            if (adjustQuantity+nowNum<0){
                errorMsg += "第 " + adjustBillDetail.getId() + "条记录调整量有误（库存可用量不能小于0）,操作失败!\n";
                continue;
            }


            // 将更新的调整明细单的状态设置为：0未开始,1调整中,2已完成
            adjustBillDetail.setStatus(2);
            // 更新 调整明细单
            adjustBillDetail.setModifyId(userId);
            adjustBillDetail.setModifyDate(TimeStampUtils.getTimeStamp());


            adjustBillDetailMapper.updateByPrimaryKey(adjustBillDetail);

            //调整库存,根据调整后数量
            updateStock(placeId, adjustBillDetail, adjustQuantity);


            num++;
            adjustBillId = adjustBillDetail.getAdjustBillId();

        }

        if (num > 0) {
            // 用于设置调整单明细表中的 status 的值， 以此判断 移动单中的 adjustStatus 的值
            Integer adjustStatus = getStatus(adjustBillId);
            AdjustBill adjustBill = adjustBillMapper.selectByPrimaryKey(adjustBillId);
            adjustBill.setModifyId(userId);
            adjustBill.setModifyDate(TimeStampUtils.getTimeStamp());
            adjustBill.setAdjustStatus(adjustStatus);

            adjustBillMapper.updateByPrimaryKey(adjustBill);
        }

        return errorMsg;
    }

    @Override
    @Transactional
    public String updateAdjustBill(List<AdjustBill> adjustBills, String placeId, String userId) {
        String errorMsg = "";


        for (AdjustBill adjustBill : adjustBills) {
            // 调整单状态为： 0-未开始、1-调整中 的才可进行更新操作
            if (adjustBill.getAdjustStatus() == 2) {
                errorMsg += "调整单：" + adjustBill.getAdjustBillId() + " 单据状态不对,调整失败!\n";
                continue;
            }

            String adjustBillId = adjustBill.getAdjustBillId();

            // 获取 调整单 下的所有 调整明细单
            List<AdjustBillDetail> adjustBillDetails = adjustBillDetailMapper.selectAdjustBillDetailByAdjustBillId(adjustBillId);
            if (adjustBillDetails.size() == 0) {
                continue;
            }

            Boolean flag = false;
            for (AdjustBillDetail adjustBillDetail : adjustBillDetails) {

                // 调整明细单状态为：0-未开始 的才可进行更新操作
                if (adjustBillDetail.getStatus() != 0) {
                    continue;
                }

                //现有量
                Integer existingQuantity = adjustBillDetail.getExistingQuantity();
                if (existingQuantity == null || StringUtils.isBlank(existingQuantity.toString())) {
                    continue;
                }

                // 调整量
                Integer adjustQuantity = adjustBillDetail.getAdjustQuantity();
                if (adjustQuantity == null || StringUtils.isBlank(adjustQuantity.toString())) {
                    continue;
                }

                if (adjustQuantity == 0) {
                    continue;
                }

                //调整后数量
                Integer adjustedQuantity = adjustBillDetail.getAdjustedQuantity();
                if (adjustedQuantity<0){
                    continue;
                }

                //库存有变动不能移动
                String gooderCode = adjustBillDetail.getGooderCode();
                String goodCode = adjustBillDetail.getGoodCode();
                String orgCode = adjustBillDetail.getOrgCode();
                String providerCode = adjustBillDetail.getProviderCode();
                String seatCode = adjustBillDetail.getSeatCode();

                //现有量
                Integer nowNum = seatStockMapper.selectNowNumByCode(gooderCode, goodCode, orgCode, providerCode,seatCode, placeId);
                if (nowNum==null || !nowNum.equals(existingQuantity)){
                    continue;
                }


                //可用量
                Integer useNum = seatStockMapper.selectUseNumByCode(gooderCode, goodCode, orgCode, providerCode,seatCode, placeId);
                if (useNum!=null && adjustQuantity+nowNum<0){
                    continue;
                }

                if (adjustQuantity+nowNum<0){
                    continue;
                }

                //分配量
                seatStockMapper.selectAllotNumByCode(gooderCode, goodCode, orgCode, providerCode,seatCode, placeId);

                //冻结量
                seatStockMapper.selectFreezeNumByCode(gooderCode, goodCode, orgCode, providerCode,seatCode, placeId);


                // 将更新的调整明细单的状态设置为：0未开始,1调整中,2已完成
                adjustBillDetail.setStatus(2);
                // 更新 调整明细单
                adjustBillDetail.setModifyId(userId);

                adjustBillDetail.setModifyDate(TimeStampUtils.getTimeStamp());

                adjustBillDetailMapper.updateByPrimaryKey(adjustBillDetail);


                //调整库存
                updateStock(placeId, adjustBillDetail, adjustQuantity);

                flag = true;

            }

            if (flag) {

                // 更新 AdjustBill.java 实体类 信息
                Integer status = getStatus(adjustBillId);
                adjustBill.setAdjustStatus(status);
                adjustBill.setModifyId(userId);
                adjustBill.setModifyDate(TimeStampUtils.getTimeStamp());

                adjustBillMapper.updateByPrimaryKey(adjustBill);
            }

        }

        return errorMsg;
    }


    private void updateStock(String placeId, AdjustBillDetail adjustBillDetail, Integer adjustQuantity) {
        // 更新 库位库存表 中的‘现有量’。 货主编码、货品编码、库位编码，确定一条库位库存信息。
        // 货主编码
        String gooderCode = adjustBillDetail.getGooderCode();
        // 货品编码
        String goodCode = adjustBillDetail.getGoodCode();
        // 库位编码
        String seatCode = adjustBillDetail.getSeatCode();

        //组织编码
        String orgCode = adjustBillDetail.getOrgCode();

        //供应商编码
        String providerCode = adjustBillDetail.getProviderCode();


        // 更新 库位库存表 中 ‘现有量’  -- 2018/06/19  10:12  rk
        seatStockMapper.updateNowNumAndUseNum(adjustQuantity, gooderCode, goodCode, seatCode, orgCode, providerCode, placeId);

        // 更新 库存表 中的‘现有量’。 货主编码、货品编码，确定一条库存表。  -- 2018/06/19  10:37  rk
        stockMapper.updateNowNumAndUseNum(adjustQuantity, gooderCode, goodCode, orgCode, providerCode, placeId);
    }


    public String getAdjustCode() {
        Integer id = adjustBillMapper.selectMaxId();
        if (id == null) {
            id = 1;
        } else {
            id++;
        }

        return "RD" + getBody(id);
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
     * @param adjustBillId
     * @return
     */
    public Integer getStatus(String adjustBillId) {

        List<Integer> counts = adjustBillDetailMapper.selectStatusByAdjustBillId(adjustBillId);
        if (counts==null || counts.size() == 0) {
            return 0;
        }

        if (counts.size() > 1) {
            return 1;
        } else {
            return counts.get(0);
        }

    }

}
