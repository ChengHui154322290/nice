package com.nice.good.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderGoodDto {


	/**
	 * 订单id
	 */
	private String orderId;
	private String error;

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {

		return error;
	}

	/**
	 * 货品编码
	 */
	private String goodCode;
	/**
	 * 货品名称
	 */
	private String goodName;
	/**
	 * 商品编码
	 */
	private String commodityCode;
	/**
	 * 货品规格
	 */
	private String goodModel;
	/**
	 * 货品状态
	 */
	private String  status;
	/**
	 * 采购数量
	 */
	private String  purchaseNumber;
	/**
	 * 税率(x%）
	 */
	private String rate;
	/**
	 * 进货价
	 */
	private String  originPrice;
	/**
	 * 含税进货价
	 */
	private String  ratePrice;
	/**
	 * 金额（进货价*采购数量）
	 */
	private String  amount;
	/**
	 * 总金额(含税金额=含税进货价*采购数量)
	 */
	private String  rateAmount;
	/**
	 * 是否付款 0否,1是
	 */
	private String  isPay;
	private String fileName;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {

		return fileName;
	}

	private int isPayInt;
	public int getIsPayInt() {

		if(isPay.equals("否")){
			isPayInt=0;
		}else if(isPay.equals("是")){
			isPayInt=1;
		}
		return isPayInt;
	}
	/**
	 * 创建人id
	 */
	private String createId;
	/**
	 * 创建人姓名
	 */
	private String createName;

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public void setGoodModel(String goodModel) {
		this.goodModel = goodModel;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPurchaseNumber(String purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public void setOriginPrice(String originPrice) {
		this.originPrice = originPrice;
	}

	public void setRatePrice(String ratePrice) {
		this.ratePrice = ratePrice;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setRateAmount(String rateAmount) {
		this.rateAmount = rateAmount;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public void setAuditingId(String auditingId) {
		this.auditingId = auditingId;
	}

	public void setAuditingName(String auditingName) {
		this.auditingName = auditingName;
	}

	public String getOrderId() {

		return orderId;
	}

	public String getGoodCode() {
		return goodCode;
	}

	public String getGoodName() {
		return goodName;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public String getGoodModel() {
		return goodModel;
	}

	public String getStatus() {
		return status;
	}

	public String getPurchaseNumber() {
		return purchaseNumber;
	}

	public String getRate() {
		return rate;
	}

	public String getOriginPrice() {
		return originPrice;
	}

	public String getRatePrice() {
		return ratePrice;
	}

	public String getAmount() {
		return amount;
	}

	public String getRateAmount() {
		return rateAmount;
	}

	public String getIsPay() {
		return isPay;
	}

	public String getCreateId() {
		return createId;
	}

	public String getCreateName() {
		return createName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getAuditingId() {
		return auditingId;
	}

	public String getAuditingName() {
		return auditingName;
	}

	/**
	 * 创建日期

	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private String  createDate;
	/**
	 * 审核id
	 */
	private String auditingId;

	/**
	 * 审核人姓名
	 */
	private String auditingName;


}
