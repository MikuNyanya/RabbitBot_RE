package cn.mikulink;

import cn.mikulink.bot.RabbitBot;
import cn.mikulink.filemanage.FileManagerLogo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * created by MikuLink on 2020/12/14 16:45
 * for the Reisen
 * 启动类
 */
public class Main {
    public static void main(String[] arg) {
        //logo
        FileManagerLogo.printLogo();

        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        //想要用@Autowired，不能new，不然就是个新的对象，脱离了spring容器了 https://my.oschina.net/u/2338224/blog/1822135
        RabbitBot rabbitBot = context.getBean(RabbitBot.class);
        rabbitBot.startBot();

        System.out.println("=====Rabbit ready!=====");
    }
}
