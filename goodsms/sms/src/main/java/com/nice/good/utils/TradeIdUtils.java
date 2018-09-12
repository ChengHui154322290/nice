package com.nice.good.utils;

import com.nice.good.model.DynamicGood;
import com.nice.good.service.DynamicGoodService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Create By renkuo  2018/04/27
 * 本文件用于创建 TradeId 交易编号规则
 */
public class TradeIdUtils {

    private static Logger log = LoggerFactory.getLogger(TradeIdUtils.class);

    /**
     * 获取 s_dynamic_good 表中，最大 id 值所对应的 tradeCode
     *
     * @return
     */
    public static String getTradeId(DynamicGoodService dynamicGoodService) {

        // 查询 s_dynamic_good 获取最大 id 值所对应的 DynamicGood
        DynamicGood dynamicGood = dynamicGoodService.findDynamicGoodByMaxId();
        String tradeCodeMsg = null;

        // if (StringUtils.isNotBlank(tradeCode)) {
        if (dynamicGood != null) {
            // 获取最大 id 值所对应的 trade_id(流水单号)
            String tradeCode = dynamicGood.getTradeCode();
            // LS -- 第 一部分， 0000001 -- 第 二部分
            // 判断 tradeCode(流水订单号) 是否为空

            tradeCodeMsg = getMessage(tradeCode);

        } else {

            String tradeCode = "LS00000";
            tradeCodeMsg = getMessage(tradeCode);
        }

        return tradeCodeMsg;
    }

    public static String getMessage(String tradeCode) {

        // 对 tradeCode 值进行截取
        String tradeCodeSub = tradeCode.substring(2, tradeCode.length());
        // 将截取后的 tradeCodeSub 转换成 Integer 类型
        Integer tradeCodeNum = new Integer(tradeCodeSub);
        // 获取 tradeCodeNum 的长度
        Integer tradeCodeNumLen = tradeCodeNum.toString().length();
        // 设置流水号的开头
        StringBuffer sb = new StringBuffer("LS");

        if (tradeCodeNumLen < 5) {

            int length = 5 - tradeCodeNumLen;

            for (int i = 0; i < length; i++) {
                sb.append("0");
            }

            tradeCodeNum++;
            sb.append(tradeCodeNum);

            return sb.toString();

        } else if (tradeCodeNumLen == 5) {

            tradeCodeNum++;
            sb.append(tradeCodeNum);

            return sb.toString();

        } else {
            // 在枚举类型中，设置一个值， 表示： 流水单号已经超过最大流水单号
            // TRADEID_IS_BEYOND_SETTING("1014", "流水订单号超出最大值")
            log.error("生成流水订单号tradeCode失败!");
            return "1014";
        }

    }


}
