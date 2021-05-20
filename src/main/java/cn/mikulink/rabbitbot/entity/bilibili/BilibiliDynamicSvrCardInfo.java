package cn.mikulink.rabbitbot.entity.bilibili;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BilibiliDynamicSvrCardInfo {
    @JSONField(name = "aid")
    private Long aid;
    @JSONField(name = "cid")
    private Long cid;
    @JSONField(name = "ctime")
    private Integer ctime;
    @JSONField(name = "desc")
    private String desc;
    @JSONField(name = "pic")
    private String pic;
    @JSONField(name = "short_link")
    private String shortLink;
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "videos")
    private Integer videos;

    //追番相关字段
    @JSONField(name = "apiSeasonInfo")
    private BilibiliDynamicSvrCardApiSeasonInfo apiSeasonInfo;
    @JSONField(name = "cover")
    private String cover;
    @JSONField(name = "index")
    private String index;
    @JSONField(name = "index_title")
    private String indexTitle;
    @JSONField(name = "new_desc")
    private String newDesc;
    @JSONField(name = "url")
    private String url;
}
