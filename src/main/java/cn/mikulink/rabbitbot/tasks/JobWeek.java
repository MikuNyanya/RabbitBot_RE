package cn.mikulink.rabbitbot.tasks;

import cn.mikulink.rabbitbot.bot.RabbitBot;
import cn.mikulink.rabbitbot.service.MeiPinService;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * create by MikuLink on 2022/2/21 14:49
 * for the Reisen
 * <p>
 * 周定时器
 */
@Component
//@EnableScheduling
@Slf4j
public class JobWeek {
    private static final Logger logger = LoggerFactory.getLogger(JobWeek.class);

    @Autowired
    private MeiPinService meiPinService;

//    @Scheduled(cron = "0 0 10 ? * MON")
    public void meipin4chanPush() {
        //没品网站文章更新推送
        //计划每周一上午10点进行推送
        try {
//            MessageChain msgChain = meiPinService.lastMeiPinMsgChain();
//            //给每个群推送消息
//            ContactList<Group> groupList = RabbitBot.getBot().getGroups();
//            for (Group groupInfo : groupList) {
//                try {
//                    groupInfo.sendMessage(msgChain);
//                } catch (kotlinx.coroutines.TimeoutCancellationException ex) {
//                    logger.warn("没品文章mirai发送超时");
//                }
//
//                //每个群之间间隔半秒意思一下
//                Thread.sleep(500);
//            }
        } catch (Exception ex) {
            log.warn("没品文章推送异常", ex);
        }
    }
}
