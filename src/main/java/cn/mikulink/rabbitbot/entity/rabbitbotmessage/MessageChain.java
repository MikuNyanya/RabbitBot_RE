package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * MikuLink created in 2025/4/2 11:55
 * For the Reisen
 * 消息链
 */
@Setter
@Getter
public class MessageChain {
    public MessageChain() {}

    public MessageChain(String type, MessageChainData data) {
        this.type = type;
        this.data = data;
    }

    /**
     * 消息类型
     * text 文本消息
     * image 图片
     * at @
     */
    private String type;
    /**
     * 消息内容，具体内容因type而异
     */
    private MessageChainData data;
}
