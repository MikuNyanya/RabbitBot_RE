package cn.mikulink.rabbitbot.test.service;

import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivImageInfo;
import cn.mikulink.rabbitbot.modules.pixiv.PixivService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PixivServiceTest {


    //pixiv爬虫测试 单个图片
    @Test
    public void getByPixivImgId(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        PixivService service = context.getBean(PixivService.class);
        try {
//            PixivImageInfo pixivImageInfo = service.getPixivImgInfoById(82343475L);
            //r18
            PixivImageInfo pixivImageInfo_R18 = service.getPixivImgInfoById(75717389L);

            System.out.println("");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //pixiv爬虫测试 日榜
    @Test
    public void getByPixivRank(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        PixivService service = context.getBean(PixivService.class);
        try {
            service.getPixivIllustRank(5);


            System.out.println("");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
