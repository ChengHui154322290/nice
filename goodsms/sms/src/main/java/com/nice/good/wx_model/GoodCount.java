package com.nice.good.wx_model;

import javax.persistence.*;

@Table(name = "x_good_count")
public class GoodCount {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 样品id
     */
    @Column(name = "good_id")
    private String goodId;

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

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取样品id
     *
     * @return good_id - 样品id
     */
    public String getGoodId() {
        return goodId;
    }

    /**
     * 设置样品id
     *
     * @param goodId 样品id
     */
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    /**
     * 获取点击次数
     *
     * @return click_num - 点击次数
     */
    public Integer getClickNum() {
        return clickNum;
    }

    /**
     * 设置点击次数
     *
     * @param clickNum 点击次数
     */
    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    /**
     * 获取领用次数
     *
     * @return collar_num - 领用次数
     */
    public Integer getCollarNum() {
        return collarNum;
    }

    /**
     * 设置领用次数
     *
     * @param collarNum 领用次数
     */
    public void setCollarNum(Integer collarNum) {
        this.collarNum = collarNum;
    }
}