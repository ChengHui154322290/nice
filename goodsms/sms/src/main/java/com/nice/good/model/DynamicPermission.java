package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "a_dynamic_permission")
public class DynamicPermission {
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
    @Column(name = "role_name")
    private String roleName;

    /**
     * 一级子菜单id
     */
    @Column(name = "first_permission")
    private Integer firstPermission;

    /**
     * 二级子菜单id
     */
    @Column(name = "second_permission")
    private Integer secondPermission;

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
     * @return role_name - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取一级子菜单id
     *
     * @return first_permission - 一级子菜单id
     */
    public Integer getFirstPermission() {
        return firstPermission;
    }

    /**
     * 设置一级子菜单id
     *
     * @param firstPermission 一级子菜单id
     */
    public void setFirstPermission(Integer firstPermission) {
        this.firstPermission = firstPermission;
    }

    /**
     * 获取二级子菜单id
     *
     * @return second_permission - 二级子菜单id
     */
    public Integer getSecondPermission() {
        return secondPermission;
    }

    /**
     * 设置二级子菜单id
     *
     * @param secondPermission 二级子菜单id
     */
    public void setSecondPermission(Integer secondPermission) {
        this.secondPermission = secondPermission;
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

    //无参构造函数
    public DynamicPermission() {
    }

    //全参构造函数
    public DynamicPermission(String keyId, String roleId, String roleName, Integer firstPermission,
                             Integer secondPermission, String remark, String createId, Date createDate,
                             String modifyId, Date modifyDate) {
        this.keyId = keyId;
        this.roleId = roleId;
        this.roleName = roleName;
        this.firstPermission = firstPermission;
        this.secondPermission = secondPermission;
        this.remark = remark;
        this.createId = createId;
        this.createDate = createDate;
        this.modifyId = modifyId;
        this.modifyDate = modifyDate;
    }

    // role_id、role_name、first_permission、second_permission、 remark  有参构造函数
    public DynamicPermission(String roleId, String roleName, Integer firstPermission,
                             Integer secondPermission, String remark) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.firstPermission = firstPermission;
        this.secondPermission = secondPermission;
        this.remark = remark;
    }

    // role_id, role_name, firstPermission, second_permission, remark, createId, createDate
    public DynamicPermission(String roleId, String roleName, Integer firstPermission,
                             Integer secondPermission, String remark, String createId, Date createDate) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.firstPermission = firstPermission;
        this.secondPermission = secondPermission;
        this.remark = remark;
        this.createId = createId;
        this.createDate = createDate;
    }

    //toString()方法
    @Override
    public String toString() {
        return "DynamicPermission{" +
                "keyId='" + keyId + '\'' +
                ", id=" + id +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", firstPermission=" + firstPermission +
                ", secondPermission=" + secondPermission +
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