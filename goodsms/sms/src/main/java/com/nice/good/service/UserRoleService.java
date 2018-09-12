package com.nice.good.service;

import com.nice.good.model.UserRole;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/20.
 */
public interface UserRoleService extends Service<UserRole> {
    void userRoleAdd(UserRole userRole, String userId) throws Exception;

    void userRoleUpdate(UserRole userRole, String userId);

    /**
     * 通过 username 删除用户数据
     *
     * @param username
     */
    void deleteUserByUsername(String username);

    /**
     * 通过 roleId 删除 a_user_role 表中的数据
     *
     * @param roleId
     */
    void deleteUserByRoleId(String roleId);

    /**
     * 通过 username 查询 name 角色名 -- List<String>集合  -- 2018/05/21  13:42
     *
     * @param username
     * @return
     */
    List<String> findRoleNamesByUsername(String username);


    /**
     * 通过 username 查询 roleId -- List<String>集合
     *
     * @param username
     * @return
     */
    List<String> findRoleIdsByUsername(String username);


    /**
     * 传递 username、 roleId、 userId 数据，对 a_user_role 表进行数据插入 。
     *
     * @param username
     * @param roleId
     * @param userId
     * @throws Exception
     */
    void insertUserRoleByUsernameRoleId(String username, String roleId, String userId) throws Exception;
}
