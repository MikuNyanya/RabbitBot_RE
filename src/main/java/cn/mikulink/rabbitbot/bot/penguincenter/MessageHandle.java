package cn.mikulink.rabbitbot.bot.penguincenter;

import cn.mikulink.rabbitbot.bot.RabbitBotSender;
import cn.mikulink.rabbitbot.command.Command;
import cn.mikulink.rabbitbot.command.CommandConfig;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.PrivateMessageInfo;
import cn.mikulink.rabbitbot.service.AtService;
import cn.mikulink.rabbitbot.service.DeepSeekService;
import cn.mikulink.rabbitbot.service.KeyWordService;
import cn.mikulink.rabbitbot.service.db.RabbitbotGroupMessageService;
import cn.mikulink.rabbitbot.service.db.RabbitbotPrivateMessageService;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * MikuLink created in 2025/4/2 11:31
 * For the Reisen
 * 消息相关业务
 */
@Component
public class MessageHandle {
    @Value("${bot.groupMessageResponse:false}")
    private boolean groupMessageResponse;
    @Value("${bot.privateMessageResponse:false}")
    private boolean privateMessageResponse;

    @Autowired
    private CommandConfig commandConfig;
    @Autowired
    private DeepSeekService deepSeekService;
    @Autowired
    private RabbitBotSender rabbitBotSender;
    @Autowired
    private RabbitbotGroupMessageService rabbitbotGroupMessageService;
    @Autowired
    private RabbitbotPrivateMessageService rabbitbotPrivateMessageService;
    @Autowired
    private KeyWordService keyWordService;
    @Autowired
    private AtService atService;

    public void messageHandle(String messageBody) {
        JSONObject bodyJsonObj = JSONObject.parseObject(messageBody);

        String messageType = String.valueOf(bodyJsonObj.get("message_type"));
        switch (messageType) {
            case "group":
                //群消息
                GroupMessageInfo groupMessageInfo = JSON.parseObject(messageBody, GroupMessageInfo.class);
                doMessageBiz(groupMessageInfo);
                break;
            case "private":
                //私聊消息
                PrivateMessageInfo messageInfo = JSON.parseObject(messageBody, PrivateMessageInfo.class);
                doMessageBiz(messageInfo);
                break;
        }
    }

    /**
     * 群消息业务
     *
     * @param groupMessageInfo 群消息
     */
    public void doMessageBiz(@NotNull GroupMessageInfo groupMessageInfo) {
        //总开关
        if (!groupMessageResponse) {
            return;
        }

        rabbitbotGroupMessageService.create(groupMessageInfo);

        Long groupId = groupMessageInfo.getGroupId();
        //todo 群黑名单过滤

        String rawMessageStr = groupMessageInfo.getRawMessage();
        /**匹配指令模式*/
        if (commandConfig.isCommand(rawMessageStr)) {
            //群指令
            Command command = commandConfig.getCommand(rawMessageStr, commandConfig.groupCommands);
            if (null == command) {
                //通用指令
                command = commandConfig.getCommand(rawMessageStr, commandConfig.everywhereCommands);
            }
            //匹配到指令则执行指令相关业务，然后返回消息
            //若没匹配到指令，则继续向下走其他业务
            if (command != null) {
                //判断权限
                MessageInfo result = command.permissionCheck(groupMessageInfo);
                if (null != result) {
                    //权限验证未通过
                    rabbitBotSender.sendGroupMessage(groupId, result.getMessage());
                    return;
                }

                //执行指令并回复结果
                result = command.execute(groupMessageInfo);
                if (result != null) {
                    rabbitBotSender.sendGroupMessage(groupId, result.getMessage());
                }
                return;
            }
        }

        /**at相关业务 只将at放在最前面的消息视为at业务*/
        if (CollectionUtil.isNotEmpty(groupMessageInfo.getMessage()) && groupMessageInfo.getMessage().get(0).getType().equals("at")) {
            MessageInfo result = atService.doAtBiz(groupMessageInfo);
            if (result != null) {
                rabbitBotSender.sendGroupMessage(groupId, result.getMessage());
                return;
            }
        }

        /**进入AI响应模式*/
        boolean doRequestAIResult = false;
        //at了兔叽和文本中提到兔叽的必定回复
        if (groupMessageInfo.atBot() || groupMessageInfo.mentionBot()) {
            doRequestAIResult = true;
        } else {
            //日常状态 包含响应间隔，以及响应概率

        }
        if (doRequestAIResult) {
            GroupMessageInfo result = deepSeekService.aiModeGroup(groupMessageInfo);
            if (null != result) {
                result.setGroupId(groupId);
                rabbitBotSender.sendGroupMessage(result);
                return;
            }
        }

        /**匹配关键词 (因为常规状态下，不是每一句都会触发AI响应的，所以会到这里)*/
        MessageInfo result = keyWordService.keyWordMatchGroup(groupMessageInfo);
        if (result != null) {
            rabbitBotSender.sendGroupMessage(groupId, result.getMessage());
        }
    }


    public void doMessageBiz(@NotNull PrivateMessageInfo privateMessageInfo) {
        //总开关
        if (!privateMessageResponse) {
            return;
        }

        //消息落库
        rabbitbotPrivateMessageService.create(privateMessageInfo);

        Long senderUserId = privateMessageInfo.getUserId();
        String rawMessageStr = privateMessageInfo.getRawMessage();
        /**匹配指令模式*/
        if (commandConfig.isCommand(rawMessageStr)) {
            //群指令
            Command command = commandConfig.getCommand(rawMessageStr, commandConfig.privateMsgCommands);
            if (null == command) {
                //通用指令
                command = commandConfig.getCommand(rawMessageStr, commandConfig.everywhereCommands);
            }
            //匹配到指令则执行指令相关业务，然后返回消息
            //若没匹配到指令，则继续向下走其他业务
            if (command != null) {
                //判断权限
                MessageInfo result = command.permissionCheck(privateMessageInfo);
                if (null != result) {
                    //权限验证未通过
                    rabbitBotSender.sendPrivateMessage(senderUserId, result.getMessage());
                    return;
                }

                //执行指令并回复结果
                result = command.execute(privateMessageInfo);
                if (result != null) {
                    rabbitBotSender.sendPrivateMessage(senderUserId, result.getMessage());
                }
                return;
            }
        }

        /**进入AI响应模式 (常规状态下，不是每一句都会触发AI响应的)*/
        boolean isResponded = deepSeekService.aiModePrivate(privateMessageInfo);
        if (isResponded) {
            return;
        }

        /**匹配关键词*/
        MessageInfo result = keyWordService.keyWordMatchGroup(privateMessageInfo);
        if (result != null) {
            rabbitBotSender.sendPrivateMessage(senderUserId, result.getMessage());
        }
    }

    //机器人自己的消息处理
    public void selfMessageHandle(String body) {
        JSONObject bodyJsonObj = JSONObject.parseObject(body);

        String messageType = String.valueOf(bodyJsonObj.get("message_type"));
        switch (messageType) {
            case "group" -> {
                //群消息
                GroupMessageInfo groupMessageInfo = JSON.parseObject(body, GroupMessageInfo.class);
                rabbitbotGroupMessageService.create(groupMessageInfo);
            }
            case "private" -> {
                //私聊消息
                PrivateMessageInfo messageInfo = JSON.parseObject(body, PrivateMessageInfo.class);
                rabbitbotPrivateMessageService.create(messageInfo);
            }
        }
    }


}
