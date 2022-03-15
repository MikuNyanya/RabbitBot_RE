package cn.mikulink.rabbitbot.test.service;

import cn.mikulink.rabbitbot.entity.newpneumonia.VirusInfo;
import cn.mikulink.rabbitbot.service.VirusService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * created by MikuNyanya on 2022/3/15 15:52
 * For the Reisen
 */
public class VirusServiceTest {

    @Test
    public void test() {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
            VirusService service = context.getBean(VirusService.class);
            VirusInfo info = service.getVirusInfo();



            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
