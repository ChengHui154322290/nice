package com.nice.miniprogram.utils;

import java.sql.Timestamp;

public class TimeStampUtils {
    public static Timestamp getTimeStamp(){
        return new Timestamp(System.currentTimeMillis());
    }
}
