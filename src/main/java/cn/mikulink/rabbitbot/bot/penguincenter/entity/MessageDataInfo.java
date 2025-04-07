package cn.mikulink.rabbitbot.bot.penguincenter.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * MikuLink created in 2025/3/31 23:15
 * For the Reisen
 * 消息内容 通常包含于MessageInfo下
 */
@Getter
@Setter
public class MessageDataInfo {
    //文本内容
    private String text;

    //当MessageInfo的type为image时，下列字段才会有用
    private String file;
    private String url;
    private String file_size;
    private Integer subType;

    //当MessageInfo的type为music时，下列字段才会有用
    private String type;
    private Long id;
    private String audio;
    private String title;
    private String image;

    //当MessageInfo的type为at时，下列字段才会有用
    private String qq;
}
