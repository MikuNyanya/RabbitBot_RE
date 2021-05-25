package cn.mikulink.rabbitbot.constant;

/**
 * create by MikuLink on 2021/05/19 19:41
 * for the Reisen
 * <p>
 * B站视频动态 常量
 */
public class ConstantBilibili extends ConstantCommon {
    //微博新闻最后扫描时间
    public static Long bili_dynamicdsvr_last_send_time = System.currentTimeMillis();
    //微博新闻扫描间隔 1分钟一次
    public static Long bili_dynamicdsvr_push_sprit_time = 1000L * 60 * 1;


    public static final String LAST_DYNAMIC_ID = "lastDynamicId";
    public static final String DYNAMIC_ID_OVERRIDE_SUCCESS = "DynamicId覆写完毕";
    public static final String DYNAMIC_ID_OVERRIDE_FAIL = "覆写失败，DynamicId不能为空";
    public static final String DYNAMIC_ID_OVERRIDE_FAIL_NOW_NUMBER = "覆写失败，DynamicId必须为纯数字";
    public static final String COMMAND_KEY_PULL = "pull";
    public static final String COMMAND_KEY_UN_PULL = "unpull";
    public static final String PULL_OR_UNPULL_BILI_DYNAMICDSVR_USERID_EMPTY = "订阅与取消订阅操作需要传入B站uid";
    public static final String PULL_SUCCESS = "B站视频动态订阅完毕";
    public static final String UNPULL_SUCCESS = "已取消B站视频动态订阅";
}
