package com.nice.good.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "d_out_task")
public class OutTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 行号生成,关联明细表
     */
    @Column(name = "line_code")
    private String lineCode;


    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    /**
     * 波次编号
     */
    @Column(name = "wave_code")
    private String waveCode;

    /**
     * 发货单号
     */
    @Column(name = "send_code")
    private String sendCode;

    /**
     * 任务号
     */
    @Column(name = "task_code")
    private String taskCode;

    /**
     * 优先级
     */
    private String priority;

    /**
     * 任务类型
     */
    @Column(name = "task_type")
    private String taskType;

    /**
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;

    /**
     * sku
     */
    @Column(name = "good_code")
    private String goodCode;

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
     * 关联明细表id
     */
    @Column(name = "detail_id")
    private String detailId;

    /**
     * 包装编码
     */
    @Column(name = "pack_code")
    private String packCode;

    /**
     * 状态 0 未开始,1操作中,2已完成
     */
    private Integer status;

    /**
     * 拣货量
     */
    @Column(name = "pick_num")
    private Integer pickNum;

    /**
     * 库位
     */
    @Column(name = "seat_code")
    private String seatCode;

    /**
     * 目标库位
     */
    @Column(name = "goal_seat")
    private String goalSeat;

    /**
     * LPN
     */
    private String lpn;

    /**
     * 备注
     */
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Transient
    private Integer rfid;


    public Integer getRfid() {
        return rfid;
    }

    public void setRfid(Integer rfid) {
        this.rfid = rfid;
    }


    @Transient
    private String imgId;

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    /**
     * 操作时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

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
     * 获取波次编号
     *
     * @return wave_code - 波次编号
     */
    public String getWaveCode() {
        return waveCode;
    }

    /**
     * 设置波次编号
     *
     * @param waveCode 波次编号
     */
    public void setWaveCode(String waveCode) {
        this.waveCode = waveCode;
    }

    /**
     * 获取发货单号
     *
     * @return send_code - 发货单号
     */
    public String getSendCode() {
        return sendCode;
    }

    /**
     * 设置发货单号
     *
     * @param sendCode 发货单号
     */
    public void setSendCode(String sendCode) {
        this.sendCode = sendCode;
    }

    /**
     * 获取任务号
     *
     * @return task_code - 任务号
     */
    public String getTaskCode() {
        return taskCode;
    }

    /**
     * 设置任务号
     *
     * @param taskCode 任务号
     */
    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    /**
     * 获取优先级
     *
     * @return priority - 优先级
     */
    public String getPriority() {
        return priority;
    }

    /**
     * 设置优先级
     *
     * @param priority 优先级
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * 获取任务类型
     *
     * @return task_type - 任务类型
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * 设置任务类型
     *
     * @param taskType 任务类型
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
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
     * 获取sku
     *
     * @return good_code - sku
     */
    public String getGoodCode() {
        return goodCode;
    }

    /**
     * 设置sku
     *
     * @param goodCode sku
     */
    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }


    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 关联明细表id
     *
     * @return detail_id
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置
     *
     * @param detailId 行号
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    /**
     * 获取包装编码
     *
     * @return pack_code - 包装编码
     */
    public String getPackCode() {
        return packCode;
    }

    /**
     * 设置包装编码
     *
     * @param packCode 包装编码
     */
    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取拣货量
     *
     * @return pick_num - 拣货量
     */
    public Integer getPickNum() {
        return pickNum;
    }

    /**
     * 设置拣货量
     *
     * @param pickNum 拣货量
     */
    public void setPickNum(Integer pickNum) {
        this.pickNum = pickNum;
    }

    /**
     * 获取库位
     *
     * @return seat_code - 库位
     */
    public String getSeatCode() {
        return seatCode;
    }

    /**
     * 设置库位
     *
     * @param seatCode 库位
     */
    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    /**
     * 获取目标库位
     *
     * @return goal_seat - 目标库位
     */
    public String getGoalSeat() {
        return goalSeat;
    }

    /**
     * 设置目标库位
     *
     * @param goalSeat 目标库位
     */
    public void setGoalSeat(String goalSeat) {
        this.goalSeat = goalSeat;
    }

    /**
     * 获取LPN
     *
     * @return lpn - LPN
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
     * 获取操作时间
     *
     * @return modify_time - 操作时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置操作时间
     *
     * @param modifyTime 操作时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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