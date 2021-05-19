package cn.mikulink.rabbitbot.entity.bilibili;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BilibiliDynamicSvrCardInfo {
    @JSONField(name = "aid")
    private Integer aid;
    @JSONField(name = "cid")
    private Integer cid;
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
}
