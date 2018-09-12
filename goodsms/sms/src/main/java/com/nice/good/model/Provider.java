package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "e_provider")
public class Provider {
    /**
     * 主键id,后台生成
     */
    @Id
    @Column(name = "provider_id")
    private String providerId;

    /**
     * 序号id,自动增长
     */

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 供应商编码
     */
    @Column(name = "provider_code")
    private String providerCode;

    /**
     * 供应商名称
     */
    @Column(name = "provider_name")
    private String providerName;

    /**
     * 供应商等级
     */
    @Column(name = "provider_level")
    private String providerLevel;

    /**
     * 是否启用
     */
    private Integer status;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 性别
     */
    private String sex;

    /**
     * 电话
     */
    private String phone;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 手机
     */
    private String telephone;

    /**
     * 旺旺
     */
    private String wangwang;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 传真
     */
    private String fax;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 证件类型
     */
    @Column(name = "certificate_type")
    private String certificateType;

    /**
     * 证件号码
     */
    @Column(name = "certificate_num")
    private String certificateNum;

    /**
     * 开户银行
     */
    @Column(name = "open_bank")
    private String openBank;

    /**
     * 银行账户
     */
    @Column(name = "bank_account")
    private String bankAccount;

    /**
     * 助记码
     */
    @Column(name = "help_code")
    private String helpCode;

    /**
     * 外部供应商编号
     */
    @Column(name = "other_code")
    private String otherCode;

    /**
     * 网站地址
     */
    @Column(name = "web_url")
    private String webUrl;

    /**
     * 说明
     */
    private String statement;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     *
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
     * @return org_id
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @param providerId
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
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
     * 获取供应商编码
     *
     * @return provider_code - 供应商编码
     */
    public String getProviderCode() {
        return providerCode;
    }

    /**
     * 设置供应商编码
     *
     * @param providerCode 供应商编码
     */
    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    /**
     * 获取供应商名称
     *
     * @return provider_name - 供应商名称
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * 设置供应商名称
     *
     * @param providerName 供应商名称
     */
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    /**
     * 获取供应商等级
     *
     * @return provider_level - 供应商等级
     */
    public String getProviderLevel() {
        return providerLevel;
    }

    /**
     * 设置供应商等级
     *
     * @param providerLevel 供应商等级
     */
    public void setProviderLevel(String providerLevel) {
        this.providerLevel = providerLevel;
    }

    /**
     * 获取是否启用 0否,1是
     *
     * @return status - 是否启用 0否,1是
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否启用 0否,1是
     *
     * @param status 是否启用 0否,1是
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取联系人
     *
     * @return linkman - 联系人
     */
    public String getLinkman() {
        return linkman;
    }

    /**
     * 设置联系人
     *
     * @param linkman 联系人
     */
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取QQ号
     *
     * @return qq - QQ号
     */
    public String getQq() {
        return qq;
    }

    /**
     * 设置QQ号
     *
     * @param qq QQ号
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * 获取手机
     *
     * @return telephone - 手机
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置手机
     *
     * @param telephone 手机
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取旺旺
     *
     * @return wangwang - 旺旺
     */
    public String getWangwang() {
        return wangwang;
    }

    /**
     * 设置旺旺
     *
     * @param wangwang 旺旺
     */
    public void setWangwang(String wangwang) {
        this.wangwang = wangwang;
    }

    /**
     * 获取电子邮件
     *
     * @return email - 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮件
     *
     * @param email 电子邮件
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取传真
     *
     * @return fax - 传真
     */
    public String getFax() {
        return fax;
    }

    /**
     * 设置传真
     *
     * @param fax 传真
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 获取联系地址
     *
     * @return address - 联系地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置联系地址
     *
     * @param address 联系地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取证件类型
     *
     * @return certificate_type - 证件类型
     */
    public String getCertificateType() {
        return certificateType;
    }

    /**
     * 设置证件类型
     *
     * @param certificateType 证件类型
     */
    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    /**
     * 获取证件号码
     *
     * @return certificate_num - 证件号码
     */
    public String getCertificateNum() {
        return certificateNum;
    }

    /**
     * 设置证件号码
     *
     * @param certificateNum 证件号码
     */
    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    /**
     * 获取开户银行
     *
     * @return open_bank - 开户银行
     */
    public String getOpenBank() {
        return openBank;
    }

    /**
     * 设置开户银行
     *
     * @param openBank 开户银行
     */
    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    /**
     * 获取银行账户
     *
     * @return bank_account - 银行账户
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 设置银行账户
     *
     * @param bankAccount 银行账户
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 获取助记码
     *
     * @return help_code - 助记码
     */
    public String getHelpCode() {
        return helpCode;
    }

    /**
     * 设置助记码
     *
     * @param helpCode 助记码
     */
    public void setHelpCode(String helpCode) {
        this.helpCode = helpCode;
    }

    /**
     * 获取外部供应商编号
     *
     * @return other_code - 外部供应商编号
     */
    public String getOtherCode() {
        return otherCode;
    }

    /**
     * 设置外部供应商编号
     *
     * @param otherCode 外部供应商编号
     */
    public void setOtherCode(String otherCode) {
        this.otherCode = otherCode;
    }

    /**
     * 获取网站地址
     *
     * @return web_url - 网站地址
     */
    public String getWebUrl() {
        return webUrl;
    }

    /**
     * 设置网站地址
     *
     * @param webUrl 网站地址
     */
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
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
     * 获取修改时间
     *
     * @return createtime - 修改时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置修改时间
     *
     * @param createtime 修改时间
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