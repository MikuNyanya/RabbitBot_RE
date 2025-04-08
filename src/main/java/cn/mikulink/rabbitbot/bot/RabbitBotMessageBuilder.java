package cn.mikulink.rabbitbot.bot;

import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChainData;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;

/**
 * create by MikuLink on 2025/4/2 13:00
 * for the Reisen
 * 用于方便构建消息的工具
 */
public class RabbitBotMessageBuilder {

    /**
     * 创建一个纯文本群消息对象
     * 返回对象中带有群号
     *
     * @param groupId 群号
     * @param text    文本内容
     * @return 群消息对象
     */
    public static GroupMessageInfo createGroupMessageText(Long groupId, String text) {
        MessageInfo messageInfo = createMessageText(text);
        return messageToGroupMessage(groupId, messageInfo);
    }

    /**
     * 创建纯文本消息对象
     * 注：返回对象没有发送目标信息，无法直接做为发送消息对象来使用
     *
     * @param text 文本内容
     * @return 消息对象
     */
    public static MessageInfo createMessageText(String text) {
        MessageChain message = parseMessageChainText(text);
        //List.of会构建一个不可修改的list，可能会导致其他业务问题，我大抵是在埋坑罢
//        return new MessageInfo(List.of(message));
        ArrayList<MessageChain> list = new ArrayList<>();
        list.add(message);
        return new MessageInfo(list);
    }

    /**
     * 创建纯文本消息对象
     * 注：返回对象没有发送目标信息，无法直接做为发送消息对象来使用
     *
     * @param imgUrlOrPath 图片的网络url，或者本地路径(建议绝对路径)
     * @return 消息对象
     */
    public static MessageInfo createMessageImage(String imgUrlOrPath) {
        MessageChain message = parseMessageChainImage(imgUrlOrPath);
        ArrayList<MessageChain> list = new ArrayList<>();
        list.add(message);
        return new MessageInfo(list);
    }

    //组装纯文本消息
    public static MessageChain parseMessageChainText(String text) {
        MessageChain messageChain = new MessageChain();
        messageChain.setType("text");
        messageChain.setData(new MessageChainData(text));
        return messageChain;
    }

    //组装纯图片消息
    public static MessageChain parseMessageChainImage(String imgUrlOrPath) {
        MessageChain messageChain = new MessageChain();
        messageChain.setType("image");
        messageChain.setData(MessageChainData.createImageMessageData(imgUrlOrPath));
        return messageChain;
    }

    //把一个普通消息对象转化为群消息对象
    private static GroupMessageInfo messageToGroupMessage(Long groupId, MessageInfo messageInfo) {
        GroupMessageInfo groupMessageInfo = new GroupMessageInfo();
        BeanUtils.copyProperties(messageInfo, groupMessageInfo);
        groupMessageInfo.setGroupId(groupId);
        return groupMessageInfo;
    }

}
