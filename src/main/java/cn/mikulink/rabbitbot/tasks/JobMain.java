package cn.mikulink.rabbitbot.tasks;


import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.bot.RabbitBotSender;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantWeiboNews;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoStatuses;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoWeiboHomeTimeline;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.BilibiliService;
import cn.mikulink.rabbitbot.service.FreeTimeService;
import cn.mikulink.rabbitbot.service.WeiboNewsService;
import cn.mikulink.rabbitbot.service.sys.ConfigService;
import cn.mikulink.rabbitbot.utils.DateUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * create by MikuLink on 2019/12/3 12:58
 * for the Reisen
 * <p>
 * 1分钟执行一次的定时器
 */
@Slf4j
@Component
@EnableScheduling
public class JobMain {

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
    private BilibiliService bilibiliService;
    @Autowired
    private FreeTimeService freeTimeService;
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private RabbitBotSender rabbitBotSender;
    @Autowired
    private ConfigService configService;


    @Scheduled(cron = "0 * * * * ?")
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
        try {
            String msg = freeTimeService.randomMsg();

            //给每个群发送消息
            MessageInfo messageInfo = RabbitBotMessageBuilder.createMessageImage(msg);
            List<GroupInfo> groupList = rabbitBotService.getGroupList();
            for (GroupInfo groupInfo : groupList) {
                rabbitBotSender.sendGroupMessage(groupInfo.getGroupId(), messageInfo.getMessage());
            }

        } catch (Exception ex) {
            log.error("日常语句推送执行异常:" + ex.getMessage(), ex);
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
            log.error("清理已过期的搜图指令时发生异常", ex);
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
            log.error("B站视频动态推送执行异常:" + ex.toString(), ex);
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
            //执行微博消息推送
            //获取接口返回
            InfoWeiboHomeTimeline weiboNews = weiboNewsService.getWeiboNews(10);

            //刷新最后推文标识，但如果一次请求中没有获取到新数据，since_id会为0
            Long sinceId = weiboNews.getSince_id();
            if (0 != sinceId) {
                log.info(String.format("微博sinceId刷新：[%s]->[%s]", ConstantCommon.common_config.get("sinceId"), sinceId));
                //刷新sinceId配置
                ConstantCommon.common_config.put("sinceId", String.valueOf(sinceId));
                //更新配置文件
                configService.refreshConfigFile();
            }

            //获取微博内容
            List<InfoStatuses> statuses = weiboNews.getStatuses();

            if (null == statuses || statuses.size() == 0) {
                return;
            }

            //发送微博
            for (InfoStatuses info : statuses) {
                //解析微博报文
                List<MessageChain> msgChain = weiboNewsService.parseWeiboBody(info);
                if (null != info.getRetweeted_status()) {
                    //追加被转发的微博消息
                    msgChain.addAll(weiboNewsService.parseWeiboBody(info.getRetweeted_status(), true));
                }

                //每个群发送微博推送
                MessageInfo messageInfo = new MessageInfo(msgChain);
//                List<GroupInfo> groupList = rabbitBotService.getGroupList();
//                for (GroupInfo groupInfo : groupList) {
//                    rabbitBotSender.sendGroupMessage(groupInfo.getGroupId(), messageInfo.getMessage());
//                }
                rabbitBotSender.sendPrivateMessage(455806936L,messageInfo);
            }


        } catch (Exception ex) {
            log.error("微博消息推送执行异常:" + ex.getMessage(), ex);
        }

        //刷新最后发送时间
        ConstantWeiboNews.weibo_news_last_send_time = System.currentTimeMillis();
    }
}
