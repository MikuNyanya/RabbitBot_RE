package cn.mikulink.rabbitbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitbotApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitbotApplication.class, args);
        System.out.println("=============rabbit ready=============");
    }
}
