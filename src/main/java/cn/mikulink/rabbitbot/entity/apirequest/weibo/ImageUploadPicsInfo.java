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
public class ImageUploadPicsInfo {
    @JSONField(name = "pic_1")
    private ImageUploadPic1Info pic1;
}
