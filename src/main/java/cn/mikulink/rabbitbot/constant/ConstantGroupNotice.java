package cn.mikulink.rabbitbot.constant;

/**
 * create by MikuLink on 2019/12/30 14:26
 * for the Reisen
 * 群公告常量
 */
public class ConstantGroupNotice {
    public static final String EXPLAIN = "在[.设置群公告]后输入公告内容，比如：\n"
            + ".设置群公告 头像:%userLogo%\\n欢迎%userName%入群\n"
            + "日常语句限制在200字以内\n"
            + "换行输入'\\n'"
            + "引用群员头像输入'%userLogo%'"
            + "引用群员名称输入'%userName%'";

    //最大公告长度
    public static final Integer MAX_LENGTH = 220;
    public static final String MAX_LENGTH_OVERFLOW = "群公告限制在200字以内";
}
