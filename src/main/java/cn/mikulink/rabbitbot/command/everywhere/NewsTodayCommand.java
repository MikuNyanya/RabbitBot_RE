package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.constant.ConstantWeiXin;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.WeiXinAppMsgService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
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
public class NewsTodayCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(NewsTodayCommand.class);

    @Autowired
    private WeiXinAppMsgService weiXinAppMsgService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("NewsToday", "newstoday", "今日简报");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        try {
//            if (CollectionUtil.isNotEmpty(args) && args.size() >= 2) {
//                String token = null;
//                StringBuilder stringBuilder = new StringBuilder();
//                //更新授权信息
//                for (String arg : args) {
//                    if (token == null) {
//                        token = arg;
//                        continue;
//                    }
//                    stringBuilder.append(arg);
//                }
//                weiXinAppMsgService.reTokenCookie(token, stringBuilder.toString());
//                return new PlainText("done");
//            }
//
//            //请求API获取今日简报
//            WeiXinAppMsgInfo weiXinAppMsgInfo = weiXinAppMsgService.getNewsTodayMsg();
//            //转化为消息链
//            return weiXinAppMsgService.parseNewsToday(weiXinAppMsgInfo);

            return weiXinAppMsgService.getSoyijiNews();
        } catch (Exception ex) {
            logger.error("NewsTodayCommand error", ex);
            return new PlainText(ConstantWeiXin.EXEC_ERROR);
        }
    }
}
