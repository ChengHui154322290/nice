package com.nice.good.utils;
import java.util.Random;
/**
* @Description:   id随机数生成
* @Author:   fqs
* @Date:  2018/3/22 17:17
* @Version:   1.0
*/
public class RandomIdUtils {

    public  static String  getRandomNum(){

        return  String.format("%04d",new Random().nextInt(10000));

    }
}
