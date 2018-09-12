package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "c_carrier")
public class Carrier {
    @Id
    @Column(name = "carrier_id")
    private String carrierId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 承运商名称
     */
    @Column(name = "carrier_name")
    private String carrierName;

    /**
     * 承运商编码
     */
    @Column(name = "carrier_code")
    private String carrierCode;

    /**
     * 单号规则
     */
    @Column(name = "code_rule")
    private String codeRule;

    /**
     * 说明
     */
    private String statement;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 修改时时间
     */

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifytime;

    /**
     * @return carrier_id
     */
    public String getCarrierId() {
        return carrierId;
    }

    /**
     * @param carrierId
     */
    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
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
     * 获取承运商名称
     *
     * @return carrier_name - 承运商名称
     */
    public String getCarrierName() {
        return carrierName;
    }

    /**
     * 设置承运商名称
     *
     * @param carrierName 承运商名称
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    /**
     * 获取承运商编码
     *
     * @return carrier_code - 承运商编码
     */
    public String getCarrierCode() {
        return carrierCode;
    }

    /**
     * 设置承运商编码
     *
     * @param carrierCode 承运商编码
     */
    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    /**
     * 获取单号规则
     *
     * @return code_rule - 单号规则
     */
    public String getCodeRule() {
        return codeRule;
    }

    /**
     * 设置单号规则
     *
     * @param codeRule 单号规则
     */
    public void setCodeRule(String codeRule) {
        this.codeRule = codeRule;
    }

    /**
     * 获取说明
     *
     * @return statement - 说明
     */
    public String getStatement() {
        return statement;
    }

    /**
     * 设置说明
     *
     * @param statement 说明
     */
    public void setStatement(String statement) {
        this.statement = statement;
    }

    /**
     * 获取创建人
     *
     * @return creater - 创建人
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 设置创建人
     *
     * @param creater 创建人
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * 获取修改时时间
     *
     * @return createtime - 修改时时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置修改时时间
     *
     * @param createtime 修改时时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取修改人
     *
     * @return modifier - 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人
     *
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取修改时间
     *
     * @return modifytime - 修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * 设置修改时间
     *
     * @param modifytime 修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
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