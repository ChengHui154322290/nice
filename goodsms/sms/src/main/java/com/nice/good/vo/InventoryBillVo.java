package com.nice.good.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 根据盘点单查询条件封装的一个实体类  -- 2018/06/11  15:52  rk
 */
public class InventoryBillVo {

    /**
     * 盘点单编号
     */
    private String inventoryBillCode;

    /**
     * 盘点状态:0-未开始,1-盘点中,2-已完成
     */
    private Integer inventoryStatus;

    /**
     * 盘点类型:0-二次盘点，1-RF盘点
     */
    private Integer inventoryType;

    /**
     * 源盘点单
     */
    private String sourceBillCode;

    /**
     * 是否盲盘:0-否,1-是
     */
    private Integer isBlindInventory;

    /**
     * 是否差异： 0-否,1-是
     */
    private Integer isDifferent;

    /**
     * 说明
     */
    private String remark;

    /**
     * 创建人
     */
    private String createId;

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
     * 修改人
     */
    private String modifyId;

    /**
     * 修改开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifyDateStart;

    /**
     * 修改结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifyDateEnd;


    public String getInventoryBillCode() {
        return inventoryBillCode;
    }

    public void setInventoryBillCode(String inventoryBillCode) {
        this.inventoryBillCode = inventoryBillCode;
    }

    public Integer getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(Integer inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getSourceBillCode() {
        return sourceBillCode;
    }

    public void setSourceBillCode(String sourceBillCode) {
        this.sourceBillCode = sourceBillCode;
    }

    public Integer getIsBlindInventory() {
        return isBlindInventory;
    }

    public void setIsBlindInventory(Integer isBlindInventory) {
        this.isBlindInventory = isBlindInventory;
    }

    public Integer getIsDifferent() {
        return isDifferent;
    }

    public void setIsDifferent(Integer isDifferent) {
        this.isDifferent = isDifferent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
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

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public Date getModifyDateStart() {
        return modifyDateStart;
    }

    public void setModifyDateStart(Date modifyDateStart) {
        this.modifyDateStart = modifyDateStart;
    }

    public Date getModifyDateEnd() {
        return modifyDateEnd;
    }

    public void setModifyDateEnd(Date modifyDateEnd) {
        this.modifyDateEnd = modifyDateEnd;
    }
}
