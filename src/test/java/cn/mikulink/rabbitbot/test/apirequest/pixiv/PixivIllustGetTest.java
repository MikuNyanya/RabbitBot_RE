package cn.mikulink.rabbitbot.test.apirequest.pixiv;

import cn.mikulink.rabbitbot.apirequest.pixiv.PixivIllustGet;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

public class PixivIllustGetTest {

    @Test
    public void test() {
        try {
            PixivIllustGet request = new PixivIllustGet(128565958L);
//            Map<String, String> header = new HashMap<>();
//            header.put("cookie", "cookie111");
//            request.setHeader(header);
            request.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost",31051)));
            request.doRequest();
            PixivImageInfo imageInfo = request.getPixivImageInfo();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
