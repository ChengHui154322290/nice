package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.GoodConfig;
import com.nice.good.model.StoreSeat;
import com.nice.good.model.SysUser;
import com.nice.good.service.GoodConfigService;
import com.nice.good.service.StoreSeatService;
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
 * @Description:   货品档案-配置
 * @Author:   fqs
 * @Date:  2018/3/23 10:38
 * @Version:   1.0
 */
@RestController
@RequestMapping("/good/config")
public class GoodConfigController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(GoodConfigController.class);


    @Resource
    private GoodConfigService goodConfigService;

    @Resource
    private StoreSeatService storeSeatService;

    @PostMapping("/add")
    public Result add(@RequestBody GoodConfig goodConfig,HttpServletRequest request) {
    	if(goodConfig == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

		String userId = getUserName(request);

    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		goodConfigService.goodConfigAdd(goodConfig,userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete/{configId}")
    public Result delete(@PathVariable(value = "configId") String configId) {
    	if(configId == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

    	try {
    		goodConfigService.deleteById(configId);
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody GoodConfig goodConfig,HttpServletRequest request) {
    	if(goodConfig == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}
    	if(goodConfig.getId() == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}


        String userId= getUserName(request);

    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		goodConfigService.goodConfigUpdate(goodConfig, userId);

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
    	if(id == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

    	GoodConfig goodConfig = null;
    	try {
    		goodConfig = goodConfigService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult(goodConfig);
    }

    @PostMapping("/list")
    public Result list(@RequestBody GoodConfig goodConfig, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(goodConfig.getClass());
        Criteria criteria = condition.createCriteria();

		PageInfo pageInfo = null;
		try {
    		 List<GoodConfig> list = goodConfigService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 进货质检库位,退货接收,出库待选库位
     */

    @PostMapping("/chooseSeat")
    public Result chooseTempSeat(@RequestBody StoreSeat storeSeat, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,HttpServletRequest request) {
        PageHelper.startPage(page, size);


        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        Condition condition = new Condition(storeSeat.getClass());
        Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotBlank(storeSeat.getAreaCode())) {
            criteria.andLike("areaCode","%" +storeSeat.getAreaCode()+"%");
        }
        if (StringUtils.isNotBlank(storeSeat.getSeatCode())) {
            criteria.andLike("seatCode", "%"+storeSeat.getSeatCode()+"%");
        }
        criteria.andEqualTo("seatType","暂存");

        criteria.andEqualTo("seatTag","无");

        criteria.andEqualTo("placeId",placeId);


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
     * 次品接收库位选择
     */
    @PostMapping("/chooseBadSeat")
    public Result chooseBadSeat(@RequestBody StoreSeat storeSeat, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {
        PageHelper.startPage(page, size);

        Condition condition = new Condition(storeSeat.getClass());
        Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotBlank(storeSeat.getAreaCode())) {
            criteria.andLike("areaCode","%" +storeSeat.getAreaCode()+"%");
        }
        if (StringUtils.isNotBlank(storeSeat.getSeatCode())) {
            criteria.andLike("seatCode", "%"+storeSeat.getSeatCode()+"%");
        }

        //上架正品库位
        criteria.andEqualTo("seatTag","破损");

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



}
