package com.nice.good.wx_model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
//@ApiModel(value = "领用订单明细")
public class OrderDetailVo {


    //@ApiModel(value = "主键id")
    private Integer id;
    private Integer alternativeId;
    //@ApiModel(value = "订单类型（0：领用单，1：预约单）")
    private Integer orderType;
    //@ApiModel(value = "主表id")
    private Integer orderId;
    //@ApiModel(value = "订单状态(待发货:0,待收货:1,已收货:3,已归还:5,归还中:6,已取消:7)")
    private Integer status;
    //@ApiModel(value = "样品id")
    private String skuCode;

    //@ApiModel(value = "已选数量")
    private Integer choseNum;
    //@ApiModel(value = "说明/备注")
    private String remark;
    //@ApiModel(value = "目标库位")
    private String targetSeatCode;
    //@ApiModel(value = "来源库位")
    private String sourceSeatCode;
    //@ApiModel(value = "是否已评价（否：0，是：1）")
    private Integer isFeedback;
    private String seatCode; //库位编码

    private String creater;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;
    private String modifier;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifytime;




    private String stockId;
    private String gooderCode;
    private String providerCode;
    private String skuName;
    private String styleName;
    private String categoryName;
    private String goodColor;
    private String goodSize;
    private Double normalPrice;

}
