package com.nice.good.service;

import com.nice.good.model.SysRole;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/17.
 */
public interface SysRoleService extends Service<SysRole> {
    void sysRoleAdd(SysRole sysRole, String userId) throws Exception;

    void sysRoleUpdate(SysRole sysRole, String userId);


    /**
     * 通过 name 查询 a_sys_role 表中的 role_id 值   -- 2018/05/21  17:35  rk
     *
     * @param name
     * @return
     */
    String findRoleIdByName(String name);


    /**
     * 通过 role_id 查询 a_sys_role 表中的 name 值    -- 2018/05/21 10:22 rk
     *
     * @param role_id
     * @return
     */
    String findNameByRoleId(String role_id);


    /**
     * 通过 role_id 查询 a_user_role 表中是否有对应的 username  2018/05/16  16:18  rk
     *
     * @param role_id
     * @return
     */
    String findUsernameByRoleId(String role_id);


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
     * 检查role_id、role_name是否重复
     *
     * @param role_id
     * @param
     * @return
     */
    String checkRoleIDOrRoleName(String role_id);
    // String checkRoleIDOrRoleName(String role_id, String name);

    /**
     * 通过roleId、name 模糊匹配， 查询角色。
     *
     * @param role_id
     * @param name
     * @return
     */
    List<SysRole> findRoleByIdORName(String role_id, String name);

    /**
     * 通过role_id删除 a_sys_role 中的角色
     *
     * @param roleId
     */
    void deleteByRoleId(String roleId);
}
