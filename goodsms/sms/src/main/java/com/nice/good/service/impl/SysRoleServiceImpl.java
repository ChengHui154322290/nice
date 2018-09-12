package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.SysRoleMapper;
import com.nice.good.model.SysRole;
import com.nice.good.service.SysRoleService;
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
public class SysRoleServiceImpl extends AbstractService<SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    /*
    @Override
    public String checkRoleIDOrRoleName(String role_id, String name) {
        return sysRoleMapper.checkRoleIDOrRoleName(role_id, name);
    }
    */

    /**
     * 通过 role_id 查询 a_user_role 表中是否有对应的 username  2018/05/16  16:18  rk
     * @param role_id
     * @return
     */
    @Override
    public String findUsernameByRoleId(String role_id) {
        return sysRoleMapper.findUsernameByRoleId(role_id);
    }

    @Override
    public String checkRoleIDOrRoleName(String role_id) {
        return sysRoleMapper.checkRoleIDOrRoleName(role_id);
    }

    @Override
    public void sysRoleAdd(SysRole sysRole, String userId) throws Exception {

        sysRole.setKeyId(IdsUtils.getOrderId());
        sysRole.setCreateId(userId);
        sysRole.setModifyId(userId);
        sysRole.setCreateDate(TimeStampUtils.getTimeStamp());
        sysRole.setModifyDate(TimeStampUtils.getTimeStamp());

        sysRoleMapper.insert(sysRole);

    }


    @Override
    public void sysRoleUpdate(SysRole sysRole, String userId) {

        sysRole.setModifyId(userId);
        sysRole.setModifyDate(TimeStampUtils.getTimeStamp());
        sysRoleMapper.updateByPrimaryKey(sysRole);
    }

    /**
     * 通过 name 查询 a_sys_role 表中的 role_id 值   -- 2018/05/21  17:35  rk
     *
     * @param name
     * @return
     */
    @Override
    public String findRoleIdByName(String name) {
        return sysRoleMapper.findRoleIdByName(name);
    }

    /**
     * 通过 role_id 查询 a_sys_role 表中的 name 值    -- 2018/05/21 10:22 rk
     * @param role_id
     * @return
     */
    @Override
    public String findNameByRoleId(String role_id) {
        return sysRoleMapper.findNameByRoleId(role_id);
    }

    /**
     * 查询 a_sys_role 表中的所有 name --角色名称
     * @return
     */
    @Override
    public List<String> findRoleName() {
        return sysRoleMapper.findRoleName();
    }


    /**
     * 查询 a_sys_role 表中的所有 role_id --角色id
     * @return
     */
    @Override
    public List<String> findRoleId() {
        return sysRoleMapper.findRoleId();
    }


    /**
     * 通过roleId、name 模糊匹配， 查询角色。
     * @param role_id
     * @param name
     * @return
     */
    @Override
    public List<SysRole> findRoleByIdORName(String role_id, String name) {
        return sysRoleMapper.findRoleByIdORName(role_id, name);
    }

    /**
     * 通过roleId 删除 a_sys_role 中的数据
     * @param roleId
     */
    @Override
    public void deleteByRoleId(String roleId) {
        sysRoleMapper.deleteByRoleId(roleId);
    }

}
