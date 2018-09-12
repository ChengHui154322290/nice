package com.nice.good.vo;


/**
 * 采购明细
 */
public class PurchaseDetailVo {

    /**
     * 序号
     */
    private Integer id;

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

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    /**
     * 商品编码
     */
    private String commodityCode;

    /**
     * 采购数量
     */
    private Integer purchaseNumber;


    /**
     * 接收数量
     */
    private Integer receiveNum;


    /**
     * 未到数量
     */
    private Integer noArriveNum;


    /**
     * 采购单号
     * @return
     */
    private String purchaseCode;


    /**
     * 毛重
     */
    private Double rWeight;

    /**
     * 体积
     */
    private Double bulk;


    /**
     * 是否质检
     */
    private Integer isQuality;


    public Double getrWeight() {
        return rWeight;
    }

    public void setrWeight(Double rWeight) {
        this.rWeight = rWeight;
    }

    public Double getBulk() {
        return bulk;
    }

    public void setBulk(Double bulk) {
        this.bulk = bulk;
    }

    public Integer getIsQuality() {
        return isQuality;
    }

    public void setIsQuality(Integer isQuality) {
        this.isQuality = isQuality;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Integer getReceiveNum() {

        if (receiveNum==null){
            receiveNum=0;
        }

        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    public Integer getNoArriveNum() {

        return purchaseNumber -receiveNum;
    }

    public void setNoArriveNum(Integer noArriveNum) {
        this.noArriveNum = noArriveNum;
    }
}
