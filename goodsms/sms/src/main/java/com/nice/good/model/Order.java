package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Table(name = "o_order")
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *主键id,后台生成
     */
    @Id
    @Column(name = "order_id")
    private String orderId;

    /**
     * 采购单号
     */
    @Column(name = "purchase_code")
    private String purchaseCode;

    /**
     * 外部单号
     */
    @Column(name = "outside_code")
    private String outsideCode;

    /**
     * 单据状态0未开始1已确认2收货中3已收货5质检中6已质检7已结算
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 采购类型0新品采购1补货采购2备货采购3其他采购
     */
    @Column(name = "purchase_type")
    private Integer purchaseType;


    /**
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;


    /**
     * 货主名称
     */
    @Transient
    private String gooderName;



    /**
     * 供应商编码
     */
    @Column(name = "provider_code")
    private String providerCode;


    /**
     * 供应商名称
     */
    @Transient
    private String providerName;



    /**
     * 场地编码
     */
    @Column(name = "place_number")
    private String placeNumber;

    /**
     * 场地名称
     */

    @Transient
    private String exhibition;

    
    /**
     * 说明
     */
    @Column(name = "statement")
    private String statement;

    /**
     * 组织机构编码
     */
    @Column(name = "org_code")
    private String orgCode;


    /**
     * 组织机构名称
     */
    @Transient
    private  String orgName;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    /**
     * 承运商编码
     */
    @Transient
    private  String carrierCode;

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    /**
     * 创建人id
     */
    @Column(name = "create_id")
    private String createId;

    /**
     * 创建人name
     */
    @Column(name = "create_name")
    private String createName;

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 创建日期
     */
    @Column(name = "create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 修改人id
     */
    @Column(name = "modify_id")
    private String modifyId;

    /**
     * 修改时间
     */
    @Column(name = "modify_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyDate;

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 审核人id
     */
    @Column(name = "auditing_id")
    private String  auditingId;

    /**
     * 审核人name
     */
    @Column(name = "auditing_name")
    private String  auditingName;


    public String getAuditingName() {
        return auditingName;
    }

    public void setAuditingName(String auditingName) {
        this.auditingName = auditingName;
    }

    /**

     * 审核日期
     */
    @Column(name = "auditing_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditingDate;

    /**
     * 发货时间
     */
    @Column(name = "send_time")
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date sendTime;

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }


    /**
     * 货品编码
     */
    @Transient
    private String goodCode;

	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}

    public String getGoodCode() {
        return goodCode;
    }


    /**
     * 承运商
     */
    @Transient
    private String carrierName;

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    /**
     * 采购明细对象集合
     */
	@Transient
    private List<OrderGoodMapping> objs; 
    
    

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public void setObjs(List<OrderGoodMapping> objs) {
		this.objs = objs;
	}

	
	public List<OrderGoodMapping> getObjs() {
		return objs;
	}


    /**
     * 创建开始时间
     */
     @Transient
     @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
     private Date createDateStart;

    /**
     * 创建结束时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDateEnd;

    /**
     * 审核开始日期
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditingDateStart;

    /**

     * 审核结束日期
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditingDateEnd;


    public Date getCreateDateStart() {
        return createDateStart;
    }


    public void setCreateDateStart(Date createDateStart) throws ParseException {

        this.createDateStart = createDateStart;
    }


    public Date getCreateDateEnd() {
        return createDateEnd;
    }


    public void setCreateDateEnd(Date createDateEnd) throws ParseException {

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




    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取内部订单id
     *
     * @return order_id - 内部订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置内部订单id
     *
     * @param orderId 内部订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取采购单号
     *
     * @return purchase_code - 采购单号
     */
    public String getPurchaseCode() {
        return purchaseCode;
    }

    /**
     * 设置采购单号
     *
     * @param purchaseCode 采购单号
     */
    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    /**
     * 获取外部单号
     *
     * @return outside_code - 外部单号
     */
    public String getOutsideCode() {
        return outsideCode;
    }

    /**
     * 设置外部单号
     *
     * @param outsideCode 外部单号
     */
    public void setOutsideCode(String outsideCode) {
        this.outsideCode = outsideCode;
    }

    /**
     * 获取单据状态0未开始1收货中2已确认3已收货4已质检5质检中6已结算
     *
     * @return order_status - 单据状态0未开始1收货中2已确认3已收货4已质检5质检中6已结算
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置单据状态 0未开始 1 已确认 2 收货中 3 已收货 4 已质检 5 质检中 6 已结算
     *
     * @param orderStatus 单据状态 0未开始 1已确认 2收货中 3已收货 4 已质检 5质检中 6已结算
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取采购类型0新品采购1补货采购2备货采购3其他采购
     *
     * @return purchase_type - 采购类型0新品采购1补货采购2备货采购3其他采购
     */
    public Integer getPurchaseType() {
        return purchaseType;
    }

    /**
     * 设置采购类型0新品采购1补货采购2备货采购3其他采购
     *
     * @param purchaseType 采购类型0新品采购1补货采购2备货采购3其他采购
     */
    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }


    /**
     * 获取创建人id
     *
     * @return create_id - 创建人id
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * 设置创建人id
     *
     * @param createId 创建人id
     */
    public void setCreateId(String createId) {
        this.createId = createId;
    }

    /**
     * 获取创建日期
     *
     * @return create_date - 创建日期
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建日期
     *
     * @param createDate 创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取审核人id
     *
     * @return auditing_id - 审核人id
     */
    public String getAuditingId() {
        return auditingId;
    }

    /**
     * 设置审核人id
     *
     * @param auditingId 审核人id
     */
    public void setAuditingId(String auditingId) {
        this.auditingId = auditingId;
    }


    /**
     * 获取审核日期
     *
     * @return auditing_date - 审核日期
     */
    public Date getAuditingDate() {
        return auditingDate;
    }

    /**
     * 设置审核日期
     *
     * @param auditingDate 审核日期
     */
    public void setAuditingDate(Date auditingDate) {
        this.auditingDate = auditingDate;
    }

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

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    public String getExhibition() {
        return exhibition;
    }

    public void setExhibition(String exhibition) {
        this.exhibition = exhibition;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    /**
     * 场地id
     */
    @Column(name = "place_id")
    private String placeId ;


    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }


    /**
     * 企业id
     */
    @Column(name = "company_id")
    private String companyId;


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}