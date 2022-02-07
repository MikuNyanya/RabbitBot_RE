package cn.mikulink.rabbitbot.test.service;

import cn.mikulink.rabbitbot.RabbitbotApplication;
import cn.mikulink.rabbitbot.entity.rpg.PlayerCharacterStats;
import cn.mikulink.rabbitbot.service.rpg.CharacterStatsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * created by MikuNyanya on 2021/5/20 10:35
 * For the Reisen
 */
@SpringBootTest(classes = RabbitbotApplication.class)
@RunWith(SpringRunner.class)
public class CharacterStatsServiceTest {
    @Autowired
    private CharacterStatsService service;

    @Test
    public void test() {
        try {
            PlayerCharacterStats info = service.parseCharacterStatsList("傻总");
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
