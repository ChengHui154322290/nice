package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.SysPermission;

import java.util.List;

public interface SysPermissionMapper extends Mapper<SysPermission> {

    /**
     * 根据 username 返回a_sys_Permission所有信息
     */
    List<SysPermission> listPermissions(String username);

    /**
     * 通过permissionId 获取 permission
     * @param permissionId
     * @return
     */
    SysPermission ListPermissionByPermissionId(Integer permissionId);



}