package com.nice.good.wx_model;

import java.util.Date;
import java.util.List;

public class PurchaseXcBean {

	private List<String> gooderCode;//货主编码

	private List<String> placeNumbers; //场地编码

	private String placeNumber;

	private List<String> roomCodes; //直播间编码

	public void setPlaceNumber(String placeNumber) {
		this.placeNumber = placeNumber;
	}

	public String getPlaceNumber() {

		return placeNumber;
	}

	private String orderCode; //借领单号

	private List<String> areaCodes; // 库区编码

	private List<String> seatCode;  //库位编码

	private List<String> account; //主播账号

	public void setStyles(List<String> styles) {
		this.styles = styles;
	}

	public List<String> getStyles() {

		return styles;
	}

	private List<String> styles;  //直播间风格


	public void setAccount(List<String> account) {
		this.account = account;
	}

	public List<String> getAccount() {

		return account;
	}

	public void setRoomCodes(List<String> roomCodes) {
		this.roomCodes = roomCodes;
	}

	public List<String> getRoomCodes() {

		return roomCodes;
	}

	public void setAreaCodes(List<String> areaCodes) {
		this.areaCodes = areaCodes;
	}

	public void setSeatCode(List<String> seatCode) {
		this.seatCode = seatCode;
	}

	public List<String> getSeatCode() {

		return seatCode;
	}

	public List<String> getAreaCodes() {

		return areaCodes;
	}

	public PurchaseXcBean(List<String> gooderCode, List<String> placeNumbers, String orderCode) {
		this.gooderCode = gooderCode;
		this.placeNumbers = placeNumbers;
		this.orderCode = orderCode;
	}

	public void setPlaceNumbers(List<String> placeNumbers) {

		this.placeNumbers = placeNumbers;
	}

	public List<String> getPlaceNumbers() {

		return placeNumbers;
	}

	private String creater; //创建人

	public PurchaseXcBean() {
	}

	private Date createtime; //创建时间

	private String modifier; //修改人

	private Date modifytime; //修改时间

	public PurchaseXcBean(List<String> gooderCode, String orderCode, String creater, Date createtime, String modifier, Date modifytime) {
		this.gooderCode = gooderCode;
		this.orderCode = orderCode;
		this.creater = creater;
		this.createtime = createtime;
		this.modifier = modifier;
		this.modifytime = modifytime;
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

	public String getCreater() {

		return creater;
	}

	public PurchaseXcBean(List<String> gooderCode, String orderCode) {
		this.gooderCode = gooderCode;
		this.orderCode = orderCode;
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

	public PurchaseXcBean(List<String> gooderCode) {
		this.gooderCode = gooderCode;
	}

	public void setGooderCode(List<String> gooderCode) {
		this.gooderCode = gooderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}


	public List<String> getGooderCode() {
		return gooderCode;
	}

	public String getOrderCode() {
		return orderCode;
	}
}
