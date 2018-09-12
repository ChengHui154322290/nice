package com.nice.good.aop;


import com.nice.good.exception.NoLoginException;
import com.nice.good.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class LoginAspect {


    @Pointcut("execution(public * com.nice.good.web.*.*(..))" +
            "&& !execution(public * com.nice.good.web.SysLoginController.*(..))" +
            "&& !execution(public * com.nice.good.web.WebSocket.*(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doVerify() {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();


        //获取cookie
//        String cookieName = "tokenId";
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
//
//        System.out.println("============================================");
//
//
//        System.out.println("我是cookieValue: " + cookieValue);
//
//        System.out.println("我是request: " + request);
//
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
//
//        if (cookieValue == null) {
//            throw new NoLoginException();
//        }

        SysUser sessionUser = (SysUser) request.getSession().getAttribute("sessionUser");

//        sessionUser =new SysUser();
//        sessionUser.setUsername("admin");
//        sessionUser.setUserId("0101");

        if (sessionUser == null) {
            throw new NoLoginException();
        }
    }
}

//    @Around("verify()")
//    public void around(ProceedingJoinPoint pjp) throws Throwable{
//
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        SysUser currentUser = (SysUser) request.getSession().getAttribute("currentUser");
//
//        if (currentUser==null) {
//          //  ServletActionContext.getResponse().sendRedirect(登陆页面);
//           throw new NoLoginException();
//        }
//
//        pjp. proceed();
//    }

//}
