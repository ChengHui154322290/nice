package com.nice.good.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConversionUtils {

    private static Logger log = LoggerFactory.getLogger(ConversionUtils.class);

    /**
     * 将 字符串 转换为 int 类型
     *
     * @param str
     * @return
     */
    public static int StringToInt(String str) {

        int num = 0;

        try {

            switch (str) {
                case "one":
                    num = 1;
                    break;
                case "two":
                    num = 2;
                    break;
                case "three":
                    num = 3;
                    break;
                case "four":
                    num = 4;
                    break;
                case "five":
                    num = 5;
                    break;
                case "six":
                    num = 6;
                    break;
                case "seven":
                    num = 7;
                    break;
                case "eight":
                    num = 8;
                    break;

                case "nine":
                    num = 9;
                    break;
                default:
                    log.error("转换异常!");
                    break;
            }

        } catch (Exception e) {
            log.error("转换异常!");
        }

        return num;
    }

    public static String IntToString(Integer num) {

        String first = null;

        try {

            switch (num) {
                case 1:
                    first = "one";
                    break;
                case 2:
                    first = "two";
                    break;
                case 3:
                    first = "three";
                    break;
                case 4:
                    first = "four";
                    break;
                case 5:
                    first = "five";
                    break;
                case 6:
                    first = "six";
                    break;
                case 7:
                    first = "seven";
                    break;
                case 8:
                    first = "eight";
                    break;
                case 9:
                    first = "nine";
                    break;
                default:
                    log.error("转换异常!");
                    break;
            }

        } catch (Exception e) {
            log.error("转换异常!");
        }

        return first;
    }


}
