package cn.mikulink.rabbitbot.test.utils;

import cn.mikulink.rabbitbot.utils.DateUtil;
import org.junit.Test;

import java.util.Date;
import java.util.Locale;

/**
 * @author MikuLink
 * @date 2020/12/31 0:41
 * for the Reisen
 */
public class DateUtilTest {
    @Test
    public void test() {
        String pht = "EEE MMM dd HH:mm:ss Z yyyy";
        String dateStr = DateUtil.toString(new Date());
        int dayOfWeek = DateUtil.getDayOfWeek();

        System.out.println("");
    }
}
