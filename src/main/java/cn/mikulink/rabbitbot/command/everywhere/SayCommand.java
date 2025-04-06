package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantFreeTime;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.FreeTimeService;
import cn.mikulink.rabbitbot.service.sys.SwitchService;
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
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 说些日常句子
 */
@Command
public class SayCommand extends EverywhereCommand {

    @Autowired
    private SwitchService switchService;
    @Autowired
    private FreeTimeService freeTimeService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Say", "say");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//        //检查功能开关
//        ReString reStringSwitch = switchService.switchCheck(sender, subject, "say");
//        if (!reStringSwitch.isSuccess()) {
//            return new PlainText(reStringSwitch.getMessage());
//        }
////
////        if (ConstantFreeTime.MSG_TYPE_FREE_TIME.size() <= 0) {
////            return new PlainText(ConstantFreeTime.MSG_TYPE_FREE_TIME_EMPTY);
////        }
//
//        //随机一条日常语句
//        String msg = freeTimeService.randomMsg();
//        return new PlainText(msg);
        return null;
    }

}
