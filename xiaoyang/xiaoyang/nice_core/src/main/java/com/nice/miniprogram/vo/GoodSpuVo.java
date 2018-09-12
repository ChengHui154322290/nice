package com.nice.miniprogram.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@ApiModel(value = "商品信息表")
public class GoodSpuVo {
    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "商品编码")
    @Column(name = "spu_code")
    private String spuCode;

    @ApiModelProperty(value = "商品名称")
    private String spuName;

    @ApiModelProperty(value = "是否启用 0否,1是,默认1是")
    private Integer status;

    @ApiModelProperty(value = "货主编码")
    private String gooderCode;

    @ApiModelProperty(value = "风格id")
    private Integer styleId;

    @ApiModelProperty(value = "分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "品牌id")
    private Integer brandId;

    @ApiModelProperty(value = "价格区间")
    @Column(name = "price_section")
    private String priceSection;

    @ApiModelProperty(value = "包装编码")
    private String packCode;

    @ApiModelProperty(value = "体积")
    private Double bulk;

    @ApiModelProperty(value = "净重")
    private Double netWeight;

    @ApiModelProperty(value = "皮重")
    private Double tareWeight;

    @ApiModelProperty(value = "毛重")
    private Double roughWeight;

    @ApiModelProperty(value = "属性")
    private String property;

    @ApiModelProperty(value = "有效期")
    private String period;

    @ApiModelProperty(value = "期效单位")
    private String periodUnite;

    @ApiModelProperty(value = "产品链接")
    private String goodLink;

    @ApiModelProperty(value = "佣金链接")
    private String brokerageLink;

    @ApiModelProperty(value = "佣金比例")
    private Double brokerageRatio;

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
    private String companyId;

    @ApiModelProperty(value = "sku编码（货品编码）")
    private String skuCode;
    @ApiModelProperty(value = "可用数量")
    private Integer useNum;
    @ApiModelProperty(value = "库位编码")
    private String seatCode;
    @ApiModelProperty(value = "风格")
    private String styleName;
    @ApiModelProperty(value = "分类")
    private String categoryName;
    @ApiModelProperty(value = "spu总点击量")
    private Integer clickNum;
    @ApiModelProperty(value = "spu总领用量")
    private Integer collarNum;
    @ApiModelProperty(value = "商品最低价格")
    private Double normalPrice;

}