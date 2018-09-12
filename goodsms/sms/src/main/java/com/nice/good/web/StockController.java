package com.nice.good.web;

import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.model.Stock;
import com.nice.good.service.StockService;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.utils.DateUtils;
import com.nice.good.utils.ExcelImportUtils;
import com.nice.good.vo.StockNumVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
 * @Description: 库存操作
 * @Author: fqs
 * @Date: 2018/4/7 16:28
 * @Version: 1.0
 */
@RestController
@RequestMapping("/stock")
public class StockController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(StockController.class);

    @Resource
    private StockService stockService;

    @PostMapping("/add")
    public Result add(@RequestBody Stock stock, HttpServletRequest request) {
        if (stock == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            stockService.stockAdd(stock, userId);

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete/{stockId}")
    public Result delete(@RequestParam String stockId) {
        if (stockId == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        try {
            stockService.deleteById(stockId);
        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Stock stock, HttpServletRequest request) {
        if (stock == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        if (stock.getId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {

            stockService.stockUpdate(stock, userId);

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

        Stock stock = null;
        try {
            stock = stockService.findById(id);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult(stock);
    }

    @PostMapping("/list")
    public Result list(@RequestBody Stock stock, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(stock.getClass());

        Map<String, Object> conditionMap = new HashMap<>();

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        String gooderCode = null;
        String goodCode = null;
        String commodityCode = null;
        String goodName = null;
        String orgCode = null;
        String providerCode = null;


        //数据来源，flag,0商品上新，1采购收货
        Integer flag = stock.getFlag();
        if (flag == null) {
            flag = 1;
        }

        if (flag==0){
            stock.setGooderCode(null);
            stock.setProviderCode(null);
            stock.setOrgCode(null);
        }


        //根据id倒序输出
        condition.orderBy("id").desc();

        Criteria criteria = condition.createCriteria();
        if (StringUtils.isNotBlank(stock.getGooderCode())) {
            gooderCode = stock.getGooderCode();
            criteria.andEqualTo("gooderCode", gooderCode);
        }

        if (StringUtils.isNotBlank(stock.getGoodCode())) {
            goodCode = stock.getGoodCode();
            criteria.andLike("goodCode", "%" + goodCode + "%");

        }
        if (StringUtils.isNotBlank(stock.getCommodityCode())) {
            commodityCode = stock.getCommodityCode();
            criteria.andLike("commodityCode", "%" + commodityCode + "%");
        }

        if (StringUtils.isNotBlank(stock.getGoodName())) {
            goodName = stock.getGoodName();
            criteria.andLike("goodName", "%" + goodName + "%");

        }

        if (StringUtils.isNotBlank(stock.getOrgCode())) {
            orgCode = stock.getOrgCode();
            criteria.andLike("orgCode", "%" + orgCode + "%");
        }

        if (StringUtils.isNotBlank(stock.getProviderCode())) {
            providerCode = stock.getProviderCode();
            criteria.andLike("providerCode", "%" + providerCode + "%");
        }

        Integer removeEmpty = stock.getRemoveEmpty();

        if (removeEmpty!=null && removeEmpty==1){
            criteria.andGreaterThan("nowNum",0);
        }

        criteria.andEqualTo("placeId", placeId);

        conditionMap.put("gooderCode", gooderCode);
        conditionMap.put("goodCode", goodCode);
        conditionMap.put("commodityCode", commodityCode);
        conditionMap.put("goodName", goodName);
        conditionMap.put("orgCode", orgCode);
        conditionMap.put("providerCode", providerCode);
        conditionMap.put("removeEmpty", removeEmpty);
        conditionMap.put("placeId", placeId);

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

        PageInfo pageInfo;
        StockNumVo stockNumVo;
        try {
            List<Stock> list = stockService.findByCondition(condition);

            pageInfo = new PageInfo(list);

            stockNumVo = stockService.countStockNum(conditionMap);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult2(pageInfo, stockNumVo);
    }

    /**
     * 库存导出
     *
     * @param request
     * @param response
     * @Author: zy
     */
    @RequestMapping("/stockExport")
    public void stockExport(HttpServletRequest request, HttpServletResponse response) {
        //目前导出所有,如有查询条件需后加
        List<Stock> stockList = stockService.findAll();
        if (stockList != null && stockList.size() > 0) {
            //导出文件的标题
            String title = "货品库存" + ".xls";
            //设置表格标题行
            String[] headers = new String[]{"货主编码", "货品编码", "商品编码", "货品名称", "现有量",
                    "可用量", "分配量", "拣货量", "冻结量"};
            List<Object[]> dataList = new ArrayList<>();
            Object[] objs = null;
            for (Stock stock : stockList) {
                objs = new Object[headers.length];
                objs[0] = stock.getGooderCode();
                objs[1] = stock.getGoodCode();
                objs[2] = stock.getCommodityCode();
                objs[3] = stock.getGoodName();
                objs[4] = stock.getNowNum();
                objs[5] = stock.getUseNum();
                objs[6] = stock.getAllotNum();
                objs[7] = stock.getPickNum();
                objs[8] = stock.getFreezeNum();
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
