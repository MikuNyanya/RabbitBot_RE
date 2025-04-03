package cn.mikulink.rabbitbot.entity.apirequest.weibo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by MikuNyanya on 2022/5/13 15:55
 * For the Reisen
 * 上传图库后返回信息
 */
@NoArgsConstructor
@Data
public class ImageUploadResponseInfo {

    @JSONField(name = "code")
    private String code;
    @JSONField(name = "data")
    private ImageUploadDataInfo data;
}
