package cn.mikulink.rabbitbot.entity.apirequest.weibo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/1/14 9:24
 * for the Reisen
 * 转发的微博消息
 */
@Getter
@Setter
public class InfoRetweetedStatus {
    private Long id;
    //微博创建时间
    private String created_at;
    //推文内容
    private String text;
    //微博来源
    private String source;
    //推文图片列表
    private List<InfoPicUrl> pic_urls;
    //用户信息
    private InfoUser user;
}
