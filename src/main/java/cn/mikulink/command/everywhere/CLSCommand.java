package cn.mikulink.command.everywhere;


import cn.mikulink.entity.CommandProperties;
import cn.mikulink.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 清屏
 */
@Command
public class CLSCommand extends BaseEveryWhereCommand {
    private static String clsMessage = null;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("clear", "cls", "CLS", "清屏");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        if (clsMessage == null) {
            clsMessage = "";
            for (int i = 0; i < 20; i++) clsMessage += "\n";
            clsMessage += "已清屏!";
        }
        return new PlainText(clsMessage);
    }

}
