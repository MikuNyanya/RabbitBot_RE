package cn.mikulink.rabbitbot.entity.apirequest.bilibili;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BilibiliUserProfileInfo {
    @JSONField(name = "info")
    private BilibiliUserProfileInfoInfo info;
    @JSONField(name = "sign")
    private String sign;
}
