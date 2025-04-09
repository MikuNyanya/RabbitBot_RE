package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.deepseek.ChatCompletionsRequest;
import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.bot.RabbitBotSender;
import cn.mikulink.rabbitbot.entity.apirequest.deepseek.MessageInfo;
import cn.mikulink.rabbitbot.entity.db.RabbitbotPrivateMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.PrivateMessageInfo;
import cn.mikulink.rabbitbot.service.db.RabbitbotPrivateMessageService;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MikuLink created in 2025/4/2 12:26
 * For the Reisen
 */
@Slf4j
@Service
public class DeepSeekService {
    //给予ai用作参考的群聊历史数目 因为基本是一问一答，所以设置100相当于用户输入了50条信息，兔叽回答了50条信息
    public int groupHistoryNum = 500;

    @Value("${deepseek.token:}")
    private String token;

    @Autowired
    private RabbitBotSender rabbitBotSender;
    @Autowired
    private RabbitbotPrivateMessageService rabbitbotPrivateMessageService;

    private String requestAIResult(String message) throws IOException {
        ChatCompletionsRequest request = new ChatCompletionsRequest();
        request.setAccessToken(token);
        request.setMessagList(List.of(new MessageInfo("user", message)));
        return request.doRequest();
    }

    private String requestAIResult(List<MessageInfo> messageList) throws IOException {
        ChatCompletionsRequest request = new ChatCompletionsRequest();
        request.setAccessToken(token);
        request.setMessagList(messageList);
        return request.doRequest();
    }

    /**
     * ai戳一戳回复
     *
     * @param pokerName 谁戳的
     * @return ai回复
     */
    public String aiPoke(String pokerName) throws IOException {
        //戳一戳消息里带有完整的戳一戳信息，可能是拍一拍或者别的什么
        //但这个是自己账号设置的，所以默认为戳一戳
        //日后如果这个功能企鹅升级了，可以在这里更改传给ai的语句
        String text = pokerName + ":(戳了戳兔叽)";
        return requestAIResult(text);
    }


    public void aiModeGroup(GroupMessageInfo groupMessageInfo) {
        try {
            //at了兔叽的必定回复 不然使用时间+概率响应的模式
            if (groupMessageInfo.isAtBot()) {
                String tempMsg = groupMessageInfo.getSender().getCard() + ":";
                for (MessageChain messageInfo : groupMessageInfo.getMessage()) {
                    switch (messageInfo.getType()) {
                        case "text":
                            tempMsg += messageInfo.getData().getText();
                            break;
                        case "image":
                            tempMsg += "[图片]";
                            break;
                    }
                }

                String chatRsp = requestAIResult(tempMsg);

                rabbitBotSender.sendGroupMessage(RabbitBotMessageBuilder.createGroupMessageText(groupMessageInfo.getGroupId(), chatRsp));
            }

            //获取当前群聊向上一定数目的历史记录传给ai用作分析

            //拼接群聊信息

        } catch (Exception ex) {
            log.error("aiModeGroup AI请求异常,messageId:{}", groupMessageInfo.getMessageId(), ex);
        }

    }

    public void aiModePrivate(PrivateMessageInfo privateMessageInfo) {
        try {
            //获取当前私聊向上一定数目的历史记录传给ai用作分析
            List<RabbitbotPrivateMessageInfo> privateMessageInfoList = rabbitbotPrivateMessageService.getHistoryByTargetId(privateMessageInfo.getTargetId(), groupHistoryNum);

            //拼接私聊信息
            List<MessageInfo> paramMessageList = new ArrayList<>();
            for (RabbitbotPrivateMessageInfo rabbitbotPrivateMessageInfo : privateMessageInfoList) {
                //把消息转化为给ds的格式
                List<MessageChain> tempList = JSON.parseArray(rabbitbotPrivateMessageInfo.getMessageJson(), MessageChain.class);
                String tempStr = parseMessageToDeepSeek(tempList);

                if (rabbitbotPrivateMessageInfo.getUserId().equals(rabbitbotPrivateMessageInfo.getTargetId())) {
                    //用户消息
                    paramMessageList.add(new MessageInfo("user", tempStr));
                } else {
                    //兔叽消息
                    paramMessageList.add(new MessageInfo("assistant", tempStr));
                }
            }

            String chatRsp = requestAIResult(paramMessageList);

            rabbitBotSender.sendPrivateMessage(privateMessageInfo.getUserId(), RabbitBotMessageBuilder.parseMessageChainText(chatRsp));

        } catch (Exception ex) {
            log.error("aiModePrivate AI请求异常,messageId:{}", privateMessageInfo.getMessageId(), ex);
        }

    }

    /**
     * 把消息转化为传给ds的消息格式
     *
     * @param messageChainList  消息链
     * @return  可以直接传给ds的消息
     */
    private String parseMessageToDeepSeek(List<MessageChain> messageChainList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (MessageChain messageChain : messageChainList) {
            switch (messageChain.getType()) {
                case "text" ->
                    //text可直接拼接
                        stringBuilder.append(messageChain.getData().getText());
                case "image" ->
                    //ds无法识别图片，所以直接表明这是个图片
                        stringBuilder.append("[图片]");
            }
        }
        return stringBuilder.toString();
    }

}
