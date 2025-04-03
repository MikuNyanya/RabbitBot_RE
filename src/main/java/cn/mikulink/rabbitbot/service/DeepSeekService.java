package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.deepseek.ChatCompletionsRequest;
import cn.mikulink.rabbitbot.bot.RabbitBotSender;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MikuLink created in 2025/4/2 12:26
 * For the Reisen
 */
@Slf4j
@Service
public class DeepSeekService {
    //给予ai用作参考的群聊历史数目
    public int groupHistoryNum = 20;

    @Autowired
    private RabbitBotSender rabbitBotSender;

    public void aiModeGroup(GroupMessageInfo groupMessageInfo) {
        try {
            //at了兔叽的必定回复 不然使用时间+概率响应的模式
            boolean isAtBot = isAtBot(groupMessageInfo.getSelfId(), groupMessageInfo.getMessage());
            if (isAtBot) {
                String tempMsg = groupMessageInfo.getSender().getCard() + ":";
                for (MessageChain messageInfo : groupMessageInfo.getMessage()) {
                    switch (messageInfo.getType()){
                        case "text":
                            tempMsg += messageInfo.getData().getText();
                            break;
                        case "image":
                            tempMsg += "[图片]";
                            break;
                    }
                }

                String chatRsp = ChatCompletionsRequest.test(tempMsg);

                rabbitBotSender.sendGroupMessage(groupMessageInfo.getGroupId(), rabbitBotSender.parseMessageChainText("text", chatRsp));
            }

            //获取当前群聊向上一定数目的历史记录传给ai用作分析

            //拼接群聊信息


        } catch (Exception ex) {
            log.error("AI请求异常,messageId:{}", groupMessageInfo.getMessageId(),ex);
        }

    }

    //是否at了机器人
    public boolean isAtBot(Long botSelfId, List<MessageChain> messageChains) {
        for (MessageChain messageChain : messageChains) {
            if (messageChain.getType().equalsIgnoreCase("at")) {
                if (botSelfId.equals(NumberUtil.toLong(messageChain.getData().getQq()))) {
                    return true;
                }
            }
        }
        return false;
    }
}
