package com.nice.good.utils;

import org.apache.commons.lang3.StringUtils;

public class ErrorMsg {

    public static String getString(String errorMsg, String errorMsg1, String str) {
        if (StringUtils.isNotBlank(errorMsg1)){
            errorMsg1="第 " + errorMsg1.substring(0, errorMsg1.lastIndexOf(",")) + "条记录"+str+ "，操作不成功!";
            if (StringUtils.isNotBlank(errorMsg)){
                errorMsg+=";\n"+errorMsg1;
            }else {
                errorMsg += errorMsg1;
            }

        }
        return errorMsg;
    }
}
