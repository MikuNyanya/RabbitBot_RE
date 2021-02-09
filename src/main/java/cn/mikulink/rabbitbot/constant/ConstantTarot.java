package cn.mikulink.rabbitbot.constant;

import cn.mikulink.rabbitbot.entity.TarotInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * create by MikuLink on 2020/12/22 14:51
 * for the Reisen
 * <p>
 *
 */
public class ConstantTarot extends ConstantCommon {
    //塔罗牌图片存放目录
    public static final String IMAGE_TAROT_SAVE_PATH = "data/images/tarot";
    //猫罗牌图片存放目录
    public static final String IMAGE_CATROT_SAVE_PATH = "data/images/catrot";
    //塔罗牌列表 话说我总写这种东西丢内存里，总有一天内存会爆掉吧
    public static List<TarotInfo> TARTO_LIST = new ArrayList<>();

    public static final String TAROT_ERROR_GROUP_MESSAGE = "塔罗牌功能异常";
    public static final String CATROT_ERROR_GROUP_MESSAGE = "猫罗牌功能异常";
}
