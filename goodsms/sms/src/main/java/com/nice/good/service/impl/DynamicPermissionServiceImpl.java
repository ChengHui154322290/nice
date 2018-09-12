package com.nice.good.service.impl;


import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.DynamicPermissionMapper;
import com.nice.good.model.DynamicPermission;
import com.nice.good.service.DynamicPermissionService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/17.
 */
@Service
@Transactional
public class DynamicPermissionServiceImpl extends AbstractService<DynamicPermission> implements DynamicPermissionService {
    @Resource
    private DynamicPermissionMapper dynamicPermissionMapper;

    @Override
    public  void dynamicPermissionAdd(DynamicPermission dynamicPermission,String userId) throws Exception{

        dynamicPermission.setKeyId(IdsUtils.getOrderId());
        dynamicPermission.setCreateId(userId);
        dynamicPermission.setModifyId(userId);
        dynamicPermission.setCreateDate(TimeStampUtils.getTimeStamp());
        dynamicPermission.setModifyDate(TimeStampUtils.getTimeStamp());

        dynamicPermissionMapper.insert(dynamicPermission);

    }

    @Override
    public void dynamicPermissionAddAndDateId(DynamicPermission dynamicPermission, String userId) throws Exception{

        dynamicPermission.setKeyId(IdsUtils.getOrderId());
        dynamicPermission.setModifyId(userId);
        dynamicPermission.setModifyDate(TimeStampUtils.getTimeStamp());

        dynamicPermissionMapper.insert(dynamicPermission);
    }

    @Override
   public void  dynamicPermissionUpdate(DynamicPermission dynamicPermission,String userId){

        dynamicPermission.setModifyId(userId);
        dynamicPermission.setModifyDate(TimeStampUtils.getTimeStamp());
        dynamicPermissionMapper.updateByPrimaryKey(dynamicPermission);
   }

    /**
     *  根据role_id, first_permission 获取List<Integer> second_permission
     * @param role_id
     * @param first_permission
     * @return
     */
    @Override
    public List<Integer> querySecondPermission(String role_id, Integer first_permission) {
       return  dynamicPermissionMapper.querySecondPermission(role_id, first_permission);
    }

    /**
     * 通过role_id 删除a_dynamic_permission
     * @param role_id
     */
    @Override
    public void deleteByRoleId(String role_id) {
        dynamicPermissionMapper.deleteByRoleId(role_id);
    }

    /**
     * 通过 role_id 在 a_dynamic_permission 表中查询 first_permission， 并去除重复的 first_permission
     * @param role_id
     * @return
     */
    @Override
    public List<Integer> selectFirstPermissionByRoleId(String role_id) {
        return dynamicPermissionMapper.selectFirstPermissionByRoleId(role_id);
    }
}
