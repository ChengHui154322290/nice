package com.nice.good.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;

public class OutBaseDto {
	public String getSendCode() {
		return sendCode;
	}

	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public void setWaveCode(String waveCode) {
		this.waveCode = waveCode;
	}

	public void setGooderName(String gooderName) {
		this.gooderName = gooderName;
	}

	public void setGooderCode(String gooderCode) {
		this.gooderCode = gooderCode;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setInvoiceWay(String invoiceWay) {
		this.invoiceWay = invoiceWay;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public void setHangUp(String hangUp) {
		this.hangUp = hangUp;
	}

	public void setWaybillCode(String waybillCode) {
		this.waybillCode = waybillCode;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setR_telephone(String r_telephone) {
		this.r_telephone = r_telephone;
	}

	public void setR_phone(String r_phone) {
		this.r_phone = r_phone;
	}

	public void setR_country(String r_country) {
		this.r_country = r_country;
	}

	public void setR_province(String r_province) {
		this.r_province = r_province;
	}

	public void setR_city(String r_city) {
		this.r_city = r_city;
	}

	public void setR_district(String r_district) {
		this.r_district = r_district;
	}

	public void setR_address(String r_address) {
		this.r_address = r_address;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setS_telephone(String s_telephone) {
		this.s_telephone = s_telephone;
	}

	public void setS_phone(String s_phone) {
		this.s_phone = s_phone;
	}

	public void setS_country(String s_country) {
		this.s_country = s_country;
	}

	public void setS_province(String s_province) {
		this.s_province = s_province;
	}

	public void setS_city(String s_city) {
		this.s_city = s_city;
	}

	public void setS_district(String s_district) {
		this.s_district = s_district;
	}

	public void setS_address(String s_address) {
		this.s_address = s_address;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public void setInvoiceHead(String invoiceHead) {
		this.invoiceHead = invoiceHead;
	}

	public void setInvoicelAmount(String invoicelAmount) {
		this.invoicelAmount = invoicelAmount;
	}

	public String getOrderCode() {

		return orderCode;
	}

	public String getProviderName() {
		return providerName;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public String getPriority() {
		return priority;
	}

	public String getWaveCode() {
		return waveCode;
	}

	public String getGooderName() {
		return gooderName;
	}

	public String getGooderCode() {
		return gooderCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public String getOperator() {
		return operator;
	}

	public String getInvoiceWay() {
		return invoiceWay;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public String getHangUp() {
		return hangUp;
	}

	public String getWaybillCode() {
		return waybillCode;
	}

	public Date getPrintTime() {
		return printTime;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getR_telephone() {
		return r_telephone;
	}

	public String getR_phone() {
		return r_phone;
	}

	public String getR_country() {
		return r_country;
	}

	public String getR_province() {
		return r_province;
	}

	public String getR_city() {
		return r_city;
	}

	public String getR_district() {
		return r_district;
	}

	public String getR_address() {
		return r_address;
	}

	public String getSender() {
		return sender;
	}

	public String getS_telephone() {
		return s_telephone;
	}

	public String getS_phone() {
		return s_phone;
	}

	public String getS_country() {
		return s_country;
	}

	public String getS_province() {
		return s_province;
	}

	public String getS_city() {
		return s_city;
	}

	public String getS_district() {
		return s_district;
	}

	public String getS_address() {
		return s_address;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public String getInvoiceHead() {
		return invoiceHead;
	}

	public String getInvoicelAmount() {
		return invoicelAmount;
	}

	/**

	 * 发货编号
	 */
	@Column(name = "send_code")
	private String sendCode;

	/**
	 * 订单编号
	 */
	@Column(name = "order_code")
	private String orderCode;


	/**
	 * 供应商名称
	 */
	@Transient
	private String providerName;

	/**
	 * 供应商编码
	 */
	private String providerCode;
	private String error;

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {

		return error;
	}

	/**
	 * 订单类型
	 */
	private String orderType;
	private int orderTypeInt;

	public int getOrderTypeInt() {
		if(orderType.equals("正常出库")){
			orderTypeInt=1;
		}else if(orderType.equals("次品出库")){
			orderTypeInt=2;
		}else if(orderType.equals("换货出库")) {
			orderTypeInt = 3;
		}
		return orderTypeInt;
	}

	/**
	 * 优先级
	 */
	private String priority;

	/**
	 * 波次编号
	 */
	@Column(name = "wave_code")
	private String waveCode;

	/**
	 * 货主名称
	 */
	@Transient
	private String gooderName;


	/**
	 * 货主编码
	 */
	@Column(name = "gooder_code")
	private String gooderCode;


	/**
	 * 单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货,13已取消
	 */
	@Column(name = "order_status")
	private String orderStatus;

	/**
	 * 操作员
	 */
	private String operator;

	/**
	 * 开票方式
	 */
	@Column(name = "invoice_way")
	private String invoiceWay;

	/**
	 * 承运商名称
	 */
	@Transient
	private String carrierName;

	/**
	 * 承运商id
	 */
	@Column(name = "carrier_code")
	private String carrierCode;

	/**
	 * 挂起 0否,1是
	 */
	@Column(name = "hang_up")
	private String hangUp;

	/**
	 * 运单编码
	 */
	@Column(name = "waybill_code")
	private String waybillCode;

	/**
	 * 打印时间
	 */
	@Column(name = "print_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date printTime;

	/**
	 * 下单时间
	 */
	@Column(name = "order_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date orderTime;
	/**
	 * 收货人
	 */
	private String receiver;
	/**
	 * 手机
	 */
	private String  r_telephone;

	/**
	 * 电话
	 */
	private String r_phone;

	/**
	 * 国家
	 */
	private String r_country;

	/**
	 * 省
	 */
	private String r_province;

	/**
	 * 市
	 */
	private String r_city;

	/**
	 * 区
	 */
	private String r_district;

	/**
	 * 详细地址
	 */
	private String r_address;
	/**
	 * 发货人
	 */
	private String sender;

	/**
	 * 手机
	 */
	private String  s_telephone;

	/**
	 * 电话
	 */
	private String s_phone;

	/**
	 * 国家
	 */
	private String s_country;

	/**
	 * 省
	 */
	private String s_province;

	/**
	 * 市
	 */
	private String s_city;

	/**
	 * 区
	 */
	private String s_district;

	/**
	 * 地址
	 */
	private String s_address;

	/**
	 * 发票类型
	 */
	private String invoiceType;

	/**
	 * 发票抬头
	 */
	private String invoiceHead;

	/**
	 * 开票金额
	 */
	private String  invoicelAmount;
}
