package com.nice.good.utils;

import com.nice.good.model.OperateLog;
import com.nice.good.model.SysUser;

import javax.servlet.http.HttpServletRequest;

public class LoggerUtil {
    public static final String CONTENT = "content";
    public static final String LOG_TYPE = "logType";

    public LoggerUtil() {
    }

    public static OperateLog getLog() {

        OperateLog operateLog = new OperateLog();
        operateLog.setContent("content");
        operateLog.setLogType("logType");

        return operateLog;
    }

}