package cn.mikulink.rabbitbot.constant;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 * <p>
 * 微博推文 常量
 */
public class ConstantWeiboNews extends ConstantCommon {
    //微博新闻最后扫描时间
    public static Long weibo_news_last_send_time = System.currentTimeMillis();
    //微博新闻扫描间隔 20分钟一次
    public static Long weibo_news_sprit_time = 1000L * 60 * 20;

    public static final String SINCEID = "lasttag";
    public static final String SINCEID_NULL = "sinceId为NULL值";
    public static final String SINCEID_REFRESH = "refreshSinceId";
    public static final String SINCEID_OVERRIDE_SUCCESS = "sinceId覆写完毕";
    public static final String SINCEID_OVERRIDE_FAIL = "覆写失败，sinceId不能为空";
    public static final String SINCEID_OVERRIDE_FAIL_NOW_NUMBER = "覆写失败，sinceId必须为纯数字";
    public static final String ACCESS_TOKEN = "token";
    public static final String ACCESS_TOKEN_OVERRIDE_SUCCESS = "授权覆写完毕";
    public static final String ACCESS_TOKEN_OVERRIDE_FAIL = "覆写失败，授权不能为空";
    public static final String OPEN_SUCCESS = "微博消息推送已开启";
    public static final String NO_ACCESSTOKEN = "没有授权码";
    public static final String OFF_SUCCESS = "微博消息推送已关闭";
    public static final String EXEC_ERROR = "微博消息推送，它挂了";
    public static final String WHAT_ASSHOLE = "[头像太丑已被屏蔽]";
}
