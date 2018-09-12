package com.nice.miniprogram.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "x_good_img")
public class GoodImg {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 图片名字
     */
    @Column(name = "img_name")
    private String imgName;

    /**
     * 图片url
     */
    @Column(name = "img_url")
    private String imgUrl;

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

    /**
     * 是否主图
     */
    @Column(name = "is_main")
    private Integer isMain;

}