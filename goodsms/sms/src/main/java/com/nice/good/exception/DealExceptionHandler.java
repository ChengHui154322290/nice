package com.nice.good.exception;


import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.enums.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class DealExceptionHandler {

    @ExceptionHandler(NoLoginException.class)
    public Result noLogin(){
        return ResultGenerator.genFailResult(ResultCode.NOLOGIN);
    }
}
