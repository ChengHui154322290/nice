package com.nice.good.web;


import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.dto.TaskDto;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.*;
import com.nice.good.service.PdaOutPickService;
import com.nice.good.vo.PickNumVo;
import com.nice.good.vo.SendCodeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pda/outPick")
public class PdaOutPickController extends BaseController {


    @Autowired
    private OutBaseMapper outBaseMapper;


    @Autowired
    private OutTaskMapper outTaskMapper;


    @Autowired
    private OutDetailMapper outDetailMapper;


    @Autowired
    private GoodPictureMapper goodPictureMapper;


    @Autowired
    private OutPickMapper outPickMapper;


    @Autowired
    private RfidLabelMapper rfidLabelMapper;

    @Autowired
    private PdaOutPickService pdaOutPickService;


    /**
     * 扫描发货单号,并判断状态是否合法
     */
    @PostMapping("/scanSendCode")
    public Result scanSendCode(@RequestParam String sendCode) {

        //判断发货单号状态是否正确
        // 单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货,13已取消

        //未开始,已发货,已取消的单据不能拣货
        OutBase base = outBaseMapper.selectBySenCode(sendCode);
        Integer orderStatus = base.getOrderStatus();
        if (orderStatus == 0 || orderStatus == 12 || orderStatus == 13) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("单据状态不对!");
        }

        List<OutDetail> outDetails = outDetailMapper.selectByBaseId(base.getBaseId());

        if (outDetails == null || outDetails.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("当前单据暂无出库明细单!!");
        }


        OutTask task = null;
        label:
        for (OutDetail detail : outDetails) {
            Integer status = detail.getStatus();
            //只有部分分配,已分配,部分拣货的单据才能拣货操作
            if (status != 1 && status != 2 && status != 3) {
                continue;
            }
            List<OutTask> outTasks = outTaskMapper.selectAllByDetailId(detail.getDetailId());
            if (outTasks == null || outTasks.size() == 0) {
                continue;
            }

            for (OutTask outTask : outTasks) {
                //只有未开始和操作中的单据才能拣货
                if (outTask == null) {
                    continue;
                }

                //状态 0 未开始,1操作中,2已完成
                if (outTask.getStatus() != 0 && outTask.getStatus() != 1) {
                    continue;
                }

                task = outTask;

                break label;

            }
        }

        if (task == null) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("当前单据暂无拣货任务!");
        }

        //已拣货量
        OutPick outPick = outPickMapper.selectByPrimaryKey(task.getId());
        Integer rfid = outPick.getRfid();
        if (rfid == null) {
            rfid = 0;
        }

        task.setRfid(rfid);

        //图片
        List<String> imgIds = goodPictureMapper.selectImgsByGoodCode(task.getGooderCode(), task.getGoodCode());
        if (imgIds != null && imgIds.size() > 0) {
            task.setImgId(imgIds.get(0));
        }


        return ResultGenerator.genSuccessResult().setData(task);
    }


//    /**
//     * 领取任务
//     */
//    @PostMapping("/getTask")
//    public Result getTask(@RequestBody OutTask task) {
//
//        if (task == null) {
//            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
//        }
//
//        String lpn = task.getLpn();
//        if (StringUtils.isBlank(lpn)) {
//            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("拣货车号不能为空!");
//        }
//
//        //发货单,货主,供应商,库位,lpn,sku拣货量,sku或者rfid,图片
//        TaskDto taskDto = new TaskDto();
//        taskDto.setSendCode(task.getSendCode());
//        taskDto.setDetailId(task.getDetailId());
//        taskDto.setGooderCode(task.getGooderCode());
//        taskDto.setGoodCode(task.getGoodCode());
//        taskDto.setLpn(task.getLpn());
//        taskDto.setSeatCode(task.getSeatCode());
//        taskDto.setPickNum(task.getPickNum());
//        taskDto.setId(task.getId());
//
//        //已拣货量
//        OutPick outPick = outPickMapper.selectByPrimaryKey(task.getId());
//        Integer rfid = outPick.getRfid();
//        if (rfid == null) {
//            rfid = 0;
//        }
//
//        taskDto.setRfid(rfid);
//
//        //图片
//        List<String> imgIds = goodPictureMapper.selectImgsByGoodCode(task.getGooderCode(), task.getGoodCode());
//        if (imgIds != null && imgIds.size() > 0) {
//            taskDto.setImgId(imgIds.get(0));
//        }
//
//        //落放库位
//        String goalSeat = task.getGoalSeat();
//        taskDto.setGoalSeat(goalSeat);
//
//        return ResultGenerator.genSuccessResult().setData(taskDto);
//    }

    /**
     * 拣货数量累加
     */
    @PostMapping("/getPickNum")
    public Result getPickNum(@RequestBody PickNumVo pickNumVo) {
        /**
         * rfidOrCode可能是rfid标签,也可能是货品编码
         */
        String skuOrRfid = pickNumVo.getSkuOrRfid();

        if (StringUtils.isBlank(skuOrRfid)) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("SKU或RFID不能为空!");
        }

        String gooderCode = pickNumVo.getGooderCode();
        String goodCode = pickNumVo.getGoodCode();

        //采集量
        Integer rfid = pickNumVo.getRfid();

        //先判断是否是rfid
        RfidLabel rfidLabel = rfidLabelMapper.selectByPrimaryKey(skuOrRfid);
        if (rfidLabel != null) {
            String gooderCode1 = rfidLabel.getGooderCode();
            String goodCode1 = rfidLabel.getGoodCode();

            if (!gooderCode.equals(gooderCode1) || !goodCode.equals(goodCode1)) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("当前rfid采集有误!");
            }
        } else {

            if (!goodCode.equals(skuOrRfid)) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("当前sku采集有误!");
            }
        }

        return ResultGenerator.genSuccessResult().setData(rfid + 1);

    }


    /**
     * 提交保存
     */
    @PostMapping("/submitSave")
    public Result submitSave(@RequestBody TaskDto taskDto, HttpServletRequest request) {



        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        //采集量
        Integer rfid = taskDto.getRfid();
        if (rfid == null || StringUtils.isBlank(rfid.toString())) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("采集量不能为空!");
        }

        if (rfid == 0) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("采集量不能为0!");
        }

        String userId = getUserName(request);

        String message = pdaOutPickService.submitSave(taskDto,placeId,userId);
        if (StringUtils.isNotBlank(message)){
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(message);
        }

        return ResultGenerator.genSuccessResult();
    }


}
