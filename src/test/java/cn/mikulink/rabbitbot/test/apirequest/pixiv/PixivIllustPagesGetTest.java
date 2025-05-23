package cn.mikulink.rabbitbot.test.apirequest.pixiv;

import cn.mikulink.rabbitbot.modules.pixiv.api.PixivIllustPagesGet;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivImageUrlInfo;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PixivIllustPagesGetTest {

    @Test
    public void test() {
        try {
            PixivIllustPagesGet request = new PixivIllustPagesGet(130000503L);
            Map<String, String> header = new HashMap<>();
            header.put("cookie", "_cookie");
            request.setHeader(header);
            request.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost",31051)));
            request.doRequest();
            List<PixivImageUrlInfo> imageInfos = request.getResponseList();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
