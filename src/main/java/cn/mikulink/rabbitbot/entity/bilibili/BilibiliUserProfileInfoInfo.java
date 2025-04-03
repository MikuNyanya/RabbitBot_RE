package cn.mikulink.rabbitbot.entity.bilibili;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BilibiliUserProfileInfoInfo {
    @JSONField(name = "uid")
    private Long uid;
    @JSONField(name = "uname")
    private String uname;
    @JSONField(name = "face")
    private String face;
}
