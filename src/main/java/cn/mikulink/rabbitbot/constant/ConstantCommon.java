package cn.mikulink.rabbitbot.constant;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2019/12/3 14:39
 * for the Reisen
 * <p>
 * 公共常量
 */
@Component
public class ConstantCommon {
    //换行符
    public static final String NEXT_LINE = "\n";
    //开关-开
    public static final String ON = "on";
    //开关-关
    public static final String OFF = "off";
    //文本-执行
    public static final String EXEC = "exec";
    //文本-设置
    public static final String SET = "set";
    //文本-删除
    public static final String DELETE = "delete";
    //文本-删除(简写)
    public static final String DEL = "del";

    //群主
    public static final String OWNER = "OWNER";
    //管理
    public static final String ADMIN = "ADMINISTRATOR";
    //群员
    public static final String MEMBER = "MEMBER";


    public static final String ERROR_UNKONW = "未知错误";
    public static final String API_BODY_EMPTY = "请求结果报文为空";
    public static final String COMMAND_ERROR = "指令[%s]无效";
    public static final String COMMAND_NEED_AUTHORITY = "该指令需要更高的权限";
    public static final String COMMAND_ROLE_ADMIN = "该指令需要管理权限";
    public static final List<String> COMMAND_MASTER_ONLY = Arrays.asList(
            "权限不足",
            "访问拒绝，权限不足",
            "你无权对我发号施令",
            "你无权让我执行这条指令ヽ(#`Д´)ノ",
            "只有兔子才能使用这个指令",
            "你不是兔子！不准用这个指令",
            "这个指令需要更高的权限",
            "这个指令只对兔子开放"
    );
    public static final List<String> COMMAND_ADMIN_ONLY = Arrays.asList(
            "权限不足",
            "访问拒绝，权限不足",
            "需要更高的权限",
            "兔叽不会听从你的指令",
            "你无权对我发号施令，让你的上级和我讲话！",
            "你无权让我执行这条指令ヽ(#`Д´)ノ",
            "这个指令需要更高的权限"
    );
    public static final String PARAM_ERROR = "参数无效";
    public static final String GAME_PARAM_NUMBER_NOT_NULL = "你得选择一个数字";
    public static final String GAME_PARAM_NUMBER_ONLY = "参数必须为纯数字";
    public static final String GAME_PARAM_NUMBER_TO_SMALL = "设置的数字必须大于0";
    public static final String GAME_PARAM_SELECT_ONLY = "只能选择纯数字";
    public static final String GAME_PARAM_SELECT_OVER = "数字不能超出范围(0~%s)";

    //公共配置
    public static Map<String, String> common_config = new HashMap<>();
}
