package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.DynamicPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DynamicPermissionMapper extends Mapper<DynamicPermission> {
    /**
     * 根据role_id, first_permission 获取List<Integer> second_permission
     * @param role_id
     * @param first_permission
     * @return
     */
    List<Integer> querySecondPermission(@Param(value ="role_id" ) String role_id,@Param(value = "first_permission") Integer first_permission);

    /**
     * 通过role_id 删除a_dynamic_permission
     * @param role_id
     */
    void deleteByRoleId(@Param(value = "role_id") String role_id);

    /**
     * 通过 role_id 在 a_dynamic_permission 表中查询 first_permission， 并去除重复的 first_permission
     * @param role_id
     * @return
     */
    List<Integer> selectFirstPermissionByRoleId(@Param(value = "role_id")String role_id);

    String selectReceivePermission(String username);

    String selectPickPermission(String username);





}