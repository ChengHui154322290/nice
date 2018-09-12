package com.nice.good.exception;


/**
* @Description:   java类作用描述
* @Author:   fqs
* @Date:  2018/3/24 9:31
* @Version:   1.0
*/
public class IllegalOperateException extends  RuntimeException {
    private static final long serialVersionUID = -5519743897907627214L;

    public IllegalOperateException(String message) {
        super(message);
    }
}
