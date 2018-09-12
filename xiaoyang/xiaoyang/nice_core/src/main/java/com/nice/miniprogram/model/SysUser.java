package com.nice.miniprogram.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "a_sys_user")
public class SysUser {
    /**
     * 用户ID，主键id
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 数据行号
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

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
     * 说明
     */
    private String remark;

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
     * 用户类型--模糊类型
     */
    @Column(name = "user_type")
    private String userType;

    /**
     * 组织机构编码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 手机号
     */
    @Column(name = "mobile_phone")
    private String mobilePhone;

    /**
     * 电话号
     */
    private String phone;

    /**
     * 性别，0是男，1是女，2是未知
     */
    private String sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * QQ号
     */
    @Column(name = "qq_number")
    private Integer qqNumber;

    /**
     * 是否启用
     */
    @Column(name = "is_ok")
    private String isOk;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 企业id
     */
    @Column(name = "company_id")
    private String companyId;

    /**
     * 获取用户ID，主键id
     *
     * @return user_id - 用户ID，主键id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID，主键id
     *
     * @param userId 用户ID，主键id
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
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
     * 获取用户类型--模糊类型
     *
     * @return user_type - 用户类型--模糊类型
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 设置用户类型--模糊类型
     *
     * @param userType 用户类型--模糊类型
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 获取组织机构编码
     *
     * @return org_code - 组织机构编码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置组织机构编码
     *
     * @param orgCode 组织机构编码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取手机号
     *
     * @return mobile_phone - 手机号
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * 设置手机号
     *
     * @param mobilePhone 手机号
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * 获取电话号
     *
     * @return phone - 电话号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话号
     *
     * @param phone 电话号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取性别，0是男，1是女，2是未知
     *
     * @return sex - 性别，0是男，1是女，2是未知
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别，0是男，1是女，2是未知
     *
     * @param sex 性别，0是男，1是女，2是未知
     */
    public void setSex(String sex) {
        this.sex = sex;
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
     * 获取QQ号
     *
     * @return qq_number - QQ号
     */
    public Integer getQqNumber() {
        return qqNumber;
    }

    /**
     * 设置QQ号
     *
     * @param qqNumber QQ号
     */
    public void setQqNumber(Integer qqNumber) {
        this.qqNumber = qqNumber;
    }

    /**
     * 获取是否启用
     *
     * @return is_ok - 是否启用
     */
    public String getIsOk() {
        return isOk;
    }

    /**
     * 设置是否启用
     *
     * @param isOk 是否启用
     */
    public void setIsOk(String isOk) {
        this.isOk = isOk;
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
     * 获取企业id
     *
     * @return company_id - 企业id
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 设置企业id
     *
     * @param companyId 企业id
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}