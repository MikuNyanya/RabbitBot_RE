package cn.mikulink.rabbitbot.test.apirequest.pixiv;

import cn.mikulink.rabbitbot.modules.pixiv.api.PixivIllustRankGet;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivRankImageInfo;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PixivIllustRankGetTest {
    @Test
    public void test() {
        try {
            PixivIllustRankGet request = new PixivIllustRankGet();
            Map<String, String> header = new HashMap<>();
            header.put("cookie", "_cookie");
            request.setHeader(header);
            request.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost",31051)));
            request.setMode("daily");
            request.setContent("illust");
            request.doRequest();
            List<PixivRankImageInfo> imageInfos = request.getResponseList();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
