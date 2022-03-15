package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.newpneumonia.VirusInfo;
import cn.mikulink.rabbitbot.service.VirusService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2022/03/15 17:04
 * for the Reisen
 * <p>
 * 新冠数据
 */
@Command
public class VirusCommand extends BaseEveryWhereCommand {
    @Autowired
    private VirusService virusService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("virus", "新冠");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //简写，反正不是永久性的。。。大概吧，这已经是新冠第三年了
        VirusInfo info = virusService.getVirusInfo();
        return virusService.parseMsg(info);
    }

}
