package cn.mikulink.rabbitbot.entity.newpneumonia;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * created by MikuNyanya on 2022/3/15 15:43
 * For the Reisen
 */
@NoArgsConstructor
@Data
public class ComponentInfo {
    @JSONField(name = "backgroundColor")
    private String backgroundColor;
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "mapLastUpdatedTime")
    private String mapLastUpdatedTime;
    @JSONField(name = "type")
    private String type;
    @JSONField(name = "id")
    private String id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "version")
    private String version;
    @JSONField(name = "summaryDataIn")
    private SummaryDataInInfo summaryDataIn;
    @JSONField(name = "summaryDataOut")
    private SummaryDataOutInfo summaryDataOut;
    @JSONField(name = "share")
    private ShareInfo share;
    @JSONField(name = "hotwordsDuros")
    private String hotwordsDuros;
    @JSONField(name = "subtitle")
    private String subtitle;
    @JSONField(name = "foreignLastUpdatedTime")
    private String foreignLastUpdatedTime;
    @JSONField(name = "asymptomaticTopProvince")
    private List<AsymptomaticTopProvinceInfo> asymptomaticTopProvince;
    @JSONField(name = "newAddTopProvince")
    private List<NewAddTopProvinceInfo> newAddTopProvince;
}
