package cn.mikulink.rabbitbot.test.apirequest;

import cn.mikulink.rabbitbot.apirequest.saucenao.SaucenaoImageSearch;
import cn.mikulink.rabbitbot.entity.apirequest.saucenao.SaucenaoSearchResult;
import org.junit.Test;

public class SaucenaoImageSearchTest {

    @Test
    public void test() {

        try {
            SaucenaoImageSearch request = new SaucenaoImageSearch();
            request.setAccessToken("AccessToken");
            request.setNumres(1);
            request.setUrl("http://gchat.qpic.cn/gchatpic_new/455806936/3987173185-2313513830-E56BF67AC506FD30EE6B5F6169101AF8/0?term=2");

            request.doRequest();
            SaucenaoSearchResult saucenaoSearchResult = request.getEntity();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
