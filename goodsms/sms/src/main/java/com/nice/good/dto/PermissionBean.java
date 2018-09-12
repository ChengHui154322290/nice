package com.nice.good.dto;

import java.util.List;
import java.util.Map;

public class PermissionBean {

    // 用户名
    public String username;

    // 角色id
    public List<Map<String, String>> roleIdList;

    // 货主编码
    public List<Map<String, String>> gooderCodeList;

    // 场地编码
    public List<Map<String, String>> placeNumberList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Map<String, String>> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Map<String, String>> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<Map<String, String>> getGooderCodeList() {
        return gooderCodeList;
    }

    public void setGooderCodeList(List<Map<String, String>> gooderCodeList) {
        this.gooderCodeList = gooderCodeList;
    }

    public List<Map<String, String>> getPlaceNumberList() {
        return placeNumberList;
    }

    public void setPlaceNumberList(List<Map<String, String>> placeNumberList) {
        this.placeNumberList = placeNumberList;
    }

    // 无参构造函数
    public PermissionBean() {
    }

    // 全参构造函数
    public PermissionBean(String username, List<Map<String, String>> roleIdList, List<Map<String, String>> gooderCodeList,
                          List<Map<String, String>> placeNumberList) {
        this.username = username;
        this.roleIdList = roleIdList;
        this.gooderCodeList = gooderCodeList;
        this.placeNumberList = placeNumberList;
    }

    // roleIdList、 gooderCodeList、 placeNumberList，3个参数构造函数
    public PermissionBean(List<Map<String, String>> roleIdList, List<Map<String, String>> gooderCodeList, List<Map<String, String>> placeNumberList) {
        this.roleIdList = roleIdList;
        this.gooderCodeList = gooderCodeList;
        this.placeNumberList = placeNumberList;
    }

    // 重写 toString() 方法
    @Override
    public String toString() {
        return "PermissionBean{" +
                "username='" + username + '\'' +
                ", roleIdList=" + roleIdList +
                ", gooderCodeList=" + gooderCodeList +
                ", placeNumberList=" + placeNumberList +
                '}';
    }
}
