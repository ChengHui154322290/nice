package com.nice.good.vo;

/**
 *   用于批量新增 明细单 表中数据  -- 2018/06/08  11:21  rk
 */
public class BillVo {

    /**
     * 货主编码
     */
    private String gooderCode;

    /**
     * 货主名称
     */
    private String gooderName;

    /**
     * 货品编码
     */
    private String goodCode;

    /**
     * 货品名称
     */
    private String goodName;

    /**
     * 库区编号
     */
    private String areaCode;

    /**
     * 库位编码
     */
    private String seatCode;

    /**
     * 供应商编码
     */
    private String providerCode;

    /**
     * 组织机构编码
     */
    private String orgCode;

    /**
     * 现存量
     */
    private Integer existingQuantity;

    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    public String getGooderName() {
        return gooderName;
    }

    public void setGooderName(String gooderName) {
        this.gooderName = gooderName;
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getExistingQuantity() {
        return existingQuantity;
    }

    public void setExistingQuantity(Integer existingQuantity) {
        this.existingQuantity = existingQuantity;
    }
}
