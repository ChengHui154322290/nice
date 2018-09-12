package com.nice.good.utils;


import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class UserDesktopPath {


    /**获取当前用户桌面路径
     *
     */
    public static String  getUserDesktopPath(){

        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();
        System.out.println("当前用户桌面: "+com.getPath());

        return com.getPath();
    }
}
