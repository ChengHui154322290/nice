package com.nice.good.web;

import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.AdjustBillDetailMapper;
import com.nice.good.dao.MoveBillDetailMapper;
import com.nice.good.dao.SysUserMapper;
import com.nice.good.dao.UserSeatMapper;
import com.nice.good.model.AdjustBillDetail;
import com.nice.good.model.MoveBill;
import com.nice.good.model.MoveBillDetail;
import com.nice.good.model.StoreSeat;
import com.nice.good.service.AdjustBillDetailService;
import com.nice.good.service.MoveBillDetailService;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.service.MoveBillService;
import com.nice.good.service.StoreSeatService;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.vo.BillDetailVo;
import com.nice.good.vo.BillVo;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;;

/**
 * @Description: java类作用描述
 * @Author: fqs
 * @Date: 2018/8/10 16:12
 * @Version: 1.0
 */
@RestController
@RequestMapping("/move/bill/detail")
public class MoveBillDetailController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(MoveBillDetailController.class);


    @Resource
    private MoveBillDetailService moveBillDetailService;

    @Resource
    private MoveBillService moveBillService;

    @Resource
    private MoveBillDetailMapper moveBillDetailMapper;


    @Resource
    private StoreSeatService storeSeatService;


    @Resource
    private SysUserMapper sysUserMapper;


    @Resource
    private UserSeatMapper userSeatMapper;

    /**
     * 批量新增 移动明细单(k_move_bill_detail)     --  2018/06/08  17:56  rk
     *
     * @param billDetailVos
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestParam String moveBillId, @RequestBody List<BillDetailVo> billDetailVos, HttpServletRequest request) {
        if (billDetailVos == null || billDetailVos.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);
        try {

            if (StringUtils.isBlank(moveBillId)) {
                //单号不能为空
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("盘点单号不能为空!");
            }


            MoveBill moveBill = moveBillService.findById(moveBillId);

            if (moveBill.getMoveStatus() != 0) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("只有未开始的单据才能新增移动明细!");
            }



            if (billDetailVos == null || billDetailVos.size() == 0) {
                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("请选择要添加的明细单!");
            }

            List<String> list = new ArrayList<>();

            //现在新增和以前新增的记录都不能重复
            //1.原有的记录
            List<MoveBillDetail> moveBillDetails = moveBillDetailMapper.selectMoveBillDetailByMoveBillId(moveBillId);

            if (moveBillDetails!=null && moveBillDetails.size()>0){
                for (MoveBillDetail detail:moveBillDetails){

                    String gooderCode = detail.getGooderCode();
                    String goodCode = detail.getGoodCode();
                    String orgCode = detail.getOrgCode();
                    String providerCode = detail.getProviderCode();
                    String seatCode = detail.getSourceSeat();

                    String goodMessage = gooderCode + goodCode + orgCode + providerCode + seatCode;

                    list.add(goodMessage);
                }
            }


            //2.新增的记录
            for (BillDetailVo detail : billDetailVos) {

                String gooderCode = detail.getGooderCode();
                String goodCode = detail.getGoodCode();
                String orgCode = detail.getOrgCode();
                String providerCode = detail.getProviderCode();
                String seatCode = detail.getSeatCode();

                String goodMessage = gooderCode + goodCode + orgCode + providerCode + seatCode;

                if (list.contains(goodMessage)) {
                    return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("新增的明细中存在重复的记录，添加失败!");
                }

                list.add(goodMessage);


                if (StringUtils.isBlank(detail.getSeatCode())) {
                    return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("来源库位不能为空!");
                }

                //现有量
                Integer existingQuantity = detail.getNowNum();

                if (existingQuantity == null || StringUtils.isBlank(existingQuantity.toString())) {
                    return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("移动明细现有量不能为空!");

                }

                if (existingQuantity == 0) {
                    return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage("移动明细现有量不能为0!");
                }


            }


            // 循环新增 移动单明细 信息
            for (BillDetailVo billVo : billDetailVos) {
                MoveBillDetail detail = new MoveBillDetail();
                detail.setDetailId(IdsUtils.getOrderId());
                detail.setMoveBillId(moveBillId);
                detail.setStatus(0);
                //现有量
                detail.setExistingQuantity(billVo.getNowNum());
                detail.setCreateId(userId);
                Timestamp timeStamp = TimeStampUtils.getTimeStamp();
                detail.setCreateDate(timeStamp);
                detail.setModifyId(userId);
                detail.setModifyDate(timeStamp);
                detail.setSourceSeat(billVo.getSeatCode());
                BeanUtils.copyProperties(billVo, detail, "id");

                moveBillDetailService.save(detail);
            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据 List<T> detailIds( 移动明细表主键List<T>集合 ) 批量删除 k_move_bill_detail(移动明细单) 表中数据  -- 2018/06/09  09:58  rk
     *
     * @param detailIds
     * @return
     */

    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> detailIds, HttpServletRequest request) {
        if (detailIds == null || detailIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        String userId = getUserName(request);
        // 记录移动明细单删除信息
        String errorMsg = "";
        try {

            Boolean flag = false;
            String moveBillId = null;
            for (String detailId : detailIds) {

                MoveBillDetail detail = moveBillDetailService.findById(detailId);
                if (detail == null) {
                    continue;
                }

                if (detail.getStatus() != 0) {
                    errorMsg += "第" + detail.getId() + "条记录单据状态不对,删除失败!\n";
                    continue;
                }

                moveBillDetailService.deleteById(detailId);

                flag = true;
                moveBillId = detail.getMoveBillId();
            }


            if (flag) {

                MoveBill moveBill = moveBillService.findById(moveBillId);

                Integer status = getStatus(moveBillId);
                moveBill.setMoveStatus(status);
                moveBill.setModifyId(userId);
                moveBill.setModifyDate(TimeStampUtils.getTimeStamp());

                moveBillService.update(moveBill);

            }

        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        if (StringUtils.isNotBlank(errorMsg)) {
            return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
        }
        return ResultGenerator.genSuccessResult();
    }


    public Integer getStatus(String moveBillId) {

        List<Integer> status = moveBillDetailService.selectStatusByMoveBillId(moveBillId);
        if (status == null || status.size() == 0) {
            return 0;
        }

        if (status.size() > 1) {
            return 1;
        } else {
            return status.get(0);
        }

    }


    /**
     * 页面刷新
     *
     * @param moveBillId
     * @return
     */
    @PostMapping("/listDetail")
    public Result listDetail(@RequestParam String moveBillId) {
        MoveBill moveBill = moveBillService.findById(moveBillId);
        List<MoveBillDetail> moveBillDetails = moveBillDetailService.selectMoveBillDetailByMoveBillId(moveBillId);
        if (moveBillDetails != null && moveBillDetails.size() > 0) {
            moveBill.setMoveBillDetails(moveBillDetails);
        }
        return ResultGenerator.genSuccessResult(moveBill);
    }

    @PostMapping("/update")
    public Result update(@RequestBody MoveBillDetail moveBillDetail, HttpServletRequest request) {
        if (moveBillDetail == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        if (moveBillDetail.getId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }
        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            moveBillDetailService.moveBillDetailUpdate(moveBillDetail, userId);

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/list")
    public Result list(@RequestBody MoveBillDetail moveBillDetail, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(moveBillDetail.getClass());
        Criteria criteria = condition.createCriteria();

        PageInfo pageInfo = null;
        try {
            List<MoveBillDetail> list = moveBillDetailService.findByCondition(condition);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @PostMapping("/listMsg")
    public Result findMsg(@RequestBody BillDetailVo billDetailVo /*, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size*/, HttpServletRequest request) {

//        PageHelper.startPage(page, size);


        PageInfo pageInfo = null;

        try {

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            List<BillDetailVo> list2 = new ArrayList<>();
            List<String> gooderCodes = getGooderCodes(request);

            String errorMsg = "";

            //如果当前用户是运营，并且是归还单据，则来源库位为运营的冻结库位
            String userName = getUserName(request);

            String userType = sysUserMapper.selecUserTypeByUserName(userName);

            if ("运营".equals(userType)) {

                Integer moveType = billDetailVo.getMoveType();
                //0-正常移动 ,1借领,2归还

                if (moveType == null) {

                    errorMsg = "移动类型不能为空！";

                    return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
                }

                if ("1".equals(moveType)) {

                    errorMsg = "当前移动类型选择有误！（当前用户为运营，移动类型只能选择‘借领’或‘归还’。）";

                    return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
                }

                if ("2".equals(moveType)) {
                    //归还操作，库位为当前用户的冻结库位
                    if (gooderCodes != null && gooderCodes.size() > 0) {

                        List<BillDetailVo> list = moveBillDetailMapper.findGoalMsg(billDetailVo.getGooderCode(), billDetailVo.getGoodCode(), billDetailVo.getAreaCode(), billDetailVo.getGoodModel(), billDetailVo.getSeatCode(), billDetailVo.getGoodName(), placeId);
                        if (list != null && list.size() > 0) {
                            for (BillDetailVo vo : list) {
                                if (gooderCodes.contains(vo.getGooderCode())) {
                                    list2.add(vo);
                                }
                            }
                        }
                    }


                }else {

                    queryDetail(billDetailVo, placeId, list2, gooderCodes);

                }


            } else {

                queryDetail(billDetailVo, placeId, list2, gooderCodes);
            }

            pageInfo = new PageInfo(list2);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    private void queryDetail(@RequestBody BillDetailVo billDetailVo, String placeId, List<BillDetailVo> list2, List<String> gooderCodes) {
        if (gooderCodes != null && gooderCodes.size() > 0) {

            List<BillDetailVo> list = moveBillDetailMapper.findMsg(billDetailVo.getGooderCode(), billDetailVo.getGoodCode(), billDetailVo.getAreaCode(), billDetailVo.getGoodModel(), billDetailVo.getSeatCode(), billDetailVo.getGoodName(), placeId);
            if (list != null && list.size() > 0) {
                for (BillDetailVo vo : list) {
                    if (gooderCodes.contains(vo.getGooderCode())) {
                        list2.add(vo);
                    }
                }
            }
        }
    }

    /**
     * 移动时选择库位
     */
    @PostMapping("/chooseMoveSeat")
    public Result chooseMoveSeat(@RequestBody StoreSeat storeSeat,/*, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size*/ HttpServletRequest request) {
//        PageHelper.startPage(page, size);

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        Condition condition = new Condition(storeSeat.getClass());
        Criteria criteria = condition.createCriteria();

        criteria.andEqualTo("placeId", placeId);

        String errorMsg = "";


        //判断当前用户是否是运营，运营账号移动类型只有借领和归还的类型

        String userName = getUserName(request);

        String userType = sysUserMapper.selecUserTypeByUserName(userName);

        if ("运营".equals(userType)) {

            Integer moveType = storeSeat.getMoveType();
            //0-正常移动 ,1借领,2归还

            if (moveType == null) {

                errorMsg = "移动类型不能为空！";

                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
            }

            if ("1".equals(moveType)) {

                errorMsg = "当前移动类型选择有误！（当前用户为运营，移动类型只能选择‘借领’或‘归还’。）";

                return ResultGenerator.genFailResult(ResultCode.FAIL).setMessage(errorMsg);
            }

            //如果是借领，目标库位为当前运营冻结库位
            String seatCode = userSeatMapper.selectSeatCodeByUserName(userName, placeId);

            criteria.andEqualTo("seatCode", seatCode);
            criteria.andEqualTo("seatType", "冻结");

        } else {


            if (StringUtils.isNotBlank(storeSeat.getAreaCode())) {
                criteria.andLike("areaCode", "%" + storeSeat.getAreaCode() + "%");
            }
            if (StringUtils.isNotBlank(storeSeat.getSeatCode())) {
                criteria.andLike("seatCode", "%" + storeSeat.getSeatCode() + "%");
            }
        }


        List<StoreSeat> list;
        try {
            list = storeSeatService.findByCondition(condition);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult().setData(list);
    }


}
