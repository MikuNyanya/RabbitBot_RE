package cn.mikulink.entity.apirequest.dxynCoV;

import lombok.Getter;
import lombok.Setter;

/**
 * @author MikuLink
 * @date 2020/1/31 23:28
 * for the Reisen
 * 省份级别的疫情信息
 * GsonFormat生成
 */
@Getter
@Setter
public class InfoAllnCoV {

    /**
     * id : 1
     * createTime : 1579537899000
     * modifyTime : 1580481674000
     * infectSource : 野生动物，可能为中华菊头蝠
     * passWay : 经呼吸道飞沫传播，亦可通过接触传播
     * imgUrl : https://img1.dxycdn.com/2020/0131/214/3394045773397760494-73.png
     * dailyPic : https://img1.dxycdn.com/2020/0131/475/3394029770349575031-73.jpg
     * summary :
     * deleted : false
     * countRemark :
     * confirmedCount : 9811
     * suspectedCount : 15238
     * curedCount : 214
     * deadCount : 213
     * virus : 新型冠状病毒 2019-nCoV
     * remark1 : 易感人群: 人群普遍易感。老年人及有基础疾病者感染后病情较重，儿童及婴幼儿也有发病
     * remark2 : 潜伏期: 一般为 3~7 天，最长不超过 14 天，潜伏期内存在传染性
     * remark3 :
     * remark4 :
     * remark5 :
     * generalRemark : 疑似病例数来自国家卫健委数据，目前为全国数据，未分省市自治区等
     * abroadRemark :
     */

    private int id;
    private long createTime;
    private long modifyTime;
    private String infectSource;
    private String passWay;
    private String imgUrl;
    private String dailyPic;
    private String summary;
    private boolean deleted;
    private String countRemark;
    private int currentConfirmedCount;
    private int confirmedCount;
    private int suspectedCount;
    private int seriousCount;
    private int curedCount;
    private int deadCount;
    private String virus;
    private String remark1;
    private String remark2;
    private String remark3;
    private String remark4;
    private String remark5;
    private String note1;
    private String note2;
    private String note3;
    private String generalRemark;
    private String abroadRemark;
}
