package cn.mikulink.rabbitbot.test.apirequest;

import cn.mikulink.rabbitbot.apirequest.pixiv.PixivIllustGet;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import org.junit.Test;

public class PixivIllustGetTest {

    @Test
    public void test() {
        try {
            PixivIllustGet request = new PixivIllustGet(75717389L);
            request.doRequest();
            PixivImageInfo imageInfo = request.getPixivImageInfo();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
