package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "a_sys_user")
public class SysUser implements Serializable{

    /**
     * 用户ID，主键id，调用后台Util包中类自动生成
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 数据行号，自动增长。
     */
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;


    /**
     * 姓名
     */
    @Column(name = "name")
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDate;

    /**
     * 用户类型
     */
    @Column(name = "user_type")
    private String userType;

    /**
     * 组织机构
     */
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
     * 性别
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
     * 获取用户Id、主键Id
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取数据行号
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置数据行号
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户名称
     *
     * @return name - 用户名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户名称
     *
     * @param name 用户名称
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
     * 获取修改人Id
     * @return  modifyId - 修改人
     */
    public String getModifyId() {
        return modifyId;
    }

    /**
     * 设置修改人Id
     * @param modifyId - 修改人
     */
    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 获取创建人
     * @return createId - 创建人
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * 设置创建人
     * @param createId - 创建人
     */
    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
     * 获取用户类型
     *
     * @return user_type - 用户类型
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 设置用户类型
     *
     * @param userType 用户类型
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 获取组织机构
     *
     * @return orgCode - 组织机构
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置组织机构
     *
     * @param orgCode 组织机构
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

    //无参构造函数

    public SysUser() {
    }

    //全参构造函数
    public SysUser(String userId, String username, String name, String password,
                   String modifyId, Date modifyDate, String remark, String createId,
                   Date createDate, String userType, String orgCode, String mobilePhone,
                   String phone, String sex, String email, Integer qqNumber,
                   String isOk, String address) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.password = password;
        this.modifyId = modifyId;
        this.modifyDate = modifyDate;
        this.remark = remark;
        this.createId = createId;
        this.createDate = createDate;
        this.userType = userType;
        this.orgCode = orgCode;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.sex = sex;
        this.email = email;
        this.qqNumber = qqNumber;
        this.isOk = isOk;
        this.address = address;
    }


    //有参构造函数：username、userType、name
    public SysUser(String userId, String username, String name, String userType) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.userType = userType;
    }

    // 有参构造函数 newUserId, id, username, name, password, remark, userType, orgCode, mobilePhone, phone, sex, email, qqNumber, isOk, address, createId, createDate
    public SysUser(String userId, Integer id, String username, String name, String password, String remark,
                   String userType, String orgCode, String mobilePhone, String phone, String sex, String email,
                   Integer qqNumber, String isOk, String address, String createId, Date createDate) {
        this.userId = userId;
        this.id = id;
        this.username = username;  // 必须写的字段
        this.name = name;
        this.password = password;
        this.remark = remark;
        this.userType = userType;
        this.orgCode = orgCode;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.sex = sex;
        this.email = email;
        this.qqNumber = qqNumber;
        this.isOk = isOk;
        this.address = address;
        this.createId = createId;
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


    /**
     * 冗余字段，0表示新增，1表示修改
     */
    @Transient
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }


}