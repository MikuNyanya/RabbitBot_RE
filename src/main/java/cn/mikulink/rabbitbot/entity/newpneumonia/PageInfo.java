package cn.mikulink.rabbitbot.entity.newpneumonia;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * created by MikuNyanya on 2022/3/15 15:43
 * For the Reisen
 */
@NoArgsConstructor
@Data
public class PageInfo {
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "seo")
    private List<?> seo;
    @JSONField(name = "hasTongji")
    private Boolean hasTongji;
    @JSONField(name = "type")
    private String type;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "sharePageUrl")
    private String sharePageUrl;
    @JSONField(name = "shareDesc")
    private String shareDesc;
    @JSONField(name = "shareTitle")
    private String shareTitle;
    @JSONField(name = "shareImg")
    private String shareImg;
}
