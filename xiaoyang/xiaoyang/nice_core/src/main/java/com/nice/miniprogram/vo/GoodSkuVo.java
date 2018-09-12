package com.nice.miniprogram.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@ApiModel(value = "货品信息表")
public class GoodSkuVo {
    @ApiModelProperty(value = "主键id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "商品编码")
    @Column(name = "spu_code")
    private String spuCode;

    @ApiModelProperty(value = "货品编码")
    @Column(name = "sku_code")
    private String skuCode;

    @ApiModelProperty(value = "货品名称")
    @Column(name = "sku_name")
    private String skuName;

    @ApiModelProperty(value = "是否启用 0否,1是")
    private Integer status;

    @ApiModelProperty(value = "货品颜色")
    @Column(name = "good_color")
    private String goodColor;

    @ApiModelProperty(value = "尺码")
    @Column(name = "good_size")
    private String goodSize;

    @ApiModelProperty(value = "佣金链接")
    @Column(name = "brokerage_link")
    private String brokerageLink;

    @ApiModelProperty(value = "佣金比例")
    @Column(name = "brokerage_ratio")
    private Double brokerageRatio;

    @ApiModelProperty(value = "正常售价")
    @Column(name = "normal_price")
    private Double normalPrice;

    @ApiModelProperty(value = "秒杀价")
    @Column(name = "seckill_price")
    private Double seckillPrice;

    @ApiModelProperty(value = "优惠形式")
    @Column(name = "discount_method")
    private String discountMethod;

    @ApiModelProperty(value = "优惠链接")
    @Column(name = "discount_link")
    private String discountLink;

    @ApiModelProperty(value = "优惠内容")
    @Column(name = "discount_content")
    private String discountContent;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String creater;

    @ApiModelProperty(value = "创建时间")
    private Date createtime;

    @ApiModelProperty(value = "修改人")
    private String modifier;

    @ApiModelProperty(value = "修改时间")
    private Date modifytime;

    @ApiModelProperty(value = "企业id")
    @Column(name = "company_id")
    private String companyId;

    @ApiModelProperty(value = "可用库存")
    private Integer useNum;
    @ApiModelProperty(value = "商品名称")
    private String spuName;
    @ApiModelProperty(value = "点击量")
    private Integer clickNum;
    @ApiModelProperty(value = "领用量")
    private Integer collarNum;

    @ApiModelProperty(value = "库区编码")
    private String areaCode;
    @ApiModelProperty(value = "库位编码")
    private String seatCode;
    @ApiModelProperty(value = "场地id")
    private String placeId;
}