package com.nice.good.web;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.*;
import com.nice.good.service.PdaReceiveGoodService;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.vo.PdaDetailVo;
import com.nice.good.vo.PdaListDetailVo;
import com.nice.good.vo.ReceiveDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

@Slf4j
@RestController
@RequestMapping("/pda/receive")
public class PdaReceiveGoodController extends BaseController {


    @Autowired
    private ReceiveOrderMapper receiveOrderMapper;


    @Autowired
    private ReceiveDetailMapper receiveDetailMapper;

    @Autowired
    private GoodPictureMapper goodPictureMapper;

    @Autowired
    private RfidLabelMapper rfidLabelMapper;


    @Autowired
    private PdaReceiveGoodService pdaReceiveGoodService;

    /**
     * 扫描收货单号,判断状态是否合法
     *
     * @return
     */
    @PostMapping("/scanReceiveCode")
    public Result scanReceiveCode(@RequestParam String receiveCode) {

        //检验发货单号是否存在
        String receiveId = receiveOrderMapper.selectReceiveIdByReceiveCode(receiveCode);
        if (receiveId==null){
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("收货单号不存在!");
        }

        //判断收货单号状态是否正确
        Integer status = receiveOrderMapper.selectStatusByReceiveCode(receiveCode);
        /**
         * 单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
         */
        //已收货,已上架,已结算的单据不能收货
        if (status == 2 || status == 6 || status == 7) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("单据状态不对!");
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 扫描收货单下收货明细
     *
     * @return
     */
    @PostMapping("/scanDetail")
    public Result scanDetail(@RequestBody PdaDetailVo pdaDetailVo) {

        //必须先扫描收货单
        String receiveCode = pdaDetailVo.getReceiveCode();
        if (StringUtils.isBlank(receiveCode)) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("请先扫描收货单号!");
        }
        //goodRfidCode可能是sku也可能是rfid

        String skuOrRfid = pdaDetailVo.getSkuOrRfid();

        if (StringUtils.isBlank(skuOrRfid)) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("SKU或RFID不能为空!");
        }
        //同一个收货单只有一个货主
        //获取货主信息
        String gooderCode = receiveOrderMapper.selectGooderCodeByReceiveCode(receiveCode);
        //判断该收货单下的明细状态是否合法
        String receiveId = receiveOrderMapper.selectReceiveIdByReceiveCode(receiveCode);

        List<ReceiveDetail> details = receiveDetailMapper.selectDetailByReceiveIdAndCode(receiveId, gooderCode, skuOrRfid);

        //skuOrRfid不是sku
        if (details == null || details.size() == 0) {

            //货品编码不存在,可能就是rfid编码
            RfidLabel rfidLabel = rfidLabelMapper.selectByPrimaryKey(skuOrRfid);
            if (rfidLabel == null) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("当前收货单下该商品不存在!");
            }


            String goodCode = rfidLabel.getGoodCode();
            details = receiveDetailMapper.selectDetailByReceiveIdAndCode(receiveId, gooderCode, goodCode);

            if (details == null || details.size() == 0) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("当前收货单下该商品不存在!");
            }

            //第 一次采集
            if (StringUtils.isBlank(pdaDetailVo.getGoodCode())) {
                return getResult(gooderCode, details, goodCode);
            }

            //同一款sku第 二次采集
            if (!goodCode.equals(pdaDetailVo.getGoodCode())) {
                pdaDetailVo.setSkuOrRfid(null);
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("货品SKU或RFID有误!").setData(pdaDetailVo);
            }

            pdaDetailVo.setReceiveNum(pdaDetailVo.getReceiveNum() + 1);
            pdaDetailVo.setSkuOrRfid(null);
            return ResultGenerator.genSuccessResult().setData(pdaDetailVo);

        } else {

            //goodRfidCode是sku 第 一次采集
            if (StringUtils.isBlank(pdaDetailVo.getGoodCode())) {
                return getResult(gooderCode, details, skuOrRfid);
            }

            //第 二次采集
            if (!skuOrRfid.equals(pdaDetailVo.getGoodCode())) {
                pdaDetailVo.setSkuOrRfid(null);
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("货品SKU或RFID有误!").setData(pdaDetailVo);
            }

            pdaDetailVo.setReceiveNum(pdaDetailVo.getReceiveNum() + 1);
            pdaDetailVo.setSkuOrRfid(null);
            return ResultGenerator.genSuccessResult().setData(pdaDetailVo);

        }

    }


    private Result getResult(String gooderCode, List<ReceiveDetail> details, String goodCode) {
        //只有未开始的明细才能收货
        ReceiveDetail receiveDetail = null;
        for (ReceiveDetail detail : details) {
            if (detail.getStatus() != 0) {
                continue;
            }
            receiveDetail = detail;
            break;
        }


        if (receiveDetail == null) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("当前sku单据状态不对!");
        }

        //显示页面信息
        PdaDetailVo pdaDetailVo = new PdaDetailVo();
        //供应商编码来自收货明细
        pdaDetailVo.setProviderCode(receiveDetail.getProviderCode());
        pdaDetailVo.setGooderCode(gooderCode);
        pdaDetailVo.setGoodCode(goodCode);
        pdaDetailVo.setReceiveNum(0);
        pdaDetailVo.setExpectNum(receiveDetail.getExpectNum());
        pdaDetailVo.setLpn(receiveDetail.getLpn());
        pdaDetailVo.setSeatCode(receiveDetail.getSeatCode());

        //收货单明细表id
        pdaDetailVo.setDetailId(receiveDetail.getDetailId());

        //查找图片,该显示哪一张图片 默认显示第 一张
        List<String> list = goodPictureMapper.selectImgsByGoodCode(gooderCode, goodCode);
        if (list != null && list.size() > 0) {
            pdaDetailVo.setImgUrl(list.get(0));
        }

        return ResultGenerator.genSuccessResult().setData(pdaDetailVo);
    }


    /**
     * 收货单提交
     *
     * @return
     */
    @PostMapping("/saveDetail")
    public Result saveDetail(PdaDetailVo pdaDetailVo, HttpServletRequest request) {


        //场地不能为空
        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        //当前单据状态确定
        if (pdaDetailVo == null) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("收货单不能为空!");
        }

        //接收量
        Integer receiveNum = pdaDetailVo.getReceiveNum();
        if (receiveNum == 0) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("收货量不能为0!");
        }

        String userId=getUserName(request);

        String errorMsg = null;
        try {
            errorMsg = pdaReceiveGoodService.receiveGood(pdaDetailVo,placeId,userId);
        } catch (Exception e) {

            log.error("收货异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        if (StringUtils.isNotBlank(errorMsg)){
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
        }


        return ResultGenerator.genSuccessResult();

    }


    @PostMapping("/listReceiveDetail")
    public Result listReceiveDetail(@RequestParam String receiveCode, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);

        PageInfo pageInfo = null;
        try {
            String receiveId = receiveOrderMapper.selectReceiveIdByReceiveCode(receiveCode);

            List<ReceiveDetail> details = receiveDetailMapper.selectReceiveDetailByReceiveId(receiveId);

            List<PdaListDetailVo> list = new ArrayList<>();
            for (ReceiveDetail detail:details){
                PdaListDetailVo detailVo= new PdaListDetailVo();
                detailVo.setGoodCode(detail.getGoodCode());
                detailVo.setGoodName(detail.getGoodName());
                detailVo.setGoodModel(detail.getGoodModel());
                detailVo.setExpectNum(detail.getExpectNum());
                detailVo.setReceiveNum(detail.getReceiveNum());
            }

            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


}
