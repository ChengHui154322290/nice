package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "k_inventory_bill")
public class InventoryBill {

    /**
     * 主键id
     */
    @Id
    @Column(name = "inventory_bill_id")
    private String inventoryBillId;


    /**
     * 盘点单编号
     */
    @Column(name = "inventory_bill_code")
    private String inventoryBillCode;

    public String getInventoryBillCode() {
        return inventoryBillCode;
    }

    public void setInventoryBillCode(String inventoryBillCode) {
        this.inventoryBillCode = inventoryBillCode;
    }

    /**
     * 源盘点单编号  -- 新增字段，在新增盘点单信息时，设置其默认值为 null 。   -- 2018/06/11  17:42  rk
     */
    @Column(name = "source_bill_code")
    private String sourceBillCode;


    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 盘点状态:0-未开始,1-盘点中,2-已完成
     */
    @Column(name = "inventory_status")
    private Integer inventoryStatus;

    /**
     * 盘点类型:0-二次盘点，1-RF盘点
     */
    @Column(name = "inventory_type")
    private Integer inventoryType;

    /**
     * 是否盲盘:0-否,1-是
     */
    @Column(name = "is_blind_inventory")
    private Integer isBlindInventory;

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

    /**
     * 是否有差异: 0-无差异， 1-有差异
     */
    @Transient
    private Integer isDifferent;


    @Transient
    List<InventoryBillDetail> inventoryBillDetails;

    public List<InventoryBillDetail> getInventoryBillDetails() {
        return inventoryBillDetails;
    }

    public void setInventoryBillDetails(List<InventoryBillDetail> inventoryBillDetails) {
        this.inventoryBillDetails = inventoryBillDetails;
    }


    /**
     *  获取 '是否差异'
     * @return
     */
    public Integer getIsDifferent() {
        return isDifferent;
    }

    /**
     *  设置 '是否差异'
     * @param isDifferent
     */
    public void setIsDifferent(Integer isDifferent) {
        this.isDifferent = isDifferent;
    }

    /**
     * 获取盘点单编号
     *
     * @return inventory_bill_id - 盘点单编号
     */
    public String getInventoryBillId() {
        return inventoryBillId;
    }

    /**
     * 设置盘点单编号
     *
     * @param inventoryBillId 盘点单编号
     */
    public void setInventoryBillId(String inventoryBillId) {
        this.inventoryBillId = inventoryBillId;
    }

    /**
     *  获取 源盘点单编号
     * @return
     */
    public String getSourceBillCode() {
        return sourceBillCode;
    }

    /**
     *  设置 源盘点单编号
     * @param sourceBillCode
     */
    public void setSourceBillCode(String sourceBillCode) {
        this.sourceBillCode = sourceBillCode;
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
     * 获取盘点状态:0-未开始,1-盘点中,2-已完成
     *
     * @return inventory_status - 盘点状态:0-未开始,1-盘点中,2-已完成
     */
    public Integer getInventoryStatus() {
        return inventoryStatus;
    }

    /**
     * 设置盘点状态:0-未开始,1-盘点中,2-已完成
     *
     * @param inventoryStatus 盘点状态:0-未开始,1-盘点中,2-已完成
     */
    public void setInventoryStatus(Integer inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    /**
     * 获取盘点类型:1-RF盘点
     *
     * @return inventory_type - 盘点类型:1-RF盘点
     */
    public Integer getInventoryType() {
        return inventoryType;
    }

    /**
     * 设置盘点类型:1-RF盘点
     *
     * @param inventoryType 盘点类型:1-RF盘点
     */
    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    /**
     * 获取是否盲盘:0-否,1-是
     *
     * @return is_blind_inventory - 是否盲盘:0-否,1-是
     */
    public Integer getIsBlindInventory() {
        return isBlindInventory;
    }

    /**
     * 设置是否盲盘:0-否,1-是
     *
     * @param isBlindInventory 是否盲盘:0-否,1-是
     */
    public void setIsBlindInventory(Integer isBlindInventory) {
        this.isBlindInventory = isBlindInventory;
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