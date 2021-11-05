package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.bot.RabbitBot;
import cn.mikulink.rabbitbot.constant.ConstantWorld;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.sys.SwitchService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2021/07/12 16:02
 * for the Reisen
 * <p>
 * 兔叽的世界频道
 */
@Command
public class WorldCommand extends BaseEveryWhereCommand {
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private SwitchService switchService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("world", "world");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //权限检查
        if (!rabbitBotService.isMaster(sender.getId())) {
            return new PlainText(RandomUtil.rollStrFromList(ConstantWorld.COMMAND_MASTER_ONLY));
        }

        if (CollectionUtil.isEmpty(args)) {
            return new PlainText(ConstantWorld.ARGS_ERROR);
        }

        //组装消息
        MessageChain resultMsg = MessageUtils.newChain();

        for (int i = 0; i < messageChain.size(); i++) {
            //忽略第一个，这是消息参数
            if (i == 0) {
                continue;
            }
            //第二段必定为字符串，抹掉指令
            if (i == 1) {
                String text = ((PlainText) messageChain.get(i)).contentToString();
                text = text.replace(".world", "");
                if (StringUtil.isNotEmpty(text)) {
                    resultMsg = resultMsg.plus(new PlainText(text));
                }
                continue;
            }

            //后续原样拼接
            resultMsg = resultMsg.plus(messageChain.get(i));
        }

        //发送给每个群
        ContactList<Group> groupList = RabbitBot.getBot().getGroups();
        for (Group group : groupList) {
            //检查功能开关
//            ReString reStringSwitch = switchService.switchCheck(null, group, "receiveWorld");
//            if (!reStringSwitch.isSuccess()) {
//                continue;
//            }

            //发送消息
            group.sendMessage(resultMsg);
        }

        return null;
    }
}
