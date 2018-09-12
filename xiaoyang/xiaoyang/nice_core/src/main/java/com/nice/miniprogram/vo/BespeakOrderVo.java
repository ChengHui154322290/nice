package com.nice.miniprogram.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "预约单")
public class BespeakOrderVo {

    @ApiModelProperty(value="主键ID")
    private Integer id;
    @ApiModelProperty(value="预约单编号")
    private String orderCode;
    @ApiModelProperty(value="预约的开始时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bespeakTimeStart;
    @ApiModelProperty(value="预约的结束时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bespeakTimeEnd;
    @ApiModelProperty(value="预约的主播间id")
    private Integer showRoomId;
    @ApiModelProperty(value="订单所属用户id")
    private Integer ownId;
    @ApiModelProperty(value="操作人")
    private Integer operatorId;
    @ApiModelProperty(value="操作类型（订单由主播创建：0，运营创建：1）")
    private Integer operatorType=0;
    @ApiModelProperty(value="预约单状态（已预约：0；已完成：1；已取消：2）")
    private Integer status=0;
    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value = "订单明细")
    private List<OrderDetailVo> orderDetailVoList;

    @ApiModelProperty(value = "展厅id")
    private Integer exhibitionRoomId;


    @ApiModelProperty(value="预约的开始时间（字符串类型）")
    private String bespeakTimeStartStr;
    @ApiModelProperty(value="预约的结束时间（字符串类型）")
    private String bespeakTimeEndStr;

}
