package cn.mikulink.rabbitbot.test.apirequest.pixiv;

import cn.mikulink.rabbitbot.apirequest.pixiv.PixivIllustPagesGet;
import cn.mikulink.rabbitbot.entity.apirequest.pixiv.PixivImageUrlInfo;
import org.junit.Test;

import java.util.List;

public class PixivIllustPagesGetTest {

    @Test
    public void test() {
        try {
            PixivIllustPagesGet request = new PixivIllustPagesGet(11687088L);
            request.doRequest();
            List<PixivImageUrlInfo> imageInfos = request.getResponseList();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
