package cn.mikulink.rabbitbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitbotApplication {
    public static void main(String[] args) {
        //启动后业务入口在cn.mikulink.rabbitbot.bot.RabbitBot中
        SpringApplication.run(RabbitbotApplication.class, args);
        System.out.println("=============rabbit ready=============");
    }
}
