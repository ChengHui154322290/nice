package com.nice.good.web;


import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.DynamicPermissionMapper;
import com.nice.good.dao.SysUserMapper;
import com.nice.good.dao.UserRoleMapper;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.DynamicPermission;
import com.nice.good.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pda/login")
public class PdaLoginController {

    private static Logger log = LoggerFactory.getLogger(PdaLoginController.class);


    @Autowired
    private SysUserMapper sysUserMapper;


    @Autowired
    private DynamicPermissionMapper dynamicPermissionMapper;


    @PostMapping("/in")
    public Result pdaLogin(@RequestParam(required = true) String username, @RequestParam(required = true) String password, HttpServletRequest request, HttpServletResponse response) {

        //这里还需要判断用户是否有操作pda的权限,这里根据用户是否有收货和订单管理的权限

        String userId = sysUserMapper.checkIdByUsername(username);

        if (StringUtils.isBlank(userId)) {
            return ResultGenerator.genFailResult(ResultCode.USERNAME_NOT_EXIST);
        }

        SysUser user = sysUserMapper.login(username, password);
        if (user == null) {
            return ResultGenerator.genFailResult(ResultCode.PASSWORD_IS_WORONG);
        }

        //收货权限
        String keyId1 = dynamicPermissionMapper.selectReceivePermission(username);

        //拣货权限
        String keyId2 = dynamicPermissionMapper.selectPickPermission(username);


        if (StringUtils.isBlank(keyId1) && StringUtils.isBlank(keyId2)) {
            return ResultGenerator.genFailResult(ResultCode.PERMISSION_NOT_HAS);
        }

        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(keyId1)) {
            list.add("收货");
        }

        if (StringUtils.isNotBlank(keyId2)) {
            list.add("拣货");
        }


        //向cookie中写入token
        String tokenId = UUID.randomUUID().toString();

        Cookie cookie = new Cookie("tokenId", tokenId);

        cookie.setMaxAge(60 * 60);

        response.addCookie(cookie);

        //把用户信息写入session
        request.getSession().setAttribute(tokenId, user);


        return ResultGenerator.genSuccessResult().setData(list);
    }

    @PostMapping("/out")
    public Result pdaOut(HttpServletRequest request) {
        //清空session
        request.getSession().invalidate();

        return ResultGenerator.genSuccessResult();

    }


}
