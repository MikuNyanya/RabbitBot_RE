package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.deepseek.ChatCompletionsRequest;
import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.bot.RabbitBotSender;
import cn.mikulink.rabbitbot.entity.apirequest.deepseek.MessageInfo;
import cn.mikulink.rabbitbot.entity.db.RabbitbotGroupMessageInfo;
import cn.mikulink.rabbitbot.entity.db.RabbitbotPrivateMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.PrivateMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.SenderInfo;
import cn.mikulink.rabbitbot.service.db.RabbitbotGroupMessageService;
import cn.mikulink.rabbitbot.service.db.RabbitbotPrivateMessageService;
import cn.mikulink.rabbitbot.utils.StringUtil;
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
    public int groupHistoryNum = 50;
    //私聊历史条目，可以设置的稍微多一些
    public int privateHistoryNum = 500;

    @Value("${deepseek.token:}")
    private String token;

    @Autowired
    private RabbitBotSender rabbitBotSender;
    @Autowired
    private RabbitbotPrivateMessageService rabbitbotPrivateMessageService;
    @Autowired
    private RabbitbotGroupMessageService rabbitbotGroupMessageService;

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

    public boolean aiModeGroup(GroupMessageInfo groupMessageInfo) {
        Long groupId = groupMessageInfo.getGroupId();
        try {
            boolean doRequestAIResult = false;
            //at了兔叽和文本中提到兔叽的必定回复
            if (groupMessageInfo.isAtBot() || groupMessageInfo.isMentionBot()) {
                doRequestAIResult = true;
            } else {
                //日常状态 包含响应间隔，以及响应概率

            }

            if (!doRequestAIResult) {
                //表示没有执行ai响应
                return false;
            }

            //获取当前私聊向上一定数目的历史记录传给ai用作分析
            List<RabbitbotGroupMessageInfo> privateMessageInfoList = rabbitbotGroupMessageService.getHistoryByTargetId(groupId, groupHistoryNum);

            //拼接私聊信息
            List<MessageInfo> paramMessageList = new ArrayList<>();
            for (RabbitbotGroupMessageInfo rabbitbotGroupMessageInfo : privateMessageInfoList) {

                //获取用户名
                SenderInfo senderInfo = JSON.parseObject(rabbitbotGroupMessageInfo.getSenderJson(), SenderInfo.class);
                String userNick = senderInfo.getCard();
                if (StringUtil.isEmpty(userNick)) {
                    userNick = senderInfo.getNickname();
                }

                //把消息转化为给ds的格式
                List<MessageChain> tempList = JSON.parseArray(rabbitbotGroupMessageInfo.getMessageJson(), MessageChain.class);
                String tempStr = parseMessageToDeepSeek(tempList);

                if (rabbitbotGroupMessageInfo.getUserId().equals(groupMessageInfo.getSelfId())) {
                    //兔叽消息
                    paramMessageList.add(new MessageInfo("assistant", tempStr));
                } else {
                    tempStr = String.format("[%s]:%s",userNick,tempStr);
                    //用户消息
                    paramMessageList.add(new MessageInfo("user", userNick, tempStr));
                }
            }

            String chatRsp = requestAIResult(paramMessageList);

            rabbitBotSender.sendGroupMessage(groupId, RabbitBotMessageBuilder.parseMessageChainText(chatRsp));

            return true;
        } catch (Exception ex) {
            log.error("aiModeGroup AI请求异常,messageId:{}", groupMessageInfo.getMessageId(), ex);
            return false;
        }
    }

    public boolean aiModePrivate(PrivateMessageInfo privateMessageInfo) {
        try {
            //获取当前私聊向上一定数目的历史记录传给ai用作分析
            List<RabbitbotPrivateMessageInfo> privateMessageInfoList = rabbitbotPrivateMessageService.getHistoryByTargetId(privateMessageInfo.getTargetId(), privateHistoryNum);

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

            return true;
        } catch (Exception ex) {
            log.error("aiModePrivate AI请求异常,messageId:{}", privateMessageInfo.getMessageId(), ex);
            return false;
        }
    }

    /**
     * 把消息转化为传给ds的消息格式
     *
     * @param messageChainList 消息链
     * @return 可以直接传给ds的消息
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
