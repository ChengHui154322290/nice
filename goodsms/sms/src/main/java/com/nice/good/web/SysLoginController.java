package com.nice.good.web;


import com.alibaba.fastjson.JSON;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.SysPlaceMapper;
import com.nice.good.dao.UserGooderMapper;
import com.nice.good.dao.UserPlaceMapper;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.*;
import com.nice.good.service.DynamicPermissionService;
import com.nice.good.service.SysPermissionService;
import com.nice.good.service.SysUserService;
import com.nice.good.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/sys/login")
public class SysLoginController {

    private static Logger log = LoggerFactory.getLogger(SysUserController.class);

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysPermissionService sysPermissionService;
    @Resource
    private DynamicPermissionService dynamicPermissionService;

    @Resource
    private UserPlaceMapper userPlaceMapper;

    @Resource
    private UserGooderMapper userGooderMapper;



    /**
     * 实现登录功能
     *
     * @param request
     * @return
     */
    @PostMapping("/in")
    public String login(@RequestBody SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {

        try {

            // 用于返回"flag"标志，已判断其是否登录成功。
            Map<String, Object> map = new HashMap<String, Object>();


            String username = sysUser.getUsername();
            String password = sysUser.getPassword();

            // 核查 username 是否存在
            String checkUsername = sysUserService.checkUsername(username);

            // 通过 username、password 查询 user 的信息
            SysUser user = sysUserService.sysUserLogin(username, password);


            // 接收封装好的 第 一级菜单名， 第 二级菜单名、第 二级菜单路径
            List<Object> listObj = new ArrayList<Object>();

            //设置第 一级子菜单的permission_id值
            List<SysPermission> listsPermission;

            //将第 一级菜单、第 二级菜单封装到Map<String, List<String>>集合中
            //根据第 一级子菜单ID值，获取第 一级子菜单名字 。Map<K,V>中的K
            //根据role_id, first_permission获取第 二级permission_id，之后查询a_sys_permission中，获取第 二级permission_id对应的name值
            // Map<String, List<String>> dynamicPermissionMaps = new HashMap<String, List<String>>();

            if (StringUtils.isBlank(checkUsername)) {
                map.put("flag", "noName");
                return JSON.toJSONString(map);
            }

            if (user == null || "否".equals(user.getIsOk())) {
                // 登录失败
                map.put("flag", "ng");
                return JSON.toJSONString(map);
            }


            //查询一级菜单  范青山添加备注
            listsPermission = sysPermissionService.listPermissions(username);

            // 循环遍历，逐个获取第 一级子菜单下的List<String> n个 二级子菜单
            for (SysPermission sysPermission : listsPermission) {

                // 封装第 一级菜单名，封装第 二级菜单名和第 二级菜单path
                Map<String, Object> mapObject = new HashMap<String, Object>();

                // 第 一级权限名
                String firstPermissionName = sysPermission.getName();
                // 封装第 一级菜单名
                mapObject.put("name", firstPermissionName);

                // 第 一级权限id
                Integer firstPermissionId = sysPermission.getPermissionId();

                // 获取role_id  -- 现在，一个用户多个角色， role_id 返回结果为 List<String>  -- 2018/05/19 16:30

                List<String> roleIds = sysUserService.queryRoleId(username);


                //设置第 二级子菜单的permission_id值 -- 集合
                Set<Integer> listsSecond = new HashSet<Integer>();


                // 根据List<String> role_id、第 一级权限id，获取第 二级权限id 获取的是一个List<Integer> 集合
                for (String role : roleIds) {

                    // 根据 role、 first_permission_id 获取 第 二级子菜单 second_permission
                    List<Integer> testSecond = dynamicPermissionService.querySecondPermission(role, firstPermissionId);

                    for (Integer second : testSecond) {
                        listsSecond.add(second);
                    }

                }

                //设置第 二级子菜单的信息：name、path -- 集合
                List<Map<String, String>> listsSecondNameAndPath = new ArrayList<Map<String, String>>();

                // 循环遍历 listsSeconde -- 第 二级子菜单permission_id， 到 a_sys_permission 查询到对应的 name, path，在进行封装。 -- 2018/05/10 rk
                for (Integer nums : listsSecond) {

                    Map<String, String> secondMsg = new HashMap<String, String>();
                    // 通过 nums -- 第 二级菜单permission_id，获取第 二级菜单的：name、 path
                    SysPermission secondSysPermission = sysPermissionService.ListPermissionByPermissionId(nums);

                    secondMsg.put("name", secondSysPermission.getName());
                    secondMsg.put("path", secondSysPermission.getPath());

                    // 添加第 二级菜单封装好的数据： 菜单名--name、 菜单路径--path
                    listsSecondNameAndPath.add(secondMsg);
                }

                mapObject.put("child", listsSecondNameAndPath);

                // 第 一级菜单名，第 二级菜单名name、菜单路径path 封装完成
                listObj.add(mapObject);

            }

            map.put("permission", listObj);
            // 登录成功
            map.put("flag", "ok");


            //向cookie中写入数据
            String cookieName = "tokenId";

            String cookieValue = UUID.randomUUID().toString();

            //设置cookie失效时间,12个小时
            int existTime = 12 * 60 * 60;


            Cookie cookie = new Cookie(cookieName, cookieValue);
            System.out.println("我是cookie" + cookie);

            cookie.setMaxAge(12 * 60 * 60);
            response.addCookie(cookie);


            //把uuid写入session
            // 取得到对象之后，将对象需要的属性存入到session中，为了以后可能会使用到登录用户的数据。
            SysUser sessionUser = new SysUser(user.getUserId(), user.getUsername(), user.getName(), user.getUserType());

            request.getSession().setAttribute("sessionUser", sessionUser);

            //将sessionUser 放入到 map 中
            map.put("currentUser", sessionUser.toString());


            //将场地id写入session
            //1.查询当前用户的权限,具有操作哪些场地的权限,如果只有一个场地,则默认为该场地

            List<String> placeIds = userPlaceMapper.selectPlaceIdByUserName(username);

            if (placeIds != null && placeIds.size() == 1) {

                request.getSession().setAttribute("placeId", placeIds.get(0));

                System.out.println("我是场地的权限(一个场地,默认): "+placeIds.get(0));
            }

            //获取货主权限

            List<String> gooderCodeList = userGooderMapper.selectGooderCodeByUserName(username);

            if (gooderCodeList!=null && gooderCodeList.size()!=0) {


             //   Object gooderCodes = JSON.toJSON(gooderCodeList);


                request.getSession().setAttribute("gooderCodes", gooderCodeList);

                System.out.println("我是所有货主的权限: "+gooderCodeList.toString());

            }else {
                request.getSession().setAttribute("gooderCodes", null);
            }



            // 设置 session 超时机制
            HttpSession sessionOutTime = request.getSession(true);

            // 将sessionOutTime 的值设置为 12小时
            sessionOutTime.setMaxInactiveInterval(existTime);


            // 将map转换成json给前台
            return JSON.toJSONString(map);

        } catch (Exception e) {
            log.error("登录异常！", e);
        }

        return null;

    }

    /**
     * 实现登出功能
     *
     * @return
     */
    @PostMapping("/out")
    public Result loginOut(HttpServletRequest request) {


        try {
            request.getSession().removeAttribute("sessionUser");

            request.getSession().removeAttribute("placeId");

            request.getSession().removeAttribute("gooderCodes");


        } catch (Exception e) {
            log.error("退出登录异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();

    }


    /**
     * 选择场地
     */
    @RequestMapping(value = "/choosePlace", method = {RequestMethod.GET, RequestMethod.POST})
    public Result choosePlace(@RequestParam String placeId, HttpServletRequest request) {

        if (StringUtils.isBlank(placeId)) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {
            //设置cookie失效时间,12个小时
            int existTime = 12 * 60 * 60;

            request.getSession().setAttribute("placeId", placeId);

            // 设置 session 超时机制
            HttpSession sessionOutTime = request.getSession(true);

            // 将sessionOutTime 的值设置为 12小时
            sessionOutTime.setMaxInactiveInterval(existTime);
        } catch (Exception e) {
            log.error("选择场地失败:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();

    }

}
