package com.nice.good.web;

import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.SeatStockMapper;
import com.nice.good.model.SeatStock;
import com.nice.good.service.SeatStockService;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.utils.ExcelImportUtils;
import com.nice.good.vo.SeatStockVo;
import com.nice.good.vo.StockNumVo;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/04/09.
 */
@RestController
@RequestMapping("/seat/stock")
public class SeatStockController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(SeatStockController.class);


    @Resource
    private SeatStockService seatStockService;

    @Resource
    private SeatStockMapper seatStockMapper;

    @PostMapping("/add")
    public Result add(@RequestBody SeatStock seatStock, HttpServletRequest request) {
        if (seatStock == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            seatStockService.seatStockAdd(seatStock, userId);

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete/{stockId}")
    public Result delete(@PathVariable(value = "stockId") String stockId) {
        if (stockId == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        try {
            seatStockService.deleteById(stockId);
        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody SeatStock seatStock, HttpServletRequest request) {
        if (seatStock == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        if (seatStock.getId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            seatStockService.seatStockUpdate(seatStock, userId);

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id, String userId) {
        if (id == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }
        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        SeatStock seatStock = null;
        try {
            seatStock = seatStockService.findById(id);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult(seatStock);
    }

    /**
     *
     * @param seatStockvo
     * @param page
     * @param size
     * @param request
     * @return
     */
    @PostMapping("/list/bar")
    public Result listBar(@RequestBody SeatStockVo seatStockvo, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {

        PageHelper.startPage(page, size);
        PageInfo pageInfo;

        try {

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }
            seatStockvo.setPlaceId(placeId);
            List<SeatStockVo> list = seatStockService.findByBar(seatStockvo);

            pageInfo = new PageInfo(list);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * @Description: 货品库位库存查询
     * @Author: fqs
     * @Date: 2018/4/11 10:36
     * @Version: 1.0
     */
    @PostMapping("/list")
    public Result list(@RequestBody SeatStock seatStock, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(seatStock.getClass());

        PageInfo pageInfo;
        StockNumVo stockNumVo;

        try {

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }


            Map<String, Object> conditionMap = new HashMap<>();

            String gooderCode = null;
            String goodCode = null;
            String commodityCode = null;
            String goodName = null;
            String orgCode = null;
            String providerCode = null;
            String seatCode = null;


            //数据来源，flag,0商品上新，1采购收货
            Integer flag = seatStock.getFlag();
            if (flag == null) {
                flag = 1;
            }

            if (flag==0){
                seatStock.setGooderCode(null);
                seatStock.setProviderCode(null);
                seatStock.setOrgCode(null);
            }

            //根据id倒序输出
            condition.orderBy("id").desc();

            Criteria criteria = condition.createCriteria();

            if (StringUtils.isNotBlank(seatStock.getGooderCode())) {
                gooderCode = seatStock.getGooderCode();
                criteria.andEqualTo("gooderCode", gooderCode);

            }
            if (StringUtils.isNotBlank(seatStock.getCommodityCode())) {
                commodityCode = seatStock.getCommodityCode();
                criteria.andLike("commodityCode", "%" + commodityCode + "%");

            }
            if (StringUtils.isNotBlank(seatStock.getGoodCode())) {
                goodCode = seatStock.getGoodCode();
                criteria.andLike("goodCode", "%" + goodCode + "%");
            }
            if (StringUtils.isNotBlank(seatStock.getSeatCode())) {
                seatCode = seatStock.getSeatCode();
                criteria.andLike("seatCode", "%" + seatCode + "%");
            }
            if (StringUtils.isNotBlank(seatStock.getGoodName())) {
                goodName = seatStock.getGoodName();
                criteria.andLike("goodName", "%" + goodName + "%");
            }

            if (StringUtils.isNotBlank(seatStock.getOrgCode())) {
                orgCode = seatStock.getOrgCode();
                criteria.andLike("orgCode", "%" + orgCode + "%");
            }

            if (StringUtils.isNotBlank(seatStock.getProviderCode())) {
                providerCode = seatStock.getProviderCode();
                criteria.andLike("providerCode", "%" + providerCode + "%");
            }

            Integer removeEmpty = seatStock.getRemoveEmpty();

            if (removeEmpty != null && removeEmpty == 1) {
                criteria.andGreaterThan("nowNum", 0);
            }

            criteria.andEqualTo("placeId", placeId);

            conditionMap.put("gooderCode", gooderCode);
            conditionMap.put("goodCode", goodCode);
            conditionMap.put("commodityCode", commodityCode);
            conditionMap.put("goodName", goodName);
            conditionMap.put("orgCode", orgCode);
            conditionMap.put("providerCode", providerCode);
            conditionMap.put("seatCode", seatCode);
            conditionMap.put("placeId", placeId);
            conditionMap.put("removeEmpty", removeEmpty);
            conditionMap.put("flag", flag);


            if (flag == 1) {
                criteria.andIsNotNull("gooderCode");
                criteria.andIsNotNull("providerCode");
                criteria.andIsNotNull("orgCode");
            }

            if (flag == 0) {
                criteria.andIsNull("gooderCode");
                criteria.andIsNull("providerCode");
                criteria.andIsNull("orgCode");
            }


            List<SeatStock> list = seatStockService.findByCondition(condition);

            pageInfo = new PageInfo(list);

            stockNumVo = seatStockService.countSeatNum(conditionMap);


        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult2(pageInfo, stockNumVo);
    }


    @RequestMapping("/stockExport")
    public void stockExport(HttpServletRequest request, HttpServletResponse response) {
        //目前导出所有,如有查询条件需后加
        List<SeatStock> seatList = seatStockService.findAll();
        if (seatList != null && seatList.size() > 0) {
            //导出文件标题
            String title = "货品库位" + ".xls";
            //设置表格标题行
            String[] headers = new String[]{"货主编码", "货品编码", "库位编码", "商品编码", "货品名称", "现有量",
                    "可用量", "分配量", "拣货量", "冻结量"};
            List<Object[]> dataList = new ArrayList<>();
            Object[] objs = null;
            for (SeatStock stock : seatList) {
                objs = new Object[headers.length];
                objs[0] = stock.getGooderCode();
                objs[1] = stock.getGoodCode();
                objs[2] = stock.getSeatCode();
                objs[3] = stock.getCommodityCode();
                objs[4] = stock.getGoodName();
                objs[5] = stock.getNowNum();
                objs[6] = stock.getUseNum();
                objs[7] = stock.getAllotNum();
                objs[8] = stock.getPickNum();
                objs[9] = stock.getFreezeNum();
                //将数据添加到excel表格中
                dataList.add(objs);
            }
            //用IO将数据导出
            OutputStream out = null;
            try {
                //防止中文乱码
                String headStr = "attachment; filename=\"" + new String(title.getBytes("gb2312"), "ISO8859-1") + "\"";
                response.setContentType("octets/stream");
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", headStr);
                out = response.getOutputStream();
                //ExportExcel ex = new ExportExcel(title, headers, dataList);//有标题
                ExcelImportUtils.ExportExcelSeedBack ex = new ExcelImportUtils.ExportExcelSeedBack(title, headers, dataList);//没有标题
                ex.export(out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
