package com.nice.good.utils;


import com.nice.good.constant.FILE_PATH;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

@Slf4j
public class DelPicutre {

    public static void deleteImg(List<String> fileNames) {

        try {
            String datapath = FILE_PATH.IMG_PATH;

            File file = new File(datapath);
            if (!file.exists()) {
                return;
            }

            if (!file.isDirectory()) {
                return;
            }

            if (fileNames == null || fileNames.size() == 0) {
                return;
            }

            for (String fileName : fileNames) {

                String[] pictures = file.list();

                if (pictures.length == 0) {
                    return;
                }
                File temp = null;
                for (int i = 0; i < pictures.length; i++) {
                    if (pictures[i].equals(fileName)) {
                        temp = new File(datapath + pictures[i]);
                    }
                    if (temp != null && temp.isFile()) {
                        System.out.println("我是当前删除的图片：" + datapath + pictures[i]);
                        temp.delete();
                        break;
                    }

                }
            }
        } catch (Exception e) {
            log.error("图片删除失败e:{}", e);
        }
    }


}
