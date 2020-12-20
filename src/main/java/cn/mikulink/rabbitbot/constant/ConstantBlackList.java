package cn.mikulink.rabbitbot.constant;

import java.util.Arrays;
import java.util.List;

/**
 * create by MikuLink on 2019/12/5 11:38
 * for the Reisen
 * <p>
 * 可以存放黑名单
 */
public class ConstantBlackList {
    //黑名单 针对所有功能 填写qq号即可
    public static final List<Long> BLACK_LIST = Arrays.asList(
            0L,
            1784402819L,//盲盲
            2413572936L,//盲盲2号机
            243557854L//公主连结机器人
    );
}
