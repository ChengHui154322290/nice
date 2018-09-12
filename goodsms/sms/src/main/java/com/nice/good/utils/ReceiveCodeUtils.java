package com.nice.good.utils;

import com.nice.good.model.ReceiveOrder;
import com.nice.good.service.ReceiveOrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create By renkuo  2018/05/02
 * 本文件用于创建 ReceiveCode ASN/收货单号规则
 */
public class ReceiveCodeUtils {

    private static Logger log = LoggerFactory.getLogger(ReceiveCodeUtils.class);

    public static String getReceiveCode(ReceiveOrderService receiveOrderService) {

        // 查询 o_receive_order表 获取最大 id 值所对应的 receive_code 收货单编号规则
        ReceiveOrder receiveOrder = receiveOrderService.findReceiveOrderByMaxId();
        String receiveCodeMsg = null;

        //if (StringUtils.isNotBlank(receiveCode)) {
        if (receiveOrder != null) {

            // 获取最大 id 值所对应的 receive_code(收货单号规则)
            String receiveCode = receiveOrder.getReceiveCode();

            // ASN --  第 一部分， 180919 -- 第 二部分， 000108 -- 第 三部分
            // 判断 receiveCode(收货单编号) 是否为空

            receiveCodeMsg = getMessage(receiveCode);

        } else {
            String receiveCode = "ASN18091900000";
            receiveCodeMsg = getMessage(receiveCode);
        }

        return receiveCodeMsg;
    }


    public static String getMessage(String receiveCode) {

        // 对 receiveCode 值进行截取
        // ASN180919 000000
        String receiveCodeSub = receiveCode.substring(9, receiveCode.length());

        // 将 receiveCode 截取的第 3部分转换成 Integer 类型
        Integer thirdNum = new Integer(receiveCodeSub);

        // 获取 thirdNum 的长度
        Integer thirdNumLen = thirdNum.toString().length();

        // 获取系统当前时间，将其设置为第 2部分字符串
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String secondStr = sf.format(date);
        String secondNum = secondStr.substring(2, secondStr.length());
        // System.out.println("第 二部分时间为：" + secondNum);

        // 设置'收货单编号规则'的开头
        StringBuffer sb = new StringBuffer("ASN");
        sb.append(secondNum);
        // System.out.println("前2部分的时间为：" + sb.toString());

        if (thirdNumLen < 5) {

            int length = 5 - thirdNumLen;

            for (int i = 0; i < length; i++) {
                sb.append("0");
            }

            thirdNum++;
            sb.append(thirdNum);

            return sb.toString();

        } else if (thirdNumLen == 5) {

            thirdNum++;
            sb.append(secondNum).append(thirdNum);
            return sb.toString();

        } else {
            // 在枚举类型中，设置一个值， 表示： 收货单编号生成异常
            // RECEIVECODE_IS_ERROR("1016", "收货单编号生成异常")
            log.error("收货单编号receiveCode生成失败!");
            return "1016";
        }

    }


}
