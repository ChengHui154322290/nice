package com.nice.good.rfid.netty;

import com.basung.common.core.utils.HexConvertUtils;
import com.nice.good.constant.REDIS_KEY;
import com.nice.good.web.WebSocket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;


@Component
public class CodcUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void inventoryOnce(byte[] data) {
        byte[] endByte = {(byte) 0xFF, (byte) 0xFF, 0x00};
        if (Arrays.equals(new byte[]{0x00}, data)) {
            System.out.println("start...");
        } else if (Arrays.equals(endByte, data)){
            System.out.println("end...");
        } else if (data.length == 3) {
            System.out.println("当前天线 " + (data[2]==0x00?1:2) + " 盘存结束,盘存数据 " + (data[0] + data[1]*256) + " 笔!");
        } else {
            System.out.println("接收的数据:" + HexConvertUtils.bytesToHexString(data));
            byte   length = data[0];
            byte   rssi   = data[1];
            byte[] pc     = Arrays.copyOfRange(data, 2,4);
            byte[] epc    = Arrays.copyOfRange(data, 4,data.length-1);
            byte   aerial = data[data.length-1];

            String electGoods = redisTemplate.opsForValue().get(REDIS_KEY.ELECT_GOODS_KEY);

            String epcData = HexConvertUtils.bytesToHexString(epc);
            if (StringUtils.isNotBlank(electGoods)) {
                if (!redisTemplate.opsForSet().isMember(electGoods, epcData)) {
                    redisTemplate.opsForSet().add(electGoods, epcData);
                    //sendMessage
                    try {
                        WebSocket.sendInfo(electGoods+":"+epcData+":"+redisTemplate.opsForSet().size(electGoods));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
