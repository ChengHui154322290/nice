package com.nice.miniprogram.vo;

import lombok.Data;

import javax.persistence.*;


@Data
public class GoodCountSumVo {

    /**
     * spu
     */
    @Column(name = "spu_code")
    private String spuCode;

    /**
     * 总点击次数
     */
    @Column(name = "click_num")
    private Integer clickNum;

    /**
     * 总领用次数
     */
    @Column(name = "collar_num")
    private Integer collarNum;


}