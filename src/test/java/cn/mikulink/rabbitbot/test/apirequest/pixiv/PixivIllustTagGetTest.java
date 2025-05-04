package cn.mikulink.rabbitbot.test.apirequest.pixiv;

import cn.mikulink.rabbitbot.modules.pixiv.api.PixivIllustTagGet;
import org.junit.Test;

public class PixivIllustTagGetTest {

    @Test
    public void test() {
        try {
            PixivIllustTagGet request = new PixivIllustTagGet();
            request.setP(1);
            request.setWord("初音ミク");
            request.doRequest();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
