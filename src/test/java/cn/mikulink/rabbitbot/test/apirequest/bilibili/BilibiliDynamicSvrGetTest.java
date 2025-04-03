package cn.mikulink.rabbitbot.test.apirequest.bilibili;

import cn.mikulink.rabbitbot.apirequest.bilibili.BilibiliDynamicSvrGet;
import cn.mikulink.rabbitbot.entity.bilibili.BilibiliDynamicSvrCardInfo;
import cn.mikulink.rabbitbot.entity.bilibili.BilibiliDynamicSvrResponseInfo;
import com.alibaba.fastjson2.JSONObject;
import org.junit.Test;

public class BilibiliDynamicSvrGetTest {

    @Test
    public void test() {
        try {
            BilibiliDynamicSvrGet request = new BilibiliDynamicSvrGet();
//            request.setUid(127879L);
//            request.setOffsetDynamicId(523029134353942890L);
            request.getHeader().put("cookie", "DedeUserID=127879; DedeUserID__ckMd5=72db60a892f9991b; buvid_fp_plain=undefined; is-2022-channel=1; buvid4=D5ECE118-E3F6-4FBA-E4C5-AC0FFBF04A8E58748-023041517-T73Z3yjpdrLRXOgKO7QYuw%3D%3D; CURRENT_BLACKGAP=0; FEED_LIVE_VERSION=V_WATCHLATER_PIP_WINDOW3; buvid3=1C3EB410-7A77-D923-92FF-2A39092334B488170infoc; b_nut=1713091788; _uuid=C4CEC557-9127-102F1-101E8-694C9227185990932infoc; enable_web_push=DISABLE; header_theme_version=CLOSE; hit-dyn-v2=1; LIVE_BUVID=AUTO6217161164865352; rpdid=|(Y|RkYllR)0J'u~uYumJJR~; CURRENT_QUALITY=80; SESSDATA=e6fa186c%2C1749033064%2Cf55ab%2Ac2CjDCk8Z_xdiiRGxWKgiuUVeLJin0141JSYzoOWa2tyKfsOJW9bpaneRABPZA3RWkLr8SVnNWb29vZEFoenozaWtfNlRJSU44VWpQSnlFbFUzN2tGTDVYYjREQ0w3WGVSZmlQcUlYVnZ0MnRQazJlbWxqUFpHVFdtN0c5amd0RVBvZzB2ZmFRLUFnIIEC; bili_jct=36489a48e5d846705aa5ed2c74558c6d; fingerprint=1911338ed5b7e1c009f5d7b643ff71a8; bili_ticket=eyJhbGciOiJIUzI1NiIsImtpZCI6InMwMyIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MzM4MzQ2OTcsImlhdCI6MTczMzU3NTQzNywicGx0IjotMX0.FNeeA8TvmeX0GDC1r-nuCFoZxVYUIAZb7kWfHTWdIRY; bili_ticket_expires=1733834637; home_feed_column=5; sid=66jkvnhl; PVID=8; CURRENT_FNVAL=4048; buvid_fp=1911338ed5b7e1c009f5d7b643ff71a8; b_lsid=106E410DD7_193A8523A01; browser_resolution=1745-455; bp_t_offset_127879=1008709095651278848");
            request.doRequest();
            System.out.println(request.getBody());
            BilibiliDynamicSvrResponseInfo responseInfo = request.parseResponseInfo();
            BilibiliDynamicSvrCardInfo bilibiliDynamicSvrCardInfo = JSONObject.parseObject(responseInfo.getData().getCards().get(0).getCard(), BilibiliDynamicSvrCardInfo.class);

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
