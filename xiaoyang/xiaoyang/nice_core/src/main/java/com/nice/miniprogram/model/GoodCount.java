package com.nice.miniprogram.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Table(name = "x_good_count")
public class GoodCount {
    /**
     * 主键
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
     * 点击次数
     */
    @Column(name = "click_num")
    private Integer clickNum;

    /**
     * 领用次数
     */
    @Column(name = "collar_num")
    private Integer collarNum;


}