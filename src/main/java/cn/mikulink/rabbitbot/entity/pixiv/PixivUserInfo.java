package cn.mikulink.rabbitbot.entity.pixiv;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2021/1/6 4:01
 * for the Reisen
 * P站 用户信息
 */
@Setter
@Getter
public class PixivUserInfo {
    /**
     * 图片P站id
     */
    private String id;
    /**
     * 用户昵称
     */
    private String nick;
    /**
     * 头像图片链接
     */
    private String logoUrl;
}
