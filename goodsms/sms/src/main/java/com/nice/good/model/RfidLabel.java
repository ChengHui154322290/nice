package com.nice.good.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "r_rfid_label")
public class RfidLabel {
    /**
     * rfid编码
     */
    @Id
    @Column(name = "label_code")
    private String labelCode;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
     * 来源库位编码
     */
    @Column(name = "seat_code")
    private String seatCode;


    /**
     * rfid标签状态,0未使用,1使用中
     */
    private Integer status;

    /**
     * 创建人
     */
    @Column(name = "create_id")
    private String createId;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
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
    private Date modifyDate;

    /**
     * 获取rfid编码
     *
     * @return label_code - rfid编码
     */
    public String getLabelCode() {
        return labelCode;
    }

    /**
     * 设置rfid编码
     *
     * @param labelCode rfid编码
     */
    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

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

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    /**
     * 获取rfid标签状态,0未使用,1使用中
     *
     * @return status - rfid标签状态,0未使用,1使用中
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置rfid标签状态,0未使用,1使用中
     *
     * @param status rfid标签状态,0未使用,1使用中
     */
    public void setStatus(Integer status) {
        this.status = status;
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