package cn.mikulink.rabbitbot.test.apirequest;

import cn.hutool.http.*;
import cn.mikulink.rabbitbot.apirequest.danbooru.DanbooruImageGet;
import cn.mikulink.rabbitbot.entity.DanbooruImageInfo;
import cn.mikulink.rabbitbot.utils.ProxyUtil;
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
            request.setDanbooruId("5445814");
            request.setProxy(ProxyUtil.getProxy("127.0.0.1", 31051));
            request.doRequest();
            DanbooruImageInfo imageInfo = request.parseDanbooruImageInfo();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
