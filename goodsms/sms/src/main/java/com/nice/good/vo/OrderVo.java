package com.nice.good.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class OrderVo {

    /**
     *序号id
     */
    private Integer id;

    /**
     * 采购单主键orderId
     */
    private String orderId;


    /**
     * 采购单号
     */

    private String purchaseCode;

    /**
     * 外部单号
     */

    private String outsideCode;

    /**
     * 货主编码
     */
    private String gooderCode;

    /**
     * 单据状态0未开始1已确认2收货中3已收货5质检中6已质检7已结算
     */
    private Integer orderStatus;

    /**
     * 组织机构编码
     */
    private String orgCode;

    /**
     * 采购类型0新品采购1补货采购2备货采购3其他采购
     */

    private Integer purchaseType;

    /**
     * 供应商编码
     */
    private String providerCode;

    /**
     * 承运编码 来源于收货单表
     */
    private String carrierCode;




    /**
     * 采购数量 采购明细汇总数量
     */

    private Integer purchaseNumber;


    /**
     * 收货量  来源于收货明细表
     */
    private Integer receiveNum;


    /**
     * 备注
     */
    private String statement;


    /**
     * 创建人name
     */

    private String createName;


    /**
     * 创建日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDate;


    /**
     * 审核人name
     */

    private String  auditingName;
    /**

     * 审核日期
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date auditingDate;

    /**
     * 送货时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date sendTime;



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

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getOutsideCode() {
        return outsideCode;
    }

    public void setOutsideCode(String outsideCode) {
        this.outsideCode = outsideCode;
    }


    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
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

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }


    public String getAuditingName() {
        return auditingName;
    }

    public void setAuditingName(String auditingName) {
        this.auditingName = auditingName;
    }

    /*===================================企业id=====================================*/
//    /**
//     * 企业id
//     */
////    @Column(name = "company_id")
//    private String companyId;
//
//
//    public String getCompanyId() {
//        return companyId;
//    }
//
//    public void setCompanyId(String companyId) {
//        this.companyId = companyId;
//    }
//    /*===================================场地id=====================================*/
//    /**
//     * 场地id
//     */
////    @Column(name = "place_id")
//    private String placeId ;
//
//
//    public String getPlaceId() {
//        return placeId;
//    }
//
//    public void setPlaceId(String placeId) {
//        this.placeId = placeId;
//    }

}
