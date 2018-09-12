package com.nice.miniprogram.vo;

import com.nice.miniprogram.model.GoodPicture;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "领用订单明细")
public class OrderDetailVo {

    @ApiModelProperty(value = "主键id")
    private Integer id;
    private Integer alternativeId;
    @ApiModelProperty(value = "订单类型（0：领用单，1：预约单）")
    private Integer orderType;
    @ApiModelProperty(value = "主表id")
    private Integer orderId;
    @ApiModelProperty(value = "订单状态(待发货:0,待收货:1,已收货:3,已归还:5,归还中:6,已取消:7)")
    private Integer status;
    @ApiModelProperty(value = "样品id")
    private String skuCode;

    @ApiModelProperty(value = "已选数量")
    private Integer choseNum;
    @ApiModelProperty(value = "说明/备注")
    private String remark;
    @ApiModelProperty(value = "图片")
    private List<GoodPicture> pictureUrls;
    @ApiModelProperty(value = "目标库位")
    private String targetSeatCode;
    @ApiModelProperty(value = "来源库位")
    private String sourceSeatCode;
    @ApiModelProperty(value = "是否已评价（否：0，是：1）")
    private Integer isFeedback;
}
