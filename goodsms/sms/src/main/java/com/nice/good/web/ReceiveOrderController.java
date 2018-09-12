package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.GoodMapper;
import com.nice.good.dao.ReceiveDetailMapper;
import com.nice.good.dao.ReceiveOrderMapper;
import com.nice.good.dao.StoreSeatMapper;
import com.nice.good.dto.*;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.Good;
import com.nice.good.model.ReceiveDetail;
import com.nice.good.model.ReceiveOrder;
import com.nice.good.service.*;
import com.nice.good.utils.ExcelImportUtils;
import com.nice.good.utils.ReceiveCodeUtils;
import com.nice.good.vo.GoodVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


;

/**
 * @Description: 收货单操作
 * @Author: fqs
 * @Date: 2018/3/29 9:11
 * @Version: 1.0
 */
@RestController
@RequestMapping("/receive/order")
public class ReceiveOrderController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(ReceiveOrderController.class);
	private final static String TMP_PATH = "/file_temp/";
	private final static String STORE_SEAT_MODEL = "receive_model_tmp.xlsx";
	private static ReceiveCodeUtils receiveCodeUtils;
	private final static String UP_PATH = "/upload_temp/";
	private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
	private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径
	@Resource
	private ReceiveOrderService receiveOrderService;

	@Resource
	private ReceiveDetailMapper receiveDetailMapper;


	@Resource
	private ProviderService providerService;

	@Resource
	private CarrierService carrierService;

	@Resource
	private OrganizationService organizationService;

	@Resource
	private SysPlaceService sysPlaceService;

	@Resource
	private StoreSeatMapper storeSeatMapper;

	@Resource
	private GoodMapper goodMapper;

	@Resource
	private ReceiveOrderMapper receiveOrderMapper;


	/**
	 * 新增操作
	 *
	 * @param receiveOrder
	 * @return
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单新增")
	@PostMapping("/add")
	public Result add(@RequestBody ReceiveOrder receiveOrder, HttpServletRequest request) {
		if (receiveOrder == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		try {

            String userId = getUserName(request);

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            if (StringUtils.isBlank(placeId)){
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            System.out.println("我是场地id: "+placeId);

			String errorMsg = receiveOrderService.receiveOrderAdd(receiveOrder,placeId, userId);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("新增对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 只有收货单主表界面的删除操作
	 *
	 * @param receiveIds
	 * @return
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单删除")
	@PostMapping("/delete")
	public Result delete(@RequestParam List<String> receiveIds) {
		if (receiveIds == null || receiveIds.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}

		try {
			String errorMsg = receiveOrderService.deleteByReceiveId(receiveIds);
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
	 * 查询操作
	 *
	 * @param receiveOrder
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/list")
	public Result list(@RequestBody ReceiveOrder receiveOrder, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,HttpServletRequest request) {

		PageHelper.startPage(page, size);

		PageInfo pageInfo = null;
		try {

            String placeId = getPlaceId(request);

            List<String> gooderCodes = getGooderCodes(request);

            if (StringUtils.isBlank(placeId)){
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            System.out.println("我是场地id: "+placeId);

			List<ReceiveOrder> list = receiveOrderService.findByConditions(receiveOrder,placeId,gooderCodes);
			pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**
	 * 收货明细界面的订单挂起/取消挂起操作 hangUp: 0取消挂起,1挂起 ,flag: 0表示明细界面操作,1表示主界面操作
	 *
	 * @param receiveIds
	 * @param hangUp
	 * @return
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单挂起/取消挂起")
	@PostMapping("/hangUp")
	public Result hangUp(@RequestParam List<String> receiveIds, @RequestParam Integer hangUp, @RequestParam Integer flag, HttpServletRequest request) {
		if (receiveIds == null || receiveIds.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		try {

            String userId = getUserName(request);


            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
            }

			String errorMsg = receiveOrderService.receiveOrderHangUp(receiveIds, hangUp, flag, userId);

			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("修改对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}


	/**
	 * 针对既有主表又有明细表界面的收货操作
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单收货")
	@PostMapping("/receiveGoods")
	public Result receiveGoods(@RequestBody ReceiveOrder receiveOrder, HttpServletRequest request) {
		if (receiveOrder == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		try {

            String userId = getUserName(request);

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
            }


            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

			String errorMsg = receiveOrderService.receiveGoods(receiveOrder,placeId, userId);

			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("收货异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 明细界面收货操作
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单收货")
	@PostMapping("/receiveMainGoods")
	public Result receiveMainGoods(@RequestBody List<ReceiveOrder> receiveOrders, HttpServletRequest request) {
		if (receiveOrders == null || receiveOrders.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		try {

            String userId = getUserName(request);

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }


            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
            }

			String errorMsg = receiveOrderService.receiveMainGoods(receiveOrders,placeId, userId);

			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("修改对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 针对既有主表又有明细表界面撤销收货
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单撤销收货")
	@PostMapping("/cancelGoods")
	public Result cancelGoods(@RequestBody List<ReceiveDetail> receiveDetails, HttpServletRequest request) {
		if (receiveDetails == null && receiveDetails.size() > 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		try {

            String userId = getUserName(request);

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
            }

			String errorMsg = receiveOrderService.cancelGoods(receiveDetails,placeId, userId);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("修改对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 针对只有主表界面撤销收货
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单撤销收货")
	@PostMapping("/cancelMainGoods")
	public Result cancelMainGoods(@RequestBody List<ReceiveOrder> receiveOrders, HttpServletRequest request) {
		if (receiveOrders == null || receiveOrders.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

        try {
            String userId = getUserName(request);

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

			String errorMsg = receiveOrderService.cancelMainGoods(receiveOrders,placeId, userId);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("修改对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 上架操作
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单上架")
	@PostMapping("/shelfGoods")
	public Result shelfGoods(@RequestBody List<ReceiveDetail> receiveDetails, HttpServletRequest request) {
		if (receiveDetails == null || receiveDetails.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		Result result;
        try {

            String userId = getUserName(request);

            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
            }

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }
            result = receiveOrderService.shelfGoods(receiveDetails,placeId,userId);
			if (StringUtils.isNotBlank(result.getMessage())) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(result.getMessage());
			}

		} catch (Exception e) {
			log.error("修改对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult().setData(result.getData());
	}


	/**
	 * 上架分配库位
	 */
	@PostMapping("/shelfAllotSeat")
	public Result shelfAllotSeat(@RequestBody List<ShelfPojo> shelfPojos,HttpServletRequest request) {

		if (shelfPojos == null || shelfPojos.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

		Result result = receiveOrderService.selectAllotSeat(shelfPojos,placeId);

		return ResultGenerator.genSuccessResult().setMessage(result.getMessage()).setData(result.getData());

	}


	/**
	 * 上架保存
	 */
	@PostMapping("/shelfGoodSave")
	public Result shelfGoodSave(@RequestBody List<ShelfPojo> shelfPojos, HttpServletRequest request) {
		if (shelfPojos == null || shelfPojos.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		try {

            String userId = getUserName(request);

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
            }

			String errorMsg = receiveOrderService.shelfGoodSave(shelfPojos,placeId, userId);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("修改对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 针对既有主表又有明细表界面的结算操作
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单结算")
	@PostMapping("/clearGoods")
	public Result clearGoods(@RequestBody List<ReceiveDetail> receiveDetails, @RequestParam Boolean forceClear, HttpServletRequest request) {
		if (receiveDetails == null || receiveDetails.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		String userId = getUserName(request);

		Result result;

		if (StringUtils.isBlank(userId)) {
			return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
		}
		try {

			result = receiveOrderService.clearGoods(receiveDetails, forceClear, userId);
			String message = result.getMessage();
			Object data = result.getData();
			if (StringUtils.isNotBlank(message)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(message).setData(data);
			}

		} catch (Exception e) {
			log.error("修改对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 针对只有主表界面的明细操作 是否强制结算
	 *
	 * @param receiveOrders
	 * @return
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单结算")
	@PostMapping("/clearMainGoods")
	public Result clearMainGoods(@RequestBody List<ReceiveOrder> receiveOrders, @RequestParam Boolean forceClear, HttpServletRequest request) {

		if (receiveOrders == null || receiveOrders.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}


        try {
            String userId = getUserName(request);

			Result result = receiveOrderService.clearMainGoods(receiveOrders, forceClear, userId);

			String errorMsg = result.getMessage();
			Object data = result.getData();

			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg).setData(data);
			}

		} catch (Exception e) {
			log.error("修改对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 修改查询
	 */
	@PostMapping("/listAll")
	public Result listAll(@RequestBody ReceiveOrder receiveOrder) {
		if (receiveOrder != null) {

			receiveOrder = receiveOrderService.findById(receiveOrder.getReceiveId());
			String receiveId = receiveOrder.getReceiveId();
			List<ReceiveDetail> receiveDetails = receiveDetailMapper.findDetailByReceiveId(receiveId);

			//毛重
			receiveOrder.setReceiveDetails(receiveDetails);
			return ResultGenerator.genSuccessResult(receiveOrder);
		}
		return ResultGenerator.genSuccessResult();
	}


	/**
	 * 修改时收货单新增明细
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单新增明细")
	@PostMapping("/addReceiveDetail")
	public Result addReceiveDetail(@RequestBody List<GoodVo> goods, @RequestParam String receiveId, HttpServletRequest request) {

		if (goods == null || goods.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}

		try {

			String userId = getUserName(request);

            String placeId = getPlaceId(request);

            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

			String errorMsg = receiveOrderService.addReceiveDetail(goods, receiveId,placeId, userId);
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
	 * 修改时收货单明细删除
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单修改删除明细")
	@PostMapping("/delReceiveDetail")
	public Result delReceiveDetail(@RequestParam List<String> detailIds, HttpServletRequest request) {

		if (detailIds == null || detailIds.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
		}

		try {

			String userId = getUserName(request);

			String errorMsg = receiveOrderService.delReceiveDetail(detailIds, userId);
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
	 * 修改时收货单明细刷新
	 */
	@PostMapping("/listReceiveDetail")
	public Result listReceiveDetail(@RequestParam String receiveId) {
		Object data;
		try {

			Result result = receiveOrderService.listReceiveDetail(receiveId);

			data = result.getData();

		} catch (Exception e) {
			log.error("查询收货明细操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult().setData(data);
	}


	/**
	 * 返回 PurchaseOrderBean.java 实体类
	 *
	 * @return
	 */
	@PostMapping("/listReceiveOrderBean")
	public Result listReceiveOrderBean(HttpServletRequest request) {

		// 自动生成 receive_code 收货单编号
		String receiveCode = getReceive();

		if (receiveCode.equals("1016")) {
			return ResultGenerator.genFailResult(ResultCode.RECEIVECODE_IS_ERROR);
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

			purchaseOrderBean = new PurchaseOrderBean(receiveCode, gooderCodes, providerCodes, carrierCodes, orgCodes, placeNumbers);


		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
		}

		return ResultGenerator.genSuccessResult(purchaseOrderBean);
	}

	/**
	 * 收货单模板下载
	 *
	 * @param
	 * @return
	 * @Author: zy
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单模板下载")
	@RequestMapping(value = "/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		new ExcelImportUtils().dowFileUtils(response, request, STORE_SEAT_MODEL, TMP_PATH);
	}

	/**
	 * 收货单:预览
	 *
	 * @return
	 * @Author: zy
	 */
	@RequestMapping(value = "/uploadForView", method = RequestMethod.POST)
	public Result uploadForView(@RequestParam String gooderCode,
								@RequestParam String providerCode, @RequestParam String carrierCode, @RequestParam String orgCode,
								@RequestParam String receiveType, @RequestParam String outsideCode, HttpServletRequest request){

        String placeId = getPlaceId(request);

        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

		MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		MultipartFile file = multipartRequest.getFile("file");
		if (file == null) {
			return ResultGenerator.genFailResult("文件不存在,请重新上传");
		}
		//截取后缀重命名
		String UuName = ExcelImportUtils.getUuName(file.getOriginalFilename());
		//将文件暂存
        try {
            ExcelImportUtils.addExlce(file, UuName);
        } catch (IOException e) {
            log.error("excel解析异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        //解析excel文件
		List<ReceiveDetailDto> receiveDetailDtos = parseExcel(file);
		ReceiveOrder receiveOrder = new ReceiveOrder();
		receiveOrder.setGooderCode(gooderCode);
		receiveOrder.setProviderCode(providerCode);
		receiveOrder.setCarrierCode(carrierCode);
		receiveOrder.setOrgCode(orgCode);
		receiveOrder.setReceiveType(receiveType);
		receiveOrder.setOutsideCode(outsideCode);
		List<String> fileName = new ArrayList<>();
		fileName.add(UuName);
		//校验数据
		Map<String, List> map = valid(receiveDetailDtos, receiveOrder,placeId);
		map.put("fileName", fileName);
		return ResultGenerator.genSuccessResult(map);
	}

	/**
	 * 收货单:导入
	 *
	 * @param request
	 * @return
	 */
	@LogAnnotation(logType = "收货单日志", content = "收货单导入")
	@RequestMapping(value = "/uploadForSave", method = RequestMethod.POST)
	public Result uploadForSave(@RequestParam String fileName, @RequestBody ReceiveOrder receiveOrder, HttpServletRequest request) throws IOException {

        String placeId = getPlaceId(request);

        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }


	    String path = PROJECT_PATH + RESOURCES_PATH + UP_PATH;
		//根据文件名和路径读取文件
		MultipartFile file = ExcelImportUtils.findFiles(path, fileName);
		if (file == null || file.isEmpty()) {
			ExcelImportUtils.deleteDir(new File(path));
			return ResultGenerator.genFailResult("文件不存在,请重新上传");
		}
		String userId = getUserName(request);
		//解析excel文件
		List<ReceiveDetailDto> receiveDetailDtos = parseExcel(file);
		//校验数据
		Map<String, List<ReceiveDetailDto>> map = valid(receiveDetailDtos, receiveOrder,placeId);
		receiveOrder.setPlaceId(getPlaceId(request));
		//保存
		try {
			receiveOrderService.uploadExcelForAddStoreSeat(map.get("success"), userId, receiveOrder);
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
	private List<ReceiveDetailDto> parseExcel(MultipartFile file) {
		int sheetNum = 0;
		List<ReceiveDetailDto> receiveOrderDtos = new ArrayList<>();
		ReceiveDetailDto receiveDetail;
		//获取excel指定的sheet页
		Sheet sheet = ExcelImportUtils.getExcelSheet(file, sheetNum);
		//解析，获取excel中的数据
		List<String[]> list = ExcelImportUtils.getListFromSheet(sheet);
		for (String[] strs : list) {
			receiveDetail = new ReceiveDetailDto();
			receiveDetail.setGoodCode(strs[0].split("\\.")[0].trim());
			receiveDetail.setGoodModel(strs[1].split("\\.")[0].trim());
			receiveDetail.setGoodName(strs[2].split("\\.")[0].trim());
			receiveDetail.setSeatCode(strs[3].split("\\.")[0].trim());
			receiveDetail.setLpn(strs[4].split("\\.")[0].trim());
			receiveDetail.setExpectNum(strs[5].split("\\.")[0].trim());
			receiveDetail.setReceiveNum(strs[6].split("\\.")[0].trim());
			receiveDetail.setRefuseNum(strs[7].split("\\.")[0].trim());
			receiveOrderDtos.add(receiveDetail);
		}
		return receiveOrderDtos;
	}

	/**
	 * 对导入的excel校验
	 *
	 * @Author: zy
	 */
	private Map valid(List<ReceiveDetailDto> orderList, ReceiveOrder receiveOrder,String placeId) {
		Map map = new HashMap();
		List<ReceiveDetailDto> successList = new ArrayList();
		List<ReceiveDetailDto> failList = new ArrayList();
		//如果主表中外部单号已存在,所有数据都不合格直接返回
		try {
			List<String> list1 = receiveOrderMapper.selectAllOutsideCode();
			String orderOutsideCode = receiveOrder.getOutsideCode(); //外部单号
			if (StringUtils.isNotBlank(orderOutsideCode) && list1.size() > 0) {
				if (list1.contains(orderOutsideCode)) {
					for (ReceiveDetailDto receiveDetailDto : orderList) {
						receiveDetailDto.setError("外部单号不能重复");
						failList.add(receiveDetailDto);
					}
					map.put("fail", failList);
					return map;
				}
			}
			for (ReceiveDetailDto receiveDetailDto : orderList) {
				receiveDetailDto.setGooderCode(receiveOrder.getGooderCode().trim());
				String goodCode = receiveDetailDto.getGoodCode().trim();//货品编码
				String goodName = receiveDetailDto.getGoodName().trim();//货品名称
				String seatCode = receiveDetailDto.getSeatCode().trim();  //库位编码
				String expectNum = receiveDetailDto.getExpectNum().trim(); //预期量
				String receiveNum =receiveDetailDto.getReceiveNum().trim(); //接受量
				String refuseNum = receiveDetailDto.getRefuseNum().trim(); //拒收量

				//判断必填字段是否为空
				if (StringUtils.isBlank(goodName) || StringUtils.isBlank(goodCode)
						|| StringUtils.isBlank(expectNum)) {
					receiveDetailDto.setError("必填字段不能为空");
					failList.add(receiveDetailDto);
					continue;
				}
				//判断预期量如果不为空是否是整数或者小数
				if (StringUtils.isNotBlank(expectNum)) {
					if (!expectNum.matches("-?[0-9]+.?[0-9]*")) {
						receiveDetailDto.setError("预期量只能填:整数");
						failList.add(receiveDetailDto);
						continue;
					}
				}
				if (StringUtils.isNotBlank(receiveNum)) {
					if (!receiveNum.matches("-?[0-9]+.?[0-9]*")) {
						receiveDetailDto.setError("接收量只能填:整数");
						failList.add(receiveDetailDto);
						continue;
					}
					if (StringUtils.isBlank(expectNum)){
						receiveDetailDto.setError("有接收量,预期不能为空");
						failList.add(receiveDetailDto);
						continue;
					}
				}
				if (StringUtils.isNotBlank(refuseNum)) {
					if (!refuseNum.matches("-?[0-9]+.?[0-9]*")) {
						receiveDetailDto.setError("拒收量只能填:整数");
						failList.add(receiveDetailDto);
						continue;
					}
					if (StringUtils.isBlank(expectNum)){
						receiveDetailDto.setError("有拒收量,预期不能为空");
						failList.add(receiveDetailDto);
						continue;
					}
				}
				if (StringUtils.isNotBlank(expectNum)&&StringUtils.isNotBlank(receiveNum)){
					Integer expectNum1 =	Math.round(Float.valueOf(expectNum));
					Integer receiveNum1 =	Math.round(Float.valueOf(receiveNum));
					if(expectNum1<receiveNum1){
						receiveDetailDto.setError("预期量不能小于接收量");
						failList.add(receiveDetailDto);
						continue;
					}

				}
				//判断货品编码的正则是否合法
				if (!goodCode.matches("[0-9A-Za-z]{5,25}")) {
					receiveDetailDto.setError("货品编码不合法");
					failList.add(receiveDetailDto);
					continue;
				}
				//如果库位编码不为空
				if (StringUtils.isNotBlank(seatCode)) {
					if (!seatCode.matches("[0-9A-Za-z]{5,25}")) {
						receiveDetailDto.setError("库位编码不合法");
						failList.add(receiveDetailDto);
						continue;
					}
				}
				//判断库位编码是否是暂存
				if (StringUtils.isNotBlank(seatCode)) {
					String seatType = storeSeatMapper.findSeatType(seatCode,placeId);
					if (seatType != null && seatType.equals("暂存")) {
						//分配库位之前判断库位容量是否足够
						Integer capacity = storeSeatMapper.selectCapacityBySeatCode(seatCode,placeId);
						if (capacity != null && capacity != 0) {
							Integer expectNum1 = Math.round(Float.valueOf(expectNum));
							if (capacity < expectNum1) {
								receiveDetailDto.setError("库存不足");
								failList.add(receiveDetailDto);
								continue;
							}
						}
					} else {
						receiveDetailDto.setError("所属库位下面的库位类型只能是:暂存");
						failList.add(receiveDetailDto);
						continue;
					}
				}
				//查询货品编码,判断插入的数据在货品档案中是否存在
				String gooderCode = receiveOrder.getGooderCode();
				List<String> list = goodMapper.selectBygoodCode(gooderCode);
				if (list.contains(goodCode)) {
					//如果存在通过货品编码查询该条的货品名称是否一致
					Good good;
					good = goodMapper.selectgoodNameModel(goodCode);
					if (good.getGoodName().equals(goodName)) {
						//到这里说明字段合法
						successList.add(receiveDetailDto);
					} else {
						receiveDetailDto.setError("货品名称不一致");
						failList.add(receiveDetailDto);
					}
				} else {
					receiveDetailDto.setError("在该货主下的货品编码不存在");
					failList.add(receiveDetailDto);
				}
			}
			map.put("success", successList);
			map.put("fail", failList);
		} catch (Exception e) {
			log.error("校验对象异常");
		}
		return map;
	}

	/**
	 * 收货单号的规则：自动生成（ASN+6位日期+6位流水）示例：ASN180909000001
	 *
	 * @return
	 */
	public String getReceive() {
		Integer id = receiveOrderMapper.selectMaxId();
		if (id == null) {
			id = 1;
		} else {
			id++;
		}

		String receive_code = "ASN" + getBody(id);

		return receive_code;
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

}
