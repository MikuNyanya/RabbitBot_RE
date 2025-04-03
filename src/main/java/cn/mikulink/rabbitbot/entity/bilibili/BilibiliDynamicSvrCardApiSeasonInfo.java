package cn.mikulink.rabbitbot.entity.bilibili;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BilibiliDynamicSvrCardApiSeasonInfo {
    @JSONField(name = "bgm_type")
    private Integer bgmType;
    @JSONField(name = "cover")
    private String cover;
    @JSONField(name = "is_finish")
    private Integer isFinish;
    @JSONField(name = "season_id")
    private Long seasonId;
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "total_count")
    private Integer totalCount;
    @JSONField(name = "ts")
    private Integer ts;
    @JSONField(name = "type_name")
    private String typeName;
}
