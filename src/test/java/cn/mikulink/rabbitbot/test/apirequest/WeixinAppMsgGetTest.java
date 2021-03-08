package cn.mikulink.rabbitbot.test.apirequest;

import cn.mikulink.rabbitbot.apirequest.weixin.WeixinAppMsgGet;
import cn.mikulink.rabbitbot.entity.apirequest.weixin.WeiXinAppMsgInfo;
import org.junit.Test;

import java.util.List;

public class WeixinAppMsgGetTest {

    @Test
    public void test() {

        try {
            WeixinAppMsgGet request = new WeixinAppMsgGet();
            request.setAccessToken("AccessToken");
            request.setFakeid("MzI4NzcwNjY4NQ==");
//            request.setCookie("曲奇");

            request.doRequest();
            request.isInvalidSessionError();
            List<WeiXinAppMsgInfo> list = request.getMsgList();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
