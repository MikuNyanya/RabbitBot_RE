package cn.mikulink.rabbitbot.test.service;

import cn.mikulink.rabbitbot.RabbitbotApplication;
import cn.mikulink.rabbitbot.constant.ConstantRPG;
import cn.mikulink.rabbitbot.entity.rpg.PlayerStatistics;
import cn.mikulink.rabbitbot.service.rpg.StatisticsService;
import cn.mikulink.rabbitbot.utils.DateUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

/**
 * created by MikuNyanya on 2021/5/20 10:35
 * For the Reisen
 */
@SpringBootTest(classes = RabbitbotApplication.class)
@RunWith(SpringRunner.class)
public class StatisticsServiceTest {
    @Autowired
    private StatisticsService service;

    @Test
    public void test() {
        try {
            PlayerStatistics info = service.parseStatisticsList("傻总");
            String msg = service.parseStatMsg(info);

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Test
    public void testINTE() {
        try {
            for (int i = 0; i <10; i++) {
                int luck = service.getPlayerLUCK("速溶月兔(凛雪飘渺/MikuLink)");
                System.out.println(luck);
            }

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
