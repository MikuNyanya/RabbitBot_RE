package cn.mikulink.rabbitbot.test.apirequest;

import cn.mikulink.rabbitbot.apirequest.tracemoe.TracemoeSearch;
import org.junit.Test;

/**
 * created by MikuNyanya on 2021/3/17 13:31
 * For the Reisen
 */
public class TracemoeTest {

    @Test
    public void tracemoeSearchTest() {
        try {
            TracemoeSearch request = new TracemoeSearch();
            request.setImgUrl("http://b-ssl.duitang.com/uploads/item/201605/06/20160506193448_e8wxA.png");
            request.doRequest();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
