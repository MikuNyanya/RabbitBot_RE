package cn.mikulink.rabbitbot.test.apirequest;

import cn.mikulink.rabbitbot.apirequest.danbooru.DanbooruImageGet;
import org.junit.Test;

/**
 * @author MikuLink
 * @date 2021/1/4 17:05
 * for the Reisen
 */
public class DanbooruImageGetTest {
    @Test
    public void test() {
        try {
            //目标页面
            DanbooruImageGet request = new DanbooruImageGet();
            request.setDanbooruId("4272761");
            request.doRequest();
            String imageUrl = request.getDanbooruImageUrl();



            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
