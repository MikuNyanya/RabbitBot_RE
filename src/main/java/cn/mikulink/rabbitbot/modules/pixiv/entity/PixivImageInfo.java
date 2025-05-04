package cn.mikulink.rabbitbot.modules.pixiv.entity;

import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;

import java.util.List;

/**
 * create by MikuLink on 2020/3/13 16:50
 * for the Reisen
 * P站 单个图片信息对象
 */
@Setter
@Getter
public class PixivImageInfo {
    /**
     * 图片P站id
     */
    private String id;
    /**
     * 图片标题
     */
    private String title;
    /**
     * 图片简介
     */
    private String description;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 图片链接
     */
    private PixivImageUrlInfo urls;
    /**
     * 图片链接
     * 如果是多图，这个值才会有用
     */
    private List<PixivImageUrlInfo> urlList;
    /**
     * 似乎是某种限制，应该可以用来判断是否r18和r18g
     * 0为无限制 1为有限制
     */
    private Integer xRestrict;
    /**
     * 作者id
     */
    private Long userId;
    /**
     * 作者名称
     */
    private String userName;
    /**
     * 原图长度（横向）
     */
    private Integer width;
    /**
     * 原图高度（纵向）
     */
    private Integer height;
    /**
     * 一共几张图,可以以此来判断是否为多P
     */
    private Integer pageCount;
    /**
     * 阅览数
     */
    private Integer viewCount;
    /**
     * 收藏数
     */
    private Integer bookmarkCount;
    /**
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 评论数
     */
    private Integer commentCount;
//    private Object tags;
    /**
     * 相似度，在搜图功能里会用到
     */
    private String similarity;
}
