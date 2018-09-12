package com.nice.good.vo;

public class PdaDetailVo {

    //收货单明细id
    private String detailId;

    //ASN收货单号
    private String receiveCode;

    //供应商编码
    private String providerCode;

    //货主编码'
    private String gooderCode;

    //SKU/RFID
    private String skuOrRfid;

    //sku
    private String goodCode;

    //接收量
    private Integer receiveNum;

    //拒收量
    private Integer refuseNum;

    //预期量
    private Integer expectNum;

    //LPN
    private String lpn;

    //暂存库位
    private String seatCode;

    //图片
    private String imgUrl;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    public String getSkuOrRfid() {
        return skuOrRfid;
    }

    public void setSkuOrRfid(String skuOrRfid) {
        this.skuOrRfid = skuOrRfid;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }


    public Integer getRefuseNum() {
        return refuseNum;
    }

    public void setRefuseNum(Integer refuseNum) {
        this.refuseNum = refuseNum;
    }

    public Integer getExpectNum() {
        return expectNum;
    }

    public void setExpectNum(Integer expectNum) {
        this.expectNum = expectNum;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
