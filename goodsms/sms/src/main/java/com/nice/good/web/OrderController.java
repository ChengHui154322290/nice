package com.nice.good.web;

import java.io.File;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.dao.GoodMapper;
import com.nice.good.dao.OrderGoodMappingMapper;
import com.nice.good.dto.GoodImportDto;
import com.nice.good.dto.OrderGoodDto;
import com.nice.good.dto.PurchaseOrderBean;
import com.nice.good.model.*;
import com.nice.good.service.*;
import com.nice.good.utils.ExcelImportUtils;
import com.nice.good.utils.PurchaseCodeUtils;
import com.nice.good.vo.GoodVo;
import com.nice.good.vo.OrderVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.OrderMapper;
import com.nice.good.enums.ResultCode;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

/**
 * Created by CodeGenerator on 2018/03/26.
 * 订单管理控制模块
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(OrderController.class);
    private final static String TMP_PATH = "/file_temp/";
    private final static String STORE_SEAT_MODEL = "order_model_tmp.xlsx";
    private static PurchaseCodeUtils purchaseCodeUtils;
    private final static String UP_PATH = "/upload_temp/";
    private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
    private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径
    @Resource
    private OrderService orderService;


    @Resource
    private OrderMapper orderMapper;


    @Resource
    private OrderGoodMappingMapper orderGoodMappingMapper;

    @Resource
    private GooderService gooderService;


    @Resource
    private ProviderService providerService;

    @Resource
    private CarrierService carrierService;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private SysPlaceService sysPlaceService;

    @Resource
    private GoodMapper goodMapper;


    @LogAnnotation(logType = "采购单日志", content = "采购单新增")
    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public Result add(@RequestBody Order order, HttpServletRequest request) {
        if (order == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        String placeId = getPlaceId(request);

        try {

            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = orderService.addOrderOpration(order, placeId, userId);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }


    @LogAnnotation(logType = "采购单日志", content = "采购单删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public Result delete(@RequestParam List<String> orderIds) {

        if (orderIds == null || orderIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {
            String errorMsg = orderService.deleteOrderById(orderIds);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    /**
     * 修改采购单状态  :【确认】---【取消确认】---【结算】
     *
     * @param
     * @return
     */

    @LogAnnotation(logType = "采购单日志", content = "采购单/确认/取消确认/结算")
    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestParam List<String> orderIds, @RequestParam Integer status, HttpServletRequest request) {

        if (orderIds == null || orderIds.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {

            String userId = getUserName(request);


            String errorMsg = orderService.updateStatus(orderIds, status, userId);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();

    }


    @PostMapping("/update")
    public Result update(@RequestBody Order order, HttpServletRequest request) {
        if (order == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        if (order.getId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        try {
            orderService.update(order);

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/list")
    public Result list(@RequestBody Order order, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {
        PageHelper.startPage(page, size);

        PageInfo pageInfo = null;
        //采购单号
        String purchaseCode = null;
        //货主编码
        String gooderCode = null;
        //供应商编码
        String providerCode = null;
        //承运商编码
        String carrierCode = null;

        //单据状态
        Integer orderStatus = null;

        //采购类型
        Integer purchaseType = null;
        //组织机构编码
        String orgCode = null;
        //货品编码
        String goodCode = null;
        //创建人
        String createName = null;
        //审核人
        String auditingId = null;
        //创建开始日期
        Date createDateStart = null;
        //创建结束日期
        Date createDateEnd = null;
        //审核开始日期
        Date auditingDateStart = null;
        //审核结束日期
        Date auditingDateEnd = null;

        Map<String, Object> conditionMap = new HashMap<>();

        ArrayList<OrderVo> list2 = new ArrayList<>();

        try {

            List<String> gooderCodes = getGooderCodes(request);

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            if (order != null) {

                purchaseCode = order.getPurchaseCode();

                gooderCode = order.getGooderCode();

                providerCode = order.getProviderCode();

                carrierCode = order.getCarrierCode();

                orderStatus = order.getOrderStatus();

                purchaseType = order.getPurchaseType();

                orgCode = order.getOrgCode();

                goodCode = order.getGoodCode();

                createName = order.getCreateName();

                auditingId = order.getAuditingId();

                createDateStart = order.getCreateDateStart();
                createDateEnd = order.getCreateDateEnd();


                auditingDateStart = order.getAuditingDateStart();

                auditingDateEnd = order.getAuditingDateEnd();

            }

            conditionMap.put("purchaseCode", purchaseCode);
            conditionMap.put("gooderCode", gooderCode);
            conditionMap.put("providerCode", providerCode);
            conditionMap.put("carrierCode", carrierCode);
            conditionMap.put("orderStatus", orderStatus);
            conditionMap.put("purchaseType", purchaseType);
            conditionMap.put("orgCode", orgCode);
            conditionMap.put("goodCode", goodCode);
            conditionMap.put("createName", createName);
            conditionMap.put("auditingId", auditingId);
            conditionMap.put("createDateStart", createDateStart);
            conditionMap.put("createDateEnd", createDateEnd);
            conditionMap.put("auditingDateStart", auditingDateStart);
            conditionMap.put("auditingDateEnd", auditingDateEnd);

            conditionMap.put("placeId", placeId);


            if (gooderCodes != null && gooderCodes.size() > 0) {

                List<OrderVo> list = orderMapper.findByConditions(conditionMap);

                if (list != null && list.size() > 0) {
                    
                    for (OrderVo orderVo : list) {

                        String gooderCode1 = orderVo.getGooderCode();

                        if (gooderCodes.contains(gooderCode1)) {
                            list2.add(orderVo);
                        }
                    }
                }

            }

            pageInfo = new PageInfo(list2);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 选择产品
     */
    @PostMapping("/listGoods")
    public Result listGoods(@RequestBody Good good, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {
        PageHelper.startPage(page, size);

        PageInfo pageInfo = null;

        String gooderCode = null;
        String commodityCode = null;
        String goodCode = null;
        String goodName = null;
        String goodModel = null;

        Map<String, String> goodMap = new HashMap<>();

        if (good != null) {
            gooderCode = good.getGooderCode();
            commodityCode = good.getCommodityCode();
            goodCode = good.getGoodCode();
            goodName = good.getGoodName();
            goodModel = good.getGoodModel();

        }
        goodMap.put("gooderCode", gooderCode);
        goodMap.put("commodityCode", commodityCode);
        goodMap.put("goodCode", goodCode);
        goodMap.put("goodName", goodName);
        goodMap.put("goodModel", goodModel);


        try {
            List<GoodVo> list = goodMapper.findByGoodCondition(goodMap);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);

    }


    /**
     * 采购单修改查询
     */
    @PostMapping("/listAll")
    public Result list(@RequestBody Order order) {
        if (order != null) {
            String orderId = order.getOrderId();
            order = orderMapper.selectByPrimaryKey(order.getOrderId());
            //查询所有明细
            List<OrderGoodMapping> list = orderGoodMappingMapper.selectMapperByOrderId(orderId);
            order.setObjs(list);
            return ResultGenerator.genSuccessResult(order);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 返回 PurchaseOrderBean.java 实体类
     *
     * @return
     */
    @PostMapping("/listPurchaseOrderBean")
    public Result listPurchaseOrderBean(HttpServletRequest request) {


        String purchaseCode = getPurchase();

        if (purchaseCode.equals("1015")) {
            return ResultGenerator.genFailResult(ResultCode.PURCHASECODE_IS_BEYOND_SETTING);
        }

        PurchaseOrderBean purchaseOrderBean = null;

        try {

            List<String> gooderCodes = getGooderCodes(request);

            // 封装 货主编码 gooderCodes
//			List<String> gooderCodes = gooderService.findGooderCodes();

            // 封装 供应商编码 providerCodes
            List<String> providerCodes = providerService.findProviderCodes();

            // 封装 承运商编码 carrierCodes
            List<String> carrierCodes = carrierService.findCarrierCodes();

            // 封装 组织机构编码 orgCodes
            List<String> orgCodes = organizationService.findOrgCodes();

            // 封装 场地编号 placeNumbers
            List<String> placeNumbers = sysPlaceService.findPlaceNumbers();

            purchaseOrderBean = new PurchaseOrderBean(purchaseCode, gooderCodes, providerCodes, carrierCodes, orgCodes, placeNumbers);


        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);

        }

        return ResultGenerator.genSuccessResult(purchaseOrderBean);
    }

    /**
     * 采购单模板下载
     *
     * @param
     * @return
     * @Author: zy
     */
    @LogAnnotation(logType = "采购单日志", content = "采购单模板下载")
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        new ExcelImportUtils().dowFileUtils(response, request, STORE_SEAT_MODEL, TMP_PATH);
    }

    /**
     * 采购单:预览
     *
     * @return
     * @Author: zy
     */
    @LogAnnotation(logType = "采购单日志", content = "采购单预览")
    @RequestMapping(value = "/uploadForView", method = RequestMethod.POST)
    public Result uploadForView(@RequestParam String gooderCode,
                                @RequestParam String providerCode, @RequestParam Integer purchaseType, @RequestParam String placeNumber,
                                HttpServletRequest request) throws IOException {
        if (StringUtils.isBlank(gooderCode) || StringUtils.isBlank(providerCode) || purchaseType == null || StringUtils.isBlank(placeNumber)) {
            String errorMsg = "下拉框不能为空";
            return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);

        }
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile file = multipartRequest.getFile("file");
        if (file == null) {
            return ResultGenerator.genFailResult("文件不存在,请重新上传");
        }
        //截取后缀重命名
        String UuName = ExcelImportUtils.getUuName(file.getOriginalFilename());
        //将文件暂存
        ExcelImportUtils.addExlce(file, UuName);
        //解析excel文件
        List<OrderGoodDto> goods = parseExcel(file);
        List<String> fileName = new ArrayList<>();
        fileName.add(UuName);
        //校验数据
        Order order = new Order();
        order.setGooderCode(gooderCode);
        order.setProviderCode(providerCode);
        order.setPurchaseType(purchaseType);
        order.setPlaceNumber(placeNumber);
        Map<String, List> map = valid(goods, order);
        map.put("fileName", fileName);
        return ResultGenerator.genSuccessResult(map);
    }

    /**
     * 采购单:导入
     *
     * @param request
     * @return
     */
    @LogAnnotation(logType = "采购单日志", content = "采购单导入")
    @RequestMapping(value = "/uploadForSave", method = RequestMethod.POST)
    public Result uploadForSave(@RequestParam String fileName, @RequestBody Order order,
                                HttpServletRequest request) throws IOException {
        //获取文件名
        String path = PROJECT_PATH + RESOURCES_PATH + UP_PATH;
        //根据文件名和路径读取文件
        MultipartFile file = ExcelImportUtils.findFiles(path, fileName);

        if (file == null || file.isEmpty()) {
            ExcelImportUtils.deleteDir(new File(path));
            return ResultGenerator.genFailResult("文件不存在,请重新上传");
        }
        String userId = getUserName(request);
        //解析excel文件
        List<OrderGoodDto> goods = parseExcel(file);
        //校验数据
        Map<String, List<OrderGoodDto>> map = valid(goods, order);

        // String purchaseCode = purchaseCodeUtils.getPurchaseCode(orderService);
        String purchaseCode = getPurchase();

        order.setPurchaseCode(purchaseCode);
        //保存
        try {
            orderService.uploadExcelForAddStoreSeat(map.get("success"), userId, order);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        //清除该目录下所有文件
        ExcelImportUtils.deleteDir(new File(path));
        return ResultGenerator.genSuccessResultFile(map);
    }

    /**
     * 将导入的excel文件解析为对象的集合
     *
     * @param file
     * @return
     */
    private List<OrderGoodDto> parseExcel(MultipartFile file) {
        int sheetNum = 0;
        List<OrderGoodDto> goods = new ArrayList<>();
        OrderGoodDto newgoods;
        //获取excel指定的sheet页
        Sheet sheet = ExcelImportUtils.getExcelSheet(file, sheetNum);
        //解析，获取excel中的数据
        List<String[]> list = ExcelImportUtils.getListFromSheet(sheet);
        //解析，获取excel中的数据
        for (String[] strs : list) {
            newgoods = new OrderGoodDto();
            newgoods.setGoodModel(strs[0].split("\\.")[0]);
            newgoods.setGoodName(strs[1].split("\\.")[0]);
            newgoods.setGoodCode(strs[2].split("\\.")[0]);
            newgoods.setPurchaseNumber(strs[3].split("\\.")[0]);
            newgoods.setRate(strs[4].split("\\.")[0]);
            newgoods.setOriginPrice(strs[5].split("\\.")[0]);
            newgoods.setRatePrice(strs[6].split("\\.")[0]);
            newgoods.setAmount(strs[7].split("\\.")[0]);
            newgoods.setRateAmount(strs[8].split("\\.")[0]);
            newgoods.setIsPay(strs[9].split("\\.")[0]);
            goods.add(newgoods);
        }
        return goods;
    }

    /**
     * 对导入的excel校验
     *
     * @Author: zy
     */
    private Map valid(List<OrderGoodDto> orderList, Order order) {
        Map map = new HashMap();
        List<OrderGoodDto> successList = new ArrayList();
        List<OrderGoodDto> failList = new ArrayList();
        String gooderCode = order.getGooderCode();
        String providerCode = order.getProviderCode();
        Integer purchaseType = order.getPurchaseType();
        String placeNumber = order.getPlaceNumber();
        for (OrderGoodDto orderGoodDto : orderList) {
            String goodModel = orderGoodDto.getGoodModel();//货品规格
            String goodName = orderGoodDto.getGoodName();//货品名称
            String goodCode = orderGoodDto.getGoodCode();//货品编码
            String purchaseNumber = orderGoodDto.getPurchaseNumber();  //采购数量
            String rate = orderGoodDto.getRate();  //税率
            String originPrice = orderGoodDto.getOriginPrice();   //进货价
            String ratePrice = orderGoodDto.getRatePrice();  //含税进货价
            String amount = orderGoodDto.getAmount();  //金额
            String rateAmount = orderGoodDto.getRateAmount();   //含税金额
            String isPay = orderGoodDto.getIsPay();//是否付款（是/否）
            //判断必填字段是否为空
            if (StringUtils.isBlank(goodName) || StringUtils.isBlank(goodCode)
                    || StringUtils.isBlank(purchaseNumber) || StringUtils.isBlank(gooderCode) ||
                    StringUtils.isBlank(providerCode) || purchaseType == null || StringUtils.isBlank(placeNumber)) {
                orderGoodDto.setError("必填字段不能为空");
                failList.add(orderGoodDto);
                continue;
            }
            if (StringUtils.isNotBlank(isPay)) {
                if (!(isPay.equals("是") || isPay.equals("否"))) {
                    orderGoodDto.setError("是否付款:是,否");
                    failList.add(orderGoodDto);
                    continue;
                }
            }
            //判断采购数量是否是整数
            if (!purchaseNumber.matches("-?[0-9]+.?[0-9]*")) {
                orderGoodDto.setError("采购数量只能是:整数");
                failList.add(orderGoodDto);
                continue;
            }
            //判断进货价,金额等如果不为空是否是整数或者小数
            if (StringUtils.isNotBlank(originPrice)) {
                if (!originPrice.matches("-?[0-9]+.?[0-9]*")) {
                    orderGoodDto.setError("进货价只能是:整数或小数");
                    failList.add(orderGoodDto);
                    continue;
                }
            }
            if (StringUtils.isNotBlank(ratePrice)) {
                if (!ratePrice.matches("-?[0-9]+.?[0-9]*")) {
                    orderGoodDto.setError("含税进货价只能是:整数或小数");
                    failList.add(orderGoodDto);
                    continue;
                }
            }
            if (StringUtils.isNotBlank(amount)) {
                if (!amount.matches("-?[0-9]+.?[0-9]*")) {
                    orderGoodDto.setError("金额只能是:整数或小数");
                    failList.add(orderGoodDto);
                    continue;
                }
            }
            if (StringUtils.isNotBlank(rateAmount)) {
                if (!rateAmount.matches("-?[0-9]+.?[0-9]*")) {
                    orderGoodDto.setError("含税金额只能是:整数或小数");
                    failList.add(orderGoodDto);
                    continue;
                }
            }
            //判断某些必填字段的正则是否合法
            if (!goodCode.matches("[0-9A-Za-z]{5,25}")) {
                orderGoodDto.setError("货品编码不合法");
                failList.add(orderGoodDto);
                continue;
            }
            try {
                //查询货品编码,判断插入的数据在货品档案中是否存在
                List<String> list = goodMapper.selectBygoodCode(gooderCode);
                if (list.contains(goodCode)) {
                    //如果存在通过货品编码查询该条的货品名称是否一致
                    Good good;
                    good = goodMapper.selectgoodNameModel(goodCode);
                    if (good.getGoodName().equals(goodName)) {
                        //到这里说明字段合法
                        successList.add(orderGoodDto);
                    } else {
                        orderGoodDto.setError("货品名称与货品档案的不一致");
                        failList.add(orderGoodDto);
                    }

                } else {
                    orderGoodDto.setError("货品编码不存在");
                    failList.add(orderGoodDto);
                }
            } catch (Exception e) {
                log.error("校验对象异常");
            }
        }
        map.put("success", successList);
        map.put("fail", failList);
        return map;
    }

    /**
     * 采购单号的规则：自动生成（PO+6位日期+6位流水）示例：PO180327000001
     *
     * @return
     */
    public String getPurchase() {
        Integer id = orderMapper.selectMaxId();
        if (id == null) {
            id = 1;
        } else {
            id++;
        }

        String purchase_code = "PO" + getBody(id);

        return purchase_code;
    }


    /**
     * 6位日期 + 6位流水号
     *
     * @param id
     * @return
     */
    private String getBody(Integer id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()).substring(2, 8) + String.format("%06d", id);
    }


    /**
     * 采购单修改新增明细
     */
    @LogAnnotation(logType = "采购单日志", content = "采购单新增明细")
    @PostMapping("/addGoodMapping")
    public Result addGoodMapping(@RequestBody List<Good> goods, @RequestParam String orderId, HttpServletRequest request) {

        if (goods == null || goods.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        try {

            String userId = getUserName(request);

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = orderService.addGoodMapping(goods, orderId, placeId, userId);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();

    }

    /**
     * 采购单修改时删除明细
     */
    @LogAnnotation(logType = "采购单日志", content = "采购单修改删除明细")
    @PostMapping("/delGoodMapping")
    public Result delGoodMapping(@RequestParam List<Integer> ids) {

        if (ids == null || ids.size() == 0) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        try {


            String errorMsg = orderService.delGoodMapping(ids);

            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();

    }

    /**
     * 采购单修改时明细刷新
     */
    @PostMapping("/listGoodMapping")
    public Result listGoodMapping(@RequestParam String orderId) {

        Object data;
        try {

            Result result = orderService.listGoodMapping(orderId);

            data = result.getData();

        } catch (Exception e) {
            log.error("查询货品库区操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult().setData(data);

    }


}
