package com.nice.good.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ChooseOrderVo {

    /**
     * 序号id
     */
    private Integer id;


    /**
     * 采购单主键id
     */
    private String orderId;



    /**
     * 采购类型0新品采购1补货采购2备货采购3其他采购
     */
    private Integer purchaseType;

    /**
     * 供应商编码
     */
    private String providerCode;


    /**
     * 商品编码
     */
    private String commodityCode;

    /**
     * 货品编码
     */
    private String goodCode;


    /**
     * 货品名称
     */
    private String goodName;


    /**
     * 组织机构编码
     */
    private String orgCode;


    /**
     * 货主编码
     */
    private String gooderCode;


    /**
     * 货品规格
     */
    private String goodModel;


    /**
     * 创建人id
     */
    private String createId;


    /**
     * 审核人id
     */
    private String  auditingId;


    /**
     * 创建日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDate;

    /**

     * 审核日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date auditingDate;


    /**
     * 创建开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDateStart;

    /**
     * 创建结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDateEnd;

    /**
     * 审核开始日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date auditingDateStart;

    /**

     * 审核结束日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date auditingDateEnd;



    /**
     * 采购单号
     * @return
     */
    private String purchaseCode;

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }


    /**
     * 备注
     */
    private String statement;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
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

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    public String getGoodModel() {
        return goodModel;
    }

    public void setGoodModel(String goodModel) {
        this.goodModel = goodModel;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getAuditingId() {
        return auditingId;
    }

    public void setAuditingId(String auditingId) {
        this.auditingId = auditingId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getAuditingDate() {
        return auditingDate;
    }

    public void setAuditingDate(Date auditingDate) {
        this.auditingDate = auditingDate;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Date getAuditingDateStart() {
        return auditingDateStart;
    }

    public void setAuditingDateStart(Date auditingDateStart) {
        this.auditingDateStart = auditingDateStart;
    }

    public Date getAuditingDateEnd() {
        return auditingDateEnd;
    }

    public void setAuditingDateEnd(Date auditingDateEnd) {
        this.auditingDateEnd = auditingDateEnd;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
