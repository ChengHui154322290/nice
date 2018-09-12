package com.nice.good.service.impl;


import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.RolePermissionMapper;
import com.nice.good.model.RolePermission;
import com.nice.good.service.RolePermissionService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/05/10.
 */
@Service
@Transactional
public class RolePermissionServiceImpl extends AbstractService<RolePermission> implements RolePermissionService {
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public  void rolePermissionAdd(RolePermission rolePermission,String userId) throws Exception {

        // 设置主键  key_id
        rolePermission.setKeyId(IdsUtils.getOrderId());
        // 设置创建人id  create_id
        rolePermission.setCreateId(userId);
        // 设置修改人id  modify_id
        rolePermission.setModifyId(userId);
        // 设置创建时间  create_date
        rolePermission.setCreateDate(TimeStampUtils.getTimeStamp());
        // 设置修改时间  modify_date
        rolePermission.setModifyDate(TimeStampUtils.getTimeStamp());

        rolePermissionMapper.insert(rolePermission);

    }


   @Override
   public void  rolePermissionUpdate(RolePermission rolePermission,String userId){

        rolePermission.setModifyId(userId);
        rolePermission.setModifyDate(TimeStampUtils.getTimeStamp());
        rolePermissionMapper.updateByPrimaryKey(rolePermission);
   }

    /**
     * 通过role_id 删除a_role_permission
     * @param role_id
     */
    @Override
    public void deleteByRoleId(String role_id) {
        rolePermissionMapper.deleteByRoleId(role_id);
    }
}
