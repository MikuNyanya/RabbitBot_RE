package cn.mikulink.rabbitbot.entity.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * MikuLink created in 2025/4/8 22:00
 * For the Reisen
 * 兔叽消息发送记录
 */
@Data
@NoArgsConstructor
public class rabbitbotSendRecordInfo {
    //主键id
    private Long id;
    //数据创建时间
    private Date createTime;
    //群号 不为空则为群消息
    private Long groupId;
    //消息发送人号 不为空则为私聊消息
    private Long userId;
    //消息id 消息发送后返回报文中带有id
    private Long messageId;
    //发送的信息报文
    private String request_json;
    //消息发送后返回的报文
    private String response_json;
}
