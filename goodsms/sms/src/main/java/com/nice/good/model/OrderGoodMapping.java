package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "o_order_good_mapping")
public class OrderGoodMapping {
    /**
     * 订单货品关联id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;

    /**
     * 货品编码
     */
    @Column(name = "good_code")
    private String goodCode;

    /**
     * 货品名称
     */
    @Column(name = "good_name")
    private String goodName;

    /**
     * 商品编码
     */
    @Column(name = "commodity_code")
    private String commodityCode;

    /**
     * 货品规格
     */
    @Column(name = "good_model")
    private String goodModel;


    /**
     * 组织机构编码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 供应商编码
     */
    @Column(name = "provider_code")
    private String providerCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    /**
     * 货品状态
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 采购数量
     */
    @Column(name = "purchase_number")
    private Integer purchaseNumber;

    /**
     * 税率(x%）
     */
    private String rate;

    /**
     * 进货价
     */
    @Column(name = "origin_price")
    private BigDecimal originPrice;

    /**
     * 含税进货价
     */
    @Column(name = "rate_price")
    private BigDecimal ratePrice;

    /**
     * 金额（进货价*采购数量）
     */
    private BigDecimal amount;

    /**
     * 总金额(含税金额=含税进货价*采购数量)
     */
    @Column(name = "rate_amount")
    private BigDecimal rateAmount;

    /**
     * 是否付款 0否,1是
     */
    @Column(name = "is_pay")
    private Integer isPay;

    /**
     * 创建人id
     */
    @Column(name = "create_id")
    private String createId;

    /**
     * 创建人姓名
     */
    @Column(name = "create_name")
    private String createName;

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
     * 修改人姓名
     */
    @Column(name = "modify_name")
    private String modifyName;


    /**
     * 修改时间
     */
    @Column(name = "modify_date")
    private Date modifyDate;


    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }


    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 审核id
     */
    @Column(name = "auditing_id")
    private String auditingId;

    /**
     * 审核人姓名
     */
    @Column(name = "auditing_name")
    private String auditingName;

    /**
     * 审核日期
     */
    @Column(name = "auditing_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditingDate;

    /**
     * 获取订单货品关联id
     *
     * @return id - 订单货品关联id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置订单货品关联id
     *
     * @param id 订单货品关联id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    /**
     * 获取货品编码
     *
     * @return good_code - 货品编码
     */
    public String getGoodCode() {
        return goodCode;
    }

    /**
     * 设置货品编码
     *
     * @param goodCode 货品编码
     */
    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    /**
     * 获取货品名称
     *
     * @return good_name - 货品名称
     */
    public String getGoodName() {
        return goodName;
    }

    /**
     * 设置货品名称
     *
     * @param goodName 货品名称
     */
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    /**
     * 获取商品编码
     *
     * @return commodity_code - 商品编码
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * 设置商品编码
     *
     * @param commodityCode 商品编码
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    /**
     * 获取货品规格
     *
     * @return good_model - 货品规格
     */
    public String getGoodModel() {
        return goodModel;
    }

    /**
     * 设置货品规格
     *
     * @param goodModel 货品规格
     */
    public void setGoodModel(String goodModel) {
        this.goodModel = goodModel;
    }

    /**
     * 获取采购数量
     *
     * @return purchase_number - 采购数量
     */
    public Integer getPurchaseNumber() {
        return purchaseNumber;
    }

    /**
     * 设置采购数量
     *
     * @param purchaseNumber 采购数量
     */
    public void setPurchaseNumber(Integer purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    /**
     * 获取税率(x%）
     *
     * @return rate - 税率(x%）
     */
    public String getRate() {
        return rate;
    }

    /**
     * 设置税率(x%）
     *
     * @param rate 税率(x%）
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     * 获取进货价
     *
     * @return origin_price - 进货价
     */
    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    /**
     * 设置进货价
     *
     * @param originPrice 进货价
     */
    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    /**
     * 获取含税进货价
     *
     * @return rate_price - 含税进货价
     */
    public BigDecimal getRatePrice() {
        return ratePrice;
    }

    /**
     * 设置含税进货价
     *
     * @param ratePrice 含税进货价
     */
    public void setRatePrice(BigDecimal ratePrice) {
        this.ratePrice = ratePrice;
    }

    /**
     * 获取金额（进货价*采购数量）
     *
     * @return amount - 金额（进货价*采购数量）
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置金额（进货价*采购数量）
     *
     * @param amount 金额（进货价*采购数量）
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取总金额(含税金额=含税进货价*采购数量)
     *
     * @return rate_amount - 总金额(含税金额=含税进货价*采购数量)
     */
    public BigDecimal getRateAmount() {
        return rateAmount;
    }

    /**
     * 设置总金额(含税金额=含税进货价*采购数量)
     *
     * @param rateAmount 总金额(含税金额=含税进货价*采购数量)
     */
    public void setRateAmount(BigDecimal rateAmount) {
        this.rateAmount = rateAmount;
    }

    /**
     * 获取是否付款 0否,1是
     *
     * @return is_pay - 是否付款 0否,1是
     */
    public Integer getIsPay() {
        return isPay;
    }

    /**
     * 设置是否付款 0否,1是
     *
     * @param isPay 是否付款 0否,1是
     */
    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
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
     * 获取创建人姓名
     *
     * @return create_name - 创建人姓名
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * 设置创建人姓名
     *
     * @param createName 创建人姓名
     */
    public void setCreateName(String createName) {
        this.createName = createName;
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
     * 获取审核id
     *
     * @return auditing_id - 审核id
     */
    public String getAuditingId() {
        return auditingId;
    }

    /**
     * 设置审核id
     *
     * @param auditingId 审核id
     */
    public void setAuditingId(String auditingId) {
        this.auditingId = auditingId;
    }

    /**
     * 获取审核人姓名
     *
     * @return auditing_name - 审核人姓名
     */
    public String getAuditingName() {
        return auditingName;
    }

    /**
     * 设置审核人姓名
     *
     * @param auditingName 审核人姓名
     */
    public void setAuditingName(String auditingName) {
        this.auditingName = auditingName;
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