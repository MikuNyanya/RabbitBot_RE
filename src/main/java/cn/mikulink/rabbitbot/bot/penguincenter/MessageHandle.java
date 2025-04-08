package cn.mikulink.rabbitbot.bot.penguincenter;

import cn.mikulink.rabbitbot.bot.RabbitBotSender;
import cn.mikulink.rabbitbot.command.Command;
import cn.mikulink.rabbitbot.command.CommandConfig;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.PrivateMessageInfo;
import cn.mikulink.rabbitbot.service.DeepSeekService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MikuLink created in 2025/4/2 11:31
 * For the Reisen
 * 消息相关业务
 */
@Component
public class MessageHandle {

    @Autowired
    private CommandConfig commandConfig;
    @Autowired
    private DeepSeekService deepSeekService;
    @Autowired
    private RabbitBotSender rabbitBotSender;

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
                    //todo 记录兔叽的回复日志，群消息的记录通过上报自身发言在消息入口记录

                }
                return;
            }
        }

        /**优先处理at自己的业务*/

        /**进入复读机响应*/

        /**at相关业务*/

        /**进入AI响应模式*/
        deepSeekService.aiModeGroup(groupMessageInfo);

        /**匹配关键词 (常规状态下，不是每一句都会触发AI响应的)*/


    }


    public void doMessageBiz(@NotNull PrivateMessageInfo privateMessageInfo) {
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
                    //todo 记录兔叽的回复日志，群消息的记录通过上报自身发言在消息入口记录

                }
                return;
            }
        }

        /**进入AI响应模式*/
        deepSeekService.aiModePrivate(privateMessageInfo);

        /**匹配关键词 (常规状态下，不是每一句都会触发AI响应的)*/


    }


}
