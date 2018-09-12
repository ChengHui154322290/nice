package com.nice.good.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dto.RoleBean;
import com.nice.good.model.DynamicPermission;
import com.nice.good.model.RolePermission;
import com.nice.good.model.SysRole;
import com.nice.good.service.DynamicPermissionService;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.service.RolePermissionService;
import com.nice.good.service.SysRoleService;
import com.nice.good.utils.ConversionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.commons.lang3.StringUtils;;


/**
 * Created by CodeGenerator on 2018/04/17.
 */

@RestController
@RequestMapping("/dynamic/permission")
public class DynamicPermissionController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(DynamicPermissionController.class);

    @Resource
    private DynamicPermissionService dynamicPermissionService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private RolePermissionService rolePermissionService;


    @Transactional
    @PostMapping("/add")
    public Result add(@RequestBody RoleBean roleBean, HttpServletRequest request) throws Exception {
        if (roleBean == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }


        // 获取keyId
        String keyId = roleBean.getKeyId();
        // 获取数据行号 id
        Integer id = roleBean.getId();
        // 获取角色编号
        String roleId = roleBean.getRoleId();
        // 获取角色名称
        String name = roleBean.getName();
        // 获取说明
        String remark = roleBean.getRemark();
        // 获取创建人
        String createId = roleBean.getCreateId();
        // 获取创建时间
        Date createDate = roleBean.getCreateDate();
        // 获取权限集合 key为一级菜单,value为二级菜单集合
        Map<String, List<String>> permission = roleBean.getPermission();


        if (StringUtils.isBlank(keyId)) {

            // 验证角色编号、角色名称不能重复
            if (sysRoleService.checkRoleIDOrRoleName(roleId) != null) {
                return ResultGenerator.genFailResult(ResultCode.ROLEIDORROLENAME_IS_REPEAT);
            }

            // 在a_sys_role 角色表中添加对应的 roleId、 name、 remark 等信息
            SysRole sysRole = new SysRole(roleId, name, remark);

            // 向 a_sys_role 表中 添加 一条数据
            sysRoleService.sysRoleAdd(sysRole, userId);
        } else {

            // 更新角色功能
            SysRole sysRole = new SysRole(keyId, id, roleId, name, remark, createId, createDate);

            // 向 a_sys_role 表中 更新 一条数据
            sysRoleService.sysRoleUpdate(sysRole, userId);

        }


        // 在这里需要循环获取 first_permission、 second_permission
        Set<String> permissionKey = permission.keySet();


        if (permissionKey == null || permissionKey.size() == 0) {
            return ResultGenerator.genSuccessResult();
        }

        //新增角色操作
        if (StringUtils.isBlank(keyId)) {

            addUnitePermission(userId, roleId, name, remark, permission, permissionKey);

        } else {

            // 更新角色动态权限信息
            // 在 a_dynamic_permission 动态权限表中 删除所有 roleId 对应的数据
            dynamicPermissionService.deleteByRoleId(roleId);

            // 更新 角色权限表信息
            // 在 a_role_permission 表中，删除所有 roleId 对应的数据
            rolePermissionService.deleteByRoleId(roleId);

            addUnitePermission(userId, roleId, name, remark, permission, permissionKey);
        }

        return ResultGenerator.genSuccessResult();
    }


    private void addUnitePermission(String userId, String roleId, String name, String remark, Map<String, List<String>> permission, Set<String> permissionKey) throws Exception {

        for (String key : permissionKey) {
            // 根据 key--Key，获取 permission<K,V> 中的 V值 -- List<String>， 第 二级子菜单集合
            List<String> secondPermissions = permission.get(key);

            // 对前台传入的数据进行判断， 二级子菜单为“空”的数据进行舍弃
            if (secondPermissions != null && !secondPermissions.isEmpty()) {
                // 获取第 一级菜单id

                Integer firstPermission = ConversionUtils.StringToInt(key);

                RolePermission rolePermission = new RolePermission(roleId, firstPermission);
                // 将 firstPermission、role_id 添加到 a_role_permission
                rolePermissionService.rolePermissionAdd(rolePermission, userId);


                for (String second : secondPermissions) {

                    // 至此，解析完毕，循环获取到第 一级菜单id、第 二级菜单id
                    Integer secondPermission = new Integer(second);

                    // 将roleId、 name、 firstPermission、 second_permission、remark 封装到DynamicPermission 实体类中
                    DynamicPermission dynamicPermission = new DynamicPermission(roleId, name, firstPermission, secondPermission, remark);

                    // 在a_dynamic_permission 动态权限表中 添加 对应信息
                    dynamicPermissionService.dynamicPermissionAdd(dynamicPermission, userId);

                }

            }

        }
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {

        if (id == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        try {
            dynamicPermissionService.deleteById(id);
        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/doubleClick")
    public Result doubleClick(@RequestBody SysRole sysRole, HttpServletRequest request) {

        if (sysRole == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        if (sysRole.getKeyId() == null) {
            return ResultGenerator.genFailResult(ResultCode.KEYID_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }


        JSONObject jsonObject;

        try {
            // 该 roleMessage 用于封装 SysRole + 第 一级第 二级菜单信息, 并将该信息回显到“新增角色” 页面
            Map<String, Object> roleMessage = new HashMap<String, Object>();
            // 该 mapRolePermission 用于封装第 一级、第 二级菜单
            Map<String, List<Integer>> mapRolePermission = new HashMap<String, List<Integer>>();

            // 通过 roleId 查询获取 List<Integer> first_permission
            // 去除 first_permission 中重复的 first_permission

            Integer[] firstPermissions = {1, 2, 3, 4, 5, 6, 7, 8,9};


            // 通过 roleId、 first_permission 查询循环获取 second_permission
            for (Integer first : firstPermissions) {

                List<Integer> secondPermissions = dynamicPermissionService.querySecondPermission(sysRole.getRoleId(), first);
                // 将 Integer--first 转为 String--first
                String firstString = ConversionUtils.IntToString(first);
                // 将 first_permission 、 List<Integer> second_permission 封装Map<String, List<Integer>>
                mapRolePermission.put(firstString, secondPermissions);
            }

            // 设置 keyId -- 主键ID
            roleMessage.put("keyId", sysRole.getKeyId());
            // 设置 id 值 -- 数据行号
            roleMessage.put("id", sysRole.getId());
            // 设置 roleId  -- 角色编号
            roleMessage.put("roleId", sysRole.getRoleId());
            // 设置 name 值 -- 角色名称
            roleMessage.put("name", sysRole.getName());
            // 设置 remark 值 -- 说明信息
            roleMessage.put("remark", sysRole.getRemark());
            // 设置 createId 值 -- 创建人id
            roleMessage.put("createId", sysRole.getCreateId());
            // 设置 createDate 值 -- 创建时间
            roleMessage.put("createDate", sysRole.getCreateDate());
            // 设置 permission 值， 该值包含第 一级、第 二级菜单 。
            roleMessage.put("permission", mapRolePermission);

            // 将 roleMessage 信息转换成 String 字符串
            jsonObject = JSONObject.parseObject(JSON.toJSONString(roleMessage));

        } catch (Exception e) {
            log.error("更新对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(jsonObject);
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        if (id == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        DynamicPermission dynamicPermission = null;
        try {
            dynamicPermission = dynamicPermissionService.findById(id);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult(dynamicPermission);
    }

    @PostMapping("/list")
    public Result list(@RequestBody DynamicPermission dynamicPermission, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size, HttpServletRequest request) {
        String userId = getUserName(request);
        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }
        PageHelper.startPage(page, size);

        Condition condition = new Condition(dynamicPermission.getClass());
        Criteria criteria = condition.createCriteria();

        PageInfo pageInfo = null;
        try {
            List<DynamicPermission> list = dynamicPermissionService.findByCondition(condition);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}



