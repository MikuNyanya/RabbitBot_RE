package cn.mikulink.rabbitbot.constant;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * create by MikuLink on 2020/12/21 19:51
 * for the Reisen
 * <p>
 * pixiv相关常量
 */
@Component
public class ConstantPixiv extends ConstantCommon {
    public static final String IMJAD_PIXIV_ID_API_ERROR = "imjad Pixiv Api请求异常";
    public static final String IMJAD_PIXIV_ID_API_FAIL = "imjad Pixiv Api请求失败";

    //配置，p站
    public static final int PIXIV_IMAGE_PAGESIZE = 5;
    public static final String PIXIV_CONFIG_IMAGE_IGNORE = "pixivImageIgnore";
    public static final String PIXIV_IMAGE_IGNORE_WARNING = "[P站图片已忽略加载]";
    public static final String PIXIV_IMAGE_DOWNLOAD_FAIL = "[P站图片下载失败]";
    public static final String PIXIV_IMAGE_DELETE = "[P站图片已被删除]";
    public static final String PIXIV_IMAGE_TIMEOUT = "P站图片获取超时，请重试";
    public static final String PIXIV_IMAGE_RANK_ERROR = "P站日榜信息获取异常";
    public static final String PIXIV_IMAGE_RANK_JOB_ERROR = "P站日榜信息推送异常";
    public static final String PIXIV_IMAGE_ID_IS_EMPTY = "你得给我一个p站图片id";
    public static final String PIXIV_IMAGE_ID_IS_NUMBER_ONLY = "p站图片id只会是纯数字";
    public static final String PIXIV_IMAGE_R18 = "[该图片为R18，不予展示]";

    public static final String PIXIV_IMAGE_TAG_IS_EMPTY = "你得给我一个p站图片tag";
    public static final String PIXIV_IMAGE_TAG_NO_RESULT = "[这个tag没有搜索结果]";
    public static final String PIXIV_IMAGE_TAG_ALL_R18 = "[这个页全是R18，不予展示]";
    public static final String PIXIV_TAG_GET_ERROR_GROUP_MESSAGE = "根据P站图片tag获取信息异常";

    public static final String PIXIV_ID_GET_FAIL_GROUP_MESSAGE = "根据P站图片id获取信息失败";
    public static final String PIXIV_ID_GET_ERROR_GROUP_MESSAGE = "根据P站图片id获取信息异常";

    public static final String PIXIV_CONFIG_IMAGES_SHOW_COUNT = "pixivImagesShowCount";
    public static final String PIXIV_CONFIG_IMAGES_SHOW_COUNT_DEFAULT = "3";
    public static final String PIXIV_IMAGES_NOT_LEGAL = "这不是一个多图Pid";

    //p站tag相关
    //所有已收集tag
    public static ArrayList<String> PIXIV_TAG_LIST = new ArrayList<>();
    //已经经过整理的tag，数据格式和上面不同
    public static ArrayList<String> PIXIV_TAG_RABBIT_LIST = new ArrayList<>();
    public static final String PIXIV_TAG_SAVE_ERROR = "P站图片tag保存异常";
    public static final String PIXIV_TAG_IS_EMPTY = "P站tag资料库里空空如也";

    //p站作者相关
    public static ArrayList<String> PIXIV_MEMBER_LIST = new ArrayList<>();
    //每次使用作者搜图，展示多少张图片
    public static final Integer PIXIV_MEMBER_ILLUST_SHOW_COUNT = 3;
    public static final String PIXIV_MEMBER_IS_EMPTY = "你得给我一个p站作者名称";
    public static final String PIXIV_MEMBER_ID_IS_EMPTY = "你得给我一个p站作者id";
    public static final String PIXIV_MEMBER_ID_IS_NOT_NUMBER_ONLY = "p站作者id必须是纯数字";
    public static final String PIXIV_MEMBER_ID_JSON_ERROR = "获取p站作品列表失败，请确认puid是否正确";
    public static final String PIXIV_MEMBER_NOT_FOUND = "没有查到这个作者的相关信息";
    public static final String PIXIV_MEMBER_NO_ILLUST = "这个作者没有作品";
    public static final String PIXIV_MEMBER_GET_ERROR_GROUP_MESSAGE = "根据P站作者获取信息异常";
    public static final String PIXIV_MEMBER_SAVE_ERROR = "Pixiv作者信息保存异常";


    //pixiv色图pid
    public static List<String> List_SETU_PID = new ArrayList<>();
    //pixiv色图操作间隔 账号，操作时间戳
    public static Map<Long, Long> SETU_PID_SPLIT_MAP = new HashMap<>();

    //pixiv色图集合总长度
    public static Integer SETU_PID_LIST_MAX_SIZE = 0;
    //色图操作间隔提示
    public static final List<String> SETU_SPLIT_ERROR_LIST = Arrays.asList(
            "[%s]%s秒后可再次操作",
            "[%s]色图CD中...%s秒，好消息是，这个不是GCD",
            "[%s]%s秒后再看涩图吧，小撸怡情，大撸伤身，强撸灰飞烟灭",
            "[%s]%s秒后可再次操作,可以趁时间去买瓶营养快线什么的",
            "[%s]操作间隔剩余%s秒",
            "[%s]冷却中(%s)",
            "[%s]%s秒后再看涩图吧,这是为了你的身体着想"
    );
    //每日色图附加信息
    public static final List<String> SETU_DAY_EX_MSG_List = Arrays.asList(
            "色图来啦",
            "兔叽正在关心群友有没有每天看色图",
            "有谁不喜欢色图呢",
            "色图Time!",
            "来份色图",
            "都没人发色图的嘛"
    );
    //每日色图异常
    public static final String SETU_DAY_ERROR = "每日色图异常";

}
