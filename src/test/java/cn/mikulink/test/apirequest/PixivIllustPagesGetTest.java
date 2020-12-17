package cn.mikulink.test.apirequest;

import cn.mikulink.apirequest.pixiv.PixivIllustPagesGet;
import cn.mikulink.entity.pixiv.PixivImageUrlInfo;
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
