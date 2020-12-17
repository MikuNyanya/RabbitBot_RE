package cn.mikulink.command.everywhere;

import cn.mikulink.entity.CommandProperties;
import cn.mikulink.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 兔叽信息
 */
@Command
public class SystemCommand extends BaseEveryWhereCommand {
    @Value("${bot.version}")
    private String version;
    //兔叽
    @Value("${bot.name.cn:兔叽}")
    public String rabbit_bot_name;
    //RabbitBot
    @Value("${bot.name.en:RabbitBot}")
    public String rabbit_bot_name_en;


    @Override
    public CommandProperties properties() {
        return new CommandProperties("System", "system");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        StringBuilder msg = new StringBuilder();
        msg.append("==========\n");
        msg.append(String.format("[Name] %s(%s)\n", rabbit_bot_name, rabbit_bot_name_en));
        msg.append("[Birthday] 2019-12-3\n");
        msg.append("[Version] ").append(version).append("\n");
        msg.append("System Online\n");
        msg.append("兔叽增员中...\n");
        msg.append("==========");

        return new PlainText(msg.toString());
    }

}
