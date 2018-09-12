package com.nice.good.rfid.mqtt;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MQTTCodcUtil {

    public static List<Map<String, String>> inventoryCodc(String hex){
        List<Map<String, String>> list = new ArrayList<>();
        do {
            Integer x = Integer.parseInt(hex.substring(14, 16),16) * 2;
            Map map = inventoryDate(hex.substring(0, x+16+2));
            list.add(map);
//            System.out.println(hexStr.substring(0, x+16+2));
//            System.out.println(map);
            hex = hex.substring(x+16+2);
//            System.out.println(hexStr);
        } while (hex.length() > 0);

        return list;
    }

    private static Map<String, String> inventoryDate(String hex){
        HashMap<String, String> meta = new HashMap<>();

        int end;
        Integer x = Integer.parseInt(hex.substring(14, 16),16) * 2;
        meta.put("deviceType", String.valueOf(Integer.parseInt(hex.substring(0, end = 2), 16)));
        meta.put("RSSI",       String.valueOf(Integer.parseInt(hex.substring(end, end += 2), 16)));
        meta.put("antNum",     String.valueOf(Integer.parseInt(hex.substring(end, end += 2))));
        meta.put("Timestamp",  String.valueOf(Integer.parseInt(hex.substring(end, end += 8),16)));
        meta.put("EPCLength",  hex.substring(end, end += 2));
        meta.put("EPC",        hex.substring(end, end += x));
        meta.put("GPIO",       String.valueOf(Integer.parseInt(hex.substring(end, end + 2), 16)));

        log.info("rfid-data: "+meta);
        return meta;
    }


    public static Map<String, String> metaData(String hex){
        HashMap<String, String> meta = new HashMap<>();

        int end;
        //RFID阅读器固定为9
        meta.put("deviceType", String.valueOf(Integer.parseInt(hex.substring(0, end = 2), 16)));
        //设备IP地址
        int ipAddrLength = Integer.valueOf(hex.substring(end, end += 2), 16);
        meta.put("ipAddr", convertHexToString(hex.substring(end, end += ipAddrLength * 2)));
        //设备MAC地址
        int macAddrLength = Integer.valueOf(hex.substring(end, end += 2), 16);
        meta.put("macAddr", convertHexToString(hex.substring(end, end += macAddrLength *2)));
        //设备串号
        int seriaNumLength = Integer.valueOf(hex.substring(end, end += 2), 16);
        meta.put("seriaNum", convertHexToString(hex.substring(end, end += seriaNumLength *2)));
        //RFID阅读器的MCU软件版本号
        int softVerLength = Integer.valueOf(hex.substring(end, end += 2), 16);
        meta.put("softVer", convertHexToString(hex.substring(end, end += softVerLength *2)));
        //
        int handVerLength = Integer.valueOf(hex.substring(end, end += 2), 16);
        meta.put("handVer", convertHexToString(hex.substring(end, end += handVerLength *2)));
        //RFID阅读器的MCU软件序列号
        int softNumLength = Integer.valueOf(hex.substring(end, end += 2), 16);
        meta.put("softNum", convertHexToString(hex.substring(end, end += softNumLength *2)));
        //RFID阅读器的rfid模组软件版本号
        int rfidSoftVerLength = Integer.valueOf(hex.substring(end, end += 2), 16);
        meta.put("rfidSoftVer", convertHexToString(hex.substring(end, end += rfidSoftVerLength *2)));
        //RFID阅读器的esp模组软件版本号
        int espSoftVerLength = Integer.valueOf(hex.substring(end, end += 2), 16);
        meta.put("espSoftVer", convertHexToString(hex.substring(end, end += espSoftVerLength *2)));
        //设备总的天线数量
        meta.put("antCnt",    String.valueOf(Integer.valueOf(hex.substring(end, end += 2))));
        //设备型号
        int productIdLenght = Integer.valueOf(hex.substring(end, end += 2), 16);
        meta.put("productId", convertHexToString(hex.substring(end, end += productIdLenght *2)));
        //时间戳
        meta.put("timestamp", String.valueOf(Integer.valueOf(hex.substring(end), 16)));

        return meta;
    }

    public static Map<String, String> warningData(String hex) {
        HashMap<String, String> warning = new HashMap<>();

        int end;
        warning.put("deviceType", String.valueOf(Integer.parseInt(hex.substring(0, end = 2), 16)));
        warning.put("modId", String.valueOf(Integer.parseInt(hex.substring(end, end += 2), 16)));
        warning.put("warningLevel", String.valueOf(Integer.parseInt(hex.substring(end, end += 2), 16)));
        int messageLenght = Integer.valueOf(hex.substring(end, end += 2), 16);
        warning.put("message", convertHexToString(hex.substring(end, end + messageLenght *2)));

        return warning;
    }

    private static String convertHexToString(String hex){
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for( int i=0; i<hex.length()-1; i+=2 ){
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char)decimal);
            temp.append(decimal);
        }
        return sb.toString();
    }
}
