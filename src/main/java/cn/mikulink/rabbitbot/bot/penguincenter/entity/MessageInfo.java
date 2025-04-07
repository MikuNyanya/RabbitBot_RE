package cn.mikulink.rabbitbot.bot.penguincenter.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * MikuLink created in 2025/3/31 23:14
 * For the Reisen
 * 消息 适用于上报 和发送
 */
@Getter
@Setter
public class MessageInfo {
    private String type;
    private MessageDataInfo data;
}
