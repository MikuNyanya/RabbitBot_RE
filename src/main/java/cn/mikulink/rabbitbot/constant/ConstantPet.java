package cn.mikulink.rabbitbot.constant;

import cn.mikulink.rabbitbot.entity.PetInfo;

/**
 * create by MikuLink on 2021/09/15 17:57
 * for the Reisen
 * <p>
 * 养成系统
 */
public class ConstantPet extends ConstantCommon {
    public static PetInfo petInfo = null;

    public static final String EXPLAIN = "这是养成系统";

    //状态条_表示充满 █████▁▁▁▁▁ 50%
    public static final String STATUS_FULL = "█";
    //状态条_表示空的
    public static final String STATUS_EMPTY = "▁";
    //状态条最大长度
    public static final Integer STATUS_BAR_MIN = 0;
    //状态条最大长度
    public static final Integer STATUS_BAR_MAX = 10;

    //心情最大值
    public static final Integer HEART_MAX = 100;
}
