package com.nice.good.web;

import java.util.Date;

import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.SysPlaceMapper;
import com.nice.good.dao.UserPlaceMapper;
import com.nice.good.model.SysPlace;
import com.nice.good.model.UserPlace;
import com.nice.good.service.ModuleRegionService;
import com.nice.good.service.SysPlaceService;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.utils.IdsUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/04/16.
 */
@RestController
@RequestMapping("/sys/place")
public class SysPlaceController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(SysPlaceController.class);

    @Resource
    private SysPlaceService sysPlaceService;
    @Resource
    private ModuleRegionService moduleRegionService;

    @Resource
    private SysPlaceMapper sysPlaceMapper;


    @LogAnnotation(logType = "其他日志", content = "新增场地/展厅")
    @PostMapping("/add")
    public Result add(@RequestBody SysPlace sysPlace, HttpServletRequest request) {

        if (sysPlace == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);


        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }

        try {

            if (sysPlace.getPlaceId() == null) {

                //新增操作
                sysPlace.setFlag(0);

                // 检查sysUser.placeNumber(场地编号)是否重复
                String placeNumber = sysPlaceService.checkSysPlaceNumber(sysPlace.getPlaceNumber());
                if (placeNumber != null) {
                    return ResultGenerator.genFailResult(ResultCode.PLACENUMBER_IS_REPEAT);
                }

                //场地名称不能为空
                String exhibition = sysPlace.getExhibition();
                if (StringUtils.isBlank(exhibition)) {
                    return ResultGenerator.genFailResult(ResultCode.EXHIBITION_IS_NULL);
                }

                String placeId2 = sysPlaceMapper.selectPlaceIdByExhibition(exhibition);
                if (placeId2 != null) {
                    return ResultGenerator.genFailResult(ResultCode.EXHIBITION_IS_NULL);
                }

                sysPlaceService.sysPlaceAdd(sysPlace, userId);

                //同步超级管理员权限
                if ("admin".equals(userId)) {

                    UserPlace userPlace = new UserPlace();
                    userPlace.setKeyId(IdsUtils.getOrderId());
                    userPlace.setUsername(userId);
                    userPlace.setPlaceNumber(sysPlace.getPlaceNumber());
                    userPlace.setRemark("系统内置");
                    userPlace.setCreateId("system");
                    userPlace.setCreateDate(new Date());
                    userPlace.setModifyId("system");
                    userPlace.setModifyDate(new Date());

                    userPlaceMapper.insert(userPlace);
                }

            } else {

                //修改操作
                sysPlace.setFlag(1);

                // 设置 placeId -- 场地ID
                String placeId = sysPlace.getPlaceId();

                //场地名称不能为空
                String exhibition = sysPlace.getExhibition();
                if (StringUtils.isBlank(exhibition)) {
                    return ResultGenerator.genFailResult(ResultCode.EXHIBITION_IS_NULL);
                }

                String placeId2 = sysPlaceMapper.selectPlaceIdByExhibition(exhibition);
                if (placeId2 != null && !placeId2.equals(placeId)) {
                    return ResultGenerator.genFailResult(ResultCode.EXHIBITION_IS_NULL);
                }

                // 设置 id -- 数据行号
                Integer id = sysPlace.getId();
                // 设置 placeNumber -- 场地编号
                String placeNumber = sysPlace.getPlaceNumber();

                // 设置 type -- 场地类型
                String type = sysPlace.getType();
                // 设置 remark -- 说明
                String remark = sysPlace.getRemark();
                // 设置 createId -- 创建人Id
                String createId = sysPlace.getCreateId();
                // 设置 createDate -- 创建时间
                Date createDate = sysPlace.getCreateDate();
                // 设置 postcode -- 邮编
                String postcode = sysPlace.getPostcode();
                // 设置 address -- 地址
                String address = sysPlace.getAddress();
                // 设置 country -- 国家
                String country = sysPlace.getCountry();
                // 设置 province -- 省份
                String province = sysPlace.getProvince();
                // 设置 city -- 市
                String city = sysPlace.getCity();
                // 设置 district -- 区
                String district = sysPlace.getDistrict();
                // 将数据封装到 sysPlace 中
                SysPlace newSysPlace1 = new SysPlace(placeId, id, placeNumber, exhibition, type, country, province, city, district, postcode, address, remark, createId, createDate);

                sysPlaceService.sysPlaceUpdate(newSysPlace1, userId);

            }

        } catch (Exception e) {
            if (sysPlace.getPlaceId() == null) {
                log.error("新增对象操作异常e:{}", e);
            } else {
                log.error("更新对象操作异常e:{}", e);
            }
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }


    @Resource
    private UserPlaceMapper userPlaceMapper;

    @PostMapping("/delete")
    public Result delete(@RequestParam String[] placeNumber) {

        if (StringUtils.isBlank(placeNumber.toString())) {
            return ResultGenerator.genFailResult(ResultCode.PLACENUMBER_IS_NULL);
        }

        // 记录场地删除信息
        List<String> dataList = new ArrayList<String>();

        try {

            for (String key : placeNumber) {

                List<String> areaCodes = sysPlaceService.findAreaCodeByPlaceNumber(key);

                if (areaCodes != null && areaCodes.size() > 0) {
                    dataList.add(key + "场地删除失败，该场地中存在库区，不可删除。");
                    continue;
                }

                //如果场地被分配则无法删除
                List<String> list = userPlaceMapper.selectUserNameByPlaceNumber(key);
                if (list != null && list.size() > 0) {
                    dataList.add(key + "场地已被分配，不可删除。");
                    continue;
                }

                sysPlaceService.deleteByPlaceNumber(key);

                dataList.add(key + "场地删除成功");
            }

        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(dataList);
    }

    @PostMapping("/update")
    public Result update(@RequestBody SysPlace sysPlace, HttpServletRequest request) {

        if (sysPlace == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        if (sysPlace.getId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        String userId = getUserName(request);


        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }

        try {

            sysPlaceService.sysPlaceUpdate(sysPlace, userId);

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

        SysPlace sysPlace = null;

        try {
            sysPlace = sysPlaceService.findById(id);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult(sysPlace);
    }

    // 查询功能： 模糊查询，传递的7个参数： 场地编号、场地名称、场地类型、国家、省、市、区、
    @PostMapping("/list")
    public Result list(@RequestBody SysPlace sysPlace, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(sysPlace.getClass());

        //根据id倒序输出
        condition.orderBy("id").desc();

        Criteria criteria = condition.createCriteria();

        //  type  场地类型  -- 全匹配
        if (StringUtils.isNotBlank(sysPlace.getType())) {
            criteria.andEqualTo("type", sysPlace.getType());
        }
        //  exhibition  场地名称  -- 模糊查询
        if (StringUtils.isNotBlank(sysPlace.getExhibition())) {
            criteria.andLike("exhibition", "%" + sysPlace.getExhibition() + "%");
        }
        //  placeNumber  场地编号  -- 模糊匹配
        if (StringUtils.isNotBlank(sysPlace.getPlaceNumber())) {
            criteria.andLike("placeNumber", "%" + sysPlace.getPlaceNumber() + "%");
        }
        //  country  国家  -- 全匹配
        if (StringUtils.isNotBlank(sysPlace.getCountry())) {
            criteria.andEqualTo("country", sysPlace.getCountry());
        }
        //  province  省份  -- 全匹配
        if (StringUtils.isNotBlank(sysPlace.getProvince())) {
            criteria.andEqualTo("province", sysPlace.getProvince());
        }
        //  city  市  -- 全匹配
        if (StringUtils.isNotBlank(sysPlace.getCity())) {
            criteria.andEqualTo("city", sysPlace.getCity());
        }
        //  district  行政区  -- 全匹配
        if (StringUtils.isNotBlank(sysPlace.getDistrict())) {
            criteria.andEqualTo("district", sysPlace.getDistrict());
        }

        PageInfo pageInfo = null;
        try {

            List<SysPlace> list = sysPlaceService.findByCondition(condition);

            List<SysPlace> listnew = new ArrayList<>();

            int number = 0;

            for (SysPlace sys : list) {
                List<String> lists = new ArrayList<>();
                String provinceId = sys.getProvince();
                String cityId = sys.getCity();
                String districtId = sys.getDistrict();
                lists.add(provinceId);
                lists.add(cityId);
                lists.add(districtId);
                List<String> area = moduleRegionService.findCname(lists);
                int i = 1;
                for (String cn_name : area) {
                    if (i == 1) {
                        sys.setProvinceName(cn_name);
                        i++;
                    } else if (i == 2) {
                        sys.setCityName(cn_name);
                        i++;
                    } else {
                        sys.setDistrictName(cn_name);
                    }
                }
                number++;
                listnew.add(sys);
            }


            pageInfo = new PageInfo(listnew);

            pageInfo = new PageInfo(list);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @PostMapping("/listPlace")
    public Result listPlace(HttpServletRequest request) {

        String userName = getUserName(request);
        //查询当前用户所有场地

        List<String> list = userPlaceMapper.selectPlaceIdByUserName(userName);


        List<SysPlace> places = new ArrayList<>();

        if (list != null && list.size() > 0) {

            for (String placeId : list) {
                SysPlace place = sysPlaceMapper.selectByPrimaryKey(placeId);
                places.add(place);
            }

        }
        return ResultGenerator.genSuccessResult(places);

    }


}
