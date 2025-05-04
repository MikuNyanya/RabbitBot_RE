package cn.mikulink.rabbitbot.modules.pixiv.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/3/13 16:37
 * for the Reisen
 * p站图片链接信息
 */
@Setter
@Getter
public class PixivImageUrlInfo {
    private String mini;
    private String thumb;
    private String small;
    private String regular;
    //原图
    private String original;
}
