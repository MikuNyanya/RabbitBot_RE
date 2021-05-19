package cn.mikulink.rabbitbot.test.apirequest.bilibili;

import cn.mikulink.rabbitbot.apirequest.bilibili.BilibiliDynamicSvrGet;
import cn.mikulink.rabbitbot.entity.bilibili.BilibiliDynamicSvrCardInfo;
import cn.mikulink.rabbitbot.entity.bilibili.BilibiliDynamicSvrResponseInfo;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class BilibiliDynamicSvrGetTest {

    @Test
    public void test() {
        try {
            BilibiliDynamicSvrGet request = new BilibiliDynamicSvrGet();
//            request.setUid(127879L);
//            request.setOffsetDynamicId(523029134353942890L);
            request.getHeader().put("cookie", "_uuid=76DB4650-3E6B-806A-E84D-BE077396564524411infoc; buvid3=A3757A40-56F3-4084-A9DC-A288CE8CD83134752infoc; fingerprint=33f509741c3fa89b3c9702391c08240e; buvid_fp=A3757A40-56F3-4084-A9DC-A288CE8CD83134752infoc; buvid_fp_plain=A3757A40-56F3-4084-A9DC-A288CE8CD83134752infoc; SESSDATA=d797bbe6%2C1636980140%2C9f2e9%2A51; bili_jct=dd25b851b25362fadd4e1100e878019b; DedeUserID=1710206913; DedeUserID__ckMd5=23fb05175d3b01b1; sid=623ozdar");
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
