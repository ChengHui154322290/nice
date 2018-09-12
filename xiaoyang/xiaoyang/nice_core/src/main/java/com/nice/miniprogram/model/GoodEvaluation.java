package com.nice.miniprogram.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "x_good_evaluation")
public class GoodEvaluation {
    /**
     * 主键PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * spu
     */
    @Column(name = "spu_code")
    private String spuCode;

    /**
     * sku
     */
    @Column(name = "sku_code")
    private String skuCode;

    /**
     * 评分
     */
    private Double score;

    /**
     * 实物描述一致性
     */
    private Double homogeneity;

    /**
     * 粉丝口碑
     */
    @Column(name = "fans_praise")
    private Double fansPraise;

    /**
     * 评价
     */
    private String evaluation;

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


}