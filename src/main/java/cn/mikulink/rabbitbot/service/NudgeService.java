package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.event.events.NudgeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MikuLink
 * @date 2021/11/4 14:33
 * for the Reisen
 * 戳一戳相关服务
 */
@Service
public class NudgeService {
    private static final Logger logger = LoggerFactory.getLogger(NudgeService.class);

    public static Map<Long, Long> NUDGE_SPLIT_MAP = new HashMap<>();


    @Value("${nudge.split.time:10000}")
    private Long NUDGE_SPLIT_TIME = 10000L;
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private FreeTimeService freeTimeService;

    /**
     * 接收到戳一戳
     */
    public void onNudge(NudgeEvent event) {
        Long targetId = event.getTarget().getId();
            //操作间隔判断
            String timeCheck = rabbitBotService.commandTimeSplitCheck(NUDGE_SPLIT_MAP, event.getFrom().getId(), event.getFrom().getNick(),
                    NUDGE_SPLIT_TIME, RandomUtil.rollStrFromList(ConstantPixiv.SETU_SPLIT_ERROR_LIST));
            if (StringUtil.isNotEmpty(timeCheck)) {
                return;
            }
            //如果目标是兔叽，则触发日常语句回复
            String freeTimeMsg = freeTimeService.randomMsg();
            event.getSubject().sendMessage(freeTimeMsg);
            //刷新操作间隔
            NUDGE_SPLIT_MAP.put(event.getFrom().getId(), System.currentTimeMillis());

    }
}
