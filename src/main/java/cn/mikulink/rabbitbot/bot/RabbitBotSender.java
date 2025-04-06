package cn.mikulink.rabbitbot.bot;

import cn.hutool.http.*;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2025/4/2 13:00
 * for the Reisen
 * 兔叽消息发送组件
 */
@Slf4j
@Component
public class RabbitBotSender {

    /**
     * 发送私聊消息
     *
     * @param messageInfo 准备发送的消息对象
     */
    public void sendPrivateMessage(MessageInfo messageInfo) {
        sendPrivateMessage(messageInfo.getUserId(), messageInfo.getMessage());
    }

    /**
     * 发送私聊消息
     *
     * @param userId  发送目标的q号
     * @param message 准备发送的单个消息内容
     */
    public void sendPrivateMessage(Long userId, MessageChain message) {
        sendPrivateMessage(userId, List.of(message));
    }

    /**
     * 发送私聊消息
     *
     * @param userId        发送目标的q号
     * @param messageChains 准备发送的完整消息链
     */
    public void sendPrivateMessage(Long userId, List<MessageChain> messageChains) {
        if (null == userId) {
            log.warn("发送私聊消息，但私聊目标为空!message:{}", JSON.toJSONString(messageChains));
            return;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("message", messageChains);
        this.sendMessageToQQNT("http://localhost:31011//send_private_msg", JSON.toJSONString(param));
    }


    /**
     * 发送群消息
     *
     * @param groupMessageInfo 准备发送的消息对象
     */
    public void sendGroupMessage(GroupMessageInfo groupMessageInfo) {
        sendGroupMessage(groupMessageInfo.getGroupId(), groupMessageInfo.getMessage());
    }

    /**
     * 发送群消息
     *
     * @param groupId 群号
     * @param message 准备发送的单个消息内容
     */
    public void sendGroupMessage(Long groupId, MessageChain message) {
        sendGroupMessage(groupId, List.of(message));
    }

    /**
     * 发送群消息
     *
     * @param groupId       群号
     * @param messageChains 准备发送的完整消息链
     */
    public void sendGroupMessage(Long groupId, List<MessageChain> messageChains) {
        if (null == groupId) {
            log.warn("发送群消息，但群号为空!message:{}", JSON.toJSONString(messageChains));
            return;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("group_id", groupId);
        param.put("message", messageChains);
        this.sendMessageToQQNT("http://localhost:31011/send_group_msg", JSON.toJSONString(param));
    }

    /**
     * 根据消息来源回复一个纯文本消息
     *
     * @param messageInfo 来源消息
     * @param text        回复消息
     */
    public void sendSimpleText(MessageInfo messageInfo, String text) {
        switch (messageInfo.getMessageType()) {
            case "group":
                if (!(messageInfo instanceof GroupMessageInfo)) {
                    log.error("sendSimpleText准备回复群消息，但获取不到群号,msg:{}", JSON.toJSONString(messageInfo));
                    return;
                }
                this.sendGroupMessage(RabbitBotMessageBuilder.createGroupMessageText(((GroupMessageInfo) messageInfo).getGroupId(), text));
                break;
            case "private":

                break;
            default:
                log.error("sendSimpleText没有找到回复目标,msg:{}", JSON.toJSONString(messageInfo));
        }
    }

    private void sendMessageToQQNT(String url, String jsonBody) {
        //todo try-catch

        //发送到qq
        HttpRequest httpRequest = HttpUtil.createPost(url);
        httpRequest.contentType(ContentType.JSON.getValue());

        //todo 发送记录落库
        HttpResponse response = httpRequest.timeout(HttpGlobalConfig.getTimeout()).body(jsonBody).execute();
        String responseBody = response.body();

        //todo 发送回执落库
        System.out.println("发送消息回执：" + responseBody);
    }
}
