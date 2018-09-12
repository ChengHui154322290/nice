package com.nice.miniprogram.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WebSocketAction {

    @MessageMapping("/sendTest")
    @SendTo("/topic/subscribeTest")
    public String sendDemo(String message) {
        log.info("接收到了信息XXXXX");
        return "你发送的消息为:XXXX";
    }

    @SubscribeMapping("/subscribeTest")
    public String sub() {
        log.info("XXX用户订阅了我。。。");
        return "感谢你订阅了我。。。";
    }

    //调用方式
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    //客户端只要订阅了/topic/subscribeTest主题，调用这个方法即可
//    public void templateTest() {
//        messagingTemplate.convertAndSend("/topic/subscribeTest", new ServerMessage("服务器主动推的数据"));
//    }
}
