package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.dao.*;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.model.Carrier;
import com.nice.good.service.CarrierService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;


/**
 * @Description: 承运商档案
 * @Author: fqs
 * @Date: 2018/3/23 10:42
 * @Version: 1.0
 */
@Service
@Transactional
public class CarrierServiceImpl extends AbstractService<Carrier> implements CarrierService {
    @Resource
    private CarrierMapper carrierMapper;

    @Resource
    private GooderTransportMapper gooderTransportMapper;


    @Resource
    private ReceiveOrderMapper receiveOrderMapper;


    @Resource
    private OutBaseMapper outBaseMapper;

    @Override
    public String carrierAdd(Carrier carrier, String userId) throws Exception {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();


        String errorMsg = null;
        String carrierId = carrier.getCarrierId();
        if (carrierId == null) {

            carrierId = IdsUtils.getOrderId();
            //承运商编码不可重复
            errorMsg = CheckUniqueField(carrier, carrierId, errorMsg);
            if (errorMsg != null) {
                return errorMsg;
            }
            carrier.setCarrierId(carrierId);
            carrier.setCreater(userId);
            carrier.setCreatetime(timeStamp);
            carrier.setModifier(userId);
            carrier.setModifytime(timeStamp);

            carrierMapper.insert(carrier);
        } else {
            //承运商编码不可重复
            errorMsg = CheckUniqueField(carrier, carrierId, errorMsg);
            if (errorMsg != null) {
                return errorMsg;
            }
            carrier.setModifier(userId);
            carrier.setModifytime(timeStamp);
            carrierMapper.updateByPrimaryKey(carrier);

        }
        return errorMsg;

    }

    @Override
    public String deleteByCarrierId(List<String> carrierIds) {
        String errorMsg = "";
        for (String carrierId:carrierIds) {
            if (carrierId == null) {
               continue;
            }

            Carrier carrier = carrierMapper.selectByPrimaryKey(carrierId);
            if (carrier==null){
                continue;
            }

            String carrierCode = carrier.getCarrierCode();
            //物流模板中如果有关联则不能删除
            List<String> transIds = gooderTransportMapper.selectTransportByCarrierId(carrierId);
            if (transIds != null && transIds.size() > 0) {
                errorMsg += "承运商"+carrierCode+"和物流模板有关联,删除失败!\n";
                continue;
            }


            //收货单中有关联不能删除
            List<String> receiveIds = receiveOrderMapper.selectReceiveByCarrierCode(carrierCode);
            if (receiveIds != null && receiveIds.size() > 0) {
                errorMsg += "承运商"+carrierCode+"和收货单有关联,删除失败!\n";
                continue;

            }
            //出库单中如果有关联则不能删除
            List<String> baseIds = outBaseMapper.selectBaseByCarrierCode(carrierCode);
            if (baseIds != null && baseIds.size() > 0) {
                errorMsg += "承运商"+carrierCode+"和出库单有关联,删除失败!\n";
                continue;
            }

            //最后执行删除操作
            carrierMapper.deleteByPrimaryKey(carrierId);
        }

        return errorMsg;
    }



    private String CheckUniqueField(Carrier carrier, String id, String errorMsg) {
        String carrierCode = carrier.getCarrierCode();
        if (carrierCode != null) {
            String carrierId = carrierMapper.findIdByCarrierCode(carrierCode);
            if (carrierId != null && !id.equals(carrierId)) {
                errorMsg = "承运商编码不可重复!";
                return errorMsg;

            }
        }
        return errorMsg;
    }

    /**
     * 查询 c_carrier 表 中的 carrier_code
     *
     * @return
     */
    @Override
    public List<String> findCarrierCodes() {
        return carrierMapper.findCarrierCodes();
    }
}
