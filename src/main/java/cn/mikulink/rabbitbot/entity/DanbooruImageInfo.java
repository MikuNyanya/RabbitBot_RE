package cn.mikulink.rabbitbot.entity;

import cn.mikulink.rabbitbot.entity.apirequest.saucenao.SaucenaoSearchInfoResult;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * created by MikuNyanya on 2022/2/18 15:46
 * For the Reisen
 */
@NoArgsConstructor
@Data
public class DanbooruImageInfo implements Serializable {
    /**
     * danbooru图片id
     */
    @JSONField(name = "id")
    private Long id;
    /**
     * 图片创建时间
     */
    @JSONField(name = "created_at")
    private String createdAt;
    /**
     * 评分，其实意义不大，没多少用户使用这个系统
     */
    @JSONField(name = "score")
    private Integer score;
    /**
     * 图片源头
     */
    @JSONField(name = "source")
    private String source;
    @JSONField(name = "rating")
    private String rating;
    /**
     * 图片长
     */
    @JSONField(name = "image_width")
    private Integer imageWidth;
    /**
     * 图片宽
     */
    @JSONField(name = "image_height")
    private Integer imageHeight;
    /**
     * 图片包含tag
     */
    @JSONField(name = "tag_string")
    private String tagString;
    /**
     * 被收藏次数
     */
    @JSONField(name = "fav_count")
    private Integer favCount;
    /**
     * 图片后缀
     */
    @JSONField(name = "file_ext")
    private String fileExt;
    /**
     * 父级id，danbooru会把不同版本的图片连接起来
     */
    @JSONField(name = "parent_id")
    private Object parentId;
    /***
     * 是否包含子集
     */
    @JSONField(name = "has_children")
    private Boolean hasChildren;
    /**
     * 文件大小 单位应该是KB
     */
    @JSONField(name = "file_size")
    private Integer fileSize;
    /**
     * 最高分？
     */
    @JSONField(name = "up_score")
    private Integer upScore;
    /**
     * 最低分？
     */
    @JSONField(name = "down_score")
    private Integer downScore;
    @JSONField(name = "is_pending")
    private Boolean isPending;
    @JSONField(name = "is_flagged")
    private Boolean isFlagged;
    @JSONField(name = "is_deleted")
    private Boolean isDeleted;
    /**
     * tag数量
     */
    @JSONField(name = "tag_count")
    private Integer tagCount;
    /**
     * 最后更新时间
     */
    @JSONField(name = "updated_at")
    private String updatedAt;
    @JSONField(name = "is_banned")
    private Boolean isBanned;
    /**
     * pixiv id，如果来源是p站，应该有值的
     */
    @JSONField(name = "pixiv_id")
    private Object pixivId;
    @JSONField(name = "last_commented_at")
    private Object lastCommentedAt;
    @JSONField(name = "has_active_children")
    private Boolean hasActiveChildren;
    @JSONField(name = "bit_flags")
    private Integer bitFlags;
    @JSONField(name = "tag_count_meta")
    private Integer tagCountMeta;
    /**
     * 是否有大图
     */
    @JSONField(name = "has_large")
    private Boolean hasLarge;
    @JSONField(name = "has_visible_children")
    private Boolean hasVisibleChildren;
    @JSONField(name = "tag_string_general")
    private String tagStringGeneral;
    @JSONField(name = "tag_string_character")
    private String tagStringCharacter;
    @JSONField(name = "tag_string_copyright")
    private String tagStringCopyright;
    @JSONField(name = "tag_string_artist")
    private String tagStringArtist;
    @JSONField(name = "tag_string_meta")
    private String tagStringMeta;
    /**
     * 原图
     */
    @JSONField(name = "file_url")
    private String fileUrl;
    /**
     * 大图
     * 如果没有大图，则这里的会是原图链接
     */
    @JSONField(name = "large_file_url")
    private String largeFileUrl;
    /**
     * 缩略图
     */
    @JSONField(name = "preview_file_url")
    private String previewFileUrl;

    //搜图用对象
    private SaucenaoSearchInfoResult saucenaoSearchInfoResult;
}
