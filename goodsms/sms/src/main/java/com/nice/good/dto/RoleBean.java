package com.nice.good.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class RoleBean {

    /**
     * 主键id
     */
    private String keyId;

    /**
     * 数据行号
     */
    private Integer id;

    /**
     * 角色编号
     */
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDate;

    /**
     * 权限 -- 封装有 一级子菜单、 二级子菜单
     */
    private Map<String, List<String>> permission;


    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Map<String, List<String>> getPermission() {
        return permission;
    }

    public void setPermission(Map<String, List<String>> permission) {
        this.permission = permission;
    }

    // 无参构造函数
    public RoleBean() {
    }

    // 全参构造函数
    public RoleBean(String keyId, Integer id, String roleId, String name, String remark, String createId,
                    Date createDate, Map<String, List<String>> permission) {
        this.keyId = keyId;
        this.id = id;
        this.roleId = roleId;
        this.name = name;
        this.remark = remark;
        this.createId = createId;
        this.createDate = createDate;
        this.permission = permission;
    }

    // 重写 toString() 方法
    @Override
    public String toString() {
        return "RoleBean{" +
                "keyId='" + keyId + '\'' +
                ", id=" + id +
                ", roleId='" + roleId + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", createId='" + createId + '\'' +
                ", createDate=" + createDate +
                ", permission=" + permission +
                '}';
    }


}
