/*
package com.nice.miniprogram.aop;


import com.nice.miniprogram.model.User;
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


    @Pointcut("execution(public * com.nice.miniprogram.api.*.*(..))" +
            "&& !execution(public * com.nice.miniprogram.api.UserController.*(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doVerify() throws Exception {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        User sessionUser = (User) request.getSession().getAttribute("user");

//        sessionUser =new SysUser();
//        sessionUser.setUsername("admin");
//        sessionUser.setUserId("0101");

        if (sessionUser == null) {
            throw new Exception();
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
*/
