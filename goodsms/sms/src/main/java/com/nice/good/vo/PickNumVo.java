package com.nice.good.vo;

public class PickNumVo {

    /**
     * 货主编码
     */
    private String gooderCode;


    /**
     * 货品编码
     */
    private String goodCode;


    /**
     * sku或者rfid
     */
    String skuOrRfid;

    public String getGooderCode() {
        return gooderCode;
    }

    /**
     * 采集量
     * @param gooderCode
     */
    private Integer rfid;

    public Integer getRfid() {
        return rfid;
    }

    public void setRfid(Integer rfid) {
        this.rfid = rfid;
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

    public String getSkuOrRfid() {
        return skuOrRfid;
    }

    public void setSkuOrRfid(String skuOrRfid) {
        this.skuOrRfid = skuOrRfid;
    }
}
