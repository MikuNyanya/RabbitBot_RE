package cn.mikulink.rabbitbot.entity.apirequest.news;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class NewsTodayEntity {

    /**
     * code : 200
     * data : {"_intro":"特性：1、支持传入ip参数,获取指定ip地址所在城市的天气预报. 例如http://news.topurl.cn/api?ip=101.68.1.1；2、每日18点前播报当日天气,18点后预报明日天气；3、calendar.term代表节气；4、支持https；5、单个ip15秒内最多调用15次。","calendar":{"lYear":2021,"lMonth":1,"lDay":18,"animal":"牛","yearCn":"二零二一年","monthCn":"正月","dayCn":"十八","cYear":2021,"cMonth":3,"cDay":1,"gzYear":"辛丑","gzMonth":"庚寅","gzDay":"戊申","isToday":true,"isLeap":false,"nWeek":1,"ncWeek":"星期一","isTerm":false,"term":""},"historyList":[{"event":"1954年3月1日，第一枚真正的氢弹试验成功。"},{"event":"1932年3月1日，日军再次发起猛攻，第十九路军撤退，淞沪战事结束。"},{"event":"1998年3月1日，\u201c神医\u201d胡万林骗局被揭穿。"}],"newsList":[{"title":"耍官威现象呈现暴力化、校园化特点，\u201c拜权主义\u201d亟待破除","url":"https://www.thepaper.cn/newsDetail_forward_11503560","category":"时事"},{"title":"长江保护法今起实施，有望破解\u201c九龙治水\u201d困境","url":"https://www.jiemian.com/article/5740197.html","category":"国内"},{"title":"丰田燃料电池技术变现，新开发FC模块向产业链上游兜售","url":"https://www.jiemian.com/article/5738248.html","category":"商业"},{"title":"印度总理莫迪接种第一剂新冠疫苗，该疫苗系印本土研发","url":"https://www.thepaper.cn/newsDetail_forward_11503712","category":"时事"},{"title":"教育部：2020年全国高等教育毛入学率54.4%","url":"https://www.thepaper.cn/newsDetail_forward_11504953","category":"时事"},{"title":"79岁教师申诉强奸案40余年：涉事女生愿作证，法院暂未受理","url":"https://www.thepaper.cn/newsDetail_forward_11504881","category":"时事"},{"title":"驻马店妇联残联回应\u201c智障女被嫁中年男\u201d视频：多部门正核查","url":"https://www.thepaper.cn/newsDetail_forward_11503607","category":"时事"},{"title":"前喜茶CTO辞职创业做SaaS：我不想一辈子卖糖水","url":"https://www.jiemian.com/article/5734840.html","category":"商业"},{"title":"2020款宝马M235i xDrive Gran Coupe：终极身份危机","url":"https://www.jiemian.com/article/5739399.html","category":"商业"},{"title":"中国最大民营船企去年净利降近两成，但新接订单总价翻番","url":"https://www.jiemian.com/article/5741507.html","category":"商业"},{"title":"教育部：2020年全国在校生2.89亿人，高等教育毛入学率54.4%","url":"https://www.jiemian.com/article/5742299.html","category":"国内"},{"title":"生物科技公司西湖欧米完成数千万元种子轮融资","url":"https://www.jiemian.com/article/5741943.html","category":"科技"}],"phrase":{"phrase":"扶危济困","explain":"扶：帮助；济：搭救，拯救。扶助有危难的人，救济困苦的人。","from":"明·施耐庵《水浒全传》第五十五回：\u201c素知将军仗义行仁，扶危济困，不想果然如此义气。\u201d","example":"在这篇作品中，突出体现了主人公为人民～的英雄主义气慨。","simple":"fwjk","pinyin":"fú wēi jì kùn"},"sentence":{"wrong":false,"author":"韩非","sentence":"侈而惰者贫，而力而俭者富。"},"poem":{"content":["柳门柳门，芳草芊绵。","日日日日，黯然黯然。","子牟恋阙归阙，王粲下楼相别。","食实得地，颇淹岁月。","今朝天子在上，合雪必雪。","况绛之牧，文行炳洁。","释谓缘因，久昵清尘。","王嘉迎安，远狎遗民。","嫓彼二子，厥或相似。","论文不文，话道无滓。","士有贵逼，势不可遏。","麟步规矩，凤翥昂枿。","岘首仁踪项频跋，商云乳麝香可撮。","望尘□□连紫闼，吾皇必用整干坤，莫忘江头白头达。"],"type":"poem","title":"送崔使君","author":"贯休"},"weather":{"city":"上海","weatherOf":"today","detail":{"date":"2021-03-01","text_day":"小雨","code_day":"13","text_night":"小雨","code_night":"13","high":"17","low":"6","rainfall":"6.3","precip":"","wind_direction":"西北","wind_direction_degree":"315","wind_speed":"23.4","wind_scale":"4","humidity":"86"}}}
     * message : 成功
     */

    @JSONField(name = "code")
    private Integer code;
    @JSONField(name = "data")
    private DataDTO data;
    @JSONField(name = "message")
    private String message;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        /**
         * _intro : 特性：1、支持传入ip参数,获取指定ip地址所在城市的天气预报. 例如http://news.topurl.cn/api?ip=101.68.1.1；2、每日18点前播报当日天气,18点后预报明日天气；3、calendar.term代表节气；4、支持https；5、单个ip15秒内最多调用15次。
         * calendar : {"lYear":2021,"lMonth":1,"lDay":18,"animal":"牛","yearCn":"二零二一年","monthCn":"正月","dayCn":"十八","cYear":2021,"cMonth":3,"cDay":1,"gzYear":"辛丑","gzMonth":"庚寅","gzDay":"戊申","isToday":true,"isLeap":false,"nWeek":1,"ncWeek":"星期一","isTerm":false,"term":""}
         * historyList : [{"event":"1954年3月1日，第一枚真正的氢弹试验成功。"},{"event":"1932年3月1日，日军再次发起猛攻，第十九路军撤退，淞沪战事结束。"},{"event":"1998年3月1日，\u201c神医\u201d胡万林骗局被揭穿。"}]
         * newsList : [{"title":"耍官威现象呈现暴力化、校园化特点，\u201c拜权主义\u201d亟待破除","url":"https://www.thepaper.cn/newsDetail_forward_11503560","category":"时事"},{"title":"长江保护法今起实施，有望破解\u201c九龙治水\u201d困境","url":"https://www.jiemian.com/article/5740197.html","category":"国内"},{"title":"丰田燃料电池技术变现，新开发FC模块向产业链上游兜售","url":"https://www.jiemian.com/article/5738248.html","category":"商业"},{"title":"印度总理莫迪接种第一剂新冠疫苗，该疫苗系印本土研发","url":"https://www.thepaper.cn/newsDetail_forward_11503712","category":"时事"},{"title":"教育部：2020年全国高等教育毛入学率54.4%","url":"https://www.thepaper.cn/newsDetail_forward_11504953","category":"时事"},{"title":"79岁教师申诉强奸案40余年：涉事女生愿作证，法院暂未受理","url":"https://www.thepaper.cn/newsDetail_forward_11504881","category":"时事"},{"title":"驻马店妇联残联回应\u201c智障女被嫁中年男\u201d视频：多部门正核查","url":"https://www.thepaper.cn/newsDetail_forward_11503607","category":"时事"},{"title":"前喜茶CTO辞职创业做SaaS：我不想一辈子卖糖水","url":"https://www.jiemian.com/article/5734840.html","category":"商业"},{"title":"2020款宝马M235i xDrive Gran Coupe：终极身份危机","url":"https://www.jiemian.com/article/5739399.html","category":"商业"},{"title":"中国最大民营船企去年净利降近两成，但新接订单总价翻番","url":"https://www.jiemian.com/article/5741507.html","category":"商业"},{"title":"教育部：2020年全国在校生2.89亿人，高等教育毛入学率54.4%","url":"https://www.jiemian.com/article/5742299.html","category":"国内"},{"title":"生物科技公司西湖欧米完成数千万元种子轮融资","url":"https://www.jiemian.com/article/5741943.html","category":"科技"}]
         * phrase : {"phrase":"扶危济困","explain":"扶：帮助；济：搭救，拯救。扶助有危难的人，救济困苦的人。","from":"明·施耐庵《水浒全传》第五十五回：\u201c素知将军仗义行仁，扶危济困，不想果然如此义气。\u201d","example":"在这篇作品中，突出体现了主人公为人民～的英雄主义气慨。","simple":"fwjk","pinyin":"fú wēi jì kùn"}
         * sentence : {"wrong":false,"author":"韩非","sentence":"侈而惰者贫，而力而俭者富。"}
         * poem : {"content":["柳门柳门，芳草芊绵。","日日日日，黯然黯然。","子牟恋阙归阙，王粲下楼相别。","食实得地，颇淹岁月。","今朝天子在上，合雪必雪。","况绛之牧，文行炳洁。","释谓缘因，久昵清尘。","王嘉迎安，远狎遗民。","嫓彼二子，厥或相似。","论文不文，话道无滓。","士有贵逼，势不可遏。","麟步规矩，凤翥昂枿。","岘首仁踪项频跋，商云乳麝香可撮。","望尘□□连紫闼，吾皇必用整干坤，莫忘江头白头达。"],"type":"poem","title":"送崔使君","author":"贯休"}
         * weather : {"city":"上海","weatherOf":"today","detail":{"date":"2021-03-01","text_day":"小雨","code_day":"13","text_night":"小雨","code_night":"13","high":"17","low":"6","rainfall":"6.3","precip":"","wind_direction":"西北","wind_direction_degree":"315","wind_speed":"23.4","wind_scale":"4","humidity":"86"}}
         */

        @JSONField(name = "_intro")
        private String intro;
        @JSONField(name = "calendar")
        private CalendarDTO calendar;
        @JSONField(name = "historyList")
        private List<HistoryListDTO> historyList;
        @JSONField(name = "newsList")
        private List<NewsListDTO> newsList;
        @JSONField(name = "phrase")
        private PhraseDTO phrase;
        @JSONField(name = "sentence")
        private SentenceDTO sentence;
        @JSONField(name = "poem")
        private PoemDTO poem;
        @JSONField(name = "weather")
        private WeatherDTO weather;

        @NoArgsConstructor
        @Data
        public static class CalendarDTO {
            /**
             * lYear : 2021
             * lMonth : 1
             * lDay : 18
             * animal : 牛
             * yearCn : 二零二一年
             * monthCn : 正月
             * dayCn : 十八
             * cYear : 2021
             * cMonth : 3
             * cDay : 1
             * gzYear : 辛丑
             * gzMonth : 庚寅
             * gzDay : 戊申
             * isToday : true
             * isLeap : false
             * nWeek : 1
             * ncWeek : 星期一
             * isTerm : false
             * term :
             */

            @JSONField(name = "lYear")
            private Integer lYear;
            @JSONField(name = "lMonth")
            private Integer lMonth;
            @JSONField(name = "lDay")
            private Integer lDay;
            @JSONField(name = "animal")
            private String animal;
            @JSONField(name = "yearCn")
            private String yearCn;
            @JSONField(name = "monthCn")
            private String monthCn;
            @JSONField(name = "dayCn")
            private String dayCn;
            @JSONField(name = "cYear")
            private Integer cYear;
            @JSONField(name = "cMonth")
            private Integer cMonth;
            @JSONField(name = "cDay")
            private Integer cDay;
            @JSONField(name = "gzYear")
            private String gzYear;
            @JSONField(name = "gzMonth")
            private String gzMonth;
            @JSONField(name = "gzDay")
            private String gzDay;
            @JSONField(name = "isToday")
            private Boolean isToday;
            @JSONField(name = "isLeap")
            private Boolean isLeap;
            @JSONField(name = "nWeek")
            private Integer nWeek;
            @JSONField(name = "ncWeek")
            private String ncWeek;
            @JSONField(name = "isTerm")
            private Boolean isTerm;
            @JSONField(name = "term")
            private String term;
        }

        @NoArgsConstructor
        @Data
        public static class PhraseDTO {
            /**
             * phrase : 扶危济困
             * explain : 扶：帮助；济：搭救，拯救。扶助有危难的人，救济困苦的人。
             * from : 明·施耐庵《水浒全传》第五十五回：“素知将军仗义行仁，扶危济困，不想果然如此义气。”
             * example : 在这篇作品中，突出体现了主人公为人民～的英雄主义气慨。
             * simple : fwjk
             * pinyin : fú wēi jì kùn
             */

            @JSONField(name = "phrase")
            private String phrase;
            @JSONField(name = "explain")
            private String explain;
            @JSONField(name = "from")
            private String from;
            @JSONField(name = "example")
            private String example;
            @JSONField(name = "simple")
            private String simple;
            @JSONField(name = "pinyin")
            private String pinyin;
        }

        @NoArgsConstructor
        @Data
        public static class SentenceDTO {
            /**
             * wrong : false
             * author : 韩非
             * sentence : 侈而惰者贫，而力而俭者富。
             */

            @JSONField(name = "wrong")
            private Boolean wrong;
            @JSONField(name = "author")
            private String author;
            @JSONField(name = "sentence")
            private String sentence;
        }

        @NoArgsConstructor
        @Data
        public static class PoemDTO {
            /**
             * content : ["柳门柳门，芳草芊绵。","日日日日，黯然黯然。","子牟恋阙归阙，王粲下楼相别。","食实得地，颇淹岁月。","今朝天子在上，合雪必雪。","况绛之牧，文行炳洁。","释谓缘因，久昵清尘。","王嘉迎安，远狎遗民。","嫓彼二子，厥或相似。","论文不文，话道无滓。","士有贵逼，势不可遏。","麟步规矩，凤翥昂枿。","岘首仁踪项频跋，商云乳麝香可撮。","望尘□□连紫闼，吾皇必用整干坤，莫忘江头白头达。"]
             * type : poem
             * title : 送崔使君
             * author : 贯休
             */

            @JSONField(name = "content")
            private List<String> content;
            @JSONField(name = "type")
            private String type;
            @JSONField(name = "title")
            private String title;
            @JSONField(name = "author")
            private String author;
        }

        @NoArgsConstructor
        @Data
        public static class WeatherDTO {
            /**
             * city : 上海
             * weatherOf : today
             * detail : {"date":"2021-03-01","text_day":"小雨","code_day":"13","text_night":"小雨","code_night":"13","high":"17","low":"6","rainfall":"6.3","precip":"","wind_direction":"西北","wind_direction_degree":"315","wind_speed":"23.4","wind_scale":"4","humidity":"86"}
             */

            @JSONField(name = "city")
            private String city;
            @JSONField(name = "weatherOf")
            private String weatherOf;
            @JSONField(name = "detail")
            private DetailDTO detail;

            @NoArgsConstructor
            @Data
            public static class DetailDTO {
                /**
                 * date : 2021-03-01
                 * text_day : 小雨
                 * code_day : 13
                 * text_night : 小雨
                 * code_night : 13
                 * high : 17
                 * low : 6
                 * rainfall : 6.3
                 * precip :
                 * wind_direction : 西北
                 * wind_direction_degree : 315
                 * wind_speed : 23.4
                 * wind_scale : 4
                 * humidity : 86
                 */

                @JSONField(name = "date")
                private String date;
                @JSONField(name = "text_day")
                private String textDay;
                @JSONField(name = "code_day")
                private String codeDay;
                @JSONField(name = "text_night")
                private String textNight;
                @JSONField(name = "code_night")
                private String codeNight;
                @JSONField(name = "high")
                private String high;
                @JSONField(name = "low")
                private String low;
                @JSONField(name = "rainfall")
                private String rainfall;
                @JSONField(name = "precip")
                private String precip;
                @JSONField(name = "wind_direction")
                private String windDirection;
                @JSONField(name = "wind_direction_degree")
                private String windDirectionDegree;
                @JSONField(name = "wind_speed")
                private String windSpeed;
                @JSONField(name = "wind_scale")
                private String windScale;
                @JSONField(name = "humidity")
                private String humidity;
            }
        }

        @NoArgsConstructor
        @Data
        public static class HistoryListDTO {
            /**
             * event : 1954年3月1日，第一枚真正的氢弹试验成功。
             */

            @JSONField(name = "event")
            private String event;
        }

        @NoArgsConstructor
        @Data
        public static class NewsListDTO {
            /**
             * title : 耍官威现象呈现暴力化、校园化特点，“拜权主义”亟待破除
             * url : https://www.thepaper.cn/newsDetail_forward_11503560
             * category : 时事
             */

            @JSONField(name = "title")
            private String title;
            @JSONField(name = "url")
            private String url;
            @JSONField(name = "category")
            private String category;
        }
    }
}
