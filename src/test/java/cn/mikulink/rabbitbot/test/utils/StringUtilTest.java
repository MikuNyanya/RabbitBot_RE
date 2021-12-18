package cn.mikulink.rabbitbot.test.utils;

import cn.mikulink.rabbitbot.command.everywhere.rpg.RollCommand;
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

//    @Test
//    public void test() {
//        try {
//            int str = 52;
//            int luck = 55;
//
//            int successCount  = 0;
//            int failCount  = 0;
//
//            for (int i = 0; i < 1000; i++) {
//                int roll = RollCommand.rollD(str,luck);
////                System.out.println(roll);
//                if(roll>=60){
//                    successCount++;
//                }else{
//                    failCount++;
//                }
//
//                Thread.sleep(1);
//            }
//
//            System.out.println(String.format("成功概率%s",successCount/1000.0));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
