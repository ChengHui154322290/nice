package com.nice.good.web;

import com.basung.common.command.Command;
import com.basung.common.core.model.Request;
import com.basung.common.core.utils.HexConvertUtils;
import com.nice.good.base.BaseController;
import com.nice.good.constant.REDIS_KEY;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.ReceiveDetailMapper;
import com.nice.good.dao.RfidLabelMapper;
import com.nice.good.rfid.netty.Client;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/rfid/data")
public class RFIDController extends BaseController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private Client client;

    private static boolean flag = true;

    @PostMapping("/elect")
    public Result elect(@RequestParam(value = "elect", defaultValue = "") String elect) {
        Set<String> members = new HashSet<>();
        String key = redisTemplate.opsForValue().get(REDIS_KEY.ELECT_GOODS_KEY);
        if (StringUtils.isNotBlank(key) && !key.equals(elect)) {
            redisTemplate.delete(key);
        }

        redisTemplate.opsForValue().set(REDIS_KEY.ELECT_GOODS_KEY, elect);


        if (StringUtils.isNotBlank(elect)) {
            //获取货品所对应的rfid标签
            members = redisTemplate.opsForSet().members(elect);
        }

        flag = true;
        Thread thread = new Thread(() -> {
            while (flag&&(!Thread.currentThread().isInterrupted())) {
                try {
//                    log.info("scan...");
                    Request request = Request.valueOf(Command.CMD_INVENTORY_ONCE, null);
                    client.sendRequest(request);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        });
        thread.start();
        return ResultGenerator.genSuccessResult(new ArrayList<>(members));
    }

    @PostMapping("/stop")
    public Result stop() {
        flag = false;
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/transfer")
    public Result transfer(@RequestParam(value = "epc") String epc) {
        String electGoods = redisTemplate.opsForValue().get(REDIS_KEY.ELECT_GOODS_KEY);

//        String epcData = HexConvertUtils.bytesToHexString(epc);
        if (StringUtils.isNotBlank(electGoods)) {
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
        return ResultGenerator.genSuccessResult();
    }
}
