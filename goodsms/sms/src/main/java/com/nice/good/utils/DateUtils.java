package com.nice.good.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date stringToDate(String strDate) throws Exception {

        // 将传递进来的字符串进行截取
        String strSub = strDate.substring(0, 19);
        // 设置时间格式样式
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        // 将 字符串 转换成 Date 类型
        Date date = df.parse(strSub);

        return date;
    }

}
