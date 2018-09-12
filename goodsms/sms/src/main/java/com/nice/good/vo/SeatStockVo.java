package com.nice.good.vo;



public class SeatStockVo {
	/**
	 * 主键库位库存表id
	 */
	private String stockId;

	/**
	 * 序号id,自动增长
	 */
	private Integer id;


	/**
	 * 货主编码
	 */
	private String gooderCode;


	/**
	 * 组织编码
	 */
	private String orgCode;


	/**
	 * 供应商编码
	 */
	private String providerCode;


	/**
	 * 货品编码
	 */
	private String goodCode;

	/**
	 * 库位编号
	 */
	private String seatCode;

	/**
	 * 货品名称
	 */
	private String goodName;
	/**
	 * 货品规格
	 */
	private String goodModel;

	/**
	 * 商品编码
	 */
	private String commodityCode;

	/**
	 * 场地编码
	 */
	private String placeId;
	/**
	 * 优惠链接
	 */
	private String discountLink;

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setGooderCode(String gooderCode) {
		this.gooderCode = gooderCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public void setGoodModel(String goodModel) {
		this.goodModel = goodModel;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public void setDiscountLink(String discountLink) {
		this.discountLink = discountLink;
	}

	public void setNormalPrice(Double normalPrice) {
		this.normalPrice = normalPrice;
	}

	public void setBrokerageRatio(Double brokerageRatio) {
		this.brokerageRatio = brokerageRatio;
	}

	public void setCouponLimit(Double couponLimit) {
		this.couponLimit = couponLimit;
	}

	public String getStockId() {

		return stockId;
	}

	public Integer getId() {
		return id;
	}

	public String getGooderCode() {
		return gooderCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public String getGoodCode() {
		return goodCode;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public String getGoodName() {
		return goodName;
	}

	public String getGoodModel() {
		return goodModel;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public String getDiscountLink() {
		return discountLink;
	}

	public Double getNormalPrice() {
		return normalPrice;
	}

	public Double getBrokerageRatio() {
		return brokerageRatio;
	}

	public Double getCouponLimit() {
		return couponLimit;
	}

	/**

	 * 正常售价
	 */
	private Double normalPrice;

	/**
	 * 佣金比例
	 */
	private Double brokerageRatio;

	/**
	 * 优惠券额度
	 */
	private Double couponLimit;

	/**
	 * 货品颜色
	 */
	private String goodColor;

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public void setGoodColor(String goodColor) {
		this.goodColor = goodColor;
	}

	public void setGoodSize(String goodSize) {
		this.goodSize = goodSize;
	}

	public String getPlaceId() {

		return placeId;
	}

	public String getGoodColor() {
		return goodColor;
	}

	public String getGoodSize() {

		return goodSize;
	}

	/**
	 * 货品尺码
	 */

	private String goodSize;


}