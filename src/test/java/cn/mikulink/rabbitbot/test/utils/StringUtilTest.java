package cn.mikulink.rabbitbot.test.utils;

import cn.mikulink.rabbitbot.utils.StringUtil;
import org.junit.Test;

/**
 * created by MikuNyanya on 2021/12/17 17:39
 * For the Reisen
 */
public class StringUtilTest {


    @Test
    public void sumASCIITest() {
        try {

            String name = "MikuLink";
            int sumASCII = StringUtil.sumASCII(name);

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
