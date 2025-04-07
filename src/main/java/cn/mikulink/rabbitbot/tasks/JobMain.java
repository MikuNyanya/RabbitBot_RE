package cn.mikulink.rabbitbot.tasks;


import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantWeiboNews;
import cn.mikulink.rabbitbot.service.BilibiliService;
import cn.mikulink.rabbitbot.service.FreeTimeService;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.service.WeiboNewsService;
import cn.mikulink.rabbitbot.service.sys.SwitchService;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.DateUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * create by MikuLink on 2019/12/3 12:58
 * for the Reisen
 * <p>
 * 1分钟执行一次的定时器
 */
@Component
@EnableScheduling
public class JobMain {
    private static final Logger logger = LoggerFactory.getLogger(JobMain.class);

    //正常间隔(毫秒) 目前为2小时
    private static final Long SPLIT_NORMAL = 1000L * 60 * 60 * 2;
    //随机间隔最大值(分钟) 目前最长延迟1小时
    private static final Integer SPLIT_RANDOM_MAX = 60;

    //日常语句最后发送时间
    private static Long free_time_last_send_time = System.currentTimeMillis();
    //日常语句下次发送的随机间隔时间
    private static Long free_time_random_send_time = 0L;

    @Autowired
    private WeiboNewsService weiboNewsService;
    @Autowired
    private SwitchService switchService;
    @Autowired
    private BilibiliService bilibiliService;
    @Autowired
    private FreeTimeService freeTimeService;
    @Autowired
    private RabbitBotService rabbitBotService;

//    @Scheduled(cron = "0 * * * * ?")
    public void execute() {
        //日常语句
        freeTimeRabbit();

        //清理已过期的搜图指令
//        imageSearchExpire();

        //B站视频动态
//        biliDynamicSvrPush();

        //微博最新消息
        weiboNews();
    }


    public static List<MessageChain> msgList = new ArrayList<>();

    @Scheduled(cron = "0 0/10 * * * ?")
    public void execin10min() {
        //十分钟执行一次，发送队列信息
        if (CollectionUtil.isEmpty(msgList)) {
            return;
        }

        logger.info("发送群消息/10min");
        rabbitBotService.sendGroupMessage(669863883L, msgList.get(0));

        msgList.remove(0);
    }


    //日常兔子
    private void freeTimeRabbit() {
        //检测发送间隔 加上随机间隔时间
        if (System.currentTimeMillis() - free_time_last_send_time < (SPLIT_NORMAL + free_time_random_send_time)) {
            return;
        }

        //大晚上的就不发了
        int hour = DateUtil.getHour();
        if (hour < 8) {
            return;
        }

        String msg = freeTimeService.randomMsg();

        //给每个群发送消息
        try {
            MessageChain result = MessageUtils.newChain().plus(msg);
            rabbitBotService.sendGroupMessage(669863883L, result);
        } catch (Exception ex) {
            logger.error("日常语句推送执行异常:" + ex.toString(), ex);
        }

        //刷新最后发送时间
        free_time_last_send_time = System.currentTimeMillis();
        //刷新下次发送的随机延迟时间
        free_time_random_send_time = 1000L * 60 * RandomUtil.roll(SPLIT_RANDOM_MAX + 1);
    }

    //清理已过期的搜图指令
    private void imageSearchExpire() {
        try {
//            Iterator<ImageSearchMemberInfo> iterator = ConstantImage.IMAGE_SEARCH_WITE_LIST.iterator();
//            while (iterator.hasNext()){
//                ImageSearchMemberInfo searchMemberInfo = iterator.next();
//                if (System.currentTimeMillis() >= searchMemberInfo.getExpireIn().getTime()) {
//                    iterator.remove();
//                }
//            }

            ConstantImage.IMAGE_SEARCH_WITE_LIST.removeIf(item -> System.currentTimeMillis() >= item.getExpireIn().getTime());
        } catch (Exception ex) {
            logger.error("清理已过期的搜图指令时发生异常", ex);
        }
    }

    //B站视频动态
    private void biliDynamicSvrPush() {
        //检测发送间隔
//        if (System.currentTimeMillis() - ConstantBilibili.bili_dynamicdsvr_last_send_time < ConstantBilibili.bili_dynamicdsvr_push_sprit_time) {
//            return;
//        }

        try {
            //执行一次消息推送
            bilibiliService.doDynamicSvrPush();
        } catch (Exception ex) {
            logger.error("B站视频动态推送执行异常:" + ex.toString(), ex);
        }

        //刷新最后发送时间
//        ConstantBilibili.bili_dynamicdsvr_last_send_time = System.currentTimeMillis();
    }

    //微信最新消息
    private void weiboNews() {
        //检测发送间隔
        if (System.currentTimeMillis() - ConstantWeiboNews.weibo_news_last_send_time < ConstantWeiboNews.weibo_news_sprit_time) {
            return;
        }

        try {
            //执行一次微博消息推送
            weiboNewsService.doPushWeiboNews();
        } catch (Exception ex) {
            logger.error("微博消息推送执行异常:" + ex.toString(), ex);
        }

        //刷新最后发送时间
        ConstantWeiboNews.weibo_news_last_send_time = System.currentTimeMillis();
    }
}
