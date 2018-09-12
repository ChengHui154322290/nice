package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends Mapper<SysUser> {

    /**
     *  通过 username 查询 a_sys_user 表中是否含有 username , 若无对应的 username ，则给出相应的提示。  2018/05/19 11:07 rk
     * @param username
     * @return
     */
    String checkUsername(String username);


    /**
     * 检查SysUser.username是否重复
     * @param username
     * @return
     */
    String checkIdByUsername(String username);

    /**
     * 实现用户登录功能
     * @param username
     * @param password
     * @return
     */
    SysUser login(@Param(value = "username") String username,@Param(value = "password") String password);

    /**
     * 根据username 查询role_id  使用a_user_role
     * @param username
     * @return
     */
    List<String> queryRoleId(String username);

    /**
     *  通过 username 删除用户数据
     * @param username
     */
    void deleteUserByUsername(@Param(value = "username") String username);

    /**
     *  模糊匹配， 查询 a_sys_user
     * @param username
     * @param name
     * @param userType
     * @param orgCode
     * @param isOk
     * @return
     */
    List<SysUser> selectByFiveParameter(@Param(value = "username") String username,
                                        @Param(value = "name") String name,
                                        @Param(value = "userType") String userType,
                                        @Param(value = "orgCode") String orgCode,
                                        @Param(value = "isOk") String isOk);


    String selecUserTypeByUserName(String username);

}