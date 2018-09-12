package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.dto.GoodDto;
import com.nice.good.dto.CategoryMapDto;
import com.nice.good.dto.StyleMapDto;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.GoodArea;
import com.nice.good.model.GoodCategory;
import com.nice.good.model.GoodStyle;
import com.nice.good.service.GoodAreaService;
import com.nice.good.vo.AreaVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * @Description: 货品档案-库区设置
 * @Author: fqs
 * @Date: 2018/3/23 10:39
 * @Version: 1.0
 */
@RestController
@RequestMapping("/good/area")
public class GoodAreaController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(GoodAreaController.class);

    @Resource
    private GoodAreaService goodAreaService;


    @Resource
    private SysPlaceMapper sysPlaceMapper;


    @Resource
    private StoreAreaMapper storeAreaMapper;


    @Resource
    private PackManageMapper packManageMapper;

    @Resource
    private GoodCategoryMapper goodCategoryMapper;

    @Resource
    private GoodStyleMapper goodStyleMapper;


    @PostMapping("/add")
    public Result add(@RequestBody GoodArea goodArea, HttpServletRequest request) {
        if (goodArea == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            goodAreaService.goodAreaAdd(goodArea, userId);

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 货品档案新增库区
     *
     * @param areaVos
     * @param request
     * @return
     */
    @LogAnnotation(logType = "其他日志", content = "货品档案新增库区")
    @PostMapping("/addGoodArea")
    public Result addGoodArea(@RequestBody List<AreaVo> areaVos, String goodId, HttpServletRequest request) {

        if (areaVos == null || areaVos.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        try {
            String userId = getUserName(request);

            String errorMsg = goodAreaService.addGoodArea(areaVos, goodId, placeId, userId);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }
        } catch (Exception e) {
            log.error("新增货品档案库区失败", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    /**
     * 货品档案修改删除库区
     *
     * @param areaIds
     * @return
     */
    @LogAnnotation(logType = "其他日志", content = "货品档案修改删除库区")
    @PostMapping("/delGoodArea")
    public Result delGoodArea(@RequestParam List<String> areaIds,HttpServletRequest request) {

        if (areaIds == null || areaIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String placeId = getPlaceId(request);

        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        try {
            String errorMsg = goodAreaService.deleteByGoodAreaId(areaIds,placeId);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }
        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 货品档案库区修改刷新
     *
     * @param goodId
     * @return
     */
    @PostMapping("/listGoodArea")
    public Result listGoodArea(@RequestParam String goodId,HttpServletRequest request) {

        Object data;
        try {

            String placeId = getPlaceId(request);

            Result result = goodAreaService.listGoodArea(goodId,placeId);

            data = result.getData();

        } catch (Exception e) {
            log.error("查询货品库区操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult().setData(data);

    }


    @PostMapping("/update")
    public Result update(@RequestBody GoodArea goodArea, HttpServletRequest request) {
        if (goodArea == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        if (goodArea.getId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            goodAreaService.goodAreaUpdate(goodArea, userId);

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    /**
     * 库区设置 新增选择库区
     */

    @PostMapping("/chooseArea")
    public Result chooseArea(@RequestBody AreaVo areaVo, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }


        PageHelper.startPage(page, size);

        String placeNumber = null;
        String areaName = null;
        String areaType = null;

        Map<String, String> conditionMap = new HashMap<>();

        if (areaVo != null) {
            placeNumber = areaVo.getPlaceNumber();
            areaName = areaVo.getAreaName();
            areaType = areaVo.getAreaType();

        }
        conditionMap.put("placeNumber", placeNumber);
        conditionMap.put("areaName", areaName);
        conditionMap.put("areaType", areaType);
        conditionMap.put("placeId", placeId);

        PageInfo pageInfo = null;
        try {
            List<AreaVo> list = storeAreaMapper.chooseArea(conditionMap);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);

    }


    /**
     * 展厅名称下拉列表+货主编码下拉+包装编码下拉+所属类目+货品风格
     */
    @PostMapping("/pullPlaceList")
    public Result pullPlaceNumber(HttpServletRequest request) {


        GoodDto goodDto = new GoodDto();

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }


        List<String> placeNumbers = sysPlaceMapper.selectPlaceNumbers();

        List<String> gooderCodes = getGooderCodes(request);

        List<String> packCodes = packManageMapper.selectPackCodes(placeId);

        goodDto.setPlaceNumbers(placeNumbers);

        goodDto.setGooderCodes(gooderCodes);

        goodDto.setPackCodes(packCodes);

        //查询类目
        List<GoodCategory> goodCategories = goodCategoryMapper.selectAll();

        List<CategoryMapDto> list1 = new ArrayList<>();
        if (goodCategories != null && goodCategories.size() > 0) {
            for (GoodCategory category : goodCategories) {
                CategoryMapDto categoryMapDto = new CategoryMapDto();
                categoryMapDto.setCategoryId(category.getId());
                categoryMapDto.setCategoryName(category.getName());
                list1.add(categoryMapDto);
            }
        }

        goodDto.setCategoryList(list1);

        //查询风格
        List<StyleMapDto> list2 = new ArrayList<>();
        List<GoodStyle> goodStyles = goodStyleMapper.selectAll();
        if (goodStyles != null && goodStyles.size() > 0) {
            for (GoodStyle style : goodStyles) {
                StyleMapDto styleMapDto = new StyleMapDto();
                styleMapDto.setStyleId(style.getId());
                styleMapDto.setStyleName(style.getName());
                list2.add(styleMapDto);
            }

        }
        goodDto.setStyleList(list2);

        return ResultGenerator.genSuccessResult(goodDto);
    }


    //获取当前的货主编码

    @RequestMapping(value = "/pullGooderCodes", method = {RequestMethod.GET, RequestMethod.POST})
    public Result pullGooderCodes(HttpServletRequest request) {

        List<String> gooderCodes = getGooderCodes(request);

        Map<String,List<String>> map = new HashMap<>();

        map.put("gooderCodes",gooderCodes);

        return ResultGenerator.genSuccessResult(map);

    }


}
