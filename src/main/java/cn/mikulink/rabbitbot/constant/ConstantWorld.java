package cn.mikulink.rabbitbot.constant;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2021/07/12 16:03
 * for the Reisen
 * <p>
 * 世界频道相关
 */
public class ConstantWorld extends ConstantCommon {
    public static final List<String> COMMAND_MASTER_ONLY = Arrays.asList(
            "权限不足",
            "访问拒绝，权限不足",
            "你无权让我执行这条指令ヽ(#`Д´)ノ",
            "只有兔子才能使用这个指令",
            "兔子频道只有兔子才能使用，也是合情合理的吧",
            "这个指令需要更高的权限",
            "这个指令只对兔子开放"
    );

    public static final String ARGS_ERROR = "请写入广播内容";

}
