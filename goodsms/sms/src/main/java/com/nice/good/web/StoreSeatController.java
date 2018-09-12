package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.StoreAreaMapper;
import com.nice.good.dao.StoreSeatMapper;
import com.nice.good.dao.StoreSeatPictureMapper;
import com.nice.good.dao.SysPlaceMapper;
import com.nice.good.dto.SeatDto;
import com.nice.good.dto.StoreSeatDto;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.StoreSeat;
import com.nice.good.model.SysPlace;
import com.nice.good.service.StoreSeatService;
import com.nice.good.utils.ExcelImportUtils;
import com.nice.good.vo.PlaceSeatVo;
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
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description: 展厅档案-库位
 * @Author: fqs
 * @Date: 2018/3/23 10:33
 * @Version: 1.0
 */
@RestController
@RequestMapping("/store/seat")
public class StoreSeatController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(StoreSeatController.class);

	@Resource
	private StoreSeatService storeSeatService;

	@Resource
	private StoreAreaMapper storeAreaMapper;


	@Resource
	private StoreSeatMapper storeSeatMapper;


	@Resource
	private SysPlaceMapper sysPlaceMapper;


	@Resource
    private StoreSeatPictureMapper storeSeatPictureMapper;

	private final static String TMP_PATH = "/file_temp/";
	private final static String STORE_SEAT_MODEL = "stock_seat_model_tmp.xlsx";


	private final static String UP_PATH = "/upload_temp/";
	private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
	private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径

	@LogAnnotation(logType = "其他日志", content = "库位新增")
	@PostMapping("/add")
	public Result add(@RequestBody StoreSeat storeSeat, HttpServletRequest request) {


		try {
			if (storeSeat == null) {
				return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
			}


			String userId = getUserName(request);

			if (StringUtils.isBlank(userId)) {
				return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
			}

			String placeId = getPlaceId(request);
			if (StringUtils.isBlank(placeId)) {
				return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
			}

			String errorMsg = storeSeatService.storeSeatAdd(storeSeat, placeId, userId);
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
	 * 库位删除操作
	 *
	 * @param seatIds
	 * @return
	 */
	@LogAnnotation(logType = "其他日志", content = "库位删除")
	@PostMapping("/delete")
	public Result delete(@RequestParam List<String> seatIds,HttpServletRequest request) {

		if (seatIds == null || seatIds.size() == 0) {
			return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
		}

		try {

            String placeId = getPlaceId(request);
            if (StringUtils.isBlank(placeId)) {
                return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
            }

			String errorMsg = storeSeatService.deleteBySeatId(seatIds,placeId);
			if (StringUtils.isNotBlank(errorMsg)) {
				return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
			}
		} catch (Exception e) {
			log.error("删除对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/update")
	public Result update(@RequestBody StoreSeat storeSeat, HttpServletRequest request) {

		try {

			if (storeSeat == null) {
				return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
			}


			String userId = getUserName(request);

			if (StringUtils.isBlank(userId)) {
				return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
			}

			String placeId = getPlaceId(request);
			if (StringUtils.isBlank(placeId)) {
				return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
			}

            String errorMsg =storeSeatService.storeSeatUpdate(storeSeat, placeId, userId);
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
     * 库位图片上传
     * @param storeSeat
     * @param page
     * @param size
     * @param request
     * @return
     */

	@PostMapping("/list")
	public Result list(@RequestBody StoreSeat storeSeat, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size,HttpServletRequest request) {

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

		PageHelper.startPage(page, size);

		Map<String, Object> seatMap = new HashMap<>();

		String placeNumber = null;
		String areaCode = null;
		String areaName = null;
		String seatType = null;
		String seatName = null;
		String seatCode = null;

		if (storeSeat != null) {

			placeNumber = storeSeat.getPlaceNumber();
			areaCode = storeSeat.getAreaCode();
			areaName = storeSeat.getAreaName();
			seatType = storeSeat.getSeatType();
			seatName = storeSeat.getSeatName();
			seatCode = storeSeat.getSeatCode();
		}


		seatMap.put("placeNumber", placeNumber);
		seatMap.put("areaCode", areaCode);
		seatMap.put("areaName", areaName);
		seatMap.put("seatType", seatType);
		seatMap.put("seatName", seatName);
		seatMap.put("seatCode", seatCode);
        seatMap.put("placeId", placeId);

		PageInfo pageInfo = null;
		try {
			List<PlaceSeatVo> list = storeSeatMapper.findByConditions(seatMap);

			pageInfo = new PageInfo(list);
		} catch (Exception e) {
			log.error("查询对象操作异常e:{}", e);
			return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**
	 * 查找所有展厅编码和库区编码
	 */
	@PostMapping("/exhitAndAreaList")
	public Result listAllPlaceAndArea(HttpServletRequest request) {

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }

        SysPlace sysPlace = sysPlaceMapper.selectByPrimaryKey(placeId);

        String placeNumber =sysPlace.getPlaceNumber();

        List<SeatDto> list = new ArrayList<>();

		//查找所有展厅名称
        SeatDto seatDto = new SeatDto();

        seatDto.setPlaceNumber(placeNumber);

        //根据展厅编码查找所有库区编码
        List<String> areaCodes = storeAreaMapper.selectAreaCodeByPlaceId(placeId);

        seatDto.setPlaceNumber(placeNumber);
        seatDto.setAreaCodes(areaCodes);

        list.add(seatDto);

        return ResultGenerator.genSuccessResult(list);
	}

	/**
	 * 修改查询
	 */
	@PostMapping("/listSeat")
	public Result list(@RequestBody PlaceSeatVo placeSeatVo,HttpServletRequest request) {

        String placeId = getPlaceId(request);
        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
        }


        if (placeSeatVo != null) {
			StoreSeat storeSeat = storeSeatMapper.selectByPrimaryKey(placeSeatVo.getSeatId());
//			String areaName = storeAreaMapper.selectAreaNameByCode(storeSeat.getAreaCode(),placeId);
//			storeSeat.setAreaName(areaName);


            //库位图片
            List<String> list = storeSeatPictureMapper.selectSeatImgsBySeatId(storeSeat.getSeatId());
            if (list!=null && list.size()>0){
                storeSeat.setImgIds(list);
            }

            return ResultGenerator.genSuccessResult(storeSeat);
		}
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 库位设置暂存文件之后预览预览
	 */
	@RequestMapping(value = "/uploadForView", method = RequestMethod.POST)
	public Result uploadForView(HttpServletRequest request) throws IOException {

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
		ExcelImportUtils.addExlce(file, UuName);
		//解析文件
		List<StoreSeatDto> storeSeatDtos = parseExcel(file);
		List<StoreSeatDto> fileName = new ArrayList<>();
		StoreSeatDto s = new StoreSeatDto();
		s.setFileName(UuName);
		fileName.add(s);
		//校验数据

		Map<String, List<StoreSeatDto>> map = valid(storeSeatDtos, placeId);
		map.put("fileName", fileName);
		return ResultGenerator.genSuccessResult(map);
	}

	/**
	 * 库位设置导入保存
	 *
	 * @param request
	 * @return
	 */
	@LogAnnotation(logType = "其他日志", content = "库位导入")
	@RequestMapping(value = "/uploadForSave", method = RequestMethod.POST)
	public Result uploadForSave(@RequestParam String fileName, HttpServletRequest request) throws IOException {


		//获取文件名
		String path = PROJECT_PATH + RESOURCES_PATH + UP_PATH;
		Map<String, List<StoreSeatDto>> map = null;

		try {
			String placeId = getPlaceId(request);
			if (StringUtils.isBlank(placeId)) {
				return ResultGenerator.genFailResult(ResultCode.PLACE_IS_NULL);
			}

			//根据文件名和路径读取文件
			MultipartFile file = ExcelImportUtils.findFiles(path, fileName);

			if (file == null || file.isEmpty()) {
				ExcelImportUtils.deleteDir(new File(path));
				return ResultGenerator.genFailResult("文件不存在,请重新上传");
			}
			String userId = getUserName(request);
			//解析excel文件
			List<StoreSeatDto> storeSeatDtos = parseExcel(file);
			//校验数据
			map = valid(storeSeatDtos, placeId);
			//保存
			storeSeatService.uploadExcelForAddStoreSeat(map.get("success"), placeId, userId);
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
	public List<StoreSeatDto> parseExcel(MultipartFile file) {
		int sheetNum = 0;
		List<StoreSeatDto> storeSeatDtos = new ArrayList<>();
		StoreSeatDto storeSeatDto;
		//获取excel指定的sheet页
		Sheet sheet = ExcelImportUtils.getExcelSheet(file, sheetNum);
		//解析，获取excel中的数据
		List<String[]> list = ExcelImportUtils.getListFromSheet(sheet);
		for (String[] strs : list) {
			storeSeatDto = new StoreSeatDto();
			storeSeatDto.setPlaceNumber(strs[0].split("\\.")[0].trim());
			storeSeatDto.setAreaCode(strs[1].split("\\.")[0].trim());
			storeSeatDto.setSeatName(strs[2].split("\\.")[0].trim());
			storeSeatDto.setSeatCode(strs[3].split("\\.")[0].trim());
			storeSeatDto.setMixGood(strs[4].split("\\.")[0].trim());
			storeSeatDto.setMixBatch(strs[5].split("\\.")[0].trim());
			storeSeatDto.setSeatType(strs[6].split("\\.")[0].trim());
			storeSeatDto.setLevel(strs[7].split("\\.")[0].trim());
			storeSeatDto.setSeatTag(strs[8].split("\\.")[0].trim());
			storeSeatDto.setSeatStatus(strs[9].split("\\.")[0].trim());
			storeSeatDto.setSeatCapacity(strs[10].split("\\.")[0].trim());
			storeSeatDto.setStatement(strs[11].split("\\.")[0].trim());
			storeSeatDto.setLength(strs[12].trim());
			storeSeatDto.setWidth(strs[13].trim());
			storeSeatDto.setHeight(strs[14].trim());
			storeSeatDtos.add(storeSeatDto);
		}
		return storeSeatDtos;
	}

	/**
	 * 对导入的excel校验
	 *
	 * @Author: zy
	 */
	private Map valid(List<StoreSeatDto> seatList, String placeId) {
		Map map = new HashMap();
		List<StoreSeatDto> successList = new ArrayList();
		List<StoreSeatDto> failList = new ArrayList();
		List<String> seatCodes = new ArrayList<>();
		for (StoreSeatDto storeSeatDto : seatList) {
			String placeNumber = storeSeatDto.getPlaceNumber().trim();//场地编号
			String areaCode = storeSeatDto.getAreaCode().trim();//库区编码
			String seatName = storeSeatDto.getSeatName().trim();  //库位名称
			String seatCode = storeSeatDto.getSeatCode().trim();  //库位编号
			String mixGood = storeSeatDto.getMixGood().trim();   //混放货品
			String mixBatch = storeSeatDto.getMixBatch().trim();  //混放批次
			String seatType = storeSeatDto.getSeatType().trim();  //库位类型
			String seatTag = storeSeatDto.getSeatTag().trim();   //库位标记
			String seatStatus = storeSeatDto.getSeatStatus().trim();//库位状态
			String seatCapacity = storeSeatDto.getSeatCapacity().trim();//库位容量
			String level = storeSeatDto.getLevel().trim(); //层级
			String length = storeSeatDto.getLength().trim();//长
			String height = storeSeatDto.getHeight().trim();//高
			String width = storeSeatDto.getWidth().trim();//宽
			//判断必填字段是否为空
			if (StringUtils.isBlank(placeNumber) || StringUtils.isBlank(areaCode) || StringUtils.isBlank(seatName)
					|| StringUtils.isBlank(seatCode) || StringUtils.isBlank(mixGood) || StringUtils.isBlank(mixBatch)
					|| StringUtils.isBlank(seatType) || StringUtils.isBlank(seatCapacity) || StringUtils.isBlank(level)) {
				storeSeatDto.setError("必填字段不能为空");
				failList.add(storeSeatDto);
				continue;
			}

			if (!(mixGood.equals("是") || mixGood.equals("否"))) {
				storeSeatDto.setError("混放货品只能是:是,否");
				failList.add(storeSeatDto);
				continue;
			}
			if (!(mixBatch.equals("是") || mixBatch.equals("否"))) {
				storeSeatDto.setError("混放批次只能是:是,否");
				failList.add(storeSeatDto);
				continue;
			}
			if (!(seatType.equals("挂装") || seatType.equals("整箱") || seatType.equals("暂存") || seatType.equals("直播间") || seatType.equals("展厅"))) {
				storeSeatDto.setError("库位类型只能是:挂装,整箱,暂存");
				failList.add(storeSeatDto);
				continue;
			}
			if (StringUtils.isNotBlank(seatTag)) {
				if (!(seatTag.equals("无") || seatTag.equals("冻结") || seatTag.equals("破损"))) {
					storeSeatDto.setError("库位标记只能是:无,冻结,破损");
					failList.add(storeSeatDto);
					continue;
				}
			}
			if (StringUtils.isNotBlank(seatStatus)) {
				if (!(seatStatus.equals("未使用") || seatStatus.equals("使用中"))) {
					storeSeatDto.setError("库位状态:未使用,使用中");
					failList.add(storeSeatDto);
					continue;
				}
			}
			//判断库位容量是否是整数
			if (!seatCapacity.matches("[0-9].*")) {
				storeSeatDto.setError("库位容量:整数");
				failList.add(storeSeatDto);
				continue;
			}
			//如果长宽高不为空判断是否是整数或者小数
			if (StringUtils.isNotBlank(length)) {
				if (!length.matches("-?[0-9]+.?[0-9]*")) {
					storeSeatDto.setError("长:整数,小数");
					failList.add(storeSeatDto);
					continue;
				}
			}
			if (StringUtils.isNotBlank(width)) {
				if (!width.matches("-?[0-9]+.?[0-9]*")) {
					storeSeatDto.setError("宽:整数,小数");
					failList.add(storeSeatDto);
					continue;
				}
			}
			if (StringUtils.isNotBlank(height)) {
				if (!height.matches("-?[0-9]+.?[0-9]*")) {
					storeSeatDto.setError("高:整数,小数");
					failList.add(storeSeatDto);
					continue;
				}
			}
			//判断某些必填字段的正则是否合法
			if (!seatCode.matches("[0-9A-Za-z]{3,25}")) {
				storeSeatDto.setError("库位编码不合法");
				failList.add(storeSeatDto);
				continue;
			}
			if (!placeNumber.matches("[0-9A-Za-z]{5,25}")) {
				storeSeatDto.setError("场地编码不合法");
				failList.add(storeSeatDto);
				continue;
			}
			if (!areaCode.matches("[0-9A-Za-z]{5,25}")) {
				storeSeatDto.setError("库区编码不合法");
				failList.add(storeSeatDto);
				continue;
			}

			try {
				List<String> list = storeSeatMapper.selectSeatCode(placeId);
				if ((!seatCodes.contains(seatCode)) && (!list.contains(seatCode))) {
					seatCodes.add(seatCode);
					//到这里说明字段合法
					successList.add(storeSeatDto);
				} else {
					storeSeatDto.setError("该库位编码已经存在");
					failList.add(storeSeatDto);
				}
			} catch (Exception e) {
				log.error("=校验对象异常");
			}
		}
		map.put("success", successList);
		map.put("fail", failList);
		return map;
	}

	/**
	 * 库位设置下载模板
	 *
	 * @param
	 * @return
	 * @Author: zy
	 */
	@RequestMapping(value = "/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		new ExcelImportUtils().dowFileUtils(response, request, STORE_SEAT_MODEL, TMP_PATH);
	}

}
