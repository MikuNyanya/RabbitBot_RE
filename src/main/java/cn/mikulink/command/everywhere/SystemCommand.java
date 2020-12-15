package cn.mikulink.command.everywhere;

import cn.mikulink.constant.ConstantCommon;
import cn.mikulink.entity.CommandProperties;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 兔叽信息
 */
@Component
public class SystemCommand extends BaseEveryWhereCommand {
    @Override
    public CommandProperties properties() {
        return new CommandProperties("System", "system");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        StringBuilder msg = new StringBuilder();
        msg.append("==========\n");
        msg.append(String.format("[Name] %s(%s)\n", ConstantCommon.RABBIT_BOT_NAME, ConstantCommon.RABBIT_BOT_NAME_EN));
        msg.append("[Birthday] 2019-12-3\n");
        msg.append("[Version] V2.0\n");
        msg.append("System Online\n");
        msg.append("兔叽增员中...\n");
        msg.append("==========");

        return new PlainText(msg.toString());
    }

}
