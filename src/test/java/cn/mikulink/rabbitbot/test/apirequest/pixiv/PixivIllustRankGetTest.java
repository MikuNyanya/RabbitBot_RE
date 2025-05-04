package cn.mikulink.rabbitbot.test.apirequest.pixiv;

import cn.mikulink.rabbitbot.modules.pixiv.api.PixivIllustRankGet;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivRankImageInfo;
import org.junit.Test;

import java.util.List;

public class PixivIllustRankGetTest {
    @Test
    public void test() {
        try {
            PixivIllustRankGet request = new PixivIllustRankGet();
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
