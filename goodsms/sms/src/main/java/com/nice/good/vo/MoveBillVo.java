package com.nice.good.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Transient;
import java.util.Date;

/**
 *  根据移动单查询条件封装的一实体类  -- 2018/06/06  10:25  rk
 */
public class MoveBillVo {

    /**
     * 移动单编号
     */
    private String moveBillId;

    /**
     * 移动状态:0-未开始,1-部分移动,2-已完成
     */
    private Integer moveStatus;

    /**
     * 移动类型:0-正常移动
     */
    private Integer moveType;

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
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDateStart;

    /**
     * 创建结束时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDateEnd;

    /**
     * 修改人
     */
    private String modifyId;

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
     * 来源库位
     */
    private String sourceSeat;

    /**
     * 目标库位
     */
    private String targetSeat;

    public String getMoveBillId() {
        return moveBillId;
    }

    public void setMoveBillId(String moveBillId) {
        this.moveBillId = moveBillId;
    }

    public Integer getMoveStatus() {
        return moveStatus;
    }

    public void setMoveStatus(Integer moveStatus) {
        this.moveStatus = moveStatus;
    }

    public Integer getMoveType() {
        return moveType;
    }

    public void setMoveType(Integer moveType) {
        this.moveType = moveType;
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

    public String getSourceSeat() {
        return sourceSeat;
    }

    public void setSourceSeat(String sourceSeat) {
        this.sourceSeat = sourceSeat;
    }

    public String getTargetSeat() {
        return targetSeat;
    }

    public void setTargetSeat(String targetSeat) {
        this.targetSeat = targetSeat;
    }
}
