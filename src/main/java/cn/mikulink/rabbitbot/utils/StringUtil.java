package cn.mikulink.rabbitbot.utils;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 字符串工具类
 */
public class StringUtil {
    /**
     * 是否为空
     * null或者空字符串都会判定为true
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 是否不为空
     * null或者空字符串都会判定为false
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 去掉字符串中的所有空格
     *
     * @param str
     * @return
     */
    public static String trimAll(String str) {
        if (str == null)
            return "";
        return str.replaceAll(" ", "");
    }

    /**
     * 移除前面的空格
     *
     * @param original 源字符串
     * @return 移除后的字符串
     */
    public static String removeStartingSpace(String original) {
        while (original.startsWith(" ")) {
            original = original.substring(1);
        }
        return original;
    }

    /**
     * 去除首尾空格
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (null == str)
            return "";
        return str.trim();
    }

    /**
     * 匹配ABABA句式
     * 妮可妮可妮
     *
     * @param str 需要匹配的字符串
     * @return 是否符合句式
     */
    public static boolean isABABA(String str) {
        if (isEmpty(str) || str.length() < 5) {
            return false;
        }
        char a = str.charAt(0);
        char b = str.charAt(1);
        //两个字符不能相同
        if (a == b) {
            return false;
        }
        String ababa_str = String.format("%s%s%s%s%s", a, b, a, b, a);

        return ababa_str.equalsIgnoreCase(str);
    }

    /**
     * 截取cq码中的图片链接
     *
     * @param cqStr CQ码
     * @return 图片链接
     */
    public static String getCQImageUrl(String cqStr) {
        if (null == cqStr) {
            return null;
        }
        if (!cqStr.contains("[CQ:image")) {
            return null;
        }

        String[] strs = cqStr.split(",");

        for (String str : strs) {
            if (StringUtil.isEmpty(str)) {
                continue;
            }
            if (!str.startsWith("url=")) {
                continue;
            }
            return str.substring("url=".length(), str.indexOf("]"));
        }
        return null;
    }
}
