package com.nice.miniprogram.api;

import com.nice.miniprogram.model.User;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    public Integer getUserId(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        return user.getId();
    }
    public String getUserName(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        return user.getAccount();
    }
}
