package com.nice.good.web;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.constant.REDIS_KEY;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.dto.PurchaseOrderBean;
import com.nice.good.dto.RfidGatherDto;
import com.nice.good.dto.ShelfSeatDto;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.*;
import com.nice.good.service.*;
import com.nice.good.vo.ChooseOrderVo;
import com.nice.good.vo.GoodDetailVo;
import com.nice.good.vo.GoodVo;
import com.nice.good.vo.PurchaseDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * Created by CodeGenerator on 2018/03/28.
 */
@RestController
@RequestMapping("/receive/detail")
public class ReceiveDetailController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(ReceiveDetailController.class);


    @Resource
    private ReceiveDetailService receiveDetailService;

    @Resource
    private OrderGoodMappingMapper orderGoodMappingMapper;


    @Resource
    private StoreSeatService storeSeatService;

    @Resource
    private GooderService gooderService;

    @Resource
    private ProviderService providerService;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private GoodMapper goodMapper;


    @Resource
    private RfidLabelMapper rfidLabelMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Resource
    private GoodPictureMapper goodPictureMapper;


    @PostMapping("/add")
    public Result add(@RequestBody ReceiveDetail receiveDetail, HttpServletRequest request) {
        if (receiveDetail == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);


        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            receiveDetailService.receiveDetailAdd(receiveDetail, userId);

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/update")
    public Result update(@RequestBody ReceiveDetail receiveDetail, HttpServletRequest request) {
        if (receiveDetail == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        if (receiveDetail.getId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }


        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            receiveDetailService.receiveDetailUpdate(receiveDetail, userId);

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    /**
     * 选择产品查询
     */
    @PostMapping("/chooseGood")
    public Result listGood(@RequestBody Good good/*, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size*/) {

//        PageHelper.startPage(page, size);
//
//        PageInfo pageInfo = null;

        String gooderCode = null;
        String commodityCode = null;
        String goodCode = null;
        String goodName = null;
        String goodModel = null;

        Map<String, String> goodMap = new HashMap<>();

        if (good != null) {
            gooderCode = good.getGooderCode();
            commodityCode = good.getCommodityCode();
            goodCode = good.getGoodCode();
            goodName = good.getGoodName();
            goodModel = good.getGoodModel();

        }
        goodMap.put("gooderCode", gooderCode);
        if (gooderCode == null) {
            return ResultGenerator.genSuccessResult();
        }
        goodMap.put("commodityCode", commodityCode);
        goodMap.put("goodCode", goodCode);
        goodMap.put("goodName", goodName);
        goodMap.put("goodModel", goodModel);

        List<GoodVo> list;
        try {

            list = goodMapper.findByGoodCondition(goodMap);

//            pageInfo = new PageInfo(list);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult().setData(list);

    }


    /**
     * 收货界面选单 主表
     */
    @PostMapping("/choosePurchase")
    public Result choosePurchase(@RequestBody ChooseOrderVo chooseOrderVo, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);

        PageInfo pageInfo = null;
        try {
            List<ChooseOrderVo> list = receiveDetailService.chooseOrderVo(chooseOrderVo);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 选单时  采购明细单查询 选单时自动过滤掉不合法的采购单,例如未开始(采购单必须已确认),已结算的订单
     */
    @PostMapping("/chooseDetail")
    public Result chooseProduct(@RequestBody ChooseOrderVo chooseOrderVo, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);

        String orderId = chooseOrderVo.getOrderId();

        List<PurchaseDetailVo> list = orderGoodMappingMapper.selectDetailByOrderId(orderId);


        //补充字段
        //货主编码
        Map<Integer, PurchaseDetailVo> map = new HashMap<>();
        for (PurchaseDetailVo vo : list) {
            Integer id = vo.getId();
            if (!map.containsKey(id)) {
                map.put(id, vo);
                continue;
            }
            PurchaseDetailVo detailVo = new PurchaseDetailVo();
            detailVo.setId(id);
            detailVo.setGoodCode(vo.getGoodCode());
            detailVo.setGoodName(vo.getGoodName());
            detailVo.setGoodModel(vo.getGoodModel());
            detailVo.setCommodityCode(vo.getCommodityCode());
            detailVo.setPurchaseCode(vo.getPurchaseCode());
            detailVo.setPurchaseNumber(vo.getPurchaseNumber());
            detailVo.setIsQuality(vo.getIsQuality());
            detailVo.setrWeight(vo.getrWeight());
            detailVo.setBulk(vo.getBulk());
            detailVo.setReceiveNum(vo.getReceiveNum() + map.get(id).getReceiveNum());
            map.put(id, detailVo);

        }

        List<PurchaseDetailVo> list2 = new ArrayList<>();

        for (Integer detailId : map.keySet()) {

            PurchaseDetailVo detailVo = map.get(detailId);
            int noArriveNum = detailVo.getPurchaseNumber() - detailVo.getReceiveNum();
            if (noArriveNum == 0) {
                continue;
            }
            detailVo.setNoArriveNum(noArriveNum);
            list2.add(detailVo);
        }

        PageInfo pageInfo = new PageInfo(list2);

        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 收货时选择暂存库位
     */

    @PostMapping("/chooseTempSeat")
    public Result chooseTempSeat(@RequestBody StoreSeat storeSeat /*, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size*/, HttpServletRequest request) {
//        PageHelper.startPage(page, size);

        String placeId = getPlaceId(request);

        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        Condition condition = new Condition(storeSeat.getClass());
        Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotBlank(storeSeat.getAreaCode())) {
            criteria.andLike("areaCode", "%" + storeSeat.getAreaCode() + "%");
        }
        if (StringUtils.isNotBlank(storeSeat.getSeatCode())) {
            criteria.andLike("seatCode", "%" + storeSeat.getSeatCode() + "%");
        }

        //收货时,选择暂存库位
        criteria.andEqualTo("seatType", "暂存");

        criteria.andEqualTo("seatTag","无");

        criteria.andEqualTo("placeId",placeId);

//        PageInfo pageInfo = null;

        List<StoreSeat> list;
        try {
            list = storeSeatService.findByCondition(condition);
//            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult().setData(list);
    }


    /**
     * 上架时选择库位
     */
    @PostMapping("/chooseShelfSeat")
    public Result chooseGoodSeat(@RequestBody ShelfSeatDto seatDto/*@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size*/,HttpServletRequest request) {
//        PageHelper.startPage(page, size);

        String placeId = getPlaceId(request);

        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        StoreSeat storeSeat = new StoreSeat();

        Condition condition = new Condition(storeSeat.getClass());
        Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotBlank(seatDto.getAreaCode())) {
            criteria.andLike("areaCode", "%" + seatDto.getAreaCode() + "%");
        }
        if (StringUtils.isNotBlank(seatDto.getSeatCode())) {
            criteria.andLike("seatCode", "%" + seatDto.getSeatCode() + "%");
        }

        if (seatDto.getGoodSign() == 2) {
            //上架正品库位
            criteria.andEqualTo("seatTag", "无");
            criteria.andEqualTo("seatType", "直播间");
        }


        if (seatDto.getGoodSign() == 3) {

            //上架次品库位
            criteria.andEqualTo("seatTag", "冻结");
        }

        criteria.andEqualTo("placeId",placeId);

        condition.orderBy("seatName").asc();



        PageInfo pageInfo = null;
        try {
            List<StoreSeat> list = storeSeatService.findByCondition(condition);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 把数据库中的已有标签信息添加到redis
     */
    @PostMapping("/addRfidToRedis")
    public void addRfidToRedis() {
        List<String> list = rfidLabelMapper.selectAllLabelCode();
        redisTemplate.opsForList().rightPushAll(REDIS_KEY.MYSQL_LABEL_CODE, list);

    }


    /**
     * 批量采集时,rfid合法性校验,只有收货中,已收货,质检中和已质检的才能进行rfid采集
     */
    @LogAnnotation(logType = "收货单日志", content = "收货单rfid采集")
    @PostMapping("/checkRfid")
    public Result checkRfid(@RequestBody List<ReceiveDetail> receiveDetails) {

        if (receiveDetails == null || receiveDetails.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        // 收货单状态  0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算

        List<RfidGatherDto> list = new ArrayList<>();

        for (ReceiveDetail detail : receiveDetails) {
            if (detail != null) {
                Integer status = detail.getStatus();
                if (status < 1 || status > 4) {
                    return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage("第 " + detail.getId() + "条记录状态不对,操作不成功!");
                }
                //已经采集完的货品不能重复采集
                Integer rfid = detail.getRfid();
                if (rfid != 0 && rfid.equals(detail.getReceiveNum())) {
                    return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage("第 " + detail.getId() + "条记录已经采集完毕,操作不成功!");
                }
            }
        }

        for (ReceiveDetail detail : receiveDetails) {
            if (detail != null) {
                RfidGatherDto dto = new RfidGatherDto();
                dto.setDetailId(detail.getDetailId());
                dto.setGooderCode(detail.getGooderCode());
                dto.setGoodCode(detail.getGoodCode());
                dto.setGoodModel(detail.getGoodModel());
                dto.setGoodName(detail.getGoodName());
                dto.setStatus(detail.getStatus());
                dto.setReceiveNum(detail.getReceiveNum());
                //采集量
                dto.setRfidGather(detail.getRfid());
                list.add(dto);
            }
        }


        return ResultGenerator.genSuccessResult().setData(list);

    }


    /**
     * 收货时， rfid采集保存
     */
    @PostMapping("/getRfidGather")
    public Result getRfidGather(@RequestBody RfidGatherDto rfidGatherDto, HttpServletRequest request) {

        if (rfidGatherDto == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);


        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {
            //标签和货品编码绑定操作
            String errorMsg = receiveDetailService.getRfidGather(rfidGatherDto, userId);

            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();

    }


    /**
     * 清除rfid标签
     */
    @PostMapping("/clearRfidLabel")
    public Result clearRfidLabel() {

        String value = redisTemplate.opsForValue().get("goods:elect");

        if (StringUtils.isNotBlank(value)) {

            redisTemplate.delete(value);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 采购收货--收货单--选单  rk 2018/05/03
     */
    @PostMapping("/listReceiveDetailOrder")
    public Result listReceiveDetailOrder() {

        PurchaseOrderBean purchaseOrderBean = null;

        try {

            // 封装 货主编码 gooderCodes
            List<String> gooderCodes = gooderService.findGooderCodes();

            // 封装 供应商编码 providerCodes
            List<String> providerCodes = providerService.findProviderCodes();

            // 封装 组织机构编码 orgCodes
            List<String> orgCodes = organizationService.findOrgCodes();

            purchaseOrderBean = new PurchaseOrderBean(gooderCodes, providerCodes, orgCodes);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
        }


        return ResultGenerator.genSuccessResult(purchaseOrderBean);
    }


    /**
     * 查看货品信息
     */
    @PostMapping("/lookGoodDetail")
    public Result lookGoodDetail(@RequestParam String gooderCode, @RequestParam String goodCode) {

        GoodDetailVo goodDetailVo = goodMapper.selectGoodDetail(gooderCode, goodCode);

        if (goodDetailVo != null) {
            //所有图片id封装
            List<String> imgIds = goodPictureMapper.selectImgsByGoodCode(gooderCode, goodCode);
            goodDetailVo.setImgIds(imgIds);
        }


        return ResultGenerator.genSuccessResult(goodDetailVo);
    }


}
