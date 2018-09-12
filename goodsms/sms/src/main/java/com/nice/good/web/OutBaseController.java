package com.nice.good.web;

import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.dto.OutBaseDto;
import com.nice.good.dto.OutDetailDto;
import com.nice.good.dto.GatherDto;
import com.nice.good.dto.PurchaseOrderBean;
import com.nice.good.exception.IllegalOperateException;
import com.nice.good.model.*;
import com.nice.good.service.*;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.utils.ExcelImportUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/04/11.
 */
@RestController
@RequestMapping("/out/base")
public class OutBaseController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(OutBaseController.class);
	private final static String TMP_PATH = "/file_temp/";
	private final static String STORE_SEAT_MODEL = "out_base_model_tmp.xlsx";
	private final static String UP_PATH = "/upload_temp/";
	private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
	private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径
	@Resource
	private OutBaseService outBaseService;


	@Resource
	private OutReceiverMapper outReceiverMapper;

	@Resource
	private OutDetailMapper outDetailMapper;

	@Resource
	private OutPickMapper outPickMapper;


	@Resource
	private OutInvoiceMapper outInvoiceMapper;

	@Resource
	private GooderService gooderService;

	@Resource
	private ProviderService providerService;

	@Resource
	private CarrierService carrierService;

	@Resource
	private ModuleRegionMapper moduleRegionMapper;

	@Resource
	private StockMapper stockMapper;

	@Resource
	private OrganizationService organizationService;

	@Resource
	private SysPlaceService sysPlaceService;

	@Resource
	private OutBaseMapper outBaseMapper;

	@Resource
	private OutSenderMapper outSenderMapper;


	@Resource
	private OutTaskMapper outTaskMapper;


	@LogAnnotation(logType = "出库日志", content = "出库单新增")
	@PostMapping("/add")
	public Result add(@RequestBody OutBase outBase, HttpServletRequest request) {
		if (outBase == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		String userId = getUserName(request);

        String placeId = getPlaceId(request);

        if (StringUtils.isBlank(placeId)){
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

		try {

			outBaseService.outBaseAdd(outBase,placeId, userId);

		} catch (Exception e) {

			if (e.getClass().equals(IllegalOperateException.class)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(e.getMessage());
			}

			log.error("新增对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 删除操作
	 *
	 * @param baseIds
	 * @return
	 */

	@PostMapping("/delete")
	@LogAnnotation(logType = "出库日志", content = "出库单删除")
	public Result delete(@RequestParam List<String> baseIds) {

		try {
			String errorMsg = outBaseService.deleteByBaseId(baseIds);

			if (StringUtils.isNotBlank(errorMsg)) {
				ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("删除对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult();
	}


	/**
	 * 主界面模糊匹配查询
	 *
	 * @param outBase
	 * @param page
	 * @param size
	 * @return
	 */

	@PostMapping("/list")
	public Result list(@RequestBody OutBase outBase, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,HttpServletRequest request) {

        PageInfo pageInfo = null;
        try {

            PageHelper.startPage(page, size);

            String placeId = getPlaceId(request);

            Condition condition = new Condition(outBase.getClass());


            if (StringUtils.isBlank(placeId)){
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            List<String> gooderCodes = getGooderCodes(request);

            List<OutBase> list = outBaseService.findByOutBase(outBase,placeId,gooderCodes);
			pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**
	 * 挂起/取消挂起操作
	 *
	 * @param baseIds
	 * @param hangUp
	 * @param request
	 * @return
	 */

	@LogAnnotation(logType = "出库日志", content = "出库单挂起/取消挂起")
	@PostMapping("/hangUp")
	public Result hangUp(@RequestParam List<String> baseIds, @RequestParam Integer hangUp, HttpServletRequest request) {

		String userId = getUserName(request);

		try {

			String errorMsg = outBaseService.hangUp(baseIds, hangUp, userId);
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
	 * 主界面分配操作
	 */
	@LogAnnotation(logType = "出库日志", content = "出库单分配")
	@PostMapping("/generateMainOutPick")
	public Result generateMainOutPick(@RequestBody List<OutBase> outBases, HttpServletRequest request) {
		if (outBases == null || outBases.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}


        try {

            String userId = getUserName(request);

            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
            }

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

            String errorMsg = outBaseService.generateMainOutPick(outBases,placeId, userId);
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
	 * 主界面反分配
	 */
	@LogAnnotation(logType = "出库日志", content = "出库单反分配")
	@PostMapping("/cancelMainOutPick")
	public Result cancelMainOutPick(@RequestBody List<OutBase> outBases, HttpServletRequest request) {
		if (outBases == null || outBases.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}


        try {
            String userId = getUserName(request);

            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
            }


            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }


            String errorMsg = outBaseService.cancelMainOutPick(outBases,placeId, userId);
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
	 * 只有拣货中和已拣货的订单才能才能执行发货操作
	 */
	@LogAnnotation(logType = "出库日志", content = "出库单出库发货")
	@PostMapping("/outMainSend")
	public Result outMainSend(@RequestBody List<OutBase> outBases, HttpServletRequest request) {
		if (outBases == null || outBases.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}


        try {

            String userId = getUserName(request);

            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
            }


            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }


            String errorMsg = outBaseService.outMainSend(outBases,placeId, userId);
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
	 * 修改查询基本信息界面
	 */
	@PostMapping("/listBase")
	public Result listBase(@RequestBody OutBase outBase) {

		if (outBase != null) {

			String baseId = outBase.getBaseId();

			outBase = outBaseService.findById(baseId);
			OutSender outSender = outSenderMapper.selectByBaseId(baseId);
			OutInvoice outInvoice = outInvoiceMapper.selectByBaseId(baseId);
			OutReceiver outReceiver = outReceiverMapper.selectByBaseId(baseId);

			List<OutDetail> outDetails = outDetailMapper.selectByBaseId(baseId);
			List<OutPick> outPicks = new ArrayList<>();
			List<OutTask> outTasks = new ArrayList<>();
			if (outDetails != null && outDetails.size() > 0) {
				for (OutDetail outDetail : outDetails) {
					List<OutPick> picks = outPickMapper.selectAllByDetailId(outDetail.getDetailId());
					List<OutTask> tasks = outTaskMapper.selectAllByDetailId(outDetail.getDetailId());
					outPicks.addAll(picks);
					outTasks.addAll(tasks);
				}
			}

			outBase.setOutSender(outSender);
			outBase.setOutReceiver(outReceiver);
			outBase.setOutInvoice(outInvoice);
			outBase.setOutDetails(outDetails);
			outBase.setOutPicks(outPicks);
			outBase.setOutTasks(outTasks);
			return ResultGenerator.genSuccessResult(outBase);
		}

		return ResultGenerator.genSuccessResult();
	}


	/**
	 * 返回 PurchaseOrderBean.java 实体类
	 *
	 * @return
	 */
	@PostMapping("/listSendCodeBean")
	public Result listSendCodeBean(HttpServletRequest request) {


        PurchaseOrderBean purchaseOrderBean = null;

        try {

            // 自动生成 send_code 发货单编号
            String sendCode = getSendCode();

            if (sendCode.equals("1017")) {
                return ResultGenerator.genFailResult(ResultCode.SENDCODE_IS_ERROR);
            }

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

			// 封装 货主编码 gooderCodes
			List<String> gooderCodes = gooderService.selectGooderCodes(placeId);

			// 封装 供应商编码 providerCodes
			List<String> providerCodes = providerService.findProviderCodes();

			// 封装 承运商编码 carrierCodes
			List<String> carrierCodes = carrierService.findCarrierCodes();

			// 封装 组织机构编码 orgCodes
			List<String> orgCodes = organizationService.findOrgCodes();

			// 封装 场地编号 placeNumbers
			List<String> placeNumbers = sysPlaceService.findPlaceNumbers();

			purchaseOrderBean = new PurchaseOrderBean(sendCode, gooderCodes, providerCodes, carrierCodes, orgCodes, placeNumbers);

		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
		}

		return ResultGenerator.genSuccessResult(purchaseOrderBean);
	}

	/**
	 * rfid采集校验
	 */
	@LogAnnotation(logType = "出库日志", content = "出库单rfid采集")
	@PostMapping("/rfidGather")
	public Result rfidGather(@RequestBody OutBase outBase, HttpServletRequest request) {
		if (outBase == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		String userId = getUserName(request);

		Result result = outBaseService.rfidGather(outBase, userId);
		String errorMsg = result.getMessage();
		if (StringUtils.isNotBlank(errorMsg)) {
			return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
		}

		return ResultGenerator.genSuccessResult().setData(result.getData());

	}

	/**
	 * rfid采集保存
	 */
	@PostMapping("/rfidGatherSave")
	public Result rfidGatherSave(@RequestBody GatherDto gatherDto, HttpServletRequest request) {
		if (gatherDto == null) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}


		try {
			String userId = getUserName(request);


            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }


            String errorMsg = outBaseService.rfidGatherSave(gatherDto,placeId, userId);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("出库rfid采集保存失败}", e);
		}

		return ResultGenerator.genSuccessResult();

	}

	/**
	 * 取消订单
	 */
	@LogAnnotation(logType = "出库日志", content = "出库单取消订单")
	@PostMapping("/cancelOutBase")
	public Result cancelOutBase(@RequestBody List<OutBase> outBases, HttpServletRequest request) {
		if (outBases == null || outBases.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		try {
			String userId = getUserName(request);

			String errorMsg = outBaseService.cancelOutBase(outBases, userId);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("出库订单取消失败!", e);
		}

		return ResultGenerator.genSuccessResult();

	}


	/**
	 * 新增出库明细
	 */
	@PostMapping("/addOutDetail")
	public Result addDetail(@RequestBody List<SeatStock> stocks, @RequestParam String baseId, HttpServletRequest request) {

		if (stocks == null || stocks.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		try {
			String userId = getUserName(request);

			String errorMsg = outBaseService.addOutDetail(stocks, baseId, userId);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("新增明细失败!", e);
		}

		return ResultGenerator.genSuccessResult();

	}

	/**
	 * 修改删除出库明细
	 */
	@LogAnnotation(logType = "出库日志", content = "出库单修改删除")
	@PostMapping("/delOutDetail")
	public Result delDetail(@RequestParam List<String> detailIds, HttpServletRequest request) {

		if (detailIds == null || detailIds.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		try {

			String userId = getUserName(request);

			String errorMsg = outBaseService.delOutDetail(detailIds, userId);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}

		} catch (Exception e) {
			log.error("删除明细失败!", e);
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 修改时出库明细刷新操作
	 */
	@PostMapping("/listOutDetail")
	public Result listOutDetail(@RequestParam String baseId) {
		Object data;
		try {

			Result result = outBaseService.listOutDetail(baseId);

			data = result.getData();

		} catch (Exception e) {
			log.error("查询出库明细异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult().setData(data);
	}


	/**
	 * 发货单号的规则：自动生成（FH+6位日期+5位流水）示例：FH180909000001    -- 2018/05/29  17:34 rk
	 *
	 * @return
	 */
	public String getSendCode() {

		Integer id = outBaseMapper.selectMaxId();
		if (id == null) {
			id = 1;
		} else {
			id++;
		}

		String send_code = "FH" + getBody(id);

		return send_code;

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
	 * 出库订单下载模板
	 *
	 * @param
	 * @return
	 * @Author: zy
	 */
	@LogAnnotation(logType = "出库日志", content = "出库单下载模板")
	@RequestMapping(value = "/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		new ExcelImportUtils().dowFileUtils(response, request, STORE_SEAT_MODEL, TMP_PATH);
	}

	/**
	 * 出库订单导入预览
	 *
	 * @param
	 * @Author: zy
	 */
	@LogAnnotation(logType = "出库日志", content = "出库单导入预览")
	@RequestMapping(value = "/uploadForView", method = RequestMethod.POST)
	public Result uploadForView(HttpServletRequest request) throws IOException {


        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

		//解析excel文件
		MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
		MultipartFile file = multipartRequest.getFile("file");
		if (file == null) {
			return ResultGenerator.genFailResult("文件不存在,请重新上传");
		}

		//截取后缀重命名
		String UuName = ExcelImportUtils.getUuName(file.getOriginalFilename());
		//将文件暂存
		ExcelImportUtils.addExlce(file, UuName);
		//解析文件
		Map<String, List> map = parseExcel(file);
		//校验数据
		Map<String, List> newMap = valid(map,placeId);
		List<String> fileName = new ArrayList<>();
		fileName.add(UuName);
		newMap.put("fileName", fileName);
		return ResultGenerator.genSuccessResult(newMap);
	}

	/**
	 * 出库订单导入保存
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadForSave", method = RequestMethod.POST)
	public Result uploadForSave(@RequestParam String fileName, HttpServletRequest request) throws IOException {


        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

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
		Map<String, List> map = parseExcel(file);
		//校验数据
		Map<String, List> newMap = valid(map,placeId);
		//保存
		try {
			outBaseService.uploadExcelForAddStoreSeat(newMap.get("success"), newMap.get("success1"), userId);
		} catch (Exception e) {
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		ExcelImportUtils.deleteDir(new File(path));
		return ResultGenerator.genSuccessResultFile(newMap);


	}


	/**
	 * 将导入的excel文件解析为对象的集合
	 *
	 * @param file
	 * @return
	 */
	private Map<String, List> parseExcel(MultipartFile file) {
		int sheetNum = 0;
		int sheetNum1 = 1;
		List<OutBaseDto> baseDtos = new ArrayList<>();
		List<OutDetailDto> detailDtos = new ArrayList<>();
		OutBaseDto base;
		OutDetailDto detail;
		//获取excel指定的sheet页
		Sheet sheet = ExcelImportUtils.getExcelSheet(file, sheetNum);
		Sheet sheet1 = ExcelImportUtils.getExcelSheet(file, sheetNum1);
		//解析，获取excel中的数据
		List<String[]> list = ExcelImportUtils.getListFromSheet(sheet);
		for (String[] strs : list) {
			base = new OutBaseDto();
			base.setOrderCode(strs[0].split("\\.")[0]);
			base.setGooderCode(strs[1].split("\\.")[0]);
			base.setProviderCode(strs[2].split("\\.")[0]);
			base.setCarrierCode(strs[3].split("\\.")[0]);
			base.setOrderType(strs[4].split("\\.")[0]);
			base.setPriority(strs[5].split("\\.")[0]);
			base.setSender(strs[6].split("\\.")[0]);
			base.setS_telephone(strs[7].split("\\.")[0]);
			base.setS_phone(strs[8].split("\\.")[0]);
			base.setS_province(strs[9].split("\\.")[0]);
			base.setS_city(strs[10].split("\\.")[0]);
			base.setS_district(strs[11].split("\\.")[0]);
			base.setS_address(strs[12].split("\\.")[0]);
			base.setReceiver(strs[13].split("\\.")[0]);
			base.setR_telephone(strs[14].split("\\.")[0]);
			base.setR_phone(strs[15].split("\\.")[0]);
			base.setR_province(strs[16].split("\\.")[0]);
			base.setR_city(strs[17].split("\\.")[0]);
			base.setR_district(strs[18].split("\\.")[0]);
			base.setR_address(strs[19].split("\\.")[0]);
			base.setInvoiceType(strs[20].split("\\.")[0]);
			base.setInvoiceHead(strs[21].split("\\.")[0]);
			base.setInvoicelAmount(strs[22]);
			baseDtos.add(base);
		}
		List<String[]> list1 = ExcelImportUtils.getListFromSheet(sheet1);
		for (String[] strs : list1) {
			detail = new OutDetailDto();
			detail.setOrderCode(strs[0].split("\\.")[0]);
			detail.setGoodCode(strs[1].split("\\.")[0]);
			detail.setGoodName(strs[2].split("\\.")[0]);
			detail.setOrderNum(strs[3].split("\\.")[0]);
			detailDtos.add(detail);
		}
		Map map = new HashMap();
		map.put("baseDtos", baseDtos);
		map.put("detailDtos", detailDtos);
		return map;
	}

	/**
	 * 对导入的excel校验
	 *
	 * @Author: zy
	 */
	private Map valid(Map<String, List> map, String placeId) {
		Map newMap = new HashMap();
		List<OutBaseDto> successList = new ArrayList(); //主表校验成功集合
		List<OutBaseDto> failList = new ArrayList();    //主表校验失败集合
		List<OutDetailDto> successList1 = new ArrayList();//从表校验成功集合
		List<OutDetailDto> failList1 = new ArrayList();//从表校验失败集合
		Set<Stock> set = new HashSet<>();            // 接收stok的集合
		List<Stock> list7 = new ArrayList<>();   //接收stok的集合
		List<String> list8 = new ArrayList<>();  // 从表比对订单编号的集合
		List<String> list9 = new ArrayList<>();  //主表比对订单编号的集合
		List<String> list10 = new ArrayList<>(); //从表校验失败的订单编号
		List<OutBaseDto> baseList = map.get("baseDtos");
		List<OutDetailDto> detailList = map.get("detailDtos");
		for (OutBaseDto outBaseDto : baseList) {
			String orderCode = outBaseDto.getOrderCode();//订单编号
			String gooderCode = outBaseDto.getGooderCode();//货主编码
			String providerCode = outBaseDto.getProviderCode();//供应商编码
			String carrierCode = outBaseDto.getCarrierCode();  //承运商编码
			String orderType = outBaseDto.getOrderType();  //订单类型
			String priority = outBaseDto.getPriority();   //优先级
			String sender = outBaseDto.getSender();  //发货人
			String s_telephone = outBaseDto.getS_telephone();  //手机
			String s_phone = outBaseDto.getS_phone();   //电话
			String s_province = outBaseDto.getS_province();//省
			String s_city = outBaseDto.getS_city();//市
			String s_district = outBaseDto.getS_district(); //区
			String s_address = outBaseDto.getS_address();//地址
			String receiver = outBaseDto.getReceiver();//收货人
			String r_telephone = outBaseDto.getR_telephone();//手机
			String r_phone = outBaseDto.getR_phone();//电话
			String r_province = outBaseDto.getR_province();//省
			String r_city = outBaseDto.getR_city();//市
			String r_district = outBaseDto.getR_district();//区
			String r_address = outBaseDto.getR_address();//地址
			String invoiceType = outBaseDto.getInvoiceType();//发票类型
			String invoiceHead = outBaseDto.getInvoiceHead();//发票抬头
			String invoicelAmount = outBaseDto.getInvoicelAmount();//发票金额
			list8.add(orderCode);
			//处理null字段
			if ("null".equals(invoicelAmount)) {
				outBaseDto.setInvoicelAmount("");
			}
			if ("null".equals(invoiceType)) {
				outBaseDto.setInvoiceType("");
			}
			if ("null".equals(invoiceHead)) {
				outBaseDto.setInvoiceHead("");
			}
			//判断必填字段是否为空
			if (StringUtils.isBlank(orderCode) || StringUtils.isBlank(gooderCode) || StringUtils.isBlank(providerCode)
					|| StringUtils.isBlank(carrierCode) || StringUtils.isBlank(orderType) || StringUtils.isBlank(priority)
					|| StringUtils.isBlank(sender) || StringUtils.isBlank(s_telephone) || StringUtils.isBlank(s_province)
					|| StringUtils.isBlank(s_city) || StringUtils.isBlank(s_district) || StringUtils.isBlank(s_address)
					|| StringUtils.isBlank(receiver) || StringUtils.isBlank(r_telephone) || StringUtils.isBlank(r_province)
					|| StringUtils.isBlank(r_city) || StringUtils.isBlank(r_district) || StringUtils.isBlank(r_address)) {
				outBaseDto.setError("必填字段不能为空");
				failList.add(outBaseDto);
				continue;
			}
			//判断发票金额
			if (StringUtils.isNotBlank(invoicelAmount) && !"null".equals(invoicelAmount)) {
				if (!invoicelAmount.matches("-?[0-9]+.?[0-9]*")) {
					outBaseDto.setError("发票金额只能是:整数或小数");
					failList.add(outBaseDto);
					continue;
				}
			}
			try {
				//货主编码
				List<String> gooderCodes = gooderService.findGooderCodes();
				if (!gooderCodes.contains(gooderCode)) {
					outBaseDto.setError("货主编码不存在");
					failList.add(outBaseDto);
					continue;
				}
				//  供应商编码 providerCodes
				List<String> providerCodes = providerService.findProviderCodes();
				if (!providerCodes.contains(providerCode)) {
					outBaseDto.setError("供应商编码不存在");
					failList.add(outBaseDto);
					continue;
				}
				// 承运商编码 carrierCodes
				List<String> carrierCodes = carrierService.findCarrierCodes();
				if (!carrierCodes.contains(carrierCode)) {
					outBaseDto.setError("承运商编码不存在");
					failList.add(outBaseDto);
					continue;
				}
				//判断某些必填字段的正则是否合法
				if (!orderCode.matches("^[a-zA-Z\\d]+$")) {
					outBaseDto.setError("订单编号不合法");
					failList.add(outBaseDto);
					continue;
				}
				if (!gooderCode.matches("[a-zA-Z].*[0-9]>*")) {
					outBaseDto.setError("货主编码不合法");
					failList.add(outBaseDto);
					continue;
				}
				if (!providerCode.matches("^[a-zA-Z\\d]+$")) {
					outBaseDto.setError("供应商编码不合法");
					failList.add(outBaseDto);
					continue;
				}
				if (!carrierCode.matches("^[a-zA-Z\\d]+$")) {
					outBaseDto.setError("承运商编码不合法");
					failList.add(outBaseDto);
					continue;
				}
				if (!(orderType.equals("正常出库") || orderType.equals("次品出库") || orderType.equals("换货出库"))) {
					outBaseDto.setError("订单类型填写不正确");
					failList.add(outBaseDto);
					continue;
				}
				if (!(priority.equals("紧急") || priority.equals("加急") || priority.equals("一般"))) {
					outBaseDto.setError("优先级:只能是紧急,加急,一般");
					failList.add(outBaseDto);
					continue;
				}
				if (!s_telephone.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")) {
					outBaseDto.setError("手机号格式不对");
					failList.add(outBaseDto);
					continue;
				}
				if (!r_telephone.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")) {
					outBaseDto.setError("手机号格式不对");
					failList.add(outBaseDto);
					continue;
				}
				//判断发货人省市区
				List<String> list1 = moduleRegionMapper.selectArea(s_province);
				if (list1.size() < 1) {
					outBaseDto.setError("省份不对");
					failList.add(outBaseDto);
					continue;
				}
				if (!list1.contains(s_city)) {
					outBaseDto.setError("市填写不对");
					failList.add(outBaseDto);
					continue;
				}
				List<String> list2 = moduleRegionMapper.selectArea(s_city);
				if (list2.size() < 1) {
					outBaseDto.setError("市填写不对");
					failList.add(outBaseDto);
					continue;
				}
				if (!list2.contains(s_district)) {
					outBaseDto.setError("区填写不对");
					failList.add(outBaseDto);
					continue;
				}
				//判断收货人省市区
				List<String> list3 = moduleRegionMapper.selectArea(r_province);
				if (list3.size() < 1) {
					outBaseDto.setError("省份不对");
					failList.add(outBaseDto);
					continue;
				}
				if (!list3.contains(r_city)) {
					outBaseDto.setError("市填写不对");
					failList.add(outBaseDto);
					continue;
				}
				List<String> list4 = moduleRegionMapper.selectArea(r_city);
				if (list4.size() < 1) {
					outBaseDto.setError("市填写不对");
					failList.add(outBaseDto);
					continue;
				}
				if (!list4.contains(r_district)) {
					outBaseDto.setError("区填写不对");
					failList.add(outBaseDto);
					continue;
				}
				//判断主表中是否有明细行
				for (OutDetailDto outDetailDto : detailList) {
					list9.add(outDetailDto.getOrderCode());
				}
				if (!list9.contains(orderCode)) {
					outBaseDto.setError("主表中没有明细行");
					failList.add(outBaseDto);
					continue;
				}
				//到这里说明数据合格,根据货主查询库存
				list7 = stockMapper.selectByGooder(gooderCode,placeId);
				for (Stock stock : list7) {
					set.add(stock);
				}
				successList.add(outBaseDto);
			} catch (Exception e) {
				log.error("校验对象异常");
			}
		}
		for (OutDetailDto outDetailDto : detailList) {
			String orderCode = outDetailDto.getOrderCode();  //订单编号
			String goodCode = outDetailDto.getGoodCode();    //货品编码
			String goodName = outDetailDto.getGoodName();   //货品名称
			String orderNum = outDetailDto.getOrderNum();   //订单量
			//判断从表订单编号在主表中是否存在
			if (StringUtils.isNotBlank(orderCode)) {
				if (!list8.contains(orderCode)) {
					outDetailDto.setError("订单编码在主表中不存在");
					failList1.add(outDetailDto);
					list10.add(orderCode);
					continue;
				}
			}
			if (StringUtils.isBlank(orderCode) || StringUtils.isBlank(goodCode) || StringUtils.isBlank(goodName)
					|| StringUtils.isBlank(orderNum)) {
				outDetailDto.setError("必填字段不能为空");
				failList1.add(outDetailDto);
				list10.add(orderCode);
				continue;
			}

			if (!orderCode.matches("^[a-zA-Z\\d]+$")) {
				outDetailDto.setError("订单编码不合法");
				failList1.add(outDetailDto);
				list10.add(orderCode);
				continue;
			}
			if (!goodCode.matches("^[a-zA-Z\\d]+$")) {
				outDetailDto.setError("货品编码不合法");
				failList1.add(outDetailDto);
				list10.add(orderCode);
				continue;
			}
			//判断根据货主查到的stok中是否包含这行数据
			boolean i = false;
			for (Stock stock : set) {
				if (goodCode.equals(stock.getGoodCode()) && goodName.equals(stock.getGoodName())) {
					i = true;
				}
			}
			if (i == false) {
				outDetailDto.setError("货品名称,货品编码 与主表中的货主不对应");
				failList1.add(outDetailDto);
				list10.add(orderCode);
				continue;
			}
			//判断订单量是否是正整数
			if (!orderNum.matches("^\\+?[1-9][0-9]*$")) {
				outDetailDto.setError("订单量只能是整数");
				failList1.add(outDetailDto);
				list10.add(orderCode);
				continue;
			}
			//走到这里 说明该行数据校验通过
			successList1.add(outDetailDto);
		}
		//未通过明细表的数据查询主表中是否存在
		for (String oderCode : list10) {
			if (list8.contains(oderCode)) {
				for (int i = 0; i < successList.size(); i++) {
					if (successList.get(i).getOrderCode().equals(oderCode)) {
						successList.get(i).setError("对应的明细表有不合格的数据");
						failList.add(successList.get(i));
						successList.remove(i);
					}
				}

			}
		}


		newMap.put("success", successList);
		newMap.put("fail", failList);
		newMap.put("success1", successList1);
		newMap.put("fail1", failList1);
		return newMap;
	}
}