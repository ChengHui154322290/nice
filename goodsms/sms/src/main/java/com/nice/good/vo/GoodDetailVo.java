package com.nice.good.vo;

import java.util.List;

public class GoodDetailVo {


    /**
     * 货主编码
     */
    private String gooderCode;

    /**
     * 商品编码
     */
    private String commodityCode;

    /**
     * 货品编码
     */
    private String goodCode;

    /**
     * 货品规格
     */
    private String goodModel;

    /**
     * 货品名称
     */
    private String goodName;

    /**
     * 是否启用 0否,1是
     */
    private Integer status;

    /**
     * 包装编码
     */
    private String packCode;

    /**
     * 属性
     */
    private String property;

    /**
     * 备注
     */
    private String remark;

    /**
     * 产品链接
     */
//    private String goodLink;


    /**
     * 颜色
     */
    private String goodColor;


    /**
     * 尺码
     */
    private String goodSize;


    //图片链接
    private List<String> imgIds;


    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodModel() {
        return goodModel;
    }

    public void setGoodModel(String goodModel) {
        this.goodModel = goodModel;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

//    public String getGoodLink() {
//        return goodLink;
//    }
//
//    public void setGoodLink(String goodLink) {
//        this.goodLink = goodLink;
//    }

    public String getGoodColor() {
        return goodColor;
    }

    public void setGoodColor(String goodColor) {
        this.goodColor = goodColor;
    }

    public String getGoodSize() {
        return goodSize;
    }

    public void setGoodSize(String goodSize) {
        this.goodSize = goodSize;
    }

    public List<String> getImgIds() {
        return imgIds;
    }

    public void setImgIds(List<String> imgIds) {
        this.imgIds = imgIds;
    }
}
