package com.nice.good.wx_model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
//@ApiModel(value = "领用订单主表")
public class CollarOrderVo {
	//@ApiModelProperty(value = "主键id")
	private Integer id;
	//@ApiModelProperty(value = "领用单编号")
	private String orderCode;
	//@ApiModelProperty(value = "领用单状态(待发货:0,待收货:1,部分收货:2,已收货:3,部分归还:4,已归还:5,归还中:6,已取消:7)")
	private int status;
	//@ApiModelProperty(value = "直播间id")
	private Integer showRoomId;
	//@ApiModelProperty(value = "说明/备注")
	private String remark;
	//@ApiModelProperty(value = "订单所属用户id")
	private Integer ownId;
	//@ApiModelProperty(value = "操作人")
	private String operatorId;
	//@ApiModelProperty(value = "操作类型（订单由主播创建：0，由运营创建：1）")
	private Integer operatorType;
	//@ApiModelProperty(value = "领用单明细")
	private List<OrderDetailVo> collarOrderDetails;
	//@ApiModelProperty(value = "配送方式（自提：0，快递：1 ）")
	private Integer distributionMode;

	//@ApiModelProperty(value = "展厅id")
	private String exhibitionRoomId;
	//@ApiModelProperty(value = "展厅名称")
	private String exhibitionRoomName;
	private String creater;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createtime;
	private String modifier;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date modifytime;

	private String placeNumber;
	private Integer choseSum;
	private Integer takeNum; //收货数量
	private Integer stayNum ; //待归还数量
	private Integer giveNum; //已归还数量
	private String account; //主播账号
}
