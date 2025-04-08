package cn.mikulink.rabbitbot.entity.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * MikuLink created in 2025/4/8 22:00
 * For the Reisen
 * 私聊消息
 */
@Data
@NoArgsConstructor
public class RabbitbotPrivateMessageInfo {
    //主键id
    private Long id;
    //数据创建时间
    private Date createTime;
    //消息发送人号
    private Long userId;
    //消息id
    private Long messageId;
    //发送人信息json数据
    private String senderJson;
    //CQ码格式的消息
    private String rawMessage;
    //消息链json格式
    private String messageJson;
    //消息类型 group代表群消息 private私聊消息
    private String messageType;
    //表示消息的子类型 正常消息是 normal, 匿名消息是 anonymous,
    private String sub_type;
    private String messageFormat;
    //消息状态 0.正常 1.已撤回
    private Integer recall;
}
