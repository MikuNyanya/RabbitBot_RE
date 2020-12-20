package cn.mikulink.rabbitbot.entity.apirequest.weibo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/1/8 19:16
 * for the Reisen
 * 只解析了必要字段
 * api文档：https://open.weibo.com/wiki/2/statuses/home_timeline
 */
@Getter
@Setter
public class InfoWeiboHomeTimeline {
    //微博推文列表
    private List<InfoStatuses> statuses;
    //since_id 用于避免重复获取旧信息的字段
    private Long since_id;
}
