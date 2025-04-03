package cn.mikulink.rabbitbot.entity.bilibili;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BilibiliDynamicSvrCardsInfo {
    @JSONField(name = "desc")
    private BilibiliDynamicSvrCardsDescInfo desc;
    @JSONField(name = "card")
    private String card;
}
