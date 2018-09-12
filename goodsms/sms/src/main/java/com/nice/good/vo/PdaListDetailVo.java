package com.nice.good.vo;


public class PdaListDetailVo {

    /**
     * 货品编码
     */
    private String goodCode;



    /**
     * 货品名称
     */
    private String goodName;


    /**
     * 货品规格
     */
    private String goodModel;


    /**
     * 预期量
     */
    private Integer expectNum;

    /**
     * 接收量
     */

    private Integer receiveNum;

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodModel() {
        return goodModel;
    }

    public void setGoodModel(String goodModel) {
        this.goodModel = goodModel;
    }

    public Integer getExpectNum() {
        return expectNum;
    }

    public void setExpectNum(Integer expectNum) {
        this.expectNum = expectNum;
    }

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }
}
