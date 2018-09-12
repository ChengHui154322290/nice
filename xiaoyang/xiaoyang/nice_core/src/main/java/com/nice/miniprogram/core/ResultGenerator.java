package com.nice.miniprogram.core;


import com.nice.miniprogram.enums.ResultCode;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功!";
    private static final String DEFAULT_FAIL_MESSAGE    = "操作失败!";

    public static Result genSuccessResult() {
        return new Result().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE)
            .setData(data);
    }


    public static Result genFailResult(ResultCode code) {
        return new Result().setCode(code.getCode()).setMessage(code.getMessage());
    }

    public static Result genFailResult(Object data) {
        return new Result().setCode(ResultCode.FAIL).setMessage(DEFAULT_FAIL_MESSAGE).setData(data);
    }

    public static Result genFailResult(ResultCode code, Object data) {
        return new Result().setCode(code.getCode()).setMessage(code.getMessage()).setData(data);
    }
}
