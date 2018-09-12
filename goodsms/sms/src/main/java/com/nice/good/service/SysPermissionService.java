package com.nice.good.service;
import com.nice.good.model.SysPermission;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/17.
 */
public interface SysPermissionService extends Service<SysPermission> {
      void sysPermissionAdd(SysPermission sysPermission,String userId) throws Exception;
      void sysPermissionUpdate(SysPermission sysPermission,String userId);

      @Override
      List<SysPermission> findAll();

      /**
       * 返回a_sys_permission 表中所有数据
       * @param username
       * @return
       */
      List<SysPermission> listPermissions(String username);

      /**
       * 返回二级子菜单
       */
      SysPermission ListPermissionByPermissionId(Integer permissionId);

}
