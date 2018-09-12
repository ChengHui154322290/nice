package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "s_operate_log")
public class OperateLog {
    /**
     * 序号id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 操作员
     */
    @Column(name = "modify_id")
    private String modifyId;

    /**
     * 日志类型
     */
    @Column(name = "log_type")
    private String logType;

    /**
     * 相关单据
     */
    @Column(name = "relative_invoice")
    private String relativeInvoice;

    /**
     * 内容
     */
    private String content;

    /**
     * 操作时间
     */
    @Column(name = "modify_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;


    /**
     * 操作开始时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTimeStart;


    /**
     * 操作结束时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTimeEnd;

    public Date getModifyTimeStart() {
        return modifyTimeStart;
    }

    public void setModifyTimeStart(Date modifyTimeStart) {
        this.modifyTimeStart = modifyTimeStart;
    }

    public Date getModifyTimeEnd() {
        return modifyTimeEnd;
    }

    public void setModifyTimeEnd(Date modifyTimeEnd) {
        this.modifyTimeEnd = modifyTimeEnd;
    }

    /**

     * ip记录
     */
    @Column(name = "ip_record")
    private String ipRecord;

    /**
     * 获取序号id
     *
     * @return id - 序号id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置序号id
     *
     * @param id 序号id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取操作员
     *
     * @return modify_id - 操作员
     */
    public String getModifyId() {
        return modifyId;
    }

    /**
     * 设置操作员
     *
     * @param modifyId 操作员
     */
    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    /**
     * 获取日志类型
     *
     * @return log_type - 日志类型
     */
    public String getLogType() {
        return logType;
    }

    /**
     * 设置日志类型
     *
     * @param logType 日志类型
     */
    public void setLogType(String logType) {
        this.logType = logType;
    }

    /**
     * 获取相关单据
     *
     * @return relative_invoice - 相关单据
     */
    public String getRelativeInvoice() {
        return relativeInvoice;
    }

    /**
     * 设置相关单据
     *
     * @param relativeInvoice 相关单据
     */
    public void setRelativeInvoice(String relativeInvoice) {
        this.relativeInvoice = relativeInvoice;
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
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
     * 获取ip记录
     *
     * @return ip_record - ip记录
     */
    public String getIpRecord() {
        return ipRecord;
    }

    /**
     * 设置ip记录
     *
     * @param ipRecord ip记录
     */
    public void setIpRecord(String ipRecord) {
        this.ipRecord = ipRecord;
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