package cn.mikulink.rabbitbot.test.apirequest.pixiv;

import cn.mikulink.rabbitbot.apirequest.pixiv.PixivIllustGet;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class PixivIllustGetTest {

    @Test
    public void test() {
        try {
            PixivIllustGet request = new PixivIllustGet(75717389L);
            request.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("33837ddc-243b-4f60-91c1-11bd9b602eed.hk.node.touhou.place",88)));
            request.doRequest();
            PixivImageInfo imageInfo = request.getPixivImageInfo();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
