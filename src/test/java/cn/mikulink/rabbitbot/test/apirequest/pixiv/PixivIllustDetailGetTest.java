package cn.mikulink.rabbitbot.test.apirequest.pixiv;

import cn.mikulink.rabbitbot.modules.pixiv.api.PixivIllustDetailGet;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivImageInfo;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

public class PixivIllustDetailGetTest {

    @Test
    public void test() {
        try {
            PixivIllustDetailGet request = new PixivIllustDetailGet(130000503L);
            Map<String, String> header = new HashMap<>();
            header.put("cookie", "_cookie");
            request.setHeader(header);
            request.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 31051)));
            request.doRequest();
            PixivImageInfo imageInfo = request.getPixivImageInfo();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
