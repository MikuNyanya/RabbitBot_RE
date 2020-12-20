package cn.mikulink.rabbitbot.test.service;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReflectionsTest {

    //反射框架

    //获取使用了指定注解的类
    @Test
    public void test() {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
//            Reflections reflections = new Reflections("cn.mikulink.command");
//            Set<Class<? extends Object>> classes = reflections.getTypesAnnotatedWith(Command.class);

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
