package cn.mikulink.rabbitbot.entity.newpneumonia;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by MikuNyanya on 2022/3/15 15:43
 * For the Reisen
 */
@NoArgsConstructor
@Data
public class SummaryDataOutInfo {
    @JSONField(name = "confirmed")
    private String confirmed;
    @JSONField(name = "died")
    private String died;
    @JSONField(name = "curConfirm")
    private String curConfirm;
    @JSONField(name = "cured")
    private String cured;
    @JSONField(name = "confirmedRelative")
    private String confirmedRelative;
    @JSONField(name = "curedRelative")
    private String curedRelative;
    @JSONField(name = "diedRelative")
    private String diedRelative;
    @JSONField(name = "curConfirmRelative")
    private String curConfirmRelative;
    @JSONField(name = "relativeTime")
    private String relativeTime;
    @JSONField(name = "curedPercent")
    private String curedPercent;
    @JSONField(name = "diedPercent")
    private String diedPercent;
}
