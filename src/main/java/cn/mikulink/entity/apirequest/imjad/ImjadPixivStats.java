package cn.mikulink.entity.apirequest.imjad;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/2/19 14:55
 * for the Reisen
 * 一些数据，大概是观看数，点赞数，收藏数之类的
 */
@Setter
@Getter
public class ImjadPixivStats {
    /**
     * scored_count : 17459
     * score : 174061
     * views_count : 249453
     * favorited_count : {"public":18562,"private":1279}
     * commented_count : 143
     */

    //分数，应该是P站的综合评价
    private int scored_count;
    //分数，比上面分数后面多了一个0
    private int score;
    //点击数
    private int views_count;
    //评论数
    private int commented_count;
    //收藏数
    private ImjadPixivFavoritedCount favorited_count;
}
