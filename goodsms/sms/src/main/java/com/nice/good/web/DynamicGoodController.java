package com.nice.good.web;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.DynamicGoodMapper;
import com.nice.good.dao.GoodMapper;
import com.nice.good.dao.RfidLabelMapper;
import com.nice.good.model.DynamicGood;
import com.nice.good.service.DynamicGoodService;

import com.nice.good.base.BaseController;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.utils.ExcelImportUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;;

/**
* Created by CodeGenerator on 2018/05/14.
*/
@RestController
@RequestMapping("/dynamic/good")
public class DynamicGoodController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(DynamicGoodController.class);


    @Resource
    private DynamicGoodService dynamicGoodService;


    @Resource
    private RfidLabelMapper rfidLabelMapper;


    @Resource
    private GoodMapper goodMapper;

    @PostMapping("/add")
    public Result add(@RequestBody DynamicGood dynamicGood,HttpServletRequest request) {
    	if(dynamicGood == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

         String userId = getUserName(request);

    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		dynamicGoodService.dynamicGoodAdd(dynamicGood,userId);

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
    	if(id == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}
    	if(StringUtils.isBlank(id)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {
    		dynamicGoodService.deleteById(id);
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody DynamicGood dynamicGood,HttpServletRequest request) {
    	if(dynamicGood == null){
    		return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
    	}

        String userId = getUserName(request);

    	if(dynamicGood.getId() == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}
    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	try {

    		dynamicGoodService.dynamicGoodUpdate(dynamicGood,userId);

		} catch (Exception e) {
			log.error("更新对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id,HttpServletRequest request) {
    	if(id == null){
    		return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
    	}

        String userId = getUserName(request);


    	if(StringUtils.isBlank(userId)){
    		return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
    	}
    	DynamicGood dynamicGood = null;
    	try {
    		dynamicGood = dynamicGoodService.findById(id);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        
        return ResultGenerator.genSuccessResult(dynamicGood);
    }

    @PostMapping("/list")
    public Result list(@RequestBody DynamicGood dynamicGood, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,HttpServletRequest request) {

        PageHelper.startPage(page, size);
        
        Condition condition = new Condition(dynamicGood.getClass());
        Criteria criteria = condition.createCriteria();


        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

		PageInfo pageInfo = null;
		try {

		    //货主编码
		    if (StringUtils.isNotBlank(dynamicGood.getGooderCode())){
		        criteria.andEqualTo("gooderCode",dynamicGood.getGooderCode());
            }

            //货品编码
            if (StringUtils.isNotBlank(dynamicGood.getGoodCode())){
                criteria.andLike("goodCode","%"+dynamicGood.getGoodCode()+"%");
            }
            //交易类型
            if (StringUtils.isNotBlank(dynamicGood.getTradeType())){
                criteria.andEqualTo("tradeType",dynamicGood.getTradeType());
            }

            //交易编号
            if (StringUtils.isNotBlank(dynamicGood.getTradeCode())){
                criteria.andLike("tradeCode","%"+dynamicGood.getTradeCode()+"%");
            }

            //任务编号
            if (StringUtils.isNotBlank(dynamicGood.getTaskCode())){
                criteria.andLike("taskCode","%"+dynamicGood.getTaskCode()+"%");
            }
            //来源库位
            if (StringUtils.isNotBlank(dynamicGood.getFromSeat())){
                criteria.andLike("fromSeat","%"+dynamicGood.getFromSeat()+"%");
            }

            //目标库位
            if (StringUtils.isNotBlank(dynamicGood.getToSeat())){
                criteria.andLike("toSeat","%"+dynamicGood.getToSeat()+"%");
            }
            //单据号
            if (StringUtils.isNotBlank(dynamicGood.getBillCode())){
                criteria.andLike("billCode","%"+dynamicGood.getBillCode()+"%");
            }

            //操作人
            if (StringUtils.isNotBlank(dynamicGood.getModifyId())){
                criteria.andLike("modifyId","%"+dynamicGood.getModifyId()+"%");
            }

            //操作时间
            if (dynamicGood.getModifyDateStart()!=null && dynamicGood.getModifyDateEnd()!=null){
                criteria.andBetween("modifyDate",dynamicGood.getModifyDateStart(),dynamicGood.getModifyDateEnd());
            }

            criteria.andEqualTo("placeId",placeId);

    		 List<DynamicGood> list = dynamicGoodService.findByCondition(condition);
    		 pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}",e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    /**
     * 查看RFID信息
     */
    @PostMapping("/listRfid")
    public Result getRfid(@RequestBody DynamicGood dynamicGood){

        Integer rfid = dynamicGood.getRfid();
        if (rfid!=null && rfid>0) {
            String gooderCode = dynamicGood.getGooderCode();
            String goodCode = dynamicGood.getGoodCode();
            List<String> list = rfidLabelMapper.selectRfidCodeByGooderAndGoodCode(gooderCode, goodCode);
            return ResultGenerator.genSuccessResult().setData(list);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 查询货主编码
     * @return
     */

    @PostMapping("/getGooderCode")
    public Result getGooderCode(HttpServletRequest request){

        List<String> gooderCodes = getGooderCodes(request);

        return ResultGenerator.genSuccessResult().setData(gooderCodes);
    }


	/**
	 * @Author: zy
	 * 货品动态明细导出
	 */
	@RequestMapping("/dynnamicExport")
	public void dynnamicExport (HttpServletRequest request , HttpServletResponse response) {
		//目前导出所有,如有查询条件需后加
		List<DynamicGood> goodlist = dynamicGoodService.findAll();
		if(goodlist!=null && goodlist.size()>0){
			//导出文件的标题
			String title = "货品动态明细"+".xls";
			//设置表格标题行
			String[] headers = new String[]{"货主编码","交易编号","货品编码","单据号","任务编号","交易类型","来源库位",
					"目标库位","数量","RFID","操作人","操作时间"};
			List<Object[]> dataList = new ArrayList<>();
			Object[] objs = null;
			for(DynamicGood good:goodlist){
				objs = new Object[headers.length];
				objs[0] = good.getGooderCode();
				objs[1] = good.getTradeCode();
				objs[2] = good.getGoodCode();
				objs[3] = good.getBillCode();
				objs[4] = good.getTaskCode();
				objs[5] = good.getTradeType();
				objs[6] = good.getFromSeat();
				objs[7] = good.getToSeat();
				objs[8] = good.getSize();
				objs[9] = good.getRfid();
				objs[10] = good.getCreateId();
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				objs[11]=sdf.format(good.getModifyDate());
				dataList.add(objs);
			}
			//用IO将数据导出
			OutputStream out = null;
			try {
				//防止中文乱码
				String headStr = "attachment; filename=\"" + new String( title.getBytes("gb2312"), "ISO8859-1" ) + "\"";
				response.setContentType("octets/stream");
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", headStr);
				out = response.getOutputStream();
				//ExportExcel ex = new ExportExcel(title, headers, dataList);//有标题
				ExcelImportUtils.ExportExcelSeedBack ex = new ExcelImportUtils.ExportExcelSeedBack(title, headers, dataList);//没有标题
				ex.export(out);
			}catch (Exception e){
				e.printStackTrace();
			}
		}

	}
}
