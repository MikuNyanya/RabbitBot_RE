package cn.mikulink.rabbitbot.test.service;

import cn.mikulink.rabbitbot.entity.apirequest.weixin.WeiXinAppMsgInfo;
import cn.mikulink.rabbitbot.service.WeiXinAppMsgService;
import net.mamoe.mirai.message.data.MessageChain;
import org.junit.Test;

/**
 * @author MikuLink
 * @date 2021/3/3 10:15
 * for the Reisen
 */
public class WeiXinAppMsgServiceTest {

    @Test
    public void parseNewsTodayTest() {
        WeiXinAppMsgInfo info = new WeiXinAppMsgInfo();
        info.setLink("http://mp.weixin.qq.com/s?__biz=MzI4NzcwNjY4NQ==&mid=2247516645&idx=1&sn=105e223a25f8df681cb4a0574ba86308&chksm=ebcb5ab7dcbcd3a19316b8275d80f81cec737da41e2621d7428f5c2dae6161c8ab7dd5c02c3b#rd");

        WeiXinAppMsgService service = new WeiXinAppMsgService();
        try {
            MessageChain messages = service.parseNewsToday(info);
            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
