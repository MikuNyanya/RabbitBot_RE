package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.bot.penguincenter.NapCatApi;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.db.RabbitbotPrivateMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.DeepSeekService;
import cn.mikulink.rabbitbot.service.db.DeepseekChatRecordService;
import cn.mikulink.rabbitbot.service.db.RabbitbotPrivateMessageService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 测试
 */
@Command
public class TestCommand extends EverywhereCommand {
    @Autowired
    private NapCatApi napCatApi;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Test", "测试");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {

        List<GroupInfo> groupInfoList = napCatApi.getGroupList();


        return RabbitBotMessageBuilder.createMessageText("success");
    }

}
