package com.nice.good.service.impl;


import com.nice.good.dao.ReceiveDetailTempMapper;
import com.nice.good.dao.ReceiveOrderMapper;
import com.nice.good.model.ReceiveDetail;
import com.nice.good.model.ReceiveDetailTemp;
import com.nice.good.model.ReceiveOrder;
import com.nice.good.model.ReceiveOrderTemp;
import com.nice.good.service.ReceiveOrderTempService;
import com.nice.good.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/04/05.
 */
@Service
@Transactional
public class ReceiveOrderTempServiceImpl extends AbstractService<ReceiveOrderTemp> implements ReceiveOrderTempService {

    @Resource
    private ReceiveDetailTempMapper receiveDetailTempMapper;


    @Resource
    private ReceiveOrderMapper receiveOrderMapper;

    @Override
    public String receiveOrderTempSave(ReceiveOrder receiveOrder, String userId) {

        //正式表中部分可更改字段的更新
        Map<String, Object> map = new HashMap<>();
        map.put("receiveId", receiveOrder.getReceiveId());
        map.put("appointTime", receiveOrder.getAppointTime());
        map.put("carNum", receiveOrder.getCarNum());
        map.put("driver", receiveOrder.getDriver());
        map.put("telephone", receiveOrder.getTelephone());
        map.put("carrierCode", receiveOrder.getCarrierCode());
        map.put("platformType", receiveOrder.getPlatformType());
        map.put("platformCode", receiveOrder.getPlatformCode());
        map.put("receiveType", receiveOrder.getReceiveType());
        map.put("orgCode", receiveOrder.getOrgCode());
        map.put("statement", receiveOrder.getStatement());

        receiveOrderMapper.updateFields(map);

        String errorMsg = "";

        List<ReceiveDetail> details = receiveOrder.getReceiveDetails();

        Integer orderStatus = receiveOrder.getOrderStatus();
        if (orderStatus != 0 && orderStatus != 1 && orderStatus != 3 && orderStatus != 5) {
            return errorMsg;
        }


        if (details != null && details.size() > 0) {

            errorMsg = getErrorMsg(errorMsg, details);

            if (StringUtils.isNotBlank(errorMsg)) {
                return errorMsg;
            }

            for (ReceiveDetail detail : details) {

                if (detail != null) {
                    ReceiveDetailTemp detailTemp = receiveDetailTempMapper.selectByPrimaryKey(detail.getDetailId());
                    //新增操作
                    if (detailTemp == null) {

                        //只有预期量不为空，且不为0的才能保存成功

                        Integer expectNum = detail.getExpectNum();
                        if (expectNum == null || StringUtils.isBlank(expectNum.toString())) {
                            continue;
                        }

                        if (expectNum == 0) {
                            continue;
                        }

                        //只有未开始状态并且收货量和拒收量大于0的订单才能收货
                        Integer status = detail.getStatus();

                        if (status != 0) {
                            continue;
                        }

                        creatNewDetail(userId, detail);

                    } else {
                        //修改操作    
                        //只有未开始状态并且收货量和拒收量大于0的订单才能收货
                        Integer status = detail.getStatus();

                        if (status != 0) {
                            continue;
                        }

                        updateDetailTemp(userId, detail, detailTemp);
                    }
                }

            }


        }
        return errorMsg;

}

    private void updateDetailTemp(String userId, ReceiveDetail detail, ReceiveDetailTemp detailTemp) {
//       detailTemp.setId(detail.getId());
//        detailTemp.setDetailId(detail.getDetailId());
//        detailTemp.setShelfNum(detail.getShelfNum());
//        detailTemp.setSecondNum(detail.getSecondNum());
//        detailTemp.setQualityNum(detail.getQualityNum());
//        detailTemp.setStatus(detail.getStatus());
//        detailTemp.setReceiveNum(detail.getReceiveNum());
//        detailTemp.setRefuseNum(detail.getRefuseNum());
//        detailTemp.setGooderCode(detail.getGooderCode());
//        detailTemp.setOrgCode(detail.getOrgCode());
//        detailTemp.setGoodName(detail.getGoodName());
//        detailTemp.setGoodModel(detail.getGoodModel());
//        detailTemp.setPurchaseCode(detail.getPurchaseCode());
//        detailTemp.setPurchaseLineCode(detail.getOutsideLineCode());
//        detailTemp.setIsQuality(detail.getIsQuality());
//        detailTemp.setRweight(detail.getRweight());
//        detailTemp.setBulk(detail.getBulk());
//        detailTemp.setExpectNum(detail.getExpectNum());
//        detailTemp.setSeatCode(detail.getSeatCode());
//        detailTemp.setRfid(detail.getRfid());
//        detailTemp.setOperator(detail.getOperator());
//        detailTemp.setLpn(detail.getLpn());
//        detailTemp.setQualityTime(detail.getQualityTime());
//        detailTemp.setOutsideLineCode(detail.getOutsideLineCode());
//        detailTemp.setShelfTime(detail.getShelfTime());
//        detailTemp.setHangUp(detail.getHangUp());
//        detailTemp.setCreateId(detail.getCreateId());
//        detailTemp.setCreateDate(detail.getCreateDate());
//        detailTemp.setModifyDate(TimeStampUtils.getTimeStamp());
//        detailTemp.setModifyId(userId);
//
//        //主表id
//        detailTemp.setReceiveId(detail.getReceiveId());

        BeanUtils.copyProperties(detail,detailTemp);

        receiveDetailTempMapper.updateByPrimaryKey(detailTemp);
    }

    private void creatNewDetail(String userId, ReceiveDetail detail) {
        ReceiveDetailTemp detailTemp = new ReceiveDetailTemp();
//        detailTemp.setId(detail.getId());
//        detailTemp.setDetailId(detail.getDetailId());
//        detailTemp.setShelfNum(detail.getShelfNum());
//        detailTemp.setSecondNum(detail.getSecondNum());
//        detailTemp.setQualityNum(detail.getQualityNum());
//        detailTemp.setStatus(detail.getStatus());
//        detailTemp.setReceiveNum(detail.getReceiveNum());
//        detailTemp.setRefuseNum(detail.getRefuseNum());
//        detailTemp.setGooderCode(detail.getGooderCode());
//        detailTemp.setGoodCode(detail.getGoodCode());
//        detailTemp.setOrgCode(detail.getOrgCode());
//        detailTemp.setGoodName(detail.getGoodName());
//        detailTemp.setGoodModel(detail.getGoodModel());
//        detailTemp.setPurchaseCode(detail.getPurchaseCode());
//        detailTemp.setPurchaseLineCode(detail.getOutsideLineCode());
//        detailTemp.setIsQuality(detail.getIsQuality());
//        detailTemp.setRweight(detail.getRweight());
//        detailTemp.setBulk(detail.getBulk());
//        detailTemp.setExpectNum(detail.getExpectNum());
//        detailTemp.setSeatCode(detail.getSeatCode());
//        detailTemp.setRfid(detail.getRfid());
//        detailTemp.setOperator(detail.getOperator());
//        detailTemp.setLpn(detail.getLpn());
//        detailTemp.setQualityTime(detail.getQualityTime());
//        detailTemp.setShelfTime(detail.getShelfTime());
//        detailTemp.setHangUp(detail.getHangUp());
//        detailTemp.setCreateId(detail.getCreateId());
//        detailTemp.setCreateDate(detail.getCreateDate());
//        detailTemp.setModifyDate(TimeStampUtils.getTimeStamp());
//        detailTemp.setModifyId(userId);
//
//        //主表id
//        detailTemp.setReceiveId(detail.getReceiveId());

        BeanUtils.copyProperties(detail,detailTemp);


        receiveDetailTempMapper.insert(detailTemp);
    }


    private String getErrorMsg(String errorMsg, List<ReceiveDetail> details) {
        for (ReceiveDetail detail : details) {

            if (detail != null) {
                Integer receiveNum = detail.getReceiveNum();
                if (receiveNum == null || StringUtils.isBlank(receiveNum.toString())) {
                    errorMsg += "第 " + detail.getId() + "条记录收货量不能为空!";
                    continue;

                }
                Integer refuseNum = detail.getRefuseNum();
                if (refuseNum == null || StringUtils.isBlank(refuseNum.toString())) {
                    errorMsg += "第 " + detail.getId() + "条记录拒收量不能为空!";
                    continue;

                }
                if (detail.getSeatCode() == null) {
                    errorMsg += "第 " + detail.getId() + "条库位不能为空!";
                    continue;
                }


                if (receiveNum == 0 && refuseNum == 0) {
                    errorMsg += "第 " + detail.getId() + "条记录接收量和拒收量不能都为0!";
                    continue;
                }

            }
        }
        return errorMsg;
    }


}


