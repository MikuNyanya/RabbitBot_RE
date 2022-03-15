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
public class SummaryDataInInfo {
    //现有确诊
    @JSONField(name = "curConfirm")
    private String curConfirm;
    //较昨日增长
    @JSONField(name = "curConfirmRelative")
    private String curConfirmRelative;
    //累积确诊
    @JSONField(name = "confirmed")
    private String confirmed;
    @JSONField(name = "confirmedRelative")
    private String confirmedRelative;
    //无症状
    @JSONField(name = "asymptomatic")
    private String asymptomatic;
    @JSONField(name = "asymptomaticRelative")
    private String asymptomaticRelative;
    //现有疑似
    @JSONField(name = "unconfirmed")
    private String unconfirmed;
    @JSONField(name = "unconfirmedRelative")
    private String unconfirmedRelative;
    //现有重症
    @JSONField(name = "icu")
    private String icu;
    @JSONField(name = "icuRelative")
    private String icuRelative;
    //境外输入
    @JSONField(name = "overseasInput")
    private String overseasInput;
    @JSONField(name = "overseasInputRelative")
    private String overseasInputRelative;
    //累积治愈
    @JSONField(name = "cured")
    private String cured;
    @JSONField(name = "curedRelative")
    private String curedRelative;
    //累积死亡
    @JSONField(name = "died")
    private String died;
    @JSONField(name = "diedRelative")
    private String diedRelative;

    @JSONField(name = "relativeTime")
    private String relativeTime;
    @JSONField(name = "unOverseasInputCumulative")
    private String unOverseasInputCumulative;
    @JSONField(name = "unOverseasInputNewAdd")
    private String unOverseasInputNewAdd;
    @JSONField(name = "icuDisable")
    private String icuDisable;
}
