package cn.mikulink.rabbitbot.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 * <p>
 * 存一些文件相关信息 不过应该写成配置文件的
 */
public class ConstantFile extends ConstantCommon {
    //配置 文件相对路径
    public static final String CONFIG_FILE_PATH = "src/main/resources/files/config";
    //扭蛋 文件相对路径
    public static final String CAPSULE_TOY_FILE_PATH = "src/main/resources/files/capsule_toy.txt";
    //pixiv图片tag列表 文件相对路径
    public static final String PIXIV_TAG_FILE_PATH = "src/main/resources/files/pixiv_tag.txt";
    //pixiv图片tag整理后的列表 文件相对路径
    public static final String PIXIV_TAG_RABBIT_FILE_PATH = "src/main/resources/files/pixiv_tag_rabbit.txt";
    //pixiv用户信息列表 文件相对路径
    public static final String PIXIV_TMEMBER_FILE_PATH = "src/main/resources/files/pixiv_member.txt";

    //pixiv色图pid列表 文件相对路径
    public static final String PIXIV_SETU_FILE_PATH = "src/main/resources/files/pixiv_setu_pids.txt";
    //pixiv色图pid
    public static List<String> List_SETU_PID = Arrays.asList();
    //pixiv色图操作间隔 账号，操作时间戳
    public static Map<Long, Long> SETU_PID_SPLIT_MAP = new HashMap<>();
    //pixiv色图操作间隔
    public static final Long SETU_PID_SPLIT_TIME = 1000L * 60;
    //pixiv色图集合总长度
    public static Integer SETU_PID_LIST_MAX_SIZE = 0;
    //色图操作间隔提示
    public static final List<String> SETU_SPLIT_ERROR_LIST = Arrays.asList(
            "[%s]%s秒后可再次操作",
            "[%s]%s秒后可再次操作",
            "[%s]%s秒后可再次操作",
            "[%s]%s秒后可再次操作",
            "[%s]%s秒后可再次操作,可以趁时间去买瓶营养快线什么的",
            "[%s]操作间隔剩余%s秒",
            "[%s]操作间隔剩余%s秒",
            "[%s]操作间隔剩余%s秒",
            "[%s]操作间隔剩余%s秒",
            "[%s]操作间隔剩余%s秒",
            "[%s]冷却中(%s)",
            "[%s]冷却中(%s)",
            "[%s]冷却中(%s)",
            "[%s]冷却中(%s)",
            "[%s]冷却中(%s)",
            "[%s]%s秒后再看涩图吧,这是为了你的身体着想"
    );

    //客制化 常规语句 文件相对路径
    public static final String APPEND_FREE_TIME_FILE_PATH = "src/main/resources/files/free_time.txt";
    //客制化 关键词响应语句 文件相对路径
    public static final String APPEND_KEYWORD_FILE_NORMAL_PATH = "src/main/resources/files/key_word_normal.txt";
    //客制化 模糊关键词响应语句 文件相对路径
    public static final String APPEND_KEYWORD_FILE_LIKE_PATH = "src/main/resources/files/key_word_like.txt";
    //客制化 高德地图城市代码 文件相对路径
    public static final String APPEND_AMAPADCODE_FILE_PATH = "src/main/resources/files/AMap_adcode_citycode.txt";
    //客制化 摩尔斯电码对照表 文件相对路径
    public static final String APPEND_MORSECODE_FILE_PATH = "src/main/resources/files/morsecode.txt";

    //加载文件 到系统
    public static final String FILE_COMMAND_LOAD = "load";
    //对文件写入
    public static final String FILE_COMMAND_WRITE = "write";

    public static final String SRC_PATH_EMPTY = "原路径为空";
    public static final String DES_PATH_EMPTY = "目标路径为空";
    public static final String SRC_PATH_NOT_EXISTS = "原路径或文件不存在";
    public static final String DES_PATH_NOT_EXISTS = "目标路径或文件不存在";
}
