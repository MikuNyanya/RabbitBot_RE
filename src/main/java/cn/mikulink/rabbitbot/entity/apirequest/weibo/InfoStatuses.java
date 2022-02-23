package cn.mikulink.rabbitbot.entity.apirequest.weibo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/1/8 19:19
 * for the Reisen
 */
@Getter
@Setter
public class InfoStatuses {
    //微博创建时间
    private String created_at;
    //推文内容
    private String text;
    //微博来源
    private String source;
    //跟超话有关的字段，包含超话id
    private String topic_id;
    //推文图片列表
    private List<InfoPicUrl> pic_urls;
    //用户信息
    private InfoUser user;
    //转发微博
    private InfoStatuses retweeted_status;
}
