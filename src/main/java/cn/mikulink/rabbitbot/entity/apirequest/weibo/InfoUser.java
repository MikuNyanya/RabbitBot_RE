package cn.mikulink.rabbitbot.entity.apirequest.weibo;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/1/8 19:44
 * for the Reisen
 */
@Getter
@Setter
public class InfoUser {
    //用户id
    private Long id;
    //用户名称
    private String name;
    //用户简介
    private String description;
    //用户头像链接，50x50的小图
    private String profile_image_url;
    //用户头像链接，180x180的中图
    private String avatar_large;
    //用户头像链接，原图
    private String avatar_hd;
}
