package com.nice.good.rfid.mqtt;

import com.nice.good.constant.REDIS_KEY;
import com.nice.good.model.RfidDevice;
import com.nice.good.model.RfidReadRecord;
import com.nice.good.rfid.netty.Client;
import com.nice.good.service.RfidDeviceService;
import com.nice.good.service.RfidReadRecordService;
import com.nice.good.web.WebSocket;
import org.apache.commons.lang3.StringUtils;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

//@Order(1)
//@Component
public class MQTTRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(MQTTRunner.class);

    private static String HOST     = "tcp://192.168.254.200:61613";
    private static String USERNAME = "admin";
    private static String PASSWORD = "123456";

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RfidDeviceService rfidDeviceService;
    @Autowired
    private RfidReadRecordService rfidReadRecordService;
    @Autowired
    private Client client;

    private final static boolean CLEAN_START              = true;
    private final static short   KEEP_ALIVE               = 30;
    // 低耗网络，但是又需要及时获取数据，心跳30s
    private final static long    RECONNECTION_ATTEMPT_MAX = 6;
    private final static long    RECONNECTION_DELAY       = 2000;
    private final static int     SEND_BUFFER_SIZE         = 2 * 1024 * 1024;
    // 发送最大缓冲为2M

//    private static String TOPIC_META = "/"+"iotp"+"/"+"RFID-58-69-6C-58-97-F7"+"/"+"meta_data"+"/"+"RFID"+"/"+"meta_data";
//    private static String TOPIC_META = "/"+"iotp"+"/"+"RFID-58-69-6C-58-97-FE"+"/"+"meta_data"+"/"+"RFID"+"/"+"meta_data";
//    private static String TOPIC_INVENTORY = "/"+"iotp"+"/"+"RFID-58-69-6C-58-97-F7"+"/"+"status_data"+"/"+"RFID"+"/"+"inventory_data1inventory_data1";
//    private static String TOPIC_INVENTORY = "/"+"iotp"+"/"+"RFID-58-69-6C-58-97-FE"+"/"+"status_data"+"/"+"RFID"+"/"+"inventory_data1";
    public static Topic[] topics = {};//{/* new Topic(TOPIC_META, QoS.AT_MOST_ONCE),*/ new Topic(TOPIC_INVENTORY, QoS.AT_MOST_ONCE) };

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("==服务启动后，初始化MQTTRunner==");

        List<RfidDevice> all = rfidDeviceService.findAll();
        System.out.printf("all device:" + all.size());

        Set<String> monitorSet = new HashSet<>();
        Set<String> collectSet = new HashSet<>();
        List<Topic> topicList = new ArrayList<>();
        for (RfidDevice device: all) {
            String rfidCode = device.getRfidCode();
            String topic_inventory = new String("/"+"iotp"+"/"+ rfidCode +"/"+"status_data"+"/"+"RFID"+"/"+"inventory_data1");
            topicList.add(new Topic(topic_inventory, QoS.AT_MOST_ONCE));

            if (0 == device.getType()) {
                collectSet.add(rfidCode);
            }else if (1 == device.getType()) {
                monitorSet.add(rfidCode);
            }
        }

        topics = topicList.toArray(new Topic[topicList.size()]);

//        System.out.println("topic:"+topics[0]);
        MQTT mqtt = new MQTT();
        // 创建MQTT对象
        mqtt.setClientId(UUID.randomUUID().toString());
        // 设置mqtt broker的ip和端口
        mqtt.setHost(HOST);
        mqtt.setUserName(USERNAME);
        mqtt.setPassword(PASSWORD);
        mqtt.setCleanSession(CLEAN_START);
        // 连接前清空会话信息
        mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
        // 设置重新连接的次数
        mqtt.setReconnectDelay(RECONNECTION_DELAY);
        // 设置重连的间隔时间
        mqtt.setKeepAlive(KEEP_ALIVE);
        // 设置心跳时间
        mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
        // 设置缓冲的大小
        CallbackConnection connection = mqtt.callbackConnection();
        // 获取mqtt的连接对象CallbackConnection

        connection.connect(new Callback<Void>() {
            //可以在connect的onSuccess方法中发布或者订阅相应的主题
            @Override
            public void onSuccess(Void oid) {
                //进入该方法表示连接成功连接成功
                System.out.println("连接成功："+oid);
            }
            //onFailure方法中作相应的断开连接等操作
            @Override
            public void onFailure(Throwable cause) {
                //进入该方法表示连接失败
                System.out.println("连接失败");
            }
        });

        connection.listener(new Listener() {
            //表示成功，可以获取到订阅的主题和订阅的内容（UTF8Buffer topicmsg表示订阅的主题,
            //Buffer msg表示订阅的类容），一般的可以在这个方法中获取到订阅的主题和内容然后进行相应的判断和其他业务逻辑操作；
            @Override
            public void onPublish(UTF8Buffer topicmsg, Buffer msg, Runnable ack) {
                //utf-8 is used for dealing with the garbled
                String topic = topicmsg.utf8().toString();
                String payload = msg.utf8().toString();
                String deviceCode = topic.split("/")[1];
//                logger.info("topic:" + topic);
//                logger.info("message:" + payload);
//                logger.info("hex_data:" + msg.hex());
                if (topic.contains("inventory_data1")) {
                    String electGoods = redisTemplate.opsForValue().get(REDIS_KEY.ELECT_GOODS_KEY);

                    //type = 0 采集
                    if (StringUtils.isNotBlank(electGoods) && collectSet.contains(deviceCode)) {
                        List<Map<String, String>> list = MQTTCodcUtil.inventoryCodc(msg.hex());
                        for (Map<String, String> map: list) {
                            String epc = map.get("EPC");
                            if (!redisTemplate.opsForSet().isMember(electGoods, epc)) {
                                redisTemplate.opsForSet().add(electGoods, epc);
                                //sendMessage
                                try {
                                    WebSocket.sendInfo(electGoods+":"+epc+":"+redisTemplate.opsForSet().size(electGoods));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }else if (monitorSet.contains(deviceCode)) {//type = 1 检测
                        List<Map<String, String>> list = MQTTCodcUtil.inventoryCodc(msg.hex());
                        //入redis
                        for (Map<String, String> map: list) {
                            String device = deviceCode;//设备
                            String epc    = map.get("EPC");
                            String antNum = map.get("antNum");
                            String timestamp = map.get("Timestamp");
                            redisTemplate.opsForSet().add(REDIS_KEY.RFID_MONITOR_KEY, device + ":" + antNum + ":" + epc + ":" + timestamp);
                        }
                        //数量 > 1000, 入mysql
                        if (redisTemplate.opsForSet().size(REDIS_KEY.RFID_MONITOR_KEY) >= 1000) {
                            List<RfidReadRecord> recordList = new ArrayList<>();
                            Set<String> recordSet = redisTemplate.opsForSet().members(REDIS_KEY.RFID_MONITOR_KEY);
                            for (String rd: recordSet) {
                                RfidReadRecord record = new RfidReadRecord();
                                record.setRfidCode(rd.split(":")[0]);
                                record.setAntNum(rd.split(":")[1]);
                                record.setRfidLabel(rd.split(":")[2]);
                                record.setCreateId("system");
                                record.setCreateDate(new Date(Long.parseLong(rd.split(":")[3])));
                                recordList.add(record);
                            }
                            rfidReadRecordService.save(recordList);
                        }
                    }
                    //logger.info(electGoods + "##size##" + redisTemplate.opsForSet().size(electGoods));
                } else if (topic.contains("meta_data")) {
                    MQTTCodcUtil.metaData(msg.hex());
                }

                //表示监听成功
                ack.run();
            }
            //表示监听失败，这里可以调用相应的断开连接等方法；
            @Override
            public void onFailure(Throwable arg0) {
                //表示监听失败
            }
            //表示监听到连接建立，该方法只在建立连接成功时执行一次，
            //表示连接成功建立，如果有必要可以在该方法中进行相应的订阅操作；
            @Override
            public void onDisconnected() {
                //表示监听到断开连接
            }
            //表示监听到连接断开，该方法只在断开连接时执行一次，如有必要可以进行相应的资源回收操作。
            @Override
            public void onConnected() {
                //表示监听到连接成功
            }
        });

        connection.subscribe(topics, new Callback<byte[]>() {
            @Override
            public void onSuccess(byte[] qoses) {
                logger.info("mqtt client topic subscribe success!!!");
            }
            @Override
            public void onFailure(Throwable arg0) {
                logger.info("mqtt client topic subscribe fail!!");
            }
        });
        //断开连接
        /*connection.disconnect(new Callback<Void>() {
            public void onSuccess(Void value) {
                //与服务器断开连接成功
            }

            public void onFailure(Throwable value) {
                 //与服务器断开连接失败
            }
        });*/
        //回调将执行与连接相关联的调度队列，以便可以安全使用从回调的连接，但你绝不能在回调中执行任何阻塞操作，否则会改变执行的顺序，
        //这样可能出错。如果可能存在阻塞时，最好是在连接的调度队列中执行另外一个线程
        connection.getDispatchQueue().execute(() -> {
            //在这里进行相应的订阅、发布、停止连接等等操作
        });
        Thread.sleep(6000000);

    }

}
