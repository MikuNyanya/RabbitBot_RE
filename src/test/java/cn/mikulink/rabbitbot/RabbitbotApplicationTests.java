package cn.mikulink.rabbitbot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitbotApplicationTests {

    @Value("${bot.version}")
    private String version;

    @Test
    void contextLoads() {
        System.out.println("===================");
        System.out.println(version);

    }

}
