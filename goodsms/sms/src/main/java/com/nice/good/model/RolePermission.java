package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "a_role_permission")
public class RolePermission {
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
     * 权限id
     */
    @Column(name = "permission_id")
    private Integer permissionId;

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
     * 修改人id
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
     * 获取权限id
     *
     * @return permission_id - 权限id
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * 设置权限id
     *
     * @param permissionId 权限id
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
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

    // 无参构造函数
    public RolePermission() {
    }

    // 有参构造函数  role_id、permission_id
    public RolePermission(String roleId, Integer permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    // 全参构造函数
    public RolePermission(String keyId, String roleId, Integer permissionId,
                          String createId, Date createDate, String modifyId, Date modifyDate, String remark) {
        this.keyId = keyId;
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyId = modifyId;
        this.modifyDate = modifyDate;
        this.remark = remark;
    }

    // 重写 toString()方法
    @Override
    public String toString() {
        return "RolePermission{" +
                "keyId='" + keyId + '\'' +
                ", id=" + id +
                ", roleId='" + roleId + '\'' +
                ", permissionId=" + permissionId +
                ", createId='" + createId + '\'' +
                ", createDate=" + createDate +
                ", modifyId='" + modifyId + '\'' +
                ", modifyDate=" + modifyDate +
                ", remark='" + remark + '\'' +
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