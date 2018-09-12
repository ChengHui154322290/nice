package com.nice.good.utils;

import java.util.Calendar;
import java.util.Date;

public class DateCutUtils {

    private static Calendar calendar = Calendar.getInstance();


    /**
     * 获取指定时间的年份
     * @param date 指定时间
     * @return 年份
     */
    public static int getYear(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取指定时间的月份
     * @param date 指定时间
     * @return 月份
     */
    public static int getMonth(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }

    /**
     * 获取指定时间的日期
     * @param date 指定时间
     * @return 日期
     */
    public static int getDay(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定时间的小时
     * @param date 指定时间
     * @return 小时
     */
    public static int getHour(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定时间的分钟
     * @param date 指定时间
     * @return 分钟
     */
    public static int getMinute(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取指定时间的秒
     * @param date 指定时间
     * @return 秒
     */
    public static int getSecond(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

}
