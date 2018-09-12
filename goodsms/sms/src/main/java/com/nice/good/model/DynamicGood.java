package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "s_dynamic_good")
public class DynamicGood {
    /**
     * 主键id
     */
    @Id
    @Column(name = "key_id")
    private String keyId;

    /**
     * 数据行号
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;

    /**
     * 交易编码
     */
    @Column(name = "trade_code")
    private String tradeCode;

    /**
     * 货品编码
     */
    @Column(name = "good_code")
    private String goodCode;

    /**
     * 单据号
     */
    @Column(name = "bill_code")
    private String billCode;

    /**
     * 任务编号
     */
    @Column(name = "task_code")
    private String taskCode;

    /**
     * 交易类型
     */
    @Column(name = "trade_type")
    private String tradeType;

    /**
     * 来源库位
     */
    @Column(name = "from_seat")
    private String fromSeat;

    /**
     * 目标库位
     */
    @Column(name = "to_seat")
    private String toSeat;

    /**
     * 数量
     */
    private Integer size;

    /**
     * rfid值
     */
    private Integer rfid;

    /**
     * 创建人id
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
     * 操作人id
     */
    @Column(name = "modify_id")
    private String modifyId;

    /**
     * 操作时间
     */
    @Column(name = "modify_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifyDate;


    /**
     * 操作起始时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyDateStart;


    /**
     * 操作结束时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyDateEnd;

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

    /**
     * 说明
     */
    private String remark;

    /**
     * 获取主键id
     *
     * @return key_id - 主键id
     */
    public String getKeyId() {
        return keyId;
    }

    /**
     * 设置主键id
     *
     * @param keyId 主键id
     */
    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    /**
     * 获取数据行号
     *
     * @return id - 数据行号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置数据行号
     *
     * @param id 数据行号
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 获取交易编码
     *
     * @return trade_code - 交易编码
     */
    public String getTradeCode() {
        return tradeCode;
    }

    /**
     * 设置交易编码
     *
     * @param tradeCode 交易编码
     */
    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
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
     * 获取单据号
     *
     * @return bill_code - 单据号
     */
    public String getBillCode() {
        return billCode;
    }

    /**
     * 设置单据号
     *
     * @param billCode 单据号
     */
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    /**
     * 获取任务编号
     *
     * @return task_code - 任务编号
     */
    public String getTaskCode() {
        return taskCode;
    }

    /**
     * 设置任务编号
     *
     * @param taskCode 任务编号
     */
    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    /**
     * 获取交易类型
     *
     * @return trade_type - 交易类型
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * 设置交易类型
     *
     * @param tradeType 交易类型
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 获取来源库位
     *
     * @return from_seat - 来源库位
     */
    public String getFromSeat() {
        return fromSeat;
    }

    /**
     * 设置来源库位
     *
     * @param fromSeat 来源库位
     */
    public void setFromSeat(String fromSeat) {
        this.fromSeat = fromSeat;
    }

    /**
     * 获取目标库位
     *
     * @return to_seat - 目标库位
     */
    public String getToSeat() {
        return toSeat;
    }

    /**
     * 设置目标库位
     *
     * @param toSeat 目标库位
     */
    public void setToSeat(String toSeat) {
        this.toSeat = toSeat;
    }

    /**
     * 获取数量
     *
     * @return size - 数量
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置数量
     *
     * @param size 数量
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取rfid值
     *
     * @return rfid - rfid值
     */
    public Integer getRfid() {
        return rfid;
    }

    /**
     * 设置rfid值
     *
     * @param rfid rfid值
     */
    public void setRfid(Integer rfid) {
        this.rfid = rfid;
    }

    /**
     * 获取创建人id
     *
     * @return create_id - 创建人id
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * 设置创建人id
     *
     * @param createId 创建人id
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
     * 获取修改人id
     *
     * @return modify_id - 修改人id
     */
    public String getModifyId() {
        return modifyId;
    }

    /**
     * 设置修改人id
     *
     * @param modifyId 修改人id
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