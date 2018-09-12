package com.nice.good.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
* @Description:   全局异常
* @Author:   fqs
* @Date:  2018/4/23 15:35
* @Version:   1.0
*/
//@ControllerAdvice
//public class ExceptionResolver implements HandlerExceptionResolver {
//    @Override
//    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
//                                         Exception ex) {
//        //该方法的的参数会自动获取 请求（request），响应（response），异常（exception），handler（异常参数的处理器 Controller）
//        ModelAndView mav = new ModelAndView();
//
//        if(ex instanceof NoLoginException){
//            //用户异常
//            mav.addObject("message", ex.getMessage());
//            mav.setViewName("index.html");
//        }else{
////            mav.addObject("message", "系统出现异常，请稍后访问");
////            mav.setViewName("error");
//        }
//        return mav;
//    }
//}