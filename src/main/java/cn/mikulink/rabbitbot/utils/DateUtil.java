package cn.mikulink.rabbitbot.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MikuLink on 2017/7/14.
 * 时间工具
 */
public class DateUtil {
    /**
     * 转化为字符串
     * <p>
     * yyyy-MM-dd'T'HH:mm:ss.SSSXXX         2017-08-10T16:17:48.303+08:00           (iso 8601 时间格式)
     * yyyy-MM-dd HH:mm:ss
     * yyyy/MM/dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String toString(Date date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static String toString(Date date) {
        return toString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 转化为时间
     *
     * @param dateStr
     * @return
     */
    public static Date toDate(String dateStr, String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.parse(dateStr);
    }

    public static Date toDate(String dateStr) throws ParseException {
        return toDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 改变时间
     *
     * @param date 初始时间
     * @param type 要改变的类型,例如Calendar.MARCH,改变的是月份
     * @param num  变动数
     * @return
     */
    public static Date dateChange(Date date, int type, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(type, calendar.get(type) + num);
        return calendar.getTime();
    }

    /**
     * 获取当前天数的开始时间
     *
     * @return
     */
    public static Date dateToDayStart() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        //年
        int year = calendar.get(Calendar.YEAR);
        //月 从0开始，需要+1
        int month = calendar.get(Calendar.MONTH) + 1;
        //日
        int day = calendar.get(Calendar.DATE);
//        //时
//        int hour=calendar.get(Calendar.HOUR_OF_DAY);
//        //分
//        int minute=calendar.get(Calendar.MINUTE);
//        //秒
//        int second=calendar.get(Calendar.SECOND);

        String dateStr = String.format("%s-%s-%s 0:00:00", year, month, day);
        return toDate(dateStr);
    }

    /**
     * 获取当前天数的结束时间
     *
     * @return
     */
    public static Date dateToDayEnd() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        //年
        int year = calendar.get(Calendar.YEAR);
        //月 从0开始，需要+1
        int month = calendar.get(Calendar.MONTH) + 1;
        //日
        int day = calendar.get(Calendar.DATE);

        String dateStr = String.format("%s-%s-%s 23:59:59", year, month, day);
        return toDate(dateStr);
    }

    /**
     * 获取当前小时
     * 24小时制
     *
     * @return 小时
     */
    public static int getHour() {
        Calendar calendar = Calendar.getInstance();
        //时
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
