package com.nice.good.service.impl;


import com.nice.good.aop.LogAnnotation;
import com.nice.good.constant.ID_PREFIX;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.*;
import com.nice.good.dto.GoodImportDto;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.*;
import com.nice.good.service.GoodImgService;
import com.nice.good.service.GoodPictureService;
import com.nice.good.service.GoodSkuService;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.service.GoodService;
import com.nice.good.core.AbstractService;
import com.nice.good.vo.GoodVo;
import com.nice.good.wx_model.GoodImg;
import com.nice.good.wx_model.GoodSku;
import com.nice.good.wx_model.GoodSkuVo;
import com.nice.good.wx_model.GoodSpu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;


/**
 * @Description: 货品档案
 * @Author: fqs
 * @Date: 2018/3/23 10:29
 * @Version: 1.0
 */
@Service
@Transactional
public class GoodServiceImpl extends AbstractService<Good> implements GoodService {
	@Resource
	private GoodMapper goodMapper;

	@Resource
	private GoodConfigMapper goodConfigMapper;

	@Resource
	private GoodAreaMapper goodAreaMapper;


	@Resource
	private GoodAliasMapper goodAliasMapper;


	@Resource
	private OrderGoodMappingMapper orderGoodMappingMapper;

	@Resource
	private ReceiveDetailMapper receiveDetailMapper;


	@Resource
	private OutDetailMapper outDetailMapper;


	@Resource
	private GoodPictureMapper goodPictureMapper;


	@Resource
	private GooderConfigMapper gooderConfigMapper;


	@Resource
	private GooderAreaMapper gooderAreaMapper;


	@Resource
	private RfidLabelMapper rfidLabelMapper;

	@Resource
	private GoodStyleMapper goodStyleMapper;

	@Resource
	private GoodCategoryMapper goodCategoryMapper;


	@Resource
	private SysPlaceMapper sysPlaceMapper;

	@Resource
	private GoodSpuMapper goodSpuMapper;

	@Resource
	private GoodSkuMapper goodSkuMapper;

	@Resource
	private GoodSkuService goodSkuService;
	@Resource
	private GoodImgService goodImgService;
	@Resource
	private GoodPictureService goodPictureService;

	@Override
	public String goodAdd(Good good, String placeId, String userId) throws Exception {


		String errorMsg;
		String goodId = good.getGoodId();
		if (goodId == null) {
			//新增操作
			goodId = IdsUtils.getOrderId();
//			goodId = good.getGoodCode();
			errorMsg = CheckUniqueFileds(good, goodId);
			if (errorMsg != null) {
				return errorMsg;
			}

			if (StringUtils.isBlank(good.getGoodCode())) {
				errorMsg = "货品编码不能为空!";
				return errorMsg;
			}


			String commodityCode = good.getCommodityCode();
			if (StringUtils.isBlank(commodityCode)) {
				errorMsg = "商品编码不能为空!";
				return errorMsg;
			}

			//所有信息必须全部合法才能提交
			List<GoodAlias> goodAliasList = good.getGoodAlias();
			if (goodAliasList != null && goodAliasList.size() > 0) {
				for (GoodAlias goodAlias : goodAliasList) {
					if (goodAlias != null) {
						//货品别名编码,货品别名类型不可重复
						String id = goodAlias.getAliasId();
						errorMsg = CheckUniqueFiled(goodAlias, id);
						if (errorMsg != null) {
							return errorMsg;
						}
					}
				}
			}


			//货品档案基本信息
			good.setGoodId(goodId);
			good.setCreater(userId);
			good.setModifier(userId);
			good.setCreatetime(TimeStampUtils.getTimeStamp());
			good.setModifytime(TimeStampUtils.getTimeStamp());


			String gooderCode = good.getGooderCode();

			//货品档案配置信息
			GoodConfig goodConfig = good.getGoodConfig();

			if (goodConfig != null) {
				//货品档案配置默认继承货主档案配置
				GooderConfig gooderConfig = gooderConfigMapper.selectByGooderCodeAndPlaceId(gooderCode, placeId);
				if (gooderConfig != null) {
					//无PO收货
					String noPoReceive = goodConfig.getNoPoReceive();
					if (StringUtils.isEmpty(noPoReceive)) {
						goodConfig.setNoPoReceive(gooderConfig.getNoPoReceive());
					}
					//超量验证
					Integer beyondVerify = goodConfig.getBeyondVerify();
					if (beyondVerify == null || beyondVerify.toString() == "") {
						goodConfig.setBeyondVerify(gooderConfig.getBeyondVerify());
					}
					//超收比例
					Double beyondRatio = goodConfig.getBeyondRatio();
					if (beyondRatio == null || beyondRatio.toString() == "") {
						goodConfig.setBeyondRatio(gooderConfig.getBeyondRatio());
					}
					//是否质检
					Integer isQuality = goodConfig.getIsQuality();
					if (isQuality == null || isQuality.toString() == "") {
						goodConfig.setIsQuality(gooderConfig.getIsQuality());
					}
					//rfid采集
					Integer rfidGather = goodConfig.getRfidGather();
					if (rfidGather == null || rfidGather.toString() == "") {
						goodConfig.setRfidGather(gooderConfig.getRfidGather());
					}
					//进货质检库位
					String inQualitySeat = goodConfig.getInQualitySeat();
					if (StringUtils.isEmpty(inQualitySeat)) {
						goodConfig.setInQualitySeat(gooderConfig.getInQualitySeat());
					}
					//退货接收库位
					String outAcceptSeat = goodConfig.getOutAcceptSeat();
					if (StringUtils.isEmpty(outAcceptSeat)) {
						goodConfig.setOutAcceptSeat(gooderConfig.getOutAcceptSeat());
					}
					//次品接收库位
					String badAcceptSeat = goodConfig.getBadAcceptSeat();
					if (StringUtils.isEmpty(badAcceptSeat)) {
						goodConfig.setBadAcceptSeat(gooderConfig.getBadAcceptSeat());
					}
					//出库待选库位
					String outWaitSeat = goodConfig.getOutWaitSeat();
					if (StringUtils.isEmpty(outWaitSeat)) {
						goodConfig.setOutWaitSeat(gooderConfig.getOutWaitSeat());
					}
					//上架策略
					String putStrategy = goodConfig.getPutStrategy();
					if (StringUtils.isEmpty(putStrategy)) {
						goodConfig.setPutStrategy(gooderConfig.getPutStrategy());
					}
					//分配策略
					String allotStrategy = goodConfig.getAllotStrategy();
					if (StringUtils.isEmpty(allotStrategy)) {
						goodConfig.setAllotStrategy(gooderConfig.getAllotStrategy());
					}
					//周转规则
					Integer runRule = goodConfig.getRunRule();
					if (runRule == null || runRule.toString() == "") {
						goodConfig.setRunRule(gooderConfig.getRunRule());
					}
					//库存周转
					String stockRun = goodConfig.getStockRun();
					if (StringUtils.isEmpty(stockRun)) {
						goodConfig.setStockRun(gooderConfig.getStockRun());
					}
					//盘点自动处理
					Integer autoDeal = goodConfig.getAutoDeal();
					if (autoDeal == null || autoDeal.toString() == "") {
						goodConfig.setAutoDeal(gooderConfig.getAutoDeal());
					}
					//混放箱号
					Integer mixBoxNum = goodConfig.getMixBoxNum();
					if (mixBoxNum == null || mixBoxNum.toString() == "") {
						goodConfig.setMixBoxNum(gooderConfig.getMixBoxNum());
					}

				}

				goodConfig.setConfigId(IdsUtils.getOrderId());
				goodConfig.setCreater(userId);
				goodConfig.setModifier(userId);
				goodConfig.setCreatetime(TimeStampUtils.getTimeStamp());
				goodConfig.setModifytime(TimeStampUtils.getTimeStamp());
				//关联主表id
				goodConfig.setGoodId(goodId);

				//关联场地id
				goodConfig.setPlaceId(placeId);


				//出库待选库位不能为空
				if (StringUtils.isBlank(goodConfig.getOutWaitSeat())) {
					errorMsg = "出库待选库位不能为空!";
					return errorMsg;
				}

				goodConfigMapper.insert(goodConfig);

				//根据场地生成多套配置
				generateGoodConfig(placeId, userId, goodId, gooderCode);

			}


			List<GoodArea> goodAreas = good.getGoodAreas();


			//货品档案库区设置信息
			if (goodAreas == null || goodAreas.size() == 0) {
				List<GooderArea> gooderAreas = gooderAreaMapper.selectAreaByGooderCode(gooderCode, placeId);
				if (gooderAreas != null && gooderAreas.size() > 0) {
					for (GooderArea gooderArea : gooderAreas) {
						GoodArea goodArea = new GoodArea();
						goodArea.setAreaId(IdsUtils.getOrderId());
						//关联主表id
						goodArea.setGoodId(goodId);
						goodArea.setAreaCode(gooderArea.getAreaCode());
						goodArea.setAreaName(gooderArea.getAreaName());
						goodArea.setAreaType(gooderArea.getAreaType());
						goodArea.setMaxNum(gooderArea.getMaxNum());
						goodArea.setMinNum(gooderArea.getMinNum());
						goodArea.setFistArea(gooderArea.getFistArea());
						goodArea.setCreater(userId);
						goodArea.setCreatetime(TimeStampUtils.getTimeStamp());
						goodArea.setModifier(userId);
						goodArea.setModifytime(TimeStampUtils.getTimeStamp());

						//关联场地id
						goodArea.setPlaceId(placeId);
						goodAreaMapper.insert(goodArea);
					}
				} else {

					String putStrategy = goodConfig.getPutStrategy();
					if ("查找货品设置库区中可用库位".equals(putStrategy)) {

						errorMsg = "库区设置不能为空!";
						return errorMsg;
					}
				}

			} else {

				for (GoodArea goodArea : goodAreas) {
					if (goodArea != null) {
						addGoodArea(userId, goodId, goodArea);
					}
				}
			}
			//货品档案别名
			if (goodAliasList != null && goodAliasList.size() > 0) {
				for (GoodAlias goodAlias : goodAliasList) {
					if (goodAlias != null) {
						goodAlias.setAliasId(IdsUtils.getOrderId());
						goodAlias.setCreater(userId);
						goodAlias.setModifier(userId);
						goodAlias.setCreatetime(TimeStampUtils.getTimeStamp());
						goodAlias.setModifytime(TimeStampUtils.getTimeStamp());
						//关联主表id
						goodAlias.setGoodId(goodId);
						goodAliasMapper.insert(goodAlias);
					}
				}
			}

			//图片绑定货品goodId
			updateImg(good, userId, goodId);

			goodMapper.insert(good);


			//同步到小程序
			synchronizeXiao(good);


		} else {
			//修改操作
			errorMsg = goodUpdate(good, userId);
			if (errorMsg != null) {
				return errorMsg;
			}
		}
		return errorMsg;
	}

	private void synchronizeXiao(Good good) {
		GoodSpu spu = goodSpuMapper.getSpuByGooderAndCommodityCode(good.getGooderCode(), good.getCommodityCode());
		if (spu == null) {
			spu = new GoodSpu();
			spu.setBrandId(0);
			spu.setBrokerageLink(good.getBrokerageLink());
			spu.setBrokerageRatio(good.getBrokerageRatio());
			spu.setBulk(good.getBulk());
			spu.setCompanyId(good.getCompanyId());
			spu.setCategoryId(good.getCategoryId());
			spu.setGoodLink(good.getGoodLink());
			spu.setGooderCode(good.getGooderCode());
			spu.setNetWeight(good.getNetWeight());
			spu.setPackCode(good.getPackCode());
			spu.setPeriod(good.getPeriod());
			spu.setPeriodUnite(good.getPeriodUnite());
			spu.setPriceSection("");
			spu.setProperty(good.getProperty());
			spu.setRemark(good.getRemark());
			spu.setRoughWeight(good.getRoughWeight());
			spu.setSpuCode(good.getCommodityCode());
			spu.setSpuName(good.getGoodName());
			spu.setStatus(good.getStatus());
			spu.setStyleId(good.getStyleId());
			spu.setTareWeight(good.getTareWeight());

			goodSpuMapper.insert(spu);
		}
		GoodSku sku = new GoodSku();
		sku.setBrokerageLink(good.getBrokerageLink());
		sku.setBrokerageRatio(good.getBrokerageRatio());
		sku.setCompanyId(good.getCompanyId());
		sku.setDiscountContent(good.getDiscountContent());
		sku.setDiscountLink(good.getDiscountLink());
		sku.setDiscountMethod(good.getDiscountMethod());
		sku.setGoodColor(good.getGoodColor());
		sku.setGoodSize(good.getGoodSize());
		sku.setNormalPrice(good.getNormalPrice());
		sku.setRemark(good.getRemark());
		sku.setSeckillPrice(good.getSeckillPrice());
		sku.setSkuCode(good.getGoodCode());
		sku.setSkuName(good.getGoodName());
		sku.setSpuCode(good.getCommodityCode());
		sku.setStatus(good.getStatus());
		goodSkuMapper.insert(sku);
	}

	private void generateGoodConfig(String placeId, String userId, String goodId, String gooderCode) {
		List<SysPlace> places = sysPlaceMapper.selectAll();
		if (places != null && places.size() > 0) {
			for (SysPlace place : places) {
				String placeId1 = place.getPlaceId();
				if (!placeId.equals(placeId1)) {
					//查找对应货主配置
					GooderConfig gooderConfig1 = gooderConfigMapper.selectByGooderCodeAndPlaceId(gooderCode, placeId1);

					GoodConfig goodConfig1 = new GoodConfig();
					BeanUtils.copyProperties(gooderConfig1, goodConfig1);
					//主键id
					goodConfig1.setConfigId(IdsUtils.getOrderId());
					goodConfig1.setId(null);
					goodConfig1.setCreater(userId);
					goodConfig1.setCreatetime(TimeStampUtils.getTimeStamp());
					goodConfig1.setModifier(userId);
					goodConfig1.setModifytime(TimeStampUtils.getTimeStamp());

					//关联场地
					goodConfig1.setPlaceId(placeId1);


					//关联货品
					goodConfig1.setGoodId(goodId);

					goodConfigMapper.insert(goodConfig1);
				}
			}
		}
	}

	/**
	 * 货品档案更新
	 *
	 * @param good
	 * @param userId
	 * @return
	 */
	private String goodUpdate(Good good, String userId) throws Exception {

		Timestamp timeStamp = TimeStampUtils.getTimeStamp();
		String errorMsg = "";
		String goodId = good.getGoodId();
//		errorMsg = CheckUniqueFileds(good, goodId);
//		if (errorMsg != null) {
//			return errorMsg;
//		}

		//所有信息必须全部合法才能提交
		List<GoodAlias> goodAliasList = good.getGoodAlias();
		if (goodAliasList != null && goodAliasList.size() > 0) {
			for (GoodAlias goodAlias : goodAliasList) {
				if (goodAlias != null) {
					//货品别名编码,货品别名类型不可重复
					String id = goodAlias.getAliasId();
					errorMsg = CheckUniqueFiled(goodAlias, id);
					if (errorMsg != null) {
						return errorMsg;
					}
				}
			}
		}

		String commodityCode = good.getCommodityCode();
		if (StringUtils.isBlank(commodityCode)) {
			errorMsg = "商品编码不能为空!";
			return errorMsg;
		}


		//货品配置信息更新
		GoodConfig goodConfig = good.getGoodConfig();

		//无PO收货
		String noPoReceive = goodConfig.getNoPoReceive();
		if (StringUtils.isEmpty(noPoReceive)) {
			errorMsg = "无PO收货不能为空!";
			return errorMsg;
		}
		//超量验证
		Integer beyondVerify = goodConfig.getBeyondVerify();
		if (beyondVerify == null || beyondVerify.toString() == "") {
			errorMsg = "超量验证不能为空!";
			return errorMsg;
		}

		//是否质检
		Integer isQuality = goodConfig.getIsQuality();
		if (isQuality == null || isQuality.toString() == "") {
			errorMsg = "是否质检不能为空!";
			return errorMsg;
		}
		//rfid采集
		Integer rfidGather = goodConfig.getRfidGather();
		if (rfidGather == null || rfidGather.toString() == "") {
			errorMsg = "是否RFID采集不能为空!";
			return errorMsg;
		}

		//货品档案配置中,如果已经有了rfid采集的记录,没有出库,则不能进行修改
		if (goodConfig.getRfidGather() != 1) {
			List<RfidLabel> rfidLabels = rfidLabelMapper.selectByGooderAndGoodCode(good.getGooderCode(), good.getGoodCode());
			if (rfidLabels != null && rfidLabels.size() > 0) {
				errorMsg = "存在已采集的货品尚未出库,更新失败!";
				return errorMsg;
			}
		}

		//出库待选库位
		String outWaitSeat = goodConfig.getOutWaitSeat();
		if (StringUtils.isEmpty(outWaitSeat)) {
			errorMsg = "出库待选库位不能为空!";
			return errorMsg;
		}
		//上架策略
		String putStrategy = goodConfig.getPutStrategy();
		if (StringUtils.isEmpty(putStrategy)) {
			errorMsg = "上架策略不能为空!";
			return errorMsg;
		}
		//分配策略
		String allotStrategy = goodConfig.getAllotStrategy();
		if (StringUtils.isEmpty(allotStrategy)) {
			errorMsg = "分配策略不能为空!";
			return errorMsg;
		}
		//周转规则
		Integer runRule = goodConfig.getRunRule();
		if (runRule == null || runRule.toString() == "") {
			errorMsg = "周转规则不能为空!";
			return errorMsg;
		}
		//库存周转
		String stockRun = goodConfig.getStockRun();
		if (StringUtils.isEmpty(stockRun)) {
			errorMsg = "库存周转不能为空!";
			return errorMsg;
		}
		//盘点自动处理
		Integer autoDeal = goodConfig.getAutoDeal();
		if (autoDeal == null || autoDeal.toString() == "") {
			errorMsg = "盘点自动处理不能为空!";
			return errorMsg;
		}
		//混放箱号
		Integer mixBoxNum = goodConfig.getMixBoxNum();
		if (mixBoxNum == null || mixBoxNum.toString() == "") {
			errorMsg = "混放箱号不能为空!";
			return errorMsg;
		}


		goodConfig.setModifier(userId);
		goodConfig.setModifytime(timeStamp);
		goodConfigMapper.updateByPrimaryKey(goodConfig);

		//货品档案库区设置更新
		List<GoodArea> goodAreas = good.getGoodAreas();

		if ("查找货品设置库区中可用库位".equals(putStrategy)) {
			if (goodAreas == null || goodAreas.size() == 0) {
				errorMsg = "库区设置不能为空!";
				return errorMsg;
			}
		}

		if (goodAreas != null && goodAreas.size() > 0) {
			for (GoodArea goodArea : goodAreas) {
				if (goodArea != null) {
					String areaId = goodArea.getAreaId();
					if (areaId == null) {
						//新增库区
						addGoodArea(userId, goodId, goodArea);
					} else {
						//修改库区
						goodArea.setModifier(userId);
						goodArea.setModifytime(timeStamp);

						goodAreaMapper.updateByPrimaryKey(goodArea);
					}

				}
			}
		}

		//货品别名更新
		if (goodAliasList != null && goodAliasList.size() > 0) {
			for (GoodAlias goodAlias : goodAliasList) {
				if (goodAlias != null) {
					String aliasId = goodAlias.getAliasId();
					if (aliasId == null) {
						//新增操作
						aliasId = IdsUtils.getOrderId();

						errorMsg = CheckUniqueFiled(goodAlias, aliasId);
						if (errorMsg != null) {
							return errorMsg;
						}

						goodAlias.setAliasId(IdsUtils.getOrderId());
						goodAlias.setCreater(userId);
						goodAlias.setModifier(userId);
						goodAlias.setCreatetime(TimeStampUtils.getTimeStamp());
						goodAlias.setModifytime(TimeStampUtils.getTimeStamp());
						//关联主表id
						goodAlias.setGoodId(goodId);
						goodAliasMapper.insert(goodAlias);

					} else {
						//修改操作
						errorMsg = CheckUniqueFiled(goodAlias, aliasId);
						if (errorMsg != null) {
							return errorMsg;
						}
						goodAlias.setModifier(userId);
						goodAlias.setModifytime(timeStamp);
						goodAliasMapper.updateByPrimaryKey(goodAlias);
					}
				}
			}

		}

		//图片绑定货品goodId
		updateImg(good, userId, goodId);


		//货品基本信息更新
		good.setModifier(userId);
		good.setModifytime(timeStamp);
		goodMapper.updateByPrimaryKey(good);


		return errorMsg;
	}

	private void addGoodArea(String userId, String goodId, GoodArea goodArea) throws Exception {
		goodArea.setAreaId(IdsUtils.getOrderId());
		goodArea.setCreater(userId);
		goodArea.setModifier(userId);
		goodArea.setCreatetime(TimeStampUtils.getTimeStamp());
		goodArea.setModifytime(TimeStampUtils.getTimeStamp());
		//关联主表id
		goodArea.setGoodId(goodId);

		goodAreaMapper.insert(goodArea);
	}

	private void updateImg(Good good, String userId, String goodId) {
		List<String> imgIds = good.getImgIds();
		if (imgIds != null && imgIds.size() > 0) {
			for (String imgId : imgIds) {
				if (imgId != null) {
					Long newImgId = Long.valueOf(imgId.substring(0, imgId.lastIndexOf(".")));
					//继续新增图片
					GoodPicture goodPicture = goodPictureMapper.selectByPrimaryKey(newImgId);
					if (goodPicture == null) {
						continue;
					}

					if (goodPicture.getGoodId() != null) {
						continue;
					}
					goodPicture.setGoodId(goodId);
					goodPicture.setModifier(userId);
					goodPicture.setModifytime(TimeStampUtils.getTimeStamp());
					goodPictureMapper.updateByPrimaryKey(goodPicture);
				}
			}
		}
        //同步商品图片到小程序
        parseImg(userId);
	}


	@Override
	public String deleteGoodByGoodId(List<String> goodIds, String placeId) {

		String errorMsg = "";

		for (String goodId : goodIds) {
			if (goodId == null) {
				continue;
			}

			Good good = goodMapper.selectByPrimaryKey(goodId);

			if (good == null) {
				continue;
			}

			String gooderCode = good.getGooderCode();
			String goodCode = good.getGoodCode();


			//如果货品在采购单中有关联,则不能删除
			List<String> orderIds = orderGoodMappingMapper.selectByGoodCode(goodCode);
			if (orderIds != null && orderIds.size() > 0) {
				errorMsg += "货品" + goodCode + "和采购单有关联,删除失败!\n";
				continue;
			}

			//如果货品在收货单中有关联,则不能删除
			List<String> detailIds = receiveDetailMapper.selectByGooderAndCode(gooderCode, goodCode);
			if (detailIds != null && detailIds.size() > 0) {
				errorMsg += "货品" + goodCode + "和收货单有关联,删除失败!\n";
				continue;
			}

			//如果货品在出库单中有关联,则不能删除
			List<String> outIds = outDetailMapper.selectByGooderAndCode(gooderCode, goodCode);
			if (outIds != null && outIds.size() > 0) {
				errorMsg += "货品" + goodCode + "和出库单有关联,删除失败!\n";
				continue;
			}


			//删除子表-配置表
			goodConfigMapper.deleteByGoodId(goodId);
			//删除子表-库区设置表
			goodAreaMapper.deleteByGoodId(goodId, placeId);
			//删除子表货品别名表
			goodAliasMapper.deleteByGoodId(goodId);
			//删除图片
			goodPictureMapper.deleteByGoodId(goodId);

			//同步删除小程序表中的数据
            goodMapper.deleteBySkuCode(goodId);

            String commodityCode = good.getCommodityCode();

            String spu = goodMapper.selectBySpuCode(commodityCode);
            if (spu==null){
                goodMapper.deleteBySpuCode(spu);
            }

            //删除主表
			goodMapper.deleteByPrimaryKey(goodId);

		}

		return errorMsg;
	}


	@Override
	public List<Good> findByConditions(Good good) {

		Map<String, Object> conditionMap = new HashMap<>();

		String gooderCode = null;

		String commodityCode = null;

		String goodModel = null;

		Integer status = null;

		String goodName = null;

		String goodCode = null;

		String packCode = null;

		String aliasCode = null;


		if (good != null) {
			gooderCode = good.getGooderCode();
			commodityCode = good.getCommodityCode();
			goodModel = good.getGoodModel();
			status = good.getStatus();
			goodName = good.getGoodName();
			goodCode = good.getGoodCode();
			packCode = good.getPackCode();
			aliasCode = good.getAliasCode();
		}

		//货主名称模糊匹配

		conditionMap.put("gooderCode", gooderCode);

		//商品编码模糊匹配

		conditionMap.put("commodityCode", commodityCode);

		//货品规格模糊查询

		conditionMap.put("goodModel", goodModel);

		conditionMap.put("status", status);

		conditionMap.put("goodName", goodName);

		//货品编码

		conditionMap.put("goodCode", goodCode);

		//包装

		conditionMap.put("packCode", packCode);

		//别名编码模糊匹配

		conditionMap.put("aliasCode", aliasCode);


		return goodMapper.selectByConditionMap(conditionMap);


	}

	/**
	 * 货品别名修改删除操作
	 *
	 * @param aliasIds
	 * @return
	 */
	@Override
	public String delGoodAlias(List<String> aliasIds) {
		String errorMsg = "";
		for (String aliasId : aliasIds) {
			if (aliasId == null) {
				continue;
			}
			goodAliasMapper.deleteByPrimaryKey(aliasId);

		}
		return errorMsg;
	}

	/**
	 * 修改新增货品别名
	 *
	 * @param goodAliasList
	 * @return
	 */
	@Override
	public String addGoodAlias(List<GoodAlias> goodAliasList, String goodId, String userId) throws Exception {

		String errorMsg = "";

		for (GoodAlias goodAlias : goodAliasList) {
			if (StringUtils.isBlank(goodAlias.getGooderCode())) {
				errorMsg = "货主编码不能为空!";
				return errorMsg;

			}

			if (StringUtils.isBlank(goodAlias.getGoodCode())) {
				errorMsg = "货品编码不能为空!";
				return errorMsg;

			}

			if (StringUtils.isBlank(goodId)) {
				errorMsg = "货品ID不能为空!";
				return errorMsg;

			}

			Timestamp timeStamp = TimeStampUtils.getTimeStamp();
			//关联主表id
			goodAlias.setGoodId(goodId);

			goodAlias.setAliasId(IdsUtils.getOrderId());
			goodAlias.setCreater(userId);
			goodAlias.setCreatetime(timeStamp);
			goodAlias.setModifier(userId);
			goodAlias.setModifytime(timeStamp);

			goodAliasMapper.insert(goodAlias);
		}
		return errorMsg;
	}

	/**
	 * 货品别名刷新
	 *
	 * @param goodId
	 * @return
	 */
	@Override
	public Result listGoodAlias(String goodId) {
		Result result = new Result();
		List<GoodAlias> goodAliasList = goodAliasMapper.selectAliasByGoodId(goodId);
		result.setData(goodAliasList);
		return result;
	}

	/**
	 * 通过excel导入库位信息
	 *
	 * @param
	 * @Author: zy
	 */
	@LogAnnotation(logType = "图片导入", content = "图片导入")
	@Override
	@Transactional
	public String uploadExcelForAddStoreSeat(List<GoodImportDto> success, String placeId, String userId) throws Exception {


		Good goods;
		List<Good> good = new ArrayList<Good>();
		List<GoodConfig> goodConfigs = new ArrayList<>();
		List<GoodArea> goodAreas = new ArrayList<>();
		for (GoodImportDto goodImportDto : success) {
			goods = new Good();

			GoodConfig goodConfig = new GoodConfig();
			GoodArea goodArea = new GoodArea();
			GooderConfig gooderConfig = new GooderConfig();
			GooderArea gooderArea = new GooderArea();
			BeanUtils.copyProperties(goodImportDto, goods);
			//将不是String类型的字段做转换
			if (StringUtils.isNotBlank(goodImportDto.getBulk())) {
				goods.setBulk(Double.valueOf(goodImportDto.getBulk()));
			}
			if (StringUtils.isNotBlank(goodImportDto.getNetWeight())) {
				goods.setNetWeight(Double.valueOf(goodImportDto.getNetWeight()));
			}
			if (StringUtils.isNotBlank(goodImportDto.getTareWeight())) {
				goods.setTareWeight(Double.valueOf(goodImportDto.getTareWeight()));
			}
			if (StringUtils.isNotBlank(goodImportDto.getRoughWeight())) {
				goods.setRoughWeight(Double.valueOf(goodImportDto.getRoughWeight()));
			}
			if (StringUtils.isNotBlank(goodImportDto.getBrokerageRatio())) {
				goods.setBrokerageRatio(Double.valueOf(goodImportDto.getBrokerageRatio()));
			}
			if (StringUtils.isNotBlank(goodImportDto.getNormalPrice())) {
				goods.setNormalPrice(Double.valueOf(goodImportDto.getNormalPrice()));
			}
			String seckillPrice = goodImportDto.getSeckillPrice();
			if (StringUtils.isNotBlank(seckillPrice)) {
				goods.setSeckillPrice(Double.valueOf(seckillPrice));
			}
			//根据风格和类目的名字查询id
			String styleName = goodImportDto.getStyleName();
			String categoryName = goodImportDto.getCategoryName();
			Integer styleId = goodStyleMapper.findStyleId(styleName);
			Integer categoryId = goodCategoryMapper.findCategoryId(categoryName);
			goods.setStyleId(styleId);
			goods.setCategoryId(categoryId);

			goods.setStatus(goodImportDto.getStatusInt());
//			String goodId = goodImportDto.getGoodCode();

            String goodId =IdsUtils.getOrderId();
                    //向goodImportDto写入goodId;
			goodImportDto.setGoodId(goodId);

			goods.setGoodId(goodId);
			goods.setCreater(userId);
			goods.setModifier(userId);
			good.add(goods);
			//获取配置信息
			String gooderCode = goodImportDto.getGooderCode();
			gooderConfig = gooderConfigMapper.selectByGooderCodeAndPlaceId(gooderCode, placeId);
			//这里继承的是首选区,需要添加条件 fistArea=1
			Integer firstArea = 1;
			gooderArea = gooderAreaMapper.selectByGooderCodeAndPlaceId(gooderCode, placeId, firstArea);
			if (gooderConfig != null) {
				BeanUtils.copyProperties(gooderConfig, goodConfig);
			}


			goodConfig.setConfigId(IdsUtils.getOrderId());
			goodConfig.setCreater(userId);
			goodConfig.setModifier(userId);
			goodConfig.setCreatetime(TimeStampUtils.getTimeStamp());
			goodConfig.setModifytime(TimeStampUtils.getTimeStamp());
			//关联主表id
			goodConfig.setGoodId(goodId);
			goodConfig.setId(null);
			if (goodConfig != null) {
				goodConfigMapper.insert(goodConfig);
			}


			//根据场地生成多套配置
			generateGoodConfig(placeId, userId, goodId, gooderCode);

			//获取库区设置
			if (gooderArea != null) {
				BeanUtils.copyProperties(gooderArea, goodArea);
				goodArea.setAreaId(IdsUtils.getOrderId());
				goodArea.setCreater(userId);
				goodArea.setCreatetime(TimeStampUtils.getTimeStamp());
				goodArea.setModifier(userId);
				goodArea.setModifytime(TimeStampUtils.getTimeStamp());
				goodArea.setGoodId(goodId);
				goodArea.setId(null);
				goodAreaMapper.insert(goodArea);
			}


		}
		if (good.size() > 0) {
//				goodMapper.batchInsertList(good);//批量保存
			// goodMapper.insertList(good);
			for (Good good5 : good) {

				synchronizeXiao(good5);   //同步sku  spu

				good5.setCreater(userId);
				good5.setCreatetime(TimeStampUtils.getTimeStamp());
				good5.setModifier(userId);
				good5.setModifytime(TimeStampUtils.getTimeStamp());
				goodMapper.insert(good5);
			}
		}

		return "操作成功!";

	}


//	@Override
//	public List<GoodVo> getGoodList(String seatCode) {
//		List<GoodVo> list = goodMapper.getGoodList(seatCode);
//		List<GoodVo> goodVos = new ArrayList<>();
//		for (GoodVo goodVo : list) {
//			List<GoodPicture> goodPictures = findPicturesByGoodId(goodVo.getGoodId());
//			goodVo.setGoodPictures(goodPictures);
//			goodVos.add(goodVo);
//		}
//		return goodVos;
//	}
//
//	private List<GoodPicture> findPicturesByGoodId(String goodId) {
//		return goodMapper.findPicturesByGoodId(goodId);
//	}

	private String CheckUniqueFileds(Good good, String goodId) {


		String gooderCode = good.getGooderCode();
		String commodityCode = good.getCommodityCode();
		String goodCode = good.getGoodCode();
		String errorMsg = null;
//		if (StringUtils.isNotBlank(gooderCode) && StringUtils.isNotBlank(commodityCode)) {
//			String id = goodMapper.findIdByCommodityCode(gooderCode, commodityCode);
//			if (id != null && !goodId.equals(id)) {
//				errorMsg = "商品编码不能重复！";
//				return errorMsg;
//			}
//		}

		//一个货品不能有多个商品编码
		if (StringUtils.isNotBlank(gooderCode) && StringUtils.isNotBlank(goodCode)) {
			String commodityCode1 = goodMapper.selectCommodityCode(gooderCode, goodCode);
			if (commodityCode1 != null && !commodityCode1.equals(commodityCode)) {
				errorMsg = "商品品编码有误!";
				return errorMsg;
			}
		}


		if (StringUtils.isNotBlank(gooderCode) && StringUtils.isNotBlank(goodCode)) {
			String goodId1 = goodMapper.findIdByGoodCode(gooderCode, goodCode);
			if (goodId1 != null && goodId.equals(goodId1)) {

				errorMsg = "货品编码不能重复！";

				return errorMsg;
			}
		}
		return errorMsg;

	}

	private String CheckUniqueFiled(GoodAlias goodAlias, String id) {

		String aliasCode = goodAlias.getAliasCode();
		String aliasType = goodAlias.getAliasType();
		String errorMsg = null;
		if (StringUtils.isNotBlank(aliasCode)) {
			String aliasId = goodAliasMapper.findIdByAliasCode(aliasCode);

			if (aliasId != null && !id.equals(aliasId)) {

				errorMsg = "货品别名不能重复!";

				return errorMsg;
			}
		}
		if (StringUtils.isNotBlank(aliasType)) {
			String aliasId = goodAliasMapper.findIdByAliasType(aliasType);
			if (aliasId != null && !id.equals(aliasId)) {
				errorMsg = "货品别名类型不可重复!";

				return errorMsg;
			}

		}
		return errorMsg;
	}


    /**
     * 图片同步小程序
     * @param userId
     */

    @Override
	public void parseImg(String userId) {
		List<GoodSku> skuList = goodSkuService.findAll();
		for(GoodSku goodSku:skuList){
			Good good = getOneByGoodCode(goodSku.getSkuCode());
			if(good==null){
			    continue;
            }
			String goodId = good.getGoodId();
			List<GoodPicture> goodPictures = goodPictureService.getByGoodId(goodId);
			for(int i=0;i<goodPictures.size();i++){
				Map<String,Object> params = new HashMap<>();
				params.put("skuCode",goodSku.getSkuCode());
				params.put("imgUrl",goodPictures.get(i).getImgUrl());
				List<GoodImg> goodImgs = goodImgService.selectByParams(params);
				if(goodImgs ==null || goodImgs.size()<=0) {
					GoodImg img = goodImgService.getBySpu(goodSku.getSpuCode());
					if (i == 0 && img == null) {
						img = new GoodImg();
						img.setImgName(goodPictures.get(i).getImgName());
						img.setImgUrl(goodPictures.get(i).getImgUrl());
						img.setIsMain(1);
						img.setSkuCode(goodSku.getSkuCode());
						img.setSpuCode(goodSku.getSpuCode());
					} else {
						img = new GoodImg();
						img.setImgName(goodPictures.get(i).getImgName());
						img.setImgUrl(goodPictures.get(i).getImgUrl());
						img.setSkuCode(goodSku.getSkuCode());
						img.setSpuCode(goodSku.getSpuCode());
					}
					img.setCreater(userId);
					img.setCreatetime(TimeStampUtils.getTimeStamp());
					img.setModifier(userId);
					img.setModifytime(TimeStampUtils.getTimeStamp());

					goodImgService.save(img);
				}
			}
		}
	}
	private Good getOneByGoodCode(String goodCode) {
		return goodMapper.getOneByGoodCode(goodCode);
	}
}

