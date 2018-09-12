package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "k_inventory_bill_detail")
public class InventoryBillDetail {

    /**
     * 主键盘点单明细id,后台生成
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
     * 盘点单编号
     */
    @Column(name = "inventory_bill_id")
    private String inventoryBillId;

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
     * 库区编号
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 批号
     */
    @Column(name = "lot_num")
    private String lotNum;

    /**
     * LPN
     */
    @Column(name = "LPN")
    private String lpn;

    /**
     * 现存量
     */
    @Column(name = "existing_quantity")
    private Integer existingQuantity;

    /**
     * 盘点量
     */
    @Column(name = "inventory_quantity")
    private Integer inventoryQuantity;

    /**
     * 差异量
     */
    @Column(name = "diff_quantity")
    private Integer diffQuantity;

    /**
     * 盘点状态:0-未开始, 1-盘点中， 2-已完成
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
    public InventoryBillDetail(Integer id, String inventoryBillId, String detailId, String providerCode, String orgCode, String gooderCode,
                               String gooderName, String goodCode, String goodName, String seatCode, String areaCode, String lotNum,
                               String lpn, Integer existingQuantity, Integer inventoryQuantity, Integer diffQuantity, Integer status,
                               String remark, String createId, Date createDate, String modifyId, Date modifyDate) {
        this.id = id;
        this.inventoryBillId = inventoryBillId;
        this.detailId = detailId;
        this.providerCode = providerCode;
        this.orgCode = orgCode;
        this.gooderCode = gooderCode;
        this.gooderName = gooderName;
        this.goodCode = goodCode;
        this.goodName = goodName;
        this.seatCode = seatCode;
        this.areaCode = areaCode;
        this.lotNum = lotNum;
        this.lpn = lpn;
        this.existingQuantity = existingQuantity;
        this.inventoryQuantity = inventoryQuantity;
        this.diffQuantity = diffQuantity;
        this.status = status;
        this.remark = remark;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyId = modifyId;
        this.modifyDate = modifyDate;
    }



    // 无参构造函数
    public InventoryBillDetail() {
    }

    /**
     * 获取主键盘点单明细id,后台生成
     *
     * @return detail_id - 主键盘点单明细id,后台生成
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置主键盘点单明细id,后台生成
     *
     * @param detailId 主键盘点单明细id,后台生成
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
     * 获取供应商编码
     *
     * @return provider_code - 供应商编码
     */
    public String getProviderCode() {
        return providerCode;
    }

    /**
     * 设置供应商编码
     *
     * @param providerCode 供应商编码
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
     * 获取库区编号
     *
     * @return area_code - 库区编号
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置库区编号
     *
     * @param areaCode 库区编号
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取批号
     *
     * @return lot_num - 批号
     */
    public String getLotNum() {
        return lotNum;
    }

    /**
     * 设置批号
     *
     * @param lotNum 批号
     */
    public void setLotNum(String lotNum) {
        this.lotNum = lotNum;
    }

    /**
     * 获取LPN
     *
     * @return LPN - LPN
     */
    public String getLpn() {
        return lpn;
    }

    /**
     * 设置LPN
     *
     * @param lpn LPN
     */
    public void setLpn(String lpn) {
        this.lpn = lpn;
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
     * 获取盘点量
     *
     * @return inventory_quantity - 盘点量
     */
    public Integer getInventoryQuantity() {
        return inventoryQuantity;
    }

    /**
     * 设置盘点量
     *
     * @param inventoryQuantity 盘点量
     */
    public void setInventoryQuantity(Integer inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    /**
     * 获取差异量
     *
     * @return diff_quantity - 差异量
     */
    public Integer getDiffQuantity() {
        return diffQuantity;
    }

    /**
     * 设置差异量
     *
     * @param diffQuantity 差异量
     */
    public void setDiffQuantity(Integer diffQuantity) {
        this.diffQuantity = diffQuantity;
    }

    /**
     * 获取盘点状态:0-未开始,1-已完成
     *
     * @return status - 盘点状态:0-未开始,1-已完成
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置盘点状态:0-未开始,1-已完成
     *
     * @param status 盘点状态:0-未开始,1-已完成
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