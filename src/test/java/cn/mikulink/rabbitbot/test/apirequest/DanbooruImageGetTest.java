package cn.mikulink.rabbitbot.test.apirequest;

import cn.hutool.http.*;
import cn.mikulink.rabbitbot.apirequest.danbooru.DanbooruImageGet;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.DanbooruImageInfo;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.ImageUtil;
import cn.mikulink.rabbitbot.utils.ProxyUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;

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


    @Test
    public void falseknees() {

        try {
            for (int i = 1; i <= 435; i++) {
                String url = "https://www.falseknees.com/" + i + ".html";
                try {

                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 33001));
                    //通过请求获取到返回的页面
                    String htmlStr = null;
                    byte[] temoReqps = HttpsUtil.doGet(url, proxy);
                    htmlStr = new String(temoReqps);
                    //使用jsoup解析html
                    Document document = Jsoup.parse(htmlStr);
                    //选择目标节点，类似于JS的选择器
                    Elements elements = document.select("img[title]");

                    for (Element element : elements) {
                        String imageUrl = "https://www.falseknees.com/" + element.attr("src");
                        String imgFileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                        HashMap<String, String> header = new HashMap<>();
                        header.put("referer", url);
                        ImageUtil.downloadImage(header, imageUrl, "d:\\1", imgFileName, proxy);
                        System.out.println("图片下载完毕:" + imgFileName);
                    }
                } catch (Exception ex) {
                    System.err.println("图片下载异常:" + url);
                }
            }
            System.out.println("\n\n所有图片下载完毕");

        } catch (Exception ex) {
            System.err.println("图片下载异常\n");
            ex.printStackTrace();
        }
    }
}
