package com.nice.miniprogram.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "x_alternative")
public class Alternative {
    /**
     * 序号id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 图片id
     */
    @Column(name = "img_id")
    private String imgId;

    /**
     * 货品编码
     */
    @Column(name = "sku_code")
    private String skuCode;
    /**
     * 已选数量
     */
    @Column(name = "checked_num")
    private Integer checkedNum;
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
     * 所属用户id
     */

    @Column(name = "own_id")
    private Integer ownId;


}