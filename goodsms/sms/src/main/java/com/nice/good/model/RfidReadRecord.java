package com.nice.good.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "f_rfid_read_record")
public class RfidReadRecord {
    /**
     * 数据行号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * rfid编号
     */
    @Column(name = "rfid_code")
    private String rfidCode;

    /**
     * 天线编号
     */
    @Column(name = "ant_num")
    private String antNum;

    /**
     * rfid标签编号
     */
    @Column(name = "rfid_label")
    private String rfidLabel;

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
     * 获取rfid编号
     *
     * @return rfid_code - rfid编号
     */
    public String getRfidCode() {
        return rfidCode;
    }

    /**
     * 设置rfid编号
     *
     * @param rfidCode rfid编号
     */
    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    /**
     * 获取天线编号
     *
     * @return ant_num - 天线编号
     */
    public String getAntNum() {
        return antNum;
    }

    /**
     * 设置天线编号
     *
     * @param antNum 天线编号
     */
    public void setAntNum(String antNum) {
        this.antNum = antNum;
    }

    /**
     * 获取rfid标签编号
     *
     * @return rfid_label - rfid标签编号
     */
    public String getRfidLabel() {
        return rfidLabel;
    }

    /**
     * 设置rfid标签编号
     *
     * @param rfidLabel rfid标签编号
     */
    public void setRfidLabel(String rfidLabel) {
        this.rfidLabel = rfidLabel;
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