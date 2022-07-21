package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.MeiPinService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * created by MikuNyanya on 2022/2/21 14:30
 * For the Reisen
 * 由Fall_ark翻译的没品+4chan笑话集
 * http://meipin.im/
 * 已停更
 */
//@Command
@Slf4j
public class MeiPinCommand extends BaseEveryWhereCommand {

    @Autowired
    private MeiPinService meiPinService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("MeiPin", "meipin", "没品");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        MessageChain result = null;
        try {
            result = meiPinService.lastMeiPinMsgChain();
        } catch (Exception ex) {
            log.error("没品文章信息获取异常", ex);
            result = MessageUtils.newChain().plus("没品文章信息获取异常");
        }
        return result;
    }

}
