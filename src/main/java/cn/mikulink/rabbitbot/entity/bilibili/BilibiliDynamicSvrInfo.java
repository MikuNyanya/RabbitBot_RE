package cn.mikulink.rabbitbot.entity.bilibili;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class BilibiliDynamicSvrInfo {
    @JSONField(name = "cards")
    private List<BilibiliDynamicSvrCardsInfo> cards;
    @JSONField(name = "max_dynamic_id")
    private Long maxDynamicId;
    @JSONField(name = "history_offset")
    private Long historyOffset;
}
