package com.nice.good.service.impl;


import com.nice.good.dao.RfidLabelMapper;
import com.nice.good.dto.RfidGatherDto;
import com.nice.good.model.Good;
import com.nice.good.model.RfidLabel;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.ReceiveDetailMapper;
import com.nice.good.model.ReceiveDetail;
import com.nice.good.service.ReceiveDetailService;
import com.nice.good.core.AbstractService;
import com.nice.good.vo.ChooseOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;


import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @Description: 收货单明细表操作
 * @Author: fqs
 * @Date: 2018/3/29 16:32
 * @Version: 1.0
 */
@Service
@Transactional
public class ReceiveDetailServiceImpl extends AbstractService<ReceiveDetail> implements ReceiveDetailService {
    @Resource
    private ReceiveDetailMapper receiveDetailMapper;

    @Resource
    private RfidLabelMapper rfidLabelMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void receiveDetailAdd(ReceiveDetail receiveDetail, String userId) throws Exception {

        receiveDetail.setDetailId(IdsUtils.getOrderId());
        receiveDetail.setCreateId(userId);
        receiveDetail.setModifyId(userId);
        receiveDetail.setCreateDate(TimeStampUtils.getTimeStamp());
        receiveDetail.setModifyDate(TimeStampUtils.getTimeStamp());

        receiveDetailMapper.insert(receiveDetail);

    }


    @Override
    public void receiveDetailUpdate(ReceiveDetail receiveDetail, String userId) {

        receiveDetail.setModifyId(userId);
        receiveDetail.setModifyDate(TimeStampUtils.getTimeStamp());
        receiveDetailMapper.updateByPrimaryKey(receiveDetail);
    }


    /**
     * 选单查询
     */
    @Override
    public List<ChooseOrderVo> chooseOrderVo(ChooseOrderVo chooseOrderVo) {

        return receiveDetailMapper.chooseOrderVo(chooseOrderVo);
    }


    /**
     * 收货时rfid采集保存
     *
     * @param rfidGatherDto
     * @param userId
     */
    @Override
    public String getRfidGather(RfidGatherDto rfidGatherDto, String userId) {

        String errorMsg = "";

        //采集量为0的跳过
        if (rfidGatherDto.getRfidGather() == 0) {
            errorMsg = "采集量不能为0,保存失败!";
            return errorMsg;
        }

        ReceiveDetail detail = receiveDetailMapper.selectByPrimaryKey(rfidGatherDto.getDetailId());
        //货主编码
        String gooderCode = rfidGatherDto.getGooderCode();
        //货品编码
        String goodCode = rfidGatherDto.getGoodCode();


        //从redis中获取rfid编码,向rfidGather表中保存编码和货品的绑定关系,一旦绑定,标签状态变为使用中
        //维护字段gooderCode, goodCode,labelCode,status

        //原有采集量
        Integer rfid = detail.getRfid();
        Set<String> rfidLabels = redisTemplate.opsForSet().members(gooderCode + ":" + goodCode);

        if (rfidLabels == null || rfidLabels.size() == 0) {
            errorMsg = "RFID标签编码为空,保存失败!";
            return errorMsg;
        }

        for (String rfidCode : rfidLabels) {
            if (rfidCode != null) {
                //已经采集过的要过滤掉
                RfidLabel lable = rfidLabelMapper.selectByPrimaryKey(rfidCode);
                if (lable != null) {
                    redisTemplate.delete(gooderCode + ":" + goodCode);
                    errorMsg = "当前标签使用中,保存失败!";
                    return errorMsg;
                }

            }
        }

        int gather = 0;
        for (String rfidCode : rfidLabels) {
            if (rfidCode != null) {
                //接收量
                int receiveNum = rfidGatherDto.getReceiveNum();

                if (receiveNum == gather + rfid) {
                    break;
                }

                RfidLabel rfidLabel = new RfidLabel();
                rfidLabel.setLabelCode(rfidCode);
                rfidLabel.setGooderCode(gooderCode);
                rfidLabel.setGoodCode(goodCode);
                //标签使用中
                rfidLabel.setStatus(1);
                rfidLabel.setCreateId(userId);
                rfidLabel.setCreateDate(TimeStampUtils.getTimeStamp());
                rfidLabel.setModifyId(userId);
                rfidLabel.setModifyDate(TimeStampUtils.getTimeStamp());
                rfidLabelMapper.insert(rfidLabel);
                gather++;

                //保存成功以后清除redis
                redisTemplate.delete(gooderCode + ":" + goodCode);

            }
        }

        //获取采集量同步收货明细
        if (gather > 0) {
            detail.setRfid(gather + rfid);
            detail.setModifyId(userId);
            detail.setModifyDate(TimeStampUtils.getTimeStamp());
            receiveDetailMapper.updateByPrimaryKey(detail);

        }
        return errorMsg;

    }

}

