package cn.mikulink.rabbitbot.test.utils;

import cn.mikulink.rabbitbot.utils.ImageUtil;
import cn.mikulink.rabbitbot.utils.ProxyUtil;
import org.junit.Test;

import java.net.Proxy;
import java.util.HashMap;

public class ImageUtilTest {
    @Test
    public void test(){
        try{
//           String localPath = ImageUtil.downloadImage("http://wx2.sinaimg.cn/thumbnail/92ccf492gy1glukvzgh3gj21900u07wh.jpg");

            Proxy proxy = ProxyUtil.getProxy("127.0.0.1",31051);
            HashMap<String, String> header = new HashMap<>();
            header.put("referer", "https://www.pixiv.net/artworks/" + 86361356);
            String localPath_proxy = ImageUtil.downloadImage(header,"https://i.pximg.net/img-original/img/2020/12/18/00/00/09/86361356_p0.jpg","data/images",null,proxy);

            System.out.println("");
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
