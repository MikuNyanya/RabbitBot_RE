package cn.mikulink.rabbitbot.test.utils;

import cn.mikulink.rabbitbot.utils.DateUtil;
import org.junit.Test;

import java.util.Date;

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


    @Test
    public void dateToDay() {

        Date start = DateUtil.dateToDayStart();
        Date end = DateUtil.dateToDayEnd();

        System.out.println("");
    }

    @Test
    public void dateToWeek() {
        try {
            Date test = DateUtil.toDate("2022-04-4 1:1:11");

            Date start = DateUtil.dateToWeekStart(test);
            Date end = DateUtil.dateToWeekEnd(test);


            System.out.println("");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void dateToMonth() {
        try {
            Date test = DateUtil.toDate("2000-2-28 1:1:11");

            Date start = DateUtil.dateToMonthStart(test);
            Date end = DateUtil.dateToMonthEnd(test);


            System.out.println("");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
