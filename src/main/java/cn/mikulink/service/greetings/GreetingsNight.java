package cn.mikulink.service.greetings;


import cn.mikulink.utils.DateUtil;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2020/1/17 14:40
 * for the Reisen
 * 时间问候，晚上好
 */
public class GreetingsNight extends GreetingsBase {

    @Override
    protected List<String> beforeDawn() {
        return Arrays.asList(
                "都凌晨" + DateUtil.getHour() + "点了，赶紧睡觉去",
                "都几点了还不睡觉，等着猝死吧！",
                "这都几点了，竟然还有人不睡觉"
        );
    }

    @Override
    protected List<String> morning() {
        return Arrays.asList(
                "晚上好~先去吃个早饭吧",
                "？",
                "？？？"
        );
    }

    @Override
    protected List<String> noon() {
        return Arrays.asList(
                "晚上好，你看这都" + DateUtil.getHour() + "点了",
                "。。。",
                "大中午的被太阳晒出幻觉了嘛",
                "您可能是IE浏览器的受害者",
                "你是在地球的另一端嘛"
        );
    }

    @Override
    protected List<String> afternoon() {
        return Arrays.asList(
                "晚上好，来杯下午茶吧",
                "是睡迷糊了嘛",
                "再睡一会就是晚上了"
        );
    }

    @Override
    protected List<String> night() {
        return Arrays.asList(
                "晚上好~",
                "晚上好哦",
                "晚上好，记得吃顿好的"
        );
    }
}
