package com.nice.good.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nice.good.constant.ID_PREFIX;

public class IdsUtils {
	private static Logger log = LoggerFactory.getLogger(IdsUtils.class);

	/**
	 * 获取订单号,供后台数据保存
	 * @return
	 * @throws Exception 
	 * .
	 */
//	public static String getOrderId() throws Exception {
//		String orderId = "";
//		try {
//			orderId = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//			orderId += getRandomString(6);
//		}catch(Exception e) {
//			String error = "生成内部订单id失败";
//			log.error(error);
//			throw new Exception();
//		}
//		return orderId;
//
//	}
//
//    public static String getRandomString(int length) {
//        //随机字符串的随机字符库
//        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        StringBuffer sb = new StringBuffer();
//        int len = KeyString.length();
//        for (int i = 0; i < length; i++) {
//            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
//        }
//        return sb.toString();
//    }

    public static String getOrderId(){

        return UUID.randomUUID().toString();
    }

	
}
