package com.nice.miniprogram.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "x_good_spu")
public class GoodSpu {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品编码
     */
    @Column(name = "spu_code")
    private String spuCode;

    /**
     * 商品名称
     */
    @Column(name = "spu_name")
    private String spuName;

    /**
     * 是否启用 0否,1是,默认1是
     */
    private Integer status;

    /**
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;

    /**
     * 风格id
     */
    @Column(name = "style_id")
    private Integer styleId;

    /**
     * 分类id
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 品牌id
     */
    @Column(name = "brand_id")
    private Integer brandId;

    /**
     * 价格区间
     */
    @Column(name = "price_section")
    private String priceSection;

    /**
     * 包装编码
     */
    @Column(name = "pack_code")
    private String packCode;

    /**
     * 体积
     */
    private Double bulk;

    /**
     * 净重
     */
    @Column(name = "net_weight")
    private Double netWeight;

    /**
     * 皮重
     */
    @Column(name = "tare_weight")
    private Double tareWeight;

    /**
     * 毛重
     */
    @Column(name = "rough_weight")
    private Double roughWeight;

    /**
     * 属性
     */
    private String property;

    /**
     * 有效期
     */
    private String period;

    /**
     * 期效单位
     */
    @Column(name = "period_unite")
    private String periodUnite;

    /**
     * 产品链接
     */
    @Column(name = "good_link")
    private String goodLink;

    /**
     * 佣金链接
     */
    @Column(name = "brokerage_link")
    private String brokerageLink;

    /**
     * 佣金比例
     */
    @Column(name = "brokerage_ratio")
    private Double brokerageRatio;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 企业id
     */
    @Column(name = "company_id")
    private String companyId;

}