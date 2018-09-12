package com.nice.good.dto;

import javax.persistence.Column;

public class OutDetailDto {
	/**
	 * 订单编号
	 */
	private String orderCode;

	/**
	 * 货品编码
	 */
	@Column(name = "good_code")
	private String goodCode;

	/**
	 * 货品名称
	 */
	@Column(name = "good_name")
	private String goodName;

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public String getGoodCode() {
		return goodCode;
	}

	public String getGoodName() {
		return goodName;
	}

	public String getOrderNum() {
		return orderNum;
	}

	/**

	 * 订单量
	 */
	@Column(name = "order_num")
	private String orderNum;
	private String error;

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {

		return error;
	}
}
