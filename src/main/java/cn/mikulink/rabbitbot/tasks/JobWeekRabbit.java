package cn.mikulink.rabbitbot.tasks;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.bot.RabbitBotSender;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.modules.oiapi.OiApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * create by MikuLink on 2019/12/3 12:58
 * for the Reisen
 * <p>
 * 每周执行一次的定时器
 */
@Slf4j
@Component
@EnableScheduling
public class JobWeekRabbit {

    @Value("${bot.jobOpen:false}")
    private boolean jobOpen;

    @Autowired
    private OiApiService oiApiService;
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private RabbitBotSender rabbitBotSender;

    //开封菜疯狂星期四文案 每周四11:50发
    @Scheduled(cron = "0 50 11 ? * 4")
    public void execute() {
        //定时任务开关
        if (!jobOpen) {
            return;
        }

        try {
            String msg = oiApiService.kfcV50();

            //给每个群发送消息
            MessageInfo messageInfo = RabbitBotMessageBuilder.createMessageText(msg);
            List<GroupInfo> groupList = rabbitBotService.getGroupList();
            for (GroupInfo groupInfo : groupList) {
                rabbitBotSender.sendGroupMessage(groupInfo.getGroupId(), messageInfo.getMessage());
            }

        } catch (Exception ex) {
            log.error("KFC疯狂星期四发送执行异常:" + ex.getMessage(), ex);
        }
    }

}
