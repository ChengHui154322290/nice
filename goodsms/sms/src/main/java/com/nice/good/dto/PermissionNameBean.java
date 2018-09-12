package com.nice.good.dto;

import java.util.List;
import java.util.Map;

public class PermissionNameBean {

    // 用户名
    public String username;

    // 角色名称
    public List<Map<String, String>> roleNameList;

    // 货主名称
    public List<Map<String, String>> gooderNameList;

    // 场地名称
    public List<Map<String, String>> exhibitionList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Map<String, String>> getRoleNameList() {
        return roleNameList;
    }

    public void setRoleNameList(List<Map<String, String>> roleNameList) {
        this.roleNameList = roleNameList;
    }

    public List<Map<String, String>> getGooderNameList() {
        return gooderNameList;
    }

    public void setGooderNameList(List<Map<String, String>> gooderNameList) {
        this.gooderNameList = gooderNameList;
    }

    public List<Map<String, String>> getExhibitionList() {
        return exhibitionList;
    }

    public void setExhibitionList(List<Map<String, String>> exhibitionList) {
        this.exhibitionList = exhibitionList;
    }

    // 无参构造函数
    public PermissionNameBean() {
    }

    // 全参构造函数
    public PermissionNameBean(String username, List<Map<String, String>> roleNameList, List<Map<String, String>> gooderNameList,
                              List<Map<String, String>> exhibitionList) {
        this.username = username;
        this.roleNameList = roleNameList;
        this.gooderNameList = gooderNameList;
        this.exhibitionList = exhibitionList;
    }

    // roleNameList、 gooderNameList、 exhibitionList，3个参数构造函数
    public PermissionNameBean(List<Map<String, String>> roleNameList, List<Map<String, String>> gooderNameList, List<Map<String, String>> exhibitionList) {
        this.roleNameList = roleNameList;
        this.gooderNameList = gooderNameList;
        this.exhibitionList = exhibitionList;
    }

    // 重写 toString()方法
    @Override
    public String toString() {
        return "PermissionNameBean{" +
                "username='" + username + '\'' +
                ", roleNameList=" + roleNameList +
                ", gooderNameList=" + gooderNameList +
                ", exhibitionList=" + exhibitionList +
                '}';
    }
}
