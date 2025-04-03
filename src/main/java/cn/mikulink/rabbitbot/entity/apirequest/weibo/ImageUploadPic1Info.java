package cn.mikulink.rabbitbot.entity.apirequest.weibo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by MikuNyanya on 2022/5/13 15:55
 * For the Reisen
 */
@NoArgsConstructor
@Data
public class ImageUploadPic1Info {
    @JSONField(name = "width")
    private Integer width;
    @JSONField(name = "size")
    private Integer size;
    @JSONField(name = "ret")
    private Integer ret;
    @JSONField(name = "height")
    private Integer height;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "pid")
    private String pid;
}
