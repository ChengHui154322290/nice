package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "d_out_pick")
public class OutPick {
    @Id
    @Column(name = "pick_id")
    private String pickId;

    /**
     * 行号,自动增长
     */
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
     * 发货单号
     */
    @Column(name = "send_code")
    private String sendCode;

    /**
     * 拣货明细单号
     */
    @Column(name = "detail_code")
    private String detailCode;

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
     * 包装编码
     */
    @Column(name = "pack_code")
    private String packCode;


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
     * 拣货量
     */
    @Column(name = "pick_num")
    private Integer pickNum;

    /**
     * 库位编码
     */
    @Column(name = "seat_code")
    private String seatCode;

    /**
     * 目标库位
     */
    @Column(name = "goal_seat_code")
    private String goalSeatCode;

    /**
     * rfid
     */
    private Integer rfid;

    /**
     * 操作员
     */
    private String operator;

    /**
     * 操作时间
     */
    @Column(name = "operate_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operateTime;


    /**
     * 原因
     */
    private String reason;

    /**
     * 明细表行号
     */
    @Column(name = "detail_id")
    private String detailId;

    @Column(name = "create_id")
    private String createId;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyDate;

    /**
     * @return pick_id
     */
    public String getPickId() {
        return pickId;
    }

    /**
     * @param pickId
     */
    public void setPickId(String pickId) {
        this.pickId = pickId;
    }

    /**
     * 获取行号,自动增长
     *
     * @return id - 行号,自动增长
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置行号,自动增长
     *
     * @param id 行号,自动增长
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 获取拣货明细单号
     *
     * @return detail_code - 拣货明细单号
     */
    public String getDetailCode() {
        return detailCode;
    }

    /**
     * 设置拣货明细单号
     *
     * @param detailCode 拣货明细单号
     */
    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
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
     * @return seat_code - 库位编码
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
     * @return goal_seat_code - 目标库位
     */
    public String getGoalSeatCode() {
        return goalSeatCode;
    }

    /**
     * 设置目标库位
     *
     * @param goalSeatCode 目标库位
     */
    public void setGoalSeatCode(String goalSeatCode) {
        this.goalSeatCode = goalSeatCode;
    }

    /**
     * 获取rfid
     *
     * @return rfid - rfid
     */
    public Integer getRfid() {
        return rfid;
    }

    /**
     * 设置rfid
     *
     * @param rfid rfid
     */
    public void setRfid(Integer rfid) {
        this.rfid = rfid;
    }

    /**
     * 获取操作员
     *
     * @return operator - 操作员
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作员
     *
     * @param operator 操作员
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取操作时间
     *
     * @return operate_time - 操作时间
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * 设置操作时间
     *
     * @param operateTime 操作时间
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 获取明细表id
     *
     * @return detail_id - 明细表id
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置明细表id
     *
     * @param detailId 明细表id
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    /**
     * @return create_id
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * @param createId
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


    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
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