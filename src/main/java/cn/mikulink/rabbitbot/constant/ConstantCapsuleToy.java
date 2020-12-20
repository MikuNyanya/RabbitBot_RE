package cn.mikulink.rabbitbot.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2019/12/30 14:26
 * for the Reisen
 * 扭蛋
 */
public class ConstantCapsuleToy {
    //扭蛋列表
    public static List<String> MSG_CAPSULE_TOY = Arrays.asList();
    //操作间隔 账号，操作时间戳
    public static Map<Long, Long> CAPSULE_TOY_SPLIT_MAP = new HashMap<>();
    //操作间隔
    public static final Long CAPSULE_TOY_SPLIT_TIME = 1000L * 60;
    //集合总长度
    public static Integer CAPSULE_TOY_SPLIT_MAX_SIZE = 0;

    public static final String EXPLAIN_ADD = "使用以下格式指令添加一个扭蛋\n"
            + ".扭蛋 add 胡萝卜\n"
            + "扭蛋名称不能过长，限制在50字以内\n"
            + "你可以向里面添加任何存在的，不存在的，不管是不是个东西的东西\n"
            + "发挥你的想象力\n"
            + "但请不要加太过分的东西进去";

    public static final String MSG_CAPSULE_TOY_RESULT = "[%s]扭到了:%s";
    public static final String MSG_CAPSULE_TOY_ADD_SUCCESS = "扭蛋[%s]添加完成";

    //错误提示
    public static final String CAPSULE_TOY_SPLIT_ERROR = "[%s]%s秒后可以扭蛋";
    public static final String COMMAND_SECOND_ERROR = "扭蛋指令错误";
    public static final String CAPSULE_TOY_ADD_EMPTY = "你得告诉我添加什么扭蛋";
    public static final String CAPSULE_TOY_ADD_RE = "[%s]已经在扭蛋池里了";
    public static final String CAPSULE_TOY_HAS_NOTHING = "啊。。扭蛋池空空如也";

    //副指令 添加扭蛋
    public static final String ADD = "add";

}
