package com.nice.good.utils;

import java.text.SimpleDateFormat;

public class PictureIdUtils {

    /**
     * 图片id 生成规则图片
     *
     * @return
     */
   public static  Long getImgId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String str = sdf.format(System.currentTimeMillis()) + ((int) (Math.random() * 900000) + 100000);

        return Long.valueOf(str);
    }
}
