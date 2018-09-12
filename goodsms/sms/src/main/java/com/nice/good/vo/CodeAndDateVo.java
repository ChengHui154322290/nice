package com.nice.good.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CodeAndDateVo {

    /**
     * 单据号
     */
    private String workCode;


    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDate;


    /**
     * 操作时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifyDate;

    public String getWorkCode() {
        return workCode;
    }

    /**
     * 创建人
     */
    private String createId;

    /**
     * 修改人
     */
    private String modifyId;

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
