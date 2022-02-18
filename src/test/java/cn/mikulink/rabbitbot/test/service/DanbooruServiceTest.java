package cn.mikulink.rabbitbot.test.service;

import cn.mikulink.rabbitbot.apirequest.danbooru.DanbooruImageGet;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.DanbooruImageInfo;
import cn.mikulink.rabbitbot.utils.ImageUtil;
import cn.mikulink.rabbitbot.utils.ProxyUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.junit.Test;

import java.io.File;
import java.net.Proxy;

/**
 * created by MikuNyanya on 2022/2/18 14:43
 * For the Reisen
 */
public class DanbooruServiceTest {

    @Test
    public void test() {
        String doubleId = "5135715";
        try {
            Proxy proxy = ProxyUtil.getProxy("localhost", 31051);

            //目标页面
            DanbooruImageGet request = new DanbooruImageGet();
            request.setDanbooruId(doubleId);
            request.doRequest();
            request.setProxy(proxy);
            DanbooruImageInfo imageInfo = request.parseDanbooruImageInfo();

            String imageUrl = imageInfo.getLargeFileUrl();
            //下载图片
            String localUrl = ImageUtil.downloadImage(null,
                    imageUrl,
                    ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "danbooru",
                    null,
                    proxy);
            if (StringUtil.isNotEmpty(localUrl)) {
                System.out.println(localUrl);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
