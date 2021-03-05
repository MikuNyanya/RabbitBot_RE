package cn.mikulink.rabbitbot.utils;

import java.util.Calendar;
import java.util.Date;

public class ProgressUtils {
    /**
     *
     * @return
     */
    public static String produce() {
        StringBuilder builder = new StringBuilder();

        int daysToNewYearDay = (365 - DateUtil.daysToNewYearDay()) / 20;

        for (int i = 0; i < (365/20); i++) {
            if(i <= daysToNewYearDay){
                builder.append("▓");
            }else{
                builder.append("░");
            }

        }

        return builder.toString();
    }


}
