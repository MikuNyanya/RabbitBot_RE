package cn.mikulink.rabbitbot.utils;

import java.util.List;

/**
 * create by MikuLink on 2020/12/18 1:00
 * for the Reisen
 * <p>
 * 集合工具类
 */
public class CollectionUtil {

    /**
     * 集合是否为空
     * 期望 非空
     *
     * @param list 集合
     * @return 是否为空
     */
    public static boolean isNotEmpty(List<?> list) {
        return null != list && list.size() > 0;
    }

    /**
     * 集合是否为空
     * 期望 空
     *
     * @param list 集合
     * @return 是否为空
     */
    public static boolean isEmpty(List<?> list) {
        return null == list || list.size() <= 0;
    }
}
