package com.nice.good.model;

import java.util.List;

/**
 *  本文件主要用于封装 系统设置-- 登录账号管理 -- 分配权限，对货主权限、场地权限、角色权限3种不同类型的权限进行封装
 */
public class SysUserPermission {

    /**
     * 用户名
     */
    private String username;

    /**
     *  角色id -- List集合
     */
    private List<String> roleId;

    /**
     * 货主编码 -- List集合
     */
    private List<String> gooderCode;

    /**
     * 场地编号 -- List集合
     */
    private List<String> placeNumber;

    /**
     * 获取 用户名
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置 用户名
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取 角色id roleId -- List<Integer> 集合
     * @return
     */
    public List<String> getRoleId() {
        return roleId;
    }

    /**
     * 设置 角色id roleId -- List<Integer> 集合
     * @param roleId
     */
    public void setRoleId(List<String> roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取 货主编码 gooderCode -- List<String> 集合
     * @return
     */
    public List<String> getGooderCode() {
        return gooderCode;
    }

    /**
     * 设置 货主编码 gooderCode -- List<String> 集合
     * @param gooderCode
     */
    public void setGooderCode(List<String> gooderCode) {
        this.gooderCode = gooderCode;
    }

    /**
     * 获取 场地编号 placeNumber -- List<String> 集合
     * @return
     */
    public List<String> getPlaceNumber() {
        return placeNumber;
    }

    /**
     * 设置 场地编号 placeNumber -- List<String> 集合
     * @param placeNumber
     */
    public void setPlaceNumber(List<String> placeNumber) {
        this.placeNumber = placeNumber;
    }

    /**
     * 无参构造函数
     */
    public SysUserPermission() {
    }

    /**
     * 全参构造函数
     * @param username
     * @param roleId
     * @param gooderCode
     * @param placeNumber
     */
    public SysUserPermission(String username, List<String> roleId, List<String> gooderCode, List<String> placeNumber) {
        this.username = username;
        this.roleId = roleId;
        this.gooderCode = gooderCode;
        this.placeNumber = placeNumber;
    }

    @Override
    public String toString() {
        return "SysUserPermission{" +
                "username='" + username + '\'' +
                ", roleId=" + roleId +
                ", gooderCode=" + gooderCode +
                ", placeNumber=" + placeNumber +
                '}';
    }

}
