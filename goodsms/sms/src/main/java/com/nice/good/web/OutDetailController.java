package com.nice.good.web;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.GoodMapper;
import com.nice.good.dao.PackManageMapper;
import com.nice.good.model.*;
import com.nice.good.service.OutDetailService;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.service.SeatStockService;
import com.nice.good.service.StockService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;;

/**
* Created by CodeGenerator on 2018/04/11.
*/
@RestController
@RequestMapping("/out/detail")
public class OutDetailController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(OutDetailController.class);


    @Resource
    private OutDetailService outDetailService;


    @Resource
    private SeatStockService seatStockService;


    @Resource
    private GoodMapper goodMapper;

    @Resource
    private PackManageMapper packManageMapper;

    @PostMapping("/add")
    public Result add(@RequestBody OutDetail outDetail, HttpServletRequest request) {
    	if(outDetail == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);

    	try {

    		outDetailService.outDetailAdd(outDetail,userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete/{detailId}")
    public Result delete(@PathVariable(value = "detailId") String detailId) {
    	if(detailId == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

    	try {
    		outDetailService.deleteById(detailId);
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody OutDetail outDetail,HttpServletRequest request) {
    	if(outDetail == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}
    	if(outDetail.getId() == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

        String userId = getUserName(request);
    	try {

    		outDetailService.outDetailUpdate(outDetail,userId);

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }



    @PostMapping("/list")
    public Result list(@RequestBody OutDetail outDetail, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(outDetail.getClass());
        Criteria criteria = condition.createCriteria();

		PageInfo pageInfo = null;
		try {
    		 List<OutDetail> list = outDetailService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }



    /**
     * 选择产品  主要来源于库位库存表
     */
    @PostMapping("/chooseGood")
    public Result chooseGood(@RequestBody SeatStock seatStock/*, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size*/,HttpServletRequest request){

//        PageHelper.startPage(page, size);

        Condition condition = new Condition(seatStock.getClass());
        Criteria criteria = condition.createCriteria();


        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }


        if (StringUtils.isNotBlank(seatStock.getGooderCode())){
            criteria.andLike("gooderCode","%"+seatStock.getGooderCode()+"%");
        }

        if (StringUtils.isNotBlank(seatStock.getGoodName())){
            criteria.andLike("goodName","%"+seatStock.getGoodName()+"%");
        }

        if (StringUtils.isNotBlank(seatStock.getGoodCode())){
            criteria.andLike("goodCode","%"+seatStock.getGoodCode()+"%");

        }

        if (StringUtils.isNotBlank(seatStock.getOrgCode())){
            criteria.andLike("orgCode","%"+seatStock.getOrgCode()+"%");
        }

        if (StringUtils.isNotBlank(seatStock.getProviderCode())){
            criteria.andLike("providerCode","%"+seatStock.getProviderCode()+"%");
        }

        if (StringUtils.isNotBlank(seatStock.getSeatCode())) {
            criteria.andLike("seatCode", "%" + seatStock.getSeatCode() + "%");
        }

        criteria.andEqualTo("placeId",placeId);

        criteria.andGreaterThan("useNum",0);

        PageInfo pageInfo = null;
        try {
            List<SeatStock> list = seatStockService.findByCondition(condition);

            List<SeatStock> list1 = new ArrayList<>();
            for (SeatStock st:list){
                Good good = goodMapper.selectByGooderCodeAndGoodCode(st.getGooderCode(), st.getGoodCode());
                PackManage packManage = packManageMapper.selectByPackCode(good.getPackCode(),placeId);

                if (packManage!=null) {
                    st.setUnite(packManage.getMainUniteNite());
                }
                if (good!=null) {
                    st.setPackCode(good.getPackCode());
                    st.setGoodModel(good.getGoodModel());
                }

                list1.add(st);
            }

            pageInfo = new PageInfo(list1);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}",e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }




}
