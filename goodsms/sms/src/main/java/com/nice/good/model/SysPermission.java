package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "a_sys_permission")
public class SysPermission {
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
     * 权限id
     */
    @Column(name = "permission_id")
    private Integer permissionId;

    /**
     * 权限父id
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 权限等级
     */
    private Integer type;

    /**
     * 权限名
     */
    private String name;

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
     * 权限路径
     */
    @Column(name = "path")
    private String  path;


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
     * 获取权限父id
     *
     * @return parent_id - 权限父id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置权限父id
     *
     * @param parentId 权限父id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取权限等级
     *
     * @return type - 权限等级
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置权限等级
     *
     * @param type 权限等级
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取权限名
     *
     * @return name - 权限名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置权限名
     *
     * @param name 权限名
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取权限路径
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置权限路径
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    // 无参构造函数
    public SysPermission() {
    }

    // 全参构造函数
    public SysPermission(String keyId, Integer permissionId, Integer parentId, Integer type,
                         String name, String modifyId, Date modifyDate, String createId,
                         Date createDate, String path) {
        this.keyId = keyId;
        this.permissionId = permissionId;
        this.parentId = parentId;
        this.type = type;
        this.name = name;
        this.modifyId = modifyId;
        this.modifyDate = modifyDate;
        this.createId = createId;
        this.createDate = createDate;
        this.path = path;
    }

    // 重写 toString() 方法
    @Override
    public String toString() {
        return "SysPermission{" +
                "keyId='" + keyId + '\'' +
                ", id=" + id +
                ", permissionId=" + permissionId +
                ", parentId=" + parentId +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", modifyId='" + modifyId + '\'' +
                ", modifyDate=" + modifyDate +
                ", createId='" + createId + '\'' +
                ", createDate=" + createDate +
                ", path='" + path + '\'' +
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