package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * MikuLink created in 2025/4/2 11:40
 * For the Reisen
 * 用于兔叽的消息，独立出来减少耦合
 * 所有从外部获取的qq消息，应转换为兔叽的消息对象，再使用于兔叽的业务
 */
@Getter
@Setter
public class MessagePushBase implements Serializable {
    //事件发生的unix时间戳
    @JSONField(name = "time")
    private Long time;
    //收到事件的机器人的 QQ 号
    @JSONField(name = "self_id")
    private Long selfId;
    //表示该上报的类型, message=消息, 消息发送, 请求, 通知, 或元事件
    @JSONField(name = "post_type")
    private String postType;

}
