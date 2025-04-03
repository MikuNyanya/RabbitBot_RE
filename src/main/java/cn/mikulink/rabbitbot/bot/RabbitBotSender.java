package cn.mikulink.rabbitbot.bot;

import cn.hutool.http.*;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChainData;
import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2025/4/2 13:00
 * for the Reisen
 * 兔叽消息发送组件
 */
@Component
public class RabbitBotSender {

    public void sendGroupMessage(Long groupId, MessageChain message) {
        sendGroupMessage(groupId, Arrays.asList(message));
    }

    public void sendGroupMessage(Long groupId, List<MessageChain> messageChains) {
        Map<String,Object> param = new HashMap<>();
        param.put("group_id",groupId);
        param.put("message",messageChains);

        //发送到qq
        HttpRequest httpRequest = HttpUtil.createPost("http://localhost:31011/send_group_msg");
        httpRequest.contentType(ContentType.JSON.getValue());

        System.out.println(JSON.toJSONString(param));

        HttpResponse response = httpRequest.timeout(HttpGlobalConfig.getTimeout()).body(JSON.toJSONString(param)).execute();
        String responseBody = response.body();
        System.out.println("发送消息回执：" + responseBody);
    }

    //纯文本
    public MessageChain parseMessageChainText(String type,String text){
        MessageChain messageChain = new MessageChain();
        messageChain.setType(type);
        messageChain.setData(new MessageChainData(text));

        return messageChain;
    }
}
