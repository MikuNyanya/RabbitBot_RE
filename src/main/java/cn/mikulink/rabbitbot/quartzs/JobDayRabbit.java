package cn.mikulink.rabbitbot.quartzs;

import cn.mikulink.rabbitbot.command.everywhere.LoliconAppCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * <p>
 * 一天执行一次的定时器
 */
@Component
public class JobDayRabbit {
    private static final Logger logger = LoggerFactory.getLogger(JobDayRabbit.class);

    public void execute() {
        //目前只重置推送次数
        LoliconAppCommand.PUSH_INDEX = 300;
    }
}
