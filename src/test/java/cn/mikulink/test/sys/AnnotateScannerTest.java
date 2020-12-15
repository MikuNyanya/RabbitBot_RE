package cn.mikulink.test.sys;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotateScannerTest {

    //目标类AnnotateScanner
    @Test
    public void test(){
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");



            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
