package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantFreeTime;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.filemanage.FileManagerFreeTime;
import cn.mikulink.rabbitbot.service.SwitchService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 说些日常句子
 */
@Command
public class SayCommand extends BaseEveryWhereCommand {

    @Autowired
    private SwitchService switchService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Say", "say");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //检查功能开关
        ReString reStringSwitch = switchService.switchCheck(sender, subject, "say");
        if (!reStringSwitch.isSuccess()) {
            return new PlainText(reStringSwitch.getMessage());
        }

        if (ConstantFreeTime.MSG_TYPE_FREE_TIME.size() <= 0) {
            return new PlainText(ConstantFreeTime.MSG_TYPE_FREE_TIME_EMPTY);
        }
        //从列表中删除获取的消息，实现伪随机，不然重复率太高了，体验比较差
        String msg = RandomUtil.rollAndDelStrFromList(ConstantFreeTime.MSG_TYPE_FREE_TIME);
        //删到五分之一时重新加载集合
        if (ConstantFreeTime.MSG_TYPE_FREE_TIME.size() < ConstantFreeTime.MSG_TYPE_FREE_TIME_MAX_SIZE / 5) {
            FileManagerFreeTime.doCommand(ConstantFile.FILE_COMMAND_LOAD);
        }

        return new PlainText(msg);
    }

}
