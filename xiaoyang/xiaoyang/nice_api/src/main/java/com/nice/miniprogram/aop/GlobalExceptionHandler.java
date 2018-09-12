package com.nice.miniprogram.aop;


import com.nice.miniprogram.core.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.nice.miniprogram.enums.ResultCode.INTERNAL_SERVER_ERROR;
import static com.nice.miniprogram.enums.ResultCode.NOT_FOUND;

@ControllerAdvice(annotations = {Controller.class})
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("", e);
        Result baseResp = new Result();
        baseResp.setMessage(e.toString());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            baseResp.setCode(NOT_FOUND.getCode()).setMessage(NOT_FOUND.getMessage());//404

        } else {
            baseResp.setCode(INTERNAL_SERVER_ERROR.getCode()).setMessage(INTERNAL_SERVER_ERROR.getMessage());//500
        }
        baseResp.setData("");
        return baseResp;
    }
}
