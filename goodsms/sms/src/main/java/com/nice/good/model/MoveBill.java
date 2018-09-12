package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "k_move_bill")
public class MoveBill {


    /**
     * 主键id
     */
    @Id
    @Column(name = "move_bill_id")
    private String moveBillId;


    /**
     * 移动单编号
     */
    @Column(name = "move_bill_code")
    private String moveBillCode;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 移动状态:0-未开始,1-部分移动,2-已完成
     */
    @Column(name = "move_status")
    private Integer moveStatus;

    /**
     * 移动类型:0-正常移动 ,1借领,2归还
     */
    @Column(name = "move_type")
    private Integer moveType;

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


    @Transient
    private List<MoveBillDetail> moveBillDetails;


    public List<MoveBillDetail> getMoveBillDetails() {
        return moveBillDetails;
    }

    public void setMoveBillDetails(List<MoveBillDetail> moveBillDetails) {
        this.moveBillDetails = moveBillDetails;
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
     * 获取移动状态:0-未开始,1-部分移动,2-已完成
     *
     * @return move_status - 移动状态:0-未开始,1-部分移动,2-已完成
     */
    public Integer getMoveStatus() {
        return moveStatus;
    }

    /**
     * 设置移动状态:0-未开始,1-部分移动,2-已完成
     *
     * @param moveStatus 移动状态:0-未开始,1-部分移动,2-已完成
     */
    public void setMoveStatus(Integer moveStatus) {
        this.moveStatus = moveStatus;
    }

    /**
     * 获取移动类型:0-正常移动
     *
     * @return move_type - 移动类型:0-正常移动
     */
    public Integer getMoveType() {
        return moveType;
    }

    /**
     * 设置移动类型:0-正常移动
     *
     * @param moveType 移动类型:0-正常移动
     */
    public void setMoveType(Integer moveType) {
        this.moveType = moveType;
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


    public String getMoveBillCode() {
        return moveBillCode;
    }

    public void setMoveBillCode(String moveBillCode) {
        this.moveBillCode = moveBillCode;
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