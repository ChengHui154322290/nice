package com.nice.good.service;
import com.nice.good.model.SysUser;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/12.
 */
public interface SysUserService extends Service<SysUser> {
      void sysUserAdd(SysUser sysUser,String userId) throws Exception;
      void sysUserUpdate(SysUser sysUser,String userId);
      String checkSysUsername(String sysUsername);

      /**
       *  通过 username 查询 a_sys_user 表中是否含有 username , 若无对应的 username ，则给出相应的提示。  2018/05/19 11:07 rk
       * @param username
       * @return
       */
      String checkUsername(String username);

      /**
       * 实现登录功能
       * @param username
       * @param password
       * @return
       */
      SysUser sysUserLogin(String username, String password);

      /**
       * 根据username查询role_id 在a_user_role表中查询
       * @param username
       * @return
       */
      // String queryRoleId(String username);
      List<String> queryRoleId(String username);

      /**
       *  通过 username 删除用户数据
       * @param username
       */
      void deleteUserByUsername(String username);

      /**
       * 模糊匹配，查询 a_sys_user 
       * @param username
       * @param name
       * @param user_type
       * @param org_code
       * @param is_ok
       * @return
       */
      List<SysUser> selectByFiveParameter(String username, String name, String user_type, String org_code, String is_ok);

}
