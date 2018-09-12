package com.nice.good.base;


import com.nice.good.exception.NoLoginException;
import com.nice.good.model.SysUser;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 从session中获取用户信息
 */

public class BaseController {

    /**
     * 获取当前用户所有信息
     *
     * @param request
     * @return
     */

    public SysUser getUserMessage(HttpServletRequest request) {

        SysUser sessionUser = (SysUser) request.getSession().getAttribute("sessionUser");

//        sessionUser =new SysUser();
//       sessionUser.setUsername("admin");

        if (sessionUser == null) {
            throw new NoLoginException();
        }

        return sessionUser;

    }

    /**
     * 获取当前用户userId
     * @param request
     * @return
     */

    public String getUserId(HttpServletRequest request){

        SysUser sessionUser =(SysUser) request.getSession().getAttribute("sessionUser");


        if (sessionUser==null){
            throw new NoLoginException();
        }
      //  sessionUser.setUserId("0101");

        return  sessionUser.getUserId();
    }

    /**
     * 获取当前用户名
     */
    public String getUserName(HttpServletRequest request) {

        //获取cookie
//        String cookieName="tokenId";
//
//        String cookieValue = null;
//
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals(cookieName)) {
//                cookieValue = cookie.getValue();
//            }
//        }
//
//        if (cookieValue==null){
//            throw new NoLoginException();
//        }
//
//
        SysUser sessionUser = (SysUser) request.getSession().getAttribute("sessionUser");
//
//        SysUser sessionUser =new SysUser();
//       sessionUser.setUsername("admin");



        if (sessionUser == null) {
            throw new NoLoginException();
        }


        return sessionUser.getUsername();
    }

    /**
     * 获取当前用户姓名
     */
    public String getName(HttpServletRequest request) {

        SysUser sessionUser = (SysUser) request.getSession().getAttribute("sessionUser");

//        SysUser  sessionUser =new SysUser();
//        sessionUser.setUsername("admin");

        if (sessionUser == null) {
            throw new NoLoginException();
        }


        return sessionUser.getName();
    }

    /**
     * 获取place_id
     */

    public String getPlaceId(HttpServletRequest request) {

        String placeId = (String) request.getSession().getAttribute("placeId");

     //   String placeId = "0eb030b0-4716-4aad-9939-0fb98c94972a";

        return placeId;
    }

    /**
     * 获取企业id
     */
    public String getCompanyId(HttpServletRequest request) {

        String companyId = (String) request.getSession().getAttribute("companyId");

        return companyId;
    }

    /**
     * 获取货主权限
     */

    public List<String> getGooderCodes(HttpServletRequest request) {

       Object GooderCodes = request.getSession().getAttribute("gooderCodes");

        if (GooderCodes == null) {
            return null;
        }

        List gooderCodes = (ArrayList<String>) GooderCodes;

//        List<String> gooderCodes = new ArrayList();
//
//        gooderCodes.add("SWBYHWH001");
//        gooderCodes.add("ZYL1111584");
//        gooderCodes.add("ceshi001");
//        gooderCodes.add("ZYL111222");
//        gooderCodes.add("ZYL11");
//        gooderCodes.add("ZYL111");


        return gooderCodes;
    }

    /**
     * 获取用户类型
     */

    public String getUserType(HttpServletRequest request) {


        SysUser sessionUser = (SysUser) request.getSession().getAttribute("sessionUser");

        if (sessionUser == null) {
            throw new NoLoginException();
        }

        return sessionUser.getUserType();

    }
}
