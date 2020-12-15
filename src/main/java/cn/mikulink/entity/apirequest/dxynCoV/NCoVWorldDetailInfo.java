package cn.mikulink.entity.apirequest.dxynCoV;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/3/24 15:13
 * for the Reisen
 * 国外疫情详情信息
 */
@Setter
@Getter
public class NCoVWorldDetailInfo {
    /**
     * id : 1650092
     * createTime : 1585026518000
     * modifyTime : 1585026518000
     * tags :
     * countryType : 2
     * continents : 欧洲
     * provinceId : 10
     * provinceName : 意大利
     * provinceShortName :
     * cityName :
     * currentConfirmedCount : 50869
     * confirmedCount : 64378
     * confirmedCountRank : 2
     * suspectedCount : 0
     * curedCount : 7432
     * deadCount : 6077
     * deadCountRank : 1
     * deadRate : 9.43
     * deadRateRank : 6
     * comment :
     * sort : 0
     * operator : xinyingxu
     * locationId : 965008
     * countryShortCode : ITA
     * countryFullName : Italy
     * statisticsData : https://file1.dxycdn.com/2020/0315/993/3402160517102843054-135.json
     */

    private Long id;
    private Long createTime;
    private Long modifyTime;
    private String tags;
    private Integer countryType;
    private String continents;
    private String provinceId;
    private String provinceName;
    private String provinceShortName;
    private String cityName;
    private Integer currentConfirmedCount;
    private Integer confirmedCount;
    private Integer confirmedCountRank;
    private Integer suspectedCount;
    private Integer curedCount;
    private Integer deadCount;
    private Integer deadCountRank;
    private String deadRate;
    private Integer deadRateRank;
    private String comment;
    private Integer sort;
    private String operator;
    private Long locationId;
    private String countryShortCode;
    private String countryFullName;
    private String statisticsData;
}
