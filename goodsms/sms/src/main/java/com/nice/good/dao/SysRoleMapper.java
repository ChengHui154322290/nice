package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole> {


    /**
     * 通过 name 查询 a_sys_role 表中的 role_id 值   -- 2018/05/21  17:35  rk
     * @param name
     * @return
     */
    String findRoleIdByName(@Param(value = "name") String name);


    /**
     * 通过 role_id 查询 a_sys_role 表中的 name 值    -- 2018/05/21 10:22 rk
     *
     * @param role_id
     * @return
     */
    String findNameByRoleId(@Param(value = "role_id") String role_id);


    /**
     * 通过 role_id 查询 a_user_role 表中是否有对应的 username  2018/05/16  16:18  rk
     *
     * @param role_id
     * @return
     */
    String findUsernameByRoleId(@Param(value = "role_id") String role_id);


    /**
     * 查询 a_sys_role 表中的所有 name --角色名称
     *
     * @return
     */
    List<String> findRoleName();

    /**
     * 查询 a_sys_role 表中的所有 role_id --角色id
     *
     * @return
     */
    List<String> findRoleId();

    /**
     * 检查用户id、用户名
     *
     * @param role_id
     * @param
     * @return
     */
    // String checkRoleIDOrRoleName(@Param(value = "role_id") String role_id, @Param(value = "name") String name);
    String checkRoleIDOrRoleName(@Param(value = "role_id") String role_id);

    /**
     * 模糊查询 SysRole 角色
     *
     * @param role_id
     * @param name
     * @return
     */
    List<SysRole> findRoleByIdORName(@Param(value = "role_id") String role_id, @Param(value = "name") String name);

    /**
     * 通过roleId 删除 a_sys_role 中的数据
     *
     * @param role_id
     */
    void deleteByRoleId(@Param(value = "role_id") String role_id);

}