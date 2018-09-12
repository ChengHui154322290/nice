package com.nice.good.wx_model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 领用预约单VO
 */
@Data
public class BookingSlipVo {

	private String orderCode;

	private Integer orderType; //0,内部借领 , 1 内部预约

	private Integer status;

	private String placeNumber;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
	private Date bespeakTimeStart;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
	private Date bespeakTimeEnd;

	private String creater;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
	private Date createtime;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
	private Date creaetimeEnd;

	private String targetSeatCode; //目标库位

	private String exhibition;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
	private Date modifytime;

	private String showRoomCode;

	private String  stockId;

	private String remark;

	private String placeId;

	private String account;//所属人账号









}
