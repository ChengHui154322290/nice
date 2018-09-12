package com.nice.good.vo;


import com.nice.good.model.GoodPicture;

import java.util.List;

public class GoodVo {

	/**
	 * 序号id
	 */
	private Integer id;

	private String goodId;

	private String providerCode;

	public Integer getClickNum() {
		return clickNum;
	}

	public Integer getCollarNum() {
		return collarNum;
	}

	public void setProviderCode(String providerCode) {

		this.providerCode = providerCode;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	public void setCollarNum(Integer collarNum) {
		this.collarNum = collarNum;
	}

	public String getProviderCode() {

		return providerCode;
	}

	//@ApiModelProperty(value = "点击次数")
	private Integer clickNum;
	//@ApiModelProperty(value = "领用次数")
	private Integer collarNum;

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}

	public String getNormalPrice() {

		return normalPrice;
	}

	public String getGoodId() {

		return goodId;
	}

	/**
	 * 货主编码
	 */

	private String gooderCode;

	private String styleName;

	private String categoryName;

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getStyleName() {

		return styleName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * 货品编码
	 */
	private String goodCode;

	private String normalPrice; //售价

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	private String placeNumber; //场地编码

	public void setPlaceNumber(String placeNumber) {
		this.placeNumber = placeNumber;
	}

	public String getPlaceNumber() {

		return placeNumber;
	}

	private String areaCode;  // 库区编码

	/**
	 * 货品规格
	 */
	private String goodModel;

	private String goodSize;

	private Integer nowNum;

	private Integer useNum;

	private String goodColor;

	private List<GoodPicture> goodPictures;

	public void setGoodPictures(List<GoodPicture> goodPictures) {
		this.goodPictures = goodPictures;
	}

	public List<GoodPicture> getGoodPictures() {

		return goodPictures;
	}

	public void setGoodSize(String goodSize) {
		this.goodSize = goodSize;
	}


	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}

	public void setNowNum(Integer nowNum) {
		this.nowNum = nowNum;
	}

	public Integer getNowNum() {

		return nowNum;
	}

	public String getGoodSize() {

		return goodSize;
	}


	public Integer getUseNum() {
		return useNum;
	}

	public void setGoodColor(String goodColor) {
		this.goodColor = goodColor;
	}

	public String getGoodColor() {

		return goodColor;
	}

	/**
	 * 库位编码
	 */
	private String seatCode;

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public String getSeatCode() {

		return seatCode;
	}

	/**
	 * 货品名称
	 */
	private String goodName;


	/**
	 * 商品编码
	 */
	private String commodityCode;


	/**
	 * 上架策略
	 */
	private String putStrategy;


	/**
	 * 有效期
	 */
	private String period;


	/**
	 * 期效单位
	 */
	private String periodUnite;

	/**
	 * 包装编码
	 */
	private String packCode;


	/**
	 * 是否质检
	 */

	private Integer isQuality;


	/**
	 * 体积
	 */
	private Double bulk;


	/**
	 * 毛重
	 */
	private Double rWeight;

	public Double getrWeight() {
		return rWeight;
	}

	public void setrWeight(Double rWeight) {
		this.rWeight = rWeight;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}


	public Integer getIsQuality() {
		return isQuality;
	}

	public void setIsQuality(Integer isQuality) {
		this.isQuality = isQuality;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGooderCode() {
		return gooderCode;
	}

	public void setGooderCode(String gooderCode) {
		this.gooderCode = gooderCode;
	}

	public String getGoodCode() {
		return goodCode;
	}

	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}

	public String getGoodModel() {
		return goodModel;
	}

	public void setGoodModel(String goodModel) {
		this.goodModel = goodModel;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getPutStrategy() {
		return putStrategy;
	}

	public void setPutStrategy(String putStrategy) {
		this.putStrategy = putStrategy;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPeriodUnite() {
		return periodUnite;
	}

	public void setPeriodUnite(String periodUnite) {
		this.periodUnite = periodUnite;
	}

	public String getPackCode() {
		return packCode;
	}

	public void setPackCode(String packCode) {
		this.packCode = packCode;
	}
}
