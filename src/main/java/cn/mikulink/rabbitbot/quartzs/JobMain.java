package cn.mikulink.rabbitbot.quartzs;


import cn.mikulink.rabbitbot.bot.RabbitBot;
import cn.mikulink.rabbitbot.constant.ConstantWeiboNews;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.service.BilibiliService;
import cn.mikulink.rabbitbot.service.FreeTimeService;
import cn.mikulink.rabbitbot.service.WeiboNewsService;
import cn.mikulink.rabbitbot.service.sys.SwitchService;
import cn.mikulink.rabbitbot.utils.DateUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * create by MikuLink on 2019/12/3 12:58
 * for the Reisen
 * <p>
 * 1分钟执行一次的定时器
 */
@Component
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

    public void execute() {
        //日常语句
        freeTimeRabbit();

        //B站视频动态
        biliDynamicSvrPush();

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

        String msg = freeTimeService.randomMsg();

        //给每个群发送消息
        try {
            //给每个群发送报时
            ContactList<Group> groupList = RabbitBot.getBot().getGroups();
            for (Group groupInfo : groupList) {
                //检查功能开关
                ReString reStringSwitch = switchService.switchCheck(null, groupInfo, "say");
                if (!reStringSwitch.isSuccess()) {
                    continue;
                }
                groupInfo.sendMessage(msg);
            }
        } catch (Exception ex) {
            logger.error("日常语句推送执行异常:" + ex.toString(), ex);
        }

        //刷新最后发送时间
        free_time_last_send_time = System.currentTimeMillis();
        //刷新下次发送的随机延迟时间
        free_time_random_send_time = 1000L * 60 * RandomUtil.roll(SPLIT_RANDOM_MAX + 1);
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
