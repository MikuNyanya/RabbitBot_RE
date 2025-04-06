package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.sys.annotate.Command;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 清屏
 */
@Command
public class CLSCommand extends EverywhereCommand {
    private static String clsMessage = null;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("clear", "cls", "CLS", "清屏");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        if (clsMessage == null) {
            clsMessage = "";
            for (int i = 0; i < 20; i++) clsMessage += "\n";
            clsMessage += "已清屏!";
        }
        return RabbitBotMessageBuilder.createMessageText(clsMessage);
    }

}
