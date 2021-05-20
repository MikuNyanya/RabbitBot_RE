package cn.mikulink.rabbitbot.entity.bilibili;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BilibiliDynamicSvrCardsDescInfo {
    @JSONField(name = "uid")
    private Long uid;
    @JSONField(name = "dynamic_id")
    private Long dynamicId;
    @JSONField(name = "timestamp")
    private Integer timestamp;
    @JSONField(name = "user_profile")
    private BilibiliUserProfileInfo userProfile;
    @JSONField(name = "dynamic_id_str")
    private String dynamicIdStr;
    @JSONField(name = "bvid")
    private String bvid;
    @JSONField(name = "type")
    private Integer type;
}
