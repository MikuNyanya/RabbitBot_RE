package cn.mikulink.rabbitbot.service.greetings;

import cn.mikulink.rabbitbot.utils.DateUtil;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2020/1/17 14:40
 * for the Reisen
 * 时间问候，早上好
 */
public class GreetingsMorning extends GreetingsBase {

    @Override
    protected List<String> beforeDawn() {
        return Arrays.asList(
                "早。。。\n这才几点，起的还真早呢",
                "早上好。。。？\n这是早起的，还是没睡的"
        );
    }

    @Override
    protected List<String> morning() {
        return Arrays.asList(
                "早上好~",
                "早上好!",
                "早上好",
                "早安~",
                "早安",
                "早上好哦~",
                "早，又是新的一天，努力活下去吧",
                "这里是兔叽，早上好哦",
                "哦哈呦",
                "おはよう"
        );
    }

    @Override
    protected List<String> noon() {
        return Arrays.asList(
                "早。。？\n都" + DateUtil.getHour() + "点了，才起床啊",
                "都几点了，还早安，该吃午饭了",
                "已经是中午了",
                "现在已经中午啦！\n赶紧起床吃饭",
                "那可真的能睡",
                "早安。。？",
                "早上好。。。",
                "早啊早啊，节省了一顿早饭，恭喜恭喜"
        );
    }

    @Override
    protected List<String> afternoon() {
        return Arrays.asList(
                "早。。？\n都" + DateUtil.getHour() + "点了，才起床啊",
                "早啊早啊，不仅节省了一顿早饭，连午饭也省了",
                "这都几点了，睡死算了",
                "已经是下午了",
                "早安。。？",
                "早啊，晚饭还吃嘛，要不再接着睡？",
                "早啊早啊，不再睡个回笼觉嘛"
        );
    }

    @Override
    protected List<String> night() {
        return Arrays.asList(
                "你是在地球的另一端嘛",
                "天还黑着，早着呢，接着睡",
                "早安。。？",
                "我猜你晚饭也不吃了对吧"
        );
    }
}
