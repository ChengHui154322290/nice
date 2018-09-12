package com.nice.good.utils;

import com.nice.good.model.Order;
import com.nice.good.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create By renkuo  2018/04/27
 * 本文件用于创建 PurchaseCode 采购单号规则
 */
public class PurchaseCodeUtils {

    private static Logger log = LoggerFactory.getLogger(PurchaseCodeUtils.class);

    public static String getPurchaseCode(OrderService orderService) {

        // 查询 o_order表 获取最大 id 值所对应的 purchase_code 采购单号规则
        Order order = orderService.findOrderByMaxId();
        String purchaseCodeMsg = null;

        //if (StringUtils.isNotBlank(purchaseCode)) {
        if (order != null) {
            // 获取最大 id 值所对应的 purchase_code(采购单号规则)
            String purchaseCode = order.getPurchaseCode();
            // PO --  第 一部分， 180327 -- 第 二部分， 000005 -- 第 三部分
            // 判断 purchaseCode(采购编号) 是否为空

            purchaseCodeMsg = getMessage(purchaseCode);

        } else {

            String purchaseCode = "PO11032700000";
            purchaseCodeMsg = getMessage(purchaseCode);
        }

        return purchaseCodeMsg;
    }

    public static String getMessage(String purchaseCode) {

        // 对 purchaseCode 值进行截取
        String purchaseCodeSub = purchaseCode.substring(8, purchaseCode.length());

        // 将 purchaseCode 截取的第 3部分转换成 Integer 类型
        Integer thirdNum = new Integer(purchaseCodeSub);
        // 获取 thirdNum 的长度
        Integer thirdNumLen = thirdNum.toString().length();

        // 获取系统当前时间，将其设置为第 2部分字符串
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String secondStr = sf.format(date);
        String secondNum = secondStr.substring(2, secondStr.length());

        // 设置'采购单号规则'的开头
        StringBuffer sb = new StringBuffer("PO");
        sb.append(secondNum);

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
            // 在枚举类型中，设置一个值， 表示： 采购单号超出最大值
            // TRADEID_IS_BEYOND_SETTING("1015", "采购单号超出最大值")
            log.error("生成采购单号purchaseCode失败!");
            return "1015";
        }

    }

}
