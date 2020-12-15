package cn.mikulink.service.greetings;

import cn.mikulink.utils.DateUtil;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2020/1/17 14:40
 * for the Reisen
 * 时间问候，中午好
 */
public class GreetingsNoon extends GreetingsBase {

    @Override
    protected List<String> beforeDawn() {
        return Arrays.asList(
                "？",
                "你是在地球的另一端嘛",
                "又疯一个，没救了",
                "别闹了，都凌晨" + DateUtil.getHour() + "点了，赶紧睡觉去",
                "都几点了还不睡觉，等着猝死吧！"
        );
    }

    @Override
    protected List<String> morning() {
        return Arrays.asList(
                "离中午还早呐",
                "还没到中午呐，这么急着吃饭嘛",
                "中午还得过会"
        );
    }

    @Override
    protected List<String> noon() {
        return Arrays.asList(
                "午安",
                "午安~",
                "中午好",
                "中午好~",
                "It's High----------- noon"
        );
    }

    @Override
    protected List<String> afternoon() {
        return Arrays.asList(
                "中午过去了哦",
                "下午了"
        );
    }

    @Override
    protected List<String> night() {
        return Arrays.asList(
                "你是在地球的另一端嘛",
                "已经晚上了",
                "看外面，天都黑了好嘛"
        );
    }
}
