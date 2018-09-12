package com.nice.good.service.impl;

import com.nice.good.dao.*;
import com.nice.good.dto.TaskDto;
import com.nice.good.model.OutDetail;
import com.nice.good.model.OutPick;
import com.nice.good.model.OutTask;
import com.nice.good.service.PdaOutPickService;
import com.nice.good.utils.TimeStampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.TreeSet;


@Service
public class PdaOutPickServiceImpl implements PdaOutPickService {


    @Autowired
    private OutPickMapper outPickMapper;

    @Autowired
    private OutTaskMapper outTaskMapper;


    @Autowired
    private OutDetailMapper outDetailMapper;


    @Autowired
    private SeatStockMapper seatStockMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private OutBaseMapper outBaseMapper;

    @Override
    @Transactional
    public String submitSave(TaskDto taskDto,String placeId,String userId) {

        String errorMsg = "";
        //采集量
        Integer rfid = taskDto.getRfid();

        //原有采集量
        Integer id = taskDto.getId();
        OutPick outPick = outPickMapper.selectByPrimaryKey(id);

        Integer rfid1 = outPick.getRfid();

        if (rfid < rfid1) {
            errorMsg = "当前任务采集数据有误!";
            return errorMsg;
        }
        if (rfid.equals(rfid1)) {
            return errorMsg;
        }
        //目标拣货量
        Integer pickNum = taskDto.getPickNum();
        if (rfid > pickNum) {
            errorMsg = "当前任务采集数据有误(采集量不能大于目标量)!";
            return errorMsg;
        }

        OutTask outTask = outTaskMapper.selectByPrimaryKey(id);

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

        //同步拣货任务
        if (rfid > 0 && rfid < pickNum) {
            //操作中
            outTask.setStatus(1);
        }
        if (rfid > 0 && rfid.equals(pickNum)) {
            //已完成
            outTask.setStatus(2);
        }
        outTask.setRemark(taskDto.getRemark());
        outTask.setModifyTime(timeStamp);

        outTaskMapper.updateByPrimaryKey(outTask);

        //同步拣货明细
        outPick.setRfid(rfid);
        outPick.setReason(taskDto.getRemark());
        outPickMapper.updateByPrimaryKey(outPick);

        //同步出库明细单
        Integer rfid2 = outPickMapper.selectCountRfidByDetailId(outPick.getDetailId());

        OutDetail detail = outDetailMapper.selectByPrimaryKey(outPick.getDetailId());

        //拣货量
        detail.setPickNum(rfid2);
        //状态确定

        // 单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货,13已取消
        //分配量
        Integer allotNum = detail.getAllotNum();
        int status = 0;
        if (rfid2 > 0 && rfid2 < allotNum) {
            //部分拣货
            status=3;
        }

        if (rfid2 > 0 && rfid2.equals(allotNum)) {
            //部分拣货
            status=4;
        }

        detail.setStatus(status);
        detail.setModifyId(userId);
        outDetailMapper.updateByPrimaryKey(detail);

        //同步库存
        //新增的拣货量为
        int newPickNum=rfid-rfid1;
        //目标库位
        String goalSeat = taskDto.getGoalSeat();
        //货主编码
        String gooderCode = taskDto.getGooderCode();
        //货品编码
        String goodCode = taskDto.getGoodCode();

        String orgCode = taskDto.getOrgCode();

        String providerCode = taskDto.getProviderCode();

        //目标库位库存上拣货量累加
        seatStockMapper.updatePickNum(gooderCode,goodCode,goalSeat,newPickNum,orgCode,providerCode,placeId);
        //同步总库存拣货量
        stockMapper.updatePickNum(gooderCode,goodCode,newPickNum,orgCode,providerCode,placeId);

        //同步出库单状态
        String baseId = detail.getBaseId();
        Integer baseStatus = getOutBaseStatus(baseId);

        outBaseMapper.updateStatus(baseId,baseStatus);

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
}
