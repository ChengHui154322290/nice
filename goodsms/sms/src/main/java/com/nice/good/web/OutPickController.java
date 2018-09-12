package com.nice.good.web;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.OutPick;
import com.nice.good.model.StoreSeat;
import com.nice.good.service.OutPickService;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.service.StoreSeatService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.apache.commons.lang3.StringUtils;;

/**
* Created by CodeGenerator on 2018/04/11.
*/
@RestController
@RequestMapping("/out/pick")
public class OutPickController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(OutPickController.class);


    @Resource
    private OutPickService outPickService;

    @Resource
    private StoreSeatService storeSeatService;

    @PostMapping("/add")
    public Result add(@RequestBody OutPick outPick, HttpServletRequest request) {
        if (outPick == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            outPickService.outPickAdd(outPick, userId);

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete/{pickId}")
    public Result delete(@PathVariable(value = "pickId") String pickId) {
        if (pickId == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        try {
            outPickService.deleteById(pickId);
        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody OutPick outPick, HttpServletRequest request) {
        if (outPick == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        if (outPick.getId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }


        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            outPickService.outPickUpdate(outPick, userId);

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        if (id == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        OutPick outPick = null;
        try {
            outPick = outPickService.findById(id);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult(outPick);
    }

    @PostMapping("/list")
    public Result list(@RequestBody OutPick outPick, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(outPick.getClass());
        Criteria criteria = condition.createCriteria();

        PageInfo pageInfo = null;
        try {
            List<OutPick> list = outPickService.findByCondition(condition);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @PostMapping("/chooseSeat")
    public Result chooseTempSeat(@RequestBody StoreSeat storeSeat, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {
        PageHelper.startPage(page, size);

        Condition condition = new Condition(storeSeat.getClass());
        Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotBlank(storeSeat.getSeatCode())) {
            criteria.andLike("areaCode", "%" + storeSeat.getSeatCode() + "%");
        }
        if (StringUtils.isNotBlank(storeSeat.getSeatCode())) {
            criteria.andLike("seatCode", "%" + storeSeat.getSeatCode() + "%");
        }


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
     * 出库时，rfid采集解绑
     */
}
