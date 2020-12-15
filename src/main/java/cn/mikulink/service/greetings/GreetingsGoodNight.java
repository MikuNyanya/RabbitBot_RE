package cn.mikulink.service.greetings;


import cn.mikulink.utils.DateUtil;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2020/1/17 14:40
 * for the Reisen
 * 时间问候，晚安
 */
public class GreetingsGoodNight extends GreetingsBase {

    @Override
    protected List<String> beforeDawn() {
        return Arrays.asList(
                "晚安",
                "晚安，这么晚才睡",
                "晚安晚安，赶紧睡觉去吧，很晚了"
        );
    }

    @Override
    protected List<String> morning() {
        return Arrays.asList(
                "？",
                "这是通宵了嘛",
                "地球另一端的人要睡觉了嘛"
        );
    }

    @Override
    protected List<String> noon() {
        return Arrays.asList(
                "晚安。。。？",
                "不是午睡嘛。。"
        );
    }

    @Override
    protected List<String> afternoon() {
        return Arrays.asList(
                "晚安。。。？",
                "现在睡觉是不是太早了点",
                "这才" + DateUtil.getHour() + "点，睡的这么早嘛"
        );
    }

    @Override
    protected List<String> night() {
        return Arrays.asList(
                "晚安",
                "好梦",
                "おやすみ",
                "哦呀斯密"
        );
    }
}
