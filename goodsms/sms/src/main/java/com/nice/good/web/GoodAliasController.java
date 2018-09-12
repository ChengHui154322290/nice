package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.GoodAlias;
import com.nice.good.service.GoodAliasService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

;

/**
 * @Description: 货品档案-货品别名
 * @Author: fqs
 * @Date: 2018/3/23 10:40
 * @Version: 1.0
 */
@RestController
@RequestMapping("/good/alias")
public class GoodAliasController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(GoodAliasController.class);


    @Resource
    private GoodAliasService goodAliasService;

    @PostMapping("/add")
    public Result add(@RequestBody GoodAlias goodAlias, HttpServletRequest request) {
        if (goodAlias == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);


        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            String errorMsg = goodAliasService.goodAliasAdd(goodAlias, userId);
            if (StringUtils.isNotBlank(errorMsg)) {
                ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 货品别名删除
     *
     * @param aliasId
     * @return
     */
    @PostMapping("/delete/{aliasId}")
    public Result delete(@PathVariable(value = "aliasId") String aliasId) {


        try {
            goodAliasService.deleteById(aliasId);
        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/list")
    public Result list(@RequestBody GoodAlias goodAlias, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(goodAlias.getClass());
        Criteria criteria = condition.createCriteria();

        PageInfo pageInfo = null;
        try {
            List<GoodAlias> list = goodAliasService.findByCondition(condition);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
