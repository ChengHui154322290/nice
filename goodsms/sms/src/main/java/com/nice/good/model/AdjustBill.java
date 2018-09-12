package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Table(name = "k_adjust_bill")
public class AdjustBill {


    /**
     * 主键id
     */
    @Id
    @Column(name = "adjust_bill_id")
    private String adjustBillId;

    /**
     * 调整单编号
     */
    @Column(name = "adjust_bill_code")
    private String adjustBillCode;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 来源单编号
     */
    @Column(name = "source_bill_code")
    private String sourceBillCode;

    /**
     * 调整状态:0-未开始,1-调整中,2-已完成
     */
    @Column(name = "adjust_status")
    private Integer adjustStatus;

    /**
     * 调整类型:0-正常调整,1-盘点调整
     */
    @Column(name = "adjust_type")
    private Integer adjustType;

    /**
     * 说明
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建人
     */
    @Column(name = "create_id")
    private String createId;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDate;

    /**
     * 修改人
     */
    @Column(name = "modify_id")
    private String modifyId;

    /**
     * 修改时间
     */
    @Column(name = "modify_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifyDate;

    /**
     * 创建开始时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDateStart;

    /**
     * 创建结束时间
     */
    @javax.persistence.Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDateEnd;

    /**
     * 修改开始时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifyDateStart;

    /**
     * 修改结束时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifyDateEnd;


    /**
     *  获取创建开始时间
     * @return
     */
    public Date getCreateDateStart() {
        return createDateStart;
    }

    /**
     * 设置创建开始时间
     * @param createDateStart
     */
    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    /**
     * 获取创建结束时间
     * @return
     */
    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    /**
     * 设置创建结束时间
     * @param createDateEnd
     */
    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    /**
     * 获取修改开始时间
     * @return
     */
    public Date getModifyDateStart() {
        return modifyDateStart;
    }

    /**
     * 设置修改开始时间
     * @param modifyDateStart
     */
    public void setModifyDateStart(Date modifyDateStart) {
        this.modifyDateStart = modifyDateStart;
    }

    /**
     * 获取修改结束时间
     * @return
     */
    public Date getModifyDateEnd() {
        return modifyDateEnd;
    }

    /**
     * 设置修改结束时间
     * @param modifyDateEnd
     */
    public void setModifyDateEnd(Date modifyDateEnd) {
        this.modifyDateEnd = modifyDateEnd;
    }

    /**
     * 获取调整单编号
     *
     * @return adjust_bill_id - 调整单编号
     */
    public String getAdjustBillId() {
        return adjustBillId;
    }

    /**
     * 设置调整单编号
     *
     * @param adjustBillId 调整单编号
     */
    public void setAdjustBillId(String adjustBillId) {
        this.adjustBillId = adjustBillId;
    }

    /**
     * 获取序号id,自动增长
     *
     * @return id - 序号id,自动增长
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置序号id,自动增长
     *
     * @param id 序号id,自动增长
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取来源单编号
     *
     * @return source_bill_code - 来源单编号
     */
    public String getSourceBillCode() {
        return sourceBillCode;
    }

    /**
     * 设置来源单编号
     *
     * @param sourceBillCode 来源单编号
     */
    public void setSourceBillCode(String sourceBillCode) {
        this.sourceBillCode = sourceBillCode;
    }

    /**
     * 获取调整状态:0-未开始,1-调整中,2-已完成
     *
     * @return adjust_status - 调整状态:0-未开始,1-调整中,2-已完成
     */
    public Integer getAdjustStatus() {
        return adjustStatus;
    }

    /**
     * 设置调整状态:0-未开始,1-调整中,2-已完成
     *
     * @param adjustStatus 调整状态:0-未开始,1-调整中,2-已完成
     */
    public void setAdjustStatus(Integer adjustStatus) {
        this.adjustStatus = adjustStatus;
    }

    /**
     * 获取调整类型:0-正常调整,1-盘点调整
     *
     * @return adjust_type - 调整类型:0-正常调整,1-盘点调整
     */
    public Integer getAdjustType() {
        return adjustType;
    }

    /**
     * 设置调整类型:0-正常调整,1-盘点调整
     *
     * @param adjustType 调整类型:0-正常调整,1-盘点调整
     */
    public void setAdjustType(Integer adjustType) {
        this.adjustType = adjustType;
    }

    /**
     * 获取说明
     *
     * @return explain - 说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置说明
     *
     * @param remark 说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建人
     *
     * @return create_id - 创建人
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * 设置创建人
     *
     * @param createId 创建人
     */
    public void setCreateId(String createId) {
        this.createId = createId;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改人
     *
     * @return modify_id - 修改人
     */
    public String getModifyId() {
        return modifyId;
    }

    /**
     * 设置修改人
     *
     * @param modifyId 修改人
     */
    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    /**
     * 获取修改时间
     *
     * @return modify_date - 修改时间
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 设置修改时间
     *
     * @param modifyDate 修改时间
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getAdjustBillCode() {
        return adjustBillCode;
    }

    public void setAdjustBillCode(String adjustBillCode) {
        this.adjustBillCode = adjustBillCode;
    }

    @Transient
    List<AdjustBillDetail> adjustBillDetails;

    public List<AdjustBillDetail> getAdjustBillDetails() {
        return adjustBillDetails;
    }

    public void setAdjustBillDetails(List<AdjustBillDetail> adjustBillDetails) {
        this.adjustBillDetails = adjustBillDetails;
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