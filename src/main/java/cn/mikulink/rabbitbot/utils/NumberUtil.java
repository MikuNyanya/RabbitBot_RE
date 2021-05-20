package cn.mikulink.rabbitbot.utils;

/**
 * create by MikuLink on 2019/12/3 20:07
 * for the Reisen
 * <p>
 * 数字工具类
 */
public class NumberUtil {

    /**
     * 判断一个字符串是否为纯数字组成
     *
     * @param str 需要判断的字符串
     * @return 是否为纯数字
     */
    public static boolean isNumberOnly(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        char[] cc = str.toCharArray();
        for (char c : cc) {
            if (!RegexUtil.regex(String.valueOf(c), "[0-9]")) {
                return false;
            }
        }

        return true;
    }

    /**
     * 转化为Integer数字
     *
     * @param obj 任意参数
     * @return 输出数字
     */
    public static Integer toInt(Object obj) {
        if (null == obj) {
            return null;
        }
        return Integer.valueOf(obj.toString());
    }

    /**
     * 转化为Double数字
     *
     * @param obj 任意参数
     * @return 输出数字
     */
    public static Double toDouble(Object obj) {
        if (null == obj) {
            return null;
        }
        String s = String.valueOf(obj);
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        return Double.valueOf(s);
    }

    /**
     * 转化为Long数字
     *
     * @param obj 任意参数
     * @return 输出数字
     */
    public static Long toLong(Object obj) {
        if (null == obj) {
            return null;
        }
        return Long.valueOf(obj.toString());
    }

    /**
     * double转为int，向上取整
     * 可用于分页计算什么的
     *
     * @param num 小数
     * @return 向上取整的整数
     */
    public static Integer toIntUp(Double num) {
        if (null == num) {
            return null;
        }
        if (0d == num) {
            return 0;
        }

        Integer intNum = null;
        //取整数部分
        intNum = num.intValue();

        //存在小数则向上加一
        if (num < (intNum * 1.0)) {
            intNum = intNum + 1;
        }

        return intNum;
    }

    /**
     * 保留后面小数位
     * 该方法为直接截取，不是四舍五入
     * 小于指定位数的会被java直接忽略（大概
     *
     * @param num   数字
     * @param point 保留几位小数
     * @return 截取后指定小数位的数字
     */
    public static Double keepDecimalPoint(Double num, int point) {
        if (null == num) {
            return 0d;
        }
        //使用字符串截取的方法，那些数字计算方法都不靠谱
        String numStr = String.valueOf(num);
        if (!numStr.contains(".")) {
            return num;
        }
        int pointIndex = numStr.indexOf(".");

        //小于指定位数，直接返回即可
        if (numStr.substring(pointIndex + 1).length() < point) {
            return num;
        }

        //截取小数
        String resultNumStr = numStr.substring(0, pointIndex + point + 1);

        return toDouble(resultNumStr);
    }
}
