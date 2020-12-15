package cn.mikulink.command.everywhere;


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
 * 清屏
 */
@Component
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
