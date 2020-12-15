package cn.mikulink.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 * <p>
 * 常规句子
 */
public class ConstantFreeTime extends ConstantCommon {
    public static final String EXPLAIN = "在[.addnj]或者[.添加日常话语]后跟随语句进行添加，比如：\n"
            + ".addnj 这是一个日常语句\n"
            + "日常语句限制在100字以内\n"
            + "换行输入'\\n'";

    //异常情况处理
    public static final String MSG_TYPE_FREE_TIME_EMPTY = "啊。。。我的资料库里空空如也";
    public static final String MSG_ADD_EMPTY = "添加失败，请输入需要添加的日常语句";
    public static final String MSG_ADD_SUCCESS = "日常语句添加成功";
    public static final String MSG_ADD_OVER_LENGTH = "日常语句长度不能超过100";

    //日常语句
    public static ArrayList<String> MSG_TYPE_FREE_TIME = new ArrayList<>();
    //日常语句 最多时的条目数
    public static Integer MSG_TYPE_FREE_TIME_MAX_SIZE = 0;

    //针对ABABA句式的回复
    public static final List<String> MSG_TYPE_ABABA = Arrays.asList(
            "妮可妮可妮",
            "兔子兔子兔",
            "兔叽兔叽兔",
            "兔砸兔砸兔"
    );

}
