package com.nice.miniprogram.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "x_good_sku")
public class GoodSku {
    /**
     * 主键id
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
     * 货品编码
     */
    @Column(name = "sku_code")
    private String skuCode;

    /**
     * 货品名称
     */
    @Column(name = "sku_name")
    private String skuName;

    /**
     * 是否启用 0否,1是
     */
    private Integer status;

    /**
     * 货品颜色
     */
    @Column(name = "good_color")
    private String goodColor;

    /**
     * 尺码
     */
    @Column(name = "good_size")
    private String goodSize;

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
     * 正常售价
     */
    @Column(name = "normal_price")
    private Double normalPrice;

    /**
     * 秒杀价
     */
    @Column(name = "seckill_price")
    private Double seckillPrice;

    /**
     * 优惠形式
     */
    @Column(name = "discount_method")
    private String discountMethod;

    /**
     * 优惠链接
     */
    @Column(name = "discount_link")
    private String discountLink;

    /**
     * 优惠内容
     */
    @Column(name = "discount_content")
    private String discountContent;

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