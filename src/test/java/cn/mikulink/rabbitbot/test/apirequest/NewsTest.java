package cn.mikulink.rabbitbot.test.apirequest;

import cn.mikulink.rabbitbot.apirequest.soyiji.SoyijiGet;
import org.junit.Test;

/**
 * created by MikuNyanya on 2022/2/23 15:43
 * For the Reisen
 */
public class NewsTest {
    @Test
    public void test() {
        try {

            SoyijiGet soyijiGet = new SoyijiGet();
            soyijiGet.doRequest();

            String str = soyijiGet.getImageUrl();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
