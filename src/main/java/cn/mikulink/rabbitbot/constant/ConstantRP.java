package cn.mikulink.rabbitbot.constant;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2021/3/10 9:57
 * for the Reisen
 * <p>
 * 人品相关常量
 */
public class ConstantRP extends ConstantCommon {
    //极低人品短语<10
    public static final List<String> RP_MSGEX_WTF = Arrays.asList(
            "你这。。。",
            "啊咧，这也太低了点吧",
            "有考虑过去做反向风向标嘛",
            "真可怜，让兔叽抱抱你吧~",
            "这不是。。。兔叽的错"
    );
    //低人品短语10-40
    public static final List<String> RP_MSGEX_LOW = Arrays.asList(
            "虽然是有点低，不过还好啦",
            "其实还可以啦",
            "有点勉强喔",
            "没关系，下次一定更好"
    );
    //一般人品短语40-70
    public static final List<String> RP_MSGEX_NORMAL = Arrays.asList(
            "还是很正常的嘛",
            "中规中矩",
            "平平淡淡才是真",
            "嗯嗯，不错"
    );
    //高人品短语70-90
    public static final List<String> RP_MSGEX_HIGH = Arrays.asList(
            "看起来很不错的样子",
            "说不定真的会有好事发生哦",
            "这次很高哦"
    );
    //极高人品短语>90
    public static final List<String> RP_MSGEX_WOW = Arrays.asList(
            "有考虑过去买张彩票嘛",
            "很高啊，真好",
            "恭喜恭喜~",
            "大吉大利~"
    );
}
