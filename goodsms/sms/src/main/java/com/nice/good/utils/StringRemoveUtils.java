package com.nice.good.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringRemoveUtils {

    private static Logger log = LoggerFactory.getLogger(StringRemoveUtils.class);

    /**
     * 去除字符串中的 '['、 ']' 两个字符
     * @param str
     * @return
     */
    public static String removeChar(String str) {

        String result = null;

        try {

            String testA = StringUtils.remove(str, '[');
            result = StringUtils.remove(testA, ']');

        } catch (Exception e) {
            log.error("去除字符'[' 、']'异常!");
        }

        return  result;
    }

}
