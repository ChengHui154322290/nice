package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.GooderAreaMapper;
import com.nice.good.dao.GooderConfigMapper;
import com.nice.good.dao.GooderTransportMapper;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.*;
import com.nice.good.service.GooderService;
import com.nice.good.service.ModuleRegionService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

;

/**
 * @Description: 货主档案
 * @Author: fqs
 * @Date: 2018/3/23 10:36
 * @Version: 1.0
 */
@RestController
@RequestMapping("/gooder")
public class GooderController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(GooderController.class);

    @Resource
    private GooderService gooderService;


    @Resource
    private GooderConfigMapper gooderConfigMapper;


    @Resource
    private GooderAreaMapper gooderAreaMapper;

    @Resource
    private GooderTransportMapper gooderTransportMapper;

    @Resource
    private ModuleRegionService moduleRegionService;


    @LogAnnotation(logType = "其他日志", content = "货主档案新增")
    @PostMapping("/add")
    public Result add(@RequestBody Gooder gooder, HttpServletRequest request) {
        if (gooder == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        try {

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);

            }
            String errorMsg = gooderService.gooderAdd(gooder, placeId, userId,request);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    @LogAnnotation(logType = "其他日志", content = "货主档案删除")
    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> gooderIds) {

        if (gooderIds == null || gooderIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {
            String errorMsg = gooderService.deleteByGooderId(gooderIds);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/list")
    public Result list(@RequestBody Gooder gooder, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(gooder.getClass());

        PageInfo pageInfo = null;
        try {


            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            condition.orderBy("id").desc();

            Criteria criteria = condition.createCriteria();

            List<Gooder> listnew = new ArrayList<>();

            List<Gooder> list = null;


            List<String> gooderCodes = getGooderCodes(request);


            if (gooderCodes != null && gooderCodes.size() > 0) {

                if (gooder != null) {
                    if (StringUtils.isNotBlank(gooder.getGooderCode())) {
                        criteria.andLike("gooderCode", "%" + gooder.getGooderCode() + "%");
                    }

                    if (StringUtils.isNotBlank(gooder.getGooderName())) {
                        criteria.andLike("gooderName", "%" + gooder.getGooderName() + "%");
                    }

                    if (StringUtils.isNotBlank(gooder.getCompany())) {
                        criteria.andLike("company", "%" + gooder.getCompany() + "%");
                    }

                    if (StringUtils.isNotBlank(gooder.getCountry())) {
                        criteria.andEqualTo("country", gooder.getCountry());
                    }

                    if (StringUtils.isNotBlank(gooder.getProvince())) {
                        criteria.andEqualTo("province", gooder.getProvince());
                    }

                    if (StringUtils.isNotBlank(gooder.getCity())) {
                        criteria.andEqualTo("city", gooder.getCity());
                    }
                    if (StringUtils.isNotBlank(gooder.getDistrict())) {
                        criteria.andEqualTo("district", gooder.getDistrict());
                    }

                }

                list = gooderService.findByCondition(condition);

                System.out.println("我是所有货主的信息:" + list.toString());



                //转化省市区字段

                if (list != null && list.size() > 0) {

                    for (Gooder goodern : list) {

                        //设置当前货主的货主权限

                        if (gooderCodes==null || gooderCodes.size()==0){
                            break;
                        }

                        if (!gooderCodes.contains(goodern.getGooderCode())){
                            continue;
                        }

                        List<String> lists = new ArrayList<>();
                        String province = goodern.getProvince();
                        String city = goodern.getCity();
                        String district = goodern.getDistrict();
                        lists.add(province);
                        lists.add(city);
                        lists.add(district);
                        List<String> area = moduleRegionService.findCname(lists);
                        int i = 1;
                        for (String cn_name : area) {
                            if (i == 1) {
                                goodern.setProvinceName(cn_name);
                                i++;
                            } else if (i == 2) {
                                goodern.setCityName(cn_name);
                                i++;
                            } else {
                                goodern.setDistrictName(cn_name);
                            }
                        }
                        listnew.add(goodern);
                    }
                }
            }

            pageInfo = new PageInfo(listnew);

            pageInfo.setPageSize(size);

            /**
             * lambda对日期进行排序
             */
//            Collections.sort(list, (Gooder o1, Gooder o2) -> o2.getCreatetime().compareTo(o1.getCreatetime()));

            /**
             * lambda对id进行排序
             */
//            Collections.sort(list, (Gooder o1, Gooder o2) -> o2.getId()-o1.getId());


//            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 修改查询
     */

    @PostMapping("/listAll")
    public Result listAll(@RequestBody Gooder gooder,HttpServletRequest request) {

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        if (gooder != null) {
            String gooderId = gooder.getGooderId();
            GooderConfig gooderConfigs = gooderConfigMapper.selectConfigByGooderIdAndPlaceId(gooderId,placeId);
            List<GooderArea> gooderAreas = gooderAreaMapper.selectAreaByGooderId(gooderId,placeId);
            List<GooderTransport> gooderTransports = gooderTransportMapper.selectTransByGooderId(gooderId);

            gooder.setGooderConfig(gooderConfigs);
            gooder.setGooderAreas(gooderAreas);
            gooder.setGooderTransports(gooderTransports);

            return ResultGenerator.genSuccessResult(gooder);
        }
        return ResultGenerator.genSuccessResult();
    }
}
