package cn.mikulink.rabbitbot.test.apirequest;

import cn.mikulink.rabbitbot.apirequest.weibo.WeiboHomeTimelineGet;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoWeiboHomeTimeline;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import org.junit.Test;

/**
 * created by MikuNyanya on 2022/2/23 15:43
 * For the Reisen
 */
public class WeiboNewsApi {
    @Test
    public void test(){
        try{

            WeiboHomeTimelineGet request = new WeiboHomeTimelineGet();
            request.setAccessToken("token");
            request.setPage(1);
            //每次获取最近的5条
            request.setCount(30);
//            request.setSince_id(NumberUtil.toLong(ConstantCommon.common_config.get("sinceId")));

            //发送请求
            request.doRequest();
            InfoWeiboHomeTimeline response = request.getEntity();



            System.out.println("");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
