package cn.mikulink.rabbitbot.entity.newpneumonia;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by MikuNyanya on 2022/3/15 15:43
 * For the Reisen
 */
@NoArgsConstructor
@Data
public class ShareInfo {
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "content")
    private String content;
    @JSONField(name = "url")
    private String url;
    @JSONField(name = "type")
    private String type;
    @JSONField(name = "icon")
    private String icon;
}
