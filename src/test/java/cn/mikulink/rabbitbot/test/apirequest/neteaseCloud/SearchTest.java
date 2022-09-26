package cn.mikulink.rabbitbot.test.apirequest.neteaseCloud;

import cn.mikulink.rabbitbot.apirequest.neteaseCloudMusic.NeteaseCloudSearch;
import cn.mikulink.rabbitbot.entity.apirequest.neteaseCloud.NeteaseCloudSearchResponse;
import org.junit.Test;

/**
 * @author MikuLink
 * @date 2022/9/26 15:53
 * for the Reisen
 */
public class SearchTest {

    @Test
    public void test() {
        try {
            NeteaseCloudSearch request = new NeteaseCloudSearch();
            request.setUrl("http://apiurl");
            request.setKeywords("リップ");

            request.doRequest();
            NeteaseCloudSearchResponse rsp = request.parseResponseInfo();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
