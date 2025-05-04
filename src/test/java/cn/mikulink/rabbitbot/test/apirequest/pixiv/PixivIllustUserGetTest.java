package cn.mikulink.rabbitbot.test.apirequest.pixiv;

import cn.mikulink.rabbitbot.modules.pixiv.api.PixivIllustUserGet;
import org.junit.Test;

import java.util.List;

public class PixivIllustUserGetTest {

    @Test
    public void test() {
        try {
            PixivIllustUserGet request = new PixivIllustUserGet();
            request.setUserId("19469841");
            request.doRequest();
            List<String> pids = request.getResponseList();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
