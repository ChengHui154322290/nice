package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.SysPermissionMapper;
import com.nice.good.model.SysPermission;
import com.nice.good.service.SysPermissionService;
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
public class SysPermissionServiceImpl extends AbstractService<SysPermission> implements SysPermissionService {
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public  void sysPermissionAdd(SysPermission sysPermission,String userId) throws Exception {

        sysPermission.setKeyId(IdsUtils.getOrderId());
        sysPermission.setCreateId(userId);
        sysPermission.setModifyId(userId);
        sysPermission.setCreateDate(TimeStampUtils.getTimeStamp());
        sysPermission.setModifyDate(TimeStampUtils.getTimeStamp());

        sysPermissionMapper.insert(sysPermission);

    }


   @Override
   public void  sysPermissionUpdate(SysPermission sysPermission,String userId){

        sysPermission.setModifyId(userId);
        sysPermission.setModifyDate(TimeStampUtils.getTimeStamp());
        sysPermissionMapper.updateByPrimaryKey(sysPermission);
   }

    /**
     * 返回a_sys_permission 中所有信息
     * @param username
     * @return
     */
    public List<SysPermission> listPermissions(String username) {

        return sysPermissionMapper.listPermissions(username);
    }

   @Override
   /**
    * 根据permission_id 查询获取 a_sys_permission 中所有信息
    */
   public SysPermission ListPermissionByPermissionId(Integer permissionId) {

       return sysPermissionMapper.ListPermissionByPermissionId(permissionId);
   };

}
