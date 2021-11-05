package cn.mikulink.rabbitbot.service;

import net.mamoe.mirai.event.events.NudgeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author MikuLink
 * @date 2021/11/4 14:33
 * for the Reisen
 * 戳一戳相关服务
 */
@Service
public class NudgeService {
    private static final Logger logger = LoggerFactory.getLogger(NudgeService.class);

    //兔叽账号
    @Value("${bot.account}")
    private Long botAccount;

    @Autowired
    private FreeTimeService freeTimeService;

    /**
     * 接收到戳一戳
     */
    public void onNudge(NudgeEvent event) {
        Long targetId = event.getTarget().getId();
        if (botAccount.equals(targetId)) {
            //如果目标是兔叽，则触发日常语句回复
            String freeTimeMsg = freeTimeService.randomMsg();
            event.getSubject().sendMessage(freeTimeMsg);
        }
    }
}
