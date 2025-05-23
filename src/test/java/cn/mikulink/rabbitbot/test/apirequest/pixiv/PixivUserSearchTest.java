package cn.mikulink.rabbitbot.test.apirequest.pixiv;

import cn.mikulink.rabbitbot.modules.pixiv.api.PixivUserSearch;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivUserInfo;
import org.junit.Test;

import java.util.List;

public class PixivUserSearchTest {

    @Test
    public void test() {
        try {
            PixivUserSearch request = new PixivUserSearch();
            request.getHeader().put("cookie", "曲奇");
            request.setPixivUserNick("薯子");
            request.doRequest();
            List<PixivUserInfo> pixivUserInfos = request.getResponseList();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
