package cn.mikulink.entity.apirequest.dxynCoV;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/3/24 15:28
 * for the Reisen
 * 全球疫情总览
 */
@Setter
@Getter
public class NCovWorldInfo {
    /**
     * id : 1
     * createTime : 1579537899000
     * modifyTime : 1585026519000
     * infectSource : 该字段已替换为说明2
     * passWay : 该字段已替换为说明3
     * imgUrl : https://img1.dxycdn.com/2020/0201/450/3394153392393266839-135.png
     * dailyPic : https://img1.dxycdn.com/2020/0211/763/3395998884005602079-135.png,https://img1.dxycdn.com/2020/0211/362/3395998896890788910-135.png,https://img1.dxycdn.com/2020/0211/365/3395998905480724211-135.png,https://img1.dxycdn.com/2020/0211/364/3395998916217859778-135.png,https://img1.dxycdn.com/2020/0211/922/3395998929103046444-135.png,https://img1.dxycdn.com/2020/0211/089/3395998939840182072-135.png
     * dailyPics : ["https://img1.dxycdn.com/2020/0211/763/3395998884005602079-135.png","https://img1.dxycdn.com/2020/0211/362/3395998896890788910-135.png","https://img1.dxycdn.com/2020/0211/365/3395998905480724211-135.png","https://img1.dxycdn.com/2020/0211/364/3395998916217859778-135.png","https://img1.dxycdn.com/2020/0211/922/3395998929103046444-135.png","https://img1.dxycdn.com/2020/0211/089/3395998939840182072-135.png"]
     * summary :
     * deleted : false
     * countRemark :
     * currentConfirmedCount : 5165
     * confirmedCount : 81748
     * suspectedCount : 427
     * curedCount : 73300
     * deadCount : 3283
     * seriousCount : 1573
     * suspectedIncr : 74
     * currentConfirmedIncr : -318
     * confirmedIncr : 148
     * curedIncr : 459
     * deadIncr : 7
     * seriousIncr : -176
     * virus : 该字段已替换为说明1
     * remark1 : 易感人群：人群普遍易感。老年人及有基础疾病者感染后病情较重，儿童及婴幼儿也有发病
     * remark2 : 潜伏期：一般为 3～7 天，最长不超过 14 天，潜伏期内可能存在传染性，其中无症状病例传染性非常罕见
     * remark3 : 宿主：野生动物，可能为中华菊头蝠
     * remark4 :
     * remark5 :
     * note1 : 病毒：SARS-CoV-2，其导致疾病命名 COVID-19
     * note2 : 传染源：新冠肺炎的患者。无症状感染者也可能成为传染源。
     * note3 : 传播途径：经呼吸道飞沫、接触传播是主要的传播途径。气溶胶传播和消化道等传播途径尚待明确。
     * generalRemark : 1. 3 月 12 日国家卫健委确诊补订遗漏 12 例确诊病例（非 12 日新增），暂无具体省份信息。 2. 浙江省 12 例外省治愈暂无具体省份信息。
     * abroadRemark :
     * marquee : [{"id":1698,"marqueeLabel":"欧洲互助","marqueeContent":"德国边境城市为法国患者提供治疗","marqueeLink":"https://dxy.me/pVAfJG"},{"id":1699,"marqueeLabel":"武汉","marqueeContent":"新增 1 例确诊是省人民医院医生","marqueeLink":"https://dxy.me/yYvwND"},{"id":1700,"marqueeLabel":"武汉","marqueeContent":"卫健委答复市民关心的无症状感染问题","marqueeLink":"https://dxy.me/j3db5X"}]
     * quanguoTrendChart : [{"imgUrl":"https://img1.dxycdn.com/2020/0324/278/3403801351376518263-135.png","title":"新增疑似/新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0324/243/3403801370703633541-135.png","title":"现存确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0324/285/3403801390031225557-135.png","title":"现存疑似"},{"imgUrl":"https://img1.dxycdn.com/2020/0324/775/3403801407211095604-135.png","title":"治愈"},{"imgUrl":"https://img1.dxycdn.com/2020/0324/250/3403801424390727199-135.png","title":"死亡"}]
     * hbFeiHbTrendChart : [{"imgUrl":"https://img1.dxycdn.com/2020/0324/716/3403801667056390310-135.png","title":"非湖北新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0324/212/3403801688531227742-135.png","title":"湖北新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0324/249/3403801710006303112-135.png","title":"湖北现存确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0324/324/3403801731481140521-135.png","title":"非湖北现存确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0324/770/3403801750808494166-135.png","title":"治愈/死亡"}]
     * foreignTrendChart : [{"imgUrl":"https://img1.dxycdn.com/2020/0320/702/3403079914982117944-135.png","title":"国外新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0320/396/3403079925719536561-135.png","title":"国外累计确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0320/139/3403079936457225524-135.png","title":"国外死亡"}]
     * importantForeignTrendChart : [{"imgUrl":"https://img1.dxycdn.com/2020/0320/450/3403077453965772423-135.png","title":"重点国家新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0320/276/3403077462555980566-135.png","title":"日本新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0320/340/3403077473293125942-135.png","title":"意大利新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0320/723/3403077481883334043-135.png","title":"伊朗新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0320/266/3403077492620752785-135.png","title":"美国新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0320/870/3403077503358171278-135.png","title":"法国新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0320/053/3403077511947832997-135.png","title":"德国新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0320/878/3403077520537768006-135.png","title":"西班牙新增确诊"},{"imgUrl":"https://img1.dxycdn.com/2020/0320/443/3403077531275186665-135.png","title":"韩国新增确诊"}]
     * foreignTrendChartGlobal : [{"imgUrl":"https://img1.dxycdn.com/2020/0319/740/3402859458605738923-135.png","title":"Daily New Cases"},{"imgUrl":"https://img1.dxycdn.com/2020/0319/351/3402856772103846114-135.png","title":"Total Confirmed Cases"},{"imgUrl":"https://img1.dxycdn.com/2020/0319/236/3402856782841001789-135.png","title":"Total Deaths Cases"}]
     * importantForeignTrendChartGlobal : [{"imgUrl":"https://img1.dxycdn.com/2020/0319/122/3402856838675578578-135.png","title":"Daily New Cases"},{"imgUrl":"https://img1.dxycdn.com/2020/0319/685/3402856849412997239-135.png","title":"Japan"},{"imgUrl":"https://img1.dxycdn.com/2020/0319/477/3402856860150678804-135.png","title":"Italy"},{"imgUrl":"https://img1.dxycdn.com/2020/0319/454/3402856873035581014-135.png","title":"Iran"},{"imgUrl":"https://img1.dxycdn.com/2020/0319/022/3402856883772999714-135.png","title":"U.S.A."},{"imgUrl":"https://img1.dxycdn.com/2020/0319/529/3402856894510155506-135.png","title":"France"},{"imgUrl":"https://img1.dxycdn.com/2020/0319/211/3402856905247574064-135.png","title":"Germany"},{"imgUrl":"https://img1.dxycdn.com/2020/0319/054/3402856913837508985-135.png","title":"Spain"},{"imgUrl":"https://img1.dxycdn.com/2020/0319/088/3402856928870157852-135.png","title":"Republic of Korea"}]
     * foreignStatistics : {"currentConfirmedCount":255409,"confirmedCount":296110,"suspectedCount":4,"curedCount":27604,"deadCount":13097,"suspectedIncr":0,"currentConfirmedIncr":4009,"confirmedIncr":4499,"curedIncr":381,"deadIncr":109}
     * globalStatistics : {"currentConfirmedCount":260574,"confirmedCount":377858,"curedCount":100904,"deadCount":16380,"currentConfirmedIncr":3691,"confirmedIncr":4647,"curedIncr":840,"deadIncr":116}
     * globalOtherTrendChartData : https://file1.dxycdn.com/2020/0319/925/3402966424766255493-135.json
     */

    private Long id;
    private Long createTime;
    private Long modifyTime;
    private String infectSource;
    private String passWay;
    private String imgUrl;
    private String dailyPic;
    private String summary;
    private Boolean deleted;
    private String countRemark;
    private Integer currentConfirmedCount;
    private Integer confirmedCount;
    private Integer suspectedCount;
    private Integer curedCount;
    private Integer deadCount;
    private Integer seriousCount;
    private Integer suspectedIncr;
    private Integer currentConfirmedIncr;
    private Integer confirmedIncr;
    private Integer curedIncr;
    private Integer deadIncr;
    private Integer seriousIncr;
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
    private ForeignStatisticsBean foreignStatistics;
    private GlobalStatisticsBean globalStatistics;
    private String globalOtherTrendChartData;
    private List<String> dailyPics;
    private List<NocvImageInfo> quanguoTrendChart;
    private List<NocvImageInfo> foreignTrendChart;
    private List<NocvImageInfo> importantForeignTrendChart;

    @Setter
    @Getter
    public static class ForeignStatisticsBean {
        /**
         * 不包含中国的数据
         * currentConfirmedCount : 255409
         * confirmedCount : 296110
         * suspectedCount : 4
         * curedCount : 27604
         * deadCount : 13097
         * suspectedIncr : 0
         * currentConfirmedIncr : 4009
         * confirmedIncr : 4499
         * curedIncr : 381
         * deadIncr : 109
         */

        //现存确诊
        private Integer currentConfirmedCount;
        //累积确诊
        private Integer confirmedCount;
        private Integer suspectedCount;
        //累积治愈
        private Integer curedCount;
        //累积死亡
        private Integer deadCount;
        private Integer suspectedIncr;
        //现存确诊数据变动
        private Integer currentConfirmedIncr;
        //累积确诊数据变动
        private Integer confirmedIncr;
        //累积治愈数据变动
        private Integer curedIncr;
        //累积死亡数据变动
        private Integer deadIncr;
    }

    @Setter
    @Getter
    public static class GlobalStatisticsBean {
        /**
         * 包含中国的数据
         * currentConfirmedCount : 260574
         * confirmedCount : 377858
         * curedCount : 100904
         * deadCount : 16380
         * currentConfirmedIncr : 3691
         * confirmedIncr : 4647
         * curedIncr : 840
         * deadIncr : 116
         */

        private Integer currentConfirmedCount;
        private Integer confirmedCount;
        private Integer curedCount;
        private Integer deadCount;
        private Integer currentConfirmedIncr;
        private Integer confirmedIncr;
        private Integer curedIncr;
        private Integer deadIncr;

    }

    @Setter
    @Getter
    public static class NocvImageInfo {
        /**
         * imgUrl : https://img1.dxycdn.com/2020/0324/278/3403801351376518263-135.png
         * title : 新增疑似/新增确诊
         */

        private String imgUrl;
        private String title;
    }
}
