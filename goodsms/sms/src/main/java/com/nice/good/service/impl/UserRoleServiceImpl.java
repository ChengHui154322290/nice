package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.UserRoleMapper;
import com.nice.good.model.UserRole;
import com.nice.good.service.UserRoleService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/20.
 */
@Service
@Transactional
public class UserRoleServiceImpl extends AbstractService<UserRole> implements UserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public  void userRoleAdd(UserRole userRole,String userId) throws Exception{

        userRole.setKeyId(IdsUtils.getOrderId());
        userRole.setCreateId(userId);
        userRole.setModifyId(userId);
        userRole.setCreateDate(TimeStampUtils.getTimeStamp());
        userRole.setModifyDate(TimeStampUtils.getTimeStamp());

        userRoleMapper.insert(userRole);

    }


   @Override
   public void  userRoleUpdate(UserRole userRole,String userId){

        userRole.setModifyId(userId);
        userRole.setModifyDate(TimeStampUtils.getTimeStamp());
        userRoleMapper.updateByPrimaryKey(userRole);
   }

    /**
     * 通过 username 查询 name 角色名 -- List<String>集合  -- 2018/05/21  13:42
     *
     * @param username
     * @return
     */
    @Override
    public List<String> findRoleNamesByUsername(String username) {
        return userRoleMapper.findRoleNamesByUsername(username);
    }

    /**
     * 通过 username 删除 a_user_role 数据
     * @param username
     */
    @Override
    public void deleteUserByUsername(String username) {
        userRoleMapper.deleteUserByUsername(username);
    }

    /**
     *  通过 username 查询 roleId -- List<String>集合
     * @param username
     * @return
     */
    @Override
    public List<String> findRoleIdsByUsername(String username) {
        return userRoleMapper.findRoleIdsByUsername(username);
    }

    /**
     * 传递 username、 roleId、 userId 数据，对 a_user_role 表进行数据插入 。
     * @param username
     * @param roleId
     * @param userId
     * @throws Exception
     */
    @Override
    public void insertUserRoleByUsernameRoleId(String username, String roleId, String userId) throws Exception {

        UserRole userRole = new UserRole();

        // 设置 key_id
        userRole.setKeyId(IdsUtils.getOrderId());
        // 设置 username
        userRole.setUsername(username);
        // 设置 role_id
        userRole.setRoleId(roleId);
        // 设置 create_id
        userRole.setCreateId(userId);
        // 设置 modify_id
        userRole.setModifyId(userId);
        // 设置 create_date
        userRole.setCreateDate(TimeStampUtils.getTimeStamp());
        // 设置 modify_date
        userRole.setModifyDate(TimeStampUtils.getTimeStamp());

        userRoleMapper.insert(userRole);
    }

    /**
     *  通过 roleId 删除 a_user_role 表中的数据
     * @param roleId
     */
    @Override
    public void deleteUserByRoleId(String roleId) {
        userRoleMapper.deleteUserByRoleId(roleId);
    }
}
