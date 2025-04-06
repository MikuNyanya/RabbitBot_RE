package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantWeiXin;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.WeiXinAppMsgService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2021/3/3 10:43
 * for the Reisen
 * <p>
 * 今日简报指令
 */
@Command
public class NewsTodayCommand extends EverywhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(NewsTodayCommand.class);

    @Autowired
    private WeiXinAppMsgService weiXinAppMsgService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("NewsToday", "newstoday", "今日简报");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//        try {
//            return weiXinAppMsgService.getNewsUseSourceConfig();
//        } catch (Exception ex) {
//            logger.error("NewsTodayCommand error", ex);
//            return new PlainText("新闻简报，他挂了");
//        }
        return null;
    }
}
