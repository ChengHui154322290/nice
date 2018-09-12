package com.nice.good.web;


import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.RolePermissionMapper;
import com.nice.good.dao.UserRoleMapper;
import com.nice.good.model.SysRole;
import com.nice.good.model.UserRole;
import com.nice.good.service.DynamicPermissionService;
import com.nice.good.service.SysRoleService;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.service.UserRoleService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/04/17.
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(SysRoleController.class);

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private DynamicPermissionService dynamicPermissionService;
    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermissionMapper rolePermissionMapper;


    @Resource
    private UserRoleMapper userRoleMapper;



    /**
     * 删除角色功能
     * 要在 a_sys_role 表中删除对应的角色 、在a_dynamic_permission 表中删除角色的动态权限
     * 在 a_user_role 表中，是否应该删除对应的 username -- role_id ？  该功能暂时待定   -- 工作时间紧，需求文档不完善。  -- 2018/04/23  rk
     *
     * @param roleId
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam String[] roleId) {

        if (StringUtils.isBlank(roleId.toString())) {
            return ResultGenerator.genFailResult(ResultCode.ROLEID_IS_NULL);
        }

        // 记录角色删除信息
        List<String> dataList = new ArrayList<String>();

        try {

            for (String key : roleId) {


                if ("admin1".equals(key)) {
                    dataList.add(key + "超级管理员角色不可删除!");
                    continue;
                }

                //如果该角色已被分配，则无法删除
                UserRole userRole = userRoleMapper.selectByRoleId(key);
                if (userRole != null) {
                    dataList.add(key + "角色不可删除，有用户占用该角色。");
                    continue;
                }

                // 根据 roleId 删除 a_sys_role 中的角色信息
                sysRoleService.deleteByRoleId(key);

                // 根据 roldId 删除 a_dynamic_permission 中的动态权限信息
                dynamicPermissionService.deleteByRoleId(key);

                // 在 a_user_role 表中，是否应该删除对应的 username -- role_id ？  该功能暂时待定   -- 工作时间紧，需求文档不完善。  -- 2018/04/23  rk
                userRoleService.deleteUserByRoleId(key);

                //根据roleId 删除a_role_permission()中的所有权限信息  范青山添加
                rolePermissionMapper.deleteByRoleId(key);

                dataList.add(key + "角色删除成功");

            }

        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(dataList);
    }

    @PostMapping("/update")
    public Result update(@RequestBody SysRole sysRole, HttpServletRequest request) {

        if (sysRole == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        if (sysRole.getId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }

        try {

            sysRoleService.sysRoleUpdate(sysRole, userId);

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {

        if (id == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        SysRole sysRole = null;

        try {
            sysRole = sysRoleService.findById(id);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult(sysRole);
    }

    // 查询功能： 模糊查询，传递的2个参数： 角色编码、角色名称
    // 自己编写SQL语句、模糊查询， controller层 service层、 serviceImpl层、mapper层、xml、
    @PostMapping("/list")
    public Result list(@RequestBody SysRole sysRole, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {
        // public Result list(@RequestBody SysRole sysRole) {

        PageHelper.startPage(page, size);

        PageInfo pageInfo = null;

        try {

            String role_id = sysRole.getRoleId();
            String name = sysRole.getName();
            List<SysRole> list = sysRoleService.findRoleByIdORName(role_id, name);

            pageInfo = new PageInfo(list);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }


}
