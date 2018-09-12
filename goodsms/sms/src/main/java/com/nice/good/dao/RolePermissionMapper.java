package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.RolePermission;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionMapper extends Mapper<RolePermission> {

    /**
     * 通过role_id 删除 a_role_permission
     * @param role_id
     */
    void deleteByRoleId(@Param(value = "role_id") String role_id);

}