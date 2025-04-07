package cn.mikulink.rabbitbot.bot.penguincenter.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * MikuLink created in 2025/4/7 15:17
 * For the Reisen
 */
@NoArgsConstructor
@Data
public class NotcieInfo {


    @JSONField(name = "time")
    private Long time;
    @JSONField(name = "self_id")
    private Long selfId;
    @JSONField(name = "post_type")
    private String postType;
    @JSONField(name = "notice_type")
    private String noticeType;
    @JSONField(name = "sub_type")
    private String subType;
    @JSONField(name = "target_id")
    private Long targetId;
    @JSONField(name = "user_id")
    private Long userId;
    @JSONField(name = "group_id")
    private Long groupId;
    @JSONField(name = "raw_info")
    private List<RawInfoDTO> rawInfo;

    @NoArgsConstructor
    @Data
    public static class RawInfoDTO {
        @JSONField(name = "col")
        private String col;
        @JSONField(name = "nm")
        private String nm;
        @JSONField(name = "type")
        private String type;
        @JSONField(name = "uid")
        private String uid;
        @JSONField(name = "jp")
        private String jp;
        @JSONField(name = "src")
        private String src;
        @JSONField(name = "txt")
        private String txt;
        @JSONField(name = "tp")
        private String tp;
    }
}
