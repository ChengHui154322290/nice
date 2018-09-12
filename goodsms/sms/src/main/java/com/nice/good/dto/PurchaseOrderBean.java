package com.nice.good.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Create by RenKuo 2018/04/27
 */
public class PurchaseOrderBean {

    /**
     *  采购单号
     */
    private String purchaseCode;

    /**
     * 货主编码
     */
    private List<String> gooderCodes;

    /**
     * 供应商编码
     */
    private List<String> providerCodes;

    /**
     * 承运商编码
     */
    private List<String> carrierCodes;

    /**
     * 组织机构编码
     */
    private List<String> orgCodes;

    /**
     * 场地编号
     */
    private List<String> placeNumbers;

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public List<String> getGooderCodes() {
        return gooderCodes;
    }

    public void setGooderCodes(List<String> gooderCodes) {
        this.gooderCodes = gooderCodes;
    }

    public List<String> getProviderCodes() {
        return providerCodes;
    }

    public void setProviderCodes(List<String> providerCodes) {
        this.providerCodes = providerCodes;
    }

    public List<String> getCarrierCodes() {
        return carrierCodes;
    }

    public void setCarrierCodes(List<String> carrierCodes) {
        this.carrierCodes = carrierCodes;
    }

    public List<String> getOrgCodes() {
        return orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public List<String> getPlaceNumbers() {
        return placeNumbers;
    }

    public void setPlaceNumbers(List<String> placeNumbers) {
        this.placeNumbers = placeNumbers;
    }

    // 无参构造函数
    public PurchaseOrderBean() {
    }

    // 部分参数构造函数  String purchaseCode, List<String> gooderCodes, List<String> providerCodes, List<String> carrierCodes
    public PurchaseOrderBean(String purchaseCode, List<String> gooderCodes, List<String> providerCodes, List<String> carrierCodes) {
        this.purchaseCode = purchaseCode;
        this.gooderCodes = gooderCodes;
        this.providerCodes = providerCodes;
        this.carrierCodes = carrierCodes;
    }

    // 部分参数构造函数 String purchaseCode, List<String> gooderCodes, List<String> providerCodes, List<String> carrierCodes, List<String> orgCodes
    public PurchaseOrderBean(String purchaseCode, List<String> gooderCodes, List<String> providerCodes, List<String> carrierCodes, List<String> orgCodes) {
        this.purchaseCode = purchaseCode;
        this.gooderCodes = gooderCodes;
        this.providerCodes = providerCodes;
        this.carrierCodes = carrierCodes;
        this.orgCodes = orgCodes;
    }

    // 部分参数构造函数 List<String> gooderCodes, List<String> providerCodes, List<String> orgCodes
    public PurchaseOrderBean(List<String> gooderCodes, List<String> providerCodes, List<String> orgCodes) {
        this.gooderCodes = gooderCodes;
        this.providerCodes = providerCodes;
        this.orgCodes = orgCodes;
    }

    // 全参构造函数
    public PurchaseOrderBean(String purchaseCode, List<String> gooderCodes, List<String> providerCodes, List<String> carrierCodes, List<String> orgCodes,
                             List<String> placeNumbers) {
        this.purchaseCode = purchaseCode;
        this.gooderCodes = gooderCodes;
        this.providerCodes = providerCodes;
        this.carrierCodes = carrierCodes;
        this.orgCodes = orgCodes;
        this.placeNumbers = placeNumbers;
    }

    // 重写 toString() 方法
    @Override
    public String toString() {
        return "PurchaseOrderBean{" +
                "purchaseCode='" + purchaseCode + '\'' +
                ", gooderCodes=" + gooderCodes +
                ", providerCodes=" + providerCodes +
                ", carrierCodes=" + carrierCodes +
                ", orgCodes=" + orgCodes +
                '}';
    }


}
