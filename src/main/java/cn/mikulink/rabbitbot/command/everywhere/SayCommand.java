package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantFreeTime;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.FreeTimeService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 说些日常句子
 */
@Command
public class SayCommand extends EverywhereCommand {
    @Autowired
    private FreeTimeService freeTimeService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Say", "say");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        if (ConstantFreeTime.MSG_TYPE_FREE_TIME.size() <= 0) {
            return RabbitBotMessageBuilder.createMessageText(ConstantFreeTime.MSG_TYPE_FREE_TIME_EMPTY);
        }

        //随机一条日常语句
        String msg = freeTimeService.randomMsg();
        return RabbitBotMessageBuilder.createMessageText(msg);
    }

}
