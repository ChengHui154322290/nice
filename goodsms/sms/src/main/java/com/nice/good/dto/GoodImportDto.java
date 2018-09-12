package com.nice.good.dto;

import com.nice.good.model.GoodAlias;
import com.nice.good.model.GoodArea;
import com.nice.good.model.GoodConfig;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

public class GoodImportDto {
	private String goodId;
	private Integer id;
	/**
	 * 货主名称
	 */
	private String gooderName;
	private String fileName;

	private String brand; //品牌

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBrand() {

		return brand;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getGoodId() {
		return goodId;
	}

	public Integer getId() {
		return id;
	}

	public String getGooderName() {
		return gooderName;
	}

	public String getGooderCode() {
		return gooderCode;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public String getGoodCode() {
		return goodCode;
	}

	public String getGoodModel() {
		return goodModel;
	}

	public String getGoodName() {
		return goodName;
	}

	public String getStatus() {
		return status;
	}

	public String getPackCode() {
		return packCode;
	}

	public String getBulk() {
		return bulk;
	}

	public String getNetWeight() {
		return netWeight;
	}

	public String getTareWeight() {
		return tareWeight;
	}

	public String getRoughWeight() {
		return roughWeight;
	}

	public String getProperty() {
		return property;
	}

	public String getPeriod() {
		return period;
	}

	public String getPeriodUnite() {
		return periodUnite;
	}

	public String getExhibition() {
		return exhibition;
	}

	public String getRemark() {
		return remark;
	}

	public String getCreater() {
		return creater;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public String getModifier() {
		return modifier;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public String getAliasCode() {
		return aliasCode;
	}

	public GoodConfig getGoodConfig() {
		return goodConfig;
	}

	public List<GoodArea> getGoodAreas() {
		return goodAreas;
	}

	public List<GoodAlias> getGoodAlias() {
		return goodAlias;
	}

	public List<String> getImgIds() {
		return imgIds;
	}


	/**
	 * 货主编码
	 */
	private String gooderCode;

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setGooderName(String gooderName) {
		this.gooderName = gooderName;
	}

	public void setGooderCode(String gooderCode) {
		this.gooderCode = gooderCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}

	public void setGoodModel(String goodModel) {
		this.goodModel = goodModel;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPackCode(String packCode) {
		this.packCode = packCode;
	}

	public void setBulk(String bulk) {
		this.bulk = bulk;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public void setTareWeight(String tareWeight) {
		this.tareWeight = tareWeight;
	}

	public void setRoughWeight(String roughWeight) {
		this.roughWeight = roughWeight;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setPeriodUnite(String periodUnite) {
		this.periodUnite = periodUnite;
	}

	public void setExhibition(String exhibition) {
		this.exhibition = exhibition;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public void setAliasCode(String aliasCode) {
		this.aliasCode = aliasCode;
	}

	public void setGoodConfig(GoodConfig goodConfig) {
		this.goodConfig = goodConfig;
	}

	public void setGoodAreas(List<GoodArea> goodAreas) {
		this.goodAreas = goodAreas;
	}

	public void setGoodAlias(List<GoodAlias> goodAlias) {
		this.goodAlias = goodAlias;
	}

	public void setImgIds(List<String> imgIds) {
		this.imgIds = imgIds;
	}

	/**
	 * 商品编码
	 */
	private String commodityCode;
	/**
	 * 货品编码
	 */
	private String goodCode;
	/**
	 * 货品规格
	 */
	private String goodModel;
	/**
	 * 货品名称
	 */
	private String goodName;
	/**
	 * 是否启用 0否,1是
	 */
	private String status;
	private int statusInt;
	private String error;

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {

		return error;
	}

	public int getStatusInt() {
		if (status.equals("否")) {
			statusInt = 0;
		} else if (status.equals("是")) {
			statusInt = 1;
		}
		return statusInt;
	}

	/**
	 * 包装编码
	 */
	private String packCode;
	/**
	 * 体积
	 */
	private String bulk;
	/**
	 * 净重
	 */
	private String netWeight;
	/**
	 * 皮重
	 */
	private String tareWeight;
	/**
	 * 毛重
	 */
	private String roughWeight;
	/**
	 * 属性
	 */
	private String property;
	/**
	 * 有效期
	 */
	private String period;

	/**
	 * 期效单位
	 */
	private String periodUnite;

	/**
	 * 场地名称
	 */
	private String exhibition;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建人
	 */
	private String creater;
	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 产品链接
	 */
	private String goodLink;

	/**
	 * 颜色
	 */
	private String goodColor;

	public void setGoodLink(String goodLink) {
		this.goodLink = goodLink;
	}

	public void setGoodColor(String goodColor) {
		this.goodColor = goodColor;
	}

	public void setGoodSize(String goodSize) {
		this.goodSize = goodSize;
	}

	public void setBrokerageLink(String brokerageLink) {
		this.brokerageLink = brokerageLink;
	}


	public String getGoodColor() {

		return goodColor;
	}

	public String getGoodSize() {
		return goodSize;
	}

	public String getBrokerageLink() {
		return brokerageLink;
	}


	/**
	 * 尺码
	 */
	private String goodSize;
	/**
	 * 修改人
	 */
	private String modifier;

	public void setStatusInt(int statusInt) {
		this.statusInt = statusInt;
	}

	public String getGoodLink() {

		return goodLink;
	}

	/**
	 * 修改时间
	 */
	private Date modifytime;

	/**
	 * 货品别名
	 */
	private String aliasCode;

	/**
	 * 货品配置对象
	 */
	private GoodConfig goodConfig;

	/**
	 * 货品库区配置对象
	 */

	private List<GoodArea> goodAreas;

	/**
	 * 货品别名对象
	 */
	private List<GoodAlias> goodAlias;

	/**
	 * 图片imgId
	 *
	 * @return
	 */
	private List<String> imgIds;
	/**
	 * 佣金链接
	 */
	private String brokerageLink;
	/**
	 * 佣金比例
	 */
	private String brokerageRatio;
	/**
	 * 所属类目id
	 */
	private String categoryName;
	/**
	 * 货品风格id
	 */
	private String styleName;

	/**
	 * 正常售价
	 */
	private String normalPrice;

	/**
	 * 秒杀价
	 */
	private String seckillPrice;

	/**
	 * 优惠方式
	 */
	private String discountMethod;

	/**
	 * 优惠链接
	 */
	private String discountLink;

	/**
	 * 优惠内容
	 */
	private String discountContent;

	public void setBrokerageRatio(String brokerageRatio) {
		this.brokerageRatio = brokerageRatio;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}

	public void setSeckillPrice(String seckillPrice) {
		this.seckillPrice = seckillPrice;
	}

	public void setDiscountMethod(String discountMethod) {
		this.discountMethod = discountMethod;
	}

	public void setDiscountLink(String discountLink) {
		this.discountLink = discountLink;
	}

	public void setDiscountContent(String discountContent) {
		this.discountContent = discountContent;
	}

	public String getBrokerageRatio() {

		return brokerageRatio;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getStyleName() {
		return styleName;
	}

	public String getNormalPrice() {
		return normalPrice;
	}

	public String getSeckillPrice() {
		return seckillPrice;
	}

	public String getDiscountMethod() {
		return discountMethod;
	}

	public String getDiscountLink() {
		return discountLink;
	}

	public String getDiscountContent() {
		return discountContent;
	}
}
