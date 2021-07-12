package cn.mikulink.rabbitbot.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * create by MikuLink on 2021/2/3 17:01
 * for the Reisen
 * <p>
 * 开关相关常量
 */
public class ConstantSwitch extends ConstantCommon {
    public static final String ARGS_ERROR = "======开关列表======" +
            "\n通过指令来操作开关，区分大小写" +
            "\n默认off代表关闭，on代表开启" +
            "\n格式:.switch (switchName) (switchValue)" +
            "\n范例:.switch setu on" +
            "\n----------开关列表----------";

    public static HashMap<String, String> SWITCH_NAME_DESCRIPTION_MAP = new HashMap<String, String>() {{
        put("sj", "整点报时");
        put("say", "不定期的日常语句以及相关指令");
        put("setu", "色图指令,传入[work]代表只在9am-18pm禁用色图指令");
        put("setuday", "每日色图");
        put("weibo", "微博新闻推送");
        put("pixivRank", "p站日榜推送");
        put("pixivR18", "p站r18图片开关");
        put("newstoday","今日简报推送开关");
        put("biliVideo","B站视频动态推送开关");
        put("receiveWorld","接收兔叽的跨群广播");
    }};

    public static final List<String> SWITCH_PARAM_ERR = Arrays.asList(
            "开关参数有误",
            "参数不正确",
            "啊。。。你给兔叽的东西有问题",
            "兔叽无法识别这些参数",
            "参数错误，你再。。检查下？"
    );
    public static final List<String> SWITCH_FARCE = Arrays.asList(
            "开关功能已被最高权限接管",
            "这里是兔子，开关功能目前已被强制接管",
            "只有兔子才能使用这个指令",
            "开关正在被强制接管",
            "访问拒绝，权限不足"
    );
    public static final String SWITCH_SET_SUCCESS = "开关设置完成";
    public static final String SWITCH_SET_ERROR = "开关设置异常";
    public static final String SWITCH_GET_ERROR = "获取开关设置异常";

    //工作模式，根据不同功能，业务里区别处理
    public static final String SWITCH_WORK = "work";

    public static final String SWITCH_OFF_MSG = "这个功能尚未开启";
    public static final String SWITCH_WORK_MSG = "这个功能已在工作时间段禁用";
}
