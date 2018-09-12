package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper extends Mapper<UserRole> {

    /**
     * 通过 username 查询 name 角色名 -- List<String>集合  -- 2018/05/21  13:42
     *
     * @param username
     * @return
     */
    List<String> findRoleNamesByUsername(String username);

    /**
     * 通过 username 删除 a_user_role 数据
     * @param username
     */
    void deleteUserByUsername(String username);

    /**
     *  通过 username 查询 roleId -- List<Integer>集合
     * @param username
     * @return
     */
    List<String> findRoleIdsByUsername(String username);

    /**
     *  通过 roleId 删除 a_user_role 表中的数据
     * @param role_id
     */
    void deleteUserByRoleId(@Param(value = "role_id") String role_id);

    UserRole selectByRoleId(@Param(value = "roleId") String roleId);

}