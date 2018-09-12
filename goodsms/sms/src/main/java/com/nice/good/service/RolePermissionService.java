package com.nice.good.service;
import com.nice.good.model.RolePermission;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/05/10.
 */
public interface RolePermissionService extends Service<RolePermission> {
      void rolePermissionAdd(RolePermission rolePermission,String userId) throws Exception;
      void rolePermissionUpdate(RolePermission rolePermission,String userId);

      /**
       * 通过role_id 删除a_role_permission
       * @param role_id
       */
      void deleteByRoleId(String role_id);


}
