package cn.mikulink.service.greetings;


import cn.mikulink.utils.DateUtil;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2020/1/17 14:40
 * for the Reisen
 * 时间问候，下午好
 */
public class GreetingsAfternoon extends GreetingsBase {

    @Override
    protected List<String> beforeDawn() {
        return Arrays.asList(
                "？",
                "？？？",
                "你是在地球的另一端嘛",
                "又疯一个，没救了",
                "别闹了，都凌晨" + DateUtil.getHour() + "点了，赶紧睡觉去",
                "都几点了还不睡觉，等着猝死吧！"
        );
    }

    @Override
    protected List<String> morning() {
        return Arrays.asList(
                "还没到下午，要不再接着睡会？",
                "离下午早着呢"
        );
    }

    @Override
    protected List<String> noon() {
        return Arrays.asList(
                "现在是中午哦",
                "过会才到下午"
        );
    }

    @Override
    protected List<String> afternoon() {
        return Arrays.asList(
                "下午好",
                "来杯下午茶吧",
                "下午好~",
                "啊。。。\n下午好懒啊。。。"
        );
    }

    @Override
    protected List<String> night() {
        return Arrays.asList(
                "已经晚上了",
                "天都黑了，到晚上了"
        );
    }
}
