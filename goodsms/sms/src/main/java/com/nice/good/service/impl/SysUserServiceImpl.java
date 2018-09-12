package com.nice.good.service.impl;


import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.SysUserMapper;
import com.nice.good.model.SysUser;
import com.nice.good.service.SysUserService;
import com.nice.good.core.AbstractService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/12.
 */
@Service
@Transactional
public class SysUserServiceImpl extends AbstractService<SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 通过 username 查询 a_sys_user 表中是否含有 username , 若无对应的 username ，则给出相应的提示。  2018/05/19 11:07 rk
     * @param username
     * @return
     */
    @Override
    public String checkUsername(String username) {
        return sysUserMapper.checkUsername(username);
    }

    @Override
    public void sysUserAdd(SysUser sysUser, String userId) throws Exception {

            // 以下代码没有问题.  -- 2018/04/13  14:20 rk

            sysUser.setUserId(sysUser.getUsername());
            sysUser.setCreateId(userId);
            sysUser.setModifyId(userId);
            sysUser.setCreateDate(TimeStampUtils.getTimeStamp());
            sysUser.setModifyDate(TimeStampUtils.getTimeStamp());
            sysUserMapper.insert(sysUser);

    }

    @Override
    public String checkSysUsername(String sysUsername) {
        return sysUserMapper.checkIdByUsername(sysUsername);
    };

    @Override
    public void sysUserUpdate(SysUser sysUser, String userId) {

        sysUser.setModifyId(userId);
        sysUser.setModifyDate(TimeStampUtils.getTimeStamp());
        sysUserMapper.updateByPrimaryKey(sysUser);
    }

    /**
     * 实现登录功能
     */
    @Override
    public SysUser sysUserLogin(String username, String password) {
        return sysUserMapper.login(username, password);
    }

    /**
     * 根据username 查询role_id  使用a_user_role
     * @param username
     * @return
     */
    @Override
    public List<String> queryRoleId(String username) {
        return sysUserMapper.queryRoleId(username);
    }

    /**
     * 通过 username 删除用户数据
     * @param username
     */
    @Override
    public void deleteUserByUsername(String username) {
        sysUserMapper.deleteUserByUsername(username);
    }

    /**
     * 模糊匹配，查询 a_sys_user
     * @param username
     * @param name
     * @param user_type
     * @param org_code
     * @param is_ok
     * @return
     */
    @Override
    public List<SysUser> selectByFiveParameter(String username, String name, String user_type, String org_code, String is_ok) {
        return sysUserMapper.selectByFiveParameter(username, name, user_type, org_code, is_ok);
    }
}
