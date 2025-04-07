package cn.mikulink.rabbitbot.bot.penguincenter.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * MikuLink created in 2025/3/31 22:50
 * For the Reisen
 * 上报 消息类型
 */
@Setter
@Getter
public class PostMessage{
    //发送者 QQ 号
    private Long user_id;
    //收到事件的机器人的 QQ 号
    private Long self_id;
    //消息 ID
    private Long message_id;
    //表示消息的子类型 正常消息是 normal, 匿名消息是 anonymous, 系统提示 ( 如「管理员已禁止群内匿名聊天」 ) 是 notice
    private String sub_type;

    private String message_type;
    //CQ 码格式的消息
    private String raw_message;
    private Integer font;
    //发送者信息
    private PostSender sender;
    //消息链
    private List<MessageInfo> message;
    //事件发生的unix时间戳
    private Long time;
    //表示该上报的类型, message=消息, 消息发送, 请求, 通知, 或元事件
    private String post_type;
    //群号
    private Long group_id;
}
