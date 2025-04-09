package cn.mikulink.rabbitbot.test.service;

import cn.mikulink.rabbitbot.entity.apirequest.bilibili.BilibiliDynamicSvrCardInfo;
import cn.mikulink.rabbitbot.entity.apirequest.bilibili.BilibiliDynamicSvrCardsInfo;
import cn.mikulink.rabbitbot.service.BilibiliService;
import com.alibaba.fastjson2.JSONObject;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * created by MikuNyanya on 2021/5/20 10:35
 * For the Reisen
 */
public class BilibiliServiceTest {

    @Test
    public void test() {
        String str = "\uD835\uDCD6\uD835\uDCEE\uD835\uDCFD \uD835\uDD02\uD835\uDCF8\uD835\uDCFE\uD835\uDCFB \uD835\uDCEA\uD835\uDCFC\uD835\uDCFC \uD835\uDCEB\uD835\uDCEA\uD835\uDCEC\uD835\uDCF4 \uD835\uDCF1\uD835\uDCEE\uD835\uDCFB\uD835\uDCEE";
        try {
            String jsonStr = "{\"desc\":{\"uid\":1733,\"type\":512,\"rid\":402351,\"acl\":0,\"view\":2125928,\"repost\":411,\"like\":84905,\"is_liked\":0,\"dynamic_id\":524884521572058722,\"timestamp\":1621047601,\"pre_dy_id\":0,\"orig_dy_id\":0,\"orig_type\":0,\"uid_type\":2,\"stype\":4,\"r_type\":1,\"inner_id\":0,\"status\":1,\"dynamic_id_str\":\"524884521572058722\",\"pre_dy_id_str\":\"0\",\"orig_dy_id_str\":\"0\",\"rid_str\":\"402351\"},\"card\":\"{\\\"aid\\\":845596147,\\\"apiSeasonInfo\\\":{\\\"bgm_type\\\":4,\\\"cover\\\":\\\"https:\\\\/\\\\/i0.hdslb.com\\\\/bfs\\\\/bangumi\\\\/image\\\\/c25750b639ddb810c8eaebfbd1766cf19a44236c.jpg\\\",\\\"is_finish\\\":0,\\\"season_id\\\":1733,\\\"title\\\":\\\"罗小黑战记\\\",\\\"total_count\\\":40,\\\"ts\\\":1621430881,\\\"type_name\\\":\\\"国创\\\"},\\\"bullet_count\\\":19479,\\\"cover\\\":\\\"https:\\\\/\\\\/i0.hdslb.com\\\\/bfs\\\\/archive\\\\/3d1f9c9199dbb4a2276a81152291abfc5cd94c2e.jpg\\\",\\\"episode_id\\\":402351,\\\"index\\\":\\\"33\\\",\\\"index_title\\\":\\\"无法完成的任务\\\",\\\"item\\\":{\\\"at_control\\\":\\\"\\\"},\\\"new_desc\\\":\\\"第33话 无法完成的任务\\\",\\\"online_finish\\\":0,\\\"play_count\\\":1321274,\\\"reply_count\\\":7000,\\\"url\\\":\\\"https:\\\\/\\\\/www.bilibili.com\\\\/bangumi\\\\/play\\\\/ep402351\\\"}\",\"extend_json\":\"{\\\"like_icon\\\":{\\\"action\\\":\\\"\\\",\\\"action_url\\\":\\\"\\\",\\\"end\\\":\\\"\\\",\\\"end_url\\\":\\\"\\\",\\\"start\\\":\\\"\\\",\\\"start_url\\\":\\\"\\\"},\\\"repeat_resource\\\":{\\\"items\\\":[{\\\"rid\\\":845596147,\\\"type\\\":8}]}}\",\"display\":{\"cover_play_icon_url\":\"https://i0.hdslb.com/bfs/album/2269afa7897830b397797ebe5f032b899b405c67.png\"}}";
            BilibiliDynamicSvrCardsInfo info = JSONObject.parseObject(jsonStr,BilibiliDynamicSvrCardsInfo.class);
            BilibiliDynamicSvrCardInfo cardInfo = JSONObject.parseObject(info.getCard(), BilibiliDynamicSvrCardInfo.class);

            ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
            BilibiliService service = context.getBean(BilibiliService.class);

            service.parseDynamicSvrBody(info);

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
