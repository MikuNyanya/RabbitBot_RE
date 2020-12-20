package cn.mikulink.rabbitbot.test.quartz;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class JobTest {

    @Test
    public void test(){
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

            System.out.println("请输入信息：");
            Scanner input = new Scanner(System.in);
            int x = input.nextInt();
            System.out.println(x);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
