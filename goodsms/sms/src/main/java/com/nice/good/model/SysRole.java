package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "a_sys_role")
public class SysRole {
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
     * 角色编号
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 说明
     */
    private String remark;

    /**
     * 创建人
     */
    private String createId;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    //@DateTimeFormat (pattern ="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 修改人
     */
    private String modifyId;

    /**
     * 修改时间
     */
    @Column(name = "modify_Date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    //@DateTimeFormat (pattern ="yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

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
     * 获取角色编号
     *
     * @return role_id - 角色编号
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 设置角色编号
     *
     * @param roleId 角色编号
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
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
     * @return creater - 创建人
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
     * 获取修改时间
     *
     * @return create_date - 修改时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置修改时间
     *
     * @param createDate 修改时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改人
     *
     * @return modifyId - 修改人
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

    // 无参构造函数
    public SysRole() {
    }

    // 全参构造函数
    public SysRole(String keyId, String roleId, String name,
                   String remark, String createId, Date createDate,
                   String modifyId, Date modifyDate) {
        this.keyId = keyId;
        this.roleId = roleId;
        this.name = name;
        this.remark = remark;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyId = modifyId;
        this.modifyDate = modifyDate;
    }

    // 实现role_id、role_name、remark字段 -- 有参构造函数
    public SysRole(String roleId, String name, String remark) {
        this.roleId = roleId;
        this.name = name;
        this.remark = remark;
    }

    // 实现 keyId、role_id、 role_name、 remark
    public SysRole(String keyId, Integer id, String roleId, String name, String remark, String createId, Date createDate) {
        this.keyId = keyId;
        this.id = id;
        this.roleId = roleId;
        this.name = name;
        this.remark = remark;
        this.createId = createId;
        this.createDate = createDate;
    }

    // 实现toString()方法
    @Override
    public String toString() {
        return "SysRole{" +
                "keyId='" + keyId + '\'' +
                ", id=" + id +
                ", roleId='" + roleId + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", createId='" + createId + '\'' +
                ", createDate=" + createDate +
                ", modifyId='" + modifyId + '\'' +
                ", modifyDate=" + modifyDate +
                '}';
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