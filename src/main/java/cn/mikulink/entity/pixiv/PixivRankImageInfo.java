package cn.mikulink.entity.pixiv;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/2/20 17:50
 * for the Reisen
 * P站 热榜 结果信息
 */
@Setter
@Getter
public class PixivRankImageInfo {
    /**
     * 图片P站id
     */
    private Long pid;
    /**
     * 下载到本地图片路径
     */
    private List<String> localImagesPath;
    /**
     * 图片标题
     */
    private String title;
    /**
     * 图片简介
     */
    private String caption;
    /**
     * 排名
     */
    private Integer rank;
    /**
     * 上次排名
     */
    private String previousRank;
    /**
     * 创建时间
     */
    private String createdTime;
    /**
     * 似乎是某种限制，应该可以用来判断是否r18和r18g
     * 0为无限制 1为有限制
     */
    private Integer xRestrict;
    /**
     * 作画工具
     */
    private String tools;
    /**
     * pid下图片总数
     */
    private Integer pageCount;
    /**
     * 作者id
     */
    private Long userId;
    /**
     * 作者名称
     */
    private String userName;
}
