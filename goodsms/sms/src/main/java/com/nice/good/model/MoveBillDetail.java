package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "k_move_bill_detail")
public class MoveBillDetail {
    /**
     * 主键移动单明细id,后台生成
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
     * 移动单编号
     */
    @Column(name = "move_bill_id")
    private String moveBillId;

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
     * 来源库位
     */
    @Column(name = "source_seat")
    private String sourceSeat;

    /**
     * 现存量
     */
    @Column(name = "existing_quantity")
    private Integer existingQuantity;

    /**
     * 移动量
     */
    @Column(name = "move_quantity")
    private Integer moveQuantity;

    /**
     * 目标库位
     */
    @Column(name = "target_seat")
    private String targetSeat;

    /**
     * 移动状态:0-未开始,1-移动中,2-已完成
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
    public MoveBillDetail(String detailId, Integer id, String moveBillId, String providerCode, String orgCode, String gooderCode,
                          String gooderName, String goodCode, String goodName, String sourceSeat, Integer existingQuantity,
                          Integer moveQuantity, String targetSeat, Integer status, String remark, String createId, Date createDate, String modifyId, Date modifyDate) {
        this.detailId = detailId;
        this.id = id;
        this.moveBillId = moveBillId;
        this.providerCode = providerCode;
        this.orgCode = orgCode;
        this.gooderCode = gooderCode;
        this.gooderName = gooderName;
        this.goodCode = goodCode;
        this.goodName = goodName;
        this.sourceSeat = sourceSeat;
        this.existingQuantity = existingQuantity;
        this.moveQuantity = moveQuantity;
        this.targetSeat = targetSeat;
        this.status = status;
        this.remark = remark;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyId = modifyId;
        this.modifyDate = modifyDate;
    }



    // 无参构造函数
    public MoveBillDetail() {
    }

    /**
     * 获取主键移动单明细id,后台生成
     *
     * @return detail_id - 主键移动单明细id,后台生成
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置主键移动单明细id,后台生成
     *
     * @param detailId 主键移动单明细id,后台生成
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
     * 获取移动单编号
     *
     * @return move_bill_id - 移动单编号
     */
    public String getMoveBillId() {
        return moveBillId;
    }

    /**
     * 设置移动单编号
     *
     * @param moveBillId 移动单编号
     */
    public void setMoveBillId(String moveBillId) {
        this.moveBillId = moveBillId;
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
     * 获取来源库位
     *
     * @return source_seat - 来源库位
     */
    public String getSourceSeat() {
        return sourceSeat;
    }

    /**
     * 设置来源库位
     *
     * @param sourceSeat 来源库位
     */
    public void setSourceSeat(String sourceSeat) {
        this.sourceSeat = sourceSeat;
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
     * 获取移动量
     *
     * @return move_quantity - 移动量
     */
    public Integer getMoveQuantity() {
        return moveQuantity;
    }

    /**
     * 设置移动量
     *
     * @param moveQuantity 移动量
     */
    public void setMoveQuantity(Integer moveQuantity) {
        this.moveQuantity = moveQuantity;
    }

    /**
     * 获取目标库位
     *
     * @return target_seat - 目标库位
     */
    public String getTargetSeat() {
        return targetSeat;
    }

    /**
     * 设置目标库位
     *
     * @param targetSeat 目标库位
     */
    public void setTargetSeat(String targetSeat) {
        this.targetSeat = targetSeat;
    }

    /**
     * 获取移动状态:0-未开始,1-移动中,2-已完成
     *
     * @return status 移动状态:0-未开始,1-移动中,2-已完成
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置移动状态:0-未开始,1-移动中,2-已完成
     *
     * @param status 移动状态:0-未开始,1-移动中,2-已完成
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


    /**
     * 移动类型
     */

}