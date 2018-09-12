package com.nice.good.service;
import com.nice.good.model.DynamicPermission;
import com.nice.good.core.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/17.
 */
public interface DynamicPermissionService extends Service<DynamicPermission> {
      void dynamicPermissionAdd(DynamicPermission dynamicPermission,String userId) throws Exception;
      void dynamicPermissionUpdate(DynamicPermission dynamicPermission,String userId);

      /**
       * 传递 createDate、createId 数据，用于“更新”角色信息
       * @param dynamicPermission
       * @param userId
       */
      void dynamicPermissionAddAndDateId(DynamicPermission dynamicPermission, String userId)  throws Exception;

      /**
       *  根据role_id, first_permission 获取List<Integer> second_permission
       * @param role_id
       * @param first_permission
       * @return
       */
      List<Integer> querySecondPermission(String role_id, Integer first_permission);

      /**
       * 通过role_id 删除a_dynamic_permission
       * @param role_id
       */
      void deleteByRoleId(String role_id);

      /**
       * 通过 role_id 在 a_dynamic_permission 表中查询 first_permission， 并去除重复的 first_permission
       * @param role_id
       * @return
       */
      List<Integer> selectFirstPermissionByRoleId(String role_id);

}
