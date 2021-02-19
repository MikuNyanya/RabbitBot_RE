package cn.mikulink.rabbitbot.entity.apirequest.lolicon;

import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;

import java.util.List;

@Setter
@Getter
public class LoliconImageInfo {
    private int code;
    private String msg;
    private int quota;
    private int quota_min_ttl;
    private int count;
    private List<LoliconDataEntity> data;
    /**
     * 请求来源的消息
     * 用于判断配置和开关
     */
    private Contact subject;
    /**
     * 请求来源的qq
     * 用于判断配置和开关
     */
    private User sender;

}
