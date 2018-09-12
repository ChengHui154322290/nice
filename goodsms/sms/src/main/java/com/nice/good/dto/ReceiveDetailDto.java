package com.nice.good.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import java.util.Date;

public class ReceiveDetailDto {

	/**
	 * 货主编码
	 */
	@Column(name = "gooder_code")
	private String gooderCode;


	/**
	 * 组织编码
	 */
	@Column(name = "org_code")
	private String orgCode;

	/**
	 * 货品编码
	 */
	@Column(name = "good_code")
	private String goodCode;


	private String error;

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {

		return error;
	}

	/**
	 * 货品名称
	 */
	@Column(name = "good_name")
	private String goodName;


	/**
	 * 货品规格
	 */
	@Column(name = "good_model")
	private String goodModel;


	/**
	 * 采购单号
	 */
	@Column(name = "purchase_code")
	private String purchaseCode;

	/**
	 * 采购单行号
	 */
	@Column(name = "purchase_line_code")
	private String purchaseLineCode;

	/**
	 * 外部单行号
	 */
	@Column(name = "outside_line_code")
	private String outsideLineCode;

	/**
	 * 单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
	 */
	private String status;

	/**
	 * 质检
	 */
	@Column(name = "is_quality")
	private String isQuality;

	/**
	 * 毛重
	 */
	private String rweight;

	/**
	 * 体积
	 */
	private String bulk;

	/**
	 * 预期量
	 */
	@Column(name = "expect_num")
	private String expectNum;

	/**
	 * 接收量
	 */

	@Column(name = "receive_num")
	private String receiveNum;

	/**
	 * 拒收量
	 */
	@Column(name = "refuse_num")
	private String refuseNum;

	/**
	 * 质检量
	 */
	@Column(name = "quality_num")
	private String qualityNum;

	/**
	 * 次品量
	 */
	@Column(name = "second_num")
	private String secondNum;

	/**
	 * 上架量
	 */
	@Column(name = "shelf_num")
	private String shelfNum;

	/**
	 * 库位编码
	 */
	@Column(name = "seat_code")
	private String seatCode;

	private  String fileName;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {

		return fileName;
	}

	/**
	 * rfid采集量
	 */
	private String rfid;

	public String getGooderCode() {
		return gooderCode;
	}

	public void setGooderCode(String gooderCode) {
		this.gooderCode = gooderCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public void setGoodModel(String goodModel) {
		this.goodModel = goodModel;
	}

	public void setPurchaseCode(String purchaseCode) {
		this.purchaseCode = purchaseCode;
	}

	public void setPurchaseLineCode(String purchaseLineCode) {
		this.purchaseLineCode = purchaseLineCode;
	}

	public void setOutsideLineCode(String outsideLineCode) {
		this.outsideLineCode = outsideLineCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setIsQuality(String isQuality) {
		this.isQuality = isQuality;
	}

	public void setRweight(String rweight) {
		this.rweight = rweight;
	}

	public void setBulk(String bulk) {
		this.bulk = bulk;
	}

	public void setExpectNum(String expectNum) {
		this.expectNum = expectNum;
	}

	public void setReceiveNum(String receiveNum) {
		this.receiveNum = receiveNum;
	}

	public void setRefuseNum(String refuseNum) {
		this.refuseNum = refuseNum;
	}

	public void setQualityNum(String qualityNum) {
		this.qualityNum = qualityNum;
	}

	public void setSecondNum(String secondNum) {
		this.secondNum = secondNum;
	}

	public void setShelfNum(String shelfNum) {
		this.shelfNum = shelfNum;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setLpn(String lpn) {
		this.lpn = lpn;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public void setQualityTime(Date qualityTime) {
		this.qualityTime = qualityTime;
	}

	public void setShelfTime(Date shelfTime) {
		this.shelfTime = shelfTime;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	public void setHangUp(Integer hangUp) {
		this.hangUp = hangUp;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getOrgCode() {
		return orgCode;

	}

	public String getGoodCode() {
		return goodCode;
	}

	public String getGoodName() {
		return goodName;
	}

	public String getGoodModel() {
		return goodModel;
	}

	public String getPurchaseCode() {
		return purchaseCode;
	}

	public String getPurchaseLineCode() {
		return purchaseLineCode;
	}

	public String getOutsideLineCode() {
		return outsideLineCode;
	}

	public String getStatus() {
		return status;
	}

	public String getIsQuality() {
		return isQuality;
	}

	public String getRweight() {
		return rweight;
	}

	public String getBulk() {
		return bulk;
	}

	public String getExpectNum() {
		return expectNum;
	}

	public String getReceiveNum() {
		return receiveNum;
	}

	public String getRefuseNum() {
		return refuseNum;
	}

	public String getQualityNum() {
		return qualityNum;
	}

	public String getSecondNum() {
		return secondNum;
	}

	public String getShelfNum() {
		return shelfNum;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public String getRfid() {
		return rfid;
	}

	public String getOperator() {
		return operator;
	}

	public String getLpn() {
		return lpn;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public Date getQualityTime() {
		return qualityTime;
	}

	public Date getShelfTime() {
		return shelfTime;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public Integer getHangUp() {
		return hangUp;
	}

	public String getCreateId() {
		return createId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getModifyId() {
		return modifyId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * 操作人
	 */

	private String operator;

	/**
	 * LPN
	 */
	private String lpn;

	/**
	 * 接收时间
	 */
	@Column(name = "receive_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date receiveTime;

	/**
	 * 质检时间
	 */
	@Column(name = "quality_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date qualityTime;

	/**
	 * 上架时间
	 */
	@Column(name = "shelf_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date shelfTime;

	/**
	 * asn收货单表id
	 */
	@Column(name = "receive_id")
	private String receiveId;



	/**
	 * 订单挂起 0表示未挂起 1表示挂起
	 */
	@Column(name = "hang_up")
	private Integer hangUp;

	/**
	 * 创建人id
	 */
	@Column(name = "create_id")
	private String createId;

	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createDate;

	/**
	 * 修改人
	 */
	@Column(name = "modify_id")
	private String modifyId;

	/**
	 * 修改时间
	 */
	@Column(name = "modify_date")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date modifyDate;

}
