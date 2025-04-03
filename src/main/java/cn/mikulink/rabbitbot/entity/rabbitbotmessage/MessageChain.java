package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import lombok.Getter;
import lombok.Setter;

/**
 * MikuLink created in 2025/4/2 11:55
 * For the Reisen
 * 消息链
 */
@Setter
@Getter
public class MessageChain {
    private String type;
    private MessageChainData data;
}
