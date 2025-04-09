package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import cn.mikulink.rabbitbot.utils.NumberUtil;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * MikuLink created in 2025/4/2 11:53
 * For the Reisen
 */
@Getter
@Setter
@NoArgsConstructor
public class MessageInfo extends MessagePushBase {
    public MessageInfo(List<MessageChain> message) {
        this.message = message;
    }

    public MessageInfo(Long userId, List<MessageChain> message) {
        this.userId = userId;
        this.message = message;
    }

    //发送者 QQ 号
    @JSONField(name = "user_id")
    protected Long userId;
    //消息 ID
    @JSONField(name = "message_id")
    protected Long messageId;
    //表示消息的子类型 正常消息是 normal, 匿名消息是 anonymous, 系统提示 ( 如「管理员已禁止群内匿名聊天」 ) 是 notice
    @JSONField(name = "sub_type")
    protected String subType;
    //消息类型，group代表群消息 private私聊消息
    @JSONField(name = "message_type")
    protected String messageType;
    //CQ 码格式的消息
    @JSONField(name = "raw_message")
    protected String rawMessage;
    @JSONField(name = "message_format")
    protected String messageFormat;
    //字的大小
    protected Integer font;
    //发送者信息
    protected SenderInfo sender;
    //消息链
    protected List<MessageChain> message;

    //是否为群消息
    public boolean isGroupMessage() {
        return messageType.equals("group");
    }

    //是否为私聊信息
    public boolean isPrivateMessage() {
        return messageType.equals("private");
    }

    //是否at了机器人
    public boolean isAtBot() {
        for (MessageChain messageChain : message) {
            if (messageChain.getType().equalsIgnoreCase("at")) {
                if (selfId.equals(NumberUtil.toLong(messageChain.getData().getQq()))) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否提到了机器人
    public boolean isMentionBot() {
        //todo 写到某个服务中，机器人名称使用配置文件的值
        return this.rawMessage.contains("兔叽");
    }
}
