package cn.mikulink.rabbitbot.test.apirequest.pixiv;

import cn.mikulink.rabbitbot.apirequest.pixiv.PixivIllustTagGet;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
