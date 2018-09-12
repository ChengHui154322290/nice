package com.nice.good.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dto.PermissionBean;
import com.nice.good.dto.PermissionNameBean;
import com.nice.good.model.SysUser;
import com.nice.good.model.SysUserPermission;
import com.nice.good.service.*;

import com.nice.good.enums.ResultCode;

import com.github.pagehelper.PageInfo;
import com.nice.good.utils.SavePermissionUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import org.apache.commons.lang3.StringUtils;;

/**
 * Created by CodeGenerator on 2018/04/12.
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(SysUserController.class);

    @Resource
    private SysUserService sysUserService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserGooderService userGooderService;
    @Resource
    private UserPlaceService userPlaceService;
    @Resource
    private GooderService gooderService;
    @Resource
    private SysPlaceService sysPlaceService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private OrganizationService organizationService;



    @PostMapping("/listOrgCod")
    public Result listOrgCodes() {

        List<String> orgCodes = new ArrayList<String>();

        try {

            // 封装 组织机构编码 orgCodes
            orgCodes = organizationService.findOrgCodes();

        } catch (Exception e) {
            log.error("查询组织编码异常!");
        }
        return ResultGenerator.genSuccessResult(orgCodes);
    }

    @Transactional
    @PostMapping("/add")
    @LogAnnotation(logType = "其他日志", content = "用户新增")
    public Result add(@RequestBody SysUser sysUser, HttpServletRequest request) {


        try {

            if (sysUser == null) {
                return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
            }

            String userId = getUserName(request);


            if (StringUtils.isBlank(userId)) {
                return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
            }

            if (sysUser.getUserId() == null) {

                //用户新增
                sysUser.setFlag(0);

                // 检查sysUser.username是否重复
                if (sysUserService.checkSysUsername(sysUser.getUsername()) != null) {
                    return ResultGenerator.genFailResult(ResultCode.SYSUSERNAME_IS_REPEAT);
                }

                sysUserService.sysUserAdd(sysUser, userId);

            } else {

                //用户修改
                sysUser.setFlag(1);

                // 设置 userId -- 用户ID
                String newUserId = sysUser.getUserId();
                // 设置 id -- 数据行号
                Integer id = sysUser.getId();
                // 设置 username -- 用户名
                String username = sysUser.getUsername();
                // 设置 name -- 姓名
                String name = sysUser.getName();
                // 设置 password -- 密码
                String password = sysUser.getPassword();
                // 设置 remark -- 说明
                String remark = sysUser.getRemark();
                // 设置 userType -- 用户类型
                String userType = sysUser.getUserType();
                // 设置 orgCode -- 组织机构编码
                String orgCode = sysUser.getOrgCode();
                // 设置 mobilePhone -- 手机号
                String mobilePhone = sysUser.getMobilePhone();
                // 设置 phone -- 电话号
                String phone = sysUser.getPhone();
                // 设置 sex -- 性别
                String sex = sysUser.getSex();
                // 设置 email -- 电子邮件
                String email = sysUser.getEmail();
                // 设置 qqNumber -- QQ号
                Integer qqNumber = sysUser.getQqNumber();
                // 设置 isOk -- 是否启用
                String isOk = sysUser.getIsOk();
                // 设置 address -- 联系地址
                String address = sysUser.getAddress();
                // 设置 createId -- 创建人ID
                String createId = sysUser.getCreateId();
                // 设置 createDate -- 创建时间
                Date createDate = sysUser.getCreateDate();

                // 将数据封装到 SysUser.java 中
                SysUser newSysUser = new SysUser(newUserId, id, username, name, password, remark, userType, orgCode, mobilePhone, phone, sex, email, qqNumber, isOk, address, createId, createDate);

                sysUserService.sysUserUpdate(newSysUser, userId);

            }

        } catch (Exception e) {
            if (sysUser.getUserId() == null) {
                log.error("新增对象操作异常e:{}", e);
            } else {
                log.error("更新对象操作异常e:{}", e);
            }
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 在删除用户时，还应该考虑同时删除 a_user_gooder(用户-货主表权限)、 a_user_place(用户-场地表权限)  -- 2018/04/21  14:19
     * 同时删除 a_user_gooder(用户-货主表权限)、 a_user_place(用户-场地表权限)，该功能暂时待定
     *
     * @param usernames
     * @param
     * @return
     */
    @PostMapping("/deleteUser")
    public Result delete(@RequestParam String[] usernames) {

        if (usernames == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        try {


            for (String username : usernames) {

                if ("admin".equals(username)){
                    continue;
                }

                // 通过 username 循环删除用户 a_sys_user
                sysUserService.deleteUserByUsername(username);
                // 通过 username 循环删除 a_user_role 中对应的数据
                userRoleService.deleteUserByUsername(username);
                //通过username删除a_user_place表中的数据
                userPlaceService.deleteUserPlaceByUsername(username);
                //通过username删除a_user_gooder表中数据
                userGooderService.deleteUserGooderByUsername(username);
            }

        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody SysUser sysUser,HttpServletRequest request) {

        if (sysUser == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }
        if (sysUser.getUserId() == null) {
            return ResultGenerator.genFailResult(ResultCode.ID_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }

        try {

            sysUserService.sysUserUpdate(sysUser, userId);

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

        SysUser sysUser = null;

        try {
            sysUser = sysUserService.findById(id);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult(sysUser);
    }

    /**
     * 模糊查询  ---->  根据 username、 name、 user_type、 org_code、 is_ok
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/listUsers")
    public Result list(@RequestBody SysUser sysUser, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        if (sysUser == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        PageHelper.startPage(page, size);

        PageInfo pageInfo = null;

        try {
            String username = sysUser.getUsername();
            String name = sysUser.getName();
            String user_type = sysUser.getUserType();
            String org_code = sysUser.getOrgCode();
            String is_ok = sysUser.getIsOk();



            List<SysUser> listUsers = sysUserService.selectByFiveParameter(username, name, user_type, org_code, is_ok);

            pageInfo = new PageInfo(listUsers);

        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 由于时间紧缺、研发人员不足，在该功能中，“角色权限”目前一个用户仅一个角色。
     * “货主权限”、“场地权限”，一个用户可以有多个货主权限、场地权限。
     * 该方法主要用于查询 username 的3类权限： 货主权限、 场地权限、 角色权限
     *
     * @param sysUser
     * @param
     * @return
     */
    @PostMapping("/distributionParameter")
    public Result distributionParameter(@RequestBody SysUser sysUser, HttpServletRequest request) {

        if (sysUser == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }

        // 初始化 PermissionNameBean -- 权限Bean    -- 2018/05/21  12:07
        PermissionNameBean permissionNameBean = new PermissionNameBean();
        try {

            String username = sysUser.getUsername();

            // 设置 PermissionNameBean.java 实体类中的 roleName 角色名称    -- 2018/05/21  12:07
            List<Map<String, String>> roleNameBeanList = new ArrayList<Map<String, String>>();

            // 设置 PermissionNameBean.java 实体类中的 gooderName  货主名称
            List<Map<String, String>> gooderNameBeanList = new ArrayList<Map<String, String>>();

            // 设置 PermissionNameBean.java 实体类中的 exhibition  场地名称
            List<Map<String, String>> exhibitionBeanList = new ArrayList<Map<String, String>>();


            // 查询所有的货主名称 gooderName    -- 2018/05/21  11:49
            List<String> allGooderNamesList = gooderService.findAllGooderNames();
            // 查询所有的场地名称 exhibition -- 场地名称
            List<String> allPlaceNamesList = sysPlaceService.findExhibitions();
            // a_sys_role表 查询所有的角色名称-- name
            List<String> allRoleNamesList = sysRoleService.findRoleName();


            // 根据 username 查询3张表 a_user_role、 a_user_gooder、 a_user_place 所对应的权限
            // 根据 username 查询表 a_user_role ，返回 roleId--List<String> 集合
            // 设置 username 的 name 角色名 集合权限      -- 2018/05/21  13:39
            //List<String> userRoleIdList = userRoleService.findRoleIdsByUsername(username);
            List<String> userRoleNamesList = userRoleService.findRoleNamesByUsername(username);


            // 根据 username 查询表 a_user_gooder ，返回 gooderCode--List<String> 集合
            // 设置 username 的 gooderNames 货主名称集合权限    -- 2018/05/21  14:20
            // List<String> userGooderCodeList = userGooderService.findGooderCodeByUsername(username);
            List<String> userGooderNamesList = userGooderService.findGooderNameByUsername(username);


            // 根据 username 查询表 a_user_place ，返回 placeNumber--List<String> 集合
            // 设置 username 的 placeNumber 场地编码集合权限   -- 2018/05/21  14:30
            // List<String> userPlaceNumberList = userPlaceService.findPlaceNumbersByUsername(username);
            List<String> userPlaceNamesList = userPlaceService.findPlaceNamesByUsername(username);



            // ----------------------------------------------------------
            // 给 gooderCodeBeanList<Map<K, V>> 中的 V 赋值为 false
            for (String gooderName : allGooderNamesList) {

                Map<String, String> map = new HashMap<String, String>();

                if ( userGooderNamesList.contains(gooderName) ) {

                    map.put("gooderName", gooderName);
                    map.put("checked", "true");

                } else {

                    map.put("gooderName", gooderName);
                    map.put("checked", "false");

                }

                gooderNameBeanList.add(map);
            }


            // ----------------------------------------------------------
            // 给 placeNumberBeanList<Map<K, V>> 中的K、V 赋值
            for (String placeName : allPlaceNamesList) {

                Map<String, String> map = new HashMap<String, String>();

                if ( userPlaceNamesList.contains(placeName) ) {

                    map.put("placeName", placeName);
                    map.put("checked", "true");

                } else {

                    map.put("placeName", placeName);
                    map.put("checked", "false");

                }

                exhibitionBeanList.add(map);
            }


            // ----------------------------------------------------------
            // 给 roleIdBeanList<Map<K, V>> 中的K、V 赋值
            for (String roleName : allRoleNamesList) {

                Map<String, String> map = new HashMap<String, String>();

                if ( userRoleNamesList.contains(roleName) ) {

                    map.put("roleName", roleName);
                    map.put("checked", "true");

                } else {

                    map.put("roleName", roleName);
                    map.put("checked", "false");

                }

                roleNameBeanList.add(map);
            }


            // 将roleIdBeanList、 gooderCodeBeanList、 placeNumberBeanList 封装到 permissionBean 实体类中
            // permissionBean = new PermissionBean(username, roleIdBeanList, gooderCodeBeanList, placeNumberBeanList);


            // 将roleIdBeanList、 gooderCodeBeanList、 placeNumberBeanList 封装到 permissionBean 实体类中
            permissionNameBean = new PermissionNameBean(username, roleNameBeanList, gooderNameBeanList, exhibitionBeanList);

        } catch (Exception e) {
            log.error("权限分配操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult(permissionNameBean);
    }

    /**
     * 保存3类权限： 货主权限、 场地权限、 角色权限
     * 先根据 username 删除 a_user_gooder、 a_user_place、 a_user_role 中的数据，
     * 之后，根据 username gooderName 将数据保存到 a_user_gooder 表中
     * 根据 username placeName 将数据保存到 a_user_place 表中
     * 根据 username roleName 将数据保存到 a_user_role 表中
     *
     * @param permissionNameBean
     * @param request
     * @return
     */
    @PostMapping("/saveParameter")
    public Result saveParameter(@RequestBody PermissionNameBean permissionNameBean, HttpServletRequest request) {

        if (permissionNameBean == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        String userId = getUserName(request);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERID_IS_NULL);
        }

        try {
            // 获取 username 值
            String username = permissionNameBean.getUsername();
            // 获取 List<String> gooderNames
            List<Map<String,String>> gooderNames = permissionNameBean.getGooderNameList();
            // 获取 List<String> placeNames
            List<Map<String, String>> placeNames = permissionNameBean.getExhibitionList();  //.getPlaceNumberList();
            // 获取 List<String> roleNames
            List<Map<String, String>> roleNames = permissionNameBean.getRoleNameList();  //.getRoleIdList();

            // 根据 username 删除 a_user_gooder 表中的对应数据
            userGooderService.deleteUserGooderByUsername(username);
            // 循环获取 List<String> gooderCodeList 中的值 , 并将 username -- gooderCode[i] 插入 a_user_gooder 表中
            List<String> gooderNameList = SavePermissionUtil.getGooderName(gooderNames);  // .getGooderCode(gooderNames);
            // 判断 gooderCodeList 集合是否为空
            if(gooderNameList != null && !gooderNameList.isEmpty()){
                for (String name : gooderNameList) {

                    // 根据 name 值查询 gooder_code， 在 n_gooder 表中查询
                    String code = gooderService.findGooderCodeByGooderName(name);
                    // 将 username 、 code、 userId 插入到 a_user_gooder 表中
                    userGooderService.insertUserGooderByUsernameGooderCode(username, code, userId);
                }
            }


            // 根据username 删除 a_user_place 表中的对应数据
            userPlaceService.deleteUserPlaceByUsername(username);
            // 循环获取 List<String> placeNumberList 中的值， 并将 username -- placeNumbers[i] 插入到 a_user_place 表中
            List<String> placeNameList = SavePermissionUtil.getPlaceName(placeNames);   //.getPlaceNumber(placeNames);
            // 判断 placeNumberList 集合是否为空
            if(placeNameList != null && !placeNameList.isEmpty()){
                for (String name : placeNameList) {

                    // 根据 name 值查询 place_number, 在 a_sys_place 表中查询
                    String number = sysPlaceService.findPlaceNumberByExhibition(name);
                    // 将 username 、 number、 userId 插入到 a_user_place 表中
                    userPlaceService.insertUserPlaceByUsernamePlaceNumber(username, number, userId);
                }
            }


            // 根据username 删除 a_user_role 表中的对应数据
            userRoleService.deleteUserByUsername(username);
            // 循环获取 List<String> roleIdList 中的值， 并将 username -- roleIds[i] 中的值插入到 a_user_role 表中
            List<String> roleNameList = SavePermissionUtil.getRoleName(roleNames);   // .getRoleId(roleIds);
            // 判断 roleIdList 集合是否为空
            if(roleNameList != null && !roleNameList.isEmpty()){
                for (String name : roleNameList) {

                    // 根据 name 查询 role_id， 在 a_sys_role 表中查询
                    String role_id = sysRoleService.findRoleIdByName(name);
                    // 将 username 、 role_id、 userId 插入到 a_user_role 表中
                    userRoleService.insertUserRoleByUsernameRoleId(username, role_id, userId);
                }
            }

        } catch (Exception e) {
            log.error("权限保存操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }

}
