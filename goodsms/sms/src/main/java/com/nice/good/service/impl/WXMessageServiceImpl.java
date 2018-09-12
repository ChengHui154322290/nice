package com.nice.good.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nice.good.service.UserService;
import com.nice.good.service.WXMessageService;
import com.nice.good.utils.HttpUtil;
import com.nice.good.wx_model.User;
import com.nice.good.wx_web.BespeakOrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;



@Service
@Transactional
public class WXMessageServiceImpl implements WXMessageService {
    private static Logger log = LoggerFactory.getLogger(WXMessageServiceImpl.class);
//    private String ACCESS_TOKEN = "";


    /** ======================== test ========================= */
//    private final String AppID = "wx52b91795cbdab5b5";
//    private final String AppSecret = "cd070f299bdee837e9883779f64cbd65";

    /**  ============================ server ============================== */
	private final String AppID = "wxc1137947f18d078c";
	private final String AppSecret = "7bd57909f01d1b59edcf6568ee347cd8";

    @Resource
    private UserService userService;

    @Override
    public String pushMassege(Integer ownId,Map<String,Object> dataMap,int type){
        String formId = "";
        String templateId = "";
        if(type==1) {
            formId = "formId1";     //表单提交场景下，为 submit 事件带上的 formId；
            templateId = "ZWFvTYrIzDNo1ghv8XKRXNm4oevuQzIJlgKucl8YMGE"; // 订单预约提醒
            log.info("================================== 推送消息模板id为："+templateId+" =====================================");
        }else if(type==2) {
            formId = "formId2";     //表单提交场景下，为 submit 事件带上的 formId；
            templateId = "FWwokGXFX0oAvVoxx6T_2-TDopdgNqLCN4gZKs8z0tk"; // 预约取消通知
            log.info("================================== 推送消息模板id为："+templateId+" =====================================");
        }else if(type==3){
            formId = "formId3";     //表单提交场景下，为 submit 事件带上的 formId；
            templateId = "ORLZ26Rzq4aXdNS53r2vP2uLCW5vNDRs47eVy0FsdBc"; // 预约处理提醒
            log.info("================================== 推送消息模板id为："+templateId+" =====================================");
        }
        String accessToken = getAccessToken();
        log.info("================================== accessToken为："+accessToken+" =====================================");
        User user = userService.getUser(ownId);
        String openid = user.getOpenId();//用户openid
//        String openid = "";//用户openid
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+accessToken;  // 小程序发送模板的消息接口地址
//        Map<String,Object> messageMap = new HashMap<>(); //消息数据
//        messageMap.put("value","测试数据一");
//        messageMap.put("color","#173177");
//        Map<String,Object> dataMap = new HashMap<>(); // 消息内容
//        dataMap.put("keyword1",messageMap);
//        dataMap.put("keyword2",messageMap);
//        dataMap.put("keyword3",messageMap);
//        dataMap.put("keyword4",messageMap);
//        dataMap.put("keyword5",messageMap);

        Map<String,Object> map = new HashMap<>(); // 消息接口参数
        map.put("access_token", accessToken);
        map.put("touser", openid);
        map.put("template_id", templateId);
        map.put("form_id", formId );
//        map.put("page", "pages/index/index");
        map.put("data", dataMap );
        map.put("emphasis_keyword", "keyword1.DATA");//模板需要放大的关键词，不填则默认无放大

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, map, String.class);
        String body = responseEntity.getBody();
        log.info("========================================== 消息推送，返回： "+body+" ====================================================");
        return body;
    }

    /**
     * 获取access_token
     * @return
     */
    private String getAccessToken(){
        String urlForToken = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String,Object> requestUrlParam = new HashMap<>();
        requestUrlParam.put("appid", AppID );  //开发者设置中的appId
        requestUrlParam.put("secret", AppSecret); //开发者设置中的appSecret
        requestUrlParam.put("grant_type", "client_credential");    //默认参数
        JSONObject jsonObject = JSON.parseObject(HttpUtil.sendPost(urlForToken, requestUrlParam));
        String accessToken = jsonObject.get("access_token").toString();
        return accessToken;
    }
}
