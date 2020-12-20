package cn.mikulink.rabbitbot.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 * <p>
 * 高德地图相关
 */
public class ConstantAmap extends ConstantCommon {
    //存放高德地图API所需要的城市adcode
    public static Map<String, String> map_adcode = new HashMap<>();

    //提示信息
    public static String INPUT_CITY_NAME_EMPTY = "输入的城市名称不能为空";
    public static String CITY_MATCH_EMPTY = "没有匹配到这个城市[%s]";
    public static String CITY_MATCH_FAIL = "没有完全匹配到这个城市[%s]，与之相似的城市名称有：%s";
    public static String WEATHER_API_FAIL = "啊，获取不到天气信息了。。。\n请帮兔叽联系下兔子=A=";

}
