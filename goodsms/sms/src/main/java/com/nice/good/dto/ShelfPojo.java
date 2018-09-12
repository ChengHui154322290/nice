package com.nice.good.dto;

public class ShelfPojo {

    /**
     * 行号id
     */
    private Integer id;

    /**
     * 收货明细主键id
     */
    private String detailId;

    /**
     * 货主编码
     */
    private String gooderCode;


    /**
     * 货品编码
     */
    private String goodCode;


    /**
     * 货品名称
     */
    private String goodName;

    /**
     * 商品编码
     */
    private String commodityCode;


    /**
     * 货品规格
     */
    private String goodModel;


    /**
     * 状态
     */
    private Integer status;


    /**
     * 预期量
     */
    private Integer expectNum;


    /**
     * 接收量
     */
    private Integer acceptNum;


    /**
     * 货品标记 2正品,3次品
     */
    private Integer goodSign;


    /**
     * 是否质检  0否,1是 隐藏字段
     */
    private Integer isQuality;

    public Integer getIsQuality() {
        return isQuality;
    }

    public void setIsQuality(Integer isQuality) {
        this.isQuality = isQuality;
    }

    /**
     * LPN
     */
    private String lpn;


    /**
     * 库位
     */
    private String seatCode;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

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

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getGoodModel() {
        return goodModel;
    }

    public void setGoodModel(String goodModel) {
        this.goodModel = goodModel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getExpectNum() {
        return expectNum;
    }

    public void setExpectNum(Integer expectNum) {
        this.expectNum = expectNum;
    }

    public Integer getAcceptNum() {
        return acceptNum;
    }

    public void setAcceptNum(Integer acceptNum) {
        this.acceptNum = acceptNum;
    }

    public Integer getGoodSign() {
        return goodSign;
    }

    public void setGoodSign(Integer goodSign) {
        this.goodSign = goodSign;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
}
