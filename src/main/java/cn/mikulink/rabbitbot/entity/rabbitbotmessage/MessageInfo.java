package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * MikuLink created in 2025/4/2 11:53
 * For the Reisen
 */
@Getter
@Setter
public class MessageInfo extends MessagePushBase {
    //发送者 QQ 号
    @JsonProperty("user_id")
    private Long userId;
    //消息 ID
    @JsonProperty("message_id")
    private Long messageId;
    //表示消息的子类型 正常消息是 normal, 匿名消息是 anonymous, 系统提示 ( 如「管理员已禁止群内匿名聊天」 ) 是 notice
    @JsonProperty("sub_type")
    private String subType;
    @JsonProperty("message_type")
    private String messageType;
    //CQ 码格式的消息
    @JsonProperty("raw_message")
    private String rawMessage;
    private Integer font;
    //发送者信息
    private SenderInfo sender;
    //消息链
    private List<MessageChain> message;
}
