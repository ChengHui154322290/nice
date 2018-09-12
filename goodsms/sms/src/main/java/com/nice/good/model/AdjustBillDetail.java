package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "k_adjust_bill_detail")
public class AdjustBillDetail {
    /**
     * 主键调整单明细id,后台生成
     */
    @Id
    @Column(name = "detail_id")
    private String detailId;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 调整单编号
     */
    @Column(name = "adjust_bill_id")
    private String adjustBillId;

    /**
     * 供应商编码
     */
    @Column(name = "provider_code")
    private String providerCode;

    /**
     * 组织机构编码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;

    /**
     * 货主名称
     */
    @Column(name = "gooder_name")
    private String gooderName;

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
     * 库位编码
     */
    @Column(name = "seat_code")
    private String seatCode;

    /**
     * 现存量
     */
    @Column(name = "existing_quantity")
    private Integer existingQuantity;

    /**
     * 调整量
     */
    @Column(name = "adjust_quantity")
    private Integer adjustQuantity;

    /**
     * 调整后数量
     */
    @Column(name = "adjusted_quantity")
    private Integer adjustedQuantity;

    /**
     * 调整状态:0-未开始,1-调整中,3已完成
     */
    private Integer status;

    /**
     * 说明
     */
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



    // 全参构造函数
    public AdjustBillDetail(String adjustBillId, String detailId,  String providerCode, String orgCode,
                            String gooderCode, String gooderName, String goodCode, String goodName,
                            String seatCode, Integer existingQuantity, Integer adjustQuantity, Integer adjustedQuantity,
                            Integer status, String remark, String createId, Date createDate, String modifyId, Date modifyDate) {
        this.adjustBillId = adjustBillId;
        this.detailId = detailId;
        this.providerCode = providerCode;
        this.orgCode = orgCode;
        this.gooderCode = gooderCode;
        this.gooderName = gooderName;
        this.goodCode = goodCode;
        this.goodName = goodName;
        this.seatCode = seatCode;
        this.existingQuantity = existingQuantity;
        this.adjustQuantity = adjustQuantity;
        this.adjustedQuantity = adjustedQuantity;
        this.status = status;
        this.remark = remark;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyId = modifyId;
        this.modifyDate = modifyDate;
    }

    // 无参构造函数
    public AdjustBillDetail() {
    }

    /**
     * 获取主键调整单明细id,后台生成
     *
     * @return detail_id - 主键调整单明细id,后台生成
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置主键调整单明细id,后台生成
     *
     * @param detailId 主键调整单明细id,后台生成
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
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
     * 获取供应商编码
     * @return
     */
    public String getProviderCode() {
        return providerCode;
    }

    /**
     * 设置供应商编码
     * @param providerCode
     */
    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    /**
     * 获取组织机构编码
     *
     * @return org_code - 组织机构编码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置组织机构编码
     *
     * @param orgCode 组织机构编码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取货主编码
     *
     * @return gooder_code - 货主编码
     */
    public String getGooderCode() {
        return gooderCode;
    }

    /**
     * 设置货主编码
     *
     * @param gooderCode 货主编码
     */
    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    /**
     * 获取货主名称
     *
     * @return gooder_name - 货主名称
     */
    public String getGooderName() {
        return gooderName;
    }

    /**
     * 设置货主名称
     *
     * @param gooderName 货主名称
     */
    public void setGooderName(String gooderName) {
        this.gooderName = gooderName;
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
     * 获取库位编码
     *
     * @return seat_code - 库位编码
     */
    public String getSeatCode() {
        return seatCode;
    }

    /**
     * 设置库位编码
     *
     * @param seatCode 库位编码
     */
    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    /**
     * 获取现存量
     *
     * @return existing_quantity - 现存量
     */
    public Integer getExistingQuantity() {
        return existingQuantity;
    }

    /**
     * 设置现存量
     *
     * @param existingQuantity 现存量
     */
    public void setExistingQuantity(Integer existingQuantity) {
        this.existingQuantity = existingQuantity;
    }

    /**
     * 获取调整量
     *
     * @return adjust_quantity - 调整量
     */
    public Integer getAdjustQuantity() {
        return adjustQuantity;
    }

    /**
     * 设置调整量
     *
     * @param adjustQuantity 调整量
     */
    public void setAdjustQuantity(Integer adjustQuantity) {
        this.adjustQuantity = adjustQuantity;
    }

    /**
     * 获取调整后数量
     *
     * @return adjusted_quantity - 调整后数量
     */
    public Integer getAdjustedQuantity() {
        return adjustedQuantity;
    }

    /**
     * 设置调整后数量
     *
     * @param adjustedQuantity 调整后数量
     */
    public void setAdjustedQuantity(Integer adjustedQuantity) {
        this.adjustedQuantity = adjustedQuantity;
    }

    /**
     * 获取调整状态:0-未开始,1-调整中,2-已完成
     *
     * @return status - 调整状态:0-未开始,1-调整中,2-已完成
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置调整状态:0-未开始,1-调整中,2-已完成
     *
     * @param status 调整状态:0-未开始,1-调整中,2-已完成
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取说明
     *
     * @return remark - 说明
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