package com.nice.good.web;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.GoodAreaMapper;
import com.nice.good.dao.GooderAreaMapper;
import com.nice.good.dao.StoreAreaMapper;
import com.nice.good.dao.SysPlaceMapper;
import com.nice.good.model.GooderArea;
import com.nice.good.model.StoreArea;
import com.nice.good.service.GooderAreaService;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.service.StoreAreaService;
import com.nice.good.vo.AreaVo;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;;

/**
* Created by CodeGenerator on 2018/04/16.
*/
@RestController
@RequestMapping("/gooder/area")
public class GooderAreaController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(GooderAreaController.class);


    @Resource
    private GooderAreaService gooderAreaService;


	@Resource
	private SysPlaceMapper sysPlaceMapper;


	@Resource
    private StoreAreaMapper storeAreaMapper;



    /**
     * 货主档案新增库区
     * @param areaVos
     * @param request
     * @return
     */
    @PostMapping("/addGooderArea")
    public Result addGooderArea(@RequestBody List<AreaVo> areaVos,String gooderId,HttpServletRequest request){


        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        if(areaVos == null || areaVos.size()==0){
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {
            String userId = getUserName(request);

            String errorMsg = gooderAreaService.addGooderArea(areaVos,gooderId,placeId,userId);
            if (StringUtils.isNotBlank(errorMsg)){
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }
        } catch (Exception e) {
            log.error("新增货主档案库区失败",e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }



    /**
     * 库区设置删除
     * @param areaIds
     * @return
     */
    @PostMapping("/delGooderArea")
    public Result delGooderArea(@RequestParam  List<String> areaIds,HttpServletRequest request) {

        if (areaIds==null || areaIds.size()==0){
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

    	try {

            String placeId = getPlaceId(request);

            String errorMsg = gooderAreaService.deleteByAreaId(areaIds,placeId);
            if (StringUtils.isNotBlank(errorMsg)){
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }
        } catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }


    /**
     * 货主档案库区修改刷新
     * @param gooderId
     * @return
     */
    @PostMapping("/listGooderArea")
    public Result listGooderArea(@RequestParam String gooderId,HttpServletRequest request){

        Object data;
        try {

            String placeId = getPlaceId(request);

            Result result = gooderAreaService.listGooderArea(gooderId,placeId);

            data = result.getData();

        } catch (Exception e) {
            log.error("查询货品库区操作异常e:{}",e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult().setData(data);

    }


	/**
	 * 库区设置 选择库区
	 */

	@PostMapping("/chooseArea")
	public Result chooseArea(@RequestBody AreaVo areaVo, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,HttpServletRequest request) {


		PageHelper.startPage(page, size);

        String placeId = getPlaceId(request);

        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

//		String placeNumber = null;
		String areaCode = null;
		String areaType = null;

		Map<String,String> conditionMap = new HashMap<>();

		if (areaVo != null) {
//            placeNumber = areaVo.getPlaceNumber();
            areaCode = areaVo.getAreaCode();
			areaType = areaVo.getAreaType();

		}
//		conditionMap.put("placeNumber",placeNumber);
		conditionMap.put("areaCode",areaCode);
		conditionMap.put("areaType",areaType);

        conditionMap.put("placeId",placeId);


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
	 * 展厅名称下拉列表
	 */
	@PostMapping("/pullPlaceList")
	public List<String> pullPlaceNumber(){
		List<String> placeNumbers = sysPlaceMapper.selectPlaceNumbers();
		return placeNumbers;
	}

}
