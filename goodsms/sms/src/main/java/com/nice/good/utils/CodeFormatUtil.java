package com.nice.good.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CodeFormatUtil {

    public static String formatCode(Integer id){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()).substring(2, 8) + String.format("%05d", id);
    }
    public static String formatCode(String prefix ,Integer id){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if("JL".equals(prefix)){
            return prefix + sdf.format(new Date()).substring(0, 8) + String.format("%05d", id);
        }else if("PQ".equals(prefix)){
            return prefix + sdf.format(new Date()).substring(0, 8) + String.format("%04d", id);
        }else{
            return sdf.format(new Date()).substring(0, 8) + String.format("%05d", id);
        }
    }
}
